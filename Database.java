import java.sql.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;


public class Database{
    final String dbAddress = "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/db24?autoReconnect=true&useSSL=false";
    final String dbUsername = "Group24";
    final String dbPassword = "CSCI3170";
    private Connection con = null;

    public void connectToMySQL(){
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
        stmt.executeUpdate("CREATE TABLE category ("
            + "cid INT NOT NULL,"
            + "cname VARCHAR(20) NOT NULL,"
            + "PRIMARY KEY (cid)"
            + ");");
        stmt.executeUpdate("CREATE TABLE manufacturer ("
            + "mid INT NOT NULL,"
            + "mname VARCHAR(20) NOT NULL,"
            + "maddress VARCHAR(50) NOT NULL,"
            + "mphonenumber INT NOT NULL,"
            + "PRIMARY KEY (mid)"
            + ");");
        stmt.executeUpdate("CREATE TABLE part ("
            + "pid INT NOT NULL,"
            + "pname VARCHAR(20) NOT NULL,"
            + "pprice INT NOT NULL,"
            + "mid INT NOT NULL,"
            + "cid INT NOT NULL,"
            + "pwarranty INT NOT NULL,"
            + "pavailablequantity INT NOT NULL,"
            + "PRIMARY KEY (pid),"
            + ");");
        stmt.executeUpdate("CREATE TABLE salesperson ("
            + "sid INT NOT NULL,"
            + "sname VARCHAR(20) NOT NULL,"
            + "saddress VARCHAR(50) NOT NULL,"
            + "sphonenumber INT NOT NULL,"
            + "sexperience INT NOT NULL,"
            + "PRIMARY KEY (sid)"
            + ");");
        stmt.executeUpdate("CREATE TABLE transaction ("
            + "tid INT NOT NULL,"
            + "pid INT NOT NULL,"
            + "sid INT NOT NULL,"
            + "tdate DATE NOT NULL,"
            + "PRIMARY KEY (tid),"
            + ");");
        stmt.close();
    }

    private void readAndSaveToDB(String directoryPath){
        String[] files = {directoryPath + "category.txt", directoryPath + "manufacturer.txt", directoryPath + "part.txt", directoryPath + "salesperson.txt", directoryPath + "transaction.txt"};
        BufferedReader reader;
        try{
            Statement stmt = con.createStatement();
            String line;
            String[] splitted;
            
            reader = new BufferedReader(new FileReader(files[0]));
            line = reader.readLine();            
            while(line != null){
                splitted = line.split("\t");
                int cid = Integer.parseInt(splitted[0]);
                String cname = splitted[1];
                stmt.executeUpdate("INSERT INTO category VALUES (" + cid + ", '" + cname + "');");
                line = reader.readLine();
            }

            reader = new BufferedReader(new FileReader(files[1]));
            line = reader.readLine();
            while(line != null){
                splitted = line.split("\t");
                int mid = Integer.parseInt(splitted[0]);
                String mname = splitted[1];
                String maddress = splitted[2];
                int mphonenumber = Integer.parseInt(splitted[3]);
                stmt.executeUpdate("INSERT INTO manufacturer VALUES (" + mid + ", '" + mname + "', '" + maddress + "', " + mphonenumber + ");");
                line = reader.readLine();
            }

            reader = new BufferedReader(new FileReader(files[2]));
            line = reader.readLine();
            while(line != null){
                splitted = line.split("\t");
                int pid = Integer.parseInt(splitted[0]);
                String pname = splitted[1];
                int pprice = Integer.parseInt(splitted[2]);
                int mid = Integer.parseInt(splitted[3]);
                int cid = Integer.parseInt(splitted[4]);
                int pwarranty = Integer.parseInt(splitted[5]);
                int pavailablequantity = Integer.parseInt(splitted[6]);
                stmt.executeUpdate("INSERT INTO part VALUES (" + pid + ", '" + pname + "', " + pprice + ", " + mid + ", " + cid + ", " + pwarranty + ", " + pavailablequantity + ");");
                line = reader.readLine();
            }

            reader = new BufferedReader(new FileReader(files[3]));
            line = reader.readLine();
            while(line != null){
                splitted = line.split("\t");
                int sid = Integer.parseInt(splitted[0]);
                String sname = splitted[1];
                String saddress = splitted[2];
                int sphonenumber = Integer.parseInt(splitted[3]);
                int sexperience = Integer.parseInt(splitted[4]);
                stmt.executeUpdate("INSERT INTO salesperson VALUES (" + sid + ", '" + sname + "', '" + saddress + "', " + sphonenumber + ", " + sexperience + ");");
                line = reader.readLine();
            }

            reader = new BufferedReader(new FileReader(files[4]));
            line = reader.readLine();
            while(line != null){
                splitted = line.split("\t");
                int tid = Integer.parseInt(splitted[0]);
                int pid = Integer.parseInt(splitted[1]);
                int sid = Integer.parseInt(splitted[2]);
                String tdate = splitted[3];
                stmt.executeUpdate("INSERT INTO transaction VALUES (" + tid + ", " + pid + ", " + sid + ", '" + tdate + "');");
                line = reader.readLine();
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
}