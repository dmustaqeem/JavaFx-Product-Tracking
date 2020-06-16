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
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Dawar Mustaqeem
 */
public class SelectProductTypeCustomerController implements Initializable {
    @FXML
    private Label setWelcome;
    @FXML
    private Button goBack;
    @FXML
    private Button homeAssesories;
    @FXML
    private Button electronics;
    @FXML
    private Button eatables;
    @FXML
    private Button wareables;
    String username;
    CustomerList cList = new CustomerList();
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
        Customer c = cList.searchCustomer(username);
        setWelcome.setText("Select Product Type "+c.name);
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
    private void homeAssEvent(ActionEvent event) throws IOException 
    {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("orderCreateUserPage.fxml"));
//        Parent root = loader.load();
//
//        OrderCreateUserPageController mA = loader.getController();
//        mA.setUsername(username,"Home Assessories");
//        
//        Scene scene = new Scene(root);
//        Stage stage = new Stage();
//        stage.setTitle("Select Product Type");
//
//        stage.setScene(scene);
//        stage.show();
//
//        Stage stage1 = (Stage) goBack.getScene().getWindow();
//        stage1.close();
        JOptionPane.showMessageDialog(null, "Product Type Not Available");
    }

    @FXML
    private void electronicsEvent(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("orderCreateUserPage.fxml"));
        Parent root = loader.load();

        OrderCreateUserPageController mA = loader.getController();
        mA.setUsername(username,"Electronics");
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Create Order");

        stage.setScene(scene);
        stage.show();

        Stage stage1 = (Stage) goBack.getScene().getWindow();
        stage1.close();
    }

    @FXML
    private void eatablesEvent(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("orderCreateUserPage.fxml"));
        Parent root = loader.load();

        OrderCreateUserPageController mA = loader.getController();
        mA.setUsername(username,"Eatables");
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Create Order");

        stage.setScene(scene);
        stage.show();

        Stage stage1 = (Stage) goBack.getScene().getWindow();
        stage1.close();
    }

    @FXML
    private void wareablesEvent(ActionEvent event) throws IOException 
    {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("orderCreateUserPage.fxml"));
//        Parent root = loader.load();
//
//        OrderCreateUserPageController mA = loader.getController();
//        mA.setUsername(username,"Wareables");
//        
//        Scene scene = new Scene(root);
//        Stage stage = new Stage();
//        stage.setTitle("Select Product Type");
//
//        stage.setScene(scene);
//        stage.show();
//
//        Stage stage1 = (Stage) goBack.getScene().getWindow();
//        stage1.close();
        
        JOptionPane.showMessageDialog(null, "Product Type Not Available");
    }
    
}
