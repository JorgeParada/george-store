# george-store
Georgestore – Backend API (Spring Boot 3 + JWT + Swagger + H2)

Este proyecto es una API REST que gestiona usuarios, autenticación JWT, órdenes, clientes y pagos.
Está desarrollado con Spring Boot 3, Spring Security, JWT, Spring Data JPA, H2 Database, con arquitectura DDD (Domain-Driven Design)
y documentado con Swagger/OpenAPI.

Contenido

1 Requisitos previos
2 Tecnologías utilizadas
3 Clonar el proyecto
4 Construcción e instalación
5 Ejecución del proyecto
6 Acceso a Swagger
7 Base de datos H2
8 Autenticación JWT
9 Colección Postman

✅ 1. Requisitos previos y 2 Tecnologías utilizadas
Asegúrate de tener instalado:

Herramienta	                       Versión recomendada
Java	                                       21+
Maven Wrapper (mvnd o mvnw)	       Incluido en el proyecto
Git	                               Opcional, para clonar repositorio
IDE	                               VSCode 

💡 Si usas VSCode como en mi caso, asegúrate de tener instalado:

1- Extension Pack for Java
2- Spring Boot Extension Pack


📥 3. Clonar el proyecto
git clone https://github.com/
cd georgestore

🛠️ 4. Construcción e instalación

El proyecto incluye Maven Wrapper.
Ejecuta en Windows: mvn clean install

O si estás usando mvnd: mvnd clean install

✅ 5. Ejecución del proyecto

En VSCode, puedes presionar Run > Start DebugginG - (Pero si tienes instaladas las 2 extensiones mencionadas anteriormente como en mi caso,
ve al panel izquierdo de tu VSCode y selecciona Spring Boot Dashboard > Run).

Pero si lo quieres correr desde la terminal: mvnw spring-boot:run

La API se inicializará en: http://localhost:8080

✅ 6. Acceso a Swagger:

Una vez inicializado el backend, para visualizar Swagger entra a: http://localhost:8080/swagger-ui/index.html#/

El documento OpenAPI está en: http://localhost:8080/v3/api-docs

✅ 7. Base de datos H2

El proyecto usa una base en memoria H2 para pruebas.

✅ 8. Autenticación JWT

El flujo de autenticación y prueba de endpoints es:

1. Registrar usuario
	1.1 POST /auth/register

2. Iniciar sesión
	2.1 POST /auth/login

Usar token generado en login en endpoints protegidos (Register y Login no llevan Authorization ya que ellos generan los token, los demás si lo necesitan, usarán el Token dado en Login,
cada Token de Login después de generado uno nuevo es cambiante, el de abajo es un ejemplo, debe generarse uno nuevo)

Por ejemplo, la respuesta de login incluirá:

{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnZW9yZ2UiLCJpYXQiOjE3NjUxOTA5NTksImV4cCI6MTc2NTE5NDU1OX0.vPR_9YE_dXL4iyEV_54mBDqNgHn2we7XuxEdSJDIVsc",
    "tokenType": "Bearer"
}

Tipo y en dónde colocar el Token en Postman para probar:
Authorization - Auth type: Bearer token - Colocar token en input de token

3. Crear cliente
	3.1 Post 

4. Listar cliente (Opcional para listar) 
	4.1 Get

5. Editar cliente (Opcional para editar) 
	5.1 Put

6. Delete client (Si se desea eliminar se eliminará, luego hay que repetir paso 3)
	6.1 Del

7. Listar producto de fakestoreapi (Listará todos los items que estén en fakestoreapi si se requiere verlos para conocer sus datos)
	7.1 Get

8. Seleccionar producto de fakestoreapi por id (si se quiere buscar uno en específico puede realizarse su búsqueda individual)
	8.1 Get

9. Crear orden (Se crea la orden u ordenes) 
	9.1 Post

10. Pago (Realiza el pago de la orden)
	10.1 Post

11. Detalle de la órden (Se detalla la órden y su estado de pago)
	11.1 Get

✅ 9. Colección Postman: 
https://.postman.co/workspace/Personal-Workspace~d6cd11e1-9431-41fb-95d7-a33e1d246692/collection/10776854-d02d449a-04a6-48a6-b362-8e46f3cc58aa?action=share&creator=10776854

