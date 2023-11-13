import java.io.*;
import java.sql.*;
class HelloWorld{
    public static String mesg = "Hello World!";
    public static void main(String[] args){
        // load the sqlite-JDBC driver using the current class loader
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch(Exception x) {
            System.err.println("Unable to load the driver class!");
        }

        // create a database connection
        Connection conn = DriverManager.getConnection("jdbc:mysql://projgw.cse.cuhk.edu.hk:2712/h040?autoReconnect=true&useSSL=false", "h040", "MarvicKu");
        Statement stmt = conn.createStatement();
        // create a Statement object to execute the query
        ResultSet rs = stmt.executeQuery("SELECT * FROM temp");
        String user_id;
        String password;
        while (rs.next()){
            user_id = rs.getString(1);
            password = rs.getString(2);
        }

        // create a Statement object to execute the query
        
        stmt.executeUpdate("CREATE TABLE Student " + "(UserID VARCHAR(10), " + "Password VARCHAR(8))");
        ResultSet rs2 = stmt.executeQuery("SELECT * FROM temp");
        String user_id2;
        String password2;
        while (rs2.next()){
            user_id2 = rs.getString(1);
            password2 = rs.getString(2);
        }
        // rs.next() moves the cursor down one row from its current position

        // create a Statement object to execute the query
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Student VALUES (?, ?)");
        
        for (int i = 0; i < student.length; i++){
            pstmt.setString(1, student[i][0]);
            pstmt.setString(2, student[i][1]);
            pstmt.executeUpdate();
        }

        System.out.println(mesg);

        /* destroy the result set object */
        rs.close();
        /* destroy the statement object */
        stmt.close() ;
        /* destroy the prepared statement object */
        pstmt.close();
        /* destroy the connection */
        conn.close();

        
    }
}