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
public class UserNotificationsController implements Initializable {
    @FXML
    private TextField notifIdText;
    @FXML
    private Button manageOrders;
    @FXML
    private Button goBack;
    @FXML
    private TableView<notificationCustomer> tableView;
    @FXML
    private TableColumn<notificationCustomer, Integer> notificationId;
    @FXML
    private TableColumn<notificationCustomer, Integer> lotId;
    @FXML
    private TableColumn<notificationCustomer, String> status;
    @FXML
    private TableColumn<notificationCustomer, Date> notificationDate;
    @FXML
    private TableColumn<notificationCustomer, String> txt;
    String username;
    @FXML
    private Button allNotifications;
    notificationCustomerList nList = new notificationCustomerList();
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
        notificationId.setCellValueFactory(new PropertyValueFactory<>("notificationId"));
        lotId.setCellValueFactory(new PropertyValueFactory<>("lotId"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        notificationDate.setCellValueFactory(new PropertyValueFactory<>("notifDate"));
        txt.setCellValueFactory(new PropertyValueFactory<>("text"));
        tableView.setItems(getList());
    }
    
    public ObservableList<notificationCustomer> getList()
    {
       ObservableList<notificationCustomer> list = FXCollections.observableArrayList();
       nList.loadNotifCustomer();
       notificationCustomer temp = nList.head;
       while(temp != null)
       {
           if(temp.customerId.equals(username) && temp.status.equals("Unread"))
           {
               list.add(temp);
           }
           temp = temp.next;
       }
       return list;  
    }

    @FXML
    private void manageOrdersEvent(ActionEvent event) 
    {
        try
        {
            int ntID = Integer.parseInt(notifIdText.getText());
            notificationCustomer notif = nList.searchNotif(ntID);
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("userInspectNotification.fxml"));
            Parent root = loader.load();

            UserInspectNotificationController mA = loader.getController();
            mA.setUsername(username,ntID);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("User Inspect Notifications Page");

            stage.setScene(scene);
            stage.show();

            Stage stage1 = (Stage) goBack.getScene().getWindow();
            stage1.close();
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Enter Correct Notification ID");
        }
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
    private void allNotificationsEvent(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("allCustomerNotifications.fxml"));
        Parent root = loader.load();
        
        AllCustomerNotificationsController mA = loader.getController();
        mA.setUsername(username);
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("User Account");

        stage.setScene(scene);
        stage.show();

        Stage stage1 = (Stage) goBack.getScene().getWindow();
        stage1.close();
    }
    
}
