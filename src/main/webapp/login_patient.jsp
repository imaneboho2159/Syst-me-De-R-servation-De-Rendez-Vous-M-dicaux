<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Patient Login</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="container mt-5">
<h2 class="text-center">Patient Login</h2>

<% if (request.getParameter("error") != null) { %>
<div class="alert alert-danger">Invalid email or password.</div>
<% } %>

<% if (request.getParameter("success") != null && request.getParameter("success").equals("registered")) { %>
<div class="alert alert-success">Registration successful! Please log in.</div>
<% } %>

<form action="PatientServlet" method="post">
    <input type="hidden" name="action" value="login">

    <div class="mb-3">
        <label for="email" class="form-label">Email:</label>
        <input type="email" id="email" name="email" class="form-control" required>
    </div>

    <div class="mb-3">
        <label for="password" class="form-label">Password:</label>
        <input type="password" id="password" name="password" class="form-control" required>
    </div>

    <button type="submit" class="btn btn-success">Login</button>
</form>

<div class="mt-3">
    <p>Don't have an account? <a href="register_patient.jsp">Register here</a></p>
</div>
</body>
</html>