package ua.edu.sumdu.j2se.vladislavY.tasks;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ua.edu.sumdu.j2se.vladislavY.tasks.model.ArrayTaskList;
import ua.edu.sumdu.j2se.vladislavY.tasks.model.TaskIO;


import java.io.File;

public class MainClass extends Application {
    private static ArrayTaskList tasks = new ArrayTaskList();
    public static final File file = new File("tasks.txt");

    public MainClass() {
       this.loadSavedData();
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

    public static void main(String[] args) {
        launch(args);
    }
}
