package com.gencal.testmod.message;

import com.gencal.testmod.DatabaseConfig;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static void initialize(DatabaseConfig config) {
        try {
            Configuration configuration = new Configuration();

            configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
            configuration.setProperty("hibernate.connection.url",
                    String.format("jdbc:postgresql://%s:%d/%s",
                            config.getHost(), config.getPort(), config.getDatabase()));
            configuration.setProperty("hibernate.connection.username", config.getUsername());
            configuration.setProperty("hibernate.connection.password", config.getPassword());

            // Hibernate settings
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            configuration.setProperty("hibernate.hbm2ddl.auto", "update");
            configuration.setProperty("hibernate.show_sql", "false");
            configuration.setProperty("hibernate.c3p0.min_size", "5");
            configuration.setProperty("hibernate.c3p0.max_size", config.getMaxPoolSize());
            configuration.setProperty("hibernate.c3p0.timeout", config.getConnectionTimeout());

            configuration.addAnnotatedClass(com.gencal.testmod.message.MessageEntity.class);

            StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("Hibernate initialization failed: " + e.getMessage());
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            throw new IllegalStateException("HibernateUtil not initialized!");
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}