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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Dawar Mustaqeem
 */
public class TransporterAccountSettingsController implements Initializable {
    @FXML
    private TextField phone;
    @FXML
    private TextField address;
    @FXML
    private PasswordField changePass;
    @FXML
    private PasswordField rePass;
    @FXML
    private Button changePassB;
    @FXML
    private Button save;
    @FXML
    private Button deleteAccount;
    @FXML
    private TextField name;
    @FXML
    private TextField currPhone;
    @FXML
    private TextField lisenceText;
    @FXML
    private TextField currAddress;
    
    
    String lisence;
    TransporterList tList = new TransporterList();
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setLisence(String uN) throws ClassNotFoundException, SQLException
    {
        lisence = uN;
        Transporter t = tList.searchTransporter(lisence);
        name.setText(t.name);
        currPhone.setText(t.phone);
        lisenceText.setText(t.lisence);
        currAddress.setText(t.address);
    }

    @FXML
    private void changePassEvent(ActionEvent event) 
    {
        try
        {
            if(!changePass.getText().equals("") && !rePass.getText().equals(""))
            {
                tList.editTransporter(lisence,null, null,changePass.getText());
                JOptionPane.showMessageDialog(null, "Password Updated Successfully");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "No field can be left empty");
            }
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error Occured...Try Again");
        }
    }

    @FXML
    private void saveEvent(ActionEvent event) throws ClassNotFoundException, SQLException, IOException 
    {
        int i=0;
        if(!phone.getText().equals(""))
        {
            i++;
            tList.editTransporter(lisence, null, phone.getText(), null);
        }
        if(!address.getText().equals(""))
        {
            i++;
            tList.editTransporter(lisence, address.getText(), null, null);
        }
        if(i!=0)
        {
            JOptionPane.showMessageDialog(null, "All settings saved successfully");
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("transporterAccount.fxml"));
        Parent root = loader.load();

        TransporterAccountController mA = loader.getController();
        mA.setLisence(lisence);
                    
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Transporter Account");

        stage.setScene(scene);
        stage.show();

        Stage stage1 = (Stage) save.getScene().getWindow();
        stage1.close();
    }

    @FXML
    private void deleteAccount(ActionEvent event) 
    {
        try
        {
            tList.deleteTransporterBackup(lisence);
            JOptionPane.showMessageDialog(null, "Deleted Account Successfully");
            
            Parent root = FXMLLoader.load(getClass().getResource("transporterLoginSelection.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Transporter Login Page");
            stage.setScene(scene);
            stage.show();

            Stage stage1 = (Stage) save.getScene().getWindow();
            stage1.close();
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error Occured");
        }
    }
    
}
