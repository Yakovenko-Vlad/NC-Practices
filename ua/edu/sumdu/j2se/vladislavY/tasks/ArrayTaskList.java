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

    public void add(Task task) {
        if (list.length - 1 == counter) {
            Task[] listHelper = list;
            list = new Task[counter + 2];
            System.arraycopy(listHelper, 0, list, 0, listHelper.length);
        }
        list[counter++] = task;
    }

    public boolean remove(Task task) {
        for (int i = 0; i < list.length; i++) {
            if (list[i] == task) {
                while (i < list.length - 1) {
                    list[i] = list[++i];
                }
                list[i] = null;
                counter--;
                return true;
            }
        }
        return false;
    }

    public int size() {
        return counter;
    }

    public Task getTask(int index) {
        return list[index];
    }
}
