package springmvcapp;

import app.services.DataRetrievingService;
import app.services.LoginService;
import database.Database;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes ({ "studentID" , "name" , "username" , "courses" })
public class LoginController
{
    @RequestMapping (value = "/login.springmvc", method = RequestMethod.GET)
    public String showLoginPage()
    {
        return "login";
    }

    @RequestMapping (value = "/login.springmvc", method = RequestMethod.POST)
    public String handleLoginRequest(ModelMap model , @RequestParam String username , @RequestParam String password)
    {
        boolean is_valid_user = LoginService.validateUser( username , password );

        if(is_valid_user)
        {   String role = Database.getUserRole(username);
            if(role.equals("student")){
            model.put( "username" , username );
            //model.addAttribute("email", email);
            model.put( "name" , DataRetrievingService.getStudentName( username ) );
            model.put( "courses" , DataRetrievingService.getCourses( username ) );
            model.put( "password" , password );

            return "student";
            }else if (role.equals("instructor.jsp")){
                model.put( "username" , username );
                //model.addAttribute("email", email);
                model.put( "name" , DataRetrievingService.getStudentName( username ) );
                model.put( "courses" , DataRetrievingService.getCourses( username ) );
                model.put( "password" , password );

                return "instructor";
            }

        }

        else
        {
            model.put( "errorMessage" , "Invalid Credentials !!" );

            return "login";
        }
        return "login";
    }
}
