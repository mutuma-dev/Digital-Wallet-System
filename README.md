# Digital-Wallet-System
A Digital Wallet System implementing microservices architecture using SPRING BOOT, SPRING CLOUD(EUREKA), SPRING CLOUD GATEWAY and JPA.
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
# User Service Endpoints (/api/v1/users)
1) POST /api/v1/users → Create user
2) GET /api/v1/users/{userId} → Get user by ID
3) GET /api/v1/users → Get all users
# Wallet Service Endpoints (api/v1/wallets)
1) POST /api/v1/wallets → Create wallet
2) GET /api/v1/wallets/{walletId} → Get wallet by id
3) GET /api/v1/wallets → Get all wallets
# Transaction Service Endpoints (/api/v1/transactions)
1) POST /api/v1/transactions → Create transaction
2) GET /api/v1/transaction/{transactionId} → Get transaction by id
3) GET /api/v1/transactions → Get all transactions


