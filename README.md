# API REST - Registro de Avistamientos de Aves
Proyecto académico de Ingeniería de Software 2 (ISW-2).

API REST para registrar avistamientos de aves, construida con Java + Spring Boot + H2 Database.

## Antes de ejecutar

Asegúrate de tener instalado **Java** y **Maven** en tu equipo. Puedes verificarlo ejecutando los siguientes comandos en tu terminal:

    java -version
    mvn -version

## Cómo ejecutar la aplicación

1. **Clona este repositorio:**
   
       git clone https://github.com/lsramirezp-tech/Avistamiento_software.git

2. **Entra a la carpeta del proyecto:**
   
       cd Avistamiento_software

3. **Ejecuta la aplicación:**
   
       mvn spring-boot:run

4. **Accede a la API:**
   La API quedará disponible en: `http://localhost:8080`

## Endpoints disponibles y Ejemplos de Uso (cURL)

### 1. Listar todos los avistamientos

* **Método y Ruta:** `GET /avistamientos`
* **Respuesta:** `200 OK` con la lista completa en formato JSON.
* **Ejemplo cURL:**
  
      curl -X GET http://localhost:8080/avistamientos

### 2. Ver un avistamiento por ID

* **Método y Ruta:** `GET /avistamientos/{id}`
* **Respuesta:** `200 OK` con el avistamiento encontrado, o `404 Not Found` si no existe.
* **Ejemplo cURL:**
  
      curl -X GET http://localhost:8080/avistamientos/1

### 3. Registrar un avistamiento

* **Método y Ruta:** `POST /avistamientos`
* **Respuesta:** `201 Created` con el avistamiento creado, o `400 Bad Request` si faltan datos obligatorios.
* **Ejemplo cURL:**
  
      curl -X POST http://localhost:8080/avistamientos -H "Content-Type: application/json" -d "{\"especie\":\"Colibri\",\"lugar\":\"Parque Simon Bolivar\",\"fecha\":\"2026-10-11\",\"observador\":\"Laura\"}"

### 4. Actualizar un avistamiento

* **Método y Ruta:** `PUT /avistamientos/{id}`
* **Respuesta:** `200 OK` con el avistamiento actualizado, o `404 Not Found` si no existe.
* **Ejemplo cURL:**
  
      curl -X PUT http://localhost:8080/avistamientos/1 -H "Content-Type: application/json" -d "{\"especie\":\"Gavilan\",\"lugar\":\"Reserva Chingaza\",\"fecha\":\"2026-10-12\",\"observador\":\"Laura\"}"


### 5. Eliminar un avistamiento

* **Método y Ruta:** `DELETE /avistamientos/{id}`
* **Respuesta:** `204 No Content` si se eliminó con éxito, o `404 Not Found` si no existe.
* **Ejemplo cURL:**
  
      curl -X DELETE http://localhost:8080/avistamientos/1

## Base de Datos (H2)

La aplicación utiliza una base de datos H2 integrada en memoria. Puedes acceder a su consola web de administración en:

* **URL:** `http://localhost:8080/h2-console`
* **JDBC URL:** `jdbc:h2:mem:avistamientosdb`
* **Usuario:** `sa`
* **Password:** *(Déjalo completamente vacío)*

## Autor

* **Laura Sofía Ramírez Pulido**
* Estudiante de Ingeniería de Sistemas
