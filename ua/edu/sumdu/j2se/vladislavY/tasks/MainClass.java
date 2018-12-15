package ua.edu.sumdu.j2se.vladislavY.tasks;

import java.util.*;

public class MainClass {
    public static void main(String[] args) throws Exception {
        /*Task task = null;
        Task task2 = null;
        Task task3 = null;
        Task task4 = null;

        try {
            task = new Task("task", new Date(), new Date(), 1);
            task3 = new Task("task3", new Date());
            task2 = new Task("task2", new Date());
            task4 = new Task("task4", new Date());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        task.setActive(true);
        task3.setActive(true);
        System.out.println(task.toString());
        LinkedTaskList arr = new LinkedTaskList();
        arr.add(task);
        arr.add(task2);
        arr.add(task3);
        arr.add(task4);
        //System.out.println(arr.incoming(1, 2).getTask(0).getTitle());
        System.out.println(arr.getTask(0).getTitle());
        System.out.println(arr.getTask(1).getTitle());
        System.out.println(arr.getTask(2).getTitle());
        System.out.println(arr.getTask(3).getTitle());
        arr.remove(task4);
        System.out.println(arr.getTask(0).getTitle());
        System.out.println(arr.getTask(1).getTitle());
        System.out.println(arr.getTask(2).getTitle());
        System.out.println(arr.size());
        //System.out.println(arr.incoming(0, 2).getTask(0).getTitle());

        ArrayTaskList a = (ArrayTaskList) Tasks.incoming(arr, new Date(), new Date());*/

        TreeMap<Integer, Set<String>> calendar = new TreeMap<Integer, Set<String>>();
        Set<String> str = new HashSet<>();
        str.add("dsa");

        calendar.put(1, str);

        Task t = new Task("test", new Date(new Date().getTime()-5000), new Date(new Date().getTime()+5000), 100);
        ArrayList<Date> td = Tasks.events(t, new Date(new Date().getTime()-500), new Date(new Date().getTime() + 500));
        for(Date d : td) {
            System.out.println(d.getTime());
        }
    }
}
