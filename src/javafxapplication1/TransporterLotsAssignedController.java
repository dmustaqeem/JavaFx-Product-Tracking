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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Dawar Mustaqeem
 */
public class TransporterLotsAssignedController implements Initializable {
    @FXML
    private TableView<Lot> tableView;
    @FXML
    private TableColumn<Lot, Integer> lotId;
    @FXML
    private TableColumn<Lot, String> status;
    @FXML
    private TableColumn<Lot, String> truck;
    @FXML
    private TableColumn<Lot, String> timeSlot;
    @FXML
    private Button goBack;
    String lisence;
    LotList lots = new LotList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        lotId.setCellValueFactory(new PropertyValueFactory<>("lotId"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        truck.setCellValueFactory(new PropertyValueFactory<>("plate"));
        timeSlot.setCellValueFactory(new PropertyValueFactory<>("timeSlot"));
        tableView.setItems(getList());
    }    
    
    public void setLisence(String l)
    {
        lisence = l;
    }
    public ObservableList<Lot> getList()
    {
        ObservableList<Lot> list = FXCollections.observableArrayList();
        lots.loadLots();
        Lot temp = lots.head;
        while(temp != null)
        {
            if(temp.transporterId.equals(lisence))
            {
                list.add(temp);
            }
            temp = temp.next;
        }
        return list;
    }
    
    @FXML
    private void goBackEvent(ActionEvent event) throws IOException, ClassNotFoundException, SQLException 
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("transporterAccount.fxml"));
        Parent root = loader.load();

        TransporterAccountController mA = loader.getController();
        mA.setLisence(lisence);
                    
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Transporter Account");

        stage.setScene(scene);
        stage.show();

        Stage stage1 = (Stage) goBack.getScene().getWindow();
        stage1.close();   
    }
    
}
