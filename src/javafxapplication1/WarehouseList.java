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

/**
 *
 * @author Dawar Mustaqeem
 */
public class WarehouseList {
    Warehouse head;
    Warehouse tail;
    
    WarehouseList(){}
    
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
    
    void addWarehouse(String name, String m_cnic, int capacity, String warehouseType,String location,String nextLocation, String prevLocation, int distNext, int distPrev, boolean collector)
    {
        Warehouse w = new Warehouse();
        w.name = name;
        w.managerId = m_cnic;
        w.capacity = capacity;
        w.warehouseType = warehouseType;
        w.location = location;
        w.nextLocation = nextLocation;
        w.prevLocation = prevLocation;
        w.dist_nextLocation = distNext;
        w.dist_prevLocation = distPrev;
        //if is collector then true else false
        w.collectorPoint = collector;
        
        if(isEmpty())
        {
            head = w;
            tail = w;
            head = tail;
        }
        else{
        
        if(head == tail)
        {
            tail.next = w;
            w.previous = tail;
            tail = tail.next;
        }
        if(head != tail)
        {
            tail.next = w;
            w.previous = tail;
            tail = tail.next;
        }
        }
    }
    
    Warehouse deleteWarehouse(String location)
    {
        Warehouse temp = head;
        Warehouse toRet = null;
        while(temp != null)
        {
            if(temp.location.equals(location))
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
    
    
    void addNewWarehouse(String name, String m_cnic, int capacity, String warehouseType,String location,String nextLocation, String prevLocation, int distNext, int distPrev, boolean collector) throws ClassNotFoundException, SQLException
    {
        loadWarehouse();
        addWarehouse(name,  m_cnic, capacity, warehouseType,location,nextLocation,prevLocation,distNext,distPrev,collector);
        
        String url;
        url = "jdbc:sqlserver://DAWAR-PC;databaseName=productTracking";
        String uN = "dawarDB";
        String Pass = "dawar2669";
        
        int cP = 0;
        if(collector == true)
        {
            cP = 1;
        }
        
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url,uN,Pass);
            java.sql.Statement stmt = con.createStatement();
               
            String q = "exec addWarehouse '"+name+"','"+m_cnic+"',"+capacity+",'"+warehouseType+"','"+location+"','"+nextLocation+"','"+prevLocation+"',"+distNext+","+distPrev+","+cP;
            stmt.execute(q);
        }
        catch(Exception ex)
        {
            System.out.println("Error!");
        }
    }
    
    void loadWarehouse()
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
            
            String q = "select * from warehouse";
            ResultSet rs = stmt.executeQuery(q);
            
            while (rs.next()) {
                String name = (rs.getString("w_name"));
                String m_cnic = (rs.getString("manager_id"));
                int capacity =  Integer.parseInt(rs.getString("capacity"));
                String warehouseType = rs.getString("warehouseType");
                String location = rs.getString("w_location");
                String next_location;
                if(rs.getString("next_location") == null)
                {
                    next_location = null;
                }
                else
                {
                    next_location = rs.getString("next_location");
                }
                String prev_location;
                if(rs.getString("previous_location") == null)
                {
                   prev_location = null;
                }
                else
                {
                   prev_location = rs.getString("previous_location"); 
                }
                
                int distPrev = 0;
                if(rs.getString("distance_from_prev_loc") != null)
                {
                    distPrev = Integer.parseInt(rs.getString("distance_from_prev_loc"));
                }
                int distNext = 0;
                if(rs.getString("distance_from_next_loc") != null)
                {
                   distNext = Integer.parseInt(rs.getString("distance_from_next_loc"));
                }
                int cP = Integer.parseInt(rs.getString("collectorPoint"));
                boolean collector;
                
                if(cP == 0)
                {
                    collector = false;
                }
                else
                {
                    collector = true;
                }
        addWarehouse(name,m_cnic,capacity,warehouseType,location,next_location,prev_location,distPrev,distNext,collector);
            }
        }
        catch (Exception e) {
                System.out.println("Error");
        }
    }
    
    void print()
    {
        Warehouse temp = head;
        while(temp != null)
        {
            System.out.println(temp.name);
            temp = temp.next;
        }
    }
    
    void updateWarehouse(String location,String name, String m_cnic,String warehouseType,int capacity, String nextLocation, String prevLocation, int distNext, int distPrev) throws ClassNotFoundException
    {
        loadWarehouse();
        Warehouse temp = head;
                String url;
        url = "jdbc:sqlserver://DAWAR-PC;databaseName=productTracking;user=dawarDB;password=dawar2669";
        String q = null;
        
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        try(Connection con = DriverManager.getConnection(url); java.sql.Statement stmt = con.createStatement();)
        {
        while(temp != null)
        {
            if(temp.location.equals(location))
            {
                if(name != null)
                {
                    temp.name = name;
                    q = "update warehouse set warehouse.w_name = '"+name+"' where warehouse.w_location = '" +location+"'";
                    stmt.execute(q);
                }
                if(m_cnic != null)
                {
                    temp.managerId = m_cnic;
                    q = "update warehouse set warehouse.manager_id = '"+m_cnic+"' where warehouse.w_location = '" +location+"'";
                    stmt.execute(q);
                }
                if(warehouseType != null)
                {
                    temp.warehouseType = warehouseType;
                    q = "update warehouse set warehouse.warehousetype = '"+warehouseType+"' where warehouse.w_location = '" +location+"'";
                    stmt.execute(q);
                } 
                if(capacity != 0)
                {
                    temp.capacity = capacity;
                    q = "update warehouse set warehouse.capacity = "+capacity+" where warehouse.w_location = '" +location+"'";
                    stmt.execute(q);
                }
                if(nextLocation != null)
                {
                    temp.nextLocation = nextLocation;
                    q = "update warehouse set warehouse.next_location = '"+nextLocation+"' where warehouse.w_location = '" +location+"'";
                    stmt.execute(q);
                }
                if(prevLocation != null)
                {
                    temp.prevLocation = prevLocation;
                    q = "update warehouse set warehouse.prev_location = '"+prevLocation+"' where warehouse.w_location = '" +location+"'";
                    stmt.execute(q);
                }
                if(distNext != 0)
                {
                    temp.dist_nextLocation = distNext;
                    q = "update warehouse set warehouse.distance_from_next_loc = "+distNext+" where warehouse.w_location = '" +location+"'";
                    stmt.execute(q);
                }
                if(distPrev != 0)
                {
                    temp.dist_prevLocation = distPrev;
                    q = "update warehouse set warehouse.distance_from_prev_loc = "+distPrev+" where warehouse.w_location = '" +location+"'";
                    stmt.execute(q);
                }
                
            }
            temp = temp.next;
        }
        }
        catch(Exception ex)
        {
            System.out.println("Error");
        }
    }
    
    void deleteWarehouseFromBackup(String wLocation)
    {
        loadWarehouse();
        Warehouse temp = head;
        
        String url;
        url = "jdbc:sqlserver://DAWAR-PC;databaseName=productTracking";
        String uN = "dawarDB";
        String Pass = "dawar2669";
        
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url,uN,Pass);
            java.sql.Statement stmt = con.createStatement();
            String q = null;
        while(temp != null)
        {
            if(temp.location.equals(wLocation))
            {
                q = "exec deleteWarehouse '"+temp.location+"'";
                stmt.execute(q);
                //deleteWarehouse(String location)
                deleteWarehouse(temp.location);
                break;
            }
            temp = temp.next;
        }
        }
        catch(Exception ex)
        {
            System.out.println("Error");
        }
        
    }
    
//    void updateLocationsOnDelete(String currLocation)
//    {
//        loadWarehouse();
//        Warehouse nextWarehouse;
//        Warehouse previousWarehouse;
//        Warehouse temp = head;
//        int dist =0;
//        while(temp != null)
//        {
//            if(temp.location.equals(currLocation))
//            {
//                if(temp.prevLocation != null && temp.nextLocation != null)
//                {
//                    previousWarehouse = searchWarehouse(temp.prevLocation);
//                    nextWarehouse = searchWarehouse(temp.nextLocation);
//                    previousWarehouse.nextLocation = nextWarehouse.location;
//                    nextWarehouse.prevLocation = previousWarehouse.location;
//                    dist = temp.dist_nextLocation + temp.dist_prevLocation;
//                    nextWarehouse.dist_prevLocation = dist;
//                    previousWarehouse.dist_nextLocation = dist;
//                }
//                if(temp.prevLocation != null && temp.nextLocation == null)
//                {
//                    previousWarehouse = searchWarehouse(temp.prevLocation);
//                    previousWarehouse.nextLocation = null;
//                    previousWarehouse.dist_nextLocation = 0;
//                }
//                if(temp.prevLocation == null && temp.nextLocation != null)
//                {
//                    nextWarehouse = searchWarehouse(temp.nextLocation);
//                    nextWarehouse.prevLocation = null;
//                    nextWarehouse.dist_prevLocation = 0;
//                }
//                break;
//            }
//            temp = temp.next;
//        }
//    }
//    
    
    Warehouse searchWarehouse(String location)
    {
        loadWarehouse();
        Warehouse temp = head;
        
        while(temp != null)
        {
            if(temp.location.equals(location))
            {
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }
    
    Warehouse searchWarehouseByManager(String cnic)
    {
        loadWarehouse();
        Warehouse temp = head;
        
        while(temp != null)
        {
            if(temp.managerId.equals(cnic))
            {
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }
    
    Warehouse searchWarehouseByName(String name)
    {
        loadWarehouse();
        Warehouse temp = head;
        
        while(temp != null)
        {
            if(temp.name.equals(name))
            {
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }
    
}
