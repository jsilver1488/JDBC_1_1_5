package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection cn = Util.getBDConnection(); Statement st = cn.createStatement()) {
            st.executeUpdate("create table if not exists user (" +
                    "id bigint primary key auto_increment," +
                    "name varchar(30)," +
                    "lastname varchar(30)," +
                    "age tinyint)"); //DEFAULT CHARSET=utf8
            System.out.println("Table User created");
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            System.err.println("Table User is not created");
        }
    }

    public void dropUsersTable() {
        try (Connection cn = Util.getBDConnection(); Statement st = cn.createStatement()) {
            st.executeUpdate("drop table if exists user");
            System.out.println("Drop table User succeed");
        } catch (SQLException e) {
            //          throw new RuntimeException(e);
            System.err.println("Error drop table User");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection cn = Util.getBDConnection(); PreparedStatement pst = cn
                .prepareStatement("insert into User (name, lastName, age) VALUES (?, ?, ?)")) {
            pst.setString(1, name);
            pst.setString(2, lastName);
            pst.setByte(3, age);
            pst.executeUpdate();
            System.out.printf("User с именем – %s добавлен в базу данных \n", name);
        } catch (SQLException e) {
            System.err.println("Error insert new User");
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection cn = Util.getBDConnection(); PreparedStatement pst = cn
                .prepareStatement("delete from User where id = ?")) {
            pst.setLong(1, id);
            pst.executeUpdate();
            System.out.println("User successfully deleted fot Id = " + id);
        } catch (SQLException e) {
            System.err.println("Error delete  User for Id = " + id);
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List lst = new ArrayList<User>();
        try (Connection cn = Util.getBDConnection(); ResultSet rs = cn.createStatement()
                .executeQuery("select * from user")) {
            while (rs.next()) {

                User user = new User(rs.getString("Name")
                        , rs.getString("lastName"), rs.getByte("age"));
                user.setId(rs.getLong("id"));
                lst.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Error select all User");
            e.printStackTrace();
        }
        return lst;
    }

    public void cleanUsersTable() {
        try (Connection cn = Util.getBDConnection(); Statement st = cn.createStatement()) {
            st.executeUpdate("truncate table user");
            System.out.println("Table User successfully cleared");
        } catch (SQLException e) {
            System.err.println("Error clear table  User");
            e.printStackTrace();
        }

    }
}
