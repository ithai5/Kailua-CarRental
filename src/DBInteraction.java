/*
    this class should handle interaction with MySQL Database.this class has been created by Itai Gramse, as a part of project at kea university. this c
 */
import java.sql.*;

class DBInteraction{
    private static String user;
    private static String password;
    private static String address;

    public DBInteraction (String user, String password,String address)
    {
        this.user = user;
        this.password = password;
        this.address = "jdbc:mysql://" + address + "/KeaProject";
    }

    public static ResultSet getData(String query)
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(address,user,password );
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            return rs;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int updateDatabase(String query)
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(address,user,password);
            Statement statement = connection.createStatement();
            int  result = statement.executeUpdate(query);
            return result;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setUser (String user)
    {
        this.user = user;
    }

    public void setPassword (String password)
    {
        this.password = password;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

}