/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Dawar Mustaqeem
 */
public class TransporterAccountController implements Initializable {
    @FXML
    private Button lotsAssigned;
    @FXML
    private Button notifiactionSent;
    @FXML
    private Button currentLot;
    @FXML
    private Button accountSettings;
    @FXML
    private Button goBack;
    @FXML
    private Label welcomeText;
    String lisence;
    TransporterList tList = new TransporterList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setLisence(String l) throws ClassNotFoundException, SQLException
    {
        lisence = l;
        Transporter t = tList.searchTransporter(lisence);
        welcomeText.setText("Welcome, "+t.name);
    }

    @FXML
    private void lotAssignedEvent(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("transporterLotsAssigned.fxml"));
        Parent root = loader.load();

        TransporterLotsAssignedController mA = loader.getController();
        mA.setLisence(lisence);
                    
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("All Lots Assigned to Customer");

        stage.setScene(scene);
        stage.show();

        Stage stage1 = (Stage) goBack.getScene().getWindow();
        stage1.close();   
    }

    @FXML
    private void notificationSentEvent(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("transporterNotificationsSent.fxml"));
        Parent root = loader.load();

        TransporterNotificationsSentController mA = loader.getController();
        mA.setLisence(lisence);
                    
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Notifications Sent from Transporter");

        stage.setScene(scene);
        stage.show();

        Stage stage1 = (Stage) goBack.getScene().getWindow();
        stage1.close();   
    }

    @FXML
    private void currentLotEvent(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("transporterCurrentLot.fxml"));
        Parent root = loader.load();

        TransporterCurrentLotController mA = loader.getController();
        mA.setLisence(lisence);
                    
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Transporter Account Settings");

        stage.setScene(scene);
        stage.show();

        Stage stage1 = (Stage) goBack.getScene().getWindow();
        stage1.close();      
    }

    @FXML
    private void accountSettingsEvent(ActionEvent event) throws IOException, ClassNotFoundException, SQLException 
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("transporterAccountSettings.fxml"));
        Parent root = loader.load();

        TransporterAccountSettingsController mA = loader.getController();
        mA.setLisence(lisence);
                    
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Transporter Account Settings");

        stage.setScene(scene);
        stage.show();

        Stage stage1 = (Stage) goBack.getScene().getWindow();
        stage1.close();        
    }

    @FXML
    private void goBackEvent(ActionEvent event) throws IOException 
    {
        Parent root = FXMLLoader.load(getClass().getResource("transporterLoginSelection.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Transporter Login Page");
        stage.setScene(scene);
        stage.show();

        Stage stage1 = (Stage) goBack.getScene().getWindow();
        stage1.close();
    }
    
}
