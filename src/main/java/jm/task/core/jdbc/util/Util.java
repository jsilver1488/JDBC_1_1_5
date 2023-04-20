package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    public static Connection getBDConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "root");
    }
    // реализуйте настройку соеденения с БД
}
