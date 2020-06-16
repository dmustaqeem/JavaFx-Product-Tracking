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
public class UserLogInPageController implements Initializable {
    @FXML
    private TextField u_userName;
    @FXML
    private PasswordField u_password;
    @FXML
    private Button u_signInButton;
    @FXML
    private Button u_signUpButton;
    @FXML
    private Button toGoHome;
    CustomerList customer = new CustomerList();
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void userNameText(ActionEvent event) {
    }

    @FXML
    private void u_passwordText(ActionEvent event) {
    }

    @FXML
    private void u_signInButtonAction(ActionEvent event) 
    {
        try
        {
            String username = u_userName.getText();
            String password = u_password.getText();
            Customer c = customer.searchCustomer(username);
            if(!u_password.getText().equals(""))
            {
                if(c.password.equals(password))
                {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("userAccount.fxml"));
                    Parent root = loader.load();

                    UserAccountController mA = loader.getController();
                    mA.setUsername(username);

                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setTitle("User Account");

                    stage.setScene(scene);
                    stage.show();

                    Stage stage1 = (Stage) toGoHome.getScene().getWindow();
                    stage1.close();
                }
                if(!c.password.equals(password))
                {
                    JOptionPane.showMessageDialog(null, "Wrong Password Entered...Try Again");
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null,"No field can be left empty");
            }
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null,"Try Again...");
        }
    }

    @FXML
    private void u_signUpButtonAction(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserSignUp.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Sign Up Page");

        stage.setScene(scene);
        stage.show();

        Stage stage1 = (Stage) toGoHome.getScene().getWindow();
        stage1.close();
    }

    @FXML
    private void toGoHomeEvent(ActionEvent event) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("StartPage.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Administration Member LogIn Page");
        stage.setScene(scene);
        stage.show();

        Stage stage1 = (Stage) toGoHome.getScene().getWindow();
        stage1.close();
    }
    
}
