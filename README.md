# banking-management-system
A comprehensive banking application built with Java Swing and MySQL featuring account management, transactions, and real-time balance tracking with professional GUI design.

# 🏦 Banking Management System

A comprehensive desktop banking application built with Java Swing and MySQL, featuring a modern GUI design and complete banking operations functionality.

## 🚀 Features

### Core Banking Operations
- ✅ **Account Creation** - Create new bank accounts with customer details
- ✅ **Deposit Money** - Secure cash deposit functionality
- ✅ **Withdraw Money** - Cash withdrawal with balance validation
- ✅ **Balance Inquiry** - Real-time balance checking
- ✅ **Transaction History** - Complete transaction records with timestamps

### Technical Features
- 🎨 **Modern GUI Design** - Professional interface with gradient backgrounds
- 🔒 **Data Validation** - Input validation and error handling
- 📊 **MySQL Integration** - Robust database operations with JDBC
- 🎯 **User-Friendly Interface** - Intuitive navigation and responsive design
- 📱 **Account Management** - Complete customer information management

## 🛠️ Technologies Used

- **Frontend:** Java Swing, AWT
- **Backend:** Core Java, JDBC
- **Database:** MySQL 8.0
- **Build Tool:** Maven
- **IDE:** IntelliJ IDEA / Eclipse

## 📋 Prerequisites

Before running this application, make sure you have:

- Java 8 or higher installed
- MySQL Server 8.0+ running
- MySQL Connector/J driver
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

## ⚙️ Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/ojasmehta28/banking-management-system.git
cd banking-management-system

2. Database Setup
sql-- Connect to MySQL and run:
mysql -u root -p < database/schema.sql
mysql -u root -p < database/sample_data.sql
3. Configure Database Connection
Update the database credentials in BankingSystem.java:
javaprivate static final String DB_URL = "jdbc:mysql://localhost:3306/banking_system";
private static final String DB_USER = "your_username";
private static final String DB_PASSWORD = "your_password";
4. Add MySQL Connector
Download and add mysql-connector-java-8.0.33.jar to your classpath.
5. Compile and Run
bashjavac -cp ".:mysql-connector-java-8.0.33.jar" BankingSystem.java
java -cp ".:mysql-connector-java-8.0.33.jar" BankingSystem

