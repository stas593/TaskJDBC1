package jm.task.core.jdbc;


import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.*;


public class Main {

    public static final String URL = "jdbc:mysql://localhost:3306/mydbtest?useSSL=false";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "root";

    public static void main(String[] args) {
        try(Connection connect = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connect.createStatement()) {
            UserService userService = new UserServiceImpl();
            userService.createUsersTable();
            userService.saveUser("Stas", "Loparev", (byte) 23);
            userService.saveUser("Anton", "Stelmakh", (byte) 18);
            userService.saveUser("Andrey", "Ivanon", (byte) 25);
            userService.saveUser("Ivan", "Alekseev", (byte) 20);
            for (User user : userService.getAllUsers()){
                System.out.println(user);
            }
            userService.cleanUsersTable();
            userService.dropUsersTable();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
