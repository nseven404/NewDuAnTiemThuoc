package fpoly.tn.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlHelper {
  public static String DB_TCPPort = "";
    public static String DB_DatabaseName = "";
    public static String DB_ID = "";
    public static String DB_PASS = "";

    public static String DB_URL = "";

    public static Connection conn;

    // thực hiện kết nối/đóng SQL 
    public static boolean ConnectionSQL() {

        DB_URL = String.format("jdbc:sqlserver://localhost:%s;databaseName=%s;user=%s;password=%s", DB_TCPPort, DB_DatabaseName, DB_ID, DB_PASS);
      try {
          conn = DriverManager.getConnection(DB_URL);
          return true;
      } catch (SQLException ex) {
          return false;
      }
      
    }

    // đóng kết nối và trả về trạng thái thực thi
    public static void closeConnection() {
        try {
            if(conn != null)
                conn.close();
        } catch (SQLException ex) {

        }
    }
    
    
    public static PreparedStatement prepareStatement(String sql, Object...args) throws SQLException{
        ConnectionSQL();
        PreparedStatement pstmt;
        if(sql.trim().startsWith("{")){
            pstmt = conn.prepareCall(sql);
        }
        else{
            pstmt = conn.prepareStatement(sql);
        }
        for(int i=0;i<args.length;i++){
            pstmt.setObject(i + 1, args[i]);
        }
        
        return pstmt;
    }

    public static boolean executeUpdate(String sql, Object...args) {
        boolean thanhcong = true;
        try {
            PreparedStatement stmt = prepareStatement(sql, args);
            stmt.executeUpdate();
            
        } 
        catch (SQLException e) {
            thanhcong= false;
            System.out.println("SqlHelper executeUpdate:" +sql + " "+e);
        }finally{
            closeConnection();
        }
        return thanhcong;
    }
  
    public static ResultSet executeQuery(String sql, Object...args) {
        try {
            PreparedStatement stmt = prepareStatement(sql, args);
            return stmt.executeQuery();
        } 
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

