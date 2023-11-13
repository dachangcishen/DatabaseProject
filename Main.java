import java.sql.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;


public class Database{
    final String dbAddress = "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/db24?autoReconnect=true&useSSL=false";
    final String dbUsername = "Group24";
    final String dbPassword = "CSCI3170";
    private Connection con = null;

    public Connection connectToMySQL(){
        Connection con = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(dbAddress, dbUsername, dbPassword);
        }catch(ClassNotFoundException e){
            System.out.println("[Error]: Java MySQL DB Driver not found!!");
            System.exit(0);
        }catch(SQLException e){
            System.out.println(e);
        }
        this.con = con;
    }
    
    public void createAllTables() throws SQLException{
        Statement stmt = con.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Users ("
            + "userID INT NOT NULL AUTO_INCREMENT,"
            + "username VARCHAR(20) NOT NULL,"
            + "password VARCHAR(20) NOT NULL,"
            + "PRIMARY KEY (userID),"
            + "UNIQUE (username)"
            + ");");
    }
}