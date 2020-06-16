/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.util.Date;

/**
 *
 * @author Dawar Mustaqeem
 */
public class NotificationTransporter {
        int notificationId;
        String lisence;
        String mCnic;
        String message;
        Date date;
        String status;
        int lotId;
        String plate;
        
        NotificationTransporter next;
        NotificationTransporter previous;

    public NotificationTransporter() {
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }
    
    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getLisence() {
        return lisence;
    }

    public void setLisence(String lisence) {
        this.lisence = lisence;
    }

    public String getmCnic() {
        return mCnic;
    }

    public void setmCnic(String mCnic) {
        this.mCnic = mCnic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getLotId() {
        return lotId;
    }

    public void setLotId(int lotId) {
        this.lotId = lotId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    
        
        
        
}
