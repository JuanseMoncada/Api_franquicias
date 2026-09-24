# Franquicias API

API desarrollada como solución a una prueba técnica para la gestión de franquicias, sucursales y productos.

El sistema permite administrar una estructura donde una **franquicia** puede contener múltiples **sucursales**, y cada sucursal puede contener múltiples **productos**, cada uno con una cantidad de stock determinada.

## Descripción del proyecto

La aplicación permite gestionar la siguiente estructura:

```text
Franquicia
    └── Sucursal
            └── Producto
```

Una **Franquicia** contiene:

- Identificador.
- Nombre.
- Una o varias sucursales.

Una **Sucursal** contiene:

- Identificador.
- Nombre.
- Franquicia a la que pertenece.
- Uno o varios productos.

Un **Producto** contiene:

- Identificador.
- Nombre.
- Cantidad disponible en stock.
- Sucursal a la que pertenece.

Además de las operaciones CRUD correspondientes, la aplicación permite consultar los productos con mayor stock asociados a una franquicia.

---

## Tecnologías utilizadas

El proyecto fue desarrollado utilizando:

- **Java 21**
- **Spring Boot 4.1.1**
- **Spring Web MVC**
- **Spring Data JPA**
- **Hibernate**
- **Thymeleaf**
- **Lombok**
- **Microsoft SQL Server**
- **Microsoft JDBC Driver for SQL Server**
- **Maven**

---

## Requisitos previos

Antes de ejecutar el proyecto es necesario tener instaladas las siguientes herramientas.

### Java 21

El proyecto utiliza **Java 21**.

Para comprobar la versión instalada:

```bash
java -version
```

Debe mostrarse una versión correspondiente a Java 21.

Puede utilizarse cualquier distribución compatible de JDK 21.

---

### Entorno de desarrollo

Puede utilizarse cualquier IDE compatible con proyectos Java y Maven.

Por ejemplo:

- IntelliJ IDEA
- Visual Studio Code
- Eclipse
- Spring Tool Suite

El proyecto fue estructurado como una aplicación Maven, por lo que no depende específicamente de un IDE.

---

### Maven

El proyecto incluye **Maven Wrapper**, por lo que no es obligatorio tener Maven instalado globalmente.

En Windows puede utilizarse:

```bash
mvnw.cmd
```

En Linux/macOS:

```bash
./mvnw
```

También puede utilizarse una instalación local de Maven:

```bash
mvn -version
```

---

## Ejecución con Docker

La aplicación también puede ejecutarse mediante Docker.

El proyecto utiliza un **Dockerfile multi-stage**:

- Una primera etapa utiliza Java 21 JDK para compilar la aplicación con Maven.
- Una segunda etapa utiliza Java 21 JRE únicamente para ejecutar el archivo `.jar` generado.
- Esto permite reducir el tamaño de la imagen final y evita incluir herramientas de compilación innecesarias.

### Requisitos

Para ejecutar la aplicación mediante Docker es necesario tener instalado:

- Docker Desktop
- SQL Server ejecutándose localmente
- La base de datos `Accenture_Prueba_Tec_Franquicias` creada mediante el script incluido en la carpeta `database/`

### Construir la imagen

Desde la raíz del proyecto ejecutar:

```bash
docker build -t franquicias-api .
```

Para verificar que la imagen fue creada:

```bash
docker images
```

### Ejecutar el contenedor

La aplicación se conecta al SQL Server instalado en la máquina host mediante:

```text
host.docker.internal
```

Ejecutar el contenedor proporcionando las credenciales correspondientes de SQL Server:

```bash
docker run --name franquicias-container -p 8080:8080 \
  -e "SPRING_DATASOURCE_URL=jdbc:sqlserver://host.docker.internal:1433;databaseName=Accenture_Prueba_Tec_Franquicias;encrypt=false;trustServerCertificate=true" \
  -e "SPRING_DATASOURCE_USERNAME=TU_USUARIO" \
  -e "SPRING_DATASOURCE_PASSWORD=TU_PASSWORD" \
  franquicias-api
```

> En Windows CMD, el comando anterior también puede ejecutarse en una sola línea.

Una vez iniciada la aplicación estará disponible en:

```text
http://localhost:8080
```

Vistas disponibles:

```text
http://localhost:8080/franquicias
http://localhost:8080/sucursales
http://localhost:8080/productos
```

### Detener el contenedor

Si el contenedor se está ejecutando en primer plano se puede detener con:

```text
Ctrl + C
```

También puede detenerse mediante:

```bash
docker stop franquicias-container
```

Para eliminar el contenedor:

```bash
docker rm franquicias-container
```

### Consideración sobre la base de datos

Cuando la aplicación se ejecuta directamente desde el entorno local, SQL Server se encuentra disponible mediante:

```text
localhost:1433
```

Sin embargo, dentro del contenedor Docker, `localhost` hace referencia al propio contenedor.

Por esta razón, para acceder al SQL Server instalado en la máquina host se utiliza:

```text
host.docker.internal:1433
```

Las credenciales de SQL Server no se almacenan dentro del Dockerfile ni se publican en el repositorio. Deben proporcionarse mediante variables de entorno al ejecutar el contenedor.

---

### Microsoft SQL Server

Es necesario disponer de una instancia local de **Microsoft SQL Server**.

La configuración utilizada durante el desarrollo corresponde al puerto estándar:

```text
localhost:8080
```

También se recomienda utilizar **SQL Server Management Studio (SSMS)** u otra herramienta compatible para administrar la base de datos y ejecutar el script SQL incluido en el proyecto.

---

## Clonar el proyecto

Clonar el repositorio:

```bash
git clone <URL_DEL_REPOSITORIO>
```

Ingresar al directorio:

```bash
cd franquicias-api
```

---

# Configuración de la base de datos

El repositorio incluye un script SQL encargado de crear la estructura necesaria para ejecutar la aplicación.

El archivo se encuentra en:

```text
database/Accenture_Prueba_Tec_Franquicias.sql
```

> No es necesario crear manualmente las tablas. El script incluido en el proyecto contiene la estructura requerida por la aplicación.

## 1. Iniciar SQL Server

Asegúrese de que el servicio de Microsoft SQL Server se encuentre ejecutándose.

La aplicación espera por defecto una instancia disponible mediante:

```text
localhost:8080
```

---

## 2. Abrir SQL Server Management Studio

Conectarse a la instancia local de SQL Server utilizando las credenciales configuradas en el equipo.

Por ejemplo:

```text
Servidor: localhost
Puerto: 8080
Autenticación: SQL Server Authentication
```

Las credenciales dependerán de la configuración local de cada entorno.

---

## 3. Ejecutar el script de base de datos

Desde SQL Server Management Studio:

1. Abrir el archivo:

```text
database/Accenture_Prueba_Tec_Franquicias.sql
```

2. Ejecutar el script completo.

El script creará la base de datos:

```text
Accenture_Prueba_Tec_Franquicias
```

junto con las tablas y relaciones necesarias para el funcionamiento de la aplicación.

La estructura principal de datos corresponde a:

```text
Franquicias
    │
    └── Sucursales
            │
            └── Productos
```

---

# Configuración de la conexión

La configuración de Spring Boot se encuentra en:

```text
src/main/resources/application.properties
```

La conexión puede configurarse de la siguiente manera:

```properties
spring.application.name=franquicias-api

# Conexión a SQL Server
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=Accenture_Prueba_Tec_Franquicias;encrypt=false;trustServerCertificate=true
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver

spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_PASSWORD
```

Reemplace:

```text
TU_USUARIO
TU_PASSWORD
```

por las credenciales correspondientes a su instancia local de SQL Server.

Por ejemplo, si utiliza el usuario `sa`:

```properties
spring.datasource.username=sa
spring.datasource.password=TU_PASSWORD_SQL_SERVER
```

> Por seguridad, no se recomienda almacenar contraseñas reales o credenciales sensibles dentro de repositorios públicos.

---

# Instalación de dependencias

Una vez configurada la base de datos, desde la raíz del proyecto ejecutar:

### Windows

```bash
mvnw.cmd clean install
```

### Linux/macOS

```bash
./mvnw clean install
```

También puede utilizar Maven directamente:

```bash
mvn clean install
```

Maven descargará automáticamente las dependencias definidas en el archivo:

```text
pom.xml
```

---

# Ejecución de la aplicación

Una vez creada la base de datos y configurado `application.properties`, ejecutar:

### Windows

```bash
mvnw.cmd spring-boot:run
```

### Linux/macOS

```bash
./mvnw spring-boot:run
```

También puede ejecutarse desde el IDE utilizando la clase principal:

```text
FranquiciasApiApplication
```

Cuando la aplicación haya iniciado correctamente, estará disponible por defecto en:

```text
http://localhost:8080
```

---

# Arquitectura del proyecto

El proyecto utiliza una arquitectura organizada por capas.

```text
franquicias-api
│
├── database
│   └── Accenture_Prueba_Tec_Franquicias.sql
│
├── src/main/java/com/franquicias/franquicias_api
│   │
│   ├── Controller
│   │   ├── FranquiciasController
│   │   ├── ProductosController
│   │   └── SucursalController
│   │
│   ├── DTO
│   │   ├── FranquiciaRequest
│   │   ├── FranquiciaResponse
│   │   ├── ProductosRequest
│   │   ├── ProductosResponse
│   │   ├── SucursalRequest
│   │   └── SucursalResponse
│   │
│   ├── Entity
│   │   ├── Franquicia
│   │   ├── Productos
│   │   └── Sucursal
│   │
│   ├── Repository
│   │   ├── FranquiciaRepository
│   │   ├── ProductosRepository
│   │   └── SucursalRepository
│   │
│   ├── Service
│   │   ├── FranquiciasService
│   │   ├── ProductosServices
│   │   └── SucursalServices
│   │
│   └── FranquiciasApiApplication
│
├── src/main/resources
│   │
│   ├── static
│   │   ├── Franquicias
│   │   ├── Productos
│   │   └── Sucursal
│   │
│   ├── templates
│   │   ├── fragments
│   │   ├── franquicias.html
│   │   ├── productos.html
│   │   └── sucursales.html
│   │
│   └── application.properties
│
├── pom.xml
└── README.md
```

## Responsabilidad de las capas

**Controller**

Recibe las peticiones HTTP y expone los endpoints de la aplicación.

**Service**

Contiene la lógica de negocio y coordina las operaciones realizadas sobre los datos.

**Repository**

Gestiona el acceso a la base de datos mediante Spring Data JPA.

**Entity**

Representa las entidades persistidas en SQL Server.

**DTO**

Define los objetos utilizados para recibir y devolver información mediante la API, evitando exponer directamente las entidades de persistencia.

---

# Endpoints

## Franquicias

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/franquicias` | Visualiza las franquicias |
| `POST` | `/creacionFranquicia` | Crea una nueva franquicia |
| `DELETE` | `/franquicias/{id}` | Elimina una franquicia por ID |
| `PUT` | `/franquicias/{id}` | Actualiza una franquicia por ID |

### Ejemplo de respuesta

```json
{
  "franquiciaId": 15,
  "nombreFranquicia": "McDonald's"
}
```

---

## Sucursales

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/sucursales` | Visualiza las sucursales |
| `POST` | `/crearSucursales` | Crea una nueva sucursal |
| `DELETE` | `/eliminarSucursal/{idSucursal}` | Elimina una sucursal por ID |
| `PUT` | `/actualizarSucursal/{idSucursal}` | Actualiza una sucursal por ID |

### Ejemplo de respuesta

```json
{
  "idFranquicia": 15,
  "idSucursal": 4,
  "nombreFranquicia": "McDonald's",
  "nombreSucursal": "Sucursal Centro"
}
```

---

## Productos

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/productos` | Visualiza los productos junto con información de sucursales y franquicias |
| `POST` | `/crearProductos` | Crea un nuevo producto |
| `DELETE` | `/eliminarProducto/{idProducto}` | Elimina un producto por ID |
| `PUT` | `/actualizarProducto/{idProducto}` | Actualiza un producto por ID |
| `GET` | `/franquicias/{idFranquicia}/productos-mayor-stock` | Consulta los productos con mayor stock de una franquicia |

### Ejemplo de respuesta

```json
{
  "cantidadProductoStock": 50,
  "idProducto": 9,
  "idSucursal": 1,
  "nombreProducto": "Big Mac",
  "nombreSucursal": "NORTE"
}
```

---

# Consulta de productos con mayor stock

La aplicación incluye un endpoint para consultar los productos con mayor cantidad de stock asociados a las sucursales de una franquicia.

```http
GET /franquicias/{idFranquicia}/productos-mayor-stock
```

Ejemplo:

```http
GET /franquicias/15/productos-mayor-stock
```

La respuesta contiene una lista de productos que cumplen con el criterio de mayor stock para las sucursales correspondientes a la franquicia consultada.

Ejemplo:

```json
[
  {
    "cantidadProductoStock": 50,
    "idProducto": 9,
    "idSucursal": 1,
    "nombreProducto": "Big Mac",
    "nombreSucursal": "NORTE"
  }
]
```

---

# Modelo de datos

La aplicación maneja una relación jerárquica:

```text
Franquicia (1)
     │
     │
     └──── (N) Sucursal
                  │
                  │
                  └──── (N) Producto
```

Esto significa que:

- Una franquicia puede tener múltiples sucursales.
- Cada sucursal pertenece a una franquicia.
- Una sucursal puede tener múltiples productos.
- Cada producto pertenece a una sucursal.

La integridad de estas relaciones se mantiene tanto desde la base de datos como desde la capa de persistencia de la aplicación.

---

# Interfaz web

Además de los endpoints de la API, el proyecto contiene vistas desarrolladas utilizando **Thymeleaf**.

Las principales vistas son:

```text
/franquicias
/sucursales
/productos
```

Estas permiten visualizar y gestionar la información correspondiente a las entidades principales del sistema.

---

# Pruebas de los endpoints

Los endpoints pueden probarse utilizando herramientas como:

- Postman
- Insomnia
- Bruno
- cURL
- Thunder Client

Por ejemplo:

```bash
curl http://localhost:8080/franquicias
```

Para operaciones `POST`, `PUT` y `DELETE`, se debe utilizar el método HTTP correspondiente al endpoint que se desea probar.

O simplemente ejecutar el proyecto, el cual ya incluye las vistas previamente generadas para poder interactuar con el sistema.

---

# Flujo recomendado para ejecutar el proyecto

Después de clonar el repositorio, el orden recomendado es:

```text
1. Verificar Java 21
        ↓
2. Verificar que SQL Server esté ejecutándose
        ↓
3. Ejecutar el script SQL ubicado en /database
        ↓
4. Configurar usuario y contraseña en application.properties
        ↓
5. Instalar/compilar dependencias con Maven
        ↓
6. Ejecutar Spring Boot
        ↓
7. Acceder a http://localhost:8080
        ↓
8. Probar los endpoints
```

---

# Consideraciones de seguridad

Las credenciales utilizadas para conectarse a SQL Server dependen del entorno donde se ejecute la aplicación.

No se recomienda almacenar contraseñas reales directamente en un repositorio público.

Antes de ejecutar el proyecto, configure:

```properties
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_PASSWORD
```

con las credenciales correspondientes a su instancia local.

---

# Autor

Proyecto desarrollado por Sebastian Moncada, como solución a una **prueba técnica de desarrollo Backend con Java y Spring Boot**.