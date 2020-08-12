/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto.errorObject;

/**
 *
 * @author Mk
 */
public class CreateTourErrorObject {
    private String idError, nameError, locationError, fromDateError, toDateError, priceError, quotaError, imageError; 
    private boolean empty;

    public CreateTourErrorObject() {
        this.empty = true;
    }
    
    public String getIdError() {
        return idError;
    }

    public void setIdError(String idError) {
        this.idError = idError;
        setEmpty(false);
    }
    
    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
    
    public String getNameError() {
        return nameError;
    }

    public void setNameError(String nameError) {
        this.nameError = nameError;
        setEmpty(false);
    }

    public String getLocationError() {
        return locationError;
    }

    public void setLocationError(String locationError) {
        this.locationError = locationError;
        setEmpty(false);
    }

    public String getFromDateError() {
        return fromDateError;
    }

    public void setFromDateError(String fromDateError) {
        this.fromDateError = fromDateError;
        setEmpty(false);
    }

    public String getToDateError() {
        return toDateError;
    }

    public void setToDateError(String toDateError) {
        this.toDateError = toDateError;
        setEmpty(false);
    }

    public String getPriceError() {
        return priceError;
    }

    public void setPriceError(String priceError) {
        this.priceError = priceError;
        setEmpty(false);
    }

    public String getQuotaError() {
        return quotaError;
    }

    public void setQuotaError(String quotaError) {
        this.quotaError = quotaError;
        setEmpty(false);
    }

    public String getImageError() {
        return imageError;
    }

    public void setImageError(String imageError) {
        this.imageError = imageError;
        setEmpty(false);
    }
    
    
}
