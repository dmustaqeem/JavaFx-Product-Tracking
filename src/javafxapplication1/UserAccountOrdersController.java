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
public class UserAccountOrdersController implements Initializable {
    @FXML
    private TableColumn<Order, Integer> orderId;
    @FXML
    private TableColumn<Order, String> status;
    @FXML
    private TableColumn<Order, String> product;
    @FXML
    private TableColumn<Order, Integer> quantity;
    @FXML
    private TableView<Order> tableview;
    @FXML
    private Button goBack;
    @FXML
    private TextField orderIdText;
    String username;
    @FXML
    private TableView<Order> tableView;
    @FXML
    private Button deleteOrder;
    OrdersList oList = new OrdersList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setUsername(String uN)
    {
        username = uN;
        orderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        status.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
        product.setCellValueFactory(new PropertyValueFactory<>("productName"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tableView.setItems(getList());
    }
    
    public ObservableList<Order> getList()
    {
        ObservableList<Order> list = FXCollections.observableArrayList();
        oList.loadOrdersProd();
        Order temp = oList.head;
        while(temp != null)
        {
            if(temp.username.equals(username))
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

    @FXML
    private void deleteOrder(ActionEvent event) 
    {
        try
        {
            int orderId = Integer.parseInt(orderIdText.getText());
            Order o = oList.searchOrder(orderId);
            oList.deleteOrder(orderId);
            JOptionPane.showMessageDialog(null, "Order successully deleted");
            
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Enter Correct Order ID");
        }
    }
    
}
