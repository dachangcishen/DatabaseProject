import java.sql.*;
import java.util.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Administrator {
    private static SimpleDateFormat frominput;
    private static SimpleDateFormat indatabase;

    // public Administrator(Connection con) throws SQLException {
    //     frominput = new SimpleDateFormat("dd/MM/yyyy");
    //     indatabase = new SimpleDateFormat("yyyy-MM-dd");
    // }


    public static void createAllTables(Connection con) throws SQLException {
        
        Statement stmt = con.createStatement();
                String str = "CREATE TABLE category( " +
            "cID INTEGER, " +
            "cName VARCHAR(25), " +
            "PRIMARY KEY (cID))";
        try{
            stmt.executeUpdate(str);
        }catch(Exception e){System.out.println("Table category exist.");}
        
        str = "CREATE TABLE manufacturer( " +
            "mID INTEGER, " +
            "mName VARCHAR(25), " +
            "mAddress VARCHAR(100), " +
            "mPhoneNumber INTEGER, " +
            "PRIMARY KEY (mID))";
        try{
            stmt.executeUpdate(str);
        }catch(Exception e){System.out.println("Table manufacturer exist.");}
        
        str = "CREATE TABLE part( " +
            "pID INTEGER, " +
            "pName VARCHAR(25), " +
            "pPrice INTEGER, " +
            "mID INTEGER, " +
            "cID INTEGER, " +
            "pWarrantyPeriod INTEGER, " +
            "pAvailableQuantity INTEGER, " +
            "PRIMARY KEY (pID))";
        try{
            stmt.executeUpdate(str);
        }catch(Exception e){System.out.println("Table part exist.");}
        
        str = "CREATE TABLE salesperson( " +
            "sID INTEGER, " +
            "sName VARCHAR(25), " +
            "sAddress VARCHAR(100), " +
            "sPhoneNumber INTEGER, " +
            "sExperience INTEGER, " +
            "PRIMARY KEY (sID))";
        try{
            stmt.executeUpdate(str);
        }catch(Exception e){System.out.println("Table salesperson exist.");}
        
        str = "CREATE TABLE transaction( " +
            "tID INTEGER, " +
            "pID INTEGER, " +
            "sID INTEGER, " +
            "tDate DATE, " +
            "PRIMARY KEY (tID))";
        try{
            stmt.executeUpdate(str);
        }catch(Exception e){System.out.println("Table transaction exist.");}

        stmt.close();
        // System.out.println("Processing... Done. Database is initialized.");
    }
    public static void deleteAllTables(Connection con) throws SQLException{
        Statement stmt = con.createStatement();
        stmt.executeUpdate("DROP TABLE IF EXISTS category;");
        stmt.executeUpdate("DROP TABLE IF EXISTS manufacturer;");
        stmt.executeUpdate("DROP TABLE IF EXISTS part;");
        stmt.executeUpdate("DROP TABLE IF EXISTS salesperson;");
        stmt.executeUpdate("DROP TABLE IF EXISTS transaction;");
        stmt.close();
    }
    public static void loaddata(Connection con, String directoryPath){
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

            frominput = new SimpleDateFormat("dd/MM/yyyy");
            indatabase = new SimpleDateFormat("yyyy-MM-dd");
            reader = new BufferedReader(new FileReader(files[4]));
            line = reader.readLine();
            while(line != null){
                splitted = line.split("\t");
                int tid = Integer.parseInt(splitted[0]);
                int pid = Integer.parseInt(splitted[1]);
                int sid = Integer.parseInt(splitted[2]);
                String tdate = splitted[3];
                try {
                    tdate = indatabase.format(frominput.parse(tdate));
                }catch (ParseException e) {
                    e.printStackTrace();
                }
                stmt.executeUpdate("INSERT INTO transaction VALUES (" + tid + ", " + pid + ", " + sid + ", '" + tdate + "');");
                line = reader.readLine();
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
    public static void printATable(Connection con,String tableName) throws SQLException{
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName + ";");
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        for(int i = 1; i <= columnCount; i++){
            System.out.print(rsmd.getColumnName(i) + " | ");
        }
        System.out.println();
        while(rs.next()){
            for(int i = 1; i <= columnCount; i++){
                System.out.print(rs.getString(i) + " | ");
            }
            System.out.println();
        }
        rs.close();
        stmt.close();
    }

}
