package ua.edu.sumdu.j2se.vladislavY.tasks;

import java.util.Iterator;

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
        Iterator<Task> iterator = iterator();
        while(iterator.hasNext()) {
            Task task = iterator.next();
            if (task.nextTimeAfter(from) <= to && task.nextTimeAfter(from) != -1) {
                incomingList.add(task);
            }
        }
        return incomingList;
    }

    @Override
    public Iterator<Task> iterator() {
        return new Iterator<Task>() {
            private TaskNode helper =  new TaskNode(null, head);
            private TaskNode helperLink = null;

            @Override
            public boolean hasNext() {
                return helper.link != null;
            }

            @Override
            public Task next() {
                if(hasNext()){
                    helperLink = helper.link;
                    helper = helper.link;
                    return helperLink.task;
                }
                return null;
            }

            @Override
            public void remove(){
                if(helperLink != null)
                    LinkedTaskList.this.remove(helper.task);
                else throw new IllegalStateException();
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LinkedTaskList)) return false;
        Iterator<Task> iterator = ((LinkedTaskList) o).iterator();
        Iterator<Task> iterator1 = this.iterator();
        while (iterator.hasNext() && iterator1.hasNext()){
            if(!iterator1.next().equals(iterator.next()))
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String str = "LinkedTaskList contains ";
        Iterator<Task> iterator = this.iterator();
        while (iterator.hasNext()){
            str += iterator.next().toString() + "\n ";
        }
        return str;
    }

    @Override
    protected LinkedTaskList clone() throws CloneNotSupportedException {
        LinkedTaskList arrayTaskList = new LinkedTaskList();
        Iterator<Task> iterator = iterator();
        while (iterator.hasNext()) {
            try {
                arrayTaskList.add(iterator.next());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return arrayTaskList;
    }
}
