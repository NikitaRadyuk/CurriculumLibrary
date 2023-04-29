package dao.implementation;

import DataBase.DataBase;
import entity.Edit;
import exception.DaoException;

import java.sql.*;

public class EditDaoImpl {
    private final DataBase db = DataBase.getInstance();
    private static final EditDaoImpl INSTANCE = new EditDaoImpl();
    private static final String SQL_ADD_EDIT = "INSERT INTO edit (idcurriculum, idreviewer, text) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE_EDIT = "UPDATE edit " +
            "SET idcurriculum = ?, idreviewer = ?, " +
            "text = ? " +
            "WHERE idedit = ?";
    private static final String SQL_DELETE_EDIT = "DELETE FROM edit WHERE idedit = ?";
    private static final String SQL_GET_EDIT = "SELECT * FROM edit WHERE idcurriculum=?";
    private EditDaoImpl(){}
    public static EditDaoImpl getInstance(){
        return INSTANCE;
    }

    public void addEdit(Edit edit) throws DaoException {
        try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_ADD_EDIT, Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setInt(1, edit.getIdCurriculum());
            statement.setInt(2,edit.getIdReviewer());
            statement.setString(3,edit.getEditText());

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next()){
                edit.setIdEdit(resultSet.getInt(1));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new DaoException("Error while adding an edit");
        }
    }

    public int updateEdit(Edit edit) throws DaoException {
        try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_EDIT)
        ) {
            statement.setInt(1, edit.getIdCurriculum());
            statement.setInt(2,edit.getIdReviewer());
            statement.setString(3,edit.getEditText());
            statement.setInt(4,edit.getIdEdit());

            return statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new DaoException("Error while updating an edit = " + edit, ex);
        }
    }

    public void deleteEdit(int id) throws DaoException {
        try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_DELETE_EDIT);
        ) {
            statement.setInt(1,id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new DaoException("Error while delete statement");
        }
    }

    public Edit getEdit(int id) throws DaoException{
        try(
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_GET_EDIT);
                ){
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                Edit edit = new Edit();
                edit.setIdEdit(resultSet.getInt(1));
                edit.setIdCurriculum(resultSet.getInt(2));
                edit.setIdReviewer(resultSet.getInt(3));
                edit.setEditText(resultSet.getString(4));
                return edit;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return null;
    }
}
