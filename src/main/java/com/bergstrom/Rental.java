package com.bergstrom;

public class Rental {
    private Member member;
    private Item item;
    private boolean currentlyRented = true;

    public Rental(Member member, Item item) {
        this.member = member;
        this.item = item;

    }


    public Member getMember() {
        return member;
    }

    public Item getItem () {
        return item;
    }

    public boolean getCurrentlyRented() {
        return currentlyRented;
    }

    public void returnRental() {
        this.currentlyRented = false;
        item.setRented(false);
    }
}
