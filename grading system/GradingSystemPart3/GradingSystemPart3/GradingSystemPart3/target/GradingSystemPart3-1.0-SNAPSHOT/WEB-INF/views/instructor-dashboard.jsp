<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Instructor Dashboard</title>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

</head>

<body class="container mt-3">
<h1>Welcome ${name}</h1>
<h5>Instructor Servlets</h5>
<form action="/login.springmvc" method="POST">
<div class="container mt-3">
    <h3>Your courses are :</h3>
    <br>
    <div class="list-group">
        <c:forEach items="${courses}" var="course">
            <a href="/data.servlets?course=${course}&username=${username}"
               class="list-group-item list-group-item-action">${course}</a>
        </c:forEach>
    </div>
</div>

<div class="d-grid">
</br>
        <!-- button to return to the previous page -->
  <a class="btn btn-outline-success btn-lg btn-block" onClick="history.back()">logout</a>
      </div>
      </form>

<script src="webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>


</body>
</html>
