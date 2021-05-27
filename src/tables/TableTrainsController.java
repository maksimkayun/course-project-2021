package tables;

import configs.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lists.Train;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TableTrainsController {
    private ObservableList<Train> trainObservableList = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Train> view;

    @FXML
    private TableColumn<Train, Integer> idColumn;

    @FXML
    private TableColumn<Train, String> nameStartStationColumn;

    @FXML
    private TableColumn<Train, String> nameFinishStation;

    @FXML
    void initialize() {
        initData();

        // устанавливаем тип и значение которое должно хранится в колонке
        idColumn.setCellValueFactory(new PropertyValueFactory<Train, Integer>("id"));
        nameStartStationColumn.setCellValueFactory(new PropertyValueFactory<Train, String>("startStation"));
        nameFinishStation.setCellValueFactory(new PropertyValueFactory<Train, String>("finishStation"));

        // заполняем таблицу данными
        view.setItems(trainObservableList);
    }
    private void initData() {
        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet res = dbHandler.selectAllTrains();
        try {
            while (res.next()) {
                trainObservableList.add(new Train(res.getInt("id"),
                        res.getString("start_station"),
                        res.getString("finish_station")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
