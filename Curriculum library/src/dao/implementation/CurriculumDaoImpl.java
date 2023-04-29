package dao.implementation;

import DataBase.DataBase;
import entity.Curriculum;
import entity.Edit;
import exception.DaoException;

import javax.swing.*;
import java.sql.*;

public class CurriculumDaoImpl{
    private final DataBase db = DataBase.getInstance();
    private static final CurriculumDaoImpl INSTANCE = new CurriculumDaoImpl();

    private static final String SQL_GET_ALL_CURRICULUMS = "Select * FROM curriculum";
    private static final String SQL_GET_ALL_CURRICULUMS_WITHOUT_REVIEW = "Select * FROM curriculum";
    private static final String SQL_ADD_CURRICULUM = "INSERT INTO curriculum (file, title, idAuthor, timer, department, departmentcode, authorname, coordination) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_CURRICULUM = "UPDATE curriculum SET file = ?, title = ?, idAuthor = ?, timer = ?,department = ?, departmentcode = ?, reviewers = ?, authorname=?, coordination=? WHERE idcurriculum = ?";
    private static final String SQL_DELETE_CURRICULUM = "DELETE FROM curriculum WHERE idcurriculum = ?";
    private static final String SQL_SELECT_CURRICULUM_BY_AUTHOR = "SELECT * FROM curriculum WHERE authorname LIKE ? ";
    private static final String SQL_SELECT_CURRICULUM_BY_TITLE = "SELECT * FROM curriculum WHERE title LIKE ?";
    private static final String SQL_SELECT_CURRICULUM_BY_TITLE_AND_AUTHOR = "SELECT * FROM curriculum WHERE title LIKE ? AND authorname LIKE ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM curriculum WHERE timer > NOW()";
    private static final String SQL_SELECT_CURRICULUM_BY_ID = "SELECT * FROM curriculum WHERE idcurriculum = ?";

    public CurriculumDaoImpl(){}
    public static CurriculumDaoImpl getInstance(){return INSTANCE;}

    public void add(Curriculum curriculum) throws DaoException {
        try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_ADD_CURRICULUM, Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setBlob(1, curriculum.getFile());
            statement.setString(2,curriculum.getTitle());
            statement.setInt(3,curriculum.getIdAuthor());
            statement.setDate(4, java.sql.Date.valueOf(curriculum.getTimer()));
            statement.setString(5,curriculum.getDepartment());
            statement.setString(6,curriculum.getDepartmentCode());
            statement.setString(7,curriculum.getAuthorName());
            statement.setString(8,curriculum.getCoordination());

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next()){
                curriculum.setIdCurriculum(resultSet.getInt(1));
            }

        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new DaoException("Error while adding a curriculum");
        }
    }

    public int update(Curriculum curriculum) throws DaoException {
        try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_CURRICULUM)
        ) {
            statement.setBlob(1, curriculum.getFile());
            statement.setString(2,curriculum.getTitle());
            statement.setInt(3,curriculum.getIdAuthor());
            statement.setDate(4, java.sql.Date.valueOf(curriculum.getTimer()));
            statement.setString(5,curriculum.getDepartment());
            statement.setString(6,curriculum.getDepartmentCode());
            statement.setString(7,curriculum.getAuthorName());
            statement.setString(8,curriculum.getCoordination());
            return statement.executeUpdate();
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new DaoException("Error while updating a curriculum = " + curriculum, ex);
        }
    }

    public void delete(int id) throws DaoException {
        try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_DELETE_CURRICULUM);
        ) {
            statement.setInt(1,id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new DaoException("Error while delete user");
        }
    }

    public Curriculum getCurriculumById(int idcurr) throws DaoException{
        try(
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_CURRICULUM_BY_ID);
        ){
            statement.setInt(1,idcurr);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                Curriculum curriculum = new Curriculum();
                curriculum.setIdCurriculum(resultSet.getInt(1));
                curriculum.setFile(resultSet.getBlob(2).getBinaryStream());
                curriculum.setTitle(resultSet.getString(3));
                curriculum.setIdAuthor(resultSet.getInt(4));
                curriculum.setTimer(resultSet.getDate(5).toLocalDate());
                curriculum.setDepartment(resultSet.getString(6));
                curriculum.setDepartmentCode(resultSet.getString(7));
                curriculum.setAuthorName(resultSet.getString(8));
                curriculum.setCoordination(resultSet.getString(9));
                return curriculum;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return null;
    }

    public ListModel<Curriculum> findCurriculumsByAuthor(String author) throws DaoException {
        DefaultListModel<Curriculum> booksByAuthor = new DefaultListModel<>();
        try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_CURRICULUM_BY_AUTHOR)
        ) {
            statement.setString(1, author + "%");
            try{
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    booksByAuthor.addElement(createCurriculum(resultSet));
                }
            }catch (SQLException e){
                throw new DaoException("Error create curriculum", e);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            throw new DaoException("Error while selecting a curriculum", ex);
        }
        return booksByAuthor;
    }

    public ListModel<Curriculum> findCurriculumsByTitle(String title) throws DaoException {
        DefaultListModel<Curriculum> curriculumsByTitle = new DefaultListModel<>();
        try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_CURRICULUM_BY_TITLE)
        ) {
            statement.setString(1, title + "%");
            try{
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    curriculumsByTitle.addElement(createCurriculum(resultSet));
                }
            }catch (SQLException e){
                throw new DaoException("Error while searching a curriculum", e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new DaoException("Error while selecting a curriculum", ex);
        }
        return curriculumsByTitle;
    }

    public ListModel<Curriculum> findCurriculumsByTwoCriteria(String title, String author) throws DaoException{
        DefaultListModel<Curriculum> curriculumsByTwoCriteria = new DefaultListModel<>();
        try(
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_CURRICULUM_BY_TITLE_AND_AUTHOR);
        ){
            statement.setString(1, title + "%");
            statement.setString(2, author + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                curriculumsByTwoCriteria.addElement(createCurriculum(resultSet));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error while searching curriculums by two criteria",e);
        }
        return curriculumsByTwoCriteria;
    }

    public ListModel<Curriculum> findAllCurriculums() throws DaoException {
        DefaultListModel<Curriculum> curriculums = new DefaultListModel<>();
        try(
            Connection connection = db.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL);
        ){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                curriculums.addElement(createCurriculum(resultSet));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error while selecting all curriculums",e);
        }
        return curriculums;
    }
    private Curriculum createCurriculum(ResultSet resultSet) throws SQLException {
        Curriculum curriculum = new Curriculum();
        curriculum.setIdCurriculum(resultSet.getInt(1));
        curriculum.setFile(resultSet.getBlob(2).getBinaryStream());
        curriculum.setTitle(resultSet.getString(3));
        curriculum.setIdAuthor(resultSet.getInt(4));
        curriculum.setTimer(resultSet.getDate(5).toLocalDate());
        curriculum.setDepartment(resultSet.getString(6));
        curriculum.setDepartmentCode(resultSet.getString(7));
        curriculum.setAuthorName(resultSet.getString(8));
        curriculum.setCoordination(resultSet.getString(9));
        return curriculum;
    }
}