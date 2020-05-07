package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NUserDaoJdbc extends UserDaoJdbc {
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        // N 사 DB Connection 생성 코드
        System.out.println(" N 사 ");
        Class.forName("com.mysql.jdbc.Driver");
        Connection c = DriverManager.getConnection(
                "jdbc:mysql://localhost/tobyspring","root","1111"
        );
        return c;
    }
}
