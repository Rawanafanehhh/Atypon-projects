package webapp;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataService
{
    private DataService()
    {
    }



    public static String getUserName(String username)
    {
        return Database.getUserName( username );
    }


    public static int getgrade(String username, String course) {
      return  Database.getgrade(username,course);
    }

    public static void updateGrade(String username, String courseName, float newGrade) {
       Database.updateGrade(username,courseName,newGrade);
    }


    public static ArrayList<String> getCoursesAndMarks(String username) {
       return Database.getCoursesAndMarks(username);
    }

    public static List<Map<String, Object>> getStudentGradesForCourse(String courseName) {
       return Database.getStudentGradesForCourse(courseName);
    }
    public static ArrayList<String> getInstructorCourses(String instructorUsername) {
        return Database.getInstructorCourses(instructorUsername);
    }

    public static String getUserRole(String username) {
        return Database.getUserRole(username);
    }

    public static ArrayList<String> getCourses(String username) {
    return Database.getCourses(username);
    }
    public static String getUsernameFullName(String fullName) {
        return Database.getUsernameFullName(fullName);
    }
}
