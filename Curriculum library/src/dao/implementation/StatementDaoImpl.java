package dao.implementation;

import DataBase.DataBase;
import com.mysql.cj.jdbc.StatementImpl;
import entity.Statement;
import exception.DaoException;

import javax.swing.plaf.nimbus.State;
import java.sql.*;

public class StatementDaoImpl {
    private final DataBase db = DataBase.getInstance();
    private static final StatementDaoImpl INSTANCE = new StatementDaoImpl();
    private static final String SQL_ADD_STATEMENT = "INSERT INTO statement (idcurriculum, idapprover, status)" +
            " VALUES (?, ?, ?)";
    private static final String SQL_UPDATE_STATEMENT = "UPDATE statement " +
            "SET idcurriculum = ?, idapprover = ?, status = ?" +
            "WHERE idstatement = ?";
    private static final String SQL_DELETE_STATEMENT = "DELETE FROM statement WHERE idstatement = ?";
    private static final String SQL_GET_STATEMENT = "SELECT * FROM statement WHERE idcurriculum=?";
    private StatementDaoImpl(){}
    public static StatementDaoImpl getInstance(){
        return INSTANCE;
    }

    public void addStatement(entity.Statement statemnt) throws DaoException {
        try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_ADD_STATEMENT, java.sql.Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setInt(1, statemnt.getIdCurriculum());
            statement.setInt(2,statemnt.getIdApprover());
            statement.setBoolean(3,statemnt.getStatus());

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next()){
                statemnt.setIdStatement(resultSet.getInt(1));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new DaoException("Error while adding a statement");
        }
    }

    public int updateStatement(entity.Statement statemnt) throws DaoException {
        try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_STATEMENT)
        ) {
            statement.setInt(1, statemnt.getIdCurriculum());
            statement.setInt(2,statemnt.getIdApprover());
            statement.setBoolean(3,statemnt.getStatus());

            return statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new DaoException("Error while updating a statement = " + statemnt, ex);
        }
    }

    public void deleteStatement(int id) throws DaoException {
        try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_DELETE_STATEMENT);
        ) {
            statement.setInt(1,id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new DaoException("Error while delete statement");
        }
    }

    public Statement getStatement(int id) throws DaoException{
        try(
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_GET_STATEMENT);
        ){
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                Statement statement1 = new Statement();
                statement1.setIdStatement(resultSet.getInt(1));
                statement1.setIdCurriculum(resultSet.getInt(2));
                statement1.setIdApprover(resultSet.getInt(3));
                statement1.setStatus(resultSet.getBoolean(4));
                return statement1;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        return null;
    }
}
