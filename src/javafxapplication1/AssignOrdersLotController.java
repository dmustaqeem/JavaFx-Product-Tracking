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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Dawar Mustaqeem
 */
public class AssignOrdersLotController implements Initializable {
    @FXML
    private TableView<orderLot> tableView;
    @FXML
    private TableColumn<orderLot, Integer> lotID;
    @FXML
    private TableColumn<orderLot, Integer> orderID;
    @FXML
    private Button assignOrders;
    @FXML
    private Button goBack;
    String cnic;
    notifTransporterList nList = new notifTransporterList();
    int notifID;
    orderLotList oL = new orderLotList();
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
    
    public void setNotification(int notifId)
    {
        notifID = notifId;
        lotID.setCellValueFactory(new PropertyValueFactory<>("lotId"));
        orderID.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        tableView.setItems(getList());
    }
    
    public ObservableList<orderLot> getList()
    {
        ObservableList<orderLot> oList = FXCollections.observableArrayList();
        oL.loadOrderLot();
        orderLot temp = oL.head;
        NotificationTransporter n = nList.searchNotif(notifID);
        while(temp != null)
        {
            if(temp.lotId == n.lotId)
            {
                oList.add(temp);
            }
            temp = temp.next;
        }
        return oList;
    }

    @FXML
    private void assignOrderEvent(ActionEvent event) 
    {
        try{
            NotificationTransporter n = nList.searchNotif(notifID);
            oL.assignNewOrderLot(n.lotId, cnic);
            JOptionPane.showMessageDialog(null, "Assigned Orders to Lot!");
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error Occured");
        }
    }

    @FXML
    private void goBackEvent(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("inspectNotification.fxml"));
            Parent root = loader.load();
            
            InspectNotificationController mA = loader.getController();
            mA.setCnic(cnic);
            mA.setNotif(notifID);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Inspecting Notification");
            
            stage.setScene(scene);
            stage.show();
            
            Stage stage1 = (Stage) goBack.getScene().getWindow();
            stage1.close();
    }
    
}
