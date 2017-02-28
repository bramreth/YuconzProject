import javax.swing.event.DocumentEvent;
import java.util.Scanner;
/**
 * Created by bramreth on 2/13/17.
 *
 * Main application class
 */

public class Yuconz_project_app {
    private Authentication_server authentication_server;
    private static Authorisation authorisation;
    private Database database;
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

        if(app.database.isReady()){
            app.displayLoginMenu();
        }
    }

    /**v3
     * Constructor
     * Initializes the App object and prompts the user for login
     */
    public Yuconz_project_app() {
        database = new Database("jdbc:mysql://dragon.kent.ac.uk/sjl66", "sjl66", "lef/u");
        authentication_server = new Authentication_server(database.getConnection());
        authorisation = new Authorisation();

        if (database.isReady()) {
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

    public Authorisation getAuthorisation(){
        return this.authorisation;
    }

    public User getCurrentUser(){
        return this.currentUser;
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
            System.out.println("3. read details");

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
                case 3: if(!readPersonalDetails("hruser2")){
                    System.out.println("invalid access");
                }break;

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
                currentUser.getPosition().setSubordinates(database.getSubordinates(username));
                System.out.println("\n\n\n\n\n\n\n");
                System.out.println(currentUser.getUserInfo());
                menu();
            }else{
                System.out.println("Failure");
            }
        }
    }

    public boolean login(String username, String password){
        if (authentication_server.verifyLogin(username, password)) {
            currentUser = database.getUser(username);
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

    public boolean readPersonalDetails(String userIn){
        //run authorisation method with readPersonalDetails as an action
        if(authorisation.authorisationCheck(currentUser, userIn,"readPersonalDetails")){
            Document doc = database.fetchPersonalDetails(userIn);
            doc.print();
            return true;
        }else{return false;}
    }

    public boolean createPersonalDetails(String userIn){
        //run authorisation method with readPersonalDetails as an action
        if(authorisation.authorisationCheck(currentUser, userIn, "createPersonalDetails")){

            return true;
        }
        return false;
    }

    public boolean ammendPersonalDetails(String userIn){
        //run authorisation method with readPersonalDetails as an action
        if(authorisation.authorisationCheck(currentUser, userIn, "ammendPersonalDetails")){
            return true;
        }
        return false;
    }
}
