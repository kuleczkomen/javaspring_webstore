# Webstore Backend API

This is a backend written in Java for a webstore. The app handles users, products, and orders, providing a scalable and high-performance foundation for an e-commerce platform.

## 🚀 Tech Stack

| Category | Technology |
| --- | --- |
| **Core Framework** | Spring Boot

|
| **Security** | Spring Security

|
| **ORM** | Hibernate

|
| **Primary Database** | PostgreSQL

|
| **Caching Layer** | Redis |
| **Containerization** | Docker |
| **Orchestration** | Kubernetes (GKE) |
| **Cloud Provider** | Google Cloud Platform (GCP) |

## ✨ Core Features

* **Domain Management**: The app handles users, products, and orders.


* **Authentication & Authorization**:
* Secured by password hashing (bcrypt) and jwt.


* Role-based access control is enforced, ensuring some methods are for admin only.




* **Data Persistence**: Data is stored in a PostgreSQL database.


* **Advanced Caching (Redis)**:
* Implements caching for high-traffic endpoints to minimize database load.


* The full product list and individual product lookups are cached.


* Cache integrity is maintained automatically; entries are evicted and refreshed whenever a product is added, updated, or deleted.





## ☁️ Infrastructure & Cloud Deployment

This application is built with modern DevOps and cloud-native practices in mind:

* **Docker**: The application is containerized, packaging the Java runtime, dependencies, and compiled application into a single, portable artifact.
* **Google Cloud Platform (GCP)**: Deployed in the cloud using managed GCP services, including external LoadBalancers for secure traffic routing and internal communication networks.
* **Kubernetes (GKE)**: Orchestrated via Google Kubernetes Engine. Declarative YAML manifests govern deployment rollouts, replica scaling, environment variable injection, and internal service discovery.
* **Artifact Registry**: Application container images are pushed to and pulled directly from Google Artifact Registry by the Kubernetes cluster.