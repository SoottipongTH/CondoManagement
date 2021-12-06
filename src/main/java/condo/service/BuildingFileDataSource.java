package condo.service;

import condo.model.Building;
import condo.model.BuildingList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

import java.io.*;

public  class BuildingFileDataSource implements FileDataSource {
    private String fileDirectoryName;
    private String fileName;
    private BuildingList roomList;
    private Building room;
    private String res2;
    private boolean checkRoom;


    public BuildingFileDataSource(String fileDirectoryName, String fileName) {
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
            Building room = new Building(data[0],data[1],data[2],data[3]);
            roomList.addRoom(room);
        }
        reader.close();
    }

    @Override
    public boolean create(Object object) throws IOException {
        room = (Building) object;
        roomList = new BuildingList();
        if(room.getRoomPin().isEmpty() || room.getResName1().isEmpty() || room.getRoomType().isEmpty()){
            alertBox("empty");
            return false;
        }
        readData();
        String filePath = fileDirectoryName + File.separator + fileName;
        File file = new File(filePath);
        FileWriter fileWriter = new FileWriter(file,true);
        BufferedWriter writer = new BufferedWriter(fileWriter);
        String line;
        res2 = room.getResName2();
        if(res2.isEmpty()){
            res2 = "---";
        }
        checkRoom = roomList.checkDuplicateRoom(room.getRoomPin());
        if (checkRoom){
            line = room.getRoomType() + "," + room.getRoomPin() + "," + room.getResName1() + "," + res2;
            writer.append(line);
            writer.newLine();
            writer.close();
            alertBox("createSuccess");
            return true;
        }
        alertBox("exist");
        return false;
    }

    public BuildingList showRoomList() {
        try {
            roomList = new BuildingList();
            readData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return roomList;
    }

    private void alertBox(String type){
        if(type.equals("exist")){
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "THIS ROOM ALREADY EXISTED", ButtonType.OK);
            alert.setTitle("ERROR");
            alert.initStyle(StageStyle.UTILITY);
            alert.setHeaderText(null);
            alert.show();
        }
        if(type.equals("empty")){
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "PLEASE FILL ALL BLANKS", ButtonType.OK);
            alert.setTitle("ERROR");
            alert.initStyle(StageStyle.UTILITY);
            alert.setHeaderText(null);
            alert.show();
        }
        if(type.equals("error")){
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Cannot change password check conditions below\n - The previous password cannot be used. \n - New and Confirm password must be match \n - Do not enter spaces", ButtonType.OK);
            alert.setTitle("ERROR");
            alert.initStyle(StageStyle.UTILITY);
            alert.setHeaderText(null);
            alert.show();
        }
        if(type.equals("createSuccess")){
            Alert alert = new Alert(Alert.AlertType.WARNING,
                              "SUCCESSFUL CREATE A ROOM", ButtonType.OK);
            alert.setTitle("SUCCESS");
            alert.initStyle(StageStyle.UTILITY);
            alert.setHeaderText(null);
            alert.show();
        }
    }

}
