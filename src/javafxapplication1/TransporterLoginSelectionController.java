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
public class TransporterLoginSelectionController implements Initializable {
    @FXML
    private TextField lisenceText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private Button signIn;
    @FXML
    private Button goBack;
    TransporterList tList = new TransporterList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    @FXML
    private void signInEvent(ActionEvent event) 
    {
        try
        {
            Transporter t = tList.searchTransporter(lisenceText.getText());
            if(!passwordText.getText().equals(""))
            {
                if(t.password.equals(passwordText.getText()))
                {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("transporterAccount.fxml"));
                    Parent root = loader.load();

                    TransporterAccountController mA = loader.getController();
                    mA.setLisence(t.lisence);
                    
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setTitle("Transporter Account");

                    stage.setScene(scene);
                    stage.show();

                    Stage stage1 = (Stage) goBack.getScene().getWindow();
                    stage1.close();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Enter Correct Password");   
                }
            }
            else
            {
             JOptionPane.showMessageDialog(null, "No feild can be left empty");   
            }
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error Occured...Try Again");
        }
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException 
    {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("AdministrationLogInSelection.fxml"));
                    Parent root = loader.load();

                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setTitle("Administration Log In Selection");

                    stage.setScene(scene);
                    stage.show();

                    Stage stage1 = (Stage) goBack.getScene().getWindow();
                    stage1.close();
    }
    
}
