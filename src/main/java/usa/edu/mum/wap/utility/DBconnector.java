package usa.edu.mum.wap.utility;

import java.sql.*;

public class DBconnector {
    private Connection conn;

    private DBconnector() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?useSSL=false",
                    "root", "root");
        } catch (Exception e) {
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

  


}
