package tables;

import configs.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lists.Order;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TableOrdersController {
    private ObservableList<Order> orderObservableList = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Order> view;

    @FXML
    private TableColumn<Order, Integer> idColumn;

    @FXML
    private TableColumn<Order, String> startStationColumn;

    @FXML
    private TableColumn<Order, String> finishStationColumn;

    @FXML
    private TableColumn<Order, String> nameColumn;

    @FXML
    private TableColumn<Order, Integer> numberTrainColumn;

    @FXML
    void initialize() {
        initData();

        // устанавливаем тип и значение которое должно хранится в колонке
        idColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("name"));
        startStationColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("startStation"));
        finishStationColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("finishStation"));
        numberTrainColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("numberTrain"));

        // заполняем таблицу данными
        view.setItems(orderObservableList);
    }

    private void initData() {
        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet res = dbHandler.selectAllOrders();
        try {
            while (res.next()) {
                orderObservableList.add(new Order(res.getInt("id"), res.getString("name"),
                        res.getString("start_station"), res.getString("finish_station"),
                        res.getInt("number_train")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
