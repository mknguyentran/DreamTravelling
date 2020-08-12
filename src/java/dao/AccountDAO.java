/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.AccountDTO;
import utilities.DBConnection;

/**
 *
 * @author Mk
 */
public class AccountDAO extends DAO {

    public static final String TABLE_NAME = "Account";
    public static final String ROLE_TABLE_NAME = "Role";

    public AccountDTO login(String email, String password) throws Exception {
        AccountDTO account = null;
        String name, role;
        String sql = "Select a.Name AS Name, r.Name As Role From " + TABLE_NAME + " a, " + ROLE_TABLE_NAME + " r Where Email = ? AND Password = ? AND a.Role = r.ID";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                name = rs.getString("Name");
                role = rs.getString("Role");
                account = new AccountDTO(email, name, role);
            }
        } finally {
            closeConnection();
        }
        return account;
    }

    public AccountDTO login(String email) throws Exception {
        AccountDTO account = null;
        String name, role;
        String sql = "Select a.Name AS Name, r.Name As Role From " + TABLE_NAME + " a, " + ROLE_TABLE_NAME + " r Where Email = ? AND a.Role = r.ID";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                name = rs.getString("Name");
                role = rs.getString("Role");
                account = new AccountDTO(email, name, role);
            }
        } finally {
            closeConnection();
        }
        return account;
    }
    
    public boolean isExisted(String email) throws Exception {
        boolean isExisted = false;
        String sql = "Select Email From " + TABLE_NAME + " Where Email = ?";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            isExisted = rs.next();
        } finally {
            closeConnection();
        }
        return isExisted;
    }
    
    public boolean createAccount(AccountDTO account) throws Exception {
        boolean successful = false;
        String sql1 = "Select ID From " + ROLE_TABLE_NAME + " Where Name = ?";
        String sql2 = "Insert Into " + TABLE_NAME + "(Email, Password, Role, Name) values(?,?,?,?)";
        int roleID;
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql1);
            ps.setString(1, "user");
            rs = ps.executeQuery();
            if (rs.next()) {
                roleID = rs.getInt("ID");
                ps = conn.prepareStatement(sql2);
                ps.setString(1, account.getEmail());
                ps.setString(2, account.getPassword());
                ps.setInt(3, roleID);
                ps.setString(4, account.getName());
                successful = ps.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return successful;
    }
}
