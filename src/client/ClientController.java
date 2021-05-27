package client;

import configs.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import openWindow.OpenWindow;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientController {
    public static Integer currentNumberOrder;

    @FXML
    private Text warningText;

    @FXML
    private Text orderText;

    @FXML
    private Text orderNameText;

    @FXML
    private Text startStationText;

    @FXML
    private Text finishStationText1;

    @FXML
    private Text numberTrainText;

    @FXML
    private Button exitButton;

    @FXML
    void initialize() {
        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet res = dbHandler.getOrder(currentNumberOrder);
        try {
            res.next();
            orderText.setText("Заказ №" + currentNumberOrder);
            orderNameText.setText("Имя: " + res.getString("name"));
            startStationText.setText("Станция отправления: " + res.getString("start_station"));
            finishStationText1.setText("Станция назначения: " + res.getString("finish_station"));
            numberTrainText.setText("Поезд № " + res.getInt("number_train"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        exitButton.setOnAction(actionEvent -> {
            exitButton.getScene().getWindow().hide();
            OpenWindow ow = new OpenWindow("/sample/main.fxml");
        });
    }
}
