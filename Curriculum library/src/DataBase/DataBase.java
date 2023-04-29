package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DataBase {
    private static final String URL = "jdbc:mysql://localhost:3306/librarydb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "12345678";
    private static final DataBase INSTANCE = new DataBase();
    private DataBase(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        }
        catch(Exception ex){
            System.out.println("Error while connecting to DB:");
            System.out.println(ex.getMessage());
        }
    }

    public static DataBase getInstance(){
        return INSTANCE;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}