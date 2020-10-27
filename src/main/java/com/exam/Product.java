/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Instructor
 */
@ManagedBean
@ViewScoped
public class Product {
    private int id;
    private String name;
    private double price;
    private int qty;

    public Product() {
    }
    
    

    public Product(int id, String name, double price, int qty) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.qty = qty;
    }
    
    
    
    public List<Product> getShowProductList() {
        List<Product> list = new ArrayList<>();
        try {
            
            Connection conn = DBAccess.getConnection();
            PreparedStatement pst = conn.prepareStatement("Select * from products");
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int qty = rs.getInt("qty");                
                Product p = new Product(id, name, price, qty);
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
}
