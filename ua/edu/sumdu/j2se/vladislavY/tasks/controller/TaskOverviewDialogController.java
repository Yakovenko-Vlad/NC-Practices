package ua.edu.sumdu.j2se.vladislavY.tasks.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ua.edu.sumdu.j2se.vladislavY.tasks.MainClass;
import ua.edu.sumdu.j2se.vladislavY.tasks.model.ArrayTaskList;
import ua.edu.sumdu.j2se.vladislavY.tasks.model.Task;

public class TaskOverviewDialogController {
    private ArrayTaskList tasks;
    private Task task;
    private int index;

    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button editBtn;

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


    public TaskOverviewDialogController() {
        this.tasks = MainClass.getTasks();
        this.task = MainClass.getTaskForEditiong();
        getTaskIndex();
    }

    @FXML
    private void initialize() {
        title.setText(task.getTitle());
        time.setText(task.getTime().toString());
        startDate.setText(task.getStartTime().toString());
        endDate.setText(task.getEndTime().toString());
        interval.setText(String.valueOf(task.getRepeatInterval()));
        isActive.setSelected(task.isActive());
    }

    private void getTaskIndex() {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.getTask(i).equals(task)) {
                index = i;
                return;
            }
        }
    }

    @FXML
    private void editBtnHandler() {
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
    private void saveBtnHandler() {

    }
}
