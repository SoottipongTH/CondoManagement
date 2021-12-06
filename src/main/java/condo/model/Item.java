package condo.model;

import javafx.beans.property.SimpleStringProperty;

public class Item {
    private SimpleStringProperty receiverName;
    private SimpleStringProperty roomPin;
    private SimpleStringProperty senderName;
    private SimpleStringProperty size;
    private SimpleStringProperty type;
    private SimpleStringProperty offName;
    private SimpleStringProperty company;
    private SimpleStringProperty trackNum;
    private SimpleStringProperty importance;
    private SimpleStringProperty date;
    private SimpleStringProperty time;

    public Item(String receiverName, String roomPin, String senderName, String size, String type, String offName, String company, String trackNum, String importance, String date,String time) {
        this.receiverName = new SimpleStringProperty(receiverName);
        this.roomPin = new SimpleStringProperty(roomPin);
        this.senderName = new SimpleStringProperty(senderName);
        this.size = new SimpleStringProperty(size);
        this.type = new SimpleStringProperty(type);
        this.company = new SimpleStringProperty(company);
        this.importance = new SimpleStringProperty(importance);
        this.trackNum = new SimpleStringProperty(trackNum);
        this.offName = new SimpleStringProperty(offName);
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);
    }
    @Override
    public boolean equals(Object selectedItem) {
        if(this == selectedItem){
            return true;
        }
        if(selectedItem == null || getClass() != selectedItem.getClass()){
            return false;
        }
        Item item = (Item) selectedItem;
        return getOffName().equals(item.getOffName())&&
                getReceiverName().equals(item.getReceiverName())&&
                getSenderName().equals(item.getSenderName())&&
                getSize().equals(item.getSize())&&
                getDate().equals(item.getDate())&&
                getTime().equals(item.getTime()) &&
                getType().equals(item.getType())&&
                getImportance().equals(item.getImportance())&&
                getCompany().equals(item.getCompany())&&
                getRoomPin().equals(item.getRoomPin());
    }


    public String getReceiverName() {
        return receiverName.get();
    }
    public String getOffName() {
        return offName.get();
    }
    public String getCompany() {
        return company.get();
    }
    public String getTrackNum() {
        return trackNum.get();
    }

    public String getImportance() {
        return importance.get();
    }
    public String getRoomPin() {
        return roomPin.get();
    }

    public String getDate() {
        return date.get();
    }

    public String getTime() {
        return time.get();
    }

    public String getSenderName() {
        return senderName.get();
    }
    public String getSize() {
        return size.get();
    }
    public String getType() {
        return type.get();
    }

}
