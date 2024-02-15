import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
public class User extends Application {




    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login Application");

        Label roleLabel = new Label("Are you a student or instructor?");
        TextField roleField = new TextField();

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> onLogin(roleField.getText(), usernameField.getText(), passwordField.getText()));
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(roleLabel, roleField, usernameLabel, usernameField, passwordLabel, passwordField, loginButton);

        Scene scene = new Scene(layout, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void onLogin(String role, String username, String password) {
        try {
            Socket socket = new Socket("localhost", 8000);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(role);
            out.writeUTF(username);
            out.writeUTF(password);
            boolean isUser = in.readBoolean();
            if (isUser) {
                showMainApplicationStage(username, role);
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Login Error");
                alert.setHeaderText(null);
                alert.setContentText("Invalid username or password or the role.");
                alert.showAndWait();
            }
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

        private void showMainApplicationStage(String username, String role) {
            Stage mainStage = new Stage();
            mainStage.setTitle("Main Application");

            Label welcomeLabel = new Label("Welcome!");

            VBox mainLayout = new VBox(10);
            mainLayout.getChildren().add(welcomeLabel);

            if (role.equalsIgnoreCase("student")) {
                Button seeGradeButton = new Button("See Grade for Specific Course");
                seeGradeButton.setOnAction(e -> Courses(username));
                mainLayout.getChildren().addAll(seeGradeButton);
            } else if (role.equalsIgnoreCase("instructor")) {
                Button updateGradeButton = new Button("Courses");
                updateGradeButton.setOnAction(e -> handleUpdateGradeButton(username));
                mainLayout.getChildren().addAll(updateGradeButton);
            }
            Scene mainScene = new Scene(mainLayout, 500, 500);
            mainStage.setScene(mainScene);
            mainStage.show();
        }
    private void Courses(String username) {
        ArrayList<String> coursesAndMarks = Database.getCoursesAndMarks(username);

        Stage courseStage = new Stage();
        courseStage.setTitle("Courses and Grades");

        VBox courseLayout = new VBox(10);

        for (String courseInfo : coursesAndMarks) {
            String[] parts = courseInfo.split(" ");
            if (parts.length == 2) {
                String courseName = parts[0];
                String grade = parts[1];

                Button courseButton = new Button(courseName);
                courseButton.setOnAction(e -> showCourseGradeDetails(courseName, grade));

                courseLayout.getChildren().add(courseButton);
            }
        }

        Scene courseScene = new Scene(courseLayout, 500, 500);
        courseStage.setScene(courseScene);
        courseStage.show();
    }
    private void showCourseGradeDetails(String courseName, String grade) {
        Stage courseDetailStage = new Stage();
        courseDetailStage.setTitle(courseName + " Grade Details");

        Label courseLabel = new Label("Course: " + courseName);
        Label gradeLabel = new Label("Grade: " + grade);

        VBox courseDetailLayout = new VBox(10);
        courseDetailLayout.getChildren().addAll(courseLabel, gradeLabel);

        Scene courseDetailScene = new Scene(courseDetailLayout, 500, 500);
        courseDetailStage.setScene(courseDetailScene);
        courseDetailStage.show();
    }
    private void handleUpdateGradeButton(String instructorUsername) {
        Stage courseStage = new Stage();
        courseStage.setTitle("Select a Course to Update Grades");

        VBox courseLayout = new VBox(10);

        ArrayList<String> instructorCourses = Database.getInstructorCourses(instructorUsername);

        for (String courseName : instructorCourses) {
            Button courseButton = new Button(courseName);
            courseButton.setOnAction(e -> showStudentGradesForCourse(courseName));

            courseLayout.getChildren().add(courseButton);
        }

        Scene courseScene = new Scene(courseLayout, 500, 500);
        courseStage.setScene(courseScene);
        courseStage.show();
    }
    private void showStudentGradesForCourse(String courseName) {
        ArrayList<String> studentGrades = Database.getStudentGradesForCourse(courseName);

        Stage studentGradeStage = new Stage();
        studentGradeStage.setTitle("Student Grades for " + courseName);

        VBox studentGradeLayout = new VBox(10);

        for (String studentGradeInfo : studentGrades) {
            String[] parts = studentGradeInfo.split(" ");
            if (parts.length == 2) {
                String studentName = parts[0];
                String oldGrade = parts[1];

                Label studentLabel = new Label("Student: " + studentName);
                Label gradeLabel = new Label("Grade: " + oldGrade);

                TextField newGradeField = new TextField();
                newGradeField.setPromptText("Enter new grade");

                Button updateGradeButton = new Button("Update Grade");
                updateGradeButton.setOnAction(e -> {
                    String newGradeText = newGradeField.getText();
                    if (!newGradeText.isEmpty()) {
                        float newGrade;
                        try {
                            newGrade = Float.parseFloat(newGradeText);
                            if (newGrade < 0 || newGrade > 100) {
                                throw new NumberFormatException("Grade must be a decimal number between 0 and 100.");
                            }
                            Database.updateGrade(studentName, courseName, newGrade);
                            gradeLabel.setText("Grade: " + newGrade); // Update displayed grade
                        } catch (NumberFormatException ex) {
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Input Error");
                            alert.setHeaderText(null);
                            alert.setContentText(ex.getMessage());
                            alert.showAndWait();
                        }
                    }
                });

                studentGradeLayout.getChildren().addAll(studentLabel, gradeLabel, newGradeField, updateGradeButton, new Separator());
            }
        }

        Scene studentGradeScene = new Scene(studentGradeLayout, 500, 500);
        studentGradeStage.setScene(studentGradeScene);
        studentGradeStage.show();
    }
    }
