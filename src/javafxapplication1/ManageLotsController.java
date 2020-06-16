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

/**
 * FXML Controller class
 *
 * @author Dawar Mustaqeem
 */
public class ManageLotsController implements Initializable {
    
    @FXML
    private Button createNewLot;
    @FXML
    private Button goBack;
    @FXML
    private TableView<Lot> tableView;
    @FXML
    private TableColumn<Lot, Integer> lotId;
    @FXML
    private TableColumn<Lot, Integer> occupied;
    @FXML
    private TableColumn<Lot, String> lotStatus;
    @FXML
    private TableColumn<Lot, String> transporterId;
    @FXML
    private TableColumn<Lot, String> truckPlate;
    @FXML
    private TableColumn<Lot, String> timeSlot;
    @FXML
    private TextField lotIdText;
    @FXML
    private Button inspectLot;
    String cnic;
    ManagerList mList = new ManagerList();
    WarehouseList wList = new WarehouseList();
    LotList lots = new LotList();
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
        cnic = Cnic;
        wH = wList.searchWarehouseByManager(cnic);
        lotId.setCellValueFactory(new PropertyValueFactory<>("lotId"));
        occupied.setCellValueFactory(new PropertyValueFactory<>("occupied"));
        lotStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        transporterId.setCellValueFactory(new PropertyValueFactory<>("transporterId"));
        truckPlate.setCellValueFactory(new PropertyValueFactory<>("plate"));
        timeSlot.setCellValueFactory(new PropertyValueFactory<>("timeSlot"));
        tableView.setItems(getList());
        
    }
    public ObservableList<Lot> getList()
    {
        ObservableList<Lot> list = FXCollections.observableArrayList();
        lots.loadLots();
        Lot temp = lots.head;
        while(temp != null)
        {
            list.add(temp);
            temp = temp.next;
        }
        return list;
    }

    @FXML
    private void createNewLotEvent(ActionEvent event) throws IOException 
    {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("createNewLot.fxml"));
            Parent root = loader.load();
            
            CreateNewLotController mA = loader.getController();
            mA.setCnic(cnic);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Manager's Account");
            
            stage.setScene(scene);
            stage.show();
            
            Stage stage1 = (Stage) goBack.getScene().getWindow();
            stage1.close();
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
    private void lotIdText(ActionEvent event) 
    {
        
    }

    @FXML
    private void inspectLotEvent(ActionEvent event) throws IOException 
    {
        int ltId = Integer.parseInt(lotIdText.getText());
        Lot inspLot = null;
        inspLot = lots.searchLot(ltId);
        if(inspLot != null)
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("lotInspectionInManageLots.fxml"));
            Parent root = loader.load();
            
            LotInspectionInManageLotsController mA = loader.getController();
            mA.setCnic(cnic,ltId);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Lot Inspection");
            
            stage.setScene(scene);
            stage.show();
            
            Stage stage1 = (Stage) goBack.getScene().getWindow();
            stage1.close();
        }
    }
    
}
