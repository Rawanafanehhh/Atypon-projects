
package webapp;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/data.servlets")
public class DataServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String course = request.getParameter("course");
        String username = request.getParameter("username");
        String role = DataService.getUserRole(username);
        if (role.equals("student")) {
            request.setAttribute("course", course);
            request.setAttribute("username", username);
            ArrayList<String> coursesAndMarks = DataService.getCoursesAndMarks(username);
            String foundGrade = "N/A";
            for (String courseInfo : coursesAndMarks) {
                String[] parts = courseInfo.split(" ");
                if (parts.length == 2) {
                    String courseName = parts[0];
                    String grade = parts[1];
                    if (courseName.equals(course)) {
                        foundGrade = grade;
                        break;
                    }
                }
            }
            request.setAttribute("student_mark", foundGrade);
            request.getRequestDispatcher("/WEB-INF/views/student-grades.jsp").forward(request, response);


        } // ...
        else if (role.equals("instructor")) {

            List<Map<String, Object>> studentGrades = DataService.getStudentGradesForCourse(course);


            for (Map<String, Object> studentGrade : studentGrades) {
                studentGrade.put("newGrade", studentGrade.get("grade")); }

            request.setAttribute("course", course);
            request.setAttribute("students", studentGrades);
            request.getRequestDispatcher("/WEB-INF/views/instructor-grades.jsp").forward(request, response);
        }
// ...


    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String course = request.getParameter("course");
        String fullName = request.getParameter("fullName");

        float newGrade = Float.parseFloat(request.getParameter("newGrade"));
        String username =DataService.getUsernameFullName(fullName);
        System.out.println(username);
        DataService.updateGrade(username, course, newGrade);

        boolean updateSuccessful = DataService.getgrade(username, course) == newGrade;

        List<Map<String, Object>> studentGrades = DataService.getStudentGradesForCourse(course);

        for (Map<String, Object> studentGrade : studentGrades) {
            String studentUsername = (String) studentGrade.get("username");
            if (username.equals(studentUsername)) {
                studentGrade.put("grade", newGrade);
                studentGrade.put("newGrade", newGrade);
            }
        }

        request.setAttribute("course", course);
        request.setAttribute("students", studentGrades);
        request.setAttribute("username",username);
        request.setAttribute("fullName",fullName);

        request.setAttribute("newGrade", newGrade);

        if (updateSuccessful) {
            request.setAttribute("updateSuccess", true);
        }

        request.getRequestDispatcher("/WEB-INF/views/instructor-grades.jsp").forward(request, response);
    }



}