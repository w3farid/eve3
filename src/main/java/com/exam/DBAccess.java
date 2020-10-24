/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exam;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Instructor
 */
public class DBAccess {
    private static Connection conn = null;
    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            if(conn == null){
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/eve3db", "root", "123456");
            }
            
        } catch (Exception e) {
        }
        return conn;
    }
    
    
}
