/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author L
 */
public class Conn {
    private Connection connect;
    
    public void setConnections(){
        
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("");
            try {
                connect = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "uaspbd", "uaspbd");
                System.out.println("(class)database ok");
            } catch (SQLException se) {
                System.out.println("Koneksi database gagal " + se);
                System.exit(0);
            }
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Koneksi Driver Tidak ditemukan , Terjadi kesalahan pada " + cnfe);
            System.exit(0);
        }
    }
    
    public Connection getconnections(){
        return connect;
    }
}
