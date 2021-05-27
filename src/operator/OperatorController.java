package operator;

import animations.Shake;
import configs.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import openWindow.OpenWindow;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OperatorController {

    @FXML
    private Button exitButton;

    @FXML
    private Label stationLabel;

    @FXML
    private Button showStationTable;

    @FXML
    private Button showTrainTable;

    @FXML
    private Button showOrderTable;

    @FXML
    private TextField nameOrderField;

    @FXML
    private Text showNumberOrderText;

    @FXML
    private Button regOrderButton;

    @FXML
    private TextField numberOrderField;

    @FXML
    private Button getOrderButton;

    @FXML
    private Text warningText;

    @FXML
    private TextField numberTrainOrderField;

    @FXML
    void initialize() {
        stationLabel.setText("Оператор станции " + SetCurrentStation.currentStation);
        exitButton.setOnAction(actionEvent -> {
            exitButton.getScene().getWindow().hide();
            OpenWindow ow = new OpenWindow("/sample/main.fxml");
        });
        showOrderTable.setOnAction(actionEvent -> {
            OpenWindow ow = new OpenWindow("/tables/tableOrders.fxml");
        });
        showStationTable.setOnAction(actionEvent -> {
            OpenWindow ow = new OpenWindow("/tables/tableStations.fxml");
        });
        showTrainTable.setOnAction(actionEvent -> {
            OpenWindow ow = new OpenWindow("/tables/tableTrains.fxml");
        });
        regOrderButton.setOnAction(actionEvent -> {
            try {
                String name = nameOrderField.getText().trim();
                Integer numberTrain = Integer.parseInt(numberTrainOrderField.getText().trim());
                if (!name.equals("") && !numberTrainOrderField.getText().trim().equals("") && findTrain(numberTrain)) {

                    DatabaseHandler dbHandler = new DatabaseHandler();
                    ResultSet res = dbHandler.getTrain(numberTrain);

                    res.next();
                    String startStation = res.getString("start_station");
                    String finishStation = res.getString("finish_station");

                    if (startStation.equals(SetCurrentStation.currentStation)) {
                        dbHandler.addOrder(name, startStation, finishStation, numberTrain);
                        warningText.setText("");
                        nameOrderField.clear();
                        numberTrainOrderField.clear();
                    }
                    else {
                        warningText.setText("Нельзя зарегистрировать такой заказ! Станция отправления не ваша");
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (Exception e) {
                warningText.setText("Какое-то из полей заполнено неверно!");
                Shake shName = new Shake(nameOrderField);
                Shake shNumberTrain = new Shake(numberTrainOrderField);
                shName.playAnim();
                shNumberTrain.playAnim();
            }
        });
        getOrderButton.setOnAction(actionEvent -> {
            try {
                DatabaseHandler dbHandler = new DatabaseHandler();
                Integer numberOrder = Integer.parseInt(numberOrderField.getText().trim());
                ResultSet res = dbHandler.getOrder(numberOrder);
                if (res.next()) {
                    String startStation = res.getString("start_station");
                    String finishStation = res.getString("finish_station");
                    if (findMatches(numberOrder) && (finishStation.equals(SetCurrentStation.currentStation) ||
                            startStation.equals(SetCurrentStation.currentStation))) {
                        dbHandler.deleteOrder(numberOrder);
                        numberOrderField.clear();
                        warningText.setText("");
                    }
                    else if (!finishStation.equals(SetCurrentStation.currentStation) &&
                            !startStation.equals(SetCurrentStation.currentStation)) {
                        warningText.setText("Вы не можете получить данный заказ, так как станция назначения не ваша!");
                    }
                }
            } catch (Exception e) {
                warningText.setText("Поле заполнено неверно!");
                Shake shake = new Shake(numberOrderField);
                shake.playAnim();
            }
        });
    }

    private boolean findTrain(Integer numberTrain) {
        return admin.ManageTrainsController.findMatches(numberTrain);
    }

    public boolean findMatches(Integer id) {
        boolean result = false;
        DatabaseHandler dbHandler = new DatabaseHandler();
        int counter = 0;
        ResultSet res = dbHandler.getOrder(id);
        try {
            while (res.next()) {
                counter++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (counter >= 1) {
            result = true;
        }
        return result;
    }
}
