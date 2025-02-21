<%@ page import="java.util.List" %>
<%@ page import="com.model.Doctor" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>All Doctors</title>
    <link rel="stylesheet" href="styles.css"> <!-- Add CSS for styling -->
</head>
<body>
<h2>List of All Doctors</h2>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Specialization</th>
        <th>Email</th>
        <th>Phone</th>
    </tr>
    <%
        List<Doctor> doctors = (List<Doctor>) request.getAttribute("doctors");
        if (doctors != null) {
            for (Doctor doc : doctors) {
    %>
    <tr>
        <td><%= doc.getId() %></td>
        <td><%= doc.getName() %></td>
        <td><%= doc.getSpecialization() %></td>
        <td><%= doc.getEmail() %></td>
        <td><%= doc.getPhone() %></td>
    </tr>
    <%
        }
    } else {
    %>
    <tr><td colspan="5">No doctors found.</td></tr>
    <% } %>
</table>
</body>
</html>
