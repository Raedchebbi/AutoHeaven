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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginUser.fxml"));
        Parent root = loader.load();
        //Scene sc = new Scene(root);
        stage.setScene(new Scene(root, 520, 400));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();


    }
}
