-- ============================================
-- Database: jpa_web_db
-- ============================================
DROP DATABASE IF EXISTS jpa_web_db;
CREATE DATABASE jpa_web_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE jpa_web_db;

-- ============================================
-- Table: users
-- ============================================
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name NVARCHAR(255),
    phone VARCHAR(20),
    images NVARCHAR(500),
    role_id INT DEFAULT 2
);

-- ============================================
-- Table: categories
-- ============================================
CREATE TABLE categories (
    category_id INT PRIMARY KEY AUTO_INCREMENT,
    category_name NVARCHAR(255) NOT NULL,
    images NVARCHAR(500),
    status INT DEFAULT 1
);

-- ============================================
-- Sample data
-- ============================================
-- Default admin account (username: admin / password: admin123)
INSERT INTO users (username, password, full_name, phone, images, role_id)
VALUES ('admin', 'admin123', 'Quản Trị Viên', '0900000000', NULL, 1);

INSERT INTO categories (category_name, images, status) VALUES
('Điện thoại', NULL, 1),
('Laptop', NULL, 1),
('Phụ kiện', NULL, 1);
