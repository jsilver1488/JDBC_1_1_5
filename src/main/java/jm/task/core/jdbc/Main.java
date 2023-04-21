package jm.task.core.jdbc;


import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        List<User> users;
        UserDao udao = new UserDaoJDBCImpl();
        udao.createUsersTable();
        udao.saveUser("Кефир", "Пивасоф", (byte) 3);
        udao.saveUser("Алень", "Золотые Рога", (byte) 30);
        udao.saveUser("Изюм", "Сладко-Кислый", (byte) 13);
        udao.saveUser("Ника", "Шишова", (byte) 93);

        users = udao.getAllUsers();
        for (User u: users) {
            System.out.println(u);
        }
        udao.cleanUsersTable();
        udao.dropUsersTable();
    }
}
