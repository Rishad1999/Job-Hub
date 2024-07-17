# Scalable Job Service Application Using Microservices and Kubernetes

## Project Description
This project is a job service application built using a microservices architecture. It consists of three main services: company, job, and review. Each service performs CRUD operations independently, promoting modularity and ease of maintenance. An API gateway is used to unify the endpoints into a single URL, abstracting the underlying microservices complexity. Users can add companies, post jobs, and submit reviews without needing to know the architecture's intricacies. The application is containerized using Docker and orchestrated with Kubernetes, ensuring scalability, reliability, and efficient resource management. Additional tools like PostgreSQL for the database, pgAdmin for database management, Zipkin for distributed tracing, and RabbitMQ for messaging ensure a robust and scalable backend.

## Key Features
1. **Modular Service Design**: Divides functionalities into three distinct services—company, job, and review.
2. **Seamless User Experience**: Uses an API gateway to unify endpoints, providing a single URL for user interaction.
3. **Robust Infrastructure**: Containerized using Docker and orchestrated with Kubernetes for scalability and reliability.

## Technologies Used
- **Programming Languages**: Java
- - **Framework**: Springboot
- **Microservices**: Custom-built for company, job, and review management
- **Containerization**: Docker
- **Orchestration**: Kubernetes
- **Database**: PostgreSQL
- **Database Management**: pgAdmin
- **Distributed Tracing**: Zipkin
- **Messaging Queue**: RabbitMQ
- **API Gateway**: (Specify the tool, e.g., Kong, NGINX, or another)

## How to Run
1. Clone the repository.
2. Build and run the Docker containers.
3. Deploy the application using Kubernetes.
4. Access the application via the API gateway URL.

## Installation
```bash
git clone <repository-url>
cd <repository-directory>
# Build Docker images
docker-compose build
# Deploy with Kubernetes
kubectl apply -f k8s/
