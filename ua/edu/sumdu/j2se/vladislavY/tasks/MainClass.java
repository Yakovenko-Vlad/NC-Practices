package ua.edu.sumdu.j2se.vladislavY.tasks;

public class MainClass {
    public static void main(String[] args) {
        Task task = new Task("task", 1);
        System.out.println(task.isRepeated());
        System.out.println(task.getRepeatInterval());
    }
}
