
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>DoctorRV - Home</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        body, html {
            height: 100%;
            margin: 0;
        }
        .split-container {
            display: flex;
            height: 100vh;
        }
        .left-half {
            flex: 1;
            background: url('./images/welcompage.jpg') no-repeat center center/cover;
        }
        .right-half {
            flex: 1;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            background-color: #BEDEFE;
        }
    </style>
</head>
<body>
<div class="split-container">

    <div class="left-half"></div>


    <div class="right-half">
        <h1 class="text-center">Welcome to DoctorRV</h1>
        <div class="text-center mt-4">
            <a href="login_doctor.jsp" class="btn btn-primary">Doctor Login</a>
            <a href="login_patient.jsp" class="btn btn-success">Patient Login</a>
        </div>
    </div>
</div>
</body>
</html>