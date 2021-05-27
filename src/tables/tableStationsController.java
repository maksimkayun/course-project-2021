package tables;

import configs.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lists.Station;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class tableStationsController {
    private ObservableList<Station> stationObservableList = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Station> view;

    @FXML
    private TableColumn<Station, Integer> idColumn;

    @FXML
    private TableColumn<Station, String> nameStationColumn;

    @FXML
    void initialize() {
        initData();

        // устанавливаем тип и значение которое должно хранится в колонке
        idColumn.setCellValueFactory(new PropertyValueFactory<Station, Integer>("id"));
        nameStationColumn.setCellValueFactory(new PropertyValueFactory<Station, String>("name"));

        // заполняем таблицу данными
        view.setItems(stationObservableList);
    }
    private void initData() {
        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet res = dbHandler.selectAllStations();
        try {
            while (res.next()) {
                stationObservableList.add(new Station(res.getInt("id"),
                        res.getString("station_name")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
