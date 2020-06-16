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
public class orderLotList {
    orderLot head;
    orderLot tail;
    ProductList pList = new ProductList();
    
    LotList l = new LotList();
    ManagerList mList = new ManagerList();
    OrdersList oList = new OrdersList();
    
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
    
//    int lotId;
//    int orderId;
//    Date additionDate;
//    String managerId;
    
    public void addOrderToLot(int lotId, int orderId, String date, String managerId,String prod) throws ParseException
    {
    
        orderLot oL = new orderLot();
        oL.lotId = lotId;
        oL.orderId = orderId;
        oL.additionDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        oL.managerId = managerId;
        if(prod!= null)
        {
            oL.pname = prod;
        }
       if(isEmpty() == true)
        {
            head = oL;
            tail = oL;
            head = tail;
            return;
        }
        else
        {
            if(head == tail)
            {
                tail.next = oL;
                oL.previous = tail;
                tail = tail.next;
                return;
            }
            if(head != tail)
            {
                tail.next = oL;
                oL.previous = tail;
                tail = tail.next;
                return;
            }
        }
    }
    
    
    public void loadOrderLot()
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
            
            String q = "select * from orderLot";
            ResultSet rs = stmt.executeQuery(q);
            while (rs.next()) {
                int lotId  = Integer.parseInt(rs.getString("lotId"));
                int orderId = Integer.parseInt(rs.getString("orderId"));
                String date = (rs.getString("additionDate"));
                String managerId =  (rs.getString("managerId"));
//addOrderToLot(int lotId, int orderId, String date, String managerId)  
               addOrderToLot(lotId,orderId,date,managerId,null);
            }
        }
        catch (Exception e) {
                System.out.println("Error");
        }
    }
    
    
    public void loadWithProducts()
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
            
            String q = "select * from (select * from orderLot inner join orders on orderLot.orderId = orders.o_id)ss inner join product on ss.p_id = product.p_id   ";
            ResultSet rs = stmt.executeQuery(q);
            while (rs.next()) {
                int lotId  = Integer.parseInt(rs.getString("lotId"));
                int orderId = Integer.parseInt(rs.getString("orderId"));
                String date = (rs.getString("additionDate"));
                String managerId =  (rs.getString("managerId"));
                String product = (rs.getString("pName"));
               
//addOrderToLot(int lotId, int orderId, String date, String managerId)  
               addOrderToLot(lotId,orderId,date,managerId,product);
            }
        }
        catch (Exception e) {
                System.out.println("Error");
        }
    }
    
    
    public void assignNewOrderLot(int lotId, String managerId)
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
            
            String q = "exec ord_lot_assignment '"+managerId+"',"+lotId;
            stmt.execute(q);
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error Occured!");
        }
    }
    
    public orderLot searchOrderLot(int orderId, int lotId)
    {
        loadOrderLot();
        orderLot temp = head;
        while(temp != null)
        {
            if(temp.lotId == lotId && temp.orderId == orderId)
            {
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }
    
    
}
