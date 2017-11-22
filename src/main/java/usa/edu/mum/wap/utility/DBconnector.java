package usa.edu.mum.wap.utility;

import java.sql.*;
import java.util.Enumeration;

public class DBconnector {

    private Connection conn;

    private DBconnector() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                    Config.getString("url"),
                    Config.getString("user"),
                    Config.getString("password"));
//            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?useSSL=false",
//                    "eegii", "Eegii_123");
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
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            Driver driver = null;
            // clear drivers
            while (drivers.hasMoreElements()) {
                try {
                    driver = drivers.nextElement();
                    DriverManager.deregisterDriver(driver);
                } catch (SQLException ex) {
                }
            }
        } catch (Exception e) {
        }
    }
}
