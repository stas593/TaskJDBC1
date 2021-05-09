package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import java.util.Properties;

public class Util {
    private static SessionFactory sessionFactory;

    static  {
        // A SessionFactory is set up once for an application!
//        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
//                .build();
        try {
            Configuration configuration = new Configuration();

            Properties settings = new Properties();
            settings.put(Environment.URL, "jdbc:mysql://localhost:3306/mydbtest?useSSL=false");
            settings.put(Environment.USER, "root");
            settings.put(Environment.PASS, "root");
            settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");

            configuration.setProperties(settings);

            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static SessionFactory SessionFactory() {
        return sessionFactory;
    }
}
