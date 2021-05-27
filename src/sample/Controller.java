package sample;

import animations.Shake;
import client.ClientController;
import configs.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import openWindow.OpenWindow;
import operator.SetCurrentStation;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button numberOrderButton;

    @FXML
    private TextField numberOrderField;

    @FXML
    private Text warningText;

    @FXML
    void initialize() {
        loginButton.setOnAction(event -> {
            String login = loginField.getText().trim(); // trim() удаляет лишние пробелы
            String password = passwordField.getText().trim();
            String numberOrder = numberOrderField.getText().trim();

            if (!login.equals("") && !password.equals("") && numberOrder.equals("")) {
                loginUser(login, password);
            }
            else if (!login.equals("") && !password.equals("") && !numberOrder.equals("")) {
                warningText.setText("Выберите что-то одно!");
            }
            else if (login.equals("") && password.equals("") && numberOrder.equals("")) {
                warningText.setText("Ни одно поле не заполнено!");
            }
            if (login.equals("%appdata%")){
                loginButton.getScene().getWindow().hide();
                OpenWindow ow = new OpenWindow("/testPack/LogIn.fxml");
            }
        });
        numberOrderButton.setOnAction(actionEvent -> {
            try {
                if (findNumberOrder(Integer.parseInt(numberOrderField.getText().trim()))) {
                    ClientController.currentNumberOrder = Integer.parseInt(numberOrderField.getText().trim());
                    warningText.setText("");
                    numberOrderButton.getScene().getWindow().hide();
                    OpenWindow ow = new OpenWindow("/client/client.fxml");
                } else {
                    warningText.setText("Такого заказа ещё нет!");
                    Shake shake = new Shake(numberOrderField);
                    shake.playAnim();
                }
            } catch (NumberFormatException e) {
                warningText.setText("Заполните поле!");
                Shake shake = new Shake(numberOrderField);
                shake.playAnim();
            }
        });
    }

    private boolean findNumberOrder(Integer id) {
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

    private void loginUser(String login, String password) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet result = dbHandler.getUser(login, password);
        try {
            if (result.next()) {
                    String position = result.getString("position");
                    if (position.equals("admin")) {
                        loginButton.getScene().getWindow().hide();
                        OpenWindow ow = new OpenWindow("/admin/admin.fxml");
                    }
                    else if (position.equals("operator")) {
                        loginButton.getScene().getWindow().hide();
                        setCurrentStation(login, password);
                        OpenWindow ow = new OpenWindow("/operator/operator.fxml");
                    }
            }
            else {
                Shake userLoginAnim = new Shake(loginField);
                userLoginAnim.playAnim();
                warningText.setText("Пользователь с таким логином не найден!");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setCurrentStation(String login, String password) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet res = dbHandler.getUser(login, password);

        try {
            res.next();
            String station = res.getString("station");
            SetCurrentStation.currentStation = station;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
