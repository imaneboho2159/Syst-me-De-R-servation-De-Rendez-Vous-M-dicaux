
package com.dao;

import com.model.Appointment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class appointmentDao {
    private static Connection connection; // Fix: Use instance-level connection

    public appointmentDao(Connection connection) {
        this.connection = connection;
    }
    public appointmentDao() {
    }

    // Retrieve all appointments for a specific patient
    public List<Appointment> getAppointmentsForPatient(int patientId) {
        List<Appointment> appointments = new ArrayList<>();
        String query = "SELECT * FROM appointment WHERE patient_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    appointments.add(new Appointment(
                            rs.getInt("id"),
                            rs.getInt("patient_id"),
                            rs.getInt("doctor_id"),
                            rs.getTimestamp("date_time").toLocalDateTime(),
                            rs.getString("reason"),
                            rs.getString("status")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    // Book a new appointment
    public boolean bookAppointment(Appointment appointment) {
        String query = "INSERT INTO appointment (patient_id, doctor_id, date_time, reason, status) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, appointment.getPatientId());
            ps.setInt(2, appointment.getDoctorId());
            ps.setTimestamp(3, Timestamp.valueOf(appointment.getDateTime()));
            ps.setString(4, appointment.getReason());
            ps.setString(5, appointment.getStatus());

            return ps.executeUpdate() > 0; // Returns true if insertion was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cancelAppointment(int appointmentId) {
        String query = "DELETE FROM appointment WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, appointmentId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // Retrieve all appointments
    public static List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        String query = "SELECT * FROM appointment";

        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                appointments.add(new Appointment(
                        rs.getInt("id"),
                        rs.getInt("patient_id"),
                        rs.getInt("doctor_id"),
                        rs.getTimestamp("date_time").toLocalDateTime(),
                        rs.getString("reason"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider logging instead
        }
        return appointments;
    }

}




