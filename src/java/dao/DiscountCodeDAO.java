/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.DiscountCodeDTO;
import java.sql.Timestamp;
import org.apache.log4j.Logger;
import utilities.DBConnection;

/**
 *
 * @author Mk
 */
public class DiscountCodeDAO extends DAO {

    private static final Logger logger = Logger.getLogger(DiscountCodeDAO.class);
    public static final int IS_AVAILABLE = 1;
    public static final int IS_UNAVAILABLE = 2;
    public static final int IS_USED = 3;
    public static final int IS_EXPIRED = 4;
    public static final int ERROR = 0;
    public static final String TABLE_NAME = "DiscountCode";

    public int checkDiscountCode(String id, String userEmail) throws Exception {
        int result = ERROR;
        Timestamp expiryDate = null;
        String sql1 = "Select ExpiryDate From " + TABLE_NAME + " Where ID = ?";
        String sql2 = "Select o.ID From " + TABLE_NAME + " d, " + OrderDAO.ORDER_TABLE + " o Where d.ID = ? AND o.userEmail = ? AND o.DiscountCode = d.ID";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql1);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                expiryDate = rs.getTimestamp("ExpiryDate");
                Timestamp now = new Timestamp(System.currentTimeMillis());
                ps = conn.prepareStatement(sql2);
                ps.setString(1, id);
                ps.setString(2, userEmail);
                rs = ps.executeQuery();
                if (rs.next()) {
                    result = IS_USED;
                } else {
                    if (expiryDate.compareTo(now) > 0) {
                        result = IS_AVAILABLE;
                    } else {
                        result = IS_EXPIRED;
                    }
                }
            } else {
                result = IS_UNAVAILABLE;
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public DiscountCodeDTO getDiscountCode(String id) throws Exception {
        DiscountCodeDTO discountCode = null;
        int discountPercentage;
        String sql = "Select DiscountPercentage From " + TABLE_NAME + " Where ID = ?";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                discountPercentage = rs.getInt("DiscountPercentage");
                discountCode = new DiscountCodeDTO(id, discountPercentage);
            }
        } finally {
            closeConnection();
        }
        return discountCode;
    }
}
