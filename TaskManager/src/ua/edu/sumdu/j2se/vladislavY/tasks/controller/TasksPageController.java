package ua.edu.sumdu.j2se.vladislavY.tasks.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.vladislavY.tasks.MainClass;
import ua.edu.sumdu.j2se.vladislavY.tasks.model.Task;
import ua.edu.sumdu.j2se.vladislavY.tasks.model.Tasks;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class TasksPageController {
    @FXML
    private TableView<Map.Entry<Date, Set<Task>>> tasks;

    @FXML
    private TableColumn<Map.Entry<Date, Set<Task>>, String> title;

    @FXML
    private TableColumn<Map.Entry<Date, Set<Task>>, String> nextDate;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    private static final Logger log = Logger.getLogger(TasksPageController.class);
    SortedMap<Date, Set<Task>> calendar;
    private ObservableList<Map.Entry<Date, Set<Task>>> usersData;

    public TasksPageController() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        log.info("Arr size - " + MainClass.getTasks().size());
        calendar = Tasks.calendar(MainClass.getTasks(), new Date(), cal.getTime());
    }

    @FXML
    private void initialize() {
        // title cell filling
        title.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Date, Set<Task>>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<Date, Set<Task>>, String> param) {
                // if task inActive - "inActive" word is added -> ability to work with inActive tasks (like in Outlook)
                String titles = "";
                for (Task task : param.getValue().getValue()) {
                    titles += task.isActive() ? task.getTitle() + "\n" : task.getTitle() + " inActive\n";
                }
                return new SimpleStringProperty(titles);
            }
        });

        // calendar field
        nextDate.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Date, Set<Task>>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<Date, Set<Task>>, String> param) {
                return new SimpleStringProperty(param.getValue().getKey().toString());
            }
        });

        try {
            usersData = FXCollections.observableArrayList(calendar.entrySet());
            tasks.setItems(usersData);
            tasks.getColumns().setAll(title, nextDate);
        } catch (NullPointerException e) { // if calendar is empty
            log.warn("Calendar for current period (" + new Date() + ", +1) is empty");
        }

        disablePastDatesInDatepicker(startDate);
        disablePastDatesInDatepicker(endDate);
        startDate.getEditor().setDisable(true);
        endDate.getEditor().setDisable(true);
    }

    @FXML
    public void onAddTaskButtonClickListener() throws IOException {
        log.info("Add task button handler");
        Parent root = FXMLLoader.load(getClass().getResource("addNewTaskView.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(root));
        stage.show();
        stage.setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {// refresh tableView on main page after "add task" dialog is closed
                try {
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DAY_OF_MONTH, 1);
                    calendar = Tasks.calendar(MainClass.getTasks(), new Date(), cal.getTime());
                    usersData = FXCollections.observableArrayList(calendar.entrySet());
                    tasks.setItems(usersData);
                    tasks.getColumns().setAll(title, nextDate);
                } catch (NullPointerException e) {
                    log.warn("Calendar for current period (" + new Date() + ", +1) is empty");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                log.info("Add task dialog is closed, main tableView is reloaded");
            }
        });
    }

    @FXML
    public void onApplyFilterButtonClickListener() throws Exception {
        try {
            log.info("Filter button handler");
            Instant startInstant = Instant.from(startDate.getValue().atStartOfDay(ZoneId.systemDefault()));
            Instant endInstant = Instant.from(endDate.getValue().atStartOfDay(ZoneId.systemDefault()));
            calendar = Tasks.calendar(MainClass.getTasks(), Date.from(startInstant), Date.from(endInstant));
            try {
                usersData = FXCollections.observableArrayList(calendar.entrySet());
                tasks.setItems(usersData);
                tasks.getColumns().setAll(title, nextDate);
            } catch (NullPointerException e) {
                MessageController.warnDialog("You don\'t have event in current period");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (NullPointerException e) {
            MessageController.warnDialog("Please, enter valid date");
        }
    }

    @FXML
    private void onCellClickLIstener() throws IOException {
        log.info("Cell click handler");
        Map.Entry<Date, Set<Task>> row = tasks.getSelectionModel().getSelectedItem();
        if (row != null) {
            MainClass.setTaskForEditing(row.getValue().iterator().next());
            Parent root = FXMLLoader.load(getClass().getResource("overviewTaskView.fxml"));//packaging issue
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Add new Task");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnHidden(new EventHandler<WindowEvent>() {// refresh tableView on main page after "task overview" dialog is closed
                @Override
                public void handle(WindowEvent event) {
                    try {
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.DAY_OF_MONTH, 1);
                        calendar = Tasks.calendar(MainClass.getTasks(), new Date(), cal.getTime());
                        usersData = FXCollections.observableArrayList(calendar.entrySet());
                        tasks.setItems(usersData);
                        tasks.getColumns().setAll(title, nextDate);
                    } catch (NullPointerException e) {
                        System.out.println("The list of the tasks for selected period is empty");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    log.info("OverView task dialog is closed, main tableView is reloaded");
                }
            });
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
