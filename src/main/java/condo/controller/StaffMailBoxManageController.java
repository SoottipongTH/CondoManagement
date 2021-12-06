package condo.controller;

import com.sun.deploy.panel.ExceptionListDialog;
import condo.model.*;
import condo.service.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.InputMethodTextRun;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

public class StaffMailBoxManageController {
    @FXML private Button backBut,logoutBut,viewInfoBut,addMailBut,mailboxBut,addBut,removeBut;
    @FXML private ComboBox<String> typeBox,sizeBox,importanceBox,companyBox;
    @FXML private TableView<Item> mailListTable;
    @FXML private TextField receiverName,senderName,roomPin,trackNum;
    @FXML private Pane addmailPane,mailboxPane;
    @FXML private TableColumn<Item,String> receiverCol,senderCol,staffCol,dateCol,typeCol,roomCol,timeCol;



    private String type,receiver,room,sender,importance,company,tracking,size,curTime,curDate;
    private BuildingList buildingList;
    private BuildingFileDataSource buildingFileDataSource;
    private ObservableList<Item> itemObservableList;
    private ItemFileDataSource itemFileDataSource;
    private ItemList itemList;
    private ObservableList<String> typeList,importanceList,companyList;
    private Item selectedItem,mail;
    private Person staff;


    @FXML
    public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                buildingFileDataSource = new BuildingFileDataSource("data","room.csv");
                itemFileDataSource = new ItemFileDataSource("data","item.csv");
                buildingList = buildingFileDataSource.showRoomList();
                mailListTable.setPlaceholder(new Label("NO MAIL IN BOX"));
                typeList = FXCollections.observableArrayList("DOCUMENT","MAIL","PARCEL");
                importanceList = FXCollections.observableArrayList("Urgent","Normal","Confidential");
                companyList = FXCollections.observableArrayList("THAI POST","KERRY","FAST TRACK");
                typeBox.getItems().addAll(typeList);
                importanceBox.getItems().addAll(importanceList);
                companyBox.getItems().addAll(companyList);
                itemList = itemFileDataSource.showItem();
                setDisable();
                mailTable();
                mailListTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        selectedItem = newValue;
                    }
                });
            }
        });

    }

    @FXML
    public void handleButtonOnAction(ActionEvent event) throws IOException {
        if (event.getSource() == backBut) {
            Button out = (Button) event.getSource();
            Stage stage = (Stage) out.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/staff_page.fxml"));

            stage.setScene(new Scene(loader.load(), 640, 480));
            StaffPageController staffpage = loader.getController();
            staffpage.set(staff);
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
        if(event.getSource() == addMailBut){
            addmailPane.toFront();
        }
        if(event.getSource() == mailboxBut){
            mailboxPane.toFront();
        }
        if(event.getSource() == addBut) {
            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            curTime = time.format(timeFormatter);
            curDate = date.format(dateFormatter);
            receiver = receiverName.getText();
            sender = senderName.getText();
            room = roomPin.getText();
            if (!receiver.isEmpty() && !sender.isEmpty() && !room.isEmpty() ) {
                try {
                    if (type.equals("MAIL")) {
                        itemFileDataSource.createItem(receiver, room, sender, size, type, staff.getName(), "-", "-", "-",curDate,curTime, buildingList);
                        refreshMailTable(type);
                        mailboxPane.toFront();
                        mailListTable.sort();
                    }
                    if (type.equals("DOCUMENT")) {
                        if(!importance.isEmpty()) {
                            itemFileDataSource.createItem(receiver, room, sender, size, type, staff.getName(), "-", "-", importance, curDate,curTime, buildingList);
                            refreshMailTable(type);
                            mailboxPane.toFront();
                            mailListTable.sort();
                        }
                    }
                    if (type.equals("PARCEL")) {
                        tracking = trackNum.getText();
                        if(!company.isEmpty() && !tracking.isEmpty()) {
                            itemFileDataSource.createItem(receiver, room, sender, size, type, staff.getName(), company, tracking, "-", curDate,curTime, buildingList);
                            refreshMailTable(type);
                            mailboxPane.toFront();
                            mailListTable.sort();
                        }
                    }
                }catch (NullPointerException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "PLEASE FILL ALL THE BLANKS", ButtonType.OK);
                    alert.setTitle("ERROR");
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setHeaderText(null);
                    alert.show();
                    clearFill();
                    setDisable();
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "PLEASE FILL ALL BLANKS", ButtonType.OK);
                alert.setTitle("ERROR");
                alert.initStyle(StageStyle.UTILITY);
                alert.setHeaderText(null);
                alert.show();
            }
            clearFill();
            setDisable();
        }

        if(event.getSource() == removeBut){
            if(selectedItem!=null) {
                removeMail();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "PLEASE SELECT A MAIL", ButtonType.OK);
                alert.setTitle("ERROR");
                alert.initStyle(StageStyle.UTILITY);
                alert.setHeaderText(null);
                alert.show();
            }

        }
        if(event.getSource() == viewInfoBut){
            try{
                showSelectedItem(selectedItem);
            }catch (NullPointerException e){
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "PLEASE SELECT A MAIL", ButtonType.OK);
                alert.setTitle("ERROR");
                alert.initStyle(StageStyle.UTILITY);
                alert.setHeaderText(null);
                alert.show();
            }

        }

    }
    private void refreshRemove(){
        mailListTable.getItems().remove(selectedItem);
        clearSelectItem();
        mailListTable.refresh();
        mailListTable.getSelectionModel().clearSelection();
    }
    public void refreshMailTable(String s){
        if(!buildingList.checkDuplicateRoom(room)){
            if(s.equals("MAIL")){
                mail = new Item(receiver, room, sender, size, type, staff.getName(), "-", "-","-",curDate,curTime);
            }
            if(s.equals("DOCUMENT")){
                mail = new Item(receiver, room, sender, size, type, staff.getName(), "-", "-",importance,curDate,curTime);
            }
            if(s.equals("PARCEL")){
                mail = new Item(receiver, room, sender, size, type, staff.getName(), company, tracking,"-",curDate,curTime);
            }
            itemObservableList.add(mail);
            mailListTable.refresh();
        }
    }

    private void clearSelectItem() {
        selectedItem = null;
    }
    @FXML
    public void setMailType(){
        if(!typeBox.getSelectionModel().isEmpty()) {
            type = typeBox.getValue();
                addSize();
                if (type.equals("DOCUMENT")) {
                    importanceBox.setDisable(false);
                    companyBox.setDisable(true);
                    trackNum.clear();
                    trackNum.setDisable(true);
                    sizeBox.setDisable(false);
                }
                if (type.equals("MAIL")) {
                    importanceBox.setDisable(true);
                    companyBox.setDisable(true);
                    trackNum.clear();
                    trackNum.setDisable(true);
                    sizeBox.setDisable(false);
                }
                if (type.equals("PARCEL")) {
                    importanceBox.setDisable(true);
                    companyBox.setDisable(false);
                    trackNum.setDisable(false);
                    sizeBox.setDisable(false);
                }
        }
    }
    @FXML
    public void setImportance(){
        if(!importanceBox.getSelectionModel().isEmpty()) {
            importance = importanceBox.getValue();
        }
    }
    @FXML
    public void setCompany(){
        if(!companyBox.getSelectionModel().isEmpty()) {
            company = companyBox.getValue();
        }
    }

    @FXML
    public void setSize(){
        size = sizeBox.getValue();
    }
    private void addSize(){
        sizeBox.getItems().clear();
        if(type.equals("DOCUMENT")){
            sizeBox.getItems().addAll("A2","A3","A4");
        }
        if(type.equals("MAIL")){
            sizeBox.getItems().addAll("A4","A8");
        }
        if(type.equals("PARCEL")){
            sizeBox.getItems().addAll("SMALL","MEDIUM","LARGE","EXTRA");
        }
    }

    public void mailTable(){
        itemObservableList = FXCollections.observableList(itemList.toList());
        senderCol.setCellValueFactory(new PropertyValueFactory<>("senderName"));
        receiverCol.setCellValueFactory(new PropertyValueFactory<>("receiverName"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        staffCol.setCellValueFactory(new PropertyValueFactory<>("offName"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        roomCol.setCellValueFactory(new PropertyValueFactory<>("roomPin"));
        receiverCol.setStyle("-fx-alignment : Center");
        senderCol.setStyle("-fx-alignment : Center");
        typeCol.setStyle("-fx-alignment : Center");
        dateCol.setStyle("-fx-alignment : Center");
        timeCol.setStyle("-fx-alignment : Center");
        staffCol.setStyle("-fx-alignment : Center");
        roomCol.setStyle("-fx-alignment : Center");
        mailListTable.setItems(itemObservableList);
        mailListTable.getSortOrder().add(dateCol);
        mailListTable.getSortOrder().add(timeCol);
        mailListTable.sort();
    }
    private void setDisable(){
        sizeBox.setDisable(true);
        companyBox.setDisable(true);
        importanceBox.setDisable(true);
        trackNum.setDisable(true);
    }
    private void clearFill(){
        receiverName.clear();
        senderName.clear();
        roomPin.clear();
        typeBox.setValue(null);
        sizeBox.getItems().clear();
        companyBox.setValue(null);
        importanceBox.setValue(null);
        trackNum.clear();
    }
    private void removeMail() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("MAIL DELETION");
        alert.setHeaderText(null);
        alert.setContentText("DO YOU WANT TO DELETE?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            try {
                itemFileDataSource.removeItem(selectedItem);
                refreshRemove();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void showSelectedItem(Item items){
        Dialog<String> dialog = new Dialog<>();
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        if(items.getType().equals("MAIL")){
            dialog.setTitle("MAIL INFORMATION");
            dialog.setContentText(
                    "Receiver Name: " + items.getReceiverName() +
                    "\nSender Name: " + items.getSenderName() +
                    "\nRoom: " + items.getRoomPin() +
                    "\nType: " + items.getType() +
                    "\nSize: " + items.getSize() +
                    "\nDate and Time added: " + items.getDate() +"," + items.getTime() +
                    "\nAdded by: " + items.getOffName());
            stage.show();
        }
        if(items.getType().equals("PARCEL")){
            dialog.setTitle("PARCEL INFORMATION");
            dialog.setContentText(
                    "Receiver Name: " + items.getReceiverName() +
                            "\nSender Name: " + items.getSenderName() +
                            "\nRoom: " + items.getRoomPin() +
                            "\nType: " + items.getType() +
                            "\nSize: " + items.getSize() +
                            "\nCompany: " + items.getCompany() +
                            "\nTracking Number: " + items.getTrackNum() +
                            "\nDate and Time added: " + items.getDate() +"," + items.getTime() +
                            "\nAdded by: " + items.getOffName());
            stage.show();
        }
        if(items.getType().equals("DOCUMENT")){
            dialog.setTitle("DOCUMENT INFORMATION");
            dialog.setContentText(
                    "Receiver Name: " + items.getReceiverName() +
                            "\nSender Name: " + items.getSenderName() +
                            "\nRoom: " + items.getRoomPin() +
                            "\nType: " + items.getType() +
                            "\nSize: " + items.getSize() +
                            "\nImportance: " + items.getImportance() +
                            "\nDate and Time added: " + items.getDate() +"," + items.getTime() +
                            "\nAdded by: " + items.getOffName());
            stage.show();
        }
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);

    }



    public void set(Person staff) {
        this.staff = staff;
    }
}
