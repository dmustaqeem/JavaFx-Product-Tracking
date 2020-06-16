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
public class AddNewProductManagerController implements Initializable {
    @FXML
    private TextField productName;
    @FXML
    private TextField productType;
    @FXML
    private TextField unitCost;
    @FXML
    private TextField quantity;
    @FXML
    private Button save;
    @FXML
    private Button goBack;
    @FXML
    private Label warehouseIdText;
    String cnic;
    String warehouseId;
    ProductList pList = new ProductList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setCnic(String Cnic,String wId)
    {
        cnic = Cnic;
        warehouseId = wId;
    }
    @FXML
    private void saveEvent(ActionEvent event) throws IOException {
        if(productName.getText() != null && productType.getText() != null && unitCost.getText() != null && quantity.getText() != null){
        String pName = productName.getText();
        String pType = productType.getText();
        float perUnitCost = Float.parseFloat(unitCost.getText());
        int qty = Integer.parseInt(quantity.getText());
        //String productType, String productName, float costPerUnit, int qty, String warehouseId
        pList.addNewProduct(pType, pName, perUnitCost, qty, warehouseId);
        
            JOptionPane.showMessageDialog(null,"New Product Added Successfully");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("productsInWarehouse.fxml"));
            Parent root = loader.load();
            
            ProductsInWarehouseController mA = loader.getController();
            mA.setCnic(cnic);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Products In Warehouse");
            
            stage.setScene(scene);
            stage.show();
            
            Stage stage1 = (Stage) goBack.getScene().getWindow();
            stage1.close();
    }
    }

    @FXML
    private void goBackEvent(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("productsInWarehouse.fxml"));
            Parent root = loader.load();
            
            ProductsInWarehouseController mA = loader.getController();
            mA.setCnic(cnic);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Products In Warehouse");
            
            stage.setScene(scene);
            stage.show();
            
            Stage stage1 = (Stage) goBack.getScene().getWindow();
            stage1.close();
    }
    
}
