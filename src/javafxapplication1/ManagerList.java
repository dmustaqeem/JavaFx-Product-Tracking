/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.util.Date;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Date;
import java.util.logging.Logger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
//import java.sql.Statement;

/**
 *
 * @author Dawar Mustaqeem
 */
public class ManagerList {
    Manager head = null;
    Manager tail = null;
     

     
    public ManagerList() {
    }
    
    boolean isEmpty()
    {
        if(head == null && tail == null)
        {
            return true;
        }
        return false;
    }
    
    void addManager(String name,String cnic,int age,String joining, String address,String ph,String m_password) throws ParseException
    {
        Manager manager = new Manager();
        manager.Phone = ph;
        manager.age = age;
        manager.cnic = cnic;
        Date jDate = new SimpleDateFormat("yyyy-MM-dd").parse(joining);
        manager.joinDate = jDate;
        manager.name = name;
        manager.Address = address;
        manager.password = m_password;
        
        if(isEmpty() == true)
        {
            head = manager;
            tail = manager;
            head = tail;
            return;
        }
        else
        {
            if(head == tail)
            {
                tail.next = manager;
                manager.previous = tail;
                tail = tail.next;
                return;
            }
            if(head != tail)
            {
                tail.next = manager;
                manager.previous = tail;
                tail = tail.next;
                return;
            }
        }
    }
    
    Manager deleteManager(String cnic)
    {
        Manager temp = head;
        Manager toRet = null;
        while(temp != null)
        {
            if(temp.cnic.equals(cnic))
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
    
    
    void print()
    {
        Manager temp = head;
        while(temp != null)
        {
            System.out.println(temp.name);
            temp = temp.next;
        }
    }

    void loadManagers()
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
            
            String q = "select * from manager";
            ResultSet rs = stmt.executeQuery(q);
            
            while (rs.next()) {
                String name = (rs.getString("m_name"));
                String phone = (rs.getString("m_phone"));
                String cnic =  (rs.getString("m_cnic"));
                int age = Integer.parseInt(rs.getString("m_age"));
                String jDate = rs.getString("m_employmentDate");
                String address = rs.getString("m_address");
                String pass = rs.getString("m_password");
                addManager(name,cnic,age,jDate,address,phone,pass);
            }
        }
        catch (Exception e) {
                System.out.println("Error");
        }
    }
    //to add new manager exec procedure 
    void addNewManager(String name,String cnic,int age,String address, String ph, String m_pass) throws ParseException
    {
        loadManagers();
        Date d = new Date();
        DateFormat dF = new SimpleDateFormat("yyyy-MM-dd");
        String joinDate = dF.format(d);
        addManager(name,cnic,age,joinDate,address,ph,m_pass);
        
        
        String url;
        url = "jdbc:sqlserver://DAWAR-PC;databaseName=productTracking";
        String uN = "dawarDB";
        String Pass = "dawar2669";
        
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url,uN,Pass);
            java.sql.Statement stmt = con.createStatement();
                //1-Cnic 2- Name 3-Phone 4-Address 5-Age 
            String q = "exec addManager '"+cnic+"','"+name+"','"+ph+"','"+address+"',"+age+" ,'"+m_pass+"'";
            stmt.execute(q);
        }
        catch (Exception e) 
        {
                System.out.println("Error");
        }
        
    }
    
    
    void updateManager(String cnic, String name, String password, String address,String ph) throws ClassNotFoundException
    {
        loadManagers();
        Manager temp = head;
        String url;
        url = "jdbc:sqlserver://DAWAR-PC;databaseName=productTracking;user=dawarDB;password=dawar2669";
        String q = null;
        
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        try(Connection con = DriverManager.getConnection(url); java.sql.Statement stmt = con.createStatement();)
        {
        
        while(temp != null)
        {
            if(temp.cnic.equals(cnic))
            {
                if(name != null)
                {
                    temp.name = name;
                    q = "update manager set manager.m_name = '"+name+"' where m_cnic = '"+cnic+"'";
                    stmt.execute(q);
                }
                
                if(password != null)
                {
                    temp.password = password;
                    q = "update manager set manager.m_password = '"+password+"' where m_cnic = '"+cnic+"'";
                    stmt.execute(q);
                }
                if(address != null)
                {
                    temp.Address = address;
                    q = "update manager set manager.m_address = '"+address+"' where m_cnic = '"+cnic+"'";
                    stmt.execute(q);
                }
                if(ph != null)
                {
                    temp.Phone = ph;
                    q = "update manager set manager.m_phone = '"+ph+"' where m_cnic = '"+cnic+"'";
                    stmt.execute(q);
                }
                break;
            }
            temp = temp.next;
        }
        
        
            }
        catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    
    Manager searchManager(String cnic)
    {
        loadManagers();
        Manager temp = head;
        
        while(temp != null)
        {
            if(temp.cnic.equals(cnic))
            {
                return temp;
            }
            temp = temp.next;
        }
        return null;
    } 
    
    
}
