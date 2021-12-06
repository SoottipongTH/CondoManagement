package condo.service;

import condo.model.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ItemFileDataSource{
    private String fileDirectoryName;
    private String fileName;
    private ItemList itemList;
    private Item item;

    public ItemFileDataSource(String fileDirectoryName, String fileName) {
        this.fileDirectoryName = fileDirectoryName;
        this.fileName = fileName;
        checkFileIsExisted();
    }

    private void checkFileIsExisted() {
        File file = new File(fileDirectoryName);
        if (!file.exists()) {
            file.mkdirs();
        }
        String filePath = fileDirectoryName + File.separator + fileName;
        file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Cannot create " + filePath);
            }
        }
    }

    private void readData() throws IOException {
        String filePath = fileDirectoryName + File.separator + fileName;
        File file = new File(filePath);
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            Item mail = new Item(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7], data[8], data[9], data[10]);
            itemList.addMail(mail);
        }
        reader.close();
    }


    public void createItem(String name, String roomPin, String senderName, String size, String type, String offName, String company,
                           String trackNum, String importance, String date, String times, BuildingList buildingList) throws IOException {
        String filePath = fileDirectoryName + File.separator + fileName;
        File file = new File(filePath);
        FileWriter fileWriter = new FileWriter(file, true);
        BufferedWriter writer = new BufferedWriter(fileWriter);
        String line;
        try {
            if (buildingList.checkMail(name, roomPin)) {
                alertBox("addMailSuccess");
                line = name + ","
                        + roomPin + ","
                        + senderName + ","
                        + size + ","
                        + type + ","
                        + offName + ","
                        + company + ","
                        + trackNum + ","
                        + importance + ","
                        + date + ","
                        + times;
                writer.append(line);
                writer.newLine();
                writer.close();
            } else {
                alertBox("error");
            }
        } catch (IOException e) {
            System.err.println("Cannot write " + filePath);
        }
    }

    public void removeItem(Item selectedItem) throws IOException {
        boolean check = false;
        itemList = new ItemList();
        readData();
        String filePath = fileDirectoryName + File.separator + fileName;
        File file = new File(filePath);
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(fileWriter);
        String line;
        for (Item x : itemList.toList()) {
            if (x.equals(selectedItem) && !check) {
                check = true;
                continue;
            } else {
                line = x.getReceiverName() + ","
                        + x.getRoomPin() + ","
                        + x.getSenderName() + ","
                        + x.getSize() + ","
                        + x.getType() + ","
                        + x.getOffName() + ","
                        + x.getCompany() + ","
                        + x.getTrackNum() + ","
                        + x.getImportance() + ","
                        + x.getDate() + ","
                        + x.getTime();
                writer.append(line);
                writer.newLine();
            }
        }
        writer.close();
    }

    public ItemList showItem() {
        try {
            itemList = new ItemList();
            readData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return itemList;
    }

    private void alertBox(String type) {
        if (type.equals("addMailSuccess")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "SUCCESSFULLY ADD MAIL", ButtonType.OK);
            alert.setTitle("SUCCESS");
            alert.initStyle(StageStyle.UTILITY);
            alert.setHeaderText(null);
            alert.show();
        }
        if (type.equals("empty")) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "PLEASE FILL ALL BLANKS", ButtonType.OK);
            alert.setTitle("ERROR");
            alert.initStyle(StageStyle.UTILITY);
            alert.setHeaderText(null);
            alert.show();
        }
        if (type.equals("error")) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "CANNOT PUT YOUR MAIL TO MAILBOX DUE TO CONDITION BELOW\n - ROOM PIN IS NOT CORRECT OR ROOM IS NOT CREATE \n - RECEIVER NAME IS NOT MATCH ", ButtonType.OK);
            alert.setTitle("ERROR");
            alert.initStyle(StageStyle.UTILITY);
            alert.setHeaderText(null);
            alert.show();
        }
    }


}
