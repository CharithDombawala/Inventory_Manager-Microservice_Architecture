# 🗃️ Inventory Manager (Microservices Architecture)

A scalable inventory management system built with microservices architecture.  
All microservices are developed using **Spring Boot** and use **MySQL** as the database.

---

## 🧱 Architecture Overview

| Service           | Description                              | Database  | Framework    |
|-------------------|------------------------------------------|-----------|--------------|
| Product Service   | Manage product catalog (CRUD operations) | MySQL     | Spring Boot  |
| Order Service     | Handle customer orders and payments       | MySQL     | Spring Boot  |
| Inventory Service | Track stock levels and update quantities  | MySQL     | Spring Boot  |

Each service runs independently with its own database and communicates via REST APIs.

---

## 🚀 Getting Started

### 1. Clone the repository
```bash
git clone https://github.com/CharithDombawala/inventory-manager.git
cd inventory-manager
