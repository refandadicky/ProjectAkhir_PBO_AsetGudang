package projectakhir;


import javax.swing.JOptionPane;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Refanda Dicky P
 */
public class ModelLogin extends Konektor {
    Connection koneksi;
    Statement stat;
    ResultSet rs;
    Konektor conn;
    public boolean isLogin(String username, String password){
        try {
            String sql = "SELECT * FROM user WHERE username='" + username + "' and password='" + password + "'";
            conn = new Konektor();
            koneksi = conn.Konektor();
            stat = koneksi.createStatement();
            rs = stat.executeQuery(sql);
            if (rs.next()) {
                if (username.equals(rs.getString("username")) && password.equals(rs.getString("password"))) {
                    return true;
                }
            } else {
                return false;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
}
