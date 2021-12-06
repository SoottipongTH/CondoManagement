package condo.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class MenuController {
    @FXML private Button adminBut,officerBut,creditBut;

    @FXML
    public void initialize(){
    }

    @FXML public void handleButtonOnAction(Event event) throws IOException {

        if(event.getSource() == adminBut ) {
                 Button admin = (Button ) event.getSource();
                 Stage stage = (Stage) admin.getScene().getWindow();
                 FXMLLoader loader = new FXMLLoader(
                         getClass().getResource("/fxml/admin_login.fxml"));

                 stage.setScene(new Scene(loader.load(), 640, 480));
                 loader.getController();
                 stage.show();
        }
        if(event.getSource() == officerBut ) {
                Button off = (Button) event.getSource();
                Stage stage = (Stage) off.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/fxml/staff_login.fxml"));

                stage.setScene(new Scene(loader.load(), 640, 480));
                loader.getController();
                stage.show();
        }
        if(event.getSource() == creditBut ) {
                Button credit = (Button) event.getSource();
                Stage stage = (Stage) credit.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/fxml/credit.fxml"));

                stage.setScene(new Scene(loader.load(), 640, 480));
                loader.getController();
                stage.show();
        }
    }


}
