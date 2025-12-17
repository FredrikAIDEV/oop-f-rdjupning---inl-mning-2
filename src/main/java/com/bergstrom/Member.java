package com.bergstrom;

import exception.InvalidMemberDataException;

import java.util.ArrayList;
import java.util.List;

public class Member {
    public static int idCounter =1;

    private final int id;
    public String name;
    public boolean student;
    public int history = 0;
    private List<Rental> memberRentals = new ArrayList<>();


    public Member(String name, boolean student) throws InvalidMemberDataException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidMemberDataException("Namnet får inte vara tomt!");
        }

        this.id = idCounter++;
        this.name = name;
        this.student = student;
        MemberRegistry.addMember(this);
}
    public Member(int id, String name, boolean student, int history) throws InvalidMemberDataException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidMemberDataException("Namnet får inte vara tomt!");
        }
        this.id = id;
        this.name = name;
        this.student = student;
        this.history = history;

        if (id >= idCounter) {
        idCounter = id + 1;
            }
        }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public boolean hasStudent() {
        return student;
    }

    public void setStatus(boolean student) {
        this.student = student;
    }
    public int getHistory() {
        return history;
    }

    public void addHistory(int amount) {
        this.history += amount;
    }

    public void addRental(Rental r) {
        memberRentals.add(r);
    }

}

