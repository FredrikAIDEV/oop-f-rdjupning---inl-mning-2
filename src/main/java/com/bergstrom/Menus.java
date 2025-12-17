package com.bergstrom;

import exception.InvalidMemberDataException;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
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
        filterButton.setOnAction(e-> ChangeLayout.showFilterMenu());
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

        Label addMemberLabel = new Label("Lägg till medlem");
        TextField regNameField = new TextField();
        regNameField.setPromptText("Ange ditt namn");

        Label studentLabel = new Label("Är du student? Studenter har 15 % rabatt på verktyg och 30 % rabatt på fordon!");
        RadioButton boxStudent = new RadioButton("Ja");
        RadioButton boxNoStudent = new RadioButton("Nej");
        ToggleGroup studentGroup = new ToggleGroup();
        boxStudent.setToggleGroup(studentGroup);
        boxNoStudent.setToggleGroup(studentGroup);
        boxNoStudent.setSelected(true);

        Button confirmChoiceButton = new Button("Registrera");
        confirmChoiceButton.setOnAction(e -> {
            try{
            String name = regNameField.getText();
            boolean isStundet = boxStudent.isSelected();

            new Member(name,isStundet);
            MemberRegistry.writeMember("members.txt");
            AlertBox.display("Klart!", "Medlemmen är registrerad.");
            }
            catch(InvalidMemberDataException ex){
                AlertBox.display("fel", ex.getMessage());

            }

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
        Label changeMemberLabel = new Label("Ändra medlem - Ange ID");
        TextField changeMemberField = new TextField();
        changeMemberField.setPromptText("Ange ID på medlemmen som du vill ändra");
        ChoiceBox<String> nameOrStudent = new ChoiceBox<>();
        nameOrStudent.getItems().addAll("Namn","Studentstatus");
        nameOrStudent.setValue("Namn");
        Button confirmChoiceButton = new Button("Välj");

        VBox dynamicBox = new VBox(10);

        confirmChoiceButton.setOnAction(e -> {
            dynamicBox.getChildren().clear();
            try {
                String choice = nameOrStudent.getValue();
                int idChange = Integer.parseInt(changeMemberField.getText());
                switch (choice) {
                    case "Namn":
                        TextField newNameField = new TextField();
                        newNameField.setPromptText("Ange Nytt namn");
                        Button confirmButton = new Button("Välj");
                        confirmButton.setOnAction(ee -> {
                            try {
                                String newName = newNameField.getText();
                                MembershipService.memberUpdateName(idChange, newName);
                                AlertBox.display("Klart!", "Namnet är uppdaterat.");
                            } catch (InvalidMemberDataException ex) {
                                AlertBox.display("Fel", "Ingen medlem på detta ID: " + idChange);
                            }
                        });
                        dynamicBox.getChildren().addAll(newNameField, confirmButton);
                        break;

                    case "Studentstatus":
                        Label studentLabel = new Label("Är du student? Studenter har 15 % rabatt på verktyg och 30 % rabatt på fordon!");
                        RadioButton boxStudent = new RadioButton("Ja");
                        RadioButton boxNoStudent = new RadioButton("Nej");
                        ToggleGroup studentGroup = new ToggleGroup();
                        boxStudent.setToggleGroup(studentGroup);
                        boxNoStudent.setToggleGroup(studentGroup);
                        boxNoStudent.setSelected(true);
                        Button confirmButton2 = new Button("Välj");
                        confirmButton2.setOnAction(ee -> {
                            try {
                                String studentChoiceUpdate = "nej";
                                if (boxStudent.isSelected()) {
                                    studentChoiceUpdate = "ja";
                                }
                                MembershipService.memberUpdateStudent(idChange, studentChoiceUpdate);
                                AlertBox.display("Klart!", "Medlemsstatusen är uppdaterad.");
                            } catch (InvalidMemberDataException ex) {
                                AlertBox.display("Fel", "ingen");
                            }
                        });
                        dynamicBox.getChildren().addAll(studentLabel, boxStudent, boxNoStudent, confirmButton2);

                }

            }
        catch (NumberFormatException ex) {
                AlertBox.display("Fel", "ID måste vara siffror!");
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
        TableColumn<Item, String> isRentedColumn = new TableColumn<>("Uthyrd?");
        isRentedColumn.setCellValueFactory(e -> new ReadOnlyStringWrapper(RentalService.getRentalStatus(e.getValue())));
        priceColumn.setPrefWidth(100);

        itemTable.setItems(Inventory.getInventory());

        itemTable.getColumns().addAll(nameColumn,priceColumn,isRentedColumn);

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
        boxTool.setSelected(true);
        ToggleGroup itemGroup = new ToggleGroup();
        boxTool.setToggleGroup(itemGroup);
        boxVehicle.setToggleGroup(itemGroup);
        Button confirmChoiceButton = new Button("Registrera");

        confirmChoiceButton.setOnAction(e -> {
            try {
                String name = regNameField.getText();
                String priceText = regPriceField.getText();

                if (name.isEmpty() || priceText.isEmpty()) {
                    throw new IllegalArgumentException("Fyll i alla fält!");
                }
                int price;
                try {
                    price = Integer.parseInt(priceText);
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException("Ange ett heltal!");
                }
                if (price <= 0) {
                    throw new IllegalArgumentException("Priset får inte vara 0 eller negativt!");
                }
                Item newItem;
                if (boxTool.isSelected()) {

                    newItem = new Tool(name, price);

                } else {

                    newItem = new Vehicle(name, price);

                }
                if (newItem != null) {
                    Item.writeItem("items.txt");
                }
                AlertBox.display("Klart!", "Föremålet finns nu för uthyrning");
                regNameField.clear();
                regPriceField.clear();
            } catch (IllegalArgumentException ex) {
                AlertBox.display("Fel!", ex.getMessage());
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
        toolBox.setSelected(true);
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
        TableView itemTable = new TableView();
        itemTable.setPrefHeight(200);

        TableColumn<Item,String> nameColumn = new TableColumn<>("Namn");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Item, Integer> priceColumn = new TableColumn<>("Pris");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn<Item, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        nameColumn.setPrefWidth(150);
        ObservableList<Item> rentItemList = FXCollections.observableArrayList();
        for (Item i: Inventory.getInventory()) {
            if (!i.isRented()) {
                rentItemList.add(i);
            }
        }
        itemTable.getColumns().clear();
        itemTable.getColumns().addAll(nameColumn,priceColumn,idColumn);
        itemTable.setItems(rentItemList);

        Button confirmChoiceButton = new Button("Starta uthyrning");
        confirmChoiceButton.setOnAction(e -> {
                    try {
                        String memberText = memberIdField.getText().trim();
                        String itemText = itemIdField.getText().trim();

                        if(memberText.isEmpty() || itemText.isEmpty()) {
                            throw new IllegalArgumentException("Fyll i medlems-id och föremåls-id!");
                        }

                        int memberId;
                        int itemId;

                        try {
                            memberId = Integer.parseInt(memberText);
                            itemId = Integer.parseInt(itemText);
                        } catch (NumberFormatException ex) {
                            throw new IllegalArgumentException("Ange siffror!");
                        }

                        Member rentingMember = MemberRegistry.findMemberId(memberId);
                        if (rentingMember == null) {
                            throw new IllegalArgumentException("Det finns ingen medlem med detta ID " + memberId);
                        }
                        if (itemId <= 0) {
                            throw new IllegalArgumentException("Föremålet existerar inte");
                        }
                        Item rentedItem = Inventory.findById(itemId);
                        if (rentedItem == null) {
                            throw new IllegalArgumentException("Det finns inget föremål med detta ID " + itemId);
                        }

                        if (rentedItem.isRented()) {
                            throw new IllegalArgumentException("Föremålet är redan uthyrt");
                        }

                        RentalService.rentItem(rentingMember, rentedItem);
                        AlertBox.display("Klart!", "Tack! Din nuvarande skuld är " + rentingMember.getHistory());
                    } catch (IllegalArgumentException | InvalidMemberDataException ex) {
                        AlertBox.display("Fel", ex.getMessage());
                    }

                });


        Button backButton = new Button("Backa");
        backButton.setOnAction(e -> ChangeLayout.showMainMenu());

        VBox startRentalBox = new VBox(20,startRentalLabel,memberIdField,itemIdField,itemTable,confirmChoiceButton,backButton);
        startRentalBox.setPadding(new Insets(20));
        return startRentalBox;
    }
    public static VBox stopRentalMenu() {
        Label stopRentalLabel = new Label("Avsluta uthyrning - Ange ditt medlems-ID");
        TextField memberIdField = new TextField();
        memberIdField.setPromptText("Ange ditt medlems-ID");
        TextField itemIdField = new TextField();
        itemIdField.setPromptText("Vilket föremål vill du lämna tillbaka? ange ID");

        Button confirmCoiceButton = new Button("Lämna tillbaka");
        confirmCoiceButton.setOnAction(e -> {
            try {
            String memberText = memberIdField.getText();
            String itemText = itemIdField.getText();

            if (memberText.isEmpty() || itemText.isEmpty()) {
                throw new IllegalArgumentException("Fyll i medlems-ID och föremåls-ID!");
            }

            int memberId;
            int itemId;

            try {
                memberId = Integer.parseInt(memberText);
                itemId = Integer.parseInt(itemText);
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("ID måste vara siffror!");
            }

            Member returningMember = MemberRegistry.findMemberId(memberId);
            if (returningMember == null) {
                throw new IllegalArgumentException("Det finns ingen medlem med detta ID: " + memberId);
            }
            if (itemId <= 0) {
                throw new IllegalArgumentException("Ogiltigt föremåls-ID");
            }
            RentalService.returnItem(returningMember, itemId);
            AlertBox.display("Klart!", "Du har lämnat tillbaka föremålet.");
        }catch (NumberFormatException ex){
                AlertBox.display("Fel", "ID måste vara siffror!");
            }catch (InvalidMemberDataException ex) {
                AlertBox.display("Ingen träff", "ingen träff på detta ID");
            }
                });

        Button backButton = new Button("Backa");
        backButton.setOnAction(e -> ChangeLayout.showMainMenu());


        VBox stopRentalBox = new VBox(20,stopRentalLabel,memberIdField,itemIdField,confirmCoiceButton,backButton);
        stopRentalBox.setPadding(new Insets(20));
        return stopRentalBox;
    }
    public static VBox totalRevenueMenu() {
        Label totalRevenueLabel = new Label("Alla intäkter");
        TableView revenueTable = new TableView();
        revenueTable.setPrefHeight(200);

        TableColumn<Item,String> nameColumn = new TableColumn<>("Namn");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Member, String> debtColumn = new TableColumn<>("Skuld");
        debtColumn.setCellValueFactory(e -> new ReadOnlyStringWrapper(String.valueOf(e.getValue().getHistory())));
        debtColumn.setPrefWidth(120);
        revenueTable.setItems(FXCollections.observableArrayList(MemberRegistry.allMembers));
        revenueTable.getColumns().addAll(nameColumn,debtColumn);

        int totalRevenue = MembershipService.totalMemberHistory();
        Label totalLabel = new Label("Totala intäkter : " + totalRevenue);

        Button backButton = new Button("Backa");
        backButton.setOnAction(e -> ChangeLayout.showMainMenu());

        VBox totalRevenueBox = new VBox(20,totalRevenueLabel,revenueTable,totalLabel,backButton);
        totalRevenueBox.setPadding(new Insets(20));
        return totalRevenueBox;
    }
}
