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
public class CreateNewLotController implements Initializable {
    @FXML
    private TextField transporterId;
    @FXML
    private TextField plateNumber;
    @FXML
    private TextField capacity;
    @FXML
    private Button save;
    @FXML
    private Button goBack;
    String cnic;
    LotList lots = new LotList();
    TransporterList tList = new TransporterList();
    TruckList trucks = new TruckList();
    

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
    private void saveEvent(ActionEvent event) throws ClassNotFoundException, SQLException 
    {
        try{
            if(transporterId.getText().equals("") || plateNumber.getText().equals("") || capacity.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null, "No field can be left empty");
            }
            else
            {
                int transporterStatus = tList.getCurrentLot(transporterId.getText());
                if(transporterStatus == -1)
                {
                   Transporter trans = tList.searchTransporter(transporterId.getText());
                
                Truck truck = trucks.searchTruck(plateNumber.getText());
                int cap = Integer.parseInt(capacity.getText());
                lots.addNewLot(cap, trans.lisence, truck.plate);
                JOptionPane.showMessageDialog(null,"Lot has been created successfully");
                }
                else
                {
                JOptionPane.showMessageDialog(null,"Transporter has been already assigned");    
                }
                FXMLLoader loader = new FXMLLoader(getClass().getResource("manageLots.fxml"));
                Parent root = loader.load();

                ManageLotsController mA = loader.getController();
                mA.setCnic(cnic);

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Manage Lots");

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
    private void goBackEvent(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("manageLots.fxml"));
            Parent root = loader.load();
            
            ManageLotsController mA = loader.getController();
            mA.setCnic(cnic);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Manage Lots");
            
            stage.setScene(scene);
            stage.show();
            
            Stage stage1 = (Stage) goBack.getScene().getWindow();
            stage1.close();
    }
    
}
