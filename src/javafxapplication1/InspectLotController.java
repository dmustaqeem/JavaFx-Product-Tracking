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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Dawar Mustaqeem
 */
public class InspectLotController implements Initializable {
    @FXML
    private TableView<orderLot>tableView;
    @FXML
    private TableColumn<orderLot, Integer> lotId;
    @FXML
    private TableColumn<orderLot, Integer> orderId;
    @FXML
    private TableColumn<orderLot, Date> date;
    @FXML
    private TableColumn<orderLot,String>managerId;
    
    @FXML
    private Button goBack;
    String cnic;
    int notification;
    notifTransporterList nList = new notifTransporterList();
    orderLotList oL = new orderLotList();
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    public void setCnic(String Cnic)
    {
        cnic = Cnic;
        lotId.setCellValueFactory(new PropertyValueFactory<>("lotId"));
        orderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        date.setCellValueFactory(new PropertyValueFactory<>("additionDate"));
        managerId.setCellValueFactory(new PropertyValueFactory<>("managerId"));
        tableView.setItems(getList());
    }
    
    public ObservableList<orderLot> getList()
    {
        ObservableList<orderLot> oList = FXCollections.observableArrayList();
        System.out.println(notification);
        NotificationTransporter n = nList.searchNotif(notification);
        oL.loadOrderLot();
        orderLot temp = oL.head;
        while(temp != null)
        {
            if(n.lotId == temp.lotId)
            {
                oList.add(temp);
            }
            temp = temp.next;
        }
        return oList;
    }
    
    public void setNotif(int notif)
    {
        notification = notif;
    }
    @FXML
    private void goBackEvent(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("inspectNotification.fxml"));
            Parent root = loader.load();
            
            InspectNotificationController mA = loader.getController();
            mA.setCnic(cnic);
            mA.setNotif(notification);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Inspecting Notification");
            
            stage.setScene(scene);
            stage.show();
            
            Stage stage1 = (Stage) goBack.getScene().getWindow();
            stage1.close();
    }
    
}
