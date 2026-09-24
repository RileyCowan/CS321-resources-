import java.util.Random;

/**
 * Generates tasks and manages the farmer's energy and unlucky events.
 *
 * @author Riley Cowan
 */
public class TaskGenerator implements TaskGeneratorInterface {
    private final double probability;
    private final Random rand;
    private int currentEnergyStorage;

    /**
     * Creates a task generator using a random seed.
     *
     * @param probability probability of generating a task
     */
    public TaskGenerator(double probability) {
        this.probability = probability;
        this.rand = new Random();
        this.currentEnergyStorage = DEFAULT_ENERGY;
    }

    /**
     * Creates a task generator with a specified seed.
     *
     * @param probability probability of generating a task
     * @param seed random seed
     */
    public TaskGenerator(double probability, long seed) {
        this.probability = probability;
        this.rand = new Random(seed);
        this.currentEnergyStorage = DEFAULT_ENERGY;
    }

    /**
     * Creates a task generator with default probability 0.
     */
    public TaskGenerator() {
        this(0.0);
    }

    @Override
    public Task getNewTask(int hourCreated, TaskInterface.TaskType taskType, String taskDescription) {
        return new Task(hourCreated, taskType, taskDescription);
    }

    @Override
    public void decrementEnergyStorage(Task.TaskType taskType) {
        currentEnergyStorage -= taskType.getEnergyPerHour();
    }

    @Override
    public void resetCurrentEnergyStorage() {
        currentEnergyStorage = DEFAULT_ENERGY;
    }

    @Override
    public int getCurrentEnergyStorage() {
        return currentEnergyStorage;
    }

    @Override
    public void setCurrentEnergyStorage(int newEnergyNum) {
        currentEnergyStorage = newEnergyNum;
    }

    @Override
    public boolean generateTask() {
        return rand.nextDouble() < probability;
    }

    @Override
    public int getUnlucky(Task task, double unluckyProbability) {
        if (task == null) {
            return SURVIVED;
        }

        Task.TaskType type = task.getTaskType();

        if (type == Task.TaskType.MINING
                && unluckyProbability < type.getDyingProbability()) {
            currentEnergyStorage = currentEnergyStorage / 4;
            return DEATH;
        }

        if (unluckyProbability < type.getPassingOutProbability()) {
            currentEnergyStorage = currentEnergyStorage / 2;
            return PASSED_OUT;
        }

        return SURVIVED;
    }

    /**
     * Creates the required simulation output for a completed task.
     *
     * @param task task being completed
     * @param taskType type of task
     * @return formatted task information
     */
    @Override
    public String toString(Task task, Task.TaskType taskType) {
        if(taskType == Task.TaskType.MINING) {
            return "     Mining " + task.getTaskDescription() + " at " + currentEnergyStorage + " energy points (Priority:" + task.getPriority() +")";
        }
        if(taskType == Task.TaskType.FISHING) {
            return "     Fishing " + task.getTaskDescription() + " at " + currentEnergyStorage + " energy points (Priority:" + task.getPriority() +")";
        }
        if(taskType == Task.TaskType.FARM_MAINTENANCE) {
            return "     Farm Maintenance " + task.getTaskDescription() + " at " + currentEnergyStorage + " energy points (Priority:" + task.getPriority() +")";
        }
        if(taskType == Task.TaskType.FORAGING) {
            return "     Foraging " + task.getTaskDescription() + " at " + currentEnergyStorage + " energy points (Priority:" + task.getPriority() +")";
        }
        if(taskType == Task.TaskType.FEEDING) {
            return "     Feeding " + task.getTaskDescription() + " at " + currentEnergyStorage + " energy points (Priority:" + task.getPriority() +")";
        }
        if(taskType == Task.TaskType.SOCIALIZING) {
            return "     Socializing " + task.getTaskDescription() + " at " + currentEnergyStorage + " energy points (Priority:" + task.getPriority() +")";
        }
        return "nothing to see here...";
    }
}
