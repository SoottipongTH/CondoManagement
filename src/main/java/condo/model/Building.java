package condo.model;

import javafx.beans.property.SimpleStringProperty;

public class Building {
    private SimpleStringProperty roomPin;
    private SimpleStringProperty roomType;
    private SimpleStringProperty resName1;
    private SimpleStringProperty resName2;

    public Building(String roomType, String roomPin, String resName1, String resName2) {
        this.roomType = new SimpleStringProperty(roomType);
        this.roomPin = new SimpleStringProperty(roomPin);
        this.resName1 = new SimpleStringProperty(resName1);
        this.resName2 = new SimpleStringProperty(resName2);
    }

    public String getResName1() {
        return resName1.get();
    }
    public String getResName2() {
        return resName2.get();
    }
    public String getRoomType(){
        return  roomType.get();
    }
    public String getRoomPin() {
        return roomPin.get();
    }
}
