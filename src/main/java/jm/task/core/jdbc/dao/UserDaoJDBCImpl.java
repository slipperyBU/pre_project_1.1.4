package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static Connection con = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String createTable = "CREATE TABLE IF NOT EXISTS users (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(45) NULL,lastName VARCHAR(45) NULL, age INT(3) NULL)";
        try(PreparedStatement ps = con.prepareStatement(createTable)) {
                ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String dropTable = "DROP TABLE IF EXISTS users";
        try (PreparedStatement ps = con.prepareStatement(dropTable)) {
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String insertUser = "INSERT INTO users (name, lastName, age) values (?, ?, ?)";
        try(PreparedStatement ps = con.prepareStatement(insertUser)) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String deleteUser = "DELETE FROM users WHERE id = ?";
        try(PreparedStatement ps = con.prepareStatement(deleteUser)) {
            ps.setLong(1,id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        String allUsers = "SELECT * FROM USERS";
        List<User> users = new ArrayList<>();
        try(PreparedStatement ps = con.prepareStatement(allUsers)) {
            ResultSet set = ps.executeQuery();

            while (set.next()) {
                User user = new User();
                user.setId(set.getLong(1));
                user.setName(set.getString(2));
                user.setLastName(set.getString(3));
                user.setAge(set.getByte(4));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        String cleanTable = "DELETE FROM users";
        try(PreparedStatement ps = con.prepareStatement(cleanTable)) {
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
