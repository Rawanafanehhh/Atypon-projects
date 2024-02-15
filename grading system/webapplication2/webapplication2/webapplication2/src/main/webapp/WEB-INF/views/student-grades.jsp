<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>${course} class information</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


</head>

<body class="container mt-3">
<h1>${course} class information :</h1>
<h5>StudentServlets</h5>
<div class="container mt-3">
    <div class="list-group">
        <h4>Your mark in ${course}
            is ${student_mark}</h4>
    </div>

    <br>

    <div class="d-grid">
        <!-- button to return to the previous page -->
  <a class="btn btn-outline-success btn-lg btn-block" onClick="history.back()">Back</a>
      </div>
</div>

<script src="webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>

</body>
</html>