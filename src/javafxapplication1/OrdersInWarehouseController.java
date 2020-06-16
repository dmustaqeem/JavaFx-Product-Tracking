/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.io.IOException;
import java.net.URL;
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
public class OrdersInWarehouseController implements Initializable {
    @FXML
    private TableView<Order> tableView;
    @FXML
    private TableColumn<Order, String> customerId;
    @FXML
    private TableColumn<Order, Integer> orderId;
    @FXML
    private TableColumn<Order, String> status;
    @FXML
    private TableColumn<Order, Date> orderDate;
    @FXML
    private Button goBack;
    @FXML
    private Button deleteOrder;
    @FXML
    private TextField orderIdText;
    
    WarehouseList w = new WarehouseList();
    OrdersList oL = new OrdersList();
    ManagerList mList = new ManagerList();
    ProductList pList = new ProductList();
    
    String warehouseId;
    String cnic;
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
        try
        {
            cnic = Cnic;
            wH = w.searchWarehouseByManager(cnic);
            warehouseId = wH.location;
            customerId.setCellValueFactory(new PropertyValueFactory<>("username"));
            orderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
            status.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
            orderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
            tableView.setItems(getList());
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error Occured");
        }
    }
    
    public ObservableList<Order> getList()
    {
        ObservableList<Order> list = FXCollections.observableArrayList();
        oL.loadOrders();
        Order temp = oL.head;
        
        while(temp != null)
        {
            Product prod = pList.searchProducts(temp.productId);
            if(prod.warehouseId.equals(wH.location))
            {
                list.add(temp);
            }
            temp = temp.next;
        }
        return list;
    }

    @FXML
    private void goBackEvent(ActionEvent event) throws IOException 
    {
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
    private void deleteOrderEvent(ActionEvent event) 
    {
        int orderId = Integer.parseInt(orderIdText.getText());
        try{
            Order o = oL.searchOrder(orderId);
            oL.deleteOrder(o.orderId);
            JOptionPane.showMessageDialog(null, "Deleted Successfully");
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error Occured");
        }
    }
    
}
