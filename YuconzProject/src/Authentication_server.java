import java.sql.*;
/**
 * Created by Sam Le-Cornu on 16/2/17.
 */
public class Authentication_server {

    public Authentication_server() {
        try {
            String host = "jdbc:mysql://dragon.kent.ac.uk/sjl66";
            String username = "sjl66";
            String password = "lef/u";
            Connection con = DriverManager.getConnection(host,username,password);

        }
        catch ( SQLException err ) {
            System.out.println( err.getMessage( ) );
        }
    }

   /* public boolean verifyLogin(String username,  String password){

    }*/
}
