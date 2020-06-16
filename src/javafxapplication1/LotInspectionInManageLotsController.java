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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Dawar Mustaqeem
 */
public class LotInspectionInManageLotsController implements Initializable {
    @FXML
    private TableView<Lot> tableView;
    @FXML
    private TableColumn<Lot, String> locations;
    @FXML
    private Button goBack;
    @FXML
    private Button markDilivered;
    @FXML
    private Button changeTransporter;
    @FXML
    private TextField transporterIdText;
    @FXML
    private TextField plateNumberText;
    @FXML
    private Button changeTruck;
    String cnic;
    int ltId;
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
    
    public void setCnic(String Cnic,int lotId)
    {
        cnic = Cnic;
        ltId = lotId;
        locations.setCellValueFactory(new PropertyValueFactory<>("currLocation"));
        tableView.setItems(getList());
    }
    
    public ObservableList<Lot> getList()
    {
        ObservableList<Lot> list = FXCollections.observableArrayList();
        lots.loadLotCurrentLocation();
        Lot temp = lots.head;
        while(temp != null)
        {
            if(temp.lotId == ltId)
            {
                list.add(temp);
            }
            temp = temp.next;
        }
        return list;
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

    @FXML
    private void markDiliveredEvent(ActionEvent event) 
    {
        lots.setAsDilivered(ltId);
    }

    @FXML
    private void changeTranrporter(ActionEvent event) throws ClassNotFoundException, SQLException 
    {
        try{
        String transId = transporterIdText.getText();
        Transporter trans = null;
        trans = tList.searchTransporter(transId);
            
        if(trans != null)
        {
            lots.updateLot(ltId, trans.lisence, null);
            JOptionPane.showMessageDialog(null, "Transporter has been updated");
        }
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error Occured");
        }
    }

    @FXML
    private void changeTruckEvent(ActionEvent event) {
        try
        {
            String plate = plateNumberText.getText();
            Truck t = null;
            t = trucks.searchTruck(plate);
            if(t != null)
            {
                lots.updateLot(ltId, null, plate);
                JOptionPane.showMessageDialog(null, "Truck has been updated");
            }
            
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error Occured...Try Again");
        }
    }
    
}
