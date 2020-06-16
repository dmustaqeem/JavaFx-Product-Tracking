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

/**
 *
 * @author Dawar Mustaqeem
 */
public class TruckList {
    Truck head;
    Truck tail;
    
    public TruckList(){}
    
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
    
    public void addTruck(String plate, String name, String company, int model,String date) throws ParseException
    {
        Truck t = new Truck();
        t.plate = plate;
        t.name = name;
        t.company = company;
        t.model = model;
        t.purchaseDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        
        if(isEmpty() == true)
        {
            head = t;
            tail = t;
            head = tail;
            return;
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
    public Truck deleteTruck(String plate)
    {
        Truck temp = head;
        Truck toRet = null;
        while(temp != null)
        {
            if(temp.plate.equals(plate))
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
    
    public void loadTrucks()
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
            
            String q = "select * from truck";
            ResultSet rs = stmt.executeQuery(q);
            while (rs.next()) 
            {
                String plate = (rs.getString("plate"));
                String name = (rs.getString("Tname"));
                String company =  (rs.getString("company"));
                int model =  Integer.parseInt(rs.getString("model"));
                String purchaseDate = (rs.getString("purchaseDate"));
                addTruck(plate,name,company,model,purchaseDate);
            }
        }
        catch(Exception ex)
        {
            System.out.println("Error");
        }
    }

    
    public void addNewTruck(String plate, String name, String company, int model) throws ParseException
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
            
            String q = "exec addTruck '"+plate+"','"+name+"','"+company+"',"+model;
            stmt.execute(q);
        }
        catch (Exception e) 
        {
                System.out.println("Error");
        }
        
    }
    
    public void deleteTruckBackup(String plate)
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
            
            String q = "exec deleteTruck '"+plate+"'";
            stmt.execute(q);
        }
        catch (Exception e) 
        {
                System.out.println("Error Occured");
        }
    }
    
    public Truck searchTruck(String plate)
    {
        loadTrucks();
        Truck temp = head;
        while(temp != null)
        {
            if(temp.plate.equals(plate))
            {
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }
}


