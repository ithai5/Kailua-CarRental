import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInteraction {
    private static String user;
    private static String password;
    private static String address;

    public DBInteraction(String user, String password, String address) {
        DBInteraction.user = user;
        DBInteraction.password = password;
        DBInteraction.address = "jdbc:mysql://" + address + "/KeaProject";
    }

    public static ResultSet getData(String query) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(address, user, password);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            return rs;
        } catch (SQLException | ClassNotFoundException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static int updateDatabase(String query) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(address, user, password);
            Statement statement = connection.createStatement();
            int result = statement.executeUpdate(query);
            return result;
        } catch (SQLException | ClassNotFoundException var4) {
            var4.printStackTrace();
            return 0;
        }
    }

    public void setUser(String user) {
        DBInteraction.user = user;
    }

    public void setPassword(String password) {
        DBInteraction.password = password;
    }

    public void setAddress(String address) {
        DBInteraction.address = address;
    }
}
