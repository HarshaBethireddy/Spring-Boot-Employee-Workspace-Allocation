# 🌐 Employee Workspace Allocation API

> Efficiently manage employee workspace allocation with ease! 🚀  
> **REST API** to create, allocate, and manage employee workspaces seamlessly. Ideal for organizations wanting streamlined workspace assignment.

---

## 📋 Table of Contents
- [Project Overview](#-project-overview)
- [Tech Stack](#%EF%B8%8F-tech-stack)
- [Core Concepts](#-core-concepts)
- [Setting Up Locally](#-setting-up-locally)
- [Running the Project](#%EF%B8%8F-running-the-project)
- [API Endpoints](#-api-endpoints)
- [Scheduled Tasks](#🕰-scheduled-tasks)
- [Error Handling](#%EF%B8%8F-error-handling)

---

## 🌟 Project Overview
The **Employee Workspace Allocation API** is a Spring Boot-powered REST API that helps organizations manage employee workspace assignments efficiently. Employees are automatically assigned to workspaces as they become available, and queued employees are allocated as soon as a suitable workspace opens up.

### Highlights
- 📌 **Automated Workspace Allocation**: Employees are assigned available workspaces.
- 📌 **Status Tracking**: Check workspace occupancy and employee allocations.
- 📌 **Real-time Updates**: Automatically queues and allocates employees based on workspace availability.

---

## 🛠️ Tech Stack
- **Java 17**
- **Spring Boot** (REST, JPA, Scheduling)
- **Hibernate** (ORM)
- **MySQL** (Database)
- **Lombok** (Simplifies Java code)
- **Jakarta Validation** (Request validation)

---

## 🔑 Core Concepts

### ⚙️ One-to-One Mapping
The API uses a **one-to-one relationship** between `Employee` and `Workspace` entities. Here’s a breakdown:

- **`Employee` ↔️ `Workspace`**: Each employee is associated with one unique workspace, and each workspace is assigned to one employee.
- **Hibernate Annotations**: 
  - `@OneToOne` is used to define the relationship.
  - `@JsonManagedReference` and `@JsonBackReference` annotations manage JSON serialization to avoid infinite recursion.

This approach ensures that each employee can have a designated workspace, improving organization and data clarity.

### 🌐 Endpoint Overview
The API offers various endpoints to manage employees and workspaces efficiently. Refer to [API Endpoints](#api-endpoints) below for details.

---

## 🚀 Setting Up Locally

### Prerequisites
- **Java 17** or higher
- **Maven** 3.6+
- **MySQL** (or another RDBMS)
- IDE like IntelliJ IDEA or Eclipse

### Installation

1. **Clone the Repo**
```bash
   git clone https://github.com/yourusername/EmployeeWorkspaceAllocationAPI.git
   cd EmployeeWorkspaceAllocationAPI
```
2. **Configure the Database**
Open the src/main/resources/application.properties file and update it with your database details:
```code
  spring.datasource.url=jdbc:mysql://localhost:3306/your_database
  spring.datasource.username=your_username
  spring.datasource.password=your_password
  spring.jpa.hibernate.ddl-auto=update
```
3. **Install Dependencies**
```bash
mvn clean install
```

---

## 🏃‍♂️ Running the Project

### Enable Scheduling

1. Ensure the `@EnableScheduling` annotation is added in the main application class to activate automatic allocation of workspaces.
2. Start the application
```bash
  mvn spring-boot:run
```
3. Access the API at `http://localhost:8080`

---

## 📌 API Endpoints

### 👥 Employee Endpoints

![image](https://github.com/user-attachments/assets/a7f0e1ab-33be-4222-9571-b0b275227f35)

### 🏢 Workspace Endpoints

![image](https://github.com/user-attachments/assets/a33eae76-bab0-4f7c-b679-2186e20ecfa7)

---

## 🕰️ Scheduled Tasks

A background task runs every 30 seconds to allocate available workspaces to employees waiting in the queue. This helps automate workspace assignments and maintain real-time status updates.

> The task is managed within the WorkspaceAllocationService, leveraging Spring's scheduling capabilities.

---

### ⚠️ Error Handling

The API handles errors such as:

- **Entity Not Found:** Appropriate error messages for missing employees or workspaces.
- **Validation Failures:** Request validation issues with descriptive messages.
- **No Available Workspace:** Employees are queued if no workspaces are available.
  
Each error response includes an HTTP status code and a helpful error message to guide API consumers.

---

> Happy Coding! 🎉 Enjoy managing your workspace allocations!
