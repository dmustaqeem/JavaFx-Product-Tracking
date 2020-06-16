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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Dawar Mustaqeem
 */
public class DeleteWarehouseController implements Initializable {
    @FXML
    private TextField locationText;
    @FXML
    private Button deleteW;
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
    }

    @FXML
    private void deleteWarehouseEvent(ActionEvent event) throws IOException {
        if(!locationText.getText().equals(""))
        {
            Warehouse w = null;
            w = wList.searchWarehouse(locationText.getText());
            if(w != null)
            {
                wList.deleteWarehouseFromBackup(locationText.getText());
                JOptionPane.showMessageDialog(null, "Warehouse has been successfully deleted");
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
                JOptionPane.showMessageDialog(null, "Please enter correct location");
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Location field cant be left empty!");
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
