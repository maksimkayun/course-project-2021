package admin;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import openWindow.OpenWindow;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exitButton;

    @FXML
    private Button manageOperButton;

    @FXML
    private Button manageStatButton;

    @FXML
    private Button manageTrainButton;

    @FXML
    void initialize() {
        exitButton.setOnAction(actionEvent -> {
            exitButton.getScene().getWindow().hide();
            OpenWindow ow = new OpenWindow("/sample/main.fxml");
        });
        manageOperButton.setOnAction(actionEvent -> {
            manageOperButton.getScene().getWindow().hide();
            OpenWindow ow = new OpenWindow("/admin/manageOperator.fxml");
        });
        manageStatButton.setOnAction(actionEvent -> {
            manageStatButton.getScene().getWindow().hide();
            OpenWindow ow = new OpenWindow("/admin/manageStat.fxml");
        });
        manageTrainButton.setOnAction(actionEvent -> {
            manageStatButton.getScene().getWindow().hide();
            OpenWindow ow = new OpenWindow("/admin/manageTrains.fxml");
        });
    }
}