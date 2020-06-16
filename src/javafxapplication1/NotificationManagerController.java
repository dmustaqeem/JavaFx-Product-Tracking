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

/**
 * FXML Controller class
 *
 * @author Dawar Mustaqeem
 */
public class NotificationManagerController implements Initializable {
    
    
    @FXML
    private TableView<NotificationTransporter> notificationsManager;
    @FXML
    private TableColumn<NotificationTransporter,Integer> notificationId;
    @FXML
    private TableColumn<NotificationTransporter, Date> notifyDate;
    @FXML
    private TableColumn<NotificationTransporter, String> transporterId;
    @FXML
    private TableColumn<NotificationTransporter, Integer> lotId;
    @FXML
    private TableColumn<NotificationTransporter, String> txtMessage;
    @FXML
    private TableColumn<NotificationTransporter,String> notifStatus;
    @FXML
    private TextField notifId;
    @FXML
    private Button goBack;
    @FXML
    private Button inspectNotification;
    @FXML
    private TextField notifIdDelete;
    @FXML
    private Button deleteNotif;
    ManagerList mList = new ManagerList();
    static String cnic;
    notifTransporterList nList = new notifTransporterList();

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
        notifyDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        transporterId.setCellValueFactory(new PropertyValueFactory("lisence"));
        lotId.setCellValueFactory(new PropertyValueFactory("lotId"));
        txtMessage.setCellValueFactory(new PropertyValueFactory("message"));
        notificationId.setCellValueFactory(new PropertyValueFactory("notificationId"));
        notifStatus.setCellValueFactory(new PropertyValueFactory("status"));
        notificationsManager.setItems(getList());
    }
    
    public ObservableList<NotificationTransporter> getList()
    {
        ObservableList<NotificationTransporter> oList = FXCollections.observableArrayList();
        nList.loadNotifications();
        NotificationTransporter temp = nList.head;
        while(temp != null)
        {
            if(temp.mCnic.equals(cnic)){
            oList.add(temp);
            }
            temp = temp.next;
            
        }
        return oList;
    }

    @FXML
    private void goBackEvent(ActionEvent event) throws IOException {
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
    private void inspectNotificationEvent(ActionEvent event) throws IOException {
        int ntId = Integer.parseInt(notifId.getText());
        NotificationTransporter notif = null;
        notif = nList.searchNotif(ntId);
        if(notif != null)
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("inspectNotification.fxml"));
            Parent root = loader.load();
            
            InspectNotificationController mA = loader.getController();
            mA.setCnic(cnic);
            mA.setNotif(ntId);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Inspecting Notification");
            
            stage.setScene(scene);
            stage.show();
            
            Stage stage1 = (Stage) goBack.getScene().getWindow();
            stage1.close();
        }
    }
    
    @FXML
    private void deleteNotifEvent(ActionEvent event)
    {
        int ntId = Integer.parseInt(notifIdDelete.getText());
        
        NotificationTransporter notif = null;
        notif = nList.searchNotif(ntId);
        if(notif != null)
        {
           nList.deleteNotifBackup(ntId);
        }
    }

}
