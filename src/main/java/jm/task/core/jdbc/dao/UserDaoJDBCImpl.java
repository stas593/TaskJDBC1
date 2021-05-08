package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.Main;
import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Connection connect = DriverManager.getConnection(Main.URL, Main.USERNAME, Main.PASSWORD);
             Statement statement = connect.createStatement()) {
            statement.execute("CREATE TABLE `mydbtest`.`Users` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NOT NULL,\n" +
                    "  `lastName` VARCHAR(45) NOT NULL,\n" +
                    "  `age` VARCHAR(45) NOT NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);");
        } catch (SQLSyntaxErrorException ex) {
            System.out.println("Таблица не создана");
            System.out.println("Таблица с данным названием уже существует");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connect = DriverManager.getConnection(Main.URL, Main.USERNAME, Main.PASSWORD);
             Statement statement = connect.createStatement()) {
            statement.execute("DROP TABLE users");
        } catch (SQLSyntaxErrorException ex) {
            System.out.println("Таблица не удалена");
            System.out.println("Нет таблицы с указанным называнием");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connect = DriverManager.getConnection(Main.URL, Main.USERNAME, Main.PASSWORD);
             PreparedStatement preparedStatement = connect.prepareStatement("INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connect = DriverManager.getConnection(Main.URL, Main.USERNAME, Main.PASSWORD);
             PreparedStatement preparedStatement = connect.prepareStatement("DELETE from users where id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> res = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(Main.URL, Main.USERNAME, Main.PASSWORD);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * from users");
            while (resultSet.next()){
                User user = new User(resultSet.getString("name"), resultSet.getString("lastName"), (byte) resultSet.getInt("Age"));
                user.setId(resultSet.getLong("id"));
                res.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    public void cleanUsersTable() {
        try (Connection connect = DriverManager.getConnection(Main.URL, Main.USERNAME, Main.PASSWORD);
             Statement statement = connect.createStatement()) {
            statement.execute("DELETE from users");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
