package com.bergstrom;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    public static ObservableList<Item> inventory = FXCollections.observableArrayList();




    public static void addItem(Item item) {
        inventory.add(item);
    }


    public static Item findById(int id) {
            for(Item i: inventory) {
            if (i.getItemId() == id) {
                return i;
          }
       }

       return null;
     }

    public static ObservableList<Item> getInventory() {

        return inventory;
    }
}
