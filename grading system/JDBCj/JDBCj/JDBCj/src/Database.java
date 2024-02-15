
import java.sql.*;
import java.util.ArrayList;

public class Database {
    private static Connection connection = null;
    private static ResultSet resultSet = null;
    private static PreparedStatement preparedStatement = null;

    private Database() {
    }

    public static void initializeConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost/grading_system", "root", "Rawan");
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isUser(String username, String password, String role) {
        initializeConnection();
        try {
            String tableName = (role.equals("student")) ? "students" : "instructors";
            String sqlStatement = "SELECT * FROM " + tableName + " WHERE LOWER(username) = LOWER(?) AND password = ?";

            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    public static void updateGrade(String username, String courseName, float newGrade) {
        initializeConnection();
        try {
            String updateQuery = "UPDATE grades SET grade = ? WHERE username = ? AND course_name = ?";
            preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setFloat(1, newGrade);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, courseName);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("No matching row found for the given student and course.");
            } else {
                System.out.println("Grade updated successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception and provide a meaningful error message to the user
            System.err.println("An error occurred while updating the grade.");
        }
    }


    public static ArrayList<String> getCoursesAndMarks(String username) {
        initializeConnection();
        ArrayList<String> courses = new ArrayList<>();

        try {
            String sql_statement =
                    "SELECT c.course_name, g.grade " +
                            "FROM students s " +
                            "JOIN students_courses sc ON s.student_id = sc.student_id " +
                            "JOIN courses c ON sc.course_id = c.course_id " +
                            "LEFT JOIN grades g ON s.student_id = g.student_id AND c.course_id = g.course_id " +
                            "WHERE LOWER(s.username) = LOWER(?)";  // Use the username column here

            preparedStatement = connection.prepareStatement(sql_statement);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String courseName = resultSet.getString("course_name");
                float grade = resultSet.getFloat("grade");
                String courseInfo = courseName + " " + (grade != 0 ? grade : "N/A");
                courses.add(courseInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }

    public static ArrayList<String> getStudentGradesForCourse(String courseName) {
        initializeConnection();
        ArrayList<String> studentGrades = new ArrayList<>();

        try {
            String sql_statement =
                    "SELECT s.username, g.grade " +
                            "FROM students s " +
                            "JOIN students_courses sc ON s.student_id = sc.student_id " +
                            "JOIN courses c ON sc.course_id = c.course_id " +
                            "LEFT JOIN grades g ON s.student_id = g.student_id AND c.course_id = g.course_id " +
                            "WHERE LOWER(c.course_name) = LOWER(?)";

            preparedStatement = connection.prepareStatement(sql_statement);
            preparedStatement.setString(1, courseName);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                float grade = resultSet.getFloat("grade");
                String gradeInfo = username + " " + (grade != 0 ? grade : "N/A");
                studentGrades.add(gradeInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentGrades;
    }
    public static ArrayList<String> getInstructorCourses(String instructorUsername) {
        initializeConnection();
        ArrayList<String> instructorCourses = new ArrayList<>();

        try {
            String sql_statement =
                    "SELECT c.course_name " +
                            "FROM instructors i " +
                            "JOIN instructors_courses ic ON i.instructor_id = ic.instructor_id " +
                            "JOIN courses c ON ic.course_id = c.course_id " +
                            "WHERE LOWER(i.username) = LOWER(?)";

            preparedStatement = connection.prepareStatement(sql_statement);
            preparedStatement.setString(1, instructorUsername);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String courseName = resultSet.getString("course_name");
                instructorCourses.add(courseName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return instructorCourses;
    }


}