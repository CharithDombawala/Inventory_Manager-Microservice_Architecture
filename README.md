# 🗃️ Inventory Manager (Microservices Architecture)

A modular inventory management system built using microservices.  
This system includes Product, Order, and Inventory services working independently and communicating via APIs.

---

## 🧱 Architecture Overview

| Service           | Description                             | Tech Stack           |
|-------------------|-----------------------------------------|----------------------|
| **Product Service** | Manage product catalog (CRUD operations) | Node.js + PostgreSQL |
| **Order Service**   | Handle customer orders and payments       | Express.js + MySQL   |
| **Inventory Service** | Track stock levels and update quantities | Python + MongoDB     |

Each service runs independently and communicates over REST APIs.

---

## 🚀 Getting Started

### 1. Clone the repository
```bash
git clone https://github.com/your-username/inventory-manager.git
cd inventory-manager
