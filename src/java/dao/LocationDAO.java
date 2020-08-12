/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.LocationDTO;
import java.util.ArrayList;
import java.util.List;
import utilities.DBConnection;

/**
 *
 * @author Mk
 */
public class LocationDAO extends DAO{
    public static final String TABLE_NAME = "Location";
    
    public List<LocationDTO> getLocationList() throws Exception{
        List<LocationDTO> result = null;
        LocationDTO location = null;
        String id, name;
        String sql = "Select ID, Name From " + TABLE_NAME + " Order By Name ASC";
        try {
            conn=DBConnection.getConnection();
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            result = new ArrayList<>();
            while(rs.next()){
                id=rs.getString("ID");
                name=rs.getString("Name");
                location = new LocationDTO(id, name);
                result.add(location);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
}
