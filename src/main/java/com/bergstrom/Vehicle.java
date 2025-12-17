package com.bergstrom;

public class Vehicle extends Item {



    public Vehicle(String name, int price) {
        super(name,price);
    }

    public int getVehiclePrice() {
        return price;
    }

    @Override
    public String toString() {
        return super.toString() + " Fordonets namn: " + name + " Fordonets uthyrningspris " + price + " kr.";
    }
    @Override
    public int PricePolicyStudent(int price, Member member) {
        boolean studentPrice = member.hasStudent();
        if (studentPrice) {
            int studentDiscount = price * 30 / 100;
            price = price - studentDiscount;
            return price;
        }
        else
            return price;
    }
}
