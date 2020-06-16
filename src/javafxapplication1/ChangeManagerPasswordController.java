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
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Dawar Mustaqeem
 */
public class ChangeManagerPasswordController implements Initializable {
    @FXML
    private Button savePass;
    @FXML
    private Button goBack;
    @FXML
    private PasswordField passText;
    @FXML
    private PasswordField rePassText;
    
    static String cnic;
    static ManagerList mList = new ManagerList();
    static Manager manager;
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
    private void savePassEvent(ActionEvent event) throws ClassNotFoundException, IOException {
        String pass1 = passText.getText();
        String pass2 = rePassText.getText();
        //password must be 6 charectars long
        if(pass1.length() < 6)
        {
            JOptionPane.showMessageDialog(null, "Enter Password with more then 6 Characters");
        }
        else
        {
            if(pass1.equals(pass2))
            {
                mList.updateManager(cnic,null, pass1,null, null);
                JOptionPane.showMessageDialog(null, "Password has been successfully updated!");
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ManagerAccountSettings.fxml"));
                Parent root = loader.load();

                manager = mList.searchManager(cnic);
                ManagerAccountSettingsController mAC = loader.getController();
                mAC.setCnic(cnic);
                mAC.setFields();
                
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Manager's Account Settings");

                stage.setScene(scene);
                stage.show();

                Stage stage1 = (Stage) goBack.getScene().getWindow();
                stage1.close();
                
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Passwords must match...Try Again!");
            }
        }
    }

    @FXML
    private void goBackEvent(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ManagerAccountSettings.fxml"));
            Parent root = loader.load();

            manager = mList.searchManager(cnic);
            ManagerAccountSettingsController mAC = loader.getController();
            mAC.setCnic(cnic);
            mAC.setFields();
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Manager's Account Settings");
            
            stage.setScene(scene);
            stage.show();
            
            Stage stage1 = (Stage) goBack.getScene().getWindow();
            stage1.close();
    }

    @FXML
    private void passTextEvent(ActionEvent event) {
    }

    @FXML
    private void rePassTextEvent(ActionEvent event) {
    }
    
}
