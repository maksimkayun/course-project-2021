package testPack;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import openWindow.OpenWindow;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exitButton;

    @FXML
    private TextField textField;

    @FXML
    private Button openButton;

    @FXML
    void initialize() {
        exitButton.setOnAction(actionEvent -> {
            exitButton.getScene().getWindow().hide();
            OpenWindow ow = new OpenWindow("/sample/main.fxml");
        });
        openButton.setOnAction(actionEvent -> {
            String url = textField.getText().trim();
            try {
                OpenWindow ow = new OpenWindow(url);
                textField.clear();
            } catch (Exception e) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/testPack/error.fxml"));

                try {
                    loader.load();
                } catch (IOException except) {
                    //except.printStackTrace();
                }

                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("FX UI System");
                stage.show();
            }
        });
    }
}
