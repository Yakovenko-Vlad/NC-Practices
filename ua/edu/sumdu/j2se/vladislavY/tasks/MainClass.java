package ua.edu.sumdu.j2se.vladislavY.tasks;

import java.io.File;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
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

        Task t = new Task("test1 \"best\" test", new Date(new Date().getTime()-5000), new Date(new Date().getTime()+5000), 10000);
        Task t2 = new Task("test2", new Date());
        t2.setActive(true);
//        System.out.println(t.toString());
//        System.out.println(TaskIO.dateFormater(t2.getTime()));
//        System.out.println( "\"" + t.getTitle().replaceAll("\"", "\"\"") + "\"");
//        System.out.println(t.getRepeatInterval());
//        System.out.println(TaskIO.intervalFormater(t.getRepeatInterval()));
        ArrayTaskList arrayTaskList = new ArrayTaskList();
        arrayTaskList.add(t);
        arrayTaskList.add(t2);
        TaskIO.writeBinary(arrayTaskList, new File("test.txt"));
        ArrayTaskList arrayTaskList1 = new ArrayTaskList();
        TaskIO.readBinary(arrayTaskList1, new File("test.txt"));
        System.out.println(arrayTaskList1.getTask(0).getTitle());
        System.out.println(arrayTaskList1.getTask(0).getStartTime());
        System.out.println(arrayTaskList1.getTask(0).getEndTime());
        System.out.println(arrayTaskList1.getTask(0).getRepeatInterval());

        System.out.println(arrayTaskList1.getTask(1).getTitle());
        System.out.println(arrayTaskList1.getTask(1).getTime());

//        String str1 = "\"test2\" at [2018-12-19 00:36:28.818] active.";
//        String str2 = "\"test1 \"\"best\"\" test\" from [2018-12-19 02:11:55.134] to [2018-12-19 02:12:05.134] every [2 hours 46 minutes 40 seconds];";
//        str1.matches("\"\\s*\"");
//        System.out.println(str1.matches("/active/g"));
//        //TaskIO.parseNotRepeatedTask(str1);
//        String date = "[2018-12-19 00:36:28.818]";
//        SimpleDateFormat format = new SimpleDateFormat();
//        format.applyPattern(TaskIO.DATE_PATTERN);
//        Date docDate= format.parse(str2, new ParsePosition(str2.indexOf("[")));
//        System.out.println(docDate);
//        for (int i = str2.indexOf("["); i > 0; i = str2.indexOf("[", i + 1)){
//            System.out.println(i);
//        }
//        //TaskIO.parseInterval(str2);
//        System.out.println(str2.substring(str2.lastIndexOf("[") +1 , str2.lastIndexOf("]")).split(" [a-zA-Z]"));
//        String[] a = str2.substring(str2.lastIndexOf("[") +1 , str2.lastIndexOf("]")).split(" ");
//        for(String s : a){
//            System.out.println(s);
//        }
        //format.
    }
}
