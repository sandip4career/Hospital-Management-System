# Hospital-Management-System

A robust, console-based Hospital Management System built using Java and JDBC to manage healthcare operations and streamline administrative tasks. The system utilizes MySQL for reliable, relational data persistence and incorporates clean Object-Oriented Programming (OOP) design patterns.

## 🚀 Features
* **Patient Management:** Register new patients and view comprehensive patient details dynamically stored in the database.
* **Doctor Availability:** View on-duty doctors, their specific specializations (e.g., Physician, Neurosurgeon), and fetch details via unique IDs.
* **Appointment Booking:** Seamlessly request, validate, and schedule appointments between patients and doctors.
* **Smart Scheduling Conflict Checks:** Built-in real-time verification ensures that appointments cannot be double-booked for the same doctor on the same date.

## 🛠️ Database Schema & Tech Stack
* **Language:** Java (JDBC Core)
* **Database:** MySQL
* **Tables:**
  * `patients`: Tracks `id` (Auto-Increment), `name`, `age`, and `gender`.
  * `doctors`: Stores `id` (Auto-Increment), `name`, and `specialization`.
  * `appointments`: Manages relations with `id`, `patient_id` (Foreign Key), `doctor_id` (Foreign Key), and `appointment_date`.

## 💻 Project Structure
The application cleanly divides concerns across three core module classes and a driver module:
* `Patient.java` – Handles operations like adding and listing patient metrics.
* `Doctor.java` – Evaluates on-call specialist profiles and records.
* `HospitalManagementSystem.java` – The master console runtime layout managing SQL connections, primary menus (`while-true` switch setups), and core booking engines.

## 🛠️ Getting Started

### Prerequisites
* Java Development Kit (JDK)
* MySQL Server
* MySQL Connector/J (JAR driver added to project library dependency path)

### Installation & Setup
1. Clone the repository:
   ```bash
   git clone [https://github.com/sandip4career/Hospital-Management-System.git](https://github.com/sandip4career/Hospital-Management-System.git)
