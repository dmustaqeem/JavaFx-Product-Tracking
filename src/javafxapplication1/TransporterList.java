/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Dawar Mustaqeem
 */
public class TransporterList {
    Transporter head = null;
    Transporter tail = null;
    
    TransporterList(){}
    
    boolean isEmpty()
    {
         if(head == null && tail == null)
        {
            return true;
        }
        return false;
    }
    
    public void addTransporter(String cnic,String lisence,String name,String phone,String address,String password)
    {
        Transporter t = new Transporter();
        t.name = name;
        t.cnic = cnic;
        t.lisence = lisence;
        t.phone = phone;
        t.address = address;
        t.password = password;
        
        if(isEmpty() == true)
        {
            head = t;
            tail = t;
            head = tail;
        }
        else
        {
            if(head == tail)
            {
                tail.next = t;
                t.previous = tail;
                tail = tail.next;
                return;
            }
            if(head != tail)
            {
                tail.next = t;
                t.previous = tail;
                tail = tail.next;
                return;
            }
        }
    }
    
    Transporter deleteTransporter(String lisence)
    {
        Transporter temp = head;
        Transporter toRet = null;
        while(temp != null)
        {
            if(temp.lisence.equals(lisence))
            {
                if(head == temp && head.next == null)
                {
                    toRet = head;
                    head = null;
                    tail = null;
                    return toRet;
                }
                if(head == temp && head.next != null)
                {
                    toRet = head;
                    head = head.next;
                    return toRet;
                }                
                if(tail == temp)
                {
                    toRet = tail;
                    tail = tail.previous;
                    return tail;
                }
                else
                {
                    toRet = temp;
                    temp = temp.next;
                    temp.previous = toRet.previous;
                    temp = temp.previous;
                    temp.next = toRet.next;
                    return toRet;
                }
            }
            temp = temp.next;
        }
        return null;
    }
    
    
    
    void loadTransporter() throws ClassNotFoundException, SQLException
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
            
            String q = "select * from transporter";
            ResultSet rs = stmt.executeQuery(q);
            while (rs.next()) {
                String name = (rs.getString("t_name"));
                String phone = (rs.getString("t_phone"));
                String cnic =  (rs.getString("t_cnic"));
                String address = rs.getString("t_address");
                String lisence = rs.getString("lisence");
                String pass = rs.getString("tPassword");
                addTransporter(cnic,lisence,name,phone,address,pass);
            }
        }
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, "Error Occured!");
        }
    }
    //--1-Cnic 2- Name 3- Lisence 4- Address 5- Phone 
    public void addNewTransporter(String cnic, String name, String lisence, String address, String phone, String password)
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
//exec addTransporter 'T130xx','Kami','35201','township','0333xx'            
            String q = "exec addTransporter '"+cnic+"','"+name+"','"+lisence+"','"+address+"','"+phone+"','"+password+"'";
            stmt.execute(q);
        }
        
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, "Error Occured!");
        }
    }
    
    void print() throws ClassNotFoundException, SQLException
    {
        Transporter temp = head;
        while(temp != null)
        {
            System.out.println(temp.name);
            temp = temp.next;
        }
    }
    
    
    Transporter searchTransporter(String lisence) throws ClassNotFoundException, SQLException
    {
        loadTransporter();
        Transporter temp = head;
        
        while(temp != null)
        {
            if(temp.lisence.equals(lisence))
            {
                return temp;
            }
            temp = temp.next;
        }
        return null;
    } 
    
    public void editTransporter(String lisence,String address,String phone, String password)
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
//updateTransporterAddress @lisence as nvarchar(50), @address as nvarchar(200)            
            if(address != null)
            {
                String q = "exec updateTransporterAddress '"+lisence+"','"+address+"'";
                stmt.execute(q);                
            }
//updateTransporterPhone @lisence as nvarchar(50), @phone as nvarchar(10)            
            if(phone != null)
            {
                String q = "exec updateTransporterPhone '"+lisence+"','"+phone+"'";
                stmt.execute(q);                
            }
//updateTransporterPassword @lisence as nvarchar(50), @newPass as nvarchar(50)            
            if(password != null)
            {
                String q = " exec updateTransporterPassword '"+lisence+"','"+password+"'";
                stmt.execute(q);                
            }

        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error Occured");
        }
    }
    
    public void deleteTransporterBackup(String lisence)
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
//deleteTransporter @lisence as nvarchar(50)            
            String q = "exec deleteTransporter '"+lisence+"'";
            stmt.execute(q);
        }
        
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, "Error Occured!");
        }
    }
    
    public int getCurrentLot(String lisence)
    {
        String url;
        url = "jdbc:sqlserver://DAWAR-PC;databaseName=productTracking";
        String uN = "dawarDB";
        String Pass = "dawar2669";
        int val = -1;
        
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url,uN,Pass);
            java.sql.Statement stmt = con.createStatement();
//addTLocation @tt_currentLocation as nvarchar(200), @tLisence as nvarchar(50), @tLplate as nvarchar(10),@trLot_id as int
            String q = "exec getCurrentAssignedLot '"+lisence+"'";
            ResultSet rs = stmt.executeQuery(q);
            
            while(rs.next())
            {
                val = Integer.parseInt(rs.getString("lotId"));
                if(val != -1)
                {
                    return val;
                }
            }
        }
        catch (ClassNotFoundException | SQLException | NumberFormatException e) 
        {
            JOptionPane.showMessageDialog(null, "Error");
        }
        return -1;
    }
    
    
    public void updateLocation(String lisence, String location, String plate, int lotId)
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
//addTLocation @tt_currentLocation as nvarchar(200), @tLisence as nvarchar(50), @tLplate as nvarchar(10),@trLot_id as int
            String q = "exec addTLocation '"+location+"','"+lisence+"','"+plate+"',"+lotId;
            stmt.execute(q);
        }
        
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, "Error Occured!");
        }
    }
}
