package sample.model;

import java.sql.*;

public class JDBC {

    public static Connection Connection() {

        String connectionURL = "jdbc:mysql://localhost:3306/test?user=root&password=root";

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(connectionURL);

            Class.forName("com.mysql.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            System.out.println("Driver problem");
        } catch (SQLException sqlExeption) {
            System.out.println("SQLException: " + sqlExeption.getMessage());
            System.out.println("SQLState: " + sqlExeption.getSQLState());
            System.out.println("VendorError: " + sqlExeption.getErrorCode());
        }
        return conn;
    }
}