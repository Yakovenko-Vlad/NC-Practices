package ua.edu.sumdu.j2se.vladislavY.tasks;

import java.io.File;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        Task t = new Task("test1 \"best\" test", new Date(new Date().getTime()-5000), new Date(new Date().getTime()+5000), 10000);
        Task t2 = new Task("test2", new Date());
        t2.setActive(true);
        ArrayTaskList arrayTaskList = new ArrayTaskList();
        arrayTaskList.add(t);
        arrayTaskList.add(t2);
        TaskIO.writeBinary(arrayTaskList, new File("test.txt"));
        ArrayTaskList arrayTaskList1 = new ArrayTaskList();
        TaskIO.readBinary(arrayTaskList1, new File("test.txt"));
        System.out.println("Binary IO test");
        for(Task task : arrayTaskList1) {
            System.out.println(task.toString());
        }

        System.out.println("\nText IO test");
        ArrayTaskList arrayTaskList2 = new ArrayTaskList();
        TaskIO.writeBinary(arrayTaskList, new File("test1.txt"));
        TaskIO.readBinary(arrayTaskList2, new File("test1.txt"));
        for(Task task : arrayTaskList2) {
            System.out.println(task.toString());
        }

        ArrayTaskList arrayTaskList3 = new ArrayTaskList();
        String str2 = "\"B\" from [2018-12-20 12:51:01.551] to [2018-12-21 12:51:01.551] every [1 hour ].";
        System.out.println(TaskIO.parseTitle(str2));
        System.out.println(TaskIO.parseDate(str2)[0]);
        System.out.println(TaskIO.parseDate(str2)[1]);
        System.out.println(TaskIO.parseInterval(str2));
    }
}
