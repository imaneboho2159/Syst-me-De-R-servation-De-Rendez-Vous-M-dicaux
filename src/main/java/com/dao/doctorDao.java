package com.dao;

import com.model.Doctor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class doctorDao {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/DoctorRV?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "imaboh@2159@";

    private Connection connection;
    // Constructor initializes the connection
    public doctorDao() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Ensure the driver is loaded
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found!", e);
        }
    }

    // Insert a new doctor
    public boolean insertDoctor(Doctor doctor) throws SQLException {
        if (connection == null) {
            throw new SQLException("Database connection is not initialized.");
        }

        String query = "INSERT INTO doctor (name, email, phone, specialization, password) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, doctor.getName());
            ps.setString(2, doctor.getEmail());
            ps.setString(3, doctor.getPhone());
            ps.setString(4, doctor.getSpecialization());
            ps.setString(5, doctor.getPassword());

            return ps.executeUpdate() > 0; // Returns true if insertion is successful
        }
    }

    // Retrieve a doctor by email
    public Doctor getDoctorByEmail(String email) throws SQLException {
        if (connection == null) {
            throw new SQLException("Database connection is not initialized.");
        }

        String query = "SELECT * FROM doctor WHERE email = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Doctor(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("specialization"),
                            rs.getString("password")
                    );
                }
            }
        }
        return null; // No doctor found
    }

    // Close the connection when done
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        String query = "SELECT * FROM doctor";

        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Doctor doctor = new Doctor(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("specialization"),
                        rs.getString("email"),
                        rs.getString("phone")
                );
                doctors.add(doctor);
                return  doctors;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider logging this
        }

        return doctors;
    }


}
