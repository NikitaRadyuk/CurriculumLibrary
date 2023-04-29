package dao.implementation;

import DataBase.DataBase;
import entity.Curriculum;
import entity.User;
import exception.DaoException;

import javax.swing.*;
import java.sql.*;

public class UserDaoImpl {
    private final DataBase db = DataBase.getInstance();
    private static final UserDaoImpl INSTANCE = new UserDaoImpl();

    private static final String SQL_GET_ALL_USERS = "SELECT * FROM user";
    private static final String SQL_ADD_USER = "INSERT INTO user (name, surname, login, password, email, role) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_USER = "UPDATE user " +
            "SET name = ?, surname = ?, " +
            "login = ?, password = ?, " +
            "email = ?, role = ?" +
            "WHERE iduser = ?";
    private static final String SQL_DELETE_USER = "DELETE FROM user WHERE id = ?";
    private static final String SQL_SELECT_USER_BY_LOGIN =
            "SELECT * " +
                    "FROM user " +
                    "WHERE login=?";

    private static final String SQL_SELECT_USER_BY_ID =
            "SELECT * " +
                    "FROM user " +
                    "WHERE iduser=?";
    private static final String SQL_SELECT_USER_PASSWORD_BY_LOGIN =
            "SELECT password " +
                    "FROM user " +
                    "WHERE login=?";

    private UserDaoImpl(){}
    public static UserDaoImpl getInstance(){
        return INSTANCE;
    }

    public void add(User user) throws DaoException {
        try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_ADD_USER, Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setString(1, user.getName());
            statement.setString(2,user.getSurname());
            statement.setString(3,user.getLogin());
            statement.setString(4,user.getPassword());
            statement.setString(5,user.getEmail());
            statement.setInt(6, user.getRole().ordinal());

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next()){
                user.setId(resultSet.getInt(1));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new DaoException("Error while adding a user");
        }
    }

    public int update(User user) throws DaoException {
        try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER)
        ) {
            statement.setString(1, user.getName());
            statement.setString(2,user.getSurname());
            statement.setString(3,user.getLogin());
            statement.setString(4,user.getPassword());
            statement.setString(5,user.getEmail());
            statement.setInt(6, user.getRole().ordinal());
            statement.setInt(7, user.getUserId());
            return statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new DaoException("Error while updating a user = " + user, ex);
        }
    }

    public void delete(int id) throws DaoException {
            try (
                    Connection connection = db.getConnection();
                    PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER);
            ) {
                statement.setInt(1,id);
                statement.executeUpdate();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                throw new DaoException("Error while delete user");
            }
    }

    public User findUserByLogin(String login) throws DaoException {
        try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_LOGIN)
        ) {
            statement.setString(1, login);
            try{
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return createUser(resultSet);
                }
            }catch (SQLException e){
                throw new DaoException("Error create user", e);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            throw new DaoException("Error while selecting a user", ex);
        }
        return null;
    }

    public User findUserById(Integer id) throws DaoException {
        try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_ID)
        ) {
            statement.setInt(1, id);
            try{
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return createUser(resultSet);
                }
            }catch (SQLException e){
                throw new DaoException("Error create user", e);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            throw new DaoException("Error while selecting a user", ex);
        }
        return null;
    }

    public String findUserPasswordByLogin(String login) throws DaoException {
        try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_PASSWORD_BY_LOGIN)
        ) {
            statement.setString(1, login);
            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String password = resultSet.getString(1);
                    return password;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            throw new DaoException("Error while selecting a password", ex);
        }
        return null;
    }

    public ListModel<User> findAllUsers() throws DaoException {
        DefaultListModel<User> users = new DefaultListModel<>();
        try(
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL_USERS);
        ){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.addElement(createUser(resultSet));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error while selecting all curriculums",e);
        }
        return users;
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt(1));
        user.setName(resultSet.getString(2));
        user.setSurname(resultSet.getString(3));
        user.setLogin(resultSet.getString(4));
        user.setPassword(resultSet.getString(5));
        user.setEmail(resultSet.getString(6));
        user.setRole(resultSet.getInt(7));
        return user;
    }
}
