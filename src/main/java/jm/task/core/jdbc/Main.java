package jm.task.core.jdbc;


import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.sql.*;


public class Main {

    public static final String URL = "jdbc:mysql://localhost:3306/mydbtest?useSSL=false";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "root";

    public static void main(String[] args) {
        UserDao dao = new UserDaoHibernateImpl();
        dao.createUsersTable();
        dao.saveUser("Stas", "Loparev", (byte) 23);
        dao.saveUser("Anton", "Stelmakh", (byte) 18);
        dao.saveUser("Andrey", "Ivanon", (byte) 25);
        dao.saveUser("Ivan", "Alekseev", (byte) 20);
        for (User user : dao.getAllUsers()){
            System.out.println(user);
        }
        dao.cleanUsersTable();
        dao.dropUsersTable();
    }
}
