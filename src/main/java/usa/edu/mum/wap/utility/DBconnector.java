package usa.edu.mum.wap.utility;

import java.sql.*;

public class DBconnector {
    private Connection conn;

    private DBconnector() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?useSSL=false", "eegii", "Eegii_123");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static DBconnector connector = new DBconnector();

    static DBconnector getconnector() {
        return connector;
    }

    public Connection getconnection() {
        return conn;
    }

    public void insertTask() {
        //   Statement statement = conn.createStatement();
    }

    public static void main(String[] args) {
        getconnector().getconnection();
    }


}
