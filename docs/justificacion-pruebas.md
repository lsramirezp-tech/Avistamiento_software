# Justificación de Pruebas - API de Avistamientos

## 1. Qué cambió en el código
Para hacer la API testeable, se extrajo toda la lógica de validación de negocio del controlador hacia una clase estática dedicada llamada `ValidadorDeAvistamiento`. Esto permite probar las reglas de la especie, la fecha, el lugar y el observador de forma completamente aislada, sin necesidad de levantar el contexto de Spring Boot ni simular peticiones HTTP. Adicionalmente, el controlador se refactorizó para capturar la excepción `IllegalArgumentException` y responder con el código HTTP 400 y el mensaje específico del error.

## 2. Tabla de casos (Parte A)
Se aplicaron las técnicas de partición de equivalencia y valores límite para elegir los datos de prueba.

| Campo | Valor de prueba (Input) | Condición a evaluar | Tipo de caso | Resultado esperado |
| :--- | :--- | :--- | :--- | :--- |
| **Especie** | "Pi" | 2 caracteres | Límite inválido inferior | Falla (IllegalArgumentException) |
| **Especie** | "Pez" | 3 caracteres | Límite válido inferior | Pasa validación |
| **Especie** | "Loro" | 4 caracteres | Representativo válido | Pasa validación |
| **Especie** | 50 caracteres ("A"x50) | 50 caracteres | Límite válido superior | Pasa validación |
| **Especie** | 51 caracteres ("A"x51) | 51 caracteres | Límite inválido superior | Falla (IllegalArgumentException) |
| **Fecha** | Ayer (ej. 2026-10-22) | Fecha pasada | Representativo válido | Pasa validación |
| **Fecha** | Hoy (ej. 2026-10-23) | Fecha actual | Límite válido | Pasa validación |
| **Fecha** | Mañana (ej. 2026-10-24) | Fecha futura | Límite inválido | Falla (IllegalArgumentException) |

Elegimos estos valores porque representan las fronteras exactas donde las condiciones lógicas (`length < 3`, `length > 50`, `isAfter`) cambian de comportamiento, garantizando que no se adivinen los fallos sino que se evalúen matemáticamente.

## 3. Por qué cada nivel
* **Unitarias (`ValidadorDeAvistamientoTest`):** Se eligió este nivel para la lógica de negocio porque son pruebas rápidas y verifican exhaustivamente los bordes algorítmicos. Una prueba de integración para verificar que una especie de 2 caracteres falla sería muy costosa en tiempo de ejecución.
* **Integración (`AvistamientoControllerIntegrationTest`):** Detecta lo que las unitarias no pueden: la correcta conversión de los objetos a JSON (serialización), el enrutamiento HTTP (códigos 200, 201, 400, 404) y la correcta interacción con la base de datos (por ejemplo, el efecto donde un POST realmente guarda el registro y un DELETE lo elimina).

## 4. Los dobles
* **Stub (Reloj Fijo):** En las pruebas unitarias pasamos una fecha quemada (`HOY_FIJO = LocalDate.of(2026, 10, 23)`) como parámetro de validación. Este stub es necesario porque si tomáramos el reloj del sistema, la prueba del límite de "fecha de mañana" pasaría hoy, pero fallaría automáticamente al ejecutar la suite el día de mañana, rompiendo la determinancia de la prueba.
* **Fake (Base de datos H2):** Para las pruebas de integración, usamos una base de datos en memoria (H2) en lugar de la base de datos real. Funciona como un doble rápido que nos permite borrar todos los registros (`repository.deleteAll()`) antes de cada prueba sin afectar los datos de producción o desarrollo persistente.

## 5. Lo que no probaron
1. **Los Getters y Setters del modelo `Avistamiento`:** Decidimos no probarlos porque carecen de lógica condicional; son estructuras de datos puras y probar código autogenerado o trivial no aporta valor.
2. **El `AvistamientoRepository` aislado:** No escribimos pruebas para el repositorio de Spring Data JPA porque es una interfaz cuyo comportamiento interno ya está garantizado y testeado exhaustivamente por el framework de Spring.

## 6. Lectura de la cobertura
Al ejecutar JaCoCo con el comando `mvn verify`, observamos una cobertura alta en las clases de Controlador y Validador. 
Identificamos líneas sin cubrir en:
* El método `main` de la clase `AvistamientosApiApplication`. No vale la pena escribir una prueba para esto, ya que su única responsabilidad es delegar el arranque al contenedor de Spring, lo cual ya se valida implícitamente al correr las pruebas de integración con `@SpringBootTest`.
* Algunos Getters/Setters del modelo. Como justificamos en el punto anterior, no ameritan pruebas adicionales porque no contienen lógica propensa a errores.