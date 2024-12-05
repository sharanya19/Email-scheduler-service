# Email Scheduling and Delivery System

## Overview

The goal of this system is to allow users to schedule emails for future delivery, manage those scheduled emails, and track the delivery status in real time. The system is divided into multiple microservices, each responsible for a specific task. The high-level architecture includes:

1. **Email Scheduler Service** (Front-end): A REST API where users can schedule, update, or cancel emails.
2. **Email Delivery Worker** (Back-end processing): Polls the database to find emails that need to be sent and sends them to Kafka.
3. **Email Sender Service** (Back-end processing): Reads emails from Kafka and sends them via an external email service like Amazon SES, updating the database with the delivery status.
4. **Real-Time Status Service** (Front-end notifications): Sends real-time updates to users (via WebSocket) about email delivery success or failure.

---

## High-Level Architecture

```plaintext
+------------------+         +-------------------+         +-------------------+         +------------------+
|                  |         |                   |         |                   |         |                  |
| Email Scheduler  +-------> | Email Delivery    +-------> | Email Sender      +-------> | Real-Time Status |
| Service          |         | Worker            |         | Service           |         | Service          |
|                  |         |                   |         |                   |         |                  |
+------------------+         +-------------------+         +-------------------+         +------------------+


# Email Scheduling and Delivery System

## Overview

This system allows users to schedule emails for future delivery, manage them, and track delivery status in real time. It is composed of multiple microservices:

1. **Email Scheduler Service** - REST API for scheduling and managing emails.
2. **Email Delivery Worker** - Polls the database for emails to send and forwards them to Kafka.
3. **Email Sender Service** - Consumes emails from Kafka, sends them via an email provider (e.g., Amazon SES), and updates the database.
4. **Real-Time Status Service** - Sends real-time delivery status notifications via WebSocket.

---

## Microservices Breakdown

### 1. Email Scheduler Service
- **Purpose:** Allows users to schedule emails via REST API.
- **Key Features:**
  - Store email details in PostgreSQL (recipients, subject, body, delivery time, status).
  - API Endpoints:
    - `POST /schedule`: Schedule a new email.
    - `GET /emails`: List scheduled emails.
    - `PUT /emails/{id}`: Update scheduled email.
    - `DELETE /emails/{id}`: Cancel scheduled email.

### 2. Email Delivery Worker
- **Purpose:** Processes emails that are ready for delivery.
- **Key Features:**
  - Polls the database for emails with a "Pending" status and sends them to Kafka.
  - Uses Kafka for communication between microservices.

### 3. Email Sender Service
- **Purpose:** Sends emails via an external provider (e.g., Amazon SES).
- **Key Features:**
  - Consumes emails from Kafka and sends them.
  - Updates delivery status in the database (Sent/Failed).
  - Implements retry logic if sending fails.

### 4. Real-Time Status Updates
- **Purpose:** Notifies users of email delivery status via WebSocket.
- **Key Features:**
  - WebSocket connection for real-time updates.
  - Notifications on email success or failure.

---

## Setup

### Prerequisites
- Java 21
- Docker and Docker Compose
- PostgreSQL
- Kafka

### Steps
 Clone the repository:
   ```bash
   git clone <repo_url>
   cd <repo_directory>

# Email Scheduling and Delivery System


### 1. Start Services using Docker Compose
To start all services defined in the `docker-compose.yml` file, use the following command:
```bash
docker-compose up -d
