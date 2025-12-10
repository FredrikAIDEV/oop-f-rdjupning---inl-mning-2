package com.bergstrom;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.util.List;

import static java.util.Arrays.setAll;

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
        quitButton.setOnAction(e -> {
            Platform.exit();
        });

        VBox mainMenu = new VBox(20);
        mainMenu.setPadding(new Insets(20,20,20,20));
        mainMenu.getChildren().addAll(intro,addMemberButton,searchMemberButton,changeMemberButton,listItemButton,addItemButton,filterButton,startRentalButton,stopRentalButton,totalRevenueButton,quitButton);
        return mainMenu;
    }
    public static VBox addMemberMenu() {
        //LÄGG TILL VALIDERING + förvalt alternativ
        Label addMemberLabel = new Label("Lägg till medlem");
        TextField regNameField = new TextField();
        regNameField.setPromptText("Ange ditt namn");

        Label studentLabel = new Label("Är du student? Studenter har 15 % rabatt på verktyg och 30 % rabatt på fordon!");
        RadioButton boxStudent = new RadioButton("Ja");
        RadioButton boxNoStudent = new RadioButton("Nej");
        ToggleGroup studentGroup = new ToggleGroup();
        boxStudent.setToggleGroup(studentGroup);
        boxNoStudent.setToggleGroup(studentGroup);

        Button confirmChoiceButton = new Button("Registrera");
        confirmChoiceButton.setOnAction(e -> {
            String name = regNameField.getText();
            boolean isStundet = boxStudent.isSelected();

            new Member(name,isStundet);
            MemberRegistry.writeMember("members.txt");
            System.out.println("Klart");
        });

        Button backButton = new Button("Backa");
        backButton.setOnAction(e -> ChangeLayout.showMainMenu());

        VBox addMemberBox = new VBox(20, addMemberLabel,regNameField,studentLabel,boxStudent,boxNoStudent,confirmChoiceButton,backButton);
        addMemberBox.setPadding(new Insets(20));
        return addMemberBox;

    }
    public static VBox searchMemberMenu() {
        Label searchMemberLabel = new Label("Sök medlem");
        TextField searchMemberField = new TextField();
        searchMemberField.setPromptText("Ange namnet du vill söka på");
        Button searchButton = new Button("Sök");
        TableView<Member> searchTable = new TableView<>();
        searchTable.setPrefHeight(200);

        TableColumn<Member, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Member, String> nameColumn = new TableColumn<>("Namn");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Member, String> studentColumn = new TableColumn<>("Student");
        studentColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().hasStudent() ? "Ja":"Nej"));
        TableColumn<Member, String> historyColumn = new TableColumn<>("Skuld");
        historyColumn.setCellValueFactory(cell ->
                new SimpleStringProperty()
        );
        searchTable.getColumns().addAll(idColumn,nameColumn,studentColumn,historyColumn);

        searchButton.setOnAction(e -> {
            String searchKey = searchMemberField.getText();
            List<Member> searchResult = MembershipService.memberSearch(searchKey);
            if (searchResult.isEmpty()){
                AlertBox.display("Ingen träff", "Det finns ingen medlem som heter så!");
            } else{
                searchTable.getItems().setAll(searchResult);
            }

        });



        Button backButton = new Button("Backa");
        backButton.setOnAction(e -> ChangeLayout.showMainMenu());

        VBox searchMemberBox = new VBox(20, searchMemberLabel,searchMemberField, searchButton,searchTable, backButton);
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

        VBox dynamicBox = new VBox(10);

        confirmChoiceButton.setOnAction(e -> {
            dynamicBox.getChildren().clear();

            String choice = nameOrStudent.getValue();
            switch (choice) {
                case "Namn":
                    TextField newNameField = new TextField();
                    newNameField.setPromptText("Ange Nytt namn");
                    Button confirmButton = new Button("Välj");
                    dynamicBox.getChildren().addAll(newNameField,confirmButton);
                    break;

                case "Studentstatus":
                    Label studentLabel = new Label("Är du student? Studenter har 15 % rabatt på verktyg och 30 % rabatt på fordon!");
                    RadioButton boxStudent = new RadioButton("Ja");
                    RadioButton boxNoStudent = new RadioButton("Nej");
                    ToggleGroup studentGroup = new ToggleGroup();
                    boxStudent.setToggleGroup(studentGroup);
                    boxNoStudent.setToggleGroup(studentGroup);
                    Button confirmButton2 = new Button("Välj");
                    dynamicBox.getChildren().addAll(studentLabel,boxStudent,boxNoStudent,confirmButton2);

            }

        });

        Button backButton = new Button("Backa");
        backButton.setOnAction(e -> ChangeLayout.showMainMenu());

        VBox changeMemberBox = new VBox(20,changeMemberLabel,changeMemberField,nameOrStudent,confirmChoiceButton,dynamicBox,backButton);
        changeMemberBox.setPadding(new Insets(20));
        return changeMemberBox;
    }
    public static VBox listItemMenu() {
        Label listItemLabel = new Label("Lista på alla uthyrningsföremål (verktyg och fordon)");

        TableView<Item> itemTable = new TableView<>();
        itemTable.setPrefHeight(200);

        TableColumn<Item, String> nameColumn = new TableColumn<>("Namn");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setPrefWidth(150);
        TableColumn<Item, Integer> priceColumn = new TableColumn<>("Pris");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumn.setPrefWidth(100);

        itemTable.setItems(Inventory.getInventory());

        itemTable.getColumns().addAll(nameColumn,priceColumn);

        Button backButton = new Button("Backa");
        backButton.setOnAction(e -> ChangeLayout.showMainMenu());

        VBox listItemBox = new VBox(20,listItemLabel, itemTable,backButton);
        listItemBox.setPadding(new Insets(20));
        return listItemBox;
    }
    public static VBox addItemMenu() {
        Label addItemLabel = new Label("Lägg till föremål");

        TextField regNameField = new TextField();
        regNameField.setPromptText("Ange namn på föremålet");
        TextField regPriceField = new TextField();
        regPriceField.setPromptText("Ange pris på föremålet");

        Label labelToolOrVehicle = new Label("Ange om föremålet är ett verktyg eller ett fordon");
        RadioButton boxTool = new RadioButton("Verktyg");
        RadioButton boxVehicle = new RadioButton("Fordon");
        ToggleGroup itemGroup = new ToggleGroup();
        boxTool.setToggleGroup(itemGroup);
        boxVehicle.setToggleGroup(itemGroup);
        Button confirmChoiceButton = new Button("Registrera");

        confirmChoiceButton.setOnAction(e -> {
            String name = regNameField.getText();
            int price = Integer.parseInt(regPriceField.getText());
            Item newItem = null;
            if(boxTool.isSelected()){

                newItem = new Tool(name,price);

            }else {

                newItem = new Vehicle(name,price);

            }
            if (newItem != null) {
                //Inventory.addItem(newItem);
                Item.writeItem("items.txt");
            }

        });


        Button backButton = new Button("Backa");
        backButton.setOnAction(e -> ChangeLayout.showMainMenu());

        VBox addItemBox = new VBox(20, addItemLabel,regNameField,regPriceField,labelToolOrVehicle,boxTool,boxVehicle,confirmChoiceButton,backButton);
        addItemBox.setPadding(new Insets(20));
        return addItemBox;
    }
    public static VBox filterMenu() {
        Label filterLabel = new Label("Filtrera föremål, Välj om du vill se fordon eller verktyg");
        RadioButton toolBox = new RadioButton("Verktyg");
        RadioButton vehicleBox = new RadioButton("Fordon");
        ToggleGroup itemGroup = new ToggleGroup();
        toolBox.setToggleGroup(itemGroup);
        vehicleBox.setToggleGroup(itemGroup);
        Button confirmChoiceButton = new Button("Välj");
        TableView itemTable = new TableView();
        itemTable.setPrefHeight(200);

        TableColumn<Item,String> nameColumn = new TableColumn<>("Namn");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setPrefWidth(150);

        confirmChoiceButton.setOnAction(e -> {
        ObservableList<Item> filteredList = FXCollections.observableArrayList();

        if(toolBox.isSelected()) {
            for (Item i : Inventory.getInventory()){
                if(i instanceof Tool) {
                    filteredList.add(i);
                }
            }
        }
        else {
            for (Item i: Inventory.getInventory()) {
                if(i instanceof Vehicle) {
                    filteredList.add(i);
                }
            }
        }
        itemTable.getColumns().clear();
        itemTable.getColumns().add(nameColumn);
        itemTable.setItems(filteredList);
        });

        Button backButton = new Button("Backa");
        backButton.setOnAction(e -> ChangeLayout.showMainMenu());

        VBox filterBox = new VBox(20, filterLabel,toolBox,vehicleBox,confirmChoiceButton,itemTable,backButton);
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
