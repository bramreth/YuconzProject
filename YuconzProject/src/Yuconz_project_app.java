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

    /**
     * getDatabase
     * gets the database
     * @return the database
     */
    public Database getDatabase() {
        return database;
    }

    /**
     * getAuthorisation
     * gets the authorisation
     * @return the authorisation
     */
    public Authorisation getAuthorisation(){
        return this.authorisation;
    }

    /**
     * getCurrentUser
     * gets the current logged in user
     * @return the current user
     */
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

            selection = input.nextLine();

            try{
                 selectionInt = Integer.parseInt(selection);
            } catch (NumberFormatException e) {
                selectionInt = 0;
            }

            System.out.println("\n\n\n\n\n\n\n");

            switch(selectionInt) {
                case 0: System.out.println("Please enter a number"); break;
                case 1: System.out.println(currentUser.getUserInfo()); break;
                case 2: System.out.println("Please enter the username of employee:") ;
                    String username = input.nextLine();
                    if(!readPersonalDetails(username)){
                        System.out.println("unauthorised access");
                    }break;
                case 3: System.out.println("Please enter the username of employee:") ;
                    Boolean confirmed = false;
                    while(!confirmed) {
                        String createUser = input.nextLine();
                        if (createPersonalDetails(createUser)) {
                            Document temp = createPersonalDetailsDocument(createUser);
                            System.out.println("\n");
                            temp.print();
                            /*
                             * Confirmation of entered data
                             */
                            do {
                                System.out.println("Are these details correct? Type 1 for 'yes' or 2 for 'no' or 3 to quit:");
                                response = input.nextLine();
                                try {
                                    responseInt = Integer.parseInt(response);
                                } catch (NumberFormatException e) {
                                    responseInt = 0;
                                }

                                switch (responseInt) {
                                    case 1: //yes
                                        database.createNewUser(temp);
                                        confirmed = true;
                                        break;
                                    case 2: //no
                                        System.out.println("Please re-enter details:");
                                        temp = createPersonalDetailsDocument(createUser);
                                        System.out.println("\n");
                                        temp.print();
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
                            break;
                        }
                } break;
                case 4:
                    System.out.println("Please enter the username of employee:") ;
                    String amendUser = input.nextLine();
                    if (amendPersonalDetails(amendUser)) {
                        database.amendUserPersonalDetails(amendPersonalDetailsDocument(database.fetchPersonalDetails(amendUser)));
                        break;
                    } else {
                        System.out.println("unauthorised access");
                        break;
                    }
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
            String username = input.nextLine();
            System.out.println("Password:");
            String password = input.nextLine();
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

    /**
     * login
     * Logs the user in
     * @param username
     * @param password
     * @return true if successful, otherwise false
     */
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
     * amendPersonalDetailsDocument
     * edits an existing personal details document
     * @param userDetails
     * @return the document
     */
    public Document amendPersonalDetailsDocument(Document userDetails)
    {
        String selection;
        int selectionInt = 0;

        String name = userDetails.getName();
        String surname = userDetails.getSurname();
        String dob = userDetails.getDob();
        String address = userDetails.getAddress();
        String townCity = userDetails.getTownCity();
        String county = userDetails.getCounty();
        String postcode = userDetails.getPostcode();
        String telephoneNumber = userDetails.getTelephoneNumber();
        String mobileNumber = userDetails.getMobileNumber();
        String emergencyContact = userDetails.getEmergencyContact();
        String emergencyContactNumber = userDetails.getEmergencyContactNumber();

        do {
            System.out.println("\n");
            System.out.println("Which would you like to change?");
            System.out.println("1: Name: " + name);
            System.out.println("2: Surname: " + surname);
            System.out.println("3: Date of Birth: " + dob);
            System.out.println("4: Address: " + address);
            System.out.println("5: Town/City: " + townCity);
            System.out.println("6: County: " + county);
            System.out.println("7: Post Code: " + postcode);
            System.out.println("8: Telephone Number: " + telephoneNumber);
            System.out.println("9: Mobile Number: " + mobileNumber);
            System.out.println("10: Emergency Contact: " + emergencyContact);
            System.out.println("11: Emergency Contact Number: " + emergencyContactNumber);
            System.out.println("0: Quit");
            System.out.println("\n");

            selection = input.nextLine();

            try{
                selectionInt = Integer.parseInt(selection);
            } catch (NumberFormatException e) {
                selectionInt = -1;
            }

            switch (selectionInt) {
                case 1: System.out.println("Enter a new name");
                    name = input.nextLine();
                    break;
                case 2: System.out.println("Enter a new surname");
                    surname = input.nextLine();
                    break;
                case 3: System.out.println("Enter a new date of birth");
                    dob = input.nextLine();
                    break;
                case 4: System.out.println("Enter a new address");
                    address = input.nextLine();
                    break;
                case 5: System.out.println("Enter a new town or city");
                    townCity = input.nextLine();
                    break;
                case 6: System.out.println("Enter a new county");
                    county = input.nextLine();
                    break;
                case 7: System.out.println("Enter a new postcode");
                    postcode = input.nextLine();
                    break;
                case 8: System.out.println("Enter a new telephone number");
                    telephoneNumber = input.nextLine();
                    break;
                case 9: System.out.println("Enter a new mobile number");
                    mobileNumber = input.nextLine();
                    break;
                case 10: System.out.println("Enter a new emergency contact");
                    emergencyContact = input.nextLine();
                    break;
                case 11: System.out.println("Enter a new emergency contact number");
                    emergencyContactNumber = input.nextLine();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("That is not a valid choice.");
                    break;
            }

        } while (selectionInt != 0);
        Document newUserDetails = new Document(userDetails.getUsername());
        newUserDetails.populateDocument(userDetails.getStaffNo(), name, surname, dob, address, townCity, county, postcode, telephoneNumber, mobileNumber, emergencyContact, emergencyContactNumber);
        return newUserDetails;
    }

    /**
     * createPersonalDetailsDocument
     * Creates a personal details document for a user
     * @param username
     * @return the document
     */
    private Document createPersonalDetailsDocument(String username)
    {
        Document newUser = new Document(username);
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
        System.out.println("Forename:");
        name = input.nextLine();
        System.out.println("Surname:");
        surname = input.nextLine();
        System.out.println("Date of birth:");
        dob = input.nextLine();
        System.out.println("Address:");
        address = input.nextLine();
        System.out.println("Town/City:");
        townCity = input.nextLine();
        System.out.println("County:");
        county = input.nextLine();
        System.out.println("Postcode:");
        postcode = input.nextLine();
        System.out.println("TelephoneNumber:");
        telephoneNumber = input.nextLine();
        System.out.println("Mobile number:");
        mobileNumber = input.nextLine();
        System.out.println("Emergency contact:");
        emergencyContact = input.nextLine();
        System.out.println("Emergency contact number:");
        emergencyContactNumber = input.nextLine();

        newUser.populateDocument(database.getStaffID(username), name, surname, dob, address, townCity, county, postcode, telephoneNumber, mobileNumber, emergencyContact, emergencyContactNumber);
        return newUser;

    }

    public boolean readPersonalDetails(String userIn){
        //run authorisation method with readPersonalDetails as an action
        if(authorisation.authorisationCheck(currentUser, userIn,"readPersonalDetails")){
            if(database.checkExists(userIn)) {
                Document doc = database.fetchPersonalDetails(userIn);
                doc.print();
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean createPersonalDetails(String userIn){
        //run authorisation method with createPersonalDetails as an action
        if(authorisation.authorisationCheck(currentUser, userIn, "createPersonalDetails")){
            if(!database.checkExists(userIn) && database.checkExistsEmployee(userIn)) {
               return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public  boolean amendPersonalDetails(String userIn){
        //run authorisation method with amendPersonalDetails as an action
        if(authorisation.authorisationCheck(currentUser, userIn, "amendPersonalDetails")){
           if(database.checkExists(userIn)){
               Document doc = database.fetchPersonalDetails(userIn);
               doc.print();
               return true;
           } else {
               return false;
           }
        } else {
            return false;
        }
    }
}
