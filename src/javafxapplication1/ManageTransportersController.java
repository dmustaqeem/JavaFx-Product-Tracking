/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
public class ManageTransportersController implements Initializable {
    @FXML
    private TableView<Transporter> tableView;
    @FXML
    private TableColumn<Transporter, String> name;
    @FXML
    private TableColumn<Transporter, String> lisence;
    @FXML
    private TableColumn<Transporter, String> phone;
    @FXML
    private TableColumn<Transporter, String> address;
    @FXML
    private TextField lisenceText;
    @FXML
    private Button deleteTransporter;
    @FXML
    private Button createTransporter;
    @FXML
    private Button goBack;
    String cnic;
    ManagerList mList = new ManagerList();
    TransporterList tList = new TransporterList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    } 
    
    public void setCnic(String Cnic) throws ClassNotFoundException, SQLException
    {
        cnic = Cnic;
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        lisence.setCellValueFactory(new PropertyValueFactory<>("lisence"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        tableView.setItems(getList());
    }
    
    public ObservableList<Transporter> getList() throws ClassNotFoundException, SQLException
    {
        ObservableList<Transporter> list = FXCollections.observableArrayList();
        tList.loadTransporter();
        Transporter temp = tList.head;
        while(temp != null)
        {
            list.add(temp);
            temp = temp.next;
        }
        return list;
    }

    @FXML
    private void deleteEvent(ActionEvent event) 
    {
        try
        {
            String lis = null;
            lis = lisenceText.getText();
            if(lis != null)
            {
                Transporter t = tList.searchTransporter(lis);
                tList.deleteTransporterBackup(lis);
                JOptionPane.showMessageDialog(null, "Transporter Deleted Successfully");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Enter Lisence then try to delete");
            }
            
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Enter Correct Lisence");
        }
    }

    @FXML
    private void createTransporter(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addNewTransporter.fxml"));
        Parent root = loader.load();
       
        AddNewTransporterController mA = loader.getController();
        mA.setCnic(cnic);
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Create Transporter Account");

        stage.setScene(scene);
        stage.show();

        Stage stage1 = (Stage) goBack.getScene().getWindow();
        stage1.close();
    }

    @FXML
    private void goBackEvent(ActionEvent event) throws IOException 
    {
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
