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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Dawar Mustaqeem
 */
public class AddNewWarehouseController implements Initializable {
    @FXML
    private TextField wName;
    @FXML
    private TextField mCnic;
    @FXML
    private TextField wCapacity;
    @FXML
    private TextField wType;
    @FXML
    private TextField wLocation;
    @FXML
    private TextField nextLocation;
    @FXML
    private TextField distNext;
    @FXML
    private TextField prevLocation;
    @FXML
    private TextField distPrevious;
    @FXML
    private Button save;
    @FXML
    private Button goBack;
    @FXML
    private RadioButton yesCollector;
    @FXML
    private ToggleGroup isCollector;
    @FXML
    private RadioButton noCollector;
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
    }
//String name, String m_cnic, int capacity, String warehouseType,String location,String nextLocation, String prevLocation, int distNext, int distPrev, boolean collector
    @FXML
    private void saveEvent(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
        if(!wName.getText().equals("") && !mCnic.getText().equals("") && !wCapacity.getText().equals("") && !wType.getText().equals("") && !wLocation.getText().equals("") && !nextLocation.getText().equals("") && !distNext.getText().equals(""))
        {
            if(yesCollector.isSelected() == true)
            {
                wList.addNewWarehouse(wName.getText(),mCnic.getText(),Integer.parseInt(wCapacity.getText()), wType.getText(), wLocation.getText(), nextLocation.getText(),null,Integer.parseInt(distNext.getText()),0, true);
            }
            if(noCollector.isSelected() == true)
            {
                wList.addNewWarehouse(wName.getText(),mCnic.getText(),Integer.parseInt(wCapacity.getText()), wType.getText(), wLocation.getText(), nextLocation.getText(),null,Integer.parseInt(distNext.getText()),0,false);
            }
         
            JOptionPane.showMessageDialog(null, "All changes are successfully saved");
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
        if(!wName.getText().equals("") && !mCnic.getText().equals("") && !wCapacity.getText().equals("") && !wType.getText().equals("") && !wLocation.getText().equals("") && !prevLocation.getText().equals("") && !distPrevious.getText().equals(""))
        {
            if(yesCollector.isSelected() == true)
            {
                wList.addNewWarehouse(wName.getText(),mCnic.getText(),Integer.parseInt(wCapacity.getText()), wType.getText(), wLocation.getText(), null,prevLocation.getText(),0,Integer.parseInt(distPrevious.getText()), true);
            }
            if(noCollector.isSelected() == true)
            {
                wList.addNewWarehouse(wName.getText(),mCnic.getText(),Integer.parseInt(wCapacity.getText()), wType.getText(), wLocation.getText(),null,prevLocation.getText(),0,Integer.parseInt(distPrevious.getText()),false);
            }
            JOptionPane.showMessageDialog(null, "All changes are successfully saved");
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
        else
        {
            JOptionPane.showMessageDialog(null, "No field can be left empty!");
        }
        
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
