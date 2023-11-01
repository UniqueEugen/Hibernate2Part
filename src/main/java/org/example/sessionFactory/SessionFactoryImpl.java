package org.example.sessionFactory;

import lombok.NoArgsConstructor;
import myLibrary.console.Console;
import org.example.entity.Car;
import org.example.entity.Company;
import org.example.entity.Person;
import org.example.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

@NoArgsConstructor
public class SessionFactoryImpl {
    private static SessionFactory sessionFactory;


    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Company.class);
                configuration.addAnnotatedClass(Car.class);
                configuration.addAnnotatedClass(Person.class);
                configuration.addAnnotatedClass(User.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                Console.log("Исключение!" + e);
            }
        }
        return sessionFactory;
    }
}
