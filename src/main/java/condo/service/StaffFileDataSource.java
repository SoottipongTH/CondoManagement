package condo.service;

import condo.model.Person;
import condo.model.PersonList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class StaffFileDataSource implements FileDataSource {
    private String fileDirectoryName;
    private String fileName,curTime,curDate;
    private PersonList staffList;
    private Person staff;

    public StaffFileDataSource(String fileDirectoryName, String fileName) {
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
            staff = (Person) acc;
            staffList = new PersonList();
            readData();
            if(newPassword.isEmpty() || conPassword.isEmpty() || conPassword.isEmpty() || newPassword.equals(staff.getPassword()) || !conPassword.equals(newPassword) || !curPassword.equals(staff.getPassword())){
                alertBox("changeError");
                return false;
            }
            String filePath = fileDirectoryName + File.separator + fileName;
            File file = new File(filePath);
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            String line;
            for(Person x : staffList.toList()) {
                if (x.getUsername().equals(staff.getUsername())&&x.getPassword().equals(curPassword)&&newPassword.equals(conPassword)) {
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

    @Override
    public boolean create(Object object) throws IOException {
        staff = (Person) object;
        staffList = new PersonList();
        if (staff.getName().isEmpty() || staff.getUsername().isEmpty() || staff.getPassword().isEmpty()) {
            alertBox("empty");
            return false;
        }
        readData();
        String filePath = fileDirectoryName + File.separator + fileName;
        File file = new File(filePath);
        FileWriter fileWriter = new FileWriter(file, true);
        BufferedWriter writer = new BufferedWriter(fileWriter);
        String line;
        for (Person x : staffList.toList()) {
            if (x.getName().equals(staff.getName()) && x.getUsername().equals(staff.getUsername())) {
                alertBox("exist");
                return false;
            }
        }
        line = staff.getName() + "," + staff.getUsername() + "," + staff.getPassword() + "," + staff.getDate() + "," + staff.getTime();
        writer.append(line);
        writer.newLine();
        writer.close();
        alertBox("createSuccess");
        return true;
    }


    public Person checkAccount(String username, String password) throws IOException {
        staffList = new PersonList();
        readData();
        staff = staffList.checkAccount(username,password);
        if (staff != null) {
                alertBox("loginSuccess");
                return staff;
        }
        alertBox("loginError");
        return null;
    }


    public void setLoginTime(Person person) throws IOException {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        curTime = time.format(timeFormatter);
        curDate = date.format(dateFormatter);
        String filePath = fileDirectoryName + File.separator + fileName;
        File file = new File(filePath);
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(fileWriter);
        try {
            for (Person x: staffList.toList()) {
                if(x.getName().equals(person.getName()) && x.getUsername().equals(person.getUsername())) {
                    String line = x.getName() + ","
                            + x.getUsername() + ","
                            + x.getPassword() + ","
                            + curDate +  ","
                            + curTime;
                    writer.append(line);
                    writer.newLine();
                }else{
                    String line = x.getName() + ","
                            + x.getUsername() + ","
                            + x.getPassword() + ","
                            + x.getDate() + ","
                            + x.getTime();
                    writer.append(line);
                    writer.newLine();

                }
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Cannot write " + filePath);
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
            Person person = new Person(data[0],data[1],data[2],data[3],data[4]);
            staffList.add(person);
        }
        reader.close();
    }
    public PersonList showStaff() {
        try {
            staffList = new PersonList();
            readData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return staffList;
    }
    private void alertBox(String type){
        if(type.equals("exist")){
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "THIS USERNAME ALREADY EXISTED", ButtonType.OK);
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
        if(type.equals("loginError")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "WRONG USERNAME OR PASSWORD", ButtonType.OK);
            alert.setTitle("ERROR");
            alert.initStyle(StageStyle.UTILITY);
            alert.setHeaderText(null);
            alert.show();
        }
        if(type.equals("changeError")){
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Cannot change password check conditions below\n - The previous password cannot be used. \n - New and Confirm password must be match \n - Do not enter spaces \n - Enter wrong previous password ", ButtonType.OK);
            alert.setTitle("ERROR");
            alert.initStyle(StageStyle.UTILITY);
            alert.setHeaderText(null);
            alert.show();
        }
        if(type.equals("createSuccess")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "SUCCESSFUL", ButtonType.OK);
            alert.setTitle("SUCCESS");
            alert.initStyle(StageStyle.UTILITY);
            alert.setHeaderText(null);
            alert.show();
        }
        if(type.equals("loginSuccess")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "SUCCESSFUL LOGIN", ButtonType.OK);
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
