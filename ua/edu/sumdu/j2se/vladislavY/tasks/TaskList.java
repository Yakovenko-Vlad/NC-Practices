package ua.edu.sumdu.j2se.vladislavY.tasks;

public abstract class TaskList {
    protected Task[] list;
    protected int counter;

    protected TaskList() {
        this.list = new Task[1];
    }

    /**
     * Adds new task to the end of the tasks list
     *
     * @param task task for adding to the tasks list
     */
    public abstract void add(Task task);

    /**
     * Removes task from the list, if list contains several the same tasks - the first one will be removed
     *
     * @param task searched task
     * @return true - if searched task was found and deleted, else false
     */
    public abstract boolean remove(Task task);

    /**
     * Returns taks by index
     *
     * @param index task index
     * @return task from array by index
     */
    public abstract Task getTask(int index);


    /**
     * Retunns amount of tasks
     *
     * @return number of tasks in the array
     */
    public int size() {
        return counter;
    }
}

