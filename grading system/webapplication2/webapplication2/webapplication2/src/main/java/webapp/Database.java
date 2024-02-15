package webapp;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static boolean isUser(String username, String password) {
        initializeConnection();
        try {
            // Check in the students table
            String sqlStudent = "SELECT * FROM students WHERE LOWER(username) = LOWER(?) AND password = ?";
            preparedStatement = connection.prepareStatement(sqlStudent);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            }

            // Check in the instructors table
            String sqlInstructor = "SELECT * FROM instructors WHERE LOWER(username) = LOWER(?) AND password = ?";
            preparedStatement = connection.prepareStatement(sqlInstructor);
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


    public static int getgrade(String student_name, String course_name) {
        initializeConnection();
        try {
            String sql_statement =
                    "SELECT m.grades FROM grades m " + "WHERE m.username " + "in (SELECT s.username " + "FROM " +
                            "students s WHERE LOWER(s" + ".username) = LOWER(?)) " + "AND " + "m.course_id in " + "(SELECT " + "c" + ".course_id from courses c WHERE LOWER(c.course_name) = LOWER" + "(?)); ";
            preparedStatement = connection.prepareStatement(sql_statement);
            preparedStatement.setString(1, student_name);
            preparedStatement.setString(2, course_name);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("grade");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
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


    public static List<Map<String, Object>> getStudentGradesForCourse(String courseName) {
        initializeConnection();
        List<Map<String, Object>> studentGrades = new ArrayList<>();

        try {
            String sql_statement =
                    "SELECT s.username, s.full_name, g.grade " +
                            "FROM students s " +
                            "JOIN students_courses sc ON s.student_id = sc.student_id " +
                            "JOIN courses c ON sc.course_id = c.course_id " +
                            "LEFT JOIN grades g ON s.student_id = g.student_id AND c.course_id = g.course_id " +
                            "WHERE LOWER(c.course_name) = LOWER(?)";

            preparedStatement = connection.prepareStatement(sql_statement);
            preparedStatement.setString(1, courseName);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String fullName = resultSet.getString("full_name");
                float grade = resultSet.getFloat("grade");

                Map<String, Object> studentGrade = new HashMap<>();
                studentGrade.put("fullName", fullName);
                studentGrade.put("grade", grade);
                studentGrade.put("newGrade", grade); // Add the newGrade attribute with the initial value set to the current grade

                studentGrades.add(studentGrade);
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


    public static String getUserName(String username) {
        initializeConnection();
        try {
            String role = getUserRole(username);

            if ("student".equals(role)) {
                String studentSql = "SELECT full_name FROM students WHERE LOWER(username) = LOWER(?)";
                preparedStatement = connection.prepareStatement(studentSql);
            } else if ("instructor".equals(role)) {
                String instructorSql = "SELECT full_name FROM instructors WHERE LOWER(username) = LOWER(?)";
                preparedStatement = connection.prepareStatement(instructorSql);
            } else {
                // Handle unrecognized role
                return null;
            }

            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("full_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // User not found or error occurred
    }

    public static String getUserRole(String username) {
        initializeConnection();
        try {
            String studentSql = "SELECT 'student' AS role FROM students WHERE LOWER(username) = LOWER(?)";
            String instructorSql = "SELECT 'instructor' AS role FROM instructors WHERE LOWER(username) = LOWER(?)";

            // Check in students table
            preparedStatement = connection.prepareStatement(studentSql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("role");
            }

            // Check in instructors table
            preparedStatement = connection.prepareStatement(instructorSql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("role");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // User not found or error occurred
    }
    public static ArrayList<String> getCourses(String username) {
        initializeConnection();
        ArrayList<String> courses = new ArrayList<>();

        try {
            String role = getUserRole(username);

            if ("student".equals(role)) {
                String studentSql =
                        "SELECT c.course_name " +
                                "FROM students s " +
                                "JOIN students_courses sc ON s.student_id = sc.student_id " +
                                "JOIN courses c ON sc.course_id = c.course_id " +
                                "WHERE LOWER(s.username) = LOWER(?)";

                preparedStatement = connection.prepareStatement(studentSql);
            } else if ("instructor".equals(role)) {
                String instructorSql =
                        "SELECT c.course_name " +
                                "FROM instructors i " +
                                "JOIN instructors_courses ic ON i.instructor_id = ic.instructor_id " +
                                "JOIN courses c ON ic.course_id = c.course_id " +
                                "WHERE LOWER(i.username) = LOWER(?)";

                preparedStatement = connection.prepareStatement(instructorSql);
            }

            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String courseName = resultSet.getString("course_name");
                courses.add(courseName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }
    public static String getUsernameFullName(String fullName) {
        initializeConnection();
        try {
            String sql_statement =
                    "SELECT username " +
                            "FROM students " +
                            "WHERE LOWER(full_name) = LOWER(?)";

            preparedStatement = connection.prepareStatement(sql_statement);
            preparedStatement.setString(1, fullName);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // User not found or error occurred
    }


}