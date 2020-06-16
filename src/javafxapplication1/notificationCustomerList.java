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
public class notificationCustomerList {
    notificationCustomer head;
    notificationCustomer tail;
    
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
    
//    String managerId;
//    int notificationId;
//    String customerId;
//    String text;
//    Date notifDate;
//    String status;
    
    public void addNotifCustomer(String managerId, int notifId, String customerId, String text, String date, String status,int lotId) throws ParseException
    {
        notificationCustomer nC = new notificationCustomer();
        nC.managerId = managerId;
        nC.notificationId = notifId;
        nC.customerId = customerId;
        nC.text = text;
        nC.notifDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        nC.status = status;
        nC.lotId = lotId;
        if(isEmpty() == true)
        {
            head = nC;
            tail = nC;
            head = tail;
            return;
        }
        else
        {
            if(head == tail)
            {
                tail.next = nC;
                nC.previous = tail;
                tail = tail.next;
                return;
            }
            if(head != tail)
            {
                tail.next = nC;
                nC.previous = tail;
                tail = tail.next;
                return;
            }
        }
    }
    
    
    public void loadNotifCustomer()
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
            
            String q = "select * from notify_costumer";
            ResultSet rs = stmt.executeQuery(q);
            while (rs.next()) {
                int lotId  = Integer.parseInt(rs.getString("lotId"));
                int notifId = Integer.parseInt(rs.getString("n_id"));
                String date = (rs.getString("nDate"));
                String managerId =  (rs.getString("m_Id"));
                String text = (rs.getString("txt"));
                String customerId = (rs.getString("c_id"));
                String status = (rs.getString("nc_status"));
                
//addNotifCustomer(String managerId, int notifId, String customerId, String text, String date, String status)
                addNotifCustomer(managerId,notifId,customerId,text,date,status,lotId);
            }
        }
        catch (Exception e) {
                System.out.println("Error");
        }
    }
    
    public void addNewNotif(String managerId,int lotId, int notifTransporter)
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
            
            String q = "exec notifyCustomerAgainstLot '"+managerId+"',"+lotId+","+notifTransporter;
            stmt.execute(q);
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error Occured");
        }
    }
    
    public void deleteNotification(String username, int ntId)
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
            
            String q = "exec deleteCustomerNotification '"+username+"',"+ntId;
            stmt.execute(q);
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error Occured");
        }
    }
    
    public void markAsRead(int ntId)
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
            
            String q = "exec setCustomerNotifRead "+ntId;
            stmt.execute(q);
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error Occured");
        }
    }
    
    
    
    public notificationCustomer searchNotif(int notifId)
    {
        loadNotifCustomer();
        notificationCustomer temp = head;
        while(temp != null)
        {
            if(temp.notificationId == notifId)
            {
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }
    
    
}



