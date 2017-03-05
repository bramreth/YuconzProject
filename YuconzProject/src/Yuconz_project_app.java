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
        String response;
        int selectionInt;
        int responseInt;

        do{
            System.out.println("\n");
            System.out.println("Please select an option from below");
            System.out.println("1. Show user information");
            System.out.println("2. Read details");
            System.out.println("3. Create details");
            System.out.println("4. Amend details");
            System.out.println("5. Logout");

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
                case 2: System.out.println("Please enter the username of employee: \n") ;
                    String username = input.next();
                    if(!readPersonalDetails(username)){
                        System.out.println("unauthorised access");
                    }break;
                case 3: System.out.println("Please enter the username of employee: \n") ;
                    Boolean confirmed = false;
                    while(!confirmed) {
                        String createUser = input.next();
                        if (createPersonalDetails(createUser)) {
                            createPersonalDetailsDocument(createUser);
                            Document temp = createPersonalDetailsDocument(createUser);
                            temp.print();

                            /*
                             * Confirmation of entered data
                             */
                            do {
                                System.out.println("Are these details correct? Type 1 for 'yes' or 2 for 'no' or 3 to quit:");
                                response = input.next();
                                try {
                                    responseInt = Integer.parseInt(response);
                                } catch (NumberFormatException e) {
                                    responseInt = 0;
                                }

                                switch (responseInt) {
                                    case 1: //yes
                                        database.createNewUser(temp);
                                        confirmed = true;
                                        System.out.println("Document has been created successfully.");
                                        break;
                                    case 2: //no
                                        System.out.println("Please re-enter details:");
                                        break;
                                    case 3: //quit
                                        confirmed = true;
                                        System.out.println("No user added");
                                        break;
                                    default: //no number entered
                                        System.out.println("Please enter a number"); //need this part to loop
                                        break;
                                }
                            } while (!confirmed);

                        } else {
                            System.out.println("unauthorised access");
                        }
                }break;
                case 4:
                case 5: logout(); break;
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

    /**
     * createPersonalDetailsDocument
     * Creates a document for a user
     * @param username
     * @return the document
     */
    public Document createPersonalDetailsDocument(String username){
        Document newUser = new Document(username);
        boolean confirmValid = false;
        boolean validStaffNo = true;
        int staffNo = 0;
        String name;
        String surname;
        String dob;
        String address;
        String townCity;
        String county;
        String postcode;
        String telephoneNumber;
        String mobileNumber;
        String emergencyContact;
        String emergencyContactNumber;
        System.out.println("\n");
        System.out.println("Please input the personal details as prompted:");
        System.out.println("\n");

        do {
            System.out.println("staff number:");
            String userInput = input.next();

            try {
                staffNo = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                validStaffNo = false;
            }
        } while(!validStaffNo);

        System.out.println("forename:");
        name = input.next();
        System.out.println("surname:");
        surname = input.next();
        System.out.println("dob:");
        dob = input.next();
        System.out.println("address:");
        address = input.next();
        System.out.println("townCity:");
        townCity = input.next();
        System.out.println("county:");
        county = input.next();
        System.out.println("postcode:");
        postcode = input.next();
        System.out.println("telephoneNumber:");
        telephoneNumber = input.next();
        System.out.println("mobileNumber:");
        mobileNumber = input.next();
        System.out.println("emergencyContact:");
        emergencyContact = input.next();
        System.out.println("emergencyContactNumber:");
        emergencyContactNumber = input.next();

        newUser.populateDocument(staffNo, name, surname, dob, address, townCity, county, postcode, telephoneNumber, mobileNumber, emergencyContact, emergencyContactNumber);
        return newUser;

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
        //run authorisation method with createPersonalDetails as an action
        if(authorisation.authorisationCheck(currentUser, userIn, "createPersonalDetails")){
            if(!database.checkExists(userIn)) {
               return true;
            }
        }
        return false;
    }

    public boolean amendPersonalDetails(String userIn){
        //run authorisation method with amendPersonalDetails as an action
        if(authorisation.authorisationCheck(currentUser, userIn, "amendPersonalDetails")){
           if(database.checkExists(userIn)){
               Document doc = database.fetchPersonalDetails(userIn);
               doc.print();
           }
           return true;
        }
        return false;
    }
}
