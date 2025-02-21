package com.servlet;

import com.dao.appointmentDao;
import com.model.Appointment;
import com.model.Patient;
import com.dao.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/appointments")
public class AppointmentServlet extends HttpServlet {
    private appointmentDao appointmentDao;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DBConnection.getConnection();
            appointmentDao = new appointmentDao(connection);
        } catch (SQLException e) {
            throw new RuntimeException("Database connection failed", e);
        }
    }

    // Handles appointment booking
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Patient patient = (Patient) session.getAttribute("patient");

        if (patient == null) {
            response.sendRedirect("login_patient.jsp");
            return;
        }

        try {
            int doctorId = Integer.parseInt(request.getParameter("doctorId"));
            String dateTimeStr = request.getParameter("dateTime");
            String reason = request.getParameter("reason");

            // Convert datetime string to LocalDateTime
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);

            // Create and save appointment
            Appointment appointment = new Appointment(0, patient.getId(), doctorId, dateTime, reason, "Scheduled");
            boolean isBooked = appointmentDao.bookAppointment(appointment);

            if (isBooked) {
                // Fetch updated list and store it in session
                List<Appointment> updatedAppointments = appointmentDao.getAppointmentsForPatient(patient.getId());
                session.setAttribute("appointments", updatedAppointments);
                response.sendRedirect("AppointementPatient.jsp?success=booked");
            } else {
                response.sendRedirect("AppointementPatient.jsp?error=booking_failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("AppointementPatient.jsp?error=invalid_data");
        }
    }

    // Handles listing appointments
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Patient patient = (Patient) session.getAttribute("patient");

        if (patient != null) {
            try {
                List<Appointment> appointments = appointmentDao.getAppointmentsForPatient(patient.getId());
                session.setAttribute("appointments", appointments);
                request.getRequestDispatcher("AppointementPatient.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("AppointementPatient.jsp?error=loading_failed");
            }
        } else {
            response.sendRedirect("index.jsp");
        }
    }
}
