
package com.dao;

import com.model.Patient;
import java.sql.*;

public class patientDao {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/DoctorRV?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "imaboh@2159@";

    private Connection connection;

    // Constructor initializes the connection
    public patientDao() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Ensure the driver is loaded
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found!", e);
        }
    }

    // Insert a new patient
    public boolean insertPatient(Patient patient) throws SQLException {
        if (connection == null) {
            throw new SQLException("Database connection is not initialized.");
        }

        String query = "INSERT INTO patient (name, email, phone, password) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, patient.getName());
            ps.setString(2, patient.getEmail());
            ps.setString(3, patient.getPhone());
            ps.setString(4, patient.getPassword());

            return ps.executeUpdate() > 0; // Returns true if at least one row is inserted
        }
    }

    // Retrieve a patient by email
    public Patient getPatientByEmail(String email) throws SQLException {
        if (connection == null) {
            throw new SQLException("Database connection is not initialized.");
        }

        String query = "SELECT * FROM patient WHERE email = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Patient(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("password")
                    );
                }
            }
        }
        return null;
    }

    // Close the connection when done
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public Patient loginPatient(String email, String password) {
        return null;
    }

    public boolean registerPatient(Patient patient) {


        return false;
    }}
