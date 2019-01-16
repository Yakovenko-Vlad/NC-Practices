package ua.edu.sumdu.j2se.vladislavY.tasks.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.vladislavY.tasks.MainClass;
import ua.edu.sumdu.j2se.vladislavY.tasks.model.ArrayTaskList;
import ua.edu.sumdu.j2se.vladislavY.tasks.model.Task;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskOverviewDialogController {
    private ArrayTaskList tasks;
    private Task task;
    private int index;

    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button removeBtn;

    @FXML
    private TextField title;

    @FXML
    private TextField startDate;

    @FXML
    private TextField endDate;

    @FXML
    private TextField time;

    @FXML
    private TextField interval;

    @FXML
    private CheckBox isActive;

    private static final Logger log = Logger.getLogger(TaskOverviewDialogController.class);
    String datePattern = "yyyy-MM-dd HH:mm:ss"; // pattern for date convertor

    public TaskOverviewDialogController() {
        this.tasks = MainClass.getTasks();
        this.task = MainClass.getTaskForEditing();
        getTaskIndex();
    }

    @FXML
    private void initialize() {
        Format formatter = new SimpleDateFormat(datePattern);
        title.setText(task.getTitle());
        time.setText(formatter.format(task.getTime()));
        startDate.setText(formatter.format(task.getStartTime()));
        endDate.setText(formatter.format(task.getEndTime()));
        interval.setText(String.valueOf(task.getRepeatInterval()));
        isActive.setSelected(task.isActive());
    }

    /**
     * Define task index in main tasks array
     */
    private void getTaskIndex() {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.getTask(i).equals(task)) {
                index = i;
                log.info("Tasks index for editing - " + index);
                return;
            }
        }
    }

    @FXML
    private void editBtnHandler() {
        log.info("Edit btn click");
        title.setDisable(false);
        time.setDisable(false);
        startDate.setDisable(false);
        endDate.setDisable(false);
        interval.setDisable(false);
        isActive.setDisable(false);
        saveBtn.setVisible(true);
    }

    @FXML
    private void removeBtnHandler() {
        if (MessageController.onCloseDialog("Are you sure you want to remove current task from calendar?")) {
            tasks.remove(task);
            MainClass.setTasks(tasks);
            Stage stage = (Stage) removeBtn.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void cancelBtnHandler() {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        if (MessageController.onCloseDialog("Are you sure you want to remove all changes?")) stage.close();
    }

    @FXML
    private void saveBtnHandler() throws Exception {
        Task t = validateData();
        if (t != null) {
            t.setActive(isActive.isSelected());
            tasks.remove(task);
            tasks.add(t);
            MainClass.setTasks(tasks);
            Stage stage = (Stage) saveBtn.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Validate all entered data (new task fields) after "add task" button pressing
     *
     * @return new task if there is no any validate problems otherwise null
     * @throws Exception
     */
    private Task validateData() throws Exception {
        Date timeDate;
        Date sDate;
        Date eDate;
        int intervalD;
        String titleText = title.getText();
        if (titleText.isEmpty()) {
            MessageController.warnDialog("Enter tasks title");
            return null;
        }
        String intervalText = interval.getText();
        if (intervalText.isEmpty()) {//validate interval field (is empty)
            MessageController.warnDialog("Enter Interval");
            return null;
        } else if (!intervalText.matches("[0-9]*")) {// verify is entered characters are digits
            log.warn("Interval: " + intervalText);
            MessageController.warnDialog("Use only digits");
            interval.setText("");
            return null;
        } else {
            intervalD = Integer.parseInt(intervalText);
        }
        DateFormat format = new SimpleDateFormat(datePattern);
        try {// get data from time/date fields - get exception if text is not valid
            timeDate = format.parse(time.getText());
            sDate = format.parse(startDate.getText());
            eDate = format.parse(endDate.getText());
        } catch (ParseException e) { //clear all time fields
            time.setText("");
            startDate.setText("");
            endDate.setText("");
            log.warn("Start: " + startDate.getText() + ", End: " + endDate.getText() +
                    ", Time: " + time.getText() + ". Interval - " + intervalD);
            MessageController.warnDialog("Enter valid date by pattern \'" + datePattern + "\'");
            return null;
        }
        if (intervalD == 0)
            return new Task(titleText, timeDate);
        else
            return new Task(titleText, sDate, eDate, intervalD / 1000);
    }
}
