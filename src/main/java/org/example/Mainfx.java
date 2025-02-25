package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class Mainfx extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login2.fxml"));
        Parent root = loader.load();
        //Scene sc = new Scene(root);
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 1100, 600));
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.show();


    }
}
