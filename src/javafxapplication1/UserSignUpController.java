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
public class UserSignUpController implements Initializable {
    @FXML
    private Button save;
    @FXML
    private Button goBack;
    @FXML
    private PasswordField passwordText;
    @FXML
    private TextField nameUser;
    @FXML
    private TextField ageUser;
    @FXML
    private TextField uCnic;
    @FXML
    private TextField uPhone;
    @FXML
    private TextField usernameText;
    @FXML 
    private TextField addressText;
    CustomerList cList = new CustomerList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void saveEvent(ActionEvent event) 
    {
        try
        {
            if(nameUser.getText().equals("") || uCnic.getText().equals("") || uPhone.getText().equals("") || passwordText.getText().equals("") || usernameText.getText().equals("") || ageUser.getText().equals("") || addressText.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null, "No field can be left empty");
            }
            else
            {
                String name = nameUser.getText();
                String cnic = uCnic.getText();
                String phone = uPhone.getText();
                String password = passwordText.getText();
                String username = usernameText.getText();
                int age = Integer.parseInt(ageUser.getText());
                String address = addressText.getText();
    //String username, String cnic, int age, String name, String password, String address, String phone            
                cList.addNewCustomer(username, cnic, age, name, password, address, phone);
                JOptionPane.showMessageDialog(null, "Successfully Created Account");
                
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
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error Occured!");
        }
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException 
    {
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
    
}
