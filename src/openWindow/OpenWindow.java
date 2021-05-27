package openWindow;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/* @@@ Это класс для быстрого открытия окна, принимает ссылку url в формате "/sample/main.fxml" @@@ */

public class OpenWindow {

    public OpenWindow(String url) {
        this.open(url);
    }
    public void open(String url) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(url));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle("FX UI System");
        stage.show();
    }
}
