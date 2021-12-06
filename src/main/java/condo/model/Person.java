package condo.model;

import javafx.beans.property.SimpleStringProperty;

public class Person {
    private SimpleStringProperty name;
    private SimpleStringProperty username;
    private SimpleStringProperty password;
    private SimpleStringProperty date;
    private SimpleStringProperty time;

    public Person(String name, String username, String password, String date, String time) {
        this.name = new SimpleStringProperty(name);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);
    }

    public String getName() {
        return name.get();
    }

    public String getUsername() {
        return username.get();
    }

    public String getPassword() {
        return password.get();
    }

    public String getDate() {
        return date.get();
    }

    public String getTime() {
        return time.get();
    }



}
