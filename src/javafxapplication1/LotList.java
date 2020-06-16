/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Dawar Mustaqeem
 */
public class LotList {
    Lot head;
    Lot tail;
    
    public LotList(){}
    
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
    
    public void addLot(int lotId,String creationDate, int capacity,int occupied, String transporterId,String plate,String status,String currLocation,String timeSlot) throws ParseException
    {
        Lot l = new Lot();
        l.lotId = lotId;
        l.creationDate = new SimpleDateFormat("yyyy-MM-dd").parse(creationDate);
        l.capacity = capacity;
        l.occupied = occupied;
        l.transporterId = transporterId;
        l.plate = plate;
        l.status = status;
        l.timeSlot = timeSlot;
        if(currLocation != null)
        {
            l.currLocation = currLocation;
        }
        else
        {
            l.currLocation = null;
        }
        
        if(isEmpty() == true)
        {
            head = l;
            tail = l;
            head = tail;
            return;
        }
        else
        {
            if(head == tail)
            {
                tail.next = l;
                l.previous = tail;
                tail = tail.next;
                return;
            }
            if(head != tail)
            {
                tail.next = l;
                l.previous = tail;
                tail = tail.next;
                return;
            }
        }
    }
    
    
    public void loadLots()
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
            
            String q = "select * from lot";
            ResultSet rs = stmt.executeQuery(q);
            
            while (rs.next()) {
                int lotId = Integer.parseInt(rs.getString("lotId"));
                String creationDate = (rs.getString("creationDate"));
                int capacity = Integer.parseInt(rs.getString("capacity"));
                String transporterId =  (rs.getString("transporter_id"));
                String plate = (rs.getString("plate"));
                int occupied = Integer.parseInt(rs.getString("occupied"));
                String status = (rs.getString("l_status"));
                String timeSlot = (rs.getString("timeSlot"));

                addLot(lotId,creationDate, capacity, occupied,transporterId,plate,status,null,timeSlot);
                //setLocations();
            }
        }
        catch (Exception e) {
                System.out.println("Error");
        }
    }
    public void addNewLot(int capacity,String transporterId,String plate) throws ParseException
    {
        loadLots();

        String url;
        url = "jdbc:sqlserver://DAWAR-PC;databaseName=productTracking";
        String uN = "dawarDB";
        String Pass = "dawar2669";
        
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url,uN,Pass);
            java.sql.Statement stmt = con.createStatement();

            String q = "exec lotCreation '"+transporterId+"','"+plate+"',"+capacity;
            stmt.execute(q);
        }
        catch (Exception e) 
        {
                System.out.println("Error");
        }
    }
    
    public void loadLotCurrentLocation()
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

            String q = "select * from lot inner join transporterLocation on lot.lotId = transporterLocation.lot_id";
            ResultSet rs = stmt.executeQuery(q);
            while(rs.next()){
               int ltId = Integer.parseInt(rs.getString("lotId"));
               String creationDate = (rs.getString("creationDate"));
               int capacity = Integer.parseInt(rs.getString("capacity"));
               String transporterId =  (rs.getString("transporter_id"));
               String plate = (rs.getString("plate"));
               String location = rs.getString("Current_Location");
               String status = rs.getString("l_status");
               int occupied = Integer.parseInt(rs.getString("occupied"));
               String timeSlot = (rs.getString("timeSlot"));
               addLot(ltId,creationDate, capacity, occupied,transporterId,plate,status,location,timeSlot);
            }
        }
        catch (Exception e) 
        {
                JOptionPane.showMessageDialog(null, "Error Occured!");
        }
    }
    
    public void setAsDilivered(int lotId)
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
            
            String q = "exec setDilivered "+lotId;
            stmt.execute(q);
            JOptionPane.showMessageDialog(null,"Lot id set as dilivered");
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error Occured");
        }
    }

    
    
    public void updateLot(int lotId,String transporterId, String plate)
    {
        loadLots();
        
        String url;
        url = "jdbc:sqlserver://DAWAR-PC;databaseName=productTracking";
        String uN = "dawarDB";
        String Pass = "dawar2669";
        
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url,uN,Pass);
            java.sql.Statement stmt = con.createStatement();
            if(transporterId != null)
            {
                String q = "exec updateTransporter "+lotId+",'"+transporterId+"'";
                stmt.execute(q);
            }
            if(plate != null)
            {
                String q = "exec updateTruck "+lotId+",'"+plate+"'";
                stmt.execute(q);
            }
        }
        catch (Exception e) 
        {
                System.out.println("Error");
        }
    }
    
    public Lot searchLot(int lotId)
    {
        loadLots();
        Lot temp = head;
        while(temp != null)
        {
            if(temp.lotId == lotId)
            {
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }
    
            
}
