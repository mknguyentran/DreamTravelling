/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.OrderDTO;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import utilities.DBConnection;

/**
 *
 * @author Mk
 */
public class OrderDAO extends DAO {

    public static final String ORDER_TABLE = "Orders";
    public static final String STATUS_TABLE = "OrderStatus";
    public static final String ORDER_DETAIL_TABLE = "OrderDetail";

    public int createOrder(OrderDTO order) throws Exception {
        int orderID = -1;
        String sql = "Insert Into " + ORDER_TABLE + "(UserEmail, CreatedAt, DiscountCode, Status) values(?,?,?,?)";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, order.getEmail());
            ps.setTimestamp(2, order.getCreatedAt());
            ps.setString(3, order.getDiscountCode());
            ps.setInt(4, order.getStatus());
            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    orderID = rs.getInt(1);
                }
            }
        } finally {
            closeConnection();
        }
        return orderID;
    }

    public boolean addOrderDetail(int orderID, HashMap<String, Integer> cart) throws Exception {
        boolean successful = false;
        int[] result = null;
        String sql = "Insert into " + ORDER_DETAIL_TABLE + "(OrderID, TourID, Amount) values (?,?,?)";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, orderID);
            for (Map.Entry<String, Integer> entry : cart.entrySet()) {
                ps.setString(2, entry.getKey());
                ps.setInt(3, entry.getValue());
                ps.addBatch();
            }
            result = ps.executeBatch();
            successful = true;
            for (int i = 0; i < result.length; i++) {
                if (result[i] != 1) {
                    successful = false;
                    break;
                }
            }
        } finally {
            closeConnection();
        }
        return successful;
    }

    public int getStatusID(String status) throws Exception {
        int statusID = 0;
        String sql = "Select ID From " + STATUS_TABLE + " Where Name = ?";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            rs = ps.executeQuery();
            if (rs.next()) {
                statusID = rs.getInt("ID");
            }
        } finally {
            closeConnection();
        }
        return statusID;
    }

    public List<OrderDTO> loadOrderHistory(String userEmail) throws Exception {
        List<OrderDTO> result = null;
        OrderDTO order = null;
        String discountCode, statusString;
        Timestamp createdAt;
        float total;
        String sql = "Select o.CreatedAt, o.DiscountCode, dbo.TOTAL(o.ID) As Total, s.Name AS Status From " + ORDER_TABLE + " o, " + STATUS_TABLE + " s Where o.UserEmail = ? AND o.Status = s.ID ORDER BY CreatedAt DESC";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, userEmail);
            rs = ps.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                createdAt = rs.getTimestamp("CreatedAt");
                discountCode = rs.getString("DiscountCode");
                statusString = rs.getString("Status");
                total = rs.getFloat("Total");
                order = new OrderDTO(discountCode, statusString, createdAt, total);
                result.add(order);
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean updateOrderStatus() throws Exception {
        boolean successful = false;
        String sql = "EXEC UpdateOrderStatus";
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
