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
public class OrderDTO implements Serializable {

    private String email, discountCode, statusString;
    private Timestamp createdAt;
    private int status;
    private float total;

    public OrderDTO(String email, String discountCode, Timestamp createdAt, int status) {
        this.email = email;
        this.discountCode = discountCode;
        this.createdAt = createdAt;
        this.status = status;
    }

    public OrderDTO(String discountCode, String statusString, Timestamp createdAt, float total) {
        this.discountCode = discountCode;
        this.statusString = statusString;
        this.createdAt = createdAt;
        this.total = total;
    }

    public String getStatusString() {
        return statusString;
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public String getSimpleCreatedAt() {
        return Convert.toSimpleDateWithHour(createdAt);
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
