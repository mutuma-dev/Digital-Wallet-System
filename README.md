# Digital-Wallet-System
A Digital Wallet System implementing microservices architecture in spring boot
# Architecture
The system implements :
1) user-service
2) wallet-service
3) transaction-service
4) eureka-server for service registry and 
5) API gateway for routing.
# Features
The system has all core functionalities of a digital wallet including deposit, withdrawal and transfer of funds while ensuring production-grade design principles.
# Running the app
Run each microservice indididually in the order of:
1) Eureka-server first
2) API gateway
3) Each of the other microservices.
# Commands to run app
1) mvn clean install (at the root for module management)
2) mvn spring-boot:run (for each microservice)
# Routes
1) /api/v1/users
2) /api/v1/wallets
3) /api/v1/transactions

