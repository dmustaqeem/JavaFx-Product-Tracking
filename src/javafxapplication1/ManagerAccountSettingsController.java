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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Dawar Mustaqeem
 */
public class ManagerAccountSettingsController implements Initializable {
    @FXML
    private Button editProfile;
    @FXML
    private Button changePass;
    @FXML
    private TextField nameText;
    @FXML
    private TextField ageText;
    @FXML
    private TextField addressText;
    @FXML
    private TextField phoneText;
    @FXML
    private TextField cnicText;
    @FXML
    private Button goBack;
    
    static String cnic = null;
    ManagerList mList = new ManagerList();
    Manager manager;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setCnic(String Cnic)
    {
        cnic = Cnic;
    }
    
    @FXML
    private void editProfileEvent(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("managerEditProfile.fxml"));
            Parent root = loader.load();

            ManagerEditProfileController mAC = loader.getController();
            mAC.setCnic(cnic);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Edit Manager's Profile");
            
            stage.setScene(scene);
            stage.show();
            
            Stage stage1 = (Stage) goBack.getScene().getWindow();
            stage1.close();
    }

    @FXML
    private void changePassEvent(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("changeManagerPassword.fxml"));
            Parent root = loader.load();

            ChangeManagerPasswordController mAC = loader.getController();
            mAC.setCnic(cnic);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Update Manager's Password");
            
            stage.setScene(scene);
            stage.show();
            
            Stage stage1 = (Stage) goBack.getScene().getWindow();
            stage1.close();
    }
    
    public void setFields()
    {
        manager = mList.searchManager(cnic);
        nameText.setText(manager.name);
        ageText.setText(Integer.toString(manager.age));
        addressText.setText(manager.Address);
        phoneText.setText(manager.Phone);
        cnicText.setText(manager.cnic);
    }

    @FXML
    private void goBackEvent(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ManagersAccount.fxml"));
            Parent root = loader.load();

            manager = mList.searchManager(cnic);
            ManagersAccountController mAC = loader.getController();
            mAC.setWelcome(manager.name);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Manager's Account Page");
            
            stage.setScene(scene);
            stage.show();
            
            Stage stage1 = (Stage) goBack.getScene().getWindow();
            stage1.close();
    }
    
}
