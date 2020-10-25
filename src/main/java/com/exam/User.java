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
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "user", eager = true)
@RequestScoped
public class User {

    private int id;
    private String name;
    private String mobile;
    private String course;
    private String gender;
    private String[] skill;
    private String address;
    private Connection conn = null;
    private boolean isEdit = false;
    List<User> userList = new ArrayList<User>();

    @PostConstruct
    public void init() {
        getShowUserList();
    }

    public String store() {
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
            pst.setString(6, String.join(",", skill));
            pst.setString(7, address);
            int rs = pst.executeUpdate();

            if (rs > 0) {
                context.addMessage("Success", new FacesMessage("Save successful"));
                userList.add(this);
            } else {
                context.addMessage("Error", new FacesMessage("Save failed!"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "index.xhtml";
    }

    public String update() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            conn = DBAccess.getConnection();
            String sql = "update users set name=?, mobile=?, course=?, gender=?, skill=?, address=? "
                    + "where id=?";
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, name);
            pst.setString(2, mobile);
            pst.setString(3, course);
            pst.setString(4, gender);
            pst.setString(5, String.join(",", skill));
            pst.setString(6, address);
            pst.setInt(7, id);
            int rs = pst.executeUpdate();

            if (rs > 0) {
                context.addMessage("Success", new FacesMessage("Update successful"));
                //userList.add(this);
            } else {
                context.addMessage("Error", new FacesMessage("Update failed!"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "index.xhtml";
    }

    public void getShowUserList() {
        conn = DBAccess.getConnection();

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
                String skill = rs.getString("skill");
                String address = rs.getString("address");

                user.setId(id);
                user.setName(name);
                user.setMobile(mobile);
                user.setCourse(course);
                user.setGender(gender);
                user.setSkill(skill.split(","));
                user.setAddress(address);

                userList.add(user);
            }
        } catch (Exception e) {
        }
    }

    public String edit(int id) {
        conn = DBAccess.getConnection();

        try {
            //statment
            String sql = "select * from users where id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                String name = rs.getString("name");
                String mobile = rs.getString("mobile");
                String course = rs.getString("course");
                String gender = rs.getString("gender");
                //String skill = rs.getString("skill");
                String address = rs.getString("address");

                setId(id);
                setName(name);
                setMobile(mobile);
                setCourse(course);
                setGender(gender);
                setAddress(address);

                setIsEdit(true);
            }
        } catch (Exception e) {
        }

        return "/index.xhtml";
    }

    public String delete(int id) {
        conn = DBAccess.getConnection();
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            //statment
            String sql = "delete from users where id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            int rs = pst.executeUpdate();

            if (rs > 0) {
                context.addMessage("success", new FacesMessage("Delete successful"));
            }else{
                context.addMessage("Failed!", new FacesMessage("Delete failed!"));
            }
        } catch (Exception e) {
        }

        return "index.xhtml";
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

    public boolean isIsEdit() {
        return isEdit;
    }

    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

}
