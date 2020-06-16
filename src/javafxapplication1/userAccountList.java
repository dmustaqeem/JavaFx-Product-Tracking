/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author Dawar Mustaqeem
 */
public class userAccountList {
    userAccount head;
    userAccount tail;

    public userAccountList() {
    }
    
    boolean isEmpty()
    {
        if(head == null && tail == null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public void addUserAccount(String username,int accountNumber, float credit, float balance, float debit,String creatDate) throws ParseException
    {
        userAccount account = new userAccount();
        account.accNo = accountNumber;
        account.username = username;
        account.credit = credit;
        account.balance = balance;
        account.debit = debit;
        account.creationDate = new SimpleDateFormat("yyyy-MM-dd").parse(creatDate);
        
        
        if(isEmpty() == true)
        {
            head = account;
            tail = account;
            head = tail;
            return;
        }
        else
        {
            if(head == tail)
            {
                tail.next = account;
                account.previous = tail;
                tail = tail.next;
                return;
            }
            if(head != tail)
            {
                tail.next = account;
                account.previous = tail;
                tail = tail.next;
                return;
            }
        }
    }
    
    public void loadAccounts()
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
            
            String q = "select * from account";
            ResultSet rs = stmt.executeQuery(q);
            
            while (rs.next()) {
                int accountNumber = Integer.parseInt(rs.getString("accountNo"));
                String username = (rs.getString("username"));
                float credit = Float.parseFloat(rs.getString("credit"));
                float balance = Float.parseFloat(rs.getString("balance"));
                float debit = Float.parseFloat(rs.getString("debit"));
                String createDate = (rs.getString("creationDate"));

                addUserAccount(username,accountNumber,credit,balance,debit,createDate);
            }
        }
        catch (Exception e) {
                System.out.println("Error");
        }
    }
    
    public userAccount searchAccount(int accNo)
    {
        loadAccounts();
        userAccount temp = head;
        while(temp != null)
        {
            if(temp.accNo == accNo)
            {
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }
    
    public userAccount searchAccountByUsername(String username)
    {
        loadAccounts();
        userAccount temp = head;
        while(temp != null)
        {
            if(temp.username.equals(username))
            {
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }
    
    
    public void addCredit(String username, float credit)
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
           
            userAccount user = searchAccountByUsername(username);
            if(user != null)
            {
                String q = "exec addCredit '"+username+"',"+credit;  
                stmt.execute(q);
                String update = "exec updateAccount '"+username+"'";
                stmt.execute(update);
            }
            
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error Occured!");
        }
    }
    
   
}
