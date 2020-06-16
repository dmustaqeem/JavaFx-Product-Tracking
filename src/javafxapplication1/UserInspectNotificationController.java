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
public class UserInspectNotificationController implements Initializable {
    @FXML
    private Button goBack;
    @FXML
    private Button deleteNotification;
    @FXML
    private TableView<orderLot> tableView;
    @FXML
    private TableColumn<orderLot, Integer> lotId;
    @FXML
    private TableColumn<orderLot, Integer> orderId;
    @FXML
    private TableColumn<orderLot, String> productName;
    @FXML
    private Button markAsRead;
    @FXML
    private TextField locationText;
    String username;
    int ntID;
    int ltId;
    notificationCustomerList nList = new notificationCustomerList();
    orderLotList oList = new orderLotList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setUsername(String uN,int notifId)
    {
        username = uN;
        ntID = notifId;
        notificationCustomer nC = nList.searchNotif(notifId);
        locationText.setText(nC.text);
        ltId = nC.lotId;
        lotId.setCellValueFactory(new PropertyValueFactory<>("lotId"));
        orderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        productName.setCellValueFactory(new PropertyValueFactory<>("pname"));
        tableView.setItems(getList());
    }
    
    public ObservableList<orderLot> getList()
    {
        ObservableList<orderLot> list = FXCollections.observableArrayList();
        oList.loadWithProducts();
        orderLot temp = oList.head;
        while(temp != null)
        {
            if(temp.lotId == ltId)
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("userNotifications.fxml"));
        Parent root = loader.load();

        UserNotificationsController mA = loader.getController();
        mA.setUsername(username);
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("User Notifications Settings");

        stage.setScene(scene);
        stage.show();

        Stage stage1 = (Stage) goBack.getScene().getWindow();
        stage1.close();
    }

    @FXML
    private void deleteNotifEvent(ActionEvent event) 
    {
        try
        {
            nList.deleteNotification(username, ntID);
            JOptionPane.showMessageDialog(null, "Notification has been successfully deleted");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("userNotifications.fxml"));
            Parent root = loader.load();

            UserNotificationsController mA = loader.getController();
            mA.setUsername(username);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("User Notifications Settings");

            stage.setScene(scene);
            stage.show();

            Stage stage1 = (Stage) goBack.getScene().getWindow();
            stage1.close();
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Can't Delete");
        }
    }

    @FXML
    private void markReadEvent(ActionEvent event) 
    {
        try
        {
           nList.markAsRead(ntID);
           JOptionPane.showMessageDialog(null, "Notification Status has been set to read");
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Can't Delete");
        }
    }
    
}
