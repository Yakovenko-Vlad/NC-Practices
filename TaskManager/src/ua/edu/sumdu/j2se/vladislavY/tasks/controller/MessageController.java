package ua.edu.sumdu.j2se.vladislavY.tasks.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import org.apache.log4j.Logger;

/**
 * Message controller. Contains functionality of all available messages
 */
public class MessageController {
    private static final Logger log = Logger.getLogger(MessageController.class);

    /**
     * Warning dialog with ability to select "yes" or "no"
     *
     * @param message question
     * @return is "yes" selected
     */
    public static boolean onCloseDialog(String message) {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.WARNING,
                message, yes, no);
        log.warn("Alert message on dialog close: " + message);
        alert.setTitle("Warning");
        return alert.showAndWait().orElse(no) == yes;
    }

    /**
     * Simple warning dialog
     *
     * @param message
     */
    public static void warnDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText(message);
        log.warn("Alert message: " + message);
        alert.showAndWait();
    }

    /**
     * Dialog for time reminder notifications
     *
     * @param tasksTitles
     */
    public static void notificationDialog(String tasksTitles) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Task reminder");
        alert.setHeaderText(tasksTitles);
        log.info("Alert(reminder) message: " + tasksTitles);
        alert.showAndWait();
    }
}
