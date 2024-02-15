package app.services;

import database.Database;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DataRetrievingService
{
    private DataRetrievingService()
    {
    }

    public static int getMark(String student_email , String course_name)
    {
        return Database.getgrade(student_email , course_name );
    }


    public static String getStudentName(String student_email)
    {
        return Database.getUserName( student_email );
    }

    public static ArrayList<String> getCourses(String student_email)
    {
        return Database.getCourses( student_email );
    }

    public static ArrayList<String> getCoursesAndMarks(String student_email)
    {
        return Database.getCoursesAndMarks( student_email );
    }



}