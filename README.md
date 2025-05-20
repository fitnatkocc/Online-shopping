# Online Shopping System



## Table of Contents

1. [Overview](#overview)  
2. [Features](#features)  
3. [Quick Start](#quick-start)  
   - [Prerequisites](#prerequisites)  
   - [Installation](#installation)  
   - [Running the Application](#running-the-application)  
4. [Project Structure](#project-structure)  
5. [Usage Examples](#usage-examples)  
6. [AI Tool Usage Report](#ai-tool-usage-report)  
7. [Project Report](#project-report)  
8. [Contributing](#contributing)  
9. [Authors & Acknowledgments](#authors--acknowledgments)  
10. [License](#license)  

---

## 1. Overview

The **Online Shopping System** is a Java Swing-based desktop application designed to simulate a real-world e-commerce experience.  
- **Customers** can browse product categories, add items to their shopping cart or wishlist, place orders, and submit product reviews.  

This project demonstrates core OOP principles (encapsulation, inheritance, polymorphism, abstraction) and includes a clear GUI, modular service layers, and data persistence in memory.

---

## 2. Features

- **Secure Authentication**  
  Users can register and log in securely; sessions are tracked in-memory.
![WhatsApp Görsel 2025-05-20 saat 21 31 05_d707109c](https://github.com/user-attachments/assets/017dc833-7aa5-47d9-8e18-0db8f937a8bb)

- **Product Catalog & Categories**  
  Browse products organized by categories; view detailed descriptions and pricing.
   ![WhatsApp Görsel 2025-05-20 saat 21 53 40_c109007a](https://github.com/user-attachments/assets/efe2d5c2-2518-4cc8-9d1a-11bc2b6028f5)


   
- **Shopping Cart & Wishlist**  
  Add/remove items to cart or wishlist; view totals and item counts at any time.
     ![WhatsApp Görsel 2025-05-20 saat 20 34 30_25b5d47e](https://github.com/user-attachments/assets/910900b0-ff9e-4b5c-9d74-52fb1eb2f14f)
   
  ![WhatsApp Görsel 2025-05-20 saat 20 34 35_65975269](https://github.com/user-attachments/assets/e5f14860-0800-4703-8c9e-a0e0c3ce4765)


- **Order Placement**  
  Checkout with current cart contents.
![WhatsApp Görsel 2025-05-20 saat 20 36 30_72fe0e61](https://github.com/user-attachments/assets/cf522a2b-e12d-4881-8b53-eee4731259c3)
- **Order Status**  
  After placing an order, the user can track the status of the order instantly.
  ![WhatsApp Görsel 2025-05-20 saat 21 42 34_be190052](https://github.com/user-attachments/assets/2e009f91-28e5-42c0-a4cf-7fa17bd5cd0d)


- **Search Service**  
  Filter products by name or category keywords.
![WhatsApp Görsel 2025-05-20 saat 21 50 23_75a9b4ce](https://github.com/user-attachments/assets/dda37929-6ca0-4a90-bcd0-ef911a6491c6)

- **Payment Module Stub**  
  Simulated payment processing interface to illustrate extensibility.
![WhatsApp Görsel 2025-05-20 saat 20 37 20_0fcdc62a](https://github.com/user-attachments/assets/6179b1b3-c8a7-4811-983b-cdd2fb8a3989)

---

## 3. Quick Start

### 3.1 Prerequisites

- **Java Development Kit (JDK) 11+**  
  Ensure `java` and `javac` commands are on your PATH.


## AI Tool Usage Report
ChatGPT was used throughout the project to:
- Generate UML class relationships and responsibilities
- Draft boilerplate Java class templates
- Resolve compilation errors and design issues
- Improve GUI structure and usability
- Document README and project report

## Project Report
**Introduction:**  
The system is designed as an object-oriented simulation of a modern online shopping environment. The main objective is to provide functionalities to customers.

**UML Design:**  
![umlfinal](https://github.com/user-attachments/assets/913bf6f8-582a-4c8d-a0a5-c1e7995b9cac)

**Methodology:**  
Encapsulation and inheritance were heavily used. Classes were modularized by responsibility (e.g., `user`, `model`, `service`). Java Swing was used for the GUI.

**Results:**  
The application simulates browsing, adding/removing products from the cart, placing orders, managing stocks, and customer reviews.
## Authors & Acknowledgments
- **Merve Aydemir** – 2561058 
- **Fitnat Koç** – 2450542 
- **Nuray Taghiyeva** – 2603264
- **Sezin Tarlığ** – 2561520
- **Sinem Sarıca** – 2552347
- Contributions inspired by examples and documentation from:
  - Stack Overflow
  - GeeksForGeeks
  - Oracle Docs



