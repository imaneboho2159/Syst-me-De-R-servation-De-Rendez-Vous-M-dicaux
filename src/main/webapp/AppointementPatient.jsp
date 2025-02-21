<%@ page import="java.util.List, com.model.Appointment, com.model.Patient, com.model.Doctor" %>
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
        List<Appointment> appointments = (List<Appointment>) request.getAttribute("appointments");
        List<Doctor> doctors = (List<Doctor>) request.getAttribute("doctors");

        if (appointments != null) {
            for (Appointment app : appointments) {
                String doctorName = "Unknown";
                if (doctors != null) {
                    for (Doctor doc : doctors) {
                        if (doc.getId() == app.getDoctorId()) {
                            doctorName = doc.getName();
                            break;
                        }
                    }
                }
    %>
    <tr>
        <td><%= app.getDateTime() %></td>
        <td><%= doctorName %></td>
        <td><%= app.getReason() %></td>
        <td><%= app.getStatus() %></td>
        <td>
            <form action="cancelAppointment" method="post">
                <input type="hidden" name="appointmentId" value="<%= app.getId() %>">
                <button type="submit" class="btn btn-danger">Cancel</button>
            </form>
        </td>
    </tr>
    <% } } %>
</table>

<a href="login_patient.jsp" class="btn btn-secondary">Logout</a>
</body>
</html>

