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
## UML class diagram
![image](https://github.com/fedi-guizeni/e-commerce-microservices-architecture/assets/78599201/94094512-9c85-42e2-8833-f5d4b8e57a65)

## Saga Choreography Pattern
<h2>Overview</h2>
The Saga Choreography pattern is a distributed architectural pattern used for managing long-lived transactions in a microservices architecture. Instead of relying on a central coordinator, as in the Saga Orchestration pattern, each microservice involved in the transaction broadcasts events to communicate and coordinate the overall process.

<h2>Architecture</h2>

In this example project, the Saga Choreography pattern is implemented using a set of microservices, each responsible for specific business capabilities. The microservices communicate by emitting events and reacting to events emitted by other services. This decentralized approach allows for better scalability and fault tolerance.

### Components
- **Order Service:** Manages order creation and processing.
- **Payment Service:** Handles payment processing.
- **product Service:** Manages inventory and order fulfillment.
  
### Communication
Microservices communicate by emitting events such as `Order_created`, `pending_Payment`, and `Payment_Completed`. Each service reacts to relevant events to perform its part in the overall transaction.

# Stripe Integration
This project utilizes the Stripe payment gateway for handling payments. Stripe provides a secure and easy-to-integrate solution for processing online payments. In this project, we leverage Stripe to create Payment Intents and handle webhooks.
## Setting up Stripe

1. **Create a Stripe Account:**
   - If you don't have a Stripe account, create one at [Stripe](https://stripe.com/).

2. **Obtain API Keys:**
   - Obtain your Stripe API keys from the Stripe Dashboard.

3. **Configure API Keys:**
   - Set your Stripe API keys in the environment variables or configuration files of the `payment-service`. You can use test keys during development and switch to live keys in production.

## Payment Intent Workflow

The Payment Service in this project utilizes Stripe Payment Intents to handle the payment process. Here's an overview of the workflow:

1. **Order Placement:**
   - When an order is placed (`OrderPlaced` event), the Payment Service creates a Payment Intent using the Stripe API.

2. **Payment Intent Creation:**
   - The Payment Service emits a `PaymentIntentCreated` event containing the order information and the Stripe Payment Intent ID.

3. **User Confirmation:**
   - Users confirm their payment details through the frontend, triggering the confirmation of the Stripe Payment Intent.

4. **Payment Confirmation Webhook:**
   - Stripe sends a webhook event (`payment_intent.succeeded` or `payment_intent.payment_failed`) to the specified endpoint (`/stripe-webhook`) in your application.

5. **Handling Webhooks:**
   - The Payment Service listens for Stripe webhooks and processes the event, updating the order status accordingly.

## Stripe Webhook Endpoint

Ensure your application has a publicly accessible endpoint to receive Stripe webhooks. In this project, the endpoint is set to `/stripe-webhook`. Configure this endpoint in the Stripe Dashboard under "Webhooks."

## The End
In the end, I hope you enjoyed the application and find it useful,

<h2>NB: this project is still under development</h2>

If you would like to enhance, please:
- Open PRs,
- Give feedback,
- Add new suggestions, and
- Finally, give it a 🌟.



