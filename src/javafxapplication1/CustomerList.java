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
public class CustomerList {
    Customer head;
    Customer tail;

    public CustomerList() {
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
    
    public void addCustomer(String username, String cnic, int age, String name, String password, String address, String phone, String regDate) throws ParseException
    {
        Customer c = new Customer();
        c.username = username;
        c.cnic = cnic;
        c.age = age;
        c.name = name;
        c.password = password;
        c.address = address;
        c.phone = phone;
        c.registrationDate = new SimpleDateFormat("yyyy-MM-dd").parse(regDate);
        
        if(isEmpty() == true)
        {
            head = c;
            tail = c;
            head = tail;
            return;
        }
        else
        {
            if(head == tail)
            {
                tail.next = c;
                c.previous = tail;
                tail = tail.next;
                return;
            }
            if(head != tail)
            {
                tail.next = c;
                c.previous = tail;
                tail = tail.next;
                return;
            }
        }
    }
    
    public void loadCustomer()
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
            
            String q = "select * from customer";
            ResultSet rs = stmt.executeQuery(q);
            
            while (rs.next()) {
                String username = (rs.getString("username"));
                String cnic = (rs.getString("cnic"));
                int age = Integer.parseInt(rs.getString("age"));
                String name =  (rs.getString("uName"));
                String password = (rs.getString("uPassword"));
                String address = (rs.getString("cAddress"));
                String phone = (rs.getString("phone"));
                String regDate = (rs.getString("registrationDate"));
                
                addCustomer(username,cnic,age,name,password,address,phone,regDate);
            }
        }
        catch (Exception e) {
                System.out.println("Error");
        }
    }
    
//    public void editCustomer(String password, String address, String phone,String username)
//    {
//        String url;
//        url = "jdbc:sqlserver://DAWAR-PC;databaseName=productTracking";
//        String uN = "dawarDB";
//        String Pass = "dawar2669";
//        
//        try
//        {
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            Connection con = DriverManager.getConnection(url,uN,Pass);
//            java.sql.Statement stmt = con.createStatement();
//            
//            if(password != null)
//            {
////editCustomerPassword @password as nvarchar(50), @username as nvarchar(50)                
//                String q = "exec editCustomerPassword '"+password+"','"+username+"'";
//                stmt.executeQuery(q);
//            }
//            if(address != null)
//            {
////editCustomerAddress @address as nvarchar(50),@username as nvarchar(50)                
//                String q = "exec editCustomerAddress '"+address+"','"+username+"'";
//                stmt.execute(q);
//            }
//            if(phone != null)
//            {
////editCustomerPhone @username as nvarchar(50), @phone as nvarchar(10)                
//                String q = "exec editCustomerPhone '"+phone+"','"+username+"'";
//                stmt.execute(q);
//            }
//        }
//        catch(Exception ex)
//        {
//            JOptionPane.showMessageDialog(null, "Error Occured");
//        }
//    }
    
//costumerP (@Lusername as nvarchar(50), @Lcnic as nvarchar(50), @Lage as int, @Lname as nvarchar(50), @Lpassword as nvarchar(50), @Laddress as nvarchar(200), @Lphone as nvarchar(10))    
    public void addNewCustomer(String username, String cnic, int age, String name, String password, String address, String phone)
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
            
            String q = "exec costumerP '"+username+"','"+cnic+"',"+age+",'"+name+"','"+password+"','"+address+"','"+phone+"'";
            stmt.execute(q);
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null,"Error Occured");
        }
    }
    
    public Customer searchCustomer(String username)
    {
        loadCustomer();
        Customer temp = head;
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
    
    
    public void updateCustomer(String username, String newUsername, String password, String address,String phone)
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
            if(newUsername != null){
            String q = "exec editUsername '"+username+"','"+newUsername+"'";
            stmt.execute(q);
            }
            if(password != null)
            {
            String q = "exec editCustomerPassword '"+password+"','"+username+"'";
            stmt.execute(q);
            }
            if(address != null)
            {
            String q = "exec editCustomerAddress '"+address+"','"+username+"'";
            stmt.execute(q);    
            }
            if(phone != null)
            {
            String q = "exec editCustomerPhone '"+username+"','"+phone+"'";
            stmt.execute(q); 
            }
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null,"Error Occured");
        }
    }
    
    public void deleteCustomer(String username)
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
            
            String q = "exec deleteCustomer '"+username+"'";
            stmt.execute(q);
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error Occured");
        }
    }
    
    public void addCredit(String username,float credit)
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
      
            String q = "exec addCredit '"+username+"',"+credit;
            stmt.execute(q);
            String r = "updateAccount '"+username+"'";
            stmt.execute(r);
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error Occured");
        }
    }
    
    public void updateAccount(String username)
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
      
            String r = "updateAccount '"+username+"'";
            stmt.execute(r);
            
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error Occured");
        }
    }
}
