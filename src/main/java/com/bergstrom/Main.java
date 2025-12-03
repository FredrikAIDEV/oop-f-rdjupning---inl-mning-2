package com.bergstrom;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {
    //Stage window;
    //VBox mainMenu;
    private static Main instance;
    BorderPane root;

    public Main() {
        instance = this;
    }

    public static Main getInstance(){
        return instance;
    };

    public static void main(String[] args) {
    launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {


    root = new BorderPane();
    root.setCenter(Menus.mainMenu());
    Scene scene = new Scene(root, 500,500);

    primaryStage.setScene(scene);
    primaryStage.setTitle("Inlämning 2");
    primaryStage.show();

    //LABELS
/*








    // BEHÖVS EJ?? Label quitLabel = new Label("Avsluta");



    //BUTTONS






    //TEXTFIELDS





    //CHECKBOX




    //CHOICEBOX






    //LAYOUT
    //VBox layoutStart = new VBox(20);
    //layoutStart.setPadding(new Insets(20,20,20,20));
    //layoutStart.getChildren().addAll(intro,addMemberButton,searchMemberButton,changeMemberButton,listItemButton,addItemButton,filterButton,startRentalButton,stopRentalButton,totalRevenueButton,quitButton);


    //addMemberButton.setOnAction(e -> ChangeLayout.addMemberMenu(window));


    //Scene scene = new Scene(layoutStart, 500, 500);
    //window.setScene(scene);
    //window.show();
*/

    }
    public BorderPane getRoot() {
        return root;
    }

}