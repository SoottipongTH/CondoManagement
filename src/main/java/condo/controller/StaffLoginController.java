package condo.controller;

import condo.model.Person;
import condo.service.StaffFileDataSource;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class StaffLoginController {
    @FXML private Button loginBut,backBut;
    @FXML private TextField officerUser;
    @FXML private PasswordField officerPassword;
    private StaffFileDataSource staffFileDataSource;
    private Person staff;
    private String username,password;
    @FXML public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                staffFileDataSource = new StaffFileDataSource("data", "staff.csv");
            }
        });
    }

    @FXML
    public void handleButtonOnAction(ActionEvent event)throws IOException{
        if(event.getSource() == loginBut) {
            username = officerUser.getText();
            password = officerPassword.getText();
            staff = staffFileDataSource.checkAccount(username,password);
            if(staff != null) {
                staffFileDataSource.setLoginTime(staff);
                Button log = (Button) event.getSource();
                Stage stage = (Stage) log.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/fxml/staff_page.fxml"));

                stage.setScene(new Scene(loader.load(), 640, 480));
                StaffPageController dx = loader.getController();
                dx.set(staff);
                stage.show();
            }
            else{

            }
        }
        if(event.getSource() == backBut){
            Button back = (Button) event.getSource();
            Stage stage = (Stage) back.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/menu.fxml"));

            stage.setScene(new Scene(loader.load(), 640, 480));
            loader.getController();
            stage.show();
        }
    }
}
