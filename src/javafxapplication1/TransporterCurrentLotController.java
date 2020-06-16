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
public class TransporterCurrentLotController implements Initializable {
    @FXML
    private TextField lotId;
    @FXML
    private TextField occupiedText;
    @FXML
    private TextField truckPlate;
    @FXML
    private TableView<Warehouse> tableView;
    @FXML
    private TableColumn<Warehouse, String> warehouseIdColumn;
    @FXML
    private TableColumn<Warehouse, String> warehouseLocation;
    @FXML
    private TextField warehouseIdText;
    @FXML
    private Button selectWarehouse;
    @FXML
    private Button goBack;
    @FXML
    private Button udpate;
    String lisence;
    TransporterList tList = new TransporterList();
    LotList lots = new LotList();
    int currLot;
    Warehouse selectedWarehouse;
    WarehouseList wList = new WarehouseList();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    public void setLisence(String l)
    {
        lisence = l;
       
        if(tList.getCurrentLot(lisence) != -1)
        {
            currLot = tList.getCurrentLot(lisence);
            lotId.setText(Integer.toString(currLot));
            Lot lo;
            lo = lots.searchLot(currLot);
            occupiedText.setText(Integer.toString(lo.occupied));
            truckPlate.setText(lo.plate);
        }
        else
        {
            lotId.setText("No Lot Assigned");
        }
        
        warehouseIdColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        warehouseLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        tableView.setItems(getList());
        
    }
    
    public ObservableList<Warehouse> getList()
    {
        ObservableList<Warehouse> list = FXCollections.observableArrayList();
        wList.loadWarehouse();
        Warehouse temp = wList.head;
        while(temp != null)
        {
            list.add(temp);
            temp = temp.next;
        }
        
       return list;
    }
    
    
    @FXML
    private void selectWarehouseEvent(ActionEvent event) 
    {
        try
        {
            String wId = warehouseIdText.getText();
            if(!warehouseIdText.getText().equals("")){
            selectedWarehouse = wList.searchWarehouseByName(wId);
            JOptionPane.showMessageDialog(null, "Warehouse selected successfully");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Field cant be left empty");
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("transporterAccount.fxml"));
        Parent root = loader.load();

        TransporterAccountController mA = loader.getController();
        mA.setLisence(lisence);
                    
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Transporter Account");

        stage.setScene(scene);
        stage.show();

        Stage stage1 = (Stage) goBack.getScene().getWindow();
        stage1.close();     
    }

    @FXML
    private void updateEvent(ActionEvent event) 
    {
        try
        {
            if(!lotId.getText().equals("") && !truckPlate.getText().equals("") && selectedWarehouse != null)
            {
                tList.updateLocation(lisence, selectedWarehouse.location, truckPlate.getText(), currLot);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "No Lot Assigned");
            }
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error Occured...Try Again");
        }
    }
    
}
