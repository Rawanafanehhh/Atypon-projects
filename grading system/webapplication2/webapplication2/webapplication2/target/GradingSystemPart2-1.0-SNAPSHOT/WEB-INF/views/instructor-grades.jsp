<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Course Grades</title>
</head>
<body>
    <h1>Course: ${course}</h1>

    <table>
        <tr>
            <th>Student Name</th>
            <th>Current Grade</th>
            <th>Update Grade</th>
        </tr>
        <c:forEach items="${students}" var="student">
            <tr>
                <td>${student.fullName}</td>
                <td>${student.grade}</td>
                <td>
                   <form action="${pageContext.request.contextPath}/data.servlets" method="post">
                       <input type="hidden" name="fullName" >
                       <input type="number" name="newGrade" min="0.0" max="100" step="0.01">
                       <button type="submit">Update</button>
                   </form>
                </td>
            </tr>
        </c:forEach>
    </table>

                    <p>Updated Grade for ${fullName}: ${newGrade}</p>

    <div class="d-grid">
        <!-- button to return to the previous page -->
        <a class="btn btn-outline-success btn-lg btn-block" onClick="history.back()">Back</a>
    </div>

    <script src="webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>
