package webapp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = "/login.servlets")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException,
            ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean is_valid_user = LoginService.validateUser(username, password);
        if (is_valid_user) {

            String role = Database.getUserRole(username);
            if (role.equals("student")) {
                request.setAttribute("email", username);
                request.setAttribute("name", DataService.getUserName(username));
                request.setAttribute("courses", DataService.getCourses(username));
                request.setAttribute("username", username);
                request.getRequestDispatcher("/WEB-INF/views/student-dashboard.jsp").forward(request, response);
            } else if (role.equals("instructor")) {
                request.setAttribute("email", username);
                request.setAttribute("name", DataService.getUserName(username));
                request.setAttribute("courses", DataService.getCourses(username));
                request.setAttribute("username", username);
                request.getRequestDispatcher("/WEB-INF/views/instructor-dashboard.jsp").forward(request, response);

            }
        } else {
            request.setAttribute("errorMessage", "Invalid Credentials !!");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }

    }
}