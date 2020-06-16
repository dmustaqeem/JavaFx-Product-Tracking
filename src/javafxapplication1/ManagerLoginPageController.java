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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Dawar Mustaqeem
 */
public class ManagerLoginPageController implements Initializable {
    @FXML
    private TextField managerCnicLogin;
    @FXML
    private PasswordField managerLoginPassword;
    @FXML
    private Button managerLoginButton;
    @FXML
    private Button addManagerButton;
    @FXML
    private Button goBack;

    private static String cnic;
    private static String pass;
    private ManagerList manager;
    private Manager m;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void managerCnicLoginEvent(ActionEvent event){
    }

    @FXML
    private void managerLoginPasswordEvent(ActionEvent event) {
    }

    @FXML
    private void managerLoginButtonAction(ActionEvent event) throws IOException{
        
    if(managerCnicLogin.getText().equals("") || managerLoginPassword.getText().equals(""))
    {
        System.out.println("Here we are");
        JOptionPane.showMessageDialog(null, "NO FIELD CAN BE LEFT EMPTY");
    }
    else{
    cnic = (String)managerCnicLogin.getText();
    pass = (String)managerLoginPassword.getText();
    
    manager = new ManagerList();
    m = manager.searchManager(cnic);
    
        if(m == null)
        {
            JOptionPane.showMessageDialog(null, "Wrong CNIC Entered...TRY AGAIN!");
        }
        else{
        if(m != null && m.password.equals(pass))
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ManagersAccount.fxml"));
            Parent root = loader.load();
            
            ManagersAccountController mA = loader.getController();
            mA.setWelcome(m.name);
            mA.setCnic(cnic);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Managers Account Page");
            
            stage.setScene(scene);
            stage.show();
            
            Stage stage1 = (Stage) goBack.getScene().getWindow();
            stage1.close();
        }
        
        else
        {
            JOptionPane.showMessageDialog(null, "Wrong Password Entered...TRY AGAIN!");
        }
        }
    }
    }

    @FXML
    private void addManagerButtonEvent(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("createNewManager.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Create New Manager Page");
        stage.setScene(scene);
        stage.show();

        Stage stage1 = (Stage) goBack.getScene().getWindow();
        stage1.close();
    }

    @FXML
    private void goBackEvent(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AdministrationLogInSelection.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Administration LogIn Page");
        stage.setScene(scene);
        stage.show();

        Stage stage1 = (Stage) goBack.getScene().getWindow();
        stage1.close();
        
    }

    
    
    
    
}
