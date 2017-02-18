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
    private boolean notLoggedIn;

    /**
     * Main method
     *
     * @param args
     */
    public static void main(String args[]) {
        Yuconz_project_app App = new Yuconz_project_app();
    }

    /**
     * Constructor
     * Initializes the App object and prompts the user for login
     */
    public Yuconz_project_app() {
        db = new Database("jdbc:mysql://dragon.kent.ac.uk/sjl66", "sjl66", "lef/u");
        auth = new Authentication_server(db.getConnection());

        if (db.isReady()) {
            notLoggedIn = true;
            displayLoginMenu();
        } else {
            System.out.print("Connection to database failed");
        }
    }

    /**
     * menu
     * Displays the options the users have
     */
    public void menu()
    {
        do{
            System.out.println("\n");
            System.out.println("Please select an option from below");
            System.out.println("1. Show user information");
            System.out.println("2. Logout");

            int selection = input.nextInt();

            System.out.println("\n\n\n\n\n\n\n");

            switch(selection) {
                case 1: System.out.println(currentUser.getUserInfo()); break;
                case 2: logout(); break;
            }
        } while(!notLoggedIn);
    }

    /**
     * display login menu
     * Prompts the user for login details
     */
    public void displayLoginMenu()
    {
        while (notLoggedIn) {
            System.out.println("Username:");
            String username = input.next();
            System.out.println("Password:");
            String password = input.next();
            if(!login(username, password)){
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
            notLoggedIn = false;
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
            notLoggedIn = true;
            displayLoginMenu();
        }else{
            System.out.println("No user found, returning to login");
            notLoggedIn = true;
            displayLoginMenu();
        }
    }
}
