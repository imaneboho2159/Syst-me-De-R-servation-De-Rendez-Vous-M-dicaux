<%@ page import="java.util.List, java.util.ArrayList, com.model.Appointment, com.model.Patient, com.model.Doctor" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Patient Dashboard</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="container mt-5">
<h1>Patient Dashboard</h1>

<%
    Patient patient = (Patient) session.getAttribute("patient");
    if (patient == null) {
        response.sendRedirect("login_patient.jsp");
        return;
    }
%>

<h2>Welcome, <%= patient.getName() %></h2>

<h3>Book an Appointment</h3>
<form action="appointments" method="post">
    <div class="mb-3">
        <label>Doctor ID:</label>
        <input type="number" name="doctorId" class="form-control" required>
    </div>
    <div class="mb-3">
        <label>Date & Time:</label>
        <input type="datetime-local" name="dateTime" class="form-control" required>
    </div>
    <div class="mb-3">
        <label>Reason:</label>
        <textarea name="reason" class="form-control" required></textarea>
    </div>
    <button type="submit" class="btn btn-success">Book Appointment</button>
</form>

<h3>Your Appointments</h3>
<table class="table table-bordered">
    <tr>
        <th>Date</th>
        <th>Doctor</th>
        <th>Reason</th>
        <th>Status</th>
        <th>Action</th>
    </tr>
    <%
        List<Appointment> appointmentList = (List<Appointment>) session.getAttribute("appointments");
        if (appointmentList == null) {
            appointmentList = new ArrayList<>();
        }

        if (!appointmentList.isEmpty()) {
            for (Appointment appointment : appointmentList) {
    %>
    <tr>
        <td><%= appointment.getDateTime() %></td>
        <td>Dr. <%= appointment.getDoctorId() %></td> <!-- You need to get doctor's name separately -->
        <td><%= appointment.getReason() %></td>
        <td><%= appointment.getStatus() %></td>
        <td>
            <form action="cancelAppointment" method="post">
                <input type="hidden" name="appointmentId" value="<%= appointment.getId() %>">
                <button type="submit" class="btn btn-danger">Cancel</button>
            </form>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="5" class="text-center">No Appointments Found</td>
    </tr>
    <% } %>
</table>

<a href="logoutServlet" class="btn btn-secondary">Logout</a>
</body>
</html>
