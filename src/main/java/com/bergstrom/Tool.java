package com.bergstrom;

public class Tool extends Item {
    //public String toolName;
    //public int toolPrice;


    public Tool (String name, int price) {
        super(name, price);
    }


    public int getToolPrice() {
        return price;
    }


    @Override
    public String toString() {
        return super.toString() + " Verktygets namn: " + name + " Verktygets uthyrningspris " +
                price + " kr.";    }

    @Override
    public int PricePolicyStudent(int price, Member member) {
        boolean studentPrice = member.hasStudent();
        if (studentPrice) {
            int studentDiscount = price * 15 / 100;
            price = price - studentDiscount;
            return price;
        }
        else
            return price;
    }
}
