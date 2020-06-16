/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.io.IOException;
import java.net.URL;
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
public class ProductsInWarehouseController implements Initializable {
    @FXML
    private TableView<Product> tableView;
    @FXML
    private TableColumn<Product, String> productName;
    @FXML
    private TableColumn<Product, Integer> productId;
    @FXML
    private TableColumn<Product, Integer> quantity;
    @FXML
    private TableColumn<Product, Integer> unitCost;
    @FXML
    private TextField productIDText;
    @FXML
    private Button deleteProductButton;
    @FXML
    private Button addProduct;
    @FXML
    private Button goBack;
    String cnic;
    ManagerList mList = new ManagerList();
    WarehouseList wList = new WarehouseList();
    ProductList pList = new ProductList();
    
    Warehouse wH;
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
        wH = wList.searchWarehouseByManager(cnic);
        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("qty"));
        unitCost.setCellValueFactory(new PropertyValueFactory<>("costPerUnit"));
        tableView.setItems(getList());
    }
    
    public ObservableList<Product> getList()
    {
        ObservableList<Product> list = FXCollections.observableArrayList();
        pList.loadProducts();
        Product temp = pList.head;
        
        while(temp != null)
        {
            if(temp.warehouseId.equals(wH.location))
            {
                list.add(temp);
            }
            temp = temp.next;
        }
        return list;
    }

    @FXML
    private void deleteProductEvent(ActionEvent event) {
        int pId = Integer.parseInt(productIDText.getText());
        try
        {
            Product p = pList.searchProducts(pId);
            pList.deleteProduct(p.productId);
            JOptionPane.showMessageDialog(null, "Successfully Deleted");
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Wrong Product ID Entered");
        }
        
    }

    @FXML
    private void addProductEvent(ActionEvent event) throws IOException 
    {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("addNewProductManager.fxml"));
            Parent root = loader.load();
            
            AddNewProductManagerController mA = loader.getController();
            mA.setCnic(cnic,wH.location);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Add New Product");
            
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
            mA.setCnic(cnic);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Warehouse Settings Page");
            
            stage.setScene(scene);
            stage.show();
            
            Stage stage1 = (Stage) goBack.getScene().getWindow();
            stage1.close();
    }
    
}
