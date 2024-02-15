<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Instructor Grades</title>
</head>
<body>
    <h1>Grades for Course: ${course}</h1>
    <form action="/data.springmvc" method="POST">

    <table>
        <tr>
            <th>Student Name</th>
            <th>Student Username</th>
            <th>Current Grade</th>
            <th>New Grade</th>
        </tr>
       <c:forEach var="student" items="${students}">
           <tr>
               <td>${student.fullName}</td>
               <td>${student.username}</td>
               <td>${student.grade}</td>
               <td>
                   <form method="post" action="data.servlets">
                       <input type="hidden" name="course" value="${course}">
                       <input type="hidden" name="username" value="${student.username}">
                       <input type="hidden" name="fullName" value="${student.fullName}">
                       <input type="number" name="newGrade" min="0.0" max="100" step="0.01"value="${student.newGrade}">
                       <input type="submit" value="Update Grade">
                   </form>
               </td>
           </tr>
       </c:forEach>
    </table>
    <c:if test="${updateSuccess}">
        <p>Grade updated successfully.</p>
    </c:if>
    <script src="webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
    <div class="d-grid">
           <!-- button to return to the previous page -->
     <a class="btn btn-outline-success btn-lg btn-block" onClick="window.history.go(-2)">Back</a>
         </div>
               </form>

</body>

</html>