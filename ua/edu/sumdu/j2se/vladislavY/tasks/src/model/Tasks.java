package model;

import java.util.*;

public class Tasks {

    /**
     * Returns an array of tasks performed in the current period
     *
     * @param tasks collection of tasks
     * @param from  start of the period
     * @param to    end of the period
     * @return array of the tasks which will be fulfilled in current period
     */
    public static Iterable<Task> incoming(Iterable<Task> tasks, Date from, Date to) throws Exception {
        ArrayTaskList incomingList = new ArrayTaskList();
        for (Task task : tasks)
            try {
                if (task.nextTimeAfter(from).before(to) || task.nextTimeAfter(from).equals(to))
                    incomingList.add(task);
            } catch (NullPointerException e) {
            }
        return incomingList;
    }

    /**
     * Generate calendar (table) with tasks (events) for each day
     *
     * @param tasks collection of tasks
     * @param start start of the period
     * @param end   end of the period
     * @return table of tasks for each day
     */
    public static SortedMap<Date, Set<Task>> calendar(Iterable<Task> tasks, Date start, Date end) throws Exception {
        TreeMap<Date, Set<Task>> calendar = new TreeMap<Date, Set<Task>>();
        for (Task task : Tasks.incoming(tasks, start, end))
            for (Date date : Tasks.events(task, start, end))
                if (!calendar.keySet().contains(date)) {
                    Set<Task> tasksSet = new HashSet<>();
                    tasksSet.add(task);
                    calendar.put(date, tasksSet);
                } else {
                    Set<Task> tasksSetNew = calendar.get(date);
                    tasksSetNew.add(task);
                    calendar.put(date, tasksSetNew);
                }
        return calendar;
    }

    /**
     * Generates array of event dates, which will be performed in the current period
     *
     * @param task current task
     * @param start start of the period
     * @param end   end of the period
     * @return event dates
     */
    public static ArrayList<Date> events(Task task, Date start, Date end){
        ArrayList<Date> dates = new ArrayList<>();
        for (Date i = task.nextTimeAfter(start); !end.before(i); i = task.nextTimeAfter(i))
            dates.add(i);
        return dates;
    }
}
