package org.example;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage clientStage = new Stage();
        Stage adminStage = new Stage();

        clientStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/client.fxml"))));
        clientStage.setTitle("Client Chat");
        clientStage.show();

        adminStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/admin.fxml"))));
        adminStage.setTitle("Admin Chat");
        adminStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
