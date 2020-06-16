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
public class AddNewTransporterController implements Initializable {
    @FXML
    private TextField cnic;
    @FXML
    private TextField name;
    @FXML
    private TextField lisence;
    @FXML
    private TextField address;
    @FXML
    private TextField phone;
    @FXML
    private Button save;
    @FXML
    private Button goBack;
    @FXML
    private PasswordField tPassword;
    TransporterList tList = new TransporterList();
    String cnicL;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    public void setCnic(String Cnic)
    {
        cnicL = Cnic;
    }

    @FXML
    private void saveEvent(ActionEvent event) 
    {
        try
        {
            if(!cnic.getText().equals("") && !name.getText().equals("") && !lisence.getText().equals("") && !address.getText().equals("")  && !phone.getText().equals("") && !tPassword.getText().equals(""))
            {
            String cnc = cnic.getText();
            String tName = name.getText();
            String tLisence = lisence.getText();
            String tAddress = address.getText();
            String tPhone = phone.getText();
            String pass = tPassword.getText();
            
            tList.addNewTransporter(cnc, tName, tLisence, tAddress, tPhone,pass);
            JOptionPane.showMessageDialog(null, "Transporter Added Successfully");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("manageTransporters.fxml"));
            Parent root = loader.load();

            ManageTransportersController mA = loader.getController();
            mA.setCnic(cnicL);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Manage Transporters");

            stage.setScene(scene);
            stage.show();

            Stage stage1 = (Stage) goBack.getScene().getWindow();
            stage1.close();
        }
            else
            {
                JOptionPane.showMessageDialog(null, "No Field can be left empty...Try Again");
            }
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error Occured...Try Again");
        }
    }

    @FXML
    private void goBackEvent(ActionEvent event) throws IOException, ClassNotFoundException, SQLException 
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("manageTransporters.fxml"));
        Parent root = loader.load();
       
        ManageTransportersController mA = loader.getController();
        mA.setCnic(cnicL);
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Manage Transporters");

        stage.setScene(scene);
        stage.show();

        Stage stage1 = (Stage) goBack.getScene().getWindow();
        stage1.close();
    }
    
}
