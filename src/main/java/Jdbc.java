import java.sql.*;

class Jdbc {

    private final String url = "jdbc:mysql://localhost:3306/ID_TP1";
    private final String user = "user";
    private final String password = "i9luPI3$";

    private Connection newConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    ResultSet executeQuery(String query){
        ResultSet res;
        try {
            Connection con=newConnection();
            Statement stmt = con.createStatement();
            res = stmt.executeQuery(query);
            System.out.println("requête faite");
        } catch (SQLException e) {
            res=null;
            e.printStackTrace();
        }
        return res;
    }
    int executeUpdate(String query){
        int res;
        try {
            Connection con=newConnection();
            Statement stmt = con.createStatement();
            res = stmt.executeUpdate(query);
            System.out.println("requête faite");
        } catch (SQLException e) {
            res=-1;
            e.printStackTrace();
        }
        return res;
    }
}
