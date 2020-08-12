/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import dto.TourDTO;
import java.io.File;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 *
 * @author Mk
 */
public class Generate {

    public static String generateSearchString(String searchDeparture, String searchDestination, Timestamp searchFromDate, Timestamp searchToDate, int searchFromPrice, int searchToPrice, boolean getLocationName) {
        String result = "";
        boolean first = true;
        if (searchDeparture != null) {
            result += "Where Departure = ?";
            first = false;
        }
        if (searchDestination != null) {
            if (first) {
                result += "Where Destination = ?";
                first = false;
            } else {
                result += " AND Destination = ?";
            }
        }
        if (searchFromDate != null) {
            if (first) {
                result += "Where FromDate >= ?";
                first = false;
            } else {
                result += " AND FromDate >= ?";
            }
        }
        if (searchToDate != null) {
            if (first) {
                result += "Where ToDate <= ?";
                first = false;
            } else {
                result += " AND ToDate <= ?";
            }
        }
        if (searchFromPrice > 0) {
            if (first) {
                result += "Where Price >= ?";
                first = false;
            } else {
                result += " AND Price >= ?";
            }
        }
        if (searchToPrice > 0) {
            if (first) {
                result += "Where Price <= ?";
                first = false;
            } else {
                result += " AND Price <= ?";
            }
        }
        if (getLocationName) {
            if (first) {
                result += "Where t.Departure = dep.ID AND t.Destination = des.ID";
            } else {
                result += " AND t.Departure = dep.ID AND t.Destination = des.ID";
            }
        }
        return result;
    }

    public static TourDTO generateTour(String id, String name, String departure, String destination, String fromDateString, String toDateString, String priceString, String quotaString, String image) throws Exception {
        TourDTO tour = null;
        Timestamp fromDate = generateTimestamp(fromDateString);
        Timestamp toDate = generateTimestamp(toDateString);
        float price = Float.parseFloat(priceString);
        int quota = Integer.parseInt(quotaString);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        tour = new TourDTO(quota, 1, price, id, name, departure, destination, image, fromDate, toDate, now);
        return tour;
    }

    public static Timestamp generateTimestamp(String dateString) throws Exception {
        Timestamp result = null;
        int[] dateArray = new int[3];
        String[] data = dateString.split("-");
        for (int i = 0; i < 3; i++) {
            dateArray[i] = Integer.parseInt(data[i]);
        }
        result = new Timestamp(dateArray[0] - 1900, dateArray[1] - 1, dateArray[2], 0, 0, 0, 0);
        return result;
    }

    public static Calendar generateCalendar(String dateString) throws Exception {
        Calendar result = Calendar.getInstance();
        int[] dateArray = new int[3];
        String[] data = dateString.split("-");
        for (int i = 0; i < 3; i++) {
            dateArray[i] = Integer.parseInt(data[i]);
        }
        result.set(dateArray[0], dateArray[1] - 1, dateArray[2]);
        return result;
    }

    public static int[] generatePriceArray(String priceRange) throws Exception {
        int[] result = new int[2];
        String[] data = priceRange.split("-");
        for (int i = 0; i < 2; i++) {
            result[i] = Integer.parseInt(data[i]);
        }
        return result;
    }

    public static String getSaveImagePath(String id, String appPath, String imageFolder) throws Exception {
        appPath = appPath.replace('\\', '/');
        int index = appPath.lastIndexOf("build");
        String correctPath = appPath.substring(0, index);
        correctPath += "web/";
        String fullSavePath = null;
        if (correctPath.endsWith("/")) {
            fullSavePath = correctPath + imageFolder + "/";
        } else {
            fullSavePath = correctPath + "/" + imageFolder;
        }
        File fileSaveDir = new File(fullSavePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
        String fullPath = fullSavePath + id + ".jpg";
        return fullPath;
    }

}
