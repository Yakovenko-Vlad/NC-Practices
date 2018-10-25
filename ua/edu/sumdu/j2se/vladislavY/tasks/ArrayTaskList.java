package ua.edu.sumdu.j2se.vladislavY.tasks;

/**
 * Tasks holder as an ArrayList
 *
 * @author Vladislav
 */
public class ArrayTaskList extends TaskList {
    protected Task[] list;

    public ArrayTaskList() {
        this.list = new Task[10];
    }

    /**
     * Adds new task to the end of the tasks list
     *
     * @param task task for adding to the tasks list
     */
    @Override
    public void add(Task task) throws Exception {
        if (task == null)
            throw new Exception("Task cannot be NULL");
        if (list.length - 1 == this.size()) {
            Task[] listHelper = list;
            list = new Task[(list.length * 3) / 2 + 1];
            System.arraycopy(listHelper, 0, list, 0, listHelper.length);
        }
        list[counter++] = task;
    }

    /**
     * Removes task from the list, if list contains several the same tasks - the first one will be removed
     *
     * @param task searched task
     * @return true - if searched task was found and deleted, else false
     */
    @Override
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
     * Returns task by index
     *
     * @param index task index
     * @return task from array by index
     */
    @Override
    public Task getTask(int index) {
        return list[index];
    }

    /**
     * Returns an array of tasks performed in the current period
     *
     * @param from start of the period
     * @param to   end of the period
     * @return array of the tasks which will be fulfilled in current period
     */
    public ArrayTaskList incoming(int from, int to) throws Exception {
        ArrayTaskList incomingList = new ArrayTaskList();
        // array contains 1 (last) indefinite item so list[last] is null
        /*for (Task task : this.list) {
            try {
                if (task.nextTimeAfter(from) <= to && task.nextTimeAfter(from) != -1) {
                    incomingList.add(task);
                }
            } catch (NullPointerException e) {
                break;
            }
        }*/

        for (int i = 0; i < this.size(); i++) {
            if (list[i].nextTimeAfter(from) <= to && list[i].nextTimeAfter(from) != -1) {
                incomingList.add(list[i]);
            }
        }
        return incomingList;
    }
}
