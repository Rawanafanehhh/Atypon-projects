package app.servlets;

import app.services.DataRetrievingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/student-information.servlets")
public class DisplayInformationServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException, ServletException {
        String course = request.getParameter( "course" );
        String username =  request.getParameter( "username" );

        request.setAttribute( "course" , course );
        request.setAttribute( "username" , username );

        /*
         * Students can see their marks in different courses,and select a particular
         * course to see their mark and the class avg/ median/ highest/lowest marks.
         *
         */

        request.setAttribute( "student_mark" , DataRetrievingService.getMark(username,course ) );


        request.getRequestDispatcher( "/WEB-INF/views/student-information.jsp" ).forward( request , response );
    }
}