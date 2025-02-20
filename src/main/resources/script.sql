CREATE DATABASE DoctorRV;

USE DoctorRV;

CREATE TABLE doctor (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        email VARCHAR(100) UNIQUE NOT NULL,
                        phone VARCHAR(15),
                        password VARCHAR(255) NOT NULL
);

CREATE TABLE patient (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         email VARCHAR(100) UNIQUE NOT NULL,
                         phone VARCHAR(15),
                         specialization VARCHAR(100),
                         password VARCHAR(255) NOT NULL

);