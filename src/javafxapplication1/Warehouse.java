/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

/**
 *
 * @author Dawar Mustaqeem
 */
public class Warehouse {
    String name;
    String managerId;
    int capacity;
    String warehouseType;
    String location;
    String nextLocation;
    String prevLocation;
    int dist_prevLocation;
    int dist_nextLocation;
    boolean collectorPoint;
    
    Warehouse next;
    Warehouse previous;

    public Warehouse() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getWarehouseType() {
        return warehouseType;
    }

    public void setWarehouseType(String warehouseType) {
        this.warehouseType = warehouseType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNextLocation() {
        return nextLocation;
    }

    public void setNextLocation(String nextLocation) {
        this.nextLocation = nextLocation;
    }

    public String getPrevLocation() {
        return prevLocation;
    }

    public void setPrevLocation(String prevLocation) {
        this.prevLocation = prevLocation;
    }

    public int getDist_prevLocation() {
        return dist_prevLocation;
    }

    public void setDist_prevLocation(int dist_prevLocation) {
        this.dist_prevLocation = dist_prevLocation;
    }

    public int getDist_nextLocation() {
        return dist_nextLocation;
    }

    public void setDist_nextLocation(int dist_nextLocation) {
        this.dist_nextLocation = dist_nextLocation;
    }

    public boolean isCollectorPoint() {
        return collectorPoint;
    }

    public void setCollectorPoint(boolean collectorPoint) {
        this.collectorPoint = collectorPoint;
    }
    
}
