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

/**
 *
 * @author Dawar Mustaqeem
 */
public class notifTransporterList {
    NotificationTransporter head;
    NotificationTransporter tail;

    public notifTransporterList() {
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
    
    public void addNotification(int notificationId,String tLisence, String mCnic, String message, String nDate, String status, int lotId,String plate) throws ParseException
    {
        NotificationTransporter nT = new NotificationTransporter();
        nT.notificationId = notificationId;
        nT.lotId = lotId;
        nT.mCnic = mCnic;
        nT.message = message;
        nT.date = new SimpleDateFormat("yyyy-MM-dd").parse(nDate);
        nT.status = status;
        nT.lisence = tLisence;
        if(plate != null)
        {
            nT.plate = plate;
        }
        else
        {
            nT.plate = null;
        }
        
        if(isEmpty() == true)
        {
            head = nT;
            tail = nT;
            head = tail;
            return;
        }
        else
        {
            if(head == tail)
            {
                tail.next = nT;
                nT.previous = tail;
                tail = tail.next;
                return;
            }
            if(head != tail)
            {
                tail.next = nT;
                nT.previous = tail;
                tail = tail.next;
                return;
            }
        }
    }
    
    public NotificationTransporter deleteNotification(String mCnic, int lotId)
    {
        NotificationTransporter temp = head;
        NotificationTransporter toRet = null;
        while(temp != null)
        {
            if(temp.lotId == lotId && temp.mCnic.equals(mCnic))
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
    
    void loadNotifications()
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
            
            String q = "select * from notify_transporter";
            ResultSet rs = stmt.executeQuery(q);
            while (rs.next()) {
                int ntId  = Integer.parseInt(rs.getString("n_tM"));
                String tCnic = (rs.getString("t_cnic"));
                String mCnic = (rs.getString("m_id"));
                String txt =  (rs.getString("txt_msg"));
                String date =  (rs.getString("nTDate"));
                String status = (rs.getString("nStatus"));
                int lotId = Integer.parseInt(rs.getString("lotId"));
//String tLisence, String mCnic, String message, String nDate, String status, int lotId                
                addNotification(ntId,tCnic,mCnic,txt,date,status,lotId,null);
            }
        }
        catch (Exception e) {
                System.out.println("Error");
        }
    }
    
//String tLisence, String mCnic, String message, String nDate, String status, int lotId   
    public void loadNotifJoinLots() throws ParseException
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
            
            String q = "select * from notify_transporter inner join lot on lot.lotId = notify_transporter.lotId";
            ResultSet rs = stmt.executeQuery(q);
            while(rs.next())
            {
                int ntId  = Integer.parseInt(rs.getString("n_tM"));
                String tCnic = (rs.getString("t_cnic"));
                String mCnic = (rs.getString("m_id"));
                String txt =  (rs.getString("txt_msg"));
                String date =  (rs.getString("nTDate"));
                String status = (rs.getString("nStatus"));
                int lotId = Integer.parseInt(rs.getString("lotId"));
                String plate = (rs.getString("plate"));
//String tLisence, String mCnic, String message, String nDate, String status, int lotId                
                addNotification(ntId,tCnic,mCnic,txt,date,status,lotId,plate);
            }
        }
        catch (Exception e) 
        {
                System.out.println("Error");
        }
        
    }
    
    //select * from notify_transporter inner join lot on lot.lotId = notify_transporter.lotId
            
    public void print()
    {
        loadNotifications();
        NotificationTransporter temp = head;
        while(temp != null)
        {
            System.out.println(temp.mCnic);
            temp = temp.next;
        }
        
    }
    
    public NotificationTransporter searchNotif(int notificationId)
    {
        loadNotifications();
        NotificationTransporter temp = head;
        while(temp != null)
        {
            if(temp.notificationId == notificationId)
            {
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }
    
    public void deleteNotifBackup(int notifId)
    {
        loadNotifications();
        NotificationTransporter nT = searchNotif(notifId);
        
        String url;
        url = "jdbc:sqlserver://DAWAR-PC;databaseName=productTracking";
        String uN = "dawarDB";
        String Pass = "dawar2669";
        
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url,uN,Pass);
            java.sql.Statement stmt = con.createStatement();
            
            String q = "exec deleteNotifTransporter "+notifId;
            stmt.execute(q);
            
        }
        
        catch (Exception e) 
        {
                System.out.println("Error");
        }
        
    }
    
    
    
}

    

