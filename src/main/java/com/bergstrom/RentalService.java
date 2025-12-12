package com.bergstrom;

import java.util.ArrayList;
import java.util.List;

public class RentalService {
    static List<Rental> allRentals = new ArrayList<>();


    public static void rentItem(Member member, Item item) {
        if (!item.isRented()) {
            Rental r = new Rental(member, item);
            allRentals.add(r);
            item.setRented(true);
            member.addRental(r);

            int price = 0;
            if (item instanceof Tool) {
                price = ((Tool) item).getToolPrice();

            }
            else if (item instanceof Vehicle) {
                price = ((Vehicle) item).getVehiclePrice();
            }
            int newPrice = item.PricePolicyStudent(price, member);
            member.addHistory(newPrice);
        }
        else {
            System.out.println("Fel, redan uthyrd.");
        }
    }

    public static void returnItem(Member member,int itemId) {
        boolean rentBool= false;
        for (Rental r: allRentals) {

            if (r.getMember().equals(member) && r.getItem().getItemId() == itemId && r.getCurrentlyRented()) {
                r.returnRental();
                System.out.println("Klart");
                rentBool= true;
                break;

            }

        }
        if(rentBool == false) {
            System.out.println("Fel! Föremålet är inte uthyrt.");
        }


    }
    public static String getRentalStatus(Item i) {
        return i.isRented() ? "Ja" : "Nej";
    }
}
