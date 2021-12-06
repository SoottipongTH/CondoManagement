package condo.model;

import java.util.ArrayList;

public class ItemList {
        private ArrayList<Item> itemList;

        public ItemList() {
            this.itemList = new ArrayList<>();
        }

        public void addMail(Item item) {
            itemList.add(item);
        }
        public ArrayList<Item> toList() {
            return itemList;
        }

}
