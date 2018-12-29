package model;

import java.io.Serializable;
import java.util.Iterator;

/**
 * Parent class for ArratTaskList and LinkedTaskList
 *
 * @author vladislav
 */
public abstract class TaskList implements Iterable<Task>, Cloneable, Serializable {

    protected int counter;

    protected TaskList() {
    }

    /**
     * Adds new task to the end of the tasks list
     *
     * @param task task for adding to the tasks list
     */
    public abstract void add(Task task) throws Exception;

    /**
     * Removes task from the list, if list contains several the same tasks - the first one will be removed
     *
     * @param task searched task
     * @return true - if searched task was found and deleted, else false
     */
    public abstract boolean remove(Task task);

    /**
     * Returns task by index
     *
     * @param index task index
     * @return task from array by index
     */
    public abstract Task getTask(int index);


    /**
     * Returns amount of tasks
     *
     * @return number of tasks in the array
     */
    public int size() {
        return counter;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        Iterator<Task> iterator = this.iterator();
        while (iterator.hasNext()){
            hash += iterator.next().hashCode();
        }
        return hash;
    }
}

