/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Dawar Mustaqeem
 */
public class InspectNotificationController implements Initializable {
    @FXML
    private Button assignOrder;
    @FXML
    private Button markAsRead;
    @FXML
    private Button goBack;
    @FXML
    private Button inspectLot;
    @FXML
    private Button notifyCustomer;
    @FXML
    private TextField lotId;
    @FXML
    private TextField transporterId;
    @FXML
    private TextField occupied;
    
    static String cnic;
    static int notification;
    ManagerList mList = new ManagerList();
    notifTransporterList nList = new notifTransporterList();
    LotList lots = new LotList();
    orderLotList oList = new orderLotList();
    notificationCustomerList nC = new notificationCustomerList();

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
    public void setNotif(int notif)
    {
        notification = notif;
        NotificationTransporter n = nList.searchNotif(notif);
        lotId.setText(Integer.toString(n.lotId));
        transporterId.setText(n.lisence);
        Lot l = lots.searchLot(n.lotId);
        occupied.setText(Integer.toString(l.occupied));
    }

    @FXML
    private void assignOrdersEvent(ActionEvent event) throws IOException {
        
        try{
            NotificationTransporter n = nList.searchNotif(notification);
            oList.assignNewOrderLot(n.lotId, cnic);
            JOptionPane.showMessageDialog(null, "Assigned Orders to Lot!");
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error Occured");
        }
    }

    @FXML
    private void markAsReadEvent(ActionEvent event) {
        NotificationTransporter n = nList.searchNotif(notification);
        if(n.status.equals("Read"))
        {
            JOptionPane.showMessageDialog(null, "Notification is Read Already");
        }
        else
        {
            String url;
        url = "jdbc:sqlserver://DAWAR-PC;databaseName=productTracking";
        String uN = "dawarDB";
        String Pass = "dawar2669";
        
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url,uN,Pass);
            java.sql.Statement stmt = con.createStatement();
            
            String q = "exec setNotificationRead "+n.notificationId;
            stmt.execute(q);
            JOptionPane.showMessageDialog(null, "Done");
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error Occured");
        }
        }
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("notificationManager.fxml"));
            Parent root = loader.load();

            NotificationManagerController mAC = loader.getController();
            mAC.setCnic(cnic);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Manager's Notification Page");
            
            stage.setScene(scene);
            stage.show();
            
            Stage stage1 = (Stage) goBack.getScene().getWindow();
            stage1.close();
    }

    @FXML
    private void inspectLotEvent(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("inspectLot.fxml"));
            Parent root = loader.load();

            InspectLotController mAC = loader.getController();
            mAC.setNotif(notification);
            mAC.setCnic(cnic);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Inspect Lots");
            
            stage.setScene(scene);
            stage.show();
            
            Stage stage1 = (Stage) goBack.getScene().getWindow();
            stage1.close();
    }
    

    @FXML
    private void notifyCustomerEvent(ActionEvent event) {
        try
        {
            NotificationTransporter n = nList.searchNotif(notification);
            int lotId = n.lotId;
            nC.addNewNotif(cnic, lotId, notification);
            JOptionPane.showMessageDialog(null, "Notified Cutomers");
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error Occured");
        }
    }
    
}
