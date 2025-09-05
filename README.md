# Franchise Management API

Este proyecto implementa una API reactiva para la gestión de franquicias, sucursales y productos, utilizando **Spring WebFlux**, **MongoDB** y el enfoque de **Clean Architecture** provisto por el plugin de Bancolombia.

## 🚀 Características

- **Spring WebFlux** para programación reactiva y no bloqueante.
- **Clean Architecture** para mantener la separación de responsabilidades.
- **MongoDB** como base de datos NoSQL reactiva.
- **Handlers funcionales** en lugar de controladores tradicionales.
- **Validación de arquitectura** con `validateStructure`.
- **Tests unitarios** con WebTestClient y Mockito.

---

## 🏗️ Requisitos

- Java 21+
- Gradle 8.4+
- Docker (opcional, para MongoDB local)
- MongoDB o contenedor con `docker run -d -p 27017:27017 mongo`

---

## 📂 Estructura del proyecto


**Reglas de dependencias (`validateStructure`):**
- **Model**: sin dependencias externas.
- **UseCase**: depende solo de Model.
- **Infraestructura**: puede depender de Model y UseCase.
- **App-Service**: capa más externa, depende de todos.

---

## ⚡ Ejecución

### 1. Clonar el repositorio
```bash
git clone https://github.com/mi-org/franchise-api.git
cd franchise-api

./gradlew clean build
./gradlew bootRun

http://localhost:8080

| Método | Endpoint                                                                                      | Descripción                                   |
| ------ | --------------------------------------------------------------------------------------------- | --------------------------------------------- |
| POST   | `/api/franchises`                                                                             | Crear una franquicia                          |
| POST   | `/api/franchises/{franchiseId}/branches`                                                      | Agregar una sucursal                          |
| POST   | `/api/franchises/{franchiseId}/branches/{branchName}/products`                                | Agregar un producto a una sucursal            |
| DELETE | `/api/franchises/{franchiseId}/branches/{branchName}/products/{productName}`                  | Eliminar un producto                          |
| PUT    | `/api/franchises/{franchiseId}/branches/{branchName}/products/{productName}/stock/{newStock}` | Actualizar stock del producto                 |
| GET    | `/api/franchises/{franchiseId}/branches/top-products`                                         | Obtener producto con mayor stock por sucursal |

