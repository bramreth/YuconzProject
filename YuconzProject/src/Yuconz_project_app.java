/**
 * Created by bramreth on 2/13/17.
 *
 * idea, login and logut can be static~
 */

import java.util.Scanner;
public class Yuconz_project_app
{
    private Authentication_server auth;
    private Database db;
    private User currentUser;

    /**
     * Main method
     * @param args
     */
    public static void main(String args[])
    {
        Yuconz_project_app App = new Yuconz_project_app();
    }

    /**
     * Constructor
     * Initializes the App object and prompts the user for login
     */
    public Yuconz_project_app()
    {
        db = new Database("jdbc:mysql://dragon.kent.ac.uk/sjl66","sjl66","lef/u");
        auth = new Authentication_server(db.getConnection());

        if (db.isReady()) {
            login();
        } else {
            System.out.print("Connection to database failed");
        }
    }

    /**
     * login
     * Prompts the user for login details
     */
    public void login()
    {
        boolean notLoggedIn = true;
        while (notLoggedIn) {
            Scanner input = new Scanner(System.in);

            System.out.println("Username:");
            String username = input.next();
            System.out.println("Password:");
            String password = input.next();

            if (auth.verifyLogin(username, password)) {
                notLoggedIn = false;
                currentUser = db.getUser;
                System.out.println("Logged in");
            } else {
                System.out.println("Failure");
            }
        }
    }

    /**
     * logout
     * Logs out the current user
     * @param currentUser
     * @return true is successful and false if a error occured
     */
    public boolean logout()
    {
        return true;
    }
}
