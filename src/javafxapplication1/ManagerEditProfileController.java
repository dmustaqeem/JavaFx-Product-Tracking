/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.io.IOException;
import java.net.URL;
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
import static javafxapplication1.ManagerAccountSettingsController.cnic;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Dawar Mustaqeem
 */
public class ManagerEditProfileController implements Initializable {
    @FXML
    private TextField nameText;
    @FXML
    private TextField addressText;
    @FXML
    private TextField phoneText;
    @FXML
    private Button save;
    @FXML
    private Button goBack;
    static String cnic;
    ManagerList mList = new ManagerList();
    Manager manager;

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
    
    @FXML
    private void saveEvent(ActionEvent event) throws ClassNotFoundException, IOException {
        manager = mList.searchManager(cnic);
        int i=0;
        if(!nameText.getText().equals(""))
        {
            mList.updateManager(cnic, nameText.getText(), null, null, null);
            i++;
        }
        if(!addressText.getText().equals(""))
        {
            mList.updateManager(cnic, null, null, addressText.getText(), null);
            i++;
        }
        if(!phoneText.getText().equals(""))
        {
            mList.updateManager(cnic, null, null, null, phoneText.getText());
            i++;
        }
        if(i!=0)
        {
            JOptionPane.showMessageDialog(null, "All changes are successfully saved");
        }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ManagerAccountSettings.fxml"));
            Parent root = loader.load();

            ManagerAccountSettingsController mAC = loader.getController();
            mAC.setCnic(cnic);
            mAC.setFields();
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Manager Account Settings");
            
            stage.setScene(scene);
            stage.show();
            
            Stage stage1 = (Stage) goBack.getScene().getWindow();
            stage1.close();
    }

    
    @FXML
    private void goBackEvent(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ManagerAccountSettings.fxml"));
            Parent root = loader.load();

            ManagerAccountSettingsController mAC = loader.getController();
            mAC.setCnic(cnic);
            mAC.setFields();
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Manager Account Settings");
            
            stage.setScene(scene);
            stage.show();
            
            Stage stage1 = (Stage) goBack.getScene().getWindow();
            stage1.close();
    }
    
}
