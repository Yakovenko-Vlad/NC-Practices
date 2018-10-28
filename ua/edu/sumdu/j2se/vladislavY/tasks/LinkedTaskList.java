package ua.edu.sumdu.j2se.vladislavY.tasks;

/**
 * Tasks holder as a LinkedList
 *
 * @author Vladislav
 */
public class LinkedTaskList extends TaskList {
    private TaskNode head;

    private class TaskNode {
        private Task task;
        private TaskNode link;

        TaskNode(Task task, TaskNode link) {
            this.task = task;
            this.link = link;
        }
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
        head = new TaskNode(task, head);
        counter++;
    }

    /**
     * Removes task from the list, if list contains several the same tasks - the first one will be removed
     *
     * @param task searched task
     * @return true - if searched task was found and deleted, else false
     */
    @Override
    public boolean remove(Task task) {
        TaskNode helper = head;
        TaskNode helperLink = head;
        for (; helper != null; helperLink = helper, helper = helper.link) {
            if (helper.task == task) {
                helperLink.link = helper.link;
                if (helper == head)
                    head = helperLink.link;
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
        TaskNode helper = head;
        for (int i = 0; helper != null; i++, helper = helper.link) {
            if (i == index) {
                return helper.task;
            }
        }
        return null;
    }

    /**
     * Returns an array of tasks performed in the current period
     *
     * @param from start of the period
     * @param to   end of the period
     * @return array of the tasks which will be fulfilled in current period
     */
    public LinkedTaskList incoming(int from, int to) throws Exception {
        LinkedTaskList incomingList = new LinkedTaskList();
        TaskNode helper = head;
        while (helper != null) {
            if (helper.task.nextTimeAfter(from) <= to && helper.task.nextTimeAfter(from) != -1) {
                incomingList.add(helper.task);
            }
            helper = helper.link;
        }
        return incomingList;
    }
}
