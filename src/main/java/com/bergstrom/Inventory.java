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


    //public static void showCurrentItems() {
    //    for (Item i: allItems) {
    //         if(!i.hasRented() ) {
    //             System.out.println(i);
    //        }

    //    }

    // }
    // public static void showAllItems() {
    //   for (Item i: allItems) {
    //     System.out.println(i + (i.hasRented() ? " - Uthyrd ":" - Ledig "));

    //}

    //}
    // public static void showToolsOrVehicles(int option) {
    //  switch(option) {
    //    case 1:
    //  for (Item i: allItems) {
    //  if(i instanceof Vehicle) {
    //      System.out.println(i);
    // }

    // }
    // break;
    // case 2:
    //   for(Item i: allItems) {
    //      if(i instanceof Tool) {
    //        System.out.println(i);
    //    }
    // }
    // break;
    // default:
    //    System.out.println("Fel!");

    // }

    // }

    // public static Item findById(int id) {
    //     for(Item i: allItems) {
    //        if (i.getItemId() == id) {
    //            return i;
    //       }
    //   }

    //   return null;
    // }
//
    //private static ObservableList<Item> inventory = FXCollections.observableArrayList();

    //public static void addToInventory(Item item) {
    //    inventory.add(item);
    // }
    public static ObservableList<Item> getInventory() {
        return inventory;
    }
}
