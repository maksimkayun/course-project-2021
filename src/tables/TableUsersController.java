package tables;

import configs.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lists.User;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TableUsersController {
    private ObservableList<User> userObservableList = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<User> view;

    @FXML
    private TableColumn<User, Integer> idColumn;

    @FXML
    private TableColumn<User, String> loginColumn;

    @FXML
    private TableColumn<User, String> passwordColumn;

    @FXML
    private TableColumn<User, String> stationColumn;

    @FXML
    void initialize() {
        initData();

        // устанавливаем тип и значение которое должно хранится в колонке
        idColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<User, String>("login"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        stationColumn.setCellValueFactory(new PropertyValueFactory<User, String>("nameStation"));

        // заполняем таблицу данными
        view.setItems(userObservableList);
    }
    private void initData() {
        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet res = dbHandler.selectAllUsers();
        try {
            while (res.next()) {
                userObservableList.add(new User(res.getInt("id"), res.getString("login"),
                        res.getString("password"), res.getString("position"),
                        res.getString("station")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
