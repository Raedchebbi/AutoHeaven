package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class login2Controller implements Initializable {

    @FXML
    private Button avisbtn;

    @FXML
    private Button equippbtn;

    @FXML
    private Button joinbtn;

    @FXML
    private Button loginButton;

    @FXML
    private Button marquebtn;

    @FXML
    private Button offresbtn;

    @FXML
    private Button plusbtn;

    @FXML
    private Button plusbtn1;

    @FXML
    private Button plusbtn2;

    @FXML
    private Button plusbtn3;

    @FXML
    private Button reserveerbtn;

    @FXML
    private Button reserverbtn;

    @FXML
    private Button reserverbtn1;

    @FXML
    private Button reserverbtn2;

    @FXML
    private Button reserverbtn3;

    @FXML
    private ImageView searchImageView;

    @FXML
    private Button signupButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File searchFile = new File("images/search.png");
        Image brandingImage = new Image(searchFile.toURI().toString());
        searchImageView.setImage(brandingImage);


    }


    public void loginButtonOnAction(){
    try {
        loginButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginUser.fxml"));
        Parent root = loader.load();



        Stage profileStage = new Stage();
        profileStage.setScene(new Scene(root, 1100, 600));
        profileStage.initStyle(StageStyle.UNDECORATED);
        profileStage.show();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public void signupButtonOnAction(){
        try {
            signupButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddUser.fxml"));
            Parent root = loader.load();



            Stage profileStage = new Stage();
            profileStage.setScene(new Scene(root, 1100, 600));
            profileStage.initStyle(StageStyle.UNDECORATED);
            profileStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

