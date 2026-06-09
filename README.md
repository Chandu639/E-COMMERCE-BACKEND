# 🛒 E-Commerce Backend System with AI Recommendations

A production-grade Java Spring Boot backend for an e-commerce platform, designed using real-world backend engineering principles and enhanced with AI-powered product recommendations.

This project focuses on correctness, transactional safety, security, clean architecture, and modern AI integration using Vector Databases and Retrieval-Augmented Generation (RAG).

---

# 🚀 Tech Stack

### Backend

* Java 21
* Spring Boot
* Spring Data JPA (Hibernate)
* Spring Security + JWT
* Maven

### Databases

* MySQL
* PostgreSQL
* PgVector

### AI Stack

* Spring AI
* Ollama
* nomic-embed-text Embedding Model
* Groq API
* Llama 3.3 70B

### Testing & Utilities

* JUnit 5
* Mockito
* SLF4J Logging

---

# 📦 Core Modules

## 🔐 Authentication & Authorization

* JWT-based authentication
* Role-based access (USER, ADMIN)
* Secure user identification using Spring Security Context
* No user IDs accepted from client
* Security-first design

---

## 🛍 Product Module

* Admin-only product creation, update, delete
* Product listing with:

  * Pagination
  * Sorting
  * Filtering (category, price range)
* DTO-based architecture
* Automatic vectorization support

---

## 🛒 Cart Module

* Add / update / remove cart items
* Quantity validation
* Snapshot pricing
* Idempotent operations
* Business-level logging

---

## 📦 Order Module

* Transactional order placement
* Cart → Order conversion
* Order lifecycle management
* Order history pagination
* Optimized fetching using JOIN FETCH
* Clear transaction boundaries

---

# 🤖 AI Recommendation Engine

Built using Spring AI + Ollama + PgVector.

### Features

* Product embeddings generation
* Vector storage using PgVector
* Semantic product search
* Retrieval-Augmented Generation (RAG)
* AI-powered product recommendations
* Automatic product re-vectorization
* Vector cleanup on product deletion

### Recommendation Flow

User Query

↓

Embedding Generation

↓

PgVector Similarity Search

↓

Top Matching Products

↓

LLM Context Construction

↓

AI Recommendation Response

### Example Queries

* Best laptop for students
* Budget gaming laptop
* Laptop for software development
* Business laptop under 70000

---

# 🔁 Transactions

Strong focus on transactional correctness.

* Proper use of @Transactional
* Atomic business operations
* Read-only transactions where appropriate
* Clear rollback boundaries
* Consistency-first design

---

# ⚠️ Exception Handling

Production-style exception management.

* Centralized @ControllerAdvice
* Domain-specific exceptions
* Clean HTTP responses
* No internal implementation leakage

---

# 📊 Logging

SLF4J-based logging.

Meaningful logs for:

* Product operations
* Cart updates
* Order placement
* Vectorization events
* Recommendation workflow
* Failure scenarios

---

# 🧪 Testing Strategy

Tests are written for confidence, not coverage inflation.

### Included Tests

* Service Layer Tests (Mockito)
* Repository Tests (JPA + H2)

### Philosophy

* Focus on business correctness
* Avoid redundant tests
* Interview-ready testing approach

---

# 🧠 Design Philosophy

* Security > Convenience
* Correctness > Speed
* Explicit over Implicit
* Real-world backend thinking
* Clean separation of concerns
* AI integrated as a business capability, not a gimmick

---

# 📌 What This Project Demonstrates

### Backend Engineering

* Spring Boot Architecture
* REST API Design
* Transaction Management
* Security Best Practices
* Exception Handling
* Clean Code Principles

### AI Engineering

* Spring AI
* Vector Databases
* Embeddings
* Semantic Search
* Retrieval-Augmented Generation (RAG)
* LLM Integration

---

# 🔮 Future Enhancements

### Backend

* Payment Gateway Integration
* Inventory Management
* Email Notifications
* Caching with Redis

### AI

* Personalized Recommendations
* Hybrid Search (SQL + Vector Search)
* Conversational Shopping Assistant
* Product Review Analysis

### Architecture

* Microservices Migration
* API Gateway
* Service Discovery
* Event-Driven Communication (Kafka)

---

# 📬 Author

**Venkata Chandra Krishna**

Java Backend Developer | Spring Boot | AI Applications | Distributed Systems
