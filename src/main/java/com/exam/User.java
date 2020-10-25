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
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "user", eager = true)
@ViewScoped
public class User {

    private int id;
    private String name;
    private String mobile;
    private String course;
    private String gender;
    private String[] skill;
    private String address;
    private Connection conn = null;

    public void store() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            conn = DBAccess.getConnection();
            String sql = "insert into users (id, name, mobile, course, gender, skill, address) "
                    + "values (?,?,?,?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            pst.setString(2, name);
            pst.setString(3, mobile);
            pst.setString(4, course);
            pst.setString(5, gender);
            pst.setString(6, Arrays.toString(skill));
            pst.setString(7, address);
            int rs = pst.executeUpdate();

            if (rs > 0) {
                context.addMessage("Success", new FacesMessage("Save successful"));

            } else {
                context.addMessage("Error", new FacesMessage("Save failed!"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<User> getShowUserList() {
        conn = DBAccess.getConnection();
        List<User> userList = new ArrayList<>();
        try {
            //statment
            String sql = "select * from users;";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                User user = new User();
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String mobile = rs.getString("mobile");
                String course = rs.getString("course");
                String gender = rs.getString("gender");
                //String skill = rs.getString("skill");
                String address = rs.getString("address");

                user.setId(id);
                user.setName(name);
                user.setMobile(mobile);
                user.setCourse(course);
                user.setGender(gender);
                user.setAddress(address);
                
                userList.add(user);
            }
        } catch (Exception e) {
        }
        return userList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String[] getSkill() {
        return skill;
    }

    public void setSkill(String[] skill) {
        this.skill = skill;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
