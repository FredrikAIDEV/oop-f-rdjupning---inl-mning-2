package com.bergstrom;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class Menus {


    public static VBox mainMenu() {
        Label intro = new Label("Välkommen till Fredriks rentalservice\n" +
                "Välj mellan detta:");
        Button addMemberButton = new Button("Lägg till medlem");
        Button searchMemberButton = new Button("Sök medlem");
        Button changeMemberButton = new Button("Ändra medlem");
        Button listItemButton = new Button("Se en lista på alla uthyrningsföremål (verktyg och fordon)");
        Button addItemButton = new Button("Lägg till föremål");
        Button filterButton = new Button("Filtrera föremål");
        Button startRentalButton = new Button("Starta uthyrning");
        Button stopRentalButton = new Button("Avsluta uthyrning");
        Button totalRevenueButton = new Button("Summera intäkter");
        Button quitButton = new Button("Avsluta");

        addMemberButton.setOnAction(e -> ChangeLayout.showAddMemberMenu());
        searchMemberButton.setOnAction(e -> ChangeLayout.showSearchMemberMenu());
        changeMemberButton.setOnAction(e -> ChangeLayout.showChangeMemberMenu());
        listItemButton.setOnAction(e -> ChangeLayout.showListItemMenu());
        addItemButton.setOnAction(e -> ChangeLayout.showAddItemMenu());
        filterButton.setOnAction(e-> ChangeLayout.showfilterMenu());
        startRentalButton.setOnAction(e -> ChangeLayout.showStartRentalMenu());
        stopRentalButton.setOnAction(e -> ChangeLayout.showStopRentalMenu());
        totalRevenueButton.setOnAction(e -> ChangeLayout.showTotalRevenueMenu());

        VBox mainMenu = new VBox(20, new Label("Välkommen till Fredriks rentalservice\n" +
                "Välj mellan detta:"));
        mainMenu.setPadding(new Insets(20,20,20,20));
        mainMenu.getChildren().addAll(intro,addMemberButton,searchMemberButton,changeMemberButton,listItemButton,addItemButton,filterButton,startRentalButton,stopRentalButton,totalRevenueButton,quitButton);
        return mainMenu;
    }
    public static VBox addMemberMenu() {
        Label addMemberLabel = new Label("Lägg till medlem");
        TextField regNameField = new TextField();
        regNameField.setPromptText("Ange ditt namn");
        Label studentLabel = new Label("Är du student? (Skriv 'ja') Studenter har 15 % rabatt på verktyg och 30 % rabatt på fordon!");
        CheckBox boxStudent = new CheckBox("Ja");
        CheckBox boxEjStudent = new CheckBox("Nej");

        Button backButton = new Button("Backa");
        backButton.setOnAction(e -> ChangeLayout.showMainMenu());

        VBox addMemberBox = new VBox(20, addMemberLabel,regNameField,studentLabel,boxStudent,boxEjStudent,backButton);
        addMemberBox.setPadding(new Insets(20));
        return addMemberBox;

    }
    public static VBox searchMemberMenu() {
        Label searchMemberLabel = new Label("Sök medlem");
        TextField searchMemberField = new TextField();
        searchMemberField.setPromptText("Ange namnet du vill söka på");

        Button backButton = new Button("Backa");
        backButton.setOnAction(e -> ChangeLayout.showMainMenu());

        VBox searchMemberBox = new VBox(20, searchMemberLabel,searchMemberField, backButton);
        searchMemberBox.setPadding(new Insets(20));
        return searchMemberBox;
    }
    public static VBox changeMemberMenu() {
        Label changeMemberLabel = new Label("Ändra medlem");
        TextField changeMemberField = new TextField();
        changeMemberField.setPromptText("Ange ID på medlemmen som du vill ändra");
        ChoiceBox<String> nameOrStudent = new ChoiceBox<>();
        nameOrStudent.getItems().addAll("Namn","Studentstatus");
        Button confirmChoiceButton = new Button("Välj");

        Button backButton = new Button("Backa");
        backButton.setOnAction(e -> ChangeLayout.showMainMenu());

        VBox changeMemberBox = new VBox(20,changeMemberLabel,changeMemberField,nameOrStudent,confirmChoiceButton,backButton);
        changeMemberBox.setPadding(new Insets(20));
        return changeMemberBox;
    }
    public static VBox listItemMenu() {
        Label listItemLabel = new Label("Lista på alla uthyrningsföremål (verktyg och fordon)");

        //TABLEVIEW?

        Button backButton = new Button("Backa");
        backButton.setOnAction(e -> ChangeLayout.showMainMenu());

        VBox listItemBox = new VBox(20,listItemLabel,backButton);
        listItemBox.setPadding(new Insets(20));
        return listItemBox;
    }
    public static VBox addItemMenu() {
        Label addItemLabel = new Label("Lägg till föremål");

        Button backButton = new Button("Backa");
        backButton.setOnAction(e -> ChangeLayout.showMainMenu());

        VBox addItemBox = new VBox(20, addItemLabel,backButton);
        addItemBox.setPadding(new Insets(20));
        return addItemBox;
    }
    public static VBox filterMenu() {
        Label filterLabel = new Label("Filtrera föremål, Välj om du vill se fordon eller verktyg");
        CheckBox toolBox = new CheckBox("Verktyg");
        CheckBox vehicleBox = new CheckBox("Fordon");

        Button backButton = new Button("Backa");
        backButton.setOnAction(e -> ChangeLayout.showMainMenu());

        VBox filterBox = new VBox(20, filterLabel,toolBox,vehicleBox,backButton);
        filterBox.setPadding(new Insets(20));
        return filterBox;

    }
    public static VBox startRentalMenu() {
        Label startRentalLabel = new Label("Starta uthyrning");
        TextField memberIdField = new TextField();
        memberIdField.setPromptText("Ange ditt medlems-ID");
        TextField itemIdField = new TextField();
        itemIdField.setPromptText("Vilket föremål vill du hyra? ange ID");

        Button backButton = new Button("Backa");
        backButton.setOnAction(e -> ChangeLayout.showMainMenu());

        VBox startRentalBox = new VBox(20,startRentalLabel,memberIdField,itemIdField,backButton);
        startRentalBox.setPadding(new Insets(20));
        return startRentalBox;
    }
    public static VBox stopRentalMenu() {
        Label stopRentalLabel = new Label("Avsluta uthyrning");
        TextField memberIdField = new TextField();
        memberIdField.setPromptText("Ange ditt medlems-ID");
        TextField itemIdField = new TextField();
        itemIdField.setPromptText("Vilket föremål vill du läman tillbaka? ange ID");

        Button backButton = new Button("Backa");
        backButton.setOnAction(e -> ChangeLayout.showMainMenu());

        VBox stopRentalBox = new VBox(20,stopRentalLabel,memberIdField,itemIdField,backButton);
        stopRentalBox.setPadding(new Insets(20));
        return stopRentalBox;
    }
    public static VBox totalRevenueMenu() {
        Label totalRevenueLabel = new Label("Alla intäkter");

        Button backButton = new Button("Backa");
        backButton.setOnAction(e -> ChangeLayout.showMainMenu());

        VBox totalRevenueBox = new VBox(20,totalRevenueLabel,backButton);
        totalRevenueBox.setPadding(new Insets(20));
        return totalRevenueBox;
    }
}
