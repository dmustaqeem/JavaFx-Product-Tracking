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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Dawar Mustaqeem
 */
public class OrderCreateUserPageController implements Initializable {
    @FXML
    private AnchorPane collectorPoints;
    @FXML
    private Button goBack;
    @FXML
    private TextField productIdText;
    @FXML
    private Button selectProduct;
    @FXML
    private Button createOrder;
    @FXML
    private TextField quantityText;
    @FXML
    private TableView<Product> tableView;
    @FXML
    private TableColumn<Product, Integer> productId;
    @FXML
    private TableColumn<Product, String> prodName;
    String username;
    @FXML
    private TableView<Warehouse> tableView1;
    @FXML
    private TableColumn<Warehouse, String> collectorId;
    @FXML
    private TableColumn<Warehouse, String> CollectorLocation;
    @FXML
    private TextField collectorIdText;
    @FXML
    private Button selectCollector;
    String productType;
    WarehouseList wList = new WarehouseList();
    ProductList pList = new ProductList();
    int selectedProduct = -1;
    String selectedWarehouse;
    String collectorPointId = null;
    OrdersList oList = new OrdersList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setUsername(String uN,String pType)
    {
        username = uN;
        productType = pType;
        
        productId.setCellValueFactory(new PropertyValueFactory("productId"));
        prodName.setCellValueFactory(new PropertyValueFactory("productName"));
        tableView.setItems(getProducts());
        
        collectorId.setCellValueFactory(new PropertyValueFactory("name"));
        CollectorLocation.setCellValueFactory(new PropertyValueFactory("location"));
        tableView1.setItems(getWarehouse());
    }
    
    public ObservableList<Warehouse> getWarehouse()
    {
        ObservableList<Warehouse> list = FXCollections.observableArrayList();
        wList.loadWarehouse();
        Warehouse temp = wList.head;
        while(temp != null)
        {
            if(temp.collectorPoint == true)
            {
                list.add(temp);
            }
            temp = temp.next;
        }
        return list;
    }
    
    public ObservableList<Product> getProducts()
    {
       ObservableList<Product> list = FXCollections.observableArrayList();
       pList.loadProducts();
       Product temp = pList.head;
       while(temp != null)
       {
           if(productType.equals("Electronics"))
           {
               if(temp.productType.equals("Electronics"))
               {
                   list.add(temp);
               }
           }
           if(productType.equals("Eatables"))
           {
               if(temp.productType.equals("Dairy") || temp.productType.equals("Bakery"))
               {
                   list.add(temp);
               }
           }
//           if(productType.equals("Home Assessories"))
//           {
//               JOptionPane.showMessageDialog(null, "Product Type Not Available");
//               return null;
//           }
//           if(productType.equals("Wareables"))
//           {
//               JOptionPane.showMessageDialog(null, "Product Type Not Available");
//               return null;
//           }           
           temp = temp.next;
       }
       return list;
    }

    @FXML
    private void goBackEvent(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("selectProductTypeCustomer.fxml"));
        Parent root = loader.load();

        SelectProductTypeCustomerController mA = loader.getController();
        mA.setUsername(username);
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Select Product Type");

        stage.setScene(scene);
        stage.show();

        Stage stage1 = (Stage) goBack.getScene().getWindow();
        stage1.close();
    }

    @FXML
    private void selectProductEvent(ActionEvent event) 
    {
        try
        {
            int sP = Integer.parseInt(productIdText.getText());
            Product p = pList.searchProducts(sP);
            if(p != null)
            {
                selectedProduct = sP;
                JOptionPane.showMessageDialog(null, "Done");
            }
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error Occured...Try Again");
        }
    }
    
    @FXML
    private void selectCollectorEvent(ActionEvent event) 
    {
            try
            {
                if(!collectorIdText.getText().equals(""))
                {
                    Warehouse wh = wList.searchWarehouseByName(collectorIdText.getText());
                    collectorPointId = wh.location;
                    JOptionPane.showMessageDialog(null, "Done");
                }
            }
            catch(Exception ex)
            {
                JOptionPane.showMessageDialog(null, "Error Occured...Try Again");
            }
    }
        
    @FXML
    private void createOrderEvent(ActionEvent event) 
    {
        try
        {
            if(selectedProduct != -1 && collectorPointId != null)
            {
                Product p = pList.searchProducts(selectedProduct);
                int qty = -1;
                if(!quantityText.getText().equals(""))
                {
                    qty = Integer.parseInt(quantityText.getText());
                    if(p.qty >= qty)
                    {
                        oList.addNewOrder(selectedProduct, qty, username, collectorPointId);
                        JOptionPane.showMessageDialog(null, "Created Order Successfully");
                        
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("userAccount.fxml"));
                        Parent root = loader.load();

                        UserAccountController mA = loader.getController();
                        mA.setUsername(username);

                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setTitle("User Account");

                        stage.setScene(scene);
                        stage.show();

                        Stage stage1 = (Stage) goBack.getScene().getWindow();
                        stage1.close();
                        
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Entered Quantity of Product is not Available");
                    }
                }
            }
            else
                {
                    JOptionPane.showMessageDialog(null, "Error Occured...Try Again");
                }
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "No field can be left empty...Try Again");
        }
    }

    
    
}
