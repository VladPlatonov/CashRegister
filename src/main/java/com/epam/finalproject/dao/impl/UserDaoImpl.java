package com.epam.finalproject.dao.impl;

import com.epam.finalproject.dao.ConnectionPool;
import com.epam.finalproject.dao.UserDao;
import com.epam.finalproject.dao.exception.DaoException;
import com.epam.finalproject.model.User;
import com.epam.finalproject.model.UserRole;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static  final String SQL_CREATE_USER = "INSERT INTO projectdb.users " +
            "(user_login, user_password, user_name, user_surname, role_id) " +
            "VALUES (?,?,?,?,?)";
    private static final String SQL_GET_BY_LOGIN_AND_PASSWORD = "SELECT * FROM users JOIN user_roles ON users.role_id = user_roles.role_id"+
            " WHERE user_login = ? and user_password = ?";
    private static  final String  SQL_GET_BY_ID = "SELECT * FROM users JOIN user_roles ON users.role_id = user_roles.role_id WHERE user_id = ?";
    private static final String  SQL_GET_BY_LOGIN = "SELECT * FROM users JOIN user_roles ON users.role_id = user_roles.role_id WHERE user_login = ?";
    private static final String SQL_UPDATE_USER = "UPDATE  users SET user_login = ?, user_password = ?, " +
            " role_id = ?, user_name = ? , user_surname = ?" +
            "WHERE user_id = ?";
    private static final String  SQL_DELETE_BY_ID = "DELETE FROM users WHERE user_id = ?";
    private static final String SQL_SELECT_ALL= "SELECT * FROM USERS JOIN user_roles ON users.role_id = user_roles.role_id";
    private static final String SQL_SELECT_OFFSET = "SELECT * FROM users JOIN user_roles ON users.role_id = user_roles.role_id  ORDER BY user_id LIMIT ? OFFSET ?";
    private static final String SQL_SELECT_COUNT = "SELECT COUNT(user_id) AS count FROM users";

    private static final Logger log = Logger.getLogger(UserDaoImpl.class);

    @Override
    public void create(User user) {
        log.info("Enter create user:\n" + user);
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CREATE_USER)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getSurname());
            statement.setInt(5, user.getUserRole().ordinal());
            statement.execute();
        } catch (SQLException e) {
            log.error("Can`t create user");
            throw new DaoException("Can`t create user", e);
        }
        log.info("Done!\n=======");
    }

    public User getByLoginAndPassword(String login, String password) {
        log.info("Enter getByLoginAndPassword userLogin: " + login);
        User user = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(SQL_GET_BY_LOGIN_AND_PASSWORD)) {
            insertStatement.setString(1, login);
            insertStatement.setString(2, password);
            try (ResultSet resultSet = insertStatement.executeQuery()) {
                while (resultSet.next()) {
                    user = new User();
                    user.setLogin(resultSet.getString("user_login"));
                    user.setId(resultSet.getInt("user_id"));
                    user.setPassword(resultSet.getString("user_password"));
                    user.setUserRole(UserRole.valueOf(resultSet.getString("role_description")));
                    user.setName(resultSet.getString("user_name"));
                    user.setSurname(resultSet.getString("user_surname"));
                }
            }
        } catch (SQLException e) {
            log.error("Can`t find by login and password user");
            throw new DaoException("Can`t find by login and password user", e);
        }
        log.info("Done!\n=======");
        return user;
    }


    public User getById(int id) {
        log.info("Enter getById userId:" + id);
        User user = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(SQL_GET_BY_ID)) {
            insertStatement.setInt(1, id);
            try (ResultSet resultSet = insertStatement.executeQuery()) {
                while (resultSet.next()) {
                    user = new User();
                    user.setLogin(resultSet.getString("user_login"));
                    user.setId(resultSet.getInt("user_id"));
                    user.setPassword(resultSet.getString("user_password"));
                    user.setUserRole(UserRole.valueOf(resultSet.getString("role_description")));
                    user.setName(resultSet.getString("user_name"));
                    user.setSurname(resultSet.getString("user_surname"));
                }
            }
        } catch (SQLException e) {
            log.error("Can`t get user");
            throw new DaoException("Can`t get user", e);
        }
        log.info("Done!\n=======");
        return user;
    }


    @Override
    public void update(User user) {
        log.info("Enter update user:\n"+user);
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            try (PreparedStatement insertStatement = connection.prepareStatement(SQL_UPDATE_USER)) {
                insertStatement.setString(1, user.getLogin());
                insertStatement.setString(2, user.getPassword());
                insertStatement.setInt(3, user.getUserRole().ordinal());
                insertStatement.setString(4, user.getName());
                insertStatement.setString(5, user.getSurname());
                insertStatement.setInt(6, user.getId());
                insertStatement.executeUpdate();
                connection.setAutoCommit(false);
                connection.commit();
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                connection.rollback();
                log.error("Can`t update user");
                throw new DaoException("Can`t update user", e);
            }
        } catch (SQLException e) {
            throw new DaoException("Can`t update user", e);
        }
        log.info("Done!\n=======");
    }

    @Override
    public void deleteById(int id) {
        log.info("Enter deleteById userId:"+id);
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BY_ID)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            log.error("Can`t delete user");
            throw new DaoException("Can`t delete user", e);
        }
        log.info("Done!\n=======");
    }

    @Override
    public List<User> findAll() {
        log.info("Enter findAll users:");
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User();
                user.setLogin(resultSet.getString("user_login"));
                user.setId(resultSet.getInt("user_id"));
                user.setPassword(resultSet.getString("user_password"));
                user.setUserRole(UserRole.valueOf(resultSet.getString("role_description")));
                user.setName(resultSet.getString("user_name"));
                user.setSurname(resultSet.getString("user_surname"));
                users.add(user);
            }
        } catch (SQLException e) {
            log.error("Can`t find all users");
            throw new DaoException("Can`t find all users", e);
        }
        log.info("Done!\n=======");
        return users;
    }

    @Override
    public User getByLogin(String login) {
        log.info("Enter getByLogin userLogin: " + login);
        User user = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(SQL_GET_BY_LOGIN)) {
            insertStatement.setString(1, login);
            try (ResultSet resultSet = insertStatement.executeQuery()) {
                while (resultSet.next()) {
                    user = new User();
                    user.setLogin(resultSet.getString("user_login"));
                    user.setId(resultSet.getInt("user_id"));
                    user.setPassword(resultSet.getString("user_password"));
                    user.setUserRole(UserRole.valueOf(resultSet.getString("role_description")));
                    user.setName(resultSet.getString("user_name"));
                    user.setSurname(resultSet.getString("user_surname"));
                }
            }
        } catch (SQLException e) {
            log.error("Can`t find by login and password user");
            throw new DaoException("Can`t find by login and password user", e);
        }
        log.info("Done!\n=======");
        return user;
    }

    @Override
    public List<User> findUsersUsingLimitAndOffset(int currentPage, int recordsPerPage) {
        log.info("Enter findUsersUsingLimitAndOffset users:");
        List<User> users = new ArrayList<>();
        int start = (currentPage) * recordsPerPage - recordsPerPage;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_OFFSET)) {
            statement.setInt(1, recordsPerPage);
            statement.setInt(2, start);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setLogin(resultSet.getString("user_login"));
                user.setId(resultSet.getInt("user_id"));
                user.setPassword(resultSet.getString("user_password"));
                user.setUserRole(UserRole.valueOf(resultSet.getString("role_description")));
                user.setName(resultSet.getString("user_name"));
                user.setSurname(resultSet.getString("user_surname"));
                users.add(user);
            }
        } catch (SQLException e) {
            log.error("Can`t find user with filters");
            throw new DaoException("Can`t find user with filters", e);
        }
        log.info("Done!\n=======");
        return users;
    }

    @Override
    public int getNumberOfRows() {
        log.info("Enter getNumberOfRows users:");
        int numOfRows = 0;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_COUNT)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                numOfRows = resultSet.getInt("count");
            }
        } catch (SQLException e) {
            log.error("Can`t get numbers of rows user");
            throw new DaoException("Can`t get numbers of rows user", e);
        }
        log.info("Done!\n=======");
        return numOfRows;
    }
}
