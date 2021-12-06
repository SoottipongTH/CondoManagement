package condo.model;

import java.util.ArrayList;

public class PersonList {
    private ArrayList<Person> persons;

    public PersonList() {
        this.persons = new ArrayList<>();
    }
    public Person checkAccount(String username, String password) throws RuntimeException {
        for (Person a : persons) {
            if (a.getPassword().equals(password) && a.getUsername().equals(username)) {
                return a;
            }
        }
        return null;
    }

    public void add(Person person) {
        persons.add(person);
    }
    public ArrayList<Person> toList() {
        return persons;
    }
}
