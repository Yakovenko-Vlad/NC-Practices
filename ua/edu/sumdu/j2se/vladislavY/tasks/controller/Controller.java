package ua.edu.sumdu.j2se.vladislavY.tasks.controller;

import com.sun.tools.javac.Main;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import ua.edu.sumdu.j2se.vladislavY.tasks.MainClass;
import ua.edu.sumdu.j2se.vladislavY.tasks.model.Task;
import ua.edu.sumdu.j2se.vladislavY.tasks.model.Tasks;

import java.util.*;

public class Controller {
    @FXML
    private TableView<Map.Entry<Date, Set<Task>>> tasksView;

    @FXML
    private TableColumn<Map.Entry<Date, Set<Task>>, String> title;

    @FXML
    private TableColumn<Map.Entry<Date, Set<Task>>, String> startDate;

    @FXML
    private TableColumn<Map.Entry<Date, Set<Task>>, String> endDate;

    @FXML
    private TableColumn<Map.Entry<Date, Set<Task>>, String> interval;

    @FXML
    private TableColumn<Map.Entry<Date, Set<Task>>, String> isRepeated;

    SortedMap<Date, Set<Task>> calendar;
    private ObservableList<Map.Entry<Date, Set<Task>>> usersData;



    public Controller() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        calendar = Tasks.calendar(MainClass.getTasks(), new Date(), cal.getTime());
        usersData =  FXCollections.observableArrayList(calendar.entrySet());
    }

    @FXML
    private void initialize() {
        title.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Date, Set<Task>>, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<Date, Set<Task>>, String> param) {
                return null;//(ObservableValue<String>) param.getValue().getKey();
            }

        });
        System.out.println("test!");
        tasksView.setItems(usersData);
        //tasksView.getColumns().setAll(title, startDate, endDate, interval, isRepeated);
    }
}
