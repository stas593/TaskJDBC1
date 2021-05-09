package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try(Session session = Util.SessionFactory().openSession()){
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE `mydbtest`.`Users` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NOT NULL,\n" +
                    "  `lastName` VARCHAR(45) NOT NULL,\n" +
                    "  `age` INT NOT NULL,\n" +
                    "  PRIMARY KEY (`id`));").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println("Таблица не создана");
            System.out.println("Таблица с данным названием уже существует");
        }


    }

    @Override
    public void dropUsersTable() {
        try(Session session = Util.SessionFactory().openSession();){
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE users").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println("Таблица не удалена");
            System.out.println("Нет таблицы с указанным называнием");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Session session = Util.SessionFactory().openSession();
        session.save(user);
        session.beginTransaction().commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.SessionFactory().openSession();
        User user = session.load(User.class, id);
        session.delete(user);
        session.beginTransaction().commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        try(Session session = Util.SessionFactory().openSession();){
            String sql = "select id, name, lastName, age from USERS";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            List<User> users = query.getResultList();
            session.close();
            return users;
        } catch (Exception ex) {
            return null;
        }

    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.SessionFactory().openSession();
        for(User user : this.getAllUsers()){
            session.delete(user);
        }
        session.beginTransaction().commit();
        System.out.println("Удаление данных из таблицы прошло успешно");
    }
}
