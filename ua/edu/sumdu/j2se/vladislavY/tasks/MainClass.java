package ua.edu.sumdu.j2se.vladislavY.tasks;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ua.edu.sumdu.j2se.vladislavY.tasks.controller.MessageController;
import ua.edu.sumdu.j2se.vladislavY.tasks.model.ArrayTaskList;
import ua.edu.sumdu.j2se.vladislavY.tasks.model.Task;
import ua.edu.sumdu.j2se.vladislavY.tasks.model.TaskIO;
import ua.edu.sumdu.j2se.vladislavY.tasks.model.Tasks;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainClass extends Application {
    private static ArrayTaskList tasks = new ArrayTaskList();
    private static Task taskForEditing;
    public static final File file = new File("tasks.txt");

    public MainClass() throws Exception {
        this.loadSavedData();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/tasksListView.fxml"));
        primaryStage.setTitle("Task Manager");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        onCloseListener(primaryStage);
        notifyAboutEvent();
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
        taskForEditing = task;
    }

    public static Task getTaskForEditiong() {
        return taskForEditing;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void notifyAboutEvent() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleWithFixedDelay(
                new Runnable() {
                    @Override
                    public void run() {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Calendar cal = Calendar.getInstance();
                                cal.add(Calendar.MINUTE, 1);
                                try {
                                    ArrayTaskList listForMinute = (ArrayTaskList) Tasks.incoming(MainClass.tasks, new Date(), cal.getTime());
                                    if (listForMinute.size() > 0) {
                                        String str = "";
                                        for (Task task : listForMinute) {
                                            str += task.getTitle() + "\n";
                                        }
                                        MessageController.notificationDialog(str);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }, 0, 1, TimeUnit.MINUTES
        );
    }
}
