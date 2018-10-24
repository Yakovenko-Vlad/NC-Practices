package ua.edu.sumdu.j2se.vladislavY.tasks;

public class MainClass {
    public static void main(String[] args) {
        Task task = new Task("task", 1);
        Task task3 = new Task("task3", 2);
        task.setActive(true);
        task3.setActive(true);
        ArrayTaskList arr = new ArrayTaskList();
        arr.add(task);
        arr.add(task3);
        System.out.println(arr.incoming(1, 2).getTask(0).getTitle());
    }
}
