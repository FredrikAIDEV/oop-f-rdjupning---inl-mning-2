package com.bergstrom;

import com.bergstrom.Member;
import exception.InvalidMemberDataException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MemberRegistry {
    public static List<Member> allMembers = new ArrayList<>();


    public static void addMember(Member member) {

        allMembers.add(member);
    }

    public static Member findMemberId(int id) throws InvalidMemberDataException{

        for(Member m: allMembers) {
            if (m.getId() == id) {
                return m;
            }
        }
        throw new InvalidMemberDataException("Ingen medlem finns på detta id: " + id);
    }
    public static void writeMember(String filePath) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
        for (Member m : allMembers) {
            writer.write(m.getId() + "," + m.getName() + "," + m.hasStudent() + "," + m.getHistory());
            writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } ;
    }
    public static void readMember(String filePath){
        allMembers.clear();
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                boolean student = Boolean.parseBoolean(parts[2]);
                int history = Integer.parseInt(parts[3]);
                try {
                    Member m = new Member(id, name, student, history);
                    allMembers.add(m);
                } catch (InvalidMemberDataException e){
                    System.err.println("Fel medlemsdata" + e.getMessage());
                }
            }
        }
         catch (IOException e) {
            throw new RuntimeException("Kan inte läsa filen", e);
        }
    }

}
