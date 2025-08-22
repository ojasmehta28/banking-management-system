-- Banking Management System Database Schema
-- Created by: Ojas Mehta

-- Create database
CREATE DATABASE IF NOT EXISTS banking_system;
USE banking_system;

-- Create accounts table
CREATE TABLE accounts (
    id INT PRIMARY KEY AUTO_INCREMENT,
    account_number BIGINT UNIQUE NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15) NOT NULL,
    balance DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status ENUM('ACTIVE', 'INACTIVE', 'SUSPENDED') DEFAULT 'ACTIVE',
    
    INDEX idx_account_number (account_number),
    INDEX idx_email (email),
    INDEX idx_status (status)
);

-- Create transactions table
CREATE TABLE transactions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    account_number BIGINT NOT NULL,
    transaction_type ENUM('DEPOSIT', 'WITHDRAWAL', 'TRANSFER_IN', 'TRANSFER_OUT') NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    description VARCHAR(255),
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    reference_number VARCHAR(50) UNIQUE,
    
    FOREIGN KEY (account_number) REFERENCES accounts(account_number) ON DELETE CASCADE,
    INDEX idx_account_transaction (account_number),
    INDEX idx_transaction_date (transaction_date),
    INDEX idx_transaction_type (transaction_type)
);

-- Create admin users table (for future admin functionality)
CREATE TABLE admin_users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    role ENUM('ADMIN', 'MANAGER', 'CASHIER') DEFAULT 'CASHIER',
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP NULL,
    is_active BOOLEAN DEFAULT TRUE,
    
    INDEX idx_username (username),
    INDEX idx_role (role)
);

-- Insert sample admin user (password: admin123)
INSERT INTO admin_users (username, password_hash, full_name, email, role) 
VALUES ('admin', SHA2('admin123', 256), 'System Administrator', 'admin@bankingsystem.com', 'ADMIN');

-- Create sample accounts for testing
INSERT INTO accounts (account_number, full_name, email, phone, balance) VALUES
(1001234567, 'John Doe', 'john.doe@email.com', '9876543210', 15000.00),
(1001234568, 'Jane Smith', 'jane.smith@email.com', '9876543211', 25000.00),
(1001234569, 'Mike Johnson', 'mike.johnson@email.com', '9876543212', 8500.00);

-- Create sample transactions
INSERT INTO transactions (account_number, transaction_type, amount, description) VALUES
(1001234567, 'DEPOSIT', 5000.00, 'Initial deposit'),
(1001234567, 'DEPOSIT', 10000.00, 'Salary credit'),
(1001234567, 'WITHDRAWAL', 2000.00, 'ATM withdrawal'),
(1001234568, 'DEPOSIT', 25000.00, 'Initial deposit'),
(1001234568, 'WITHDRAWAL', 3000.00, 'Online purchase'),
(1001234569, 'DEPOSIT', 8500.00, 'Initial deposit');

-- Views for better data access
-- Account summary view
CREATE VIEW account_summary AS
SELECT 
    a.account_number,
    a.full_name,
    a.email,
    a.phone,
    a.balance,
    a.created_date,
    a.status,
    COUNT(t.id) as total_transactions,
    MAX(t.transaction_date) as last_transaction_date
FROM accounts a
LEFT JOIN transactions t ON a.account_number = t.account_number
GROUP BY a.account_number;

-- Monthly transaction summary view
CREATE VIEW monthly_transaction_summary AS
SELECT 
    account_number,
    YEAR(transaction_date) as year,
    MONTH(transaction_date) as month,
    transaction_type,
    COUNT(*) as transaction_count,
    SUM(amount) as total_amount
FROM transactions
GROUP BY account_number, YEAR(transaction_date), MONTH(transaction_date), transaction_type
ORDER BY year DESC, month DESC;

-- Stored procedures for common operations
DELIMITER //

-- Procedure to transfer money between accounts
CREATE PROCEDURE TransferMoney
