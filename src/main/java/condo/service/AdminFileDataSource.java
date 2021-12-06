package condo.service;

import condo.model.Person;
import condo.model.PersonList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

import java.io.*;

public class AdminFileDataSource{
    private String fileDirectoryName;
    private String fileName;
    private PersonList adminList;
    private Person admin;


    public AdminFileDataSource(String fileDirectoryName, String fileName) {
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

    public boolean changePassword(Object acc, String newPassword, String curPassword, String conPassword) throws IOException {
        admin = (Person) acc;
        adminList = new PersonList();
        readData();
        if(newPassword.isEmpty() || conPassword.isEmpty() || conPassword.isEmpty() || newPassword.equals(admin.getPassword()) || !conPassword.equals(newPassword) || !curPassword.equals(admin.getPassword())){
            alertBox("changeError");
            return false;
        }
        String filePath = fileDirectoryName + File.separator + fileName;
        File file = new File(filePath);
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(fileWriter);
        String line;
        for(Person x : adminList.toList()) {
            if (x.getUsername().equals(admin.getUsername())&&x.getPassword().equals(curPassword)&&newPassword.equals(conPassword)) {
                line = x.getName() + "," + x.getUsername() + "," + newPassword + "," + x.getDate() + "," + x.getTime();
            }else {
                line = x.getName() + "," + x.getUsername() + "," + x.getPassword() + "," + x.getDate() + "," + x.getTime();
            }
            writer.append(line);
            writer.newLine();
        }
        writer.close();
        alertBox("changeSuccess");
        return true;
    }


    public Person checkAccount(String username, String password) throws IOException {
        adminList = new PersonList();
        readData();
        admin = adminList.checkAccount(username,password);
        if (admin != null) {
            alertBox("loginSuccess");
            return admin;
        }
        alertBox("loginError");
        return null;
    }
    private void readData() throws IOException {
        String filePath = fileDirectoryName + File.separator + fileName;
        File file = new File(filePath);
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            Person admin = new Person(data[0],data[1],data[2],data[3],data[4]);
            adminList.add(admin);
        }
        reader.close();
    }
    private void alertBox(String type){
        if(type.equals("changeError")){
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Cannot change password check conditions below\n - The previous password cannot be used. \n - New and Confirm password must be match \n - Do not enter spaces \n - Enter wrong previous password ", ButtonType.OK);
            alert.setTitle("ERROR");
            alert.initStyle(StageStyle.UTILITY);
            alert.setHeaderText(null);
            alert.show();
        }
        if(type.equals("loginError")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "WRONG USERNAME OR PASSWORD", ButtonType.OK);
            alert.setTitle("CANNOT LOGIN");
            alert.initStyle(StageStyle.UTILITY);
            alert.setHeaderText(null);
            alert.show();
        }
        if(type.equals("loginSuccess")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "SUCCESSFULLY LOGIN", ButtonType.OK);
            alert.setTitle("SUCCESS");
            alert.initStyle(StageStyle.UTILITY);
            alert.setHeaderText(null);
            alert.show();
        }
        if(type.equals("changeSuccess")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "CHANGE PASSWORD SUCCESS PLEASE LOGIN WITH NEW PASSWORD", ButtonType.OK);
            alert.setTitle("SUCCESS");
            alert.initStyle(StageStyle.UTILITY);
            alert.setHeaderText(null);
            alert.show();
        }
    }

}
