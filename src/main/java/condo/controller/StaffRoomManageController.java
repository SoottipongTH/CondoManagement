package condo.controller;

import condo.model.Building;
import condo.model.BuildingList;
import condo.model.Person;
import condo.service.BuildingFileDataSource;
import condo.service.FileDataSource;
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
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;

public class StaffRoomManageController {
    @FXML private Pane roomCreatePane,roomTablePane;
    @FXML private Button backBut,roomListBut,roomCreateBut,logoutBut,createBut;
    @FXML private ObservableList<Building> buildingObservableList;
    @FXML private ComboBox<String> buildingNameBox,floorBox,roomTypeBox,roomNumberBox;
    @FXML private TextField residentName1Field,residentName2Field;
    @FXML private Label nameLabel1,nameLabel2;
    @FXML private TableView<Building> roomTable;
    @FXML private TableColumn<Building,String> roomCol,typeCol,name1Col,name2Col;

    private BuildingList condoList;
    private String roomNumber,roomTypes,roomPin,floor, buildingName,resNames1,resNames2;
    private FileDataSource roomFileDataSource;
    private Person staff;
    private Building room;
    private ObservableList<String> floorList,buildingNameList,roomTypeList,oneBedList,twoBedList;

    @FXML
    public void initialize(){
        Platform.runLater(()->{
            buildingNameList = FXCollections.observableArrayList("A","B");
            floorList = FXCollections.observableArrayList("1","2","3","4","5","6","7","8");
            roomTypeList = FXCollections.observableArrayList("One Bed","Two Bed");
            oneBedList = FXCollections.observableArrayList("01", "02", "03", "04", "05", "06", "07", "08");
            twoBedList = FXCollections.observableArrayList("09","10");
            buildingNameBox.getItems().addAll(buildingNameList);
            floorBox.getItems().addAll(floorList);
            roomTypeBox.getItems().addAll(roomTypeList);
            roomFileDataSource = new BuildingFileDataSource("data","room.csv");
            condoList = ((BuildingFileDataSource)roomFileDataSource).showRoomList();
            setDisable();
            roomTable();
        });
    }
    @FXML
    public void handleButtonOnAction(ActionEvent event) throws IOException {
        if(event.getSource() == logoutBut){
            Button out = (Button) event.getSource();
            Stage stage = (Stage) out.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/menu.fxml"));
            stage.setScene(new Scene(loader.load(), 640, 480));
            loader.getController();
            stage.show();
        }
        if(event.getSource() == backBut){
            Button out = (Button) event.getSource();
            Stage stage = (Stage) out.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/staff_page.fxml"));

            stage.setScene(new Scene(loader.load(), 640, 480));
            StaffPageController staffpage = loader.getController();
            staffpage.set(staff);
            stage.show();
        }
        if(event.getSource() == roomCreateBut){
            roomCreatePane.toFront();
            clearAllAddRoomBox();
            setDisable();
        }
        if(event.getSource() == roomListBut){
            roomTablePane.toFront();
            clearAllAddRoomBox();
            setDisable();
        }
        if(event.getSource() == createBut) {
            resNames1 = residentName1Field.getText();
            resNames2 = residentName2Field.getText();
            room = new Building(roomTypes, roomPin, resNames1, resNames2);
            try {
                if (roomFileDataSource.create(room)) {
                refreshRoomTable();
                clearAllAddRoomBox();
                roomTablePane.toFront();
                }
                else{
                    clearAllAddRoomBox();
                }
                setDisable();
            }catch (NullPointerException e){
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "PLEASE FILL ALL BLANKS", ButtonType.OK);
                alert.setTitle("ERROR");
                alert.initStyle(StageStyle.UTILITY);
                alert.setHeaderText(null);
                alert.show();
                clearAllAddRoomBox();
                setDisable();
            }

        }
    }

    public void roomTable(){
        buildingObservableList = FXCollections.observableList(condoList.toList());
        roomCol.setCellValueFactory(new PropertyValueFactory<>("roomPin"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        name1Col.setCellValueFactory(new PropertyValueFactory<>("resName1"));
        name2Col.setCellValueFactory(new PropertyValueFactory<>("resName2"));
        roomCol.setStyle("-fx-alignment : Center");
        typeCol.setStyle("-fx-alignment : Center");
        name2Col.setStyle("-fx-alignment : Center");
        name1Col.setStyle("-fx-alignment : Center");
        roomTable.setItems(buildingObservableList);
    }
    public void refreshRoomTable() {
        if(resNames2.isEmpty()){
            resNames2 = "---";
        }
        buildingObservableList.add(new Building(roomTypes,roomPin,resNames1,resNames2));
        roomTable.refresh();
    }

    @FXML
    public void setBuildingName(ActionEvent event) {
        boolean check = buildingNameBox.getSelectionModel().isEmpty();
        if(!check){
            buildingName = buildingNameBox.getValue();
            floorBox.setDisable(false);
        }
    }

    @FXML
    public void setFloor(ActionEvent event){
        boolean check = floorBox.getSelectionModel().isEmpty();
        if(!check){
            floor = floorBox.getValue();
            roomTypeBox.setDisable(false);
        }
    }
    @FXML
    public void setRoomType(ActionEvent event){
        boolean check = roomTypeBox.getSelectionModel().isEmpty();
        roomTypes = roomTypeBox.getValue();
        if (!check){
            addRoom();
            roomNumberBox.setDisable(false);
            if (roomTypes.equals("One Bed")) {
                nameLabel1.setDisable(false);
                nameLabel2.setDisable(true);
                residentName1Field.setDisable(false);
                residentName2Field.setDisable(true);
            }
            if (roomTypes.equals("Two Bed")) {
                nameLabel1.setDisable(false);
                nameLabel2.setDisable(false);
                residentName1Field.setDisable(false);
                residentName2Field.setDisable(false);
            }
        }

    }
    private void setDisable(){
        floorBox.setDisable(true);
        roomTypeBox.setDisable(true);
        roomNumberBox.setDisable(true);
        residentName2Field.setDisable(true);
        residentName1Field.setDisable(true);
        nameLabel1.setDisable(true);
        nameLabel2.setDisable(true);
    }
    private void addRoom() {
        roomNumberBox.getItems().clear();
        if (roomTypes.equals("One Bed")) {
            roomNumberBox.getItems().addAll(oneBedList);
        }
        ;
        if (roomTypes.equals("Two Bed")) {
            roomNumberBox.getItems().addAll(twoBedList);
        }
    }
    @FXML
    public void setRoom(ActionEvent event){
        if(!roomNumberBox.getSelectionModel().isEmpty()) {
            roomNumber = roomNumberBox.getValue();
        }
        roomPin = buildingName + floor + roomNumber;
    }

    private void clearAllAddRoomBox(){
        buildingNameBox.setValue(null);
        floorBox.setValue(null);
        roomTypeBox.setValue(null);
        roomNumberBox.getItems().clear();
        residentName1Field.clear();
        residentName2Field.clear();
    }


    public void set(Person staff){
        this.staff = staff;
    }
}
