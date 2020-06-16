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
public class AdministrationLogInSelectionController implements Initializable {
    @FXML
    private Button ManagerLogInSelection;
    @FXML
    private Button TransporterLogInS;
    @FXML
    private Button backToHome;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ManagerLogInSelectionEvent(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ManagerLoginPage.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Manager LogIn Page");
        stage.setScene(scene);
        stage.show();

        Stage stage1 = (Stage) backToHome.getScene().getWindow();
        stage1.close();
        
    }

    @FXML
    private void TransporterLogInEvent(ActionEvent event) throws IOException 
    {
        Parent root = FXMLLoader.load(getClass().getResource("transporterLoginSelection.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Transporter Login Page");
        stage.setScene(scene);
        stage.show();

        Stage stage1 = (Stage) backToHome.getScene().getWindow();
        stage1.close();
    }

    @FXML
    private void backToHomeEvent(ActionEvent event) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("StartPage.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Administration Member LogIn Page");
        stage.setScene(scene);
        stage.show();

        Stage stage1 = (Stage) backToHome.getScene().getWindow();
        stage1.close();
        
    }
    
}
