package ua.edu.sumdu.j2se.vladislavY.tasks.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.vladislavY.tasks.MainClass;
import ua.edu.sumdu.j2se.vladislavY.tasks.model.Task;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Controller for "Add task" dialog
 */
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

    private static final Logger log = Logger.getLogger(AddNewTaskController.class);

    @FXML
    private void initialize() {
        disablePastDatesInDatepicker(time);
        disablePastDatesInDatepicker(startDate);
        disablePastDatesInDatepicker(endDate);
        time.getEditor().setDisable(true);
        startDate.getEditor().setDisable(true);
        endDate.getEditor().setDisable(true);
    }

    @FXML
    private void repeatChBoxHandler() {
        if (!isRepeated.isSelected()) { // enable/disable fields for repeated/not repeated tasks
            log.info("Repeated task view selected");
            startDate.setDisable(true);
            endDate.setDisable(true);
            interval.setDisable(true);
            time.setDisable(false);
        } else {
            log.info("Not repeated task view selected");
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
        Task t = validateData();
        if (t != null) { // if entered data is valid
            t.setActive(isActive.isSelected());
            MainClass.addTaskToList(t);
            Stage stage = (Stage) addButton.getScene().getWindow();
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
        String titleText = title.getText();
        if (titleText.isEmpty()) { // title field is empty
            MessageController.warnDialog("Enter task title");
            return null;
        }
        try {
            if (isRepeated.isSelected()) {// repeated task type
                Instant instantStart = Instant.from(startDate.getValue().atStartOfDay(ZoneId.systemDefault()));
                Instant instantEnd = Instant.from(endDate.getValue().atStartOfDay(ZoneId.systemDefault()));
                String intervalText = interval.getText();
                if (intervalText.isEmpty()) { //validate interval field (is empty)
                    MessageController.warnDialog("Enter Interval");
                    return null;
                } else if (!intervalText.matches("[0-9]*")) { // verify is entered characters are digits
                    log.warn("Interval:" + intervalText);
                    MessageController.warnDialog("Use only digits");
                    interval.setText("");
                    return null;
                }
                return new Task(titleText, Date.from(instantStart), Date.from(instantEnd),
                        Integer.parseInt(intervalText) / 1000);
            } else {
                Instant instant = Instant.from(time.getValue().atStartOfDay(ZoneId.systemDefault()));
                return new Task(titleText, Date.from(instant));
            }
        } catch (NullPointerException e) {
            log.warn("Start: " + startDate.getValue() + ", End: " + endDate.getValue() +
                    ", Time: " + time.getValue() + ". IsRepeated: " + isRepeated.isSelected());
            MessageController.warnDialog("Fill all task DATE fields");
            return null;
        }
    }

    /**
     * Disable earliest dated in datePicker.
     * Unable to create task for previous day
     *
     * @param datePicker
     */
    private void disablePastDatesInDatepicker(DatePicker datePicker) {
        datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0);
            }
        });
    }
}
