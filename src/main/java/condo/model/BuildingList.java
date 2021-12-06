package condo.model;

import java.util.ArrayList;

public class BuildingList {
    private ArrayList<Building> buildings;
    public BuildingList(){
        this.buildings = new ArrayList<>();
    }

    public boolean checkDuplicateRoom(String roomPin) throws RuntimeException {
        for (Building a : buildings) {
            if (a.getRoomPin().equals(roomPin)) {
                return false;
            }
        }
       return true;
    }

    public boolean checkMail(String name, String roomPin) throws RuntimeException {
        for (Building a: buildings){
            if (a.getRoomPin().equals(roomPin)){
                if (a.getResName1().equals(name)||a.getResName2().equals(name)){
                    return true;
                }
            }
        }
        return false;
    }


    public void addRoom(Building room) {
        buildings.add(room);
    }
    public ArrayList<Building> toList() {
        return buildings;
    }


}

