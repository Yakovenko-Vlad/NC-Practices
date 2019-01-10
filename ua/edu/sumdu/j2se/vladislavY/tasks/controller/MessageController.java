package ua.edu.sumdu.j2se.vladislavY.tasks.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class MessageController {

    public static boolean onCloseDialog(String message) {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.WARNING,
                message, yes, no);
        alert.setTitle("Warning");
        return  alert.showAndWait().orElse(no) == yes;
    }

    public static void warnDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
