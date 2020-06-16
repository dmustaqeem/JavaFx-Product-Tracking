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
public class OrdersList {
    Order head;
    Order tail;
    
    
    public OrdersList(){}
    
    public boolean isEmpty()
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
    
    public void addOrder(int orderId,int productId,String username, int quantity, float bill, String orderDate, String status, String destination,String prodName) throws ParseException
    {
        Order o = new Order();
        o.orderId = orderId;
        o.productId = productId;
        o.username = username;
        o.quantity = quantity;
        o.bill = bill;
        o.orderDate = new SimpleDateFormat("yyyy-MM-dd").parse(orderDate);
        o.orderStatus = status;
        o.destination = destination;
        if(prodName != null)
        {
            o.productName = prodName;
        }
        
        if(isEmpty() == true)
        {
            head = o;
            tail = o;
            head = tail;
            return;
        }
        else
        {
            if(head == tail)
            {
                tail.next = o;
                o.previous = tail;
                tail = tail.next;
                return;
            }
            if(head != tail)
            {
                tail.next = o;
                o.previous = tail;
                tail = tail.next;
                return;
            }
        }
    }
    
    public void loadOrders()
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
            
            String q = "select * from orders";
            ResultSet rs = stmt.executeQuery(q);
            while (rs.next()) {
                int orderId = Integer.parseInt(rs.getString("o_id"));
                int productId = Integer.parseInt(rs.getString("p_id"));
                String username =  (rs.getString("username"));
                int qty =  Integer.parseInt(rs.getString("o_qty"));
                float bill = Float.parseFloat(rs.getString("bill"));
                String orderDate = (rs.getString("orderDate"));
                String orderStatus = rs.getString("o_status");
                String destination = rs.getString("destination");
                
//int orderId,int productId,String username, int quantity, int bill, String orderDate, String status, String destination
                addOrder(orderId, productId, username, qty,bill,orderDate,orderStatus, destination,null);
            }
        }
        catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error Occured");
        }
    }
    
    public void loadOrdersProd()
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
            
            String q = "select * from orders inner join product on orders.p_id = product.p_id";
            ResultSet rs = stmt.executeQuery(q);
            while (rs.next()) {
                int orderId = Integer.parseInt(rs.getString("o_id"));
                int productId = Integer.parseInt(rs.getString("p_id"));
                String username =  (rs.getString("username"));
                int qty =  Integer.parseInt(rs.getString("o_qty"));
                float bill = Float.parseFloat(rs.getString("bill"));
                String orderDate = (rs.getString("orderDate"));
                String orderStatus = rs.getString("o_status");
                String destination = rs.getString("destination");
                String prodName = rs.getString("pName");
                
//int orderId,int productId,String username, int quantity, int bill, String orderDate, String status, String destination
               addOrder(orderId, productId, username, qty,bill,orderDate,orderStatus, destination,prodName);
            }
        }
        catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error Occured");
        }
    }
    
    public void addNewOrder(int productId, int quantity, String username, String destination)
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
            //exec orderCreation 4,2,'ali','Lahore Collector'
            String q = "exec orderCreation "+productId+","+quantity+",'"+username+"','"+destination+"'";
            stmt.execute(q);
            
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error Occured");
        }
    }
    
    public void deleteOrder(int orderId)
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
            //exec orderCreation 4,2,'ali','Lahore Collector'
            String q = "exec deleteOrder "+orderId;
            stmt.execute(q);
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error Occured");
        }
    }
    
    public Order searchOrder(int orderId)
    {
        loadOrders();
        Order temp = head;
        while(temp != null)
        {
            if(temp.orderId == orderId)
            {
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }
}
