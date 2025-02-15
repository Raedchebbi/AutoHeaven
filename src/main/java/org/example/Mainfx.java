//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Mainfx extends Application {
    public Mainfx() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/addreclamation.fxml"));
        Parent root = (Parent)loader.load();
        Scene sc = new Scene(root);
        stage.setScene(sc);
        stage.setTitle("Ajouter");
        stage.show();
    }
}
