/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.TourDTO;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import utilities.DBConnection;
import utilities.Generate;
import utilities.Logging;

/**
 *
 * @author Mk
 */
public class TourDAO extends DAO {

    private static final Logger logger = Logger.getLogger(TourDAO.class);
    public static final String TOUR_TABLE = "Tour";
    public static final String STATUS_TABLE = "TourStatus";
    public static final int STEP = 5;

    public List<TourDTO> search(int page, String searchDeparture, String searchDestination, Timestamp searchFromDate, Timestamp searchToDate, int searchFromPrice, int searchToPrice) throws Exception {
        List<TourDTO> result = null;
        TourDTO tour = null;
        String searchString = "";
        searchString = Generate.generateSearchString(searchDeparture, searchDestination, searchFromDate, searchToDate, searchFromPrice, searchToPrice, true);
        Logging.logDebug(logger, "searchString", searchString);
        int quota, status, index = 1;
        float price;
        String id, name, departure, destination, image;
        Timestamp fromDate, toDate, importedDate;
        String sql = "Select t.ID, t.Name, t.FromDate, t.ToDate, dep.Name AS Departure, des.Name AS Destination, Price, Quota, Image, ImportedDate, Status From " + TOUR_TABLE + " t, " + LocationDAO.TABLE_NAME + " dep, " + LocationDAO.TABLE_NAME + " des " + searchString + " ORDER BY FromDate ASC OFFSET ? ROWS FETCH NEXT " + STEP + " ROWS ONLY";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            if (searchDeparture != null) {
                    ps.setString(index++, searchDeparture);
            }
            if (searchDestination != null) {
                    ps.setString(index++, searchDestination);
            }
            if (searchFromDate != null) {
                ps.setTimestamp(index++, searchFromDate);
            }
            if (searchToDate != null) {
                ps.setTimestamp(index++, searchToDate);
            }
            if (searchFromPrice > 0) {
                ps.setInt(index++, searchFromPrice);
                logger.debug("searchFromPrice = " + searchFromPrice);
            }
            if (searchToPrice > 0) {
                ps.setInt(index++, searchToPrice);
                logger.debug("searchToPrice = " + searchToPrice);
            }
            logger.debug("page: " + page);
            ps.setInt(index, (page - 1) * STEP);
            rs = ps.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                id = rs.getString("ID");
                name = rs.getString("Name");
                fromDate = rs.getTimestamp("FromDate");
                toDate = rs.getTimestamp("ToDate");
                departure = rs.getString("Departure");
                destination = rs.getString("Destination");
                price = rs.getFloat("Price");
                quota = rs.getInt("Quota");
                image = rs.getString("Image");
                importedDate = rs.getTimestamp("ImportedDate");
                status = rs.getInt("Status");
                tour = new TourDTO(quota, status, price, id, name, departure, destination, image, fromDate, toDate, importedDate);
                result.add(tour);
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public int getPagesAmount(int page, String searchDeparture, String searchDestination, Timestamp searchFromDate, Timestamp searchToDate, int searchFromPrice, int searchToPrice) throws Exception {
        double pagesAmount = 0;
        String searchString = "";
        searchString = Generate.generateSearchString(searchDeparture, searchDestination, searchFromDate, searchToDate, searchFromPrice, searchToPrice, false);
        int index = 1;
        String sql = "Select Count(ID) AS Count From " + TOUR_TABLE + " " + searchString;
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            if (searchDeparture != null) {
                ps.setString(index++, searchDeparture);
            }
            if (searchDestination != null) {
                ps.setString(index++, searchDestination);
            }
            if (searchFromDate != null) {
                ps.setTimestamp(index++, searchFromDate);
            }
            if (searchToDate != null) {
                ps.setTimestamp(index++, searchToDate);
            }
            if (searchFromPrice > 0) {
                ps.setInt(index++, searchFromPrice);
                logger.debug("searchFromPrice = " + searchFromPrice);
            }
            if (searchToPrice > 0) {
                ps.setInt(index++, searchToPrice);
                logger.debug("searchToPrice = " + searchToPrice);
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                pagesAmount = rs.getInt("Count");
                pagesAmount = Math.ceil(pagesAmount / STEP);
            }
        } finally {
            closeConnection();
        }
        return (int) pagesAmount;
    }

    public TourDTO loadTour(String id) throws Exception {
        TourDTO tour = null;
        int quota;
        float price;
        String name, departure, destination, image, statusString;
        Timestamp fromDate, toDate;
        String sql = "Select t.Name, t.FromDate, t.ToDate, dep.Name AS Departure, des.Name AS Destination, t.Price, t.Quota, t.Image, s.Name AS Status From " + TOUR_TABLE + " t, " + LocationDAO.TABLE_NAME + " dep, " + LocationDAO.TABLE_NAME + " des, " + STATUS_TABLE + " s Where t.ID = ? AND t.Departure = dep.ID AND t.Destination = des.ID AND t.Status = s.ID";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                name = rs.getString("Name");
                fromDate = rs.getTimestamp("FromDate");
                toDate = rs.getTimestamp("ToDate");
                departure = rs.getString("Departure");
                destination = rs.getString("Destination");
                price = rs.getFloat("Price");
                quota = rs.getInt("Quota");
                image = rs.getString("Image");
                statusString = rs.getString("Status");
                tour = new TourDTO(quota, statusString, price, id, name, departure, destination, image, fromDate, toDate);
            }
        } finally {
            closeConnection();
        }
        return tour;
    }

    public TourDTO loadTourForCart(String id) throws Exception {
        TourDTO tour = null;
        float price;
        String name, statusString;
        Timestamp fromDate, toDate;
        String sql = "Select t.Name, t.FromDate, t.ToDate, t.Price, s.Name AS Status From " + TOUR_TABLE + " t, " + STATUS_TABLE + " s Where t.ID = ? AND t.Status = s.ID";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                name = rs.getString("Name");
                fromDate = rs.getTimestamp("FromDate");
                toDate = rs.getTimestamp("ToDate");
                price = rs.getFloat("Price");
                statusString = rs.getString("Status");
                tour = new TourDTO(price, id, name, statusString, fromDate, toDate);
            }
        } finally {
            closeConnection();
        }
        return tour;
    }

    public int getAvailableAmount(String id) throws Exception {
        int amount = 0;
        String sql = "Select AvailableAmount From " + TOUR_TABLE + " Where ID = ?";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                amount = rs.getInt("AvailableAmount");
            }
        } finally {
            closeConnection();
        }
        return amount;
    }

    public boolean isAvailable(String id) throws Exception {
        boolean isAvailable = false;
        String status;
        String sql = "Select s.Name AS Status From " + TOUR_TABLE + " t, " + STATUS_TABLE + " s Where t.ID = ? AND t.Status = s.ID";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                status = rs.getString("Status");
                isAvailable = status.equals("available");
            }
        } finally {
            closeConnection();
        }
        return isAvailable;
    }

    public boolean isSufficient(HashMap<String, Integer> cart) throws Exception {
        boolean sufficient = true;
        int availableAmount;
        String statusString;
        String sql = "Select t.AvailableAmount, s.Name AS Status From " + TOUR_TABLE + " t, " + STATUS_TABLE + " s Where t.ID = ? AND t.Status = s.ID";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            for (Map.Entry<String, Integer> entry : cart.entrySet()) {
                ps.setString(1, entry.getKey());
                rs = ps.executeQuery();
                if (rs.next()) {
                    availableAmount = rs.getInt("AvailableAmount");
                    statusString = rs.getString("Status");
                    if (entry.getValue() > availableAmount || !statusString.equals("available")) {
                        sufficient = false;
                        break;
                    }
                }
            }
        } finally {
            closeConnection();
        }
        return sufficient;
    }

    public boolean add(TourDTO tour) throws Exception {
        boolean successful = false;
        String sql = "Insert into " + TOUR_TABLE + "(ID, Name, FromDate, ToDate, Departure, Destination, Price, Quota, Image, ImportedDate, Status) values (?,?,?,?,?,?,?,?,?,?,?)";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, tour.getId());
            ps.setString(2, tour.getName());
            ps.setTimestamp(3, tour.getFromDate());
            ps.setTimestamp(4, tour.getToDate());
            ps.setString(5, tour.getDeparture());
            ps.setString(6, tour.getDestination());
            ps.setFloat(7, tour.getPrice());
            ps.setInt(8, tour.getQuota());
            ps.setString(9, tour.getImage());
            ps.setTimestamp(10, tour.getImportedDate());
            ps.setInt(11, tour.getStatus());
            successful = ps.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return successful;
    }

    public boolean isExisted(String id) throws Exception {
        boolean isExisted = false;
        String sql = "Select ID From " + TOUR_TABLE + " Where ID = ?";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            isExisted = rs.next();
        } finally {
            closeConnection();
        }
        return isExisted;
    }
    
    public boolean updateTourStatus() throws Exception{
        boolean successful = false;
        String sql = "EXEC UpdateTourStatusBasedOnFromDate";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            successful = ps.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return successful;
    }
}
