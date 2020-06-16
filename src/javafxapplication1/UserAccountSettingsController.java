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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Dawar Mustaqeem
 */
public class UserAccountSettingsController implements Initializable {
    @FXML
    private Button deleteAccount;
    @FXML
    private Button goBack;
    @FXML
    private TextField usernameText;
    @FXML
    private TextField phoneText;
    @FXML
    private TextField addressText;
    @FXML
    private PasswordField changePassword;
    @FXML
    private PasswordField rePassword;
    @FXML
    private Button changePass;
    CustomerList cList = new CustomerList();
    static String username;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setUsername(String uN)
    {
        username = uN;
    }

    @FXML
    private void deleteAccEvent(ActionEvent event) 
    {
        try{
            cList.deleteCustomer(username);
            JOptionPane.showMessageDialog(null,"Account Deleted Successfully");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserLogInPage.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("User Login Page");

            stage.setScene(scene);
            stage.show();

            Stage stage1 = (Stage) goBack.getScene().getWindow();
            stage1.close();
            }
            catch(Exception ex)
            {
                JOptionPane.showMessageDialog(null,"Error Occured");
            }
        
    }

    @FXML
    private void goBackEvent(ActionEvent event) throws IOException 
    {
        int i = 0;
        if(!usernameText.getText().equals("") || !phoneText.getText().equals("") || !addressText.getText().equals(""))
        {
            i = 1;
        }
        if(!usernameText.getText().equals(""))
        {
            i++;
            cList.updateCustomer(username, usernameText.getText(), null, null, null);
            setUsername(usernameText.getText());
        }
        if(!phoneText.getText().equals(""))
        {
            i++;
            cList.updateCustomer(username, null, null, null, phoneText.getText());
        }
        if(!addressText.getText().equals(""))
        {
            i++;
            cList.updateCustomer(username, null, null, addressText.getText(), null);
        }
        
        if(i != 0)
        {
            JOptionPane.showMessageDialog(null, "All changes have been saved");
        }
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("userAccount.fxml"));
        Parent root = loader.load();
        UserAccountController mA = loader.getController();
        mA.setUsername(username);
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("User Account");

        stage.setScene(scene);
        stage.show();

        Stage stage1 = (Stage) goBack.getScene().getWindow();
        stage1.close();
    }
    
    @FXML
    private void changePasswordEvent(ActionEvent event) 
    {
        if(!changePassword.getText().equals("") && !rePassword.getText().equals(""))
        {
            if(changePassword.getText().equals(rePassword.getText()))
            {
                cList.updateCustomer(username, null, changePassword.getText(), null, null);
                JOptionPane.showMessageDialog(null, "Password has been successfully saved");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Entered passwords must match...");
            }
        }
        if(!changePassword.getText().equals("") && rePassword.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Please Re-Enter Password...");
        }
    }
    
}
