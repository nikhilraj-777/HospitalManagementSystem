\# Hospital Management System



A simple desktop application for managing hospital operations including patient admission, doctor management, room allocation, and billing.



\## Features



✅ \*\*User Authentication\*\* - Admin and Staff login roles

✅ \*\*Doctor Management\*\* - Add and view doctors

✅ \*\*Room Management\*\* - Add rooms, track availability

✅ \*\*Patient Management\*\* - Add and view patients

✅ \*\*Patient Admission\*\* - Admit patients to rooms

✅ \*\*Discharge \& Billing\*\* - Discharge patients and generate bills

✅ \*\*Billing System\*\* - View all bills



\## Technology Stack



\- \*\*Language:\*\* Java

\- \*\*GUI:\*\* Java Swing

\- \*\*Database:\*\* MySQL

\- \*\*JDBC:\*\* Database connectivity

\- \*\*Build Tool:\*\* Maven

\- \*\*IDE:\*\* NetBeans



\## Requirements



\- Java 11 or higher

\- MySQL Server

\- Maven



\## Installation



\### 1. Clone the Repository

```bash

git clone https://github.com/YourUsername/HospitalManagementSystem.git

cd HospitalManagementSystem

```



\### 2. Setup Database



Open MySQL Workbench and run:



```sql

CREATE DATABASE IF NOT EXISTS hospital\\\_db;

USE hospital\\\_db;



CREATE TABLE users (

\&#x20;   id INT AUTO\\\_INCREMENT PRIMARY KEY,

\&#x20;   username VARCHAR(50) NOT NULL UNIQUE,

\&#x20;   password VARCHAR(100) NOT NULL,

\&#x20;   role ENUM('ADMIN', 'STAFF') NOT NULL

);



INSERT INTO users (username, password, role) VALUES

('admin', 'admin123', 'ADMIN'),

('staff', 'staff123', 'STAFF');



CREATE TABLE doctors (

\&#x20;   id INT AUTO\\\_INCREMENT PRIMARY KEY,

\&#x20;   name VARCHAR(100) NOT NULL,

\&#x20;   specialization VARCHAR(100) NOT NULL,

\&#x20;   phone VARCHAR(15) NOT NULL

);



CREATE TABLE patients (

\&#x20;   id INT AUTO\\\_INCREMENT PRIMARY KEY,

\&#x20;   name VARCHAR(100) NOT NULL,

\&#x20;   age INT NOT NULL,

\&#x20;   gender ENUM('MALE', 'FEMALE', 'OTHER') NOT NULL,

\&#x20;   phone VARCHAR(15) NOT NULL

);



CREATE TABLE rooms (

\&#x20;   id INT AUTO\\\_INCREMENT PRIMARY KEY,

\&#x20;   room\\\_number VARCHAR(10) NOT NULL UNIQUE,

\&#x20;   room\\\_type ENUM('General', 'ICU') NOT NULL,

\&#x20;   price\\\_per\\\_day DECIMAL(10, 2) NOT NULL,

\&#x20;   status ENUM('AVAILABLE', 'OCCUPIED') DEFAULT 'AVAILABLE'

);



CREATE TABLE admissions (

\&#x20;   id INT AUTO\\\_INCREMENT PRIMARY KEY,

\&#x20;   patient\\\_id INT NOT NULL,

\&#x20;   room\\\_id INT NOT NULL,

\&#x20;   admission\\\_date DATE NOT NULL,

\&#x20;   discharge\\\_date DATE,

\&#x20;   FOREIGN KEY (patient\\\_id) REFERENCES patients(id),

\&#x20;   FOREIGN KEY (room\\\_id) REFERENCES rooms(id)

);



CREATE TABLE bills (

\&#x20;   id INT AUTO\\\_INCREMENT PRIMARY KEY,

\&#x20;   admission\\\_id INT NOT NULL,

\&#x20;   room\\\_charge DECIMAL(10, 2) NOT NULL,

\&#x20;   doctor\\\_charge DECIMAL(10, 2) NOT NULL,

\&#x20;   total\\\_charge DECIMAL(10, 2) NOT NULL,

\&#x20;   bill\\\_date DATE NOT NULL,

\&#x20;   FOREIGN KEY (admission\\\_id) REFERENCES admissions(id)

);

```



\### 3. Build with Maven



```bash

mvn clean install

```



\### 4. Run the Application



```bash

java -jar target/HospitalManagementSystem.jar

```



\## Login Credentials



| Role | Username | Password |

|------|----------|----------|

| Admin | admin | admin123 |

| Staff | staff | staff123 |



\## Project Structure

src/main/java/com/hospital/

├── main/ # Entry point

├── db/ # Database connection

├── models/ # Data models (User, Patient, Doctor, etc.)

├── dao/ # Data Access Objects

├── interfaces/ # Billable interface

└── ui/ # GUI components (Swing)



\## Author



Nikhil - B.Tech CSE Student, MES College of Engineering, Kuttippuram



\## License



MIT License - See LICENSE file for details



\## Contributing



Feel free to fork this repository and submit pull requests.



\## Support



For issues or questions, please open an issue on GitHub.

