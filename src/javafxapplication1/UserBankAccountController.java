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
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Dawar Mustaqeem
 */
public class UserBankAccountController implements Initializable {
    @FXML
    private TextField accountNumber;
    @FXML
    private TextField credit;
    @FXML
    private TextField debit;
    @FXML
    private TextField balance;
    @FXML
    private TextField newCredit;
    @FXML
    private Button addCredit;
    @FXML
    private Button udpate;
    @FXML
    private Button goBack;
    String username;
    CustomerList cList = new CustomerList();
    userAccountList account = new userAccountList();

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
        userAccount c = account.searchAccountByUsername(username);
        accountNumber.setText(Integer.toString(c.accNo));
        credit.setText(Float.toString(c.credit));
        debit.setText(Float.toString(c.debit));
        balance.setText(Float.toString(c.balance));
    }
    @FXML
    private void addCreditEvent(ActionEvent event) 
    {
        if(!newCredit.getText().equals(""))
        {
            float creditF = Float.parseFloat(newCredit.getText());
            cList.addCredit(username, creditF);
            JOptionPane.showMessageDialog(null, "Credit has been added successfully");
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Please enter credit amount");
        }

    }

    @FXML
    private void updateAccountEvent(ActionEvent event) 
    {
        try
        {
            cList.updateAccount(username);
            JOptionPane.showMessageDialog(null, "Customer account has been successfully updated");
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null,"Error Occured");
        }
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException 
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
    
}
