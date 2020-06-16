/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Dawar Mustaqeem
 */
public class ProductList {
    Product head;
    Product tail;

    public ProductList() {
    }
    
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
    
    public void addProduct(int productId, String productType, String productName, float costPerUnit, int qty, String warehouseId)
    {
        Product p = new Product();
        p.productId = productId;
        p.productType = productType;
        p.productName = productName;
        p.costPerUnit = costPerUnit;
        p.qty = qty;
        p.warehouseId = warehouseId;
        
        if(isEmpty() == true)
        {
            head = p;
            tail = p;
            head = tail;
            return;
        }
        else
        {
            if(head == tail)
            {
                tail.next = p;
                p.previous = tail;
                tail = tail.next;
                return;
            }
            if(head != tail)
            {
                tail.next = p;
                p.previous = tail;
                tail = tail.next;
                return;
            }
        }
    }
    
    
    public void loadProducts()
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
            
            String q = "select * from product";
            ResultSet rs = stmt.executeQuery(q);
            while (rs.next()) {
                int productId = Integer.parseInt(rs.getString("p_id"));
                String pName = (rs.getString("pName"));
                String productType =  (rs.getString("pType"));
                int qty = Integer.parseInt(rs.getString("qty"));
                float costPerUnit =  Float.parseFloat(rs.getString("costPerUnit"));
                String warehouseId = rs.getString("warehouseID");
//addProduct(int productId, String productType, String productName, float costPerUnit, int qty, String warehouseId)
                addProduct(productId,productType,pName,costPerUnit,qty,warehouseId);
                
            }
        }
        catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error Occured");
        }
    }
    
    public Product searchProducts(int pId)
    {
        loadProducts();
        Product temp = head;
        while(temp != null)
        {
            if(temp.productId == pId)
            {
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }
    

//--1-Prod Type 2- Prod Name 3-Cost/Unit 4- Prod Qty 5- Prod Warehouse Id     
    public void addNewProduct(String productType, String productName, float costPerUnit, int qty, String warehouseId)
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
            
            String q = "exec addProducts '"+productType+"','"+productName+"',"+costPerUnit+","+qty+",'"+warehouseId+"'";
            stmt.execute(q);
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error Occured");
        }
    }
    
    public void deleteProduct(int prodId)
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
            
            String q = "exec deleteProduct "+prodId;
            stmt.execute(q);
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Wrong Product ID Entered");
        }
    }
}
