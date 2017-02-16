/**
 * Created by bramreth on 2/13/17.
 *
 * idea, login and logut can be static~
 */

import java.util.Scanner;
public class Yuconz_project_app
{

    public static void main(String args[])
    {
        Yuconz_project_app App = new Yuconz_project_app();

        System.out.println("Login Successful");
    }

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

    public boolean login(String username, String password)
    {
        if (username.equals("user") && password.equals("pass")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean logout()
    {
        return true;
    }
}
