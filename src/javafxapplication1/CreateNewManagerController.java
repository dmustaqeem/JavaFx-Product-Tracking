/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.ParseException;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Dawar Mustaqeem
 */
public class CreateNewManagerController implements Initializable {
    @FXML
    private TextField ManagerFirstNameText;
    @FXML
    private TextField ManagerLastNameText;
    @FXML
    private TextField ManagerAgeText;
    @FXML
    private TextField mPhoneText;
    @FXML
    private TextField mCnicText;
    @FXML
    private TextField mAddressText;
    @FXML
    private Button backPage;
    @FXML
    private Button nextPage;
    @FXML
    private PasswordField createPassword;

    /**
     * Initializes the controller class.
     */
    
    String m_cnic = null;
    String m_name= null;
    String m_phone= null;
    int m_age = 0;
    String m_address= null;
    String m_password= null;
    ManagerList manager;
    Manager l;
    @FXML
    private Text passwordText;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ManagerFirstNameTextEvent(ActionEvent event) {
    }

    @FXML
    private void ManagerLastNameTextEvent(ActionEvent event) {
    }

    @FXML
    private void ManagerAgeTextEvent(ActionEvent event) {
    }

    @FXML
    private void mPhoneTextEvent(ActionEvent event) {
    }

    @FXML
    private void mCnicTextEvent(ActionEvent event) {
    }

    @FXML
    private void mAddressTextEvent(ActionEvent event) {
    }

    
        @FXML
    private void passwordTextEvent(MouseEvent event) {
    }

    @FXML
    private void createPasswordEvent(ActionEvent event) {
    }
    
    @FXML
    private void backPageEvent(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ManagerLoginPage.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Manager LogIn Page");
        stage.setScene(scene);
        stage.show();

        Stage stage1 = (Stage) backPage.getScene().getWindow();
        stage1.close();

    }
        @FXML
    private void nextPageEvent(ActionEvent event) throws ParseException {
        try{
        m_name = ManagerFirstNameText.getText().concat(" "+ ManagerLastNameText.getText());
        m_cnic = mCnicText.getText();
        m_age = Integer.parseInt(ManagerAgeText.getText());
        m_phone = mPhoneText.getText();
        m_address = mAddressText.getText();
        m_password = createPassword.getText();
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null,"No field can be left empty!");
        }

        try
        {
            manager = new ManagerList();
            l = manager.searchManager(m_cnic);
            if(l != null)
            {
                throw new NullPointerException();
            }
        }
        catch(Exception cx)
        {
            JOptionPane.showMessageDialog(null,"CNIC already exists!");        
        }
        try{
        if(m_cnic != null && m_name != null && m_phone != null && m_age != 0 && m_password != null && m_address != null)
        {    
            manager.addNewManager(m_name,m_cnic,m_age,m_address, m_phone,m_password);
            Parent root = FXMLLoader.load(getClass().getResource("ManagerLoginPage.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Manager LogIn Page");
            stage.setScene(scene);
            stage.show();

            Stage stage1 = (Stage) backPage.getScene().getWindow();
            stage1.close();
            
        }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"Error Occured!");
        }
        //
        
        
        
    }


    
}
