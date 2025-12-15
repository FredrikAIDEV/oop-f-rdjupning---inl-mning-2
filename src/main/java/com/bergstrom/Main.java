package com.bergstrom;

import exception.InvalidMemberDataException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {
    private static Main instance;
    BorderPane root;

    //Member a = new Member("Sven",true);
    //Member b = new Member("Lasse",false);
    //Member c = new Member("Bosse",false);
    //Member d = new Member("Anna", false);

    public Main() throws InvalidMemberDataException {
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

    Item.readItem("items.txt");
    MemberRegistry.readMember("members.txt");
    root = new BorderPane();
    root.setCenter(Menus.mainMenu());
    Scene scene = new Scene(root, 500,500);

    primaryStage.setScene(scene);
    primaryStage.setTitle("Inlämning 2");
    primaryStage.show();

    }
    public BorderPane getRoot() {
        return root;
    }

}