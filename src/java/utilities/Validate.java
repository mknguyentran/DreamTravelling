/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import dao.TourDAO;
import dto.errorObject.CreateTourErrorObject;
import java.sql.Timestamp;
import java.util.Calendar;
import javax.servlet.http.Part;
import org.apache.log4j.Logger;

/**
 *
 * @author Mk
 */
public class Validate {

    private static Logger logger = Logger.getLogger(Validate.class);

    public static CreateTourErrorObject validateTour(String id, String name, String departure, String destination, String fromDateString, String toDateString, String priceString, String quotaString, Part image) throws Exception {
        CreateTourErrorObject error = new CreateTourErrorObject();
        if (id == null) {
            error.setIdError("ID not found");
        } else if (id.isEmpty()) {
            error.setIdError("Please enter an ID for the tour");
        } else if (!id.matches("[A-Za-z]{3}[\\d]{3}")) {
            error.setIdError("Invalid ID. ID must be three letters followed by three number digits, e.g. ABC123");
        } else {
            TourDAO tDAO = new TourDAO();
            if (tDAO.isExisted(id)) {
                error.setIdError("There is already a tour with ID " + id.toUpperCase() + " in the database");
            }
        }
        if (name == null) {
            error.setNameError("Name not found");
        } else if (name.isEmpty()) {
            error.setNameError("Please enter a name for the tour");
        }
        if (departure.isEmpty() || destination.isEmpty()) {
            error.setLocationError("Please enter a departure and destination for the tour");
        } else if (departure.equals(destination)) {
            error.setLocationError("Departure and Destination locations can not be the same");
        }
        if (fromDateString == null) {
            error.setFromDateError("From date not found");
        } else if (fromDateString.isEmpty()) {
            error.setFromDateError("Please enter a beginning date for the tour");
        } else if (isBeforeToday(fromDateString)) {
            error.setFromDateError("This beginning date is in the past");
        }
        if (toDateString == null) {
            error.setToDateError("To date not found");
        } else if (toDateString.isEmpty()) {
            error.setToDateError("Please enter a finishing date for the tour");
        }
        if (fromDateString != null && toDateString != null) {
            if (!fromDateString.isEmpty() && !toDateString.isEmpty()) {
                if (compareDate(fromDateString, toDateString) > 0) {
                    error.setToDateError("This finishing date is before the beginning date");
                }
            }
        }
        if (priceString.isEmpty()) {
            error.setPriceError("Please enter a price for the tour");
        } else if (!priceString.matches("[\\d]+(\\.[\\d]+)?")) {
            error.setPriceError("Invalid price");
        }
        if (quotaString.isEmpty()) {
            error.setQuotaError("Please enter a quota for the tour");
        } else if (!quotaString.matches("[\\d]+")) {
            error.setQuotaError("Invalid quota");
        }
        
        if (!image.getSubmittedFileName().isEmpty() && !(image.getSubmittedFileName().endsWith(".jpg") || image.getSubmittedFileName().endsWith(".jpeg") || image.getSubmittedFileName().endsWith(".png"))) {
            error.setImageError("Invalid picture");
        }
        return error;
    }

    public static int compareDate(String beforeDate, String afterDate) throws Exception {
        Calendar bDate = Generate.generateCalendar(beforeDate);
        Calendar aDate = Generate.generateCalendar(afterDate);
        return bDate.compareTo(aDate);
    }

    public static boolean isBeforeToday(String dateString) throws Exception {
        boolean result = false;
        Calendar today = Calendar.getInstance();
        Calendar date = Generate.generateCalendar(dateString);
        if (date.compareTo(today) < 0) {
            result = true;
        }
        return result;
    }

    public static boolean is24HoursOrMoreBeforeNow(Timestamp t1) throws Exception {
        boolean result = false;
        long difference = 0;
        long now = System.currentTimeMillis();
        difference = now - t1.getTime();
        if (difference >= 86400000) {
            result = true;
        }
        return result;
    }
}
