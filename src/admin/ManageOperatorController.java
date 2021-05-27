package admin;

import animations.Shake;
import configs.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import openWindow.OpenWindow;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageOperatorController {

    @FXML
    private Button exitButton;

    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button showOperatorTable;

    @FXML
    private Text warningText;

    @FXML
    private TextField stationField;

    @FXML
    private Button showStatTable;


    @FXML
    void initialize() {
        exitButton.setOnAction(actionEvent -> {
            exitButton.getScene().getWindow().hide();
            OpenWindow ow = new OpenWindow("/admin/admin.fxml");
        });
        showStatTable.setOnAction(actionEvent -> {
            OpenWindow ow = new OpenWindow("/tables/tableStations.fxml");
        });
        DatabaseHandler dbHandler = new DatabaseHandler();
        addButton.setOnAction(actionEvent -> {
            String login = loginField.getText().trim();
            String password = passwordField.getText().trim();
            String position = "operator";
            String station = stationField.getText().trim();

            if(!ManageStatController.findMatches(station)) {
                warningText.setText("Такой станции нет!");
                loginField.clear();
                passwordField.clear();
                stationField.clear();
            }
            else if (!login.equals("") && !password.equals("")
            && !position.equals("") && !findMatches(login, password)) {
                dbHandler.signUpUser(login, password, position, station);
                loginField.clear();
                passwordField.clear();
                stationField.clear();
                warningText.setText("");
            }
            else if (findMatches(login, password)){
                warningText.setText("Такой пользователь уже есть!");
                loginField.clear();
                passwordField.clear();
                stationField.clear();
            }
            else {
                warningText.setText("Все поля должны быть заполнены!");
            }
        });
        deleteButton.setOnAction(actionEvent -> {
            String login = loginField.getText().trim();
            String password = passwordField.getText().trim();
            String position = "operator";
            String station = stationField.getText().trim();

            if(!ManageStatController.findMatches(station)) {
                warningText.setText("Такой станции нет!");
                Shake shake = new Shake(stationField);
                shake.playAnim();
                loginField.clear();
                passwordField.clear();
                stationField.clear();
            }
            else if (!login.equals("") && !password.equals("") && findMatches(login, password)) {
                ResultSet res = dbHandler.getUser(login, password);
                try {
                    res.next();
                    String nameStation = res.getString("station");
                    if (nameStation.equals(station)) {
                        dbHandler.deleteUser(login, password, station);
                        loginField.clear();
                        passwordField.clear();
                        stationField.clear();
                        warningText.setText("");
                    }
                    else {
                        warningText.setText("Станция указана неверно!");
                        Shake shake = new Shake(stationField);
                        shake.playAnim();
                        stationField.clear();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            else {
                warningText.setText("Все поля должны быть заполнены!");
            }
        });
        showOperatorTable.setOnAction(actionEvent -> {
            OpenWindow ow = new OpenWindow("/tables/tableUsers.fxml");
        });
    }

    public boolean findMatches(String login, String password) {
        boolean result = false;
        DatabaseHandler dbHandler = new DatabaseHandler();
        int counter = 0;
        ResultSet res = dbHandler.getUser(login, password);
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
