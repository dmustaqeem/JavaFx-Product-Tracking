/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
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
public class ManageTruckController implements Initializable {
    
    @FXML
    private TableView<Truck> tableView;
    @FXML
    private TableColumn<Truck, String> plate;
    @FXML
    private TableColumn<Truck, String> make;
    @FXML
    private TableColumn<Truck, String> company;
    @FXML
    private TableColumn<Truck, Integer> model;
    @FXML
    private TableColumn<Truck, Date> purchaseDate;
    @FXML
    private TextField plateNumberText;
    @FXML
    private Button deleteTruck;
    @FXML
    private Button addNewTruck;
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
    
    public void setCnic(String Cnic) throws ClassNotFoundException, SQLException
    {
        cnic = Cnic;
        plate.setCellValueFactory(new PropertyValueFactory<>("plate"));
        make.setCellValueFactory(new PropertyValueFactory<>("name"));
        company.setCellValueFactory(new PropertyValueFactory<>("company"));
        model.setCellValueFactory(new PropertyValueFactory<>("model"));
        purchaseDate.setCellValueFactory(new PropertyValueFactory<>("purchaseDate"));
        tableView.setItems(getList());
    }
    
    public ObservableList<Truck> getList() throws ClassNotFoundException, SQLException
    {
        ObservableList<Truck> list = FXCollections.observableArrayList();
        tList.loadTrucks();
        Truck temp = tList.head;
        while(temp != null)
        {
            list.add(temp);
            temp = temp.next;
        }
        return list;
    }


    @FXML
    private void deleteTruckEvent(ActionEvent event) 
    {
        try
        {
            String pl = plateNumberText.getText();
            if(pl != null)
            {
                Truck t = tList.searchTruck(pl);
                tList.deleteTruckBackup(t.plate);
                JOptionPane.showMessageDialog(null, "Truck Deleted Successfully");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Enter Plate Number");
            }
            
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Enter Correct Plate Number");
        }
    }

    @FXML
    private void goBackEvent(ActionEvent event) throws IOException 
    {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("WarehouseSettingsPage.fxml"));
            Parent root = loader.load();
            
            WarehouseSettingsPageController mA = loader.getController();
            mA.setCnic(cnic);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Warehouse Settings Page");
            
            stage.setScene(scene);
            stage.show();
            
            Stage stage1 = (Stage) goBack.getScene().getWindow();
            stage1.close();
    }

    @FXML
    private void addNewTruckEvent(ActionEvent event) throws IOException 
    {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addNewTruck.fxml"));
            Parent root = loader.load();
            
            AddNewTruckController mA = loader.getController();
            mA.setCnic(cnic);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Add New Truck");
            
            stage.setScene(scene);
            stage.show();
            
            Stage stage1 = (Stage) goBack.getScene().getWindow();
            stage1.close();
    }
    
}
