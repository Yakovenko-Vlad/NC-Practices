package ua.edu.sumdu.j2se.vladislavY.tasks.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ua.edu.sumdu.j2se.vladislavY.tasks.MainClass;
import ua.edu.sumdu.j2se.vladislavY.tasks.model.Task;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


public class AddNewTaskController {
    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField title;

    @FXML
    private TextField interval;

    @FXML
    private DatePicker time;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private CheckBox isActive;

    @FXML
    private CheckBox isRepeated;

    @FXML
    private void initialize() {
        disablePastDatesInDatepicker(time);
        disablePastDatesInDatepicker(startDate);
        disablePastDatesInDatepicker(endDate);
    }

    @FXML
    private void repeatChBoxHandler() {
        if (!isRepeated.isSelected()) {
            startDate.setDisable(true);
            endDate.setDisable(true);
            interval.setDisable(true);
            time.setDisable(false);
        } else {
            time.setDisable(true);
            startDate.setDisable(false);
            endDate.setDisable(false);
            interval.setDisable(false);
        }
    }

    @FXML
    private void cancelButtonListener() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        if (MessageController.onCloseDialog("Are you sure you want to remove all changes?")) stage.close();
    }

    @FXML
    private void addButtonListener() throws Exception {
        Task task;
        if (isRepeated.isSelected()) {
            Instant instantStart = Instant.from(startDate.getValue().atStartOfDay(ZoneId.systemDefault()));
            Instant instantEnd = Instant.from(endDate.getValue().atStartOfDay(ZoneId.systemDefault()));
            task = new Task(title.getText(), Date.from(instantStart), Date.from(instantEnd),
                    Integer.parseInt(interval.getText()));
        } else {
            Instant instant = Instant.from(time.getValue().atStartOfDay(ZoneId.systemDefault()));
            task = new Task(title.getText(), Date.from(instant));
        }
        task.setActive(isActive.isSelected());
        MainClass.addTaskToList(task);
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
    }

    private void validateData() {
        if (isRepeated.isSelected()) {

        } else {

        }

    }

    private void disablePastDatesInDatepicker(DatePicker datePicker) {
        datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
    }
}
