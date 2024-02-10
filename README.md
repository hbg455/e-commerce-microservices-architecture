# e-commerce springboot microservices 

Important note: This project is still under development and enhancement, so stay tuned.

## Introduction

- This project is a development of a small set of Microservices projects based on Spring Boot, reactive programming, event-driven, Microservices design patterns, and coding best practices.
- This project uses cutting edge technologies like Docker,  Java SE 17, and PostgreSQL database,kafka as a message broker and Stripe for payment, all components developed with TDD in mind, covering integration & performance testing, and many more.
- This project is going to be developed as stages, and will be  documented under the project e-Commerce- springboot microservices  README file .

## Getting Started

System components Structure
First, let's demonstrate the structure of the system in order to understand its components:

```bash
  ecommerce-microservice-backend-app [shopify] --> Parent folder.

    |- api-gateway --> API Gateway server
    |- service-discovery --> Service Registery server
    |- cloud-config --> Centralized Configuration server
    |- user-service --> Manage app users (customers & admins) as well as their credentials
    |- product-service --> Manage app products and their respective categories
    |- order-service --> Manage app orders 
    |- payment-service --> Manage app order payments

|- docker-compose.yaml --> contains all services
```


