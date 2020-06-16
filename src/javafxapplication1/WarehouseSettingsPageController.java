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
import static javafxapplication1.ManagersAccountController.cnic;

/**
 * FXML Controller class
 *
 * @author Dawar Mustaqeem
 */
public class WarehouseSettingsPageController implements Initializable {
    @FXML
    private Button addWarehouse;
    @FXML
    private Button deleteWarehouse;
    @FXML
    private Button editWarehouse;
    @FXML
    private Button goBack;
    @FXML
    private TextField warehouseManager;
    @FXML
    private Button productsButton;
    @FXML
    private Button manageTransporters;
    @FXML
    private Button manageTrucks;
    
    static String cnic;
    String wLocation;
    ManagerList mList = new ManagerList();
    WarehouseList wList = new WarehouseList();
    
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
        wList.loadWarehouse();
        Warehouse w = wList.head;
        while(w != null)
        {
            if (w.managerId.equals(Cnic))
            {
                wLocation = w.location;
                warehouseManager.setText(wLocation);
                break;
            }
            w = w.next;
        }
        
    }

    @FXML
    private void addWarehouseEvent(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addNewWarehouse.fxml"));
            Parent root = loader.load();
        
            AddNewWarehouseController mA = loader.getController();
            mA.setCnic(cnic);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Add New Warehouse");
            
            stage.setScene(scene);
            stage.show();
            
            Stage stage1 = (Stage) goBack.getScene().getWindow();
            stage1.close();
    }

    @FXML
    private void deleteWarehouseEvent(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("deleteWarehouse.fxml"));
            Parent root = loader.load();
        
            DeleteWarehouseController mA = loader.getController();
            mA.setCnic(cnic);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Delete Warehouse");
            
            stage.setScene(scene);
            stage.show();
            
            Stage stage1 = (Stage) goBack.getScene().getWindow();
            stage1.close();
    }

    @FXML
    private void editWarehouseEvent(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("editWarehouse.fxml"));
            Parent root = loader.load();
        
            EditWarehouseController mA = loader.getController();
            mA.setCnic(cnic);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Edit Warehouse");
            
            stage.setScene(scene);
            stage.show();
            
            Stage stage1 = (Stage) goBack.getScene().getWindow();
            stage1.close();
            
    }

    @FXML
    private void goBackEvent(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ManagersAccount.fxml"));
            Parent root = loader.load();
            
            ManagersAccountController mA = loader.getController();
            mA.setCnic(cnic);
            Manager manager = mList.searchManager(cnic);
            mA.setWelcome(manager.name);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Manager's Account");
            
            stage.setScene(scene);
            stage.show();
            
            Stage stage1 = (Stage) goBack.getScene().getWindow();
            stage1.close();
    }

    @FXML
    private void warehouseToManager(ActionEvent event) {
    }
    
    @FXML
    private void productsEvent(ActionEvent event) throws IOException
    {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("productsInWarehouse.fxml"));
            Parent root = loader.load();
            
            ProductsInWarehouseController mA = loader.getController();
            mA.setCnic(cnic);
//            Manager manager = mList.searchManager(cnic);
//            mA.setWelcome(manager.name);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Products In Warehouse");
            
            stage.setScene(scene);
            stage.show();
            
            Stage stage1 = (Stage) goBack.getScene().getWindow();
            stage1.close();
    }

    @FXML
    private void manageTransportersEvent(ActionEvent event) throws IOException, ClassNotFoundException, SQLException 
    {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("manageTransporters.fxml"));
            Parent root = loader.load();
        
            ManageTransportersController mA = loader.getController();
            mA.setCnic(cnic);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Manage Transporters");
            
            stage.setScene(scene);
            stage.show();
            
            Stage stage1 = (Stage) goBack.getScene().getWindow();
            stage1.close();
    }

    @FXML
    private void manageTrucksEvent(ActionEvent event) throws IOException, ClassNotFoundException, SQLException 
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
