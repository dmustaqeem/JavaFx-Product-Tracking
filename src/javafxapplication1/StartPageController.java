/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Dawar Mustaqeem
 */
public class StartPageController implements Initializable {
    @FXML
    private Button userLoginSelection;
    @FXML
    private Button adminLoginSelection;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void userLoginSelectEvent(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("UserLogInPage.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("User LogIn Page");
        stage.setScene(scene);
        stage.show();
        
        Stage stage1 = (Stage) userLoginSelection.getScene().getWindow();
        stage1.close();
    }

    @FXML
    private void adminLoginSelectEvent(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AdministrationLogInSelection.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Administration Member LogIn Page");
        stage.setScene(scene);
        stage.show();

        Stage stage1 = (Stage) adminLoginSelection.getScene().getWindow();
        stage1.close();
        
    }
    
}
