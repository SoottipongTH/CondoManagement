package condo.controller;

import condo.model.Person;
import condo.service.AdminFileDataSource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;


public class AdminLoginController {
    @FXML private Button loginBut,backBut;
    @FXML private PasswordField adminPassword;
    @FXML private TextField adminUser;
    private AdminFileDataSource adminFileDataSource;
    private Person admin;
    private String username,password;
    @FXML
    public void initialize(){
        adminFileDataSource = new AdminFileDataSource("data","admin.csv");
    }
    @FXML
    public void handleButtonOnAction(ActionEvent event)throws IOException{
        if(event.getSource() == loginBut){
            username = adminUser.getText();
            password = adminPassword.getText();
            admin = adminFileDataSource.checkAccount(username,password);
            if(admin != null) {
                Button log = (Button) event.getSource();
                Stage stage = (Stage) log.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/fxml/admin_page.fxml"));
                stage.setScene(new Scene(loader.load(), 640, 480));
                AdminPageController dx = loader.getController();
                dx.set(admin);
                stage.show();
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

