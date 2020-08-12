/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.io.Serializable;
import java.sql.Timestamp;
import utilities.Convert;

/**
 *
 * @author Mk
 */
public class TourDTO implements Serializable {

    private int quota, status, amount;
    private float price, total;
    private String id, name, departure, destination, image, statusString;
    private Timestamp fromDate, toDate, importedDate;

    //loadDetail
    public TourDTO(int quota, String statusString, float price, String id, String name, String departure, String destination, String image, Timestamp fromDate, Timestamp toDate) {
        this.quota = quota;
        this.statusString = statusString;
        this.price = price;
        this.id = id;
        this.name = name;
        this.departure = departure;
        this.destination = destination;
        this.image = image;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }
    
    //add
    public TourDTO(int quota, int status, float price, String id, String name, String departure, String destination, String image, Timestamp fromDate, Timestamp toDate, Timestamp importedDate) {
        this.quota = quota;
        this.status = status;
        this.price = price;
        this.id = id;
        this.name = name;
        this.departure = departure;
        this.destination = destination;
        this.image = image;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.importedDate = importedDate;
    }
    
    //loadCart 
    public TourDTO(float price, String id, String name, String statusString, Timestamp fromDate, Timestamp toDate) {
        this.price = price;
        this.id = id;
        this.name = name;
        this.statusString = statusString;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
        this.total = price*amount;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
    
    

    public String getStatusString() {
        return statusString;
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }

    public String getSimpleFromDate() {
        return Convert.toSimpleDate(fromDate);
    }

    public String getSimpleToDate() {
        return Convert.toSimpleDate(toDate);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Timestamp getFromDate() {
        return fromDate;
    }

    public void setFromDate(Timestamp fromDate) {
        this.fromDate = fromDate;
    }

    public Timestamp getToDate() {
        return toDate;
    }

    public void setToDate(Timestamp toDate) {
        this.toDate = toDate;
    }

    public Timestamp getImportedDate() {
        return importedDate;
    }

    public void setImportedDate(Timestamp importedDate) {
        this.importedDate = importedDate;
    }

}
