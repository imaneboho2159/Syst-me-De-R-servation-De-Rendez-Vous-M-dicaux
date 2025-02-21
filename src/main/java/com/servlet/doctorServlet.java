package com.servlet;

import com.dao.doctorDao;
import com.model.Appointment;
import com.dao.appointmentDao;
import com.model.Doctor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/DoctorServlet")
public class doctorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private doctorDao doctorDao;

    @Override
    public void init() throws ServletException {
        try {
            doctorDao = new doctorDao();
        } catch (SQLException e) {
            throw new ServletException("Database connection error", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("login".equals(action)) {
            loginDoctor(request, response);
        } else if ("register".equals(action)) {
            registerDoctor(request, response);
        } else {
            response.sendRedirect("index.jsp");
        }
    }

    // Doctor login method
    private void loginDoctor(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Doctor doctor = doctorDao.getDoctorByEmail(email);
            if (doctor != null && doctor.getPassword().equals(password)) {
                HttpSession session = request.getSession();
                session.setAttribute("doctor", doctor);
                response.sendRedirect("AppointementDoctor.jsp");
            } else {
                request.setAttribute("error", "Invalid email or password.");
                request.getRequestDispatcher("login_doctor.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }

    // Doctor registration method
    private void registerDoctor(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String specialization = request.getParameter("specialization");
        String password = request.getParameter("password");

        Doctor doctor = new Doctor(0, name, email, phone, specialization, password);

        try {
            if (doctorDao.insertDoctor(doctor)) {
                response.sendRedirect("login_doctor.jsp?success=registered");
            } else {
                request.setAttribute("error", "Registration failed.");
                request.getRequestDispatcher("register_doctor.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }

    // Fetch all doctors and display them
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Doctor doctor = (Doctor) session.getAttribute("doctor");

        if (doctor == null) {
            response.sendRedirect("login_doctor.jsp");
            return;
        }

        List<Appointment> appointments = appointmentDao.getAllAppointments();
        request.setAttribute("appointments", appointments);
        request.getRequestDispatcher("AppointementDoctor.jsp").forward(request, response);
    }

}
