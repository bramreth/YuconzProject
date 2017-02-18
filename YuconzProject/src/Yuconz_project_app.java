import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * Created by bramreth on 2/13/17.
 *
 * Main application class
 */

public class Yuconz_project_app {
    private Authentication_server auth;
    private Database db;
    private User currentUser;
    private Scanner input = new Scanner(System.in);
    private boolean loggedIn;

    /**
     * Main method
     *
     * @param args
     */
    public static void main(String args[]) {
        Yuconz_project_app app = new Yuconz_project_app();
        if(app.db.isReady()){
            app.displayLoginMenu();
        }
    }

    /**v3
     * Constructor
     * Initializes the App object and prompts the user for login
     */
    public Yuconz_project_app() {
        db = new Database("jdbc:mysql://dragon.kent.ac.uk/sjl66", "sjl66", "lef/u");
        auth = new Authentication_server(db.getConnection());

        if (db.isReady()) {
            loggedIn = false;
        } else {
            System.out.print("Connection to database failed");
        }
    }

    /**
     * getLoggedIn
     * gets whether or not a user is logged in
     * @return true is logged in, otherwise false
     */
    public boolean getLoggedIn(){
        return this.loggedIn;
    }
    /**
     * menu
     * Displays the options the users have
     */
    private void menu()
    {
        String selection;
        int selectionInt;
        do{
            System.out.println("\n");
            System.out.println("Please select an option from below");
            System.out.println("1. Show user information");
            System.out.println("2. Logout");

            selection = input.next();

            try{
                 selectionInt = Integer.parseInt(selection);
            } catch (NumberFormatException e) {
                selectionInt = 0;
            }

            System.out.println("\n\n\n\n\n\n\n");

            switch(selectionInt) {
                case 0: System.out.println("Please enter a number"); break;
                case 1: System.out.println(currentUser.getUserInfo()); break;
                case 2: logout(); break;
            }
        } while(loggedIn);

        displayLoginMenu();
    }

    /**
     * display login menu
     * Prompts the user for login details
     */
    private void displayLoginMenu()
    {
        while (!loggedIn) {
            System.out.println("Username:");
            String username = input.next();
            System.out.println("Password:");
            String password = input.next();
            if(login(username, password)){
                System.out.println("\n\n\n\n\n\n\n");
                System.out.println(currentUser.getUserInfo());
                menu();
            }else{
                System.out.println("Failure");
            }
        }
    }

    public boolean login(String username, String password){
        if (auth.verifyLogin(username, password)) {
            currentUser = db.getUser(username);
            loggedIn = true;
            return true;
        } else {
            return false;
        }
    }
    /**
     * logout
     * Logs out the current user
     */
    public void logout()
    {
        if(currentUser!=null){
            System.out.println(currentUser.getName() + " has been logged out.");
            currentUser = null;
            loggedIn = false;
        }else{
            System.out.println("No user found, returning to login");
            loggedIn = false;
        }
    }
}
