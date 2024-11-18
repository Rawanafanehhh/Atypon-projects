import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    // Global task list and task ID counter
    public static ArrayList<Task> myTasks = new ArrayList<>();
    public static int id = 0;

    /**
     * Adds a new task to the list.
     * @param input Scanner object for user input.
     */
    public static void addTask(Scanner input) {
        System.out.println("The Task Name: ");
        String name = input.nextLine();

        System.out.println("The Task Description: ");
        String description = input.nextLine();

        System.out.println("Is Completed? 1.Yes 2.No");
        String isCompleted = input.nextLine();

        // Validate the input for completion status
        while (!isCompleted.equals("1") && !isCompleted.equals("2")) {
            System.out.println("Please enter 1 or 2");
            isCompleted = input.nextLine();
        }
        // Convert user input to "Yes" or "No"
        isCompleted = isCompleted.equals("1") ? "Yes" : "No";

        // Add the task to the list with an auto-incrementing ID
        myTasks.add(new Task(id++, name, description, isCompleted));

        System.out.println("\nAdded Successfully!");
        System.out.println("--------------------------------------------------------------------");
    }

    /**
     * Marks a task as completed and optionally deletes it.
     * @param input Scanner object for user input.
     */
    public static void completedTask(Scanner input) {
        if (myTasks.isEmpty()) {
            System.out.println("\nThere are no tasks.");
            System.out.println("--------------------------------------------------------------------");
            return;
        }

        if (myTasks.size() == 1) {
            // Automatically handle the only task
            myTasks.get(0).setCompleted("Yes");
        } else {
            printTasks();
            int index = getValidTaskId(input);
            myTasks.get(index).setCompleted("Yes");
        }

        System.out.println("\nMarked as Completed!");
        System.out.println("Do you want to delete it? Y/N");
        String deleteAnswer = input.nextLine().toLowerCase();

        // Validate delete confirmation input
        while (!deleteAnswer.equals("y") && !deleteAnswer.equals("n")) {
            System.out.println("Please enter y or n:");
            deleteAnswer = input.nextLine().toLowerCase();
        }

        if (deleteAnswer.equals("y")) {
            deleteTask(0); // Delete the first task
        }

        System.out.println("--------------------------------------------------------------------");
    }

    /**
     * Displays details of a specific task.
     * @param index The index of the task to view.
     */
    public static void viewingTask(int index) {
        System.out.println(myTasks.get(index).toString());
    }

    /**
     * Deletes a task from the list.
     * @param index The index of the task to delete.
     */
    public static void deleteTask(int index) {
        myTasks.remove(index);
        System.out.println("Deleted Successfully!");
        System.out.println("--------------------------------------------------------------------");
    }

    /**
     * Prints all tasks with their indices.
     */
    public static void printTasks() {
        System.out.println("Choose:");
        for (int index = 0; index < myTasks.size(); index++) {
            System.out.println(index + " - " + myTasks.get(index).getName());
        }
    }

    /**
     * Checks if the task list is empty and displays a message if true.
     * @return True if the task list is empty, false otherwise.
     */
    public static boolean isTaskListEmpty() {
        if (myTasks.isEmpty()) {
            System.out.println("\nThere are no tasks.");
            System.out.println("--------------------------------------------------------------------");
            return true;
        }
        return false;
    }

    /**
     * Gets a valid task ID from the user.
     * @param input Scanner object for user input.
     * @return The valid index chosen by the user.
     */
    public static int getValidTaskId(Scanner input) {
        int index = -1; // Default invalid value
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Enter the number: ");
            try {
                index = Integer.parseInt(input.nextLine());
                if (index >= 0 && index < myTasks.size()) {
                    validInput = true; // Valid ID entered
                } else {
                    System.out.println("Please enter a valid number based on the list.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        return index;
    }

    /**
     * The main method to run the application.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        System.out.println("--- To-Do List Menu ---");
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("Choose from these options:\n" +
                    "1.Add Task\n" +
                    "2.Mark a Task as Completed\n" +
                    "3.View Task\n" +
                    "4.View All Tasks\n" +
                    "5.Delete Task\n" +
                    "6.Exit\n");

            String answer = input.nextLine();

            switch (answer) {
                case "1":
                    addTask(input);
                    break;
                case "2":
                    completedTask(input);
                    break;
                case "3":
                    if (!isTaskListEmpty()) {
                        printTasks();
                        int index = getValidTaskId(input);
                        viewingTask(index);
                    }
                    break;
                case "4":
                    if (!isTaskListEmpty()) {
                        for (int index = 0; index < myTasks.size(); index++) {
                            viewingTask(index);
                        }
                    }
                    break;
                case "5":
                    if (!isTaskListEmpty()) {
                        printTasks();
                        int index = getValidTaskId(input);
                        deleteTask(index);
                    }
                    break;
                case "6":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Please enter a valid number.");
            }
        }
    }
}