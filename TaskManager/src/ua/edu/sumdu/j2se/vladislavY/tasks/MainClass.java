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

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class MainClass extends Application {
    private static ArrayTaskList tasks = new ArrayTaskList();
    private static Task taskForEditing;
    public static final File file = new File("tasks.txt");
    private static final Logger log = Logger.getLogger(MainClass.class);

    public MainClass() throws Exception {
        //PropertyConfigurator.configure("src/resources/log4j.properties");
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

    /**
     * Read tasks from file on app start
     */
    private void loadSavedData() {
        TaskIO.readText(tasks, file);
    }

    /**
     * Write all tasks to file on close
     *
     * @param stage
     */
    private void onCloseListener(Stage stage) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                TaskIO.writeText(tasks, file);
            }
        });
    }

    /**
     * Returns all tasks
     *
     * @return tasks array
     */
    public static ArrayTaskList getTasks() {
        return tasks;
    }

    /**
     * Replace main tasks array with new (changed) tasks array
     *
     * @param taskslist
     */
    public static void setTasks(ArrayTaskList taskslist) {
        tasks = taskslist;
    }

    /**
     * Add task to main array
     *
     * @param task
     * @throws Exception
     */
    public static void addTaskToList(Task task) throws Exception {
        tasks.add(task);
    }

    /**
     * Remember task for overview controller
     *
     * @param task
     */
    public static void setTaskForEditing(Task task) {
        taskForEditing = task;
    }

    /**
     * Returns task for overview controller
     *
     * @return
     */
    public static Task getTaskForEditing() {
        return taskForEditing;
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Notify user about event in calendar.
     * Runs each minute and go through main tasks array. Get all tasks which will happened in next minute.
     */
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
                                    ArrayTaskList listForMinute = (ArrayTaskList) Tasks.incoming(MainClass.tasks, new Date(), cal.getTime(), true);
                                    if (listForMinute.size() > 0) {
                                        String str = "";
                                        for (Task task : listForMinute) { // all tasks titles to notify in one string
                                            log.info("Title: " + task.getTitle() + ", current date: " + new Date() +
                                                    "nextTimeAfter current" + task.nextTimeAfter(new Date()));
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
