package usa.edu.mum.wap.utility;

import java.sql.*;

public class DBconnector {
    private Connection conn;

    private DBconnector() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?useSSL=false",
                    "eegii", "Eegii_123");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static DBconnector connector = new DBconnector();

    public static DBconnector getconnector() {
        return connector;
    }

    public Connection getconnection() {
        return conn;
    }

    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e) {
        }
    }

    public void insertTask() {
        //   Statement statement = conn.createStatement();
    }

    public static void main(String[] args) {
        getconnector().getconnection();
    }


}
