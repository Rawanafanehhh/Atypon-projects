/**
 * The Task class represents a single task in the To-Do List application.
 * It includes details like task name, description, ID, and completion status.
 */
public class Task {
    // Task attributes
    private String name;
    private String description;
    private int id;
    private String isCompleted;

    /**
     * Constructor to initialize a Task object.
     *
     * @param id          Unique identifier for the task.
     * @param name        Name of the task.
     * @param description Description of the task.
     * @param isCompleted Completion status ("Yes" or "No").
     */
    public Task(int id, String name, String description, String isCompleted) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isCompleted = isCompleted;
    }

    // Getters and setters for each field

    /**
     * Gets the ID of the task.
     *
     * @return Task ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the task.
     *
     * @param id Task ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the description of the task.
     *
     * @return Task description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the task.
     *
     * @param description Task description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the completion status of the task.
     *
     * @return "Yes" if completed, "No" otherwise.
     */
    public String isCompleted() {
        return isCompleted;
    }

    /**
     * Sets the completion status of the task.
     *
     * @param completed New completion status ("Yes" or "No").
     */
    public void setCompleted(String completed) {
        isCompleted = completed;
    }

    /**
     * Gets the name of the task.
     *
     * @return Task name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the task.
     *
     * @param name Task name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns a formatted string representation of the task.
     *
     * @return A string with task details.
     */
    @Override
    public String toString() {
        return "Task ID: " + id +
                "\nTask Name: " + name +
                "\nTask Description: " + description +
                "\nIs Task Completed: " + isCompleted +
                "\n--------------------------------------------------------------------";
    }
}