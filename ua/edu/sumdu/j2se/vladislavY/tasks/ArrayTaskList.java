package ua.edu.sumdu.j2se.vladislavY.tasks;

/**
 * Tasks holder as an ArrayList
 *
 * @author Vladisla
 */
public class ArrayTaskList {
    private Task[] list;
    private int counter;

    public ArrayTaskList() {
        this.list = new Task[1];
    }

    /**
     * Adds new task to the end of the tasks list
     * @param task task for adding to the tasks list
     */
    public void add(Task task) {
        if (list.length - 1 == this.size()) {
            Task[] listHelper = list;
            list = new Task[this.size() + 2];
            System.arraycopy(listHelper, 0, list, 0, listHelper.length);
        }
        list[counter++] = task;
    }

    /**
     * Removes task from the list, if list contains several the same tasks - the first one will be removed
     * @param task searched task
     * @return true - if searched task was found and deleted, else false
     */
    public boolean remove(Task task) {
        for (int i = 0; i < this.size(); i++) {
            if (list[i] == task) {
                while (i < this.size() - 1) {
                    list[i] = list[++i];
                }
                list[i] = null;
                counter--;
                return true;
            }
        }
        return false;
    }

    /**
     * Retunns amount of tasks
     * @return number of tasks in the array
     */
    public int size() {
        return counter;
    }

    /**
     * Returns taks by index
     * @param index task index
     * @return task from array by index
     */
    public Task getTask(int index) {
        return list[index];
    }
}
