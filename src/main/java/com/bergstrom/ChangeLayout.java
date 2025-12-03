package com.bergstrom;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChangeLayout {
    /*
    public static void addMemberMenu(Stage window) {

        Label addMemberLabel = new Label("Lägg till medlem");


        VBox layoutAddMember = new VBox(20);
        layoutAddMember.setPadding(new Insets(20,20,20,20));
        layoutAddMember.getChildren().addAll(addMemberLabel);
        Scene scene = new Scene(layoutAddMember, 500, 500);
        window.setScene(scene);
        window.show();
*/
    private static BorderPane getRoot() {
        return Main.getInstance().getRoot();
    }
    public static void showMainMenu(){
        getRoot().setCenter(Menus.mainMenu());
    }
    public static void showAddMemberMenu() {
        getRoot().setCenter(Menus.addMemberMenu());
    }
    public static void showSearchMemberMenu() {
        getRoot().setCenter(Menus.searchMemberMenu());
    }
    public static void showChangeMemberMenu() {
        getRoot().setCenter(Menus.changeMemberMenu());
    }
    public static void showListItemMenu() {
        getRoot().setCenter(Menus.listItemMenu());
    }
    public static void showAddItemMenu() {
        getRoot().setCenter(Menus.addItemMenu());
    }
    public static void showfilterMenu() {
        getRoot().setCenter(Menus.filterMenu());
    }
    public static void showStartRentalMenu() {
        getRoot().setCenter(Menus.startRentalMenu());
    }
    public static void showStopRentalMenu() {
        getRoot().setCenter(Menus.stopRentalMenu());
    }
    public static void showTotalRevenueMenu() {
        getRoot().setCenter(Menus.stopRentalMenu());
    }

}
