/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectakhir;

/**
 *
 * @author Refanda Dicky P
 */
import java.sql.*;
import java.sql.DriverManager;


public class Konektor {
    public static Connection koneksi;
    public static Statement statement;
    
    public static Connection Konektor(){
        if (koneksi == null) {
            try {
                String url = "jdbc:mysql://localhost/aset_gudang";
                String user = "root";
                String password = "";
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                koneksi = DriverManager.getConnection(url, user, password);
                statement = koneksi.createStatement();
                System.out.println("Berhasil");
            } catch (Exception e) {
                System.out.println("Error");
            }
        }
        return koneksi;
    }
    public static void main(String args[]){
        Konektor();
    }  
}
