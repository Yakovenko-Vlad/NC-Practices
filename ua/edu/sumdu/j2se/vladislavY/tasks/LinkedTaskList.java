package ua.edu.sumdu.j2se.vladislavY.tasks;

public class LinkedTaskList extends TaskList{
    private TaskNode head;
    public LinkedTaskList(){
        super();
        head = null;
    }
    class TaskNode {
        public Task task;
        public TaskNode link;
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
    public void add(Task task) {
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
        while(helper.link != null){
            if(helper.task == task) {
                helperLink = helper.link;
                helper = null;
                helper = helperLink;
                return true;
            }
            helperLink = helper;
            helper = helper.link;
        }
        return false;
    }

    /**
     * Returns taks by index
     *
     * @param index task index
     * @return task from array by index
     */
    @Override
    public Task getTask(int index) {
        return null;
    }

    /**
     * Returns an array of tasks performed in the current period
     *
     * @param from start of the period
     * @param to   end of the period
     * @return array of the tasks which will be fulfilled in current period
     */
    public ArrayTaskList incoming(int from, int to) {
        return null;
    }
}
