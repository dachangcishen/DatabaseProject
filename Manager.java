import java.sql.*;
import java.util.*;
import java.io.*;
public class Manager {
    public static void listAllSalespersons(Connection con,int sortMode) throws SQLException {
        // sortMode 1: sort by experience (ascending)
        // sortMode 2: sort by experience (descending)
        Statement stmt = con.createStatement();
        ResultSet rs = null;

        if (sortMode == 1) {
            rs = stmt.executeQuery("SELECT * FROM salesperson ORDER BY sexperience ASC;");
        } else if (sortMode == 2) {
            rs = stmt.executeQuery("SELECT * FROM salesperson ORDER BY sexperience DESC;");
        }

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
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
    public static void listSalesPersonsWithTransactionNumber(Connection con,int lowerLimit, int higherlimit) throws SQLException{
        // lowerLimit and higherlimit are for the range of sexperience (inclusive)
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT sid, sname, sexperience, COUNT(*) AS transactionnumber FROM salesperson, transaction WHERE salesperson.sid = transaction.sid GROUP BY salesperson.sid HAVING sexperience >= " + lowerLimit + " AND sexperience <= " + higherlimit + ";");
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
        }catch(Exception e){
            System.out.println(e);
        }
    }
    public static void listManufacturersWithTotalSalesValue(Connection con) throws SQLException{
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT mid, mname, SUM(pprice) AS totalsalesvalue FROM manufacturer, part, transaction WHERE manufacturer.mid = part.mid AND part.pid = transaction.pid GROUP BY manufacturer.mid ORDER BY totalsalesvalue DESC;");
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
    public  static void showNMostPopularParts(Connection con,int n) throws SQLException{
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT pid, pname, COUNT(*) AS popularity FROM part, transaction WHERE part.pid = transaction.pid GROUP BY part.pid ORDER BY popularity DESC LIMIT " + n + ";");
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
