package com.servlet;

import com.dao.patientDao;
import com.model.Patient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/PatientServlet")
public class PatientServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private patientDao patientDao;

    @Override
    public void init() throws ServletException {
        try {
            patientDao = new patientDao();
        } catch (SQLException e) {
            throw new ServletException("Database connection error", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("login".equals(action)) {
            loginPatient(request, response);
        } else if ("register".equals(action)) {
            registerPatient(request, response);
        } else {
            response.sendRedirect("index.jsp");
        }
    }

    private void loginPatient(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Patient patient = patientDao.getPatientByEmail(email);
            if (patient != null && patient.getPassword().equals(password)) {
                HttpSession session = request.getSession();
                session.setAttribute("patient", patient);
                response.sendRedirect("AppointementPatient.jsp");
            } else {
                request.setAttribute("error", "Invalid email or password.");
                request.getRequestDispatcher("login_patient.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }

    private void registerPatient(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");

        Patient patient = new Patient(0, name, email, phone, password);

        try {
            if (patientDao.insertPatient(patient)) {
                response.sendRedirect("login_patient.jsp?success=registered");
            } else {
                request.setAttribute("error", "Registration failed.");
                request.getRequestDispatcher("register_patient.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
}
