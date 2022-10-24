package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.createNativeQuery("CREATE TABLE IF NOT EXISTS users (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(45) NULL,lastName VARCHAR(45) NULL, age INT(3) NULL)").executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        User user = new User(name, lastName, age);
        session.persist(user);
        System.out.println("User с именем - " + name + " добавлен в базу данных");
        session.getTransaction().commit();
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.remove(session.get(User.class,id));
        session.getTransaction().commit();
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users;
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        users = session.createQuery("FROM User", User.class).list();
        session.getTransaction().commit();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.createNativeQuery("DELETE FROM users").executeUpdate();
        session.getTransaction().commit();
    }
}
