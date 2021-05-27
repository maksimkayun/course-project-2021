package admin;

import configs.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import openWindow.OpenWindow;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageStatController {


    @FXML
    private Button exitButton;

    @FXML
    private TextField nameStatField;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button showTable;

    @FXML
    private Text warningText;

    @FXML
    void initialize() {
        exitButton.setOnAction(actionEvent -> {
            exitButton.getScene().getWindow().hide();
            OpenWindow ow = new OpenWindow("/admin/admin.fxml");
        });

        DatabaseHandler dbHandler = new DatabaseHandler();
        addButton.setOnAction(actionEvent -> {
            String name = nameStatField.getText().trim();

            if (!name.equals("") && !findMatches(name)) {
                dbHandler.addStation(name);
                nameStatField.clear();
                warningText.setText("");
            }
            else if (findMatches(name)) {
                warningText.setText("Такая станция уже есть!");
                nameStatField.clear();
            }
            else {
                warningText.setText("Все поля должны быть заполнены!");
            }
        });

        deleteButton.setOnAction(actionEvent -> {
            String name = nameStatField.getText().trim();
            if (!name.equals("") && findMatches(name)) {
                dbHandler.deleteStation(name);
                nameStatField.clear();
                warningText.setText("");
            }
            else if (!findMatches(name)) {
                warningText.setText("Такой станции нет!");
                nameStatField.clear();
            }
            else {
                warningText.setText("Все поля должны быть заполнены!");
            }
        });

        showTable.setOnAction(actionEvent -> {
            OpenWindow ow = new OpenWindow("/tables/tableStations.fxml");
        });
    }

    public static boolean findMatches(String name) {
        boolean result = false;
        DatabaseHandler dbHandler = new DatabaseHandler();
        int counter = 0;
        ResultSet res = dbHandler.getStation(name);
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
