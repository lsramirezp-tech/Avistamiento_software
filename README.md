**API REST - Registro de Avistamientos de Aves**

Proyecto académico de Ingeniería de Software 2 (ISW-2).

API REST para registrar avistamientos de aves, construida con Java + Spring Boot + H2 Database.

**Cómo ejecutar la aplicación**

Clona este repositorio:
git clone https://github.com/lsramirezp-tech/Avistamiento_software.git (github.com in Bing)

Entra a la carpeta del proyecto:
cd Avistamiento_software

Ejecuta la aplicación:
mvn spring-boot:run

La API quedará disponible en:
http://localhost:8080

Endpoints disponibles

Listar todos los avistamientos
GET /avistamientos
Respuesta: 200 OK con lista en JSON

Ver un avistamiento por ID
GET /avistamientos/{id}
Respuesta: 200 OK con el avistamiento, o 404 Not Found si no existe

Registrar un avistamiento
POST /avistamientos
Respuesta: 201 Created con el avistamiento creado, o 400 Bad Request si faltan datos

Ejemplo JSON:
{
"especie": "Colibrí",
"lugar": "Parque Simón Bolívar",
"fecha": "2026-10-11",
"observador": "Laura"
}

Actualizar un avistamiento
PUT /avistamientos/{id}
Respuesta: 200 OK con el avistamiento actualizado, o 404 Not Found si no existe

Ejemplo JSON:
{
"especie": "Gavilán",
"lugar": "Reserva Chingaza",
"fecha": "2026-10-12",
"observador": "Laura"
}

Eliminar un avistamiento
DELETE /avistamientos/{id}
Respuesta: 204 No Content si se eliminó, o 404 Not Found si no existe

Bonus (opcional)

GET /avistamientos/resumen
Devuelve un resumen de avistamientos por especie en formato JSON, por ejemplo:
{
"Colibrí": 3,
"Gavilán": 2
}

Base de datos

La aplicación usa H2 en memoria.
Accede a la consola en:
http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:avistamientosdb
Usuario: sa
Password: vacío


Autor:

Laura Sofía Ramírez Pulido

Estudiante de Ingeniería de Sistemas
