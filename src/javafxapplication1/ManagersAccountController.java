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
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Dawar Mustaqeem
 */
public class ManagersAccountController implements Initializable {
    @FXML
    private AnchorPane windowPage;
    @FXML
    private Button accountSetting;
    @FXML
    private Button goBack;
    @FXML
    private Button notifications;
    @FXML
    private Button warehouseSettings;
    @FXML
    private Label welcomeText;
    @FXML
    private Button ordersInWarehouse;
    @FXML 
    private Button lot;
    static String cnic = null;
    

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
    
    public void setWelcome(String name)
    {
        welcomeText.setText("Welcome, "+name+"!");
    }
    
    
    @FXML
    private void accountSettingEvent(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
        
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ManagerAccountSettings.fxml"));
            Parent root = loader.load();
            
            ManagerAccountSettingsController mA = loader.getController();
            mA.setCnic(cnic);
            mA.setFields();
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Manager's Account Settings");
            
            stage.setScene(scene);
            stage.show();
            
            Stage stage1 = (Stage) goBack.getScene().getWindow();
            stage1.close();
    }

    @FXML
    private void goBackEvent(ActionEvent event) throws IOException 
    {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ManagerLoginPage.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Manager Login Page");
            
            stage.setScene(scene);
            stage.show();
            
            Stage stage1 = (Stage) goBack.getScene().getWindow();
            stage1.close();
            
    }

    @FXML
    private void notificationsEvent(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("notificationManager.fxml"));
            Parent root = loader.load();
            
            NotificationManagerController mA = loader.getController();
            mA.setCnic(cnic);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Notifications from Transporter");
            
            stage.setScene(scene);
            stage.show();
            
            Stage stage1 = (Stage) goBack.getScene().getWindow();
            stage1.close();
    }

    @FXML
    private void warehouseSettingEvent(ActionEvent event) throws IOException {
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
    private void ordersInWarehouse(ActionEvent event) throws IOException {
       
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ordersInWarehouse.fxml"));
            Parent root = loader.load();
            
            OrdersInWarehouseController mA = loader.getController();
            mA.setCnic(cnic);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Orders In Managers Warehouse");
            
            stage.setScene(scene);
            stage.show();
            
            Stage stage1 = (Stage) goBack.getScene().getWindow();
            stage1.close();
    }

    @FXML
    private void windowPageEvent(MouseEvent event) 
    {
    }
    
    @FXML 
    private void lotEvent(ActionEvent event) throws IOException
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
