package condo.controller;


import condo.model.Person;
import condo.service.FileDataSource;
import condo.service.StaffFileDataSource;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class StaffPageController {
    @FXML private Button mailManageBut,roomManageBut,logoutBut,changePasswordBut,changeBut;
    @FXML private Pane changePasswordPane;
    @FXML private Label staffNameLabel;
    @FXML private PasswordField curPass,newPass,conPass;
    private Person staff;
    private String curPassword,newPassword,conPassword;
    private FileDataSource staffFileDataSource;

    public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                staffNameLabel.setText("WELCOME BACK " + staff.getName().toUpperCase());
                staffFileDataSource = new StaffFileDataSource("data","staff.csv");
            }
        });

    }

    @FXML
    public void handleButtonOnAction(ActionEvent event)throws IOException {
        if (event.getSource() == mailManageBut) {
            changePasswordPane.setVisible(false);
            Button out = (Button) event.getSource();
            Stage stage = (Stage) out.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/staff_mailbox_manage.fxml"));
            stage.setScene(new Scene(loader.load(), 640, 480));
            StaffMailBoxManageController mailManage = loader.getController();
            mailManage.set(staff);
            stage.show();
        }
        if (event.getSource() == roomManageBut) {
            changePasswordPane.setVisible(false);
            Button out = (Button) event.getSource();
            Stage stage = (Stage) out.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/staff_room_manage.fxml"));

            stage.setScene(new Scene(loader.load(), 640, 480));
            StaffRoomManageController roomManage = loader.getController();
            roomManage.set(staff);
            stage.show();
        }
        if (event.getSource() == logoutBut) {
            Button out = (Button) event.getSource();
            Stage stage = (Stage) out.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/menu.fxml"));

            stage.setScene(new Scene(loader.load(), 640, 480));
            loader.getController();
            stage.show();
        }
        if(event.getSource() == changePasswordBut){
            curPassword = curPass.getText();
            newPassword = newPass.getText();
            conPassword = conPass.getText();
            if(((StaffFileDataSource)staffFileDataSource).changePassword(staff,newPassword,curPassword,conPassword)){
                Button out = (Button) event.getSource();
                Stage stage = (Stage) out.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/fxml/menu.fxml"));
                stage.setScene(new Scene(loader.load(), 640, 480));
                loader.getController();
                stage.show();
            }
            clear();
        }
    }
    private void clear(){
        curPass.clear();
        conPass.clear();
        newPass.clear();
    }

    public void set(Person staff) {
        this.staff = staff;
    }
}
