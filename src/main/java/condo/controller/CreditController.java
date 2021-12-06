package condo.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class CreditController {
    @FXML private Button closeBut;

    @FXML
    public void handleButtonOnAction(Event event) throws IOException {
        if(event.getSource() == closeBut){
            Button close = (Button) event.getSource();
            Stage stage = (Stage) close.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/menu.fxml"));

            stage.setScene(new Scene(loader.load(), 640, 480));
            loader.getController();
            stage.show();
        }

    }
}
