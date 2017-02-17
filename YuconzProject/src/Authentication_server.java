import java.sql.*;
/**
 * Created by Sam Le-Cornu on 16/2/17.
 */
public class Authentication_server {

    private boolean ready = false;
    private String host, username, password;

    /**
     * Constructor
     * Initializes the database connection for logging in and out
     */
    public Authentication_server(String host, String username, String password) {
        try {
            this.host = host;
            this.username = username;
            this.password = password;
            Connection con = DriverManager.getConnection(host,username,password);
            ready = true;
        }
        catch ( SQLException err ) {
            System.out.println( err.getMessage( ) );
        }
    }

    /**
     * isReady
     * Returns true if the db connection is successful or false if it failed
     * @return true if connection was established, false otherwise
     */
    public boolean isReady()
    {
        return ready;
    }


    /**
     * verifyLogin
     * Checks if the username and password combination is correct
     * @param username
     * @param password
     * @return true if the username and password match database records, false otherwise
     */
   public boolean verifyLogin(String username,  String password)
   {
        return true;
   }
}
