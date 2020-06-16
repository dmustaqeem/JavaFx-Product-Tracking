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
public class notificationCustomer {
    String managerId;
    int notificationId;
    String customerId;
    String text;
    Date notifDate;
    String status;
    int lotId;
    
    notificationCustomer next;
    notificationCustomer previous;

    public notificationCustomer() {
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getNotifDate() {
        return notifDate;
    }

    public void setNotifDate(Date notifDate) {
        this.notifDate = notifDate;
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


    
    
    
    
    
}
