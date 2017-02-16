/**
 * Created by bramreth on 2/13/17.
 *
 * idea, login and logut can be static~
 */

import java.util.Scanner;
public class Yuconz_project_app
{

    /**
     * Main method
     * @param args
     */
    public static void main(String args[])
    {
        Yuconz_project_app App = new Yuconz_project_app();

        System.out.println("Login Successful");
    }

    /**
     * Constructor
     * Initializes the App object and prompts the user for login
     */
    public Yuconz_project_app()
    {
        boolean notLoggedIn = true;
        while (notLoggedIn) {
            Scanner input = new Scanner(System.in);

            System.out.println("Username:");
            String username = input.next();
            System.out.println("Password:");
            String password = input.next();

            if (login(username, password)) {
                notLoggedIn = false;

            } else {
                System.out.print("Failure");
            }
        }
    }

    /**
     * login
     * Checks if the given credentials are valid
     * @param username
     * @param password
     * @return true if the login details match a record in the database else false
     */
    public boolean login(String username, String password)
    {
        if (username.equals("user") && password.equals("pass")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * logout
     * Logs out the current user
     * @param currentUser
     * @return true is successful and false if a error occured
     */
    public boolean logout(User currentUser)
    {
        return true;
    }
}
