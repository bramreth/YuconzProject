import java.sql.*;
/**
 * Created by Sam Le-Cornu on 16/02/17.
 * Last modified 17/02/17
 */
public class Authentication_server {


    private Connection con;

    /**
     * Constructor
     * Initializes the database connection for logging in and out
     */
    public Authentication_server(Connection con) {
        this.con = con;
    }

    /**
     * verifyLogin
     * Checks if the username and password combination is correct
     * @param username
     * @param password
     * @return true if the username and password match database records, false otherwise
     */
    public boolean verifyLogin(String username,  String password) {
        try {
            Statement s = con.createStatement();
            String sql = "SELECT Username FROM Yuconz_Users WHERE Password='" + password + "'";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                if (rs.getString("username").equals(username)) {
                    return true;
                }
            }
            return false;
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return false;
    }
}
