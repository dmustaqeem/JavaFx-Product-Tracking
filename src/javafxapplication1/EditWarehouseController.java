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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Dawar Mustaqeem
 */
public class EditWarehouseController implements Initializable {
    @FXML
    private TextField wName;
    @FXML
    private TextField managerCnic;
    @FXML
    private TextField wType;
    @FXML
    private TextField capacity;
    @FXML
    private TextField nextWarehouse;
    @FXML
    private TextField prevWarehouse;
    @FXML
    private TextField distNext;
    @FXML
    private TextField distPrevious;
    @FXML
    private Label currentWarehouse;
    @FXML
    private Button save;
    @FXML
    private Button goBack;
    
    String Cnic;
    WarehouseList wList = new WarehouseList();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setCnic(String cnic)
    {
        Cnic = cnic;
        Warehouse w = wList.searchWarehouseByManager(cnic);
        currentWarehouse.setText(w.location);
    }
//updateWarehouse(String location,String name, String m_cnic,String warehouseType,int capacity, String nextLocation, String prevLocation, int distNext, int distPrev) throws ClassNotFoundException
    @FXML
    private void saveEvent(ActionEvent event) throws ClassNotFoundException, IOException {
        Warehouse w = wList.searchWarehouseByManager(Cnic);
        int i=0;
        if(!wName.getText().equals(""))
        {
           wList.updateWarehouse(w.location,wName.getText(),null,null,0,null,null,0,0);
           i++;
        }
        if(!managerCnic.getText().equals(""))
        {
            wList.updateWarehouse(w.location,null,managerCnic.getText(),null,0,null,null,0,0);
            i++;
        }
        if(!wType.getText().equals(""))
        {
            wList.updateWarehouse(w.location,null,null,wType.getText(),0,null,null,0,0);
            i++;
        }
        if(!capacity.getText().equals(""))
        {
            wList.updateWarehouse(w.location,null,null,null,Integer.parseInt(capacity.getText()),null,null,0,0);
            i++;
        }
        if(!nextWarehouse.getText().equals(""))
        {
            wList.updateWarehouse(w.location,null,null,null,0,nextWarehouse.getText(),null,0,0);
            i++;
        }
        if(!prevWarehouse.getText().equals(""))
        {
            wList.updateWarehouse(w.location,null,null,null,0,null,prevWarehouse.getText(),0,0);
            i++;
        }
        if(!distNext.getText().equals(""))
        {
            wList.updateWarehouse(w.location,null,null,null,0,null,null,Integer.parseInt(distNext.getText()),0);
            i++;
        }
        if(!distPrevious.getText().equals(""))
        {
            wList.updateWarehouse(w.location,null,null,null,0,null,null,0,Integer.parseInt(distPrevious.getText()));
            i++;
        }
        
        if(i!=0)
        {
            JOptionPane.showMessageDialog(null, "All changes are successfully saved");
        }
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("WarehouseSettingsPage.fxml"));
        Parent root = loader.load();
        
        WarehouseSettingsPageController mA = loader.getController();
        mA.setCnic(Cnic);
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Warehouse Settings Page");
            
        stage.setScene(scene);
        stage.show();
            
        Stage stage1 = (Stage) goBack.getScene().getWindow();
        stage1.close();
    }

    @FXML
    private void goBackEvent(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("WarehouseSettingsPage.fxml"));
        Parent root = loader.load();
        
        WarehouseSettingsPageController mA = loader.getController();
        mA.setCnic(Cnic);
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Warehouse Settings Page");
            
        stage.setScene(scene);
        stage.show();
            
        Stage stage1 = (Stage) goBack.getScene().getWindow();
        stage1.close();
    }
    
}
