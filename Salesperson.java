import java.sql.*;
import java.util.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Salesperson {

    private static SimpleDateFormat frominput;
    private static SimpleDateFormat indatabase;

    public static void searchForParts(Connection con,String partOrManufacturerName, int searchMode, int sortMode) throws SQLException{
        // searchMode 1: partial search by part name
        // searchMode 2: partial search by manufacturer name
        // sortMode 1: sort by price (ascending)
        // sortMode 2: sort by price (descending)
        Statement stmt = con.createStatement();
        String sql = "SELECT * FROM part, manufacturer WHERE part.mid = manufacturer.mid AND ";
        if(searchMode == 1){
            sql += "part.pname LIKE '%" + partOrManufacturerName + "%' ";
        }else if(searchMode == 2){
            sql += "manufacturer.mname LIKE '%" + partOrManufacturerName + "%' ";
        }
        if(sortMode == 1){
            sql += "ORDER BY part.pprice ASC;";
        }else if(sortMode == 2){
            sql += "ORDER BY part.pprice DESC;";
        }
        ResultSet rs = stmt.executeQuery(sql);
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
    public static void sellAPart(Connection con,int pid, int sid, String date) throws SQLException{
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM part WHERE pid = " + pid + ";");
        rs.next();
        int pavailablequantity = rs.getInt("pavailablequantity");
        String pname =rs.getString("pname");
        if(pavailablequantity == 0){
            System.out.println("No available quantity for this part!");
            return;
        }
        else {
            System.out.println("Product: "+pname+"(id: "+pid+") "+"Remaining Quantity: "+ (pavailablequantity-1));
        }
        stmt.executeUpdate("UPDATE part SET pavailablequantity = " + (pavailablequantity - 1) + " WHERE pid = " + pid + ";");
        // tid is auto-incremented
        rs = stmt.executeQuery("SELECT MAX(tid) FROM transaction");
        rs.next();
        int tid = 1 + rs.getInt("MAX(tid)");
        frominput = new SimpleDateFormat("dd/MM/yyyy");
        indatabase = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = indatabase.format(frominput.parse(date));
        }catch (ParseException e) {
            e.printStackTrace();
        }
        stmt.executeUpdate("INSERT INTO transaction VALUES (" + tid + ", " + pid + ", " + sid + ", '" + date + "');");

        rs.close();
        stmt.close();
    }
}
