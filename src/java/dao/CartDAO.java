/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.HashMap;
import utilities.DBConnection;

/**
 *
 * @author Mk
 */
public class CartDAO extends DAO {

    public float getTotal(HashMap<String, Integer> cart) throws Exception {
        float total = 0;
        String sql = "Select Price From " + TourDAO.TOUR_TABLE + " Where ID = ?";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            for(String tour : cart.keySet()){
                ps.setString(1, tour);
                rs = ps.executeQuery();
                if(rs.next()){
                    total += rs.getFloat("Price");
                }
            }
        } finally {
            closeConnection();
        }
        return total;
    }
}
