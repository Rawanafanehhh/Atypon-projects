<html>
<head>
    <title>Login Page</title>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

</head>
<body class="container mt-3">

<h1>Sign in</h1>
<h6>Servlets</h6>

<form action="/login.servlets" method="POST">
    <div class="mb-3 mt-3">
        <label for="username" class="form-label">username :</label>
        <input type="username" class="form-control" id="username" placeholder="Enter username" name="username" required="required">
    </div>
    <div class="mb-3">
        <label for="password" class="form-label">Password :</label>
        <input type="password" class="form-control" id="password" placeholder="Enter password" name="password"
               required="required">
    </div>
    <p><span style="color: red; ">${errorMessage}</span></p>
    <div class="text-center">
        <button type="submit" class="btn btn-primary btn-lg">Submit</button>
    </div>
</form>

<script src="webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>

</body>
</html>