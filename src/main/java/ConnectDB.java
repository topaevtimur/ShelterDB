import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    static Connection con = null;

    public static Connection getConnection() {
        if (con != null) return con;
        return getConnection("test.db");
    }

    private static Connection getConnection(String dateBase) {
        try {
            con = DriverManager
                    .getConnection("jdbc:sqlite:" + dateBase);

        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
        }

        return con;
    }
}