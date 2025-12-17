package com.bergstrom;

import java.io.*;

import static com.bergstrom.Inventory.inventory;


public abstract class Item implements PricePolicy {
    private static int itemIdCounter = 1;

    private final int itemId;
    private boolean rented;

    protected String name;
    protected int price;


    public Item(String name, int price) {
        this.itemId = itemIdCounter++;
        this.name = name;
        this.price = price;
        Inventory.addItem(this);

    }
    public int getItemId() {
        return itemId;
    }
    public boolean isRented() {
        return rented;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }


    @Override
    public abstract int PricePolicyStudent(int price, Member member);


    @Override
    public String toString() {
        return "Objektets ID: " + itemId;
    }

    public static void writeItem(String filePath) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
            for (Item i: inventory) {
                writer.write( i.getClass().getSimpleName() + "," + i.getName() + ", " + i.getPrice());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void readItem(String filePath) {
        Inventory.getInventory().clear();
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                String type = parts[0].trim();
                String name = parts[1].trim();
                int price = Integer.parseInt(parts[2].trim());

                Item i = null;
                switch(type) {
                    case "Tool":
                        i = new Tool(name, price);
                        break;
                    case "Vehicle":
                        i = new Vehicle(name, price);
                }
            }
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
