package com.bergstrom;

import exception.InvalidMemberDataException;

import java.util.ArrayList;
import java.util.List;

import static com.bergstrom.MemberRegistry.readMember;

public class MembershipService {
    public static List memberSearch(String key) {

        MemberRegistry.readMember("members.txt");

        List<Member> matches = new ArrayList<>();
        for (Member m: MemberRegistry.allMembers) {
            if (m.getName().toLowerCase().contains(key.toLowerCase())) {
                matches.add(m);
            }
        }
        return matches;

    }

    public static void memberUpdateName(int idChange, String newName) throws InvalidMemberDataException {
        Member m = MemberRegistry.findMemberId(idChange);
        if (m != null) {
            m.setName(newName);
            MemberRegistry.writeMember("members.txt");
            System.out.println("Klart");
        }
        else {
            System.out.println("Ingen träff");
        }


    }

    public static void memberUpdateStudent(int idChange, String newStudentChoice) throws InvalidMemberDataException {
        Boolean newStudentBool = true;
        if (newStudentChoice.equalsIgnoreCase("ja")) {
            newStudentBool = true;
        }
        else {
            newStudentBool = false;
        }
        Member m = MemberRegistry.findMemberId(idChange);
        if (m != null) {
            m.setStatus(newStudentBool);
            MemberRegistry.writeMember("members.txt");
            System.out.println("Klart");
        }
        else {
            System.out.println("Ingen träff");
        }
    }

    public static int totalMemberHistory() {
        int historySummary = 0;
        for (Member m : MemberRegistry.allMembers) {
            historySummary += m.getHistory();
        }
        //System.out.println("Alla medlemmars totala skuld är " + historySummary + " kr.");
        return historySummary;
    }

}
