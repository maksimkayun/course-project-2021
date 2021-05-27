package admin;

import animations.Shake;
import configs.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import openWindow.OpenWindow;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ManageTrainsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exitButton;

    @FXML
    private TextField startStationField;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button showStationTable;

    @FXML
    private Text warningText;

    @FXML
    private Button showTrainTable;

    @FXML
    private TextField endStationField;

    @FXML
    private TextField numberTrainField;

    @FXML
    void initialize() {
        exitButton.setOnAction(actionEvent -> {
            exitButton.getScene().getWindow().hide();
            OpenWindow ow = new OpenWindow("/admin/admin.fxml");
        });
        DatabaseHandler dbHandler = new DatabaseHandler();
        deleteButton.setOnAction(actionEvent -> {
            try {
                Integer numberTrain = Integer.parseInt(numberTrainField.getText().trim());
                if (!findMatches(numberTrain)) {
                    warningText.setText("Поезда с таким номером нет!");
                    Shake shake = new Shake(numberTrainField);
                    shake.playAnim();
                } else {
                    dbHandler.deleteTrain(numberTrain);
                    numberTrainField.clear();
                }
                numberTrainField.clear();
            } catch (NumberFormatException e) {
                //e.printStackTrace();
                warningText.setText("Поле \"Удаление\" должно быть заполнено цифрой!");
                Shake shake = new Shake(numberTrainField);
                shake.playAnim();
                numberTrainField.clear();
            }
        });
        addButton.setOnAction(actionEvent -> {
            String startStation = startStationField.getText().trim();
            String finishStation = endStationField.getText().trim();
            if (!startStation.equals("") && !finishStation.equals("") &&
            ManageStatController.findMatches(startStation) && ManageStatController.findMatches(finishStation)
            && !startStation.equals(finishStation)) {
                dbHandler.addTrain(startStation, finishStation);
                startStationField.clear();
                endStationField.clear();
                warningText.setText("");
            }
            else if (startStation.equals("") || finishStation.equals("")) {
                warningText.setText("Все поля \"Добавить\" должны быть заполнены!");
                if (startStation.equals("")) {
                    Shake shake = new Shake(startStationField);
                    shake.playAnim();
                }
                if (finishStation.equals("")) {
                    Shake shake = new Shake(endStationField);
                    shake.playAnim();
                }
            }
            else if (!ManageStatController.findMatches(startStation)) {
                warningText.setText("Такой станции нет!");
                Shake shake = new Shake(startStationField);
                shake.playAnim();
            }
            else if (!ManageStatController.findMatches(finishStation)) {
                warningText.setText("Такой станции нет!");
                Shake shake = new Shake(endStationField);
                shake.playAnim();
            }
            else {
                warningText.setText("Названия станций совпадают!");
            }
        });
        showTrainTable.setOnAction(actionEvent -> {
            OpenWindow ow = new OpenWindow("/tables/tableTrains.fxml");
        });
        showStationTable.setOnAction(actionEvent -> {
            OpenWindow ow = new OpenWindow("/tables/tableStations.fxml");
        });
    }

    public static boolean findMatches(Integer id) {
        boolean result = false;
        DatabaseHandler dbHandler = new DatabaseHandler();
        int counter = 0;
        ResultSet res = dbHandler.getTrain(id);
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
