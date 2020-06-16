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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Dawar Mustaqeem
 */
public class AddNewTruckController implements Initializable {
    @FXML
    private TextField plate;
    @FXML
    private TextField company;
    @FXML
    private TextField model;
    @FXML
    private TextField name;
    @FXML
    private Button save;
    @FXML
    private Button goBack;
    String cnic;
    TruckList tList = new TruckList();

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
    private void saveEvent(ActionEvent event) 
    {
        try
        {
            if(!name.getText().equals("") && !plate.getText().equals("") && !company.getText().equals(""))
            {
                String n = name.getText();
                String p = plate.getText();
                int m = Integer.parseInt(model.getText());
                String c = company.getText();
                tList.addNewTruck(p, n, c, m);
                JOptionPane.showMessageDialog(null, "Truck Added Successfully");
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource("manageTruck.fxml"));
                Parent root = loader.load();

                ManageTruckController mA = loader.getController();
                mA.setCnic(cnic);

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Manage Trucks");

                stage.setScene(scene);
                stage.show();

                Stage stage1 = (Stage) goBack.getScene().getWindow();
                stage1.close();   
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Name, Plate or Company field cant be left empty...Try Again");
            }
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error Occured...Try Again");
        }
    }

    @FXML
    private void goBackEvent(ActionEvent event) throws ClassNotFoundException, SQLException, IOException 
    {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("manageTruck.fxml"));
            Parent root = loader.load();
        
            ManageTruckController mA = loader.getController();
            mA.setCnic(cnic);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Manage Trucks");
            
            stage.setScene(scene);
            stage.show();
            
            Stage stage1 = (Stage) goBack.getScene().getWindow();
            stage1.close();
    }
    
}
