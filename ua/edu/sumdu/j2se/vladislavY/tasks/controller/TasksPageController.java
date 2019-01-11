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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
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

    SortedMap<Date, Set<Task>> calendar;
    private ObservableList<Map.Entry<Date, Set<Task>>> usersData;

    public TasksPageController() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        System.out.println("Arr size - " + MainClass.getTasks().size());
        calendar = Tasks.calendar(MainClass.getTasks(), new Date(), cal.getTime());
    }

    @FXML
    private void initialize() {
        title.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Date, Set<Task>>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<Date, Set<Task>>, String> param) {
                String titles = "";
                for (Task task : param.getValue().getValue()) {
                    titles += task.getTitle() + "\n";
                }
                return new SimpleStringProperty(titles);
            }
        });


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
        } catch (NullPointerException e) {
            System.out.println("The list of the tasks for selected period is empty");
        }

        disablePastDatesInDatepicker(startDate);
        disablePastDatesInDatepicker(endDate);
    }

    @FXML
    public void onAddTaskButtonClickListener() throws IOException {
        System.out.println("add task");
        Parent root = FXMLLoader.load(getClass().getResource("../view/addNewTaskView.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(root));
        stage.show();
        System.out.println("Test2");
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                /*try {
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DAY_OF_MONTH, 1);
                    System.out.println("Arr size - " + MainClass.getTasks().size());
                    calendar = Tasks.calendar(MainClass.getTasks(), new Date(), cal.getTime());
                    usersData = FXCollections.observableArrayList(calendar.entrySet());
                    tasks.setItems(usersData);
                    tasks.getColumns().setAll(title, nextDate);
                } catch (NullPointerException e) {
                    System.out.println("The list of the tasks for selected period is empty");
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                System.out.println("Close add dialog");
            }
        });
        System.out.println("Test1");
    }

    @FXML
    public void onApplyFilterButtonClickListener() throws Exception {
        try {
            System.out.println("apply filter");
            Instant startInstant = Instant.from(startDate.getValue().atStartOfDay(ZoneId.systemDefault()));
            Instant endInstant = Instant.from(endDate.getValue().atStartOfDay(ZoneId.systemDefault()));
            calendar = Tasks.calendar(MainClass.getTasks(), Date.from(startInstant), Date.from(endInstant));
            try {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DAY_OF_MONTH, 1);
                System.out.println("Arr size - " + MainClass.getTasks().size());
                calendar = Tasks.calendar(MainClass.getTasks(), new Date(), cal.getTime());
                usersData = FXCollections.observableArrayList(calendar.entrySet());
                tasks.setItems(usersData);
                tasks.getColumns().setAll(title, nextDate);
            } catch (NullPointerException e) {
                System.out.println("The list of the tasks for selected period is empty");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (NullPointerException e) {
            MessageController.warnDialog("Please, enter valid date");
            startDate.getEditor().clear();
            endDate.getEditor().clear();
        }
    }

    @FXML
    private void onCellClickLIstener() throws IOException {
        Map.Entry<Date, Set<Task>> row = tasks.getSelectionModel().getSelectedItem();
        if(row != null) {
            MainClass.setTaskForEditiong(row.getValue().iterator().next());
            Parent root = FXMLLoader.load(getClass().getResource("../view/overviewTaskView.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Add new Task");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    System.out.println("test");
                }
            });
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
