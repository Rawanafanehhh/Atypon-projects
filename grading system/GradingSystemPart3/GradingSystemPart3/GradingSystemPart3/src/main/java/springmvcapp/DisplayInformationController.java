package springmvcapp;

import app.services.DataRetrievingService;
import database.Database;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes({"studentID", "name", "email", "courses"})
public class DisplayInformationController {
    @RequestMapping(value = "/student-information.springmvc", method = RequestMethod.GET)
    public String showCourseInformation(ModelMap model, @RequestParam String course) {
        String name = (String) model.get("name");
        String username = Database.getUsernameFullName(name);
        System.out.println("Student ID: " + username);

        model.put("course", course);

        ArrayList<String> coursesAndMarks = DataRetrievingService.getCoursesAndMarks(username);
        String foundGrade = "N/A";
        for (String courseInfo : coursesAndMarks) {
            String[] parts = courseInfo.split(" ");
            if (parts.length == 2) {
                String courseName = parts[0];
                String grade = parts[1];
                if (courseName.equals(course)) {
                    foundGrade = grade;
                    break; // Exit the loop once the course grade is found
                }
            }
        }

        model.put("student_mark", foundGrade);

        return "student-information";
    }

    @RequestMapping(value = "/instructor-information.springmvc", method = RequestMethod.GET)
    public String showInstructorCourseInformation(ModelMap model, @RequestParam String course) {

        List<Map<String, Object>> studentGrades = Database.getStudentGradesForCourse(course);


        for (Map<String, Object> studentGrade : studentGrades) {
            studentGrade.put("newGrade", studentGrade.get("grade")); // Set the initial newGrade value for each student to their current grade
        }

        model.put("course", course);
        model.put("students", studentGrades);

        return "instructor-information";
    }
    @RequestMapping(value = "/instructor-information.springmvc", method = RequestMethod.POST)

    public String postInstructorCourseInformation(ModelMap model, @RequestParam String course, @RequestParam String fullName, @RequestParam Float newGrade) {


        System.out.println(course);
        System.out.println(newGrade);
        System.out.println(fullName);
        String username = Database.getUsernameFullName(fullName);

        // Update the student's grade
        Database.updateGrade(username, course, newGrade);

        // Check if the grade was updated successfully
        boolean updateSuccessful = Database.getgrade(username, course) == newGrade;

        // Retrieve student information and grades for the selected course from the database
        List<Map<String, Object>> studentGrades = Database.getStudentGradesForCourse(course);

        // Update the newGrade attribute for the corresponding student in the studentGrades list
        for (Map<String, Object> studentGrade : studentGrades) {
            String studentUsername = (String) studentGrade.get("username");
            if (username.equals(studentUsername)) {
                studentGrade.put("grade", newGrade); // Update the "grade" attribute
                studentGrade.put("newGrade", newGrade); // Set the updated newGrade value
            }
        }

        model.put("course", course);
        model.put("students", studentGrades);
        model.put("username", username);
        model.put("fullName", fullName);

        // Set the newGrade attribute for display in the JSP
        model.put("newGrade", newGrade);

        if (updateSuccessful) {
            // Set attribute to indicate that the grade was updated successfully
            model.put("updateSuccess", true);
        }

        return "instructor-information";
    }


}