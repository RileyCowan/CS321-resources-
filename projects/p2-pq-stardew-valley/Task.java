/**
 * Represents one task in the Stardew Valley simulation.
 *
 * @author Riley Cowan
 */
public class Task implements TaskInterface, Comparable<Task> {
    private int priority;
    private final TaskType taskType;
    private final int hourCreated;
    private final String taskDescription;
    private int waitingTime;

    /**
     * Creates a task with priority zero.
     *
     * @param hourCreated hour when the task was created
     * @param taskType type of task
     * @param taskDescription description of the task
     */
    public Task(int hourCreated, TaskType taskType, String taskDescription) {
        this(hourCreated, taskType, taskDescription, 0);
    }

    /**
     * Creates a task with the specified priority.
     *
     * @param priority task priority
     * @param hourCreated hour when the task was created
     * @param taskType type of task
     * @param taskDescription description of the task
     */
    public Task(int priority, int hourCreated, TaskType taskType, String taskDescription) {
        this(hourCreated, taskType, taskDescription, priority);
    }

    private Task(int hourCreated, TaskType taskType, String taskDescription, int priority) {
        if (taskType == null) {
            throw new IllegalArgumentException("Task type cannot be null.");
        }
        this.priority = priority;
        this.taskType = taskType;
        this.hourCreated = hourCreated;
        this.taskDescription = taskDescription;
        this.waitingTime = 0;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public TaskType getTaskType() {
        return taskType;
    }

    @Override
    public String getTaskDescription() {
        return taskDescription;
    }

    /**
     * Returns the hour at which this task was created.
     *
     * @return creation hour
     */
    public int getHourCreated() {
        return hourCreated;
    }

    @Override
    public void incrementWaitingTime() {
        waitingTime++;
    }

    @Override
    public void resetWaitingTime() {
        waitingTime = 0;
    }

    @Override
    public int getWaitingTime() {
        return waitingTime;
    }

    /**
     * Compares tasks by priority, then by creation hour. A higher priority
     * is larger, and for equal priorities an earlier task is larger.
     *
     * @param other task to compare with
     * @return positive if this task is larger
     */
    @Override
    public int compareTo(Task other) {
        if (other == null) {
            throw new NullPointerException();
        }
        int priorityComparison = Integer.compare(this.priority, other.priority);
        if (priorityComparison != 0) {
            return priorityComparison;
        }
        return Integer.compare(other.hourCreated, this.hourCreated);
    }

    @Override
    public String toString() {
        return taskType.toString() + " " + taskDescription + " at Hour: " + hourCreated + ":00";
    }
}
