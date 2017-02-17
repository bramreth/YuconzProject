import java.sql.*;

/**
 * Created by Maximilian on 17/02/2017.
 */
public class Database {

    private boolean ready = false;
    private String host, username, password;
    private Connection con;

    /**
     * Constructor
     * Initializes the database connection for logging in and out
     */
    public Database(String host, String username, String password) {
        try {
            this.host = host;
            this.username = username;
            this.password = password;
            con = DriverManager.getConnection(host,username,password);
            ready = true;
        }
        catch ( SQLException err ) {
        System.out.println( err.getMessage( ) );
    }
}

    /**
     * getConnection
     * Returns the connection object for use elsewhere
     * @return con
     */
    public Connection getConnection()
    {
        return con;
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
}
