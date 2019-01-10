package ua.edu.sumdu.j2se.vladislavY.tasks;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ua.edu.sumdu.j2se.vladislavY.tasks.model.ArrayTaskList;
import ua.edu.sumdu.j2se.vladislavY.tasks.model.Task;
import ua.edu.sumdu.j2se.vladislavY.tasks.model.TaskIO;


import java.io.File;
import java.util.Date;

public class MainClass extends Application {
    private static ArrayTaskList tasks = new ArrayTaskList();
    private static Task taskForEditiong;
    public static final File file = new File("tasks.txt");

    public MainClass() throws Exception {
       this.loadSavedData();
       //tasks.add(new Task("test1 \"best\" test", new Date(new Date().getTime()-5000), new Date(new Date().getTime()+5000), 100000));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/tasksListView.fxml"));
        primaryStage.setTitle("Task Manager");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        onCloseListener(primaryStage);
    }

    private void loadSavedData() {
        TaskIO.readText(tasks, file);
    }

    private void onCloseListener(Stage stage) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                TaskIO.writeText(tasks, file);
            }
        });
    }

    public static ArrayTaskList getTasks() {
        return tasks;
    }

    public static void setTasks(ArrayTaskList taskslist) {
        tasks = taskslist;
    }

    public static void addTaskToList(Task task) throws Exception {
        tasks.add(task);
    }

    public static void setTaskForEditiong(Task task) {
        taskForEditiong = task;
    }

    public static Task getTaskForEditiong() {
        return taskForEditiong;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
