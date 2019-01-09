package ua.edu.sumdu.j2se.vladislavY.tasks.controller;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
    private TableView<Map.Entry<Date, Set<Task>>> tasksView;

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
        usersData = FXCollections.observableArrayList(calendar.entrySet());
    }

    @FXML
    private void initialize() {
        nextDate.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Date, Set<Task>>, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<Date, Set<Task>>, String> param) {
                return null;//(ObservableValue<String>) param.getValue().getKey();
            }

        });
        System.out.println("test!");
        System.out.println();
        try {
            tasksView.setItems(usersData);
            //tasksView.getColumns().setAll(title, startDate, endDate, interval, isRepeated);
        } catch (NullPointerException e) {
            int i = 0;
            for (Map.Entry<Date, Set<Task>> t : usersData) {
                System.out.println(i++ + " i");
            }
        }
    }

    @FXML
    public void onAddTaskButtonClickListener() throws IOException {
        System.out.println("add task");
        Parent root = FXMLLoader.load(getClass().getResource("../view/addNewTaskView.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Add new Task");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void onApplyFilterButtonClickListener() throws Exception {
        try {
            System.out.println("apply filter");
            LocalDate localDate = startDate.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date startDate = Date.from(instant);
            System.out.println(startDate);
            localDate = endDate.getValue();
            instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date endDate = Date.from(instant);
            System.out.println(endDate);
            calendar = Tasks.calendar(MainClass.getTasks(), startDate, endDate);
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Look, a Warning Dialog");
            alert.showAndWait();
            startDate.getEditor().clear();
            endDate.getEditor().clear();
        }
    }
}
