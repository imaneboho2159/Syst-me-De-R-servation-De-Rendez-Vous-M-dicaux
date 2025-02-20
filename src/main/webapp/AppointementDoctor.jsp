<%@ page import="java.util.List" %>
<%@ page import="com.model.Appointment, com.model.Doctor" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Doctor Dashboard</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="container mt-5">
<h1>Doctor Dashboard</h1>

<%
    Doctor doctor = (Doctor) session.getAttribute("doctor");
    if (doctor == null) {
        response.sendRedirect("login_doctor.jsp");
        return; // Stop further execution
    }
%>

<h2>Welcome, Dr. <%= doctor.getName() %></h2>

<h3>Your Appointments</h3>

<table class="table table-bordered">
    <tr>
        <th>Date</th>
        <th>Patient ID</th>
        <th>Reason</th>
        <th>Status</th>
        <th>Action</th>
    </tr>
    <%
        List<Appointment> appointments = (List<Appointment>) request.getAttribute("appointments");
        if (appointments != null && !appointments.isEmpty()) {
            for (Appointment app : appointments) {
    %>
    <tr>
        <td><%= app.getDateTime() %></td>
        <td><%= app.getPatientId() %></td>
        <td><%= app.getReason() %></td>
        <td><%= app.getStatus() %></td>
        <td>
            <form action="cancelAppointment" method="post">
                <input type="hidden" name="appointmentId" value="<%= app.getId() %>">
                <button type="submit" class="btn btn-danger">Cancel</button>
            </form>
        </td>
    </tr>
    <% } } else { %>
    <tr>
        <td colspan="5" class="text-center">No Appointments Found</td>
    </tr>
    <% } %>
</table>

<a href="logout" class="btn btn-secondary">Logout</a>
</body>
</html>



