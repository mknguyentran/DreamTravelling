/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author Mk
 */
public class CartDTO implements Serializable {

    private String email;
    private HashMap<String, Integer> cart;
    private DiscountCodeDTO discountCode;

    public CartDTO(String email) {
        this.email = email;
        this.cart = new HashMap<>();
    }

    public int getProductAmountInCart(){
        int amount = 0;
        for(Integer a : cart.values()){
            amount += a;
        }
        return amount;
    }
    
    public boolean add(String ID) throws Exception {
        boolean successful = false;
        int quantity = 1;
        if (this.cart.containsKey(ID)) {
            quantity = this.cart.get(ID) + 1;
        }
        this.cart.put(ID, quantity);
        successful = true;
        return successful;
    }

    public boolean decrease(String ID) throws Exception {
        boolean successful = false;
        if (this.cart.containsKey(ID)) {
            int quantity = this.cart.get(ID) - 1;
            if (quantity > 0) {
                this.cart.put(ID, quantity);
                successful = true;
            } else if (quantity == 0) {
                if (remove(ID)) {
                    successful = true;
                }
            }
        }
        return successful;
    }

    public boolean remove(String ID) throws Exception {
        boolean successful = false;
        if (this.cart.containsKey(ID)) {
            this.cart.remove(ID);
            successful = true;
        }
        return successful;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public HashMap<String, Integer> getCart() {
        return cart;
    }

    public DiscountCodeDTO getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(DiscountCodeDTO discountCode) {
        this.discountCode = discountCode;
    }

    
}
