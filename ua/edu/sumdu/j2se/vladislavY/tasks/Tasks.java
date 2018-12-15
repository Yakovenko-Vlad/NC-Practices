package ua.edu.sumdu.j2se.vladislavY.tasks;

import java.util.Date;
import java.util.Iterator;

public class Tasks {

    /**
     * Returns an array of tasks performed in the current period
     *
     * @param from start of the period
     * @param to   end of the period
     * @return array of the tasks which will be fulfilled in current period
     */
    public static Iterable<Task> incoming(Iterable<Task> tasks, Date from, Date to) throws Exception {
        ArrayTaskList incomingList = new ArrayTaskList();
        for (Task task : tasks)
            if (task.nextTimeAfter(from).before(to) && task.nextTimeAfter(from).equals(null))
                incomingList.add(task);
        return incomingList;
    }
}
