package condo.controller;

import condo.model.Person;
import condo.model.PersonList;
import condo.service.AdminFileDataSource;
import condo.service.FileDataSource;
import condo.service.StaffFileDataSource;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class AdminPageController {
    @FXML private Pane loginTablePane,changePasswordPane,createAccountPane;
    @FXML private Button logoutBut,createBut,changeBut,loginTableBut,createAccountBut,changePasswordBut;
    @FXML private TextField staffName,staffUser;
    @FXML private Label adminLabel;
    @FXML private PasswordField staffPassword;
    @FXML private TableView<Person> loginTable;
    @FXML private PasswordField curPass,newPass,conPass;
    @FXML private TableColumn<Person,String> usernameCol,nameCol,dateCol,timeCol;

    private String newPassword,conPassword,curPassword;
    private String name,username,password;
    private AdminFileDataSource adminFileDataSource;
    private FileDataSource staffFileDataSource;
    private Person admin;
    private ObservableList<Person> loginObservableList;
    private PersonList staffList;
    private Person staffAccount;
    public void initialize(){
        Platform.runLater(() ->{
            adminLabel.setText("WELCOME " + admin.getName().toUpperCase());
            loginTable.setPlaceholder(new Label("NO USER LOGIN"));
            adminFileDataSource = new AdminFileDataSource("data","admin.csv");
            staffFileDataSource = new StaffFileDataSource("data","staff.csv");
            staffList = ((StaffFileDataSource)staffFileDataSource).showStaff();
            timeTable();
        });
    }
    @FXML
    public void handleButtonOnAction(ActionEvent event)throws IOException{
        if(event.getSource() == logoutBut) {
            Button out = (Button) event.getSource();
            Stage stage = (Stage) out.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/menu.fxml"));

            stage.setScene(new Scene(loader.load(), 640, 480));
            loader.getController();
            stage.show();
        }
        if(event.getSource() == createAccountBut){
            createAccountPane.toFront();
            loginTable.sort();
            clear();
        }
        if(event.getSource() == loginTableBut){
            loginTablePane.toFront();
            clear();
        }
        if(event.getSource() == changePasswordBut){
            changePasswordPane.toFront();
            clear();
        }
        if(event.getSource() == changeBut){
            curPassword = curPass.getText();
            newPassword = newPass.getText();
            conPassword = conPass.getText();
            if(adminFileDataSource.changePassword(admin,newPassword,curPassword,conPassword)){
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
        if(event.getSource() == createBut){
            name = staffName.getText();
            username = staffUser.getText();
            password = staffPassword.getText();
            staffAccount = new Person(name,username,password,"-","-");
            if((staffFileDataSource).create(staffAccount)) {
                loginObservableList.add(new Person(name, username, password, "-", "-"));
                loginTable.sort();
                loginTable.refresh();
            }
            clear();
        }
    }

    private void timeTable(){
        loginObservableList = FXCollections.observableList(staffList.toList());
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        usernameCol.setStyle("-fx-alignment : Center");
        nameCol.setStyle("-fx-alignment : Center");
        dateCol.setStyle("-fx-alignment : Center");
        timeCol.setStyle("-fx-alignment : Center");
        loginTable.setItems(loginObservableList);
        loginTable.getSortOrder().add(dateCol);
        loginTable.getSortOrder().add(timeCol);
        loginTable.sort();

    }
    private void clear(){
        staffName.clear();
        staffUser.clear();
        staffPassword.clear();
        curPass.clear();
        conPass.clear();
        newPass.clear();
    }

    public void set(Person admin) {
        this.admin = admin;
    }
}
