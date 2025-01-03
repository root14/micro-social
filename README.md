### What is micro-social?
Micro-social is an open-source social media backend application built with a microservice architecture using Spring Boot. It supports key features like user registration, login, and profile management with JWT-based authentication. Posts are analyzed with AI, and trending data is stored in Redis for real-time access.

The application is designed for scalability and integrates advanced technologies such as:

- MongoDB for data storage
- Elasticsearch for powerful search and indexing
- Redis for caching and trend data storage
- Spring Cloud Gateway for API routing
- Eureka Server for service discovery

This project showcases a robust and modern backend system built to handle complex social media use cases efficiently.

---

### Diagram
<p align="center">
  <img src="https://github.com/root14/micro-social/blob/master/doc/open-social-api.png" width="800" title="api-diagram">
</p>

---


## API Endpoints

### **Authentication**
| Endpoint               | Method | Description                      | Authorization | 
|------------------------|--------|----------------------------------|---------------|
| `/auth/register`       | POST   | Register a new user              | No            |
| `/auth/login`          | POST   | Login and receive a JWT token    | No            |

### **User Management**
| Endpoint               | Method | Description                      | Authorization |
|------------------------|--------|----------------------------------|---------------|
| `/user/getProfile`     | GET    | Retrieve user profile by username | Bearer Token |
| `/user/updateProfile`  | PATCH  | Update user profile information   | Bearer Token |
| `/user/delete`         | DELETE | Delete a user                     | Bearer Token |

### **Post Management**
| Endpoint                         | Method | Description                                  | Authorization |
|----------------------------------|--------|----------------------------------------------|---------------|
| `/post/addPost`                  | POST   | Add a new post                              | Bearer Token |
| `/post/getPost`                  | GET    | Retrieve a post by ID                       | Bearer Token |
| `/post/getPostByAuthorId`        | GET    | Retrieve posts by author ID                 | Bearer Token |
| `/post/updatePostByPostId`       | PUT    | Update a post by post ID                    | Bearer Token |
| `/post/deletePost`               | DELETE | Delete a post                               | Bearer Token |

### **Trends**
| Endpoint       | Method | Description                    | Authorization |
|----------------|--------|--------------------------------|---------------|
| `/trends`      | GET    | Retrieve trending posts data   | Bearer Token |

### **Health**
| Endpoint       | Method | Description                    | Authorization |
|----------------|--------|--------------------------------|---------------|
| `/health`      | GET    | Check the health of the service | No            |

---

### Prerequisites
- Java 11 or higher
- Maven
- MongoDb (version 5.x or compatible)
- RabbitMQ (version 4.x or compatible)
- Elasticsearch (version 8.x or compatible)
- Redis (version 8.x or compatible)

