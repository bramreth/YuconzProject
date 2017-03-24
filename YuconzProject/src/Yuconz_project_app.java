import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * Created by bramreth on 2/13/17.
 *
 * Main application class
 */

public class Yuconz_project_app implements ActionListener,FocusListener
{
    //region global variables
    private Authentication_server authentication_server;
    private static Authorisation authorisation;
    private Database database;
    private User currentUser;
    private Scanner input = new Scanner(System.in);
    private boolean loggedIn;
    private Document detailsDocument;
    private String personalDetailsUser = null;
    private int personalDetailsDocumentID = 0;

    //GUI variables
    private static JFrame frame;
    private JPanel cards; //a panel that uses CardLayout
    private JTextField tfUsername = new JTextField("Username", 20);
    private JTextField tfPassword = new JTextField("Password", 20);
    private JLabel warningLabel = new JLabel();
    private JLabel userInfo = new JLabel();
    private JLabel viewDetailsField = new JLabel();
    private final Color OOCCOO = new Color(174, 198, 207);
    private Font normalFont = new Font("sans-serif", Font.PLAIN, 12);
    private Font titleFont = new Font("sans-serif", Font.BOLD, 13);

    //document text boxes
    private JTextField surname = new JTextField("surname", 20);
    private JTextField name = new JTextField("name", 20);
    private JTextField dob = new JTextField("dob", 20);
    private JTextField address = new JTextField("address", 20);
    private JTextField townCity = new JTextField("townCity", 20);
    private JTextField county = new JTextField("county", 20);
    private JTextField postcode = new JTextField("postcode", 20);
    private JTextField telephoneNumber = new JTextField("telephoneNumber", 20);
    private JTextField mobileNumber = new JTextField("mobileNumber", 20);
    private JTextField emergencyContact = new JTextField("emergencyContact", 20);
    private JTextField emergencyContactNumber = new JTextField("emergencyContactNumber", 20);
    private JTextField staffNo = new JTextField("staffNo", 20);
    private JButton btnConfirmPD;
    //endregion

    //region main method and constructor

    /**
     * Main method
     *
     * @param args
     */
    public static void main(String args[]) {
        Yuconz_project_app app = new Yuconz_project_app();

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                displayLoginMenu(app);
            }
        });
        //if(app.database.isReady()){
          //  app.displayLoginMenu();
        //}
    }

    /**v3
     * Constructor
     * Initializes the App object and prompts the user for login
     */
    public Yuconz_project_app() {
        database = new Database("jdbc:sqlite:Yuconz_Database.db");
        authentication_server = new Authentication_server(database.getConnection());
        authorisation = new Authorisation();
        if (database.isReady()) {
            loggedIn = false;
        } else {
            System.out.print("Connection to database failed");
        }
    }
    //endregion

    //region Accessors
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
    //endregion

    //region login/logout

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
    //endregion

    //region Personal Details
    /**
     * createPersonalDetailsDocument
     * Creates a personal details document for a user
     * @param username
     * @return the document
     */
    private Document getPersonalDetailsDocument(String username)
    {
        Document userDetails = new Document(username);

        if (personalDetailsDocumentID > 0) {
            userDetails.populateDocument(personalDetailsDocumentID, Integer.parseInt(staffNo.getText()), name.getText(), surname.getText(), dob.getText(), address.getText(), townCity.getText(), county.getText(), postcode.getText(), telephoneNumber.getText(), mobileNumber.getText(), emergencyContact.getText(), emergencyContactNumber.getText());
        } else {
            userDetails.populateDocument((database.getPDCount()+1), Integer.parseInt(staffNo.getText()), name.getText(), surname.getText(), dob.getText(), address.getText(), townCity.getText(), county.getText(), postcode.getText(), telephoneNumber.getText(), mobileNumber.getText(), emergencyContact.getText(), emergencyContactNumber.getText());
        }

        return userDetails;
    }

    /**
     * setExistingDetails
     * sets the text box text to the details found in the database
     * @param username
     */
    private void setExistingDetails(String username)
    {
        Document existingUserData = database.fetchPersonalDetails(username);
        personalDetailsDocumentID = existingUserData.getDocumentID();
        staffNo.setText("" + existingUserData.getStaffNo());
        name.setText(existingUserData.getName());
        surname.setText(existingUserData.getSurname());
        dob.setText(existingUserData.getDob());
        address.setText(existingUserData.getAddress());
        townCity.setText(existingUserData.getTownCity());
        county.setText(existingUserData.getCounty());
        postcode.setText(existingUserData.getPostcode());
        telephoneNumber.setText(existingUserData.getTelephoneNumber());
        mobileNumber.setText(existingUserData.getMobileNumber());
        emergencyContact.setText(existingUserData.getEmergencyContact());
        emergencyContactNumber.setText(existingUserData.getEmergencyContactNumber());
    }

    /**
     * setExistingDetails
     * sets the text box text to the details found in the database
     */
    private void clearExistingDetails(String username)
    {
        Document existingUserData = database.fetchPersonalDetails(username);
        staffNo.setText("" + existingUserData.getStaffNo());
        name.setText(existingUserData.getName());
        surname.setText(existingUserData.getSurname());
        dob.setText("Date of birth (yyyy-mm-dd)");
        address.setText("Address");
        townCity.setText("Town/City");
        county.setText("County");
        postcode.setText("Post Code");
        telephoneNumber.setText("Telephone Number");
        mobileNumber.setText("Mobile Number");
        emergencyContact.setText("Emergency Contact");
        emergencyContactNumber.setText("Emergency Contact Number");
    }

    /**
     * readPersonalDetails
     * checks permissions of the user for reading personal details files
     * @param userIn
     * @return true if allowed, otherwise false
     */
    public boolean readPersonalDetails(String userIn)
    {
        //run authorisation method with readPersonalDetails as an action
        if(authorisation.authorisationCheck(currentUser, userIn,"readPersonalDetails")){
            if(database.checkExistsPersonalDetails(userIn)) {
                Document doc = database.fetchPersonalDetails(userIn);
                detailsDocument = doc;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * createPersonalDetails
     * checks permissions of the user for creating personal details files
     * @param userIn
     * @return true if allowed, otherwise false
     */
    public boolean createPersonalDetails(String userIn)
    {
        //run authorisation method with createPersonalDetails as an action
        if(authorisation.authorisationCheck(currentUser, userIn, "createPersonalDetails")){
            if(!database.checkExistsPersonalDetails(userIn) && database.checkExistsEmployeeData(userIn)) {
               return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * amendPersonalDetails
     * checks permissions of the user for amending personal details files
     * @param userIn
     * @return true if allowed, otherwise false
     */
    public  boolean amendPersonalDetails(String userIn)
    {
        //run authorisation method with amendPersonalDetails as an action
        if(authorisation.authorisationCheck(currentUser, userIn, "amendPersonalDetails")){
           if(database.checkExistsPersonalDetails(userIn)){
               Document doc = database.fetchPersonalDetails(userIn);
               detailsDocument = doc;
               return true;
           } else {
               return false;
           }
        } else {
            return false;
        }
    }

    /**
     * createReview
     * checks permissions of the user for creating a review document
     * @return true if allowed, otherwise false
     */
    public  boolean createReview()
    {
        //run authorisation method with amendPersonalDetails as an action
        if(authorisation.authorisationCheck(currentUser, "", "createReviewRecord")){
           return true;
        } else {
            return false;
        }
    }
    //endregion

    //region GUI

    //GUI card names
    private final static String LOGIN = "Login";
    private final static String MAINMENU = "Main Menu";
    private final static String VIEWPD = "View Personal Details";
    private final static String AMENDPD = "Amend Personal Details";
    private final static String CREATEPD = "Create Personal Details";
    private final static String REVIEW = "Review";

    /**
     * display login menu
     * Prompts the user for login details
     */
    private static void displayLoginMenu(Yuconz_project_app app)
    {
        //Create and set up the window.
        frame = new JFrame("Yuconz File App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        //Create and set up the content pane.
        app.addComponentsToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setSize(new Dimension(300, 150));
        frame.setVisible(true);
    }

    /**
     * addComponnentsToPane
     * adds all components to the contentPane
     * @param contentPane
     */
    private void addComponentsToPane(Container contentPane)
    {
        //create the header
        JPanel headerPn = new JPanel();
        JLabel headerLbl = new JLabel("Yuconz File System App");

        headerLbl.setFont(titleFont);

        headerPn.add(headerLbl);

        JPanel card1 = createLoginCard();
        JPanel card2 = createMenuCard();
        JPanel card3 = createViewCard();
        JPanel card4 = createAmmendCard();
        JPanel card5 = createReviewCard();

        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add(card1, LOGIN);
        cards.add(card2, MAINMENU);
        cards.add(card3, VIEWPD);
        cards.add(card4, AMENDPD);
        cards.add(card5, REVIEW);

        headerPn.setBackground(OOCCOO);

        contentPane.add(headerPn, BorderLayout.PAGE_START);
        contentPane.add(cards, BorderLayout.CENTER);
    }

    /**
     * createReviewCard
     * creates the JPanel of the review screen
     * @return
     */
    private JPanel createReviewCard() {
        JPanel reviewPanel = new JPanel(new FlowLayout());

        JButton backButton = new JButton("back");
        backButton.addActionListener(this);
        backButton.setActionCommand(MAINMENU);

        JButton btnReview = new JButton("Create a new review");
        btnReview.addActionListener(this);
        btnReview.setActionCommand("createReview");

        reviewPanel.add(backButton);
        reviewPanel.add(btnReview);
        reviewPanel.setBackground(OOCCOO);

        return reviewPanel;
    }

    /**
     * createMenuCard
     * creates the JPanel of the main menu screen
     * @return a JPanel main menu screen
     */
    private JPanel createMenuCard()
    {
        JPanel menu = new JPanel(new GridLayout(0,2));
        JPanel menuRight = new JPanel();
        menuRight.setLayout(new GridLayout(5,0));

        JButton btnViewPD = new JButton(VIEWPD);
        btnViewPD.setFont(normalFont);
        btnViewPD.addActionListener(this);
        btnViewPD.setActionCommand(VIEWPD);

        JButton btnCreatePD = new JButton(CREATEPD);
        btnCreatePD.setFont(normalFont);
        btnCreatePD.addActionListener(this);
        btnCreatePD.setActionCommand(CREATEPD);

        JButton btnAmendPD = new JButton(AMENDPD);
        btnAmendPD.setFont(normalFont);
        btnAmendPD.addActionListener(this);
        btnAmendPD.setActionCommand(AMENDPD);

        JButton btnReview = new JButton(REVIEW);
        btnReview.setFont(normalFont);
        btnReview.addActionListener(this);
        btnReview.setActionCommand(REVIEW);

        JButton btnLogout = new JButton("Log Out");
        btnLogout.setFont(normalFont);
        btnLogout.addActionListener(this);
        btnLogout.setActionCommand(LOGIN);

        userInfo.setFont(normalFont);
        userInfo.setHorizontalAlignment(SwingConstants.CENTER);
        userInfo.setVerticalAlignment(SwingConstants.CENTER);

        menuRight.add(btnViewPD);
        menuRight.add(btnCreatePD);
        menuRight.add(btnAmendPD);
        menuRight.add(btnReview);
        menuRight.add(btnLogout);

        menu.add(userInfo);
        menu.add(menuRight);
        menu.setBackground(OOCCOO);

        return menu;
    }

    /**
     * createViewCard
     * creates the JPanel of the view personal details screen
     * @return a JPanel main menu screen
     */
    private JPanel createViewCard()
    {
        JPanel target = new JPanel(new FlowLayout());

        JButton backButton = new JButton("back");
        backButton.setFont(normalFont);
        backButton.addActionListener(this);
        backButton.setActionCommand(MAINMENU);

        viewDetailsField.setFont(normalFont);
        target.add(viewDetailsField);
        target.add(backButton);
        target.setBackground(OOCCOO);

        return target;
    }

    /**
     * createAmmendCard
     * creates the JPanel of the ammend and create personal details screen
     * @return a JPanel main menu screen
     */
    private JPanel createAmmendCard()
    {
        JPanel target = new JPanel(new GridLayout(12,2));

        btnConfirmPD = new JButton("confirm");
        btnConfirmPD.setFont(normalFont);
        btnConfirmPD.addActionListener(this);

        JButton backButton = new JButton("back");
        backButton.setFont(normalFont);
        backButton.addActionListener(this);
        backButton.setActionCommand(MAINMENU);

        surname.addFocusListener(this);
        name.addFocusListener(this);
        dob.addFocusListener(this);
        address.addFocusListener(this);
        townCity.addFocusListener(this);
        county.addFocusListener(this);
        postcode.addFocusListener(this);
        telephoneNumber.addFocusListener(this);
        mobileNumber.addFocusListener(this);
        emergencyContactNumber.addFocusListener(this);
        emergencyContact.addFocusListener(this);
        staffNo.addFocusListener(this);

        target.add(surname);
        target.add(name);
        target.add(dob);
        target.add(address);
        target.add(townCity);
        target.add(county);
        target.add(postcode);
        target.add(telephoneNumber);
        target.add(mobileNumber);
        target.add(emergencyContact);
        target.add(emergencyContactNumber);
        target.add(staffNo);
        target.add(btnConfirmPD);
        target.add(backButton);
        target.setBackground(OOCCOO);

        return target;
    }

    /**
     * createLoginCard
     * creates the JPanel of the login screen
     * @return a JPanel login screen
     */
    private JPanel createLoginCard()
    {
        JPanel login = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton btnLogin = new JButton("Login");
        btnLogin.setFont(normalFont);
        btnLogin.addActionListener(this);
        btnLogin.setActionCommand("authenticate");

        tfUsername.addFocusListener(this);
        tfPassword.addFocusListener(this);

        login.add(tfUsername);
        login.add(tfPassword);
        login.add(btnLogin);
        login.add(warningLabel);

        login.setBackground(OOCCOO);

        return login;
    }

    /**
     * ActionListener
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("ActionCommand: " + e.getActionCommand());
        CardLayout cl = (CardLayout) (cards.getLayout());

        if (e.getActionCommand().equalsIgnoreCase("authenticate")) { //loggin in from login screen
            if (login(tfUsername.getText(), tfPassword.getText())) { //checks login details
                cl.show(cards, MAINMENU);
                frame.setSize(new Dimension(640, 360)); //shows main menu and resizes

                warningLabel.setText("");
                currentUser.getPosition().setSubordinates(database.getSubordinates(tfUsername.getText()));
                userInfo.setText(currentUser.getUserInfo());

            } else { //wrong login details
                tfUsername.setText("Username");
                tfPassword.setText("Password");
                warningLabel.setText("Incorrect Login");
            }

        } else if (e.getActionCommand().equals(LOGIN)) { //return to login screen
            cl.show(cards, LOGIN);
            logout();
            tfUsername.setText("Username");
            tfPassword.setText("Password");
            frame.setSize(new Dimension(300, 150)); //shows login screen and resizes

        } else if (e.getActionCommand().equals(CREATEPD) || e.getActionCommand().equals(AMENDPD) || e.getActionCommand().equals(VIEWPD)) { //working with personal details files

            personalDetailsUser = inputUser(); //popup window returns string the user entered (should be a username)

            if (personalDetailsUser == null) { //nothing entered into the

                JOptionPane.showMessageDialog(frame, "Username can't be empty", "Username error", JOptionPane.PLAIN_MESSAGE);

            } else if (!database.checkExistsPersonalDetails(personalDetailsUser) && !e.getActionCommand().equals(CREATEPD)) { //user doesn't exist

                JOptionPane.showMessageDialog(frame, "User does not have a personal details file", "Username error", JOptionPane.PLAIN_MESSAGE);

            } else if (database.checkExistsPersonalDetails(personalDetailsUser) && e.getActionCommand().equals(CREATEPD)) { //trying to create a file that already exists

                JOptionPane.showMessageDialog(frame, "User already exists", "Username error", JOptionPane.PLAIN_MESSAGE);

            } else {
                switch (e.getActionCommand()) {
                    case VIEWPD:
                        if (readPersonalDetails(personalDetailsUser)) {
                            viewDetailsField.setText(detailsDocument.read());
                            cl.show(cards, (String) e.getActionCommand());
                        } else {
                            JOptionPane.showMessageDialog(frame, "Invalid permissions for that action", "Invalid Permissions", JOptionPane.PLAIN_MESSAGE);
                        }
                        break;
                    case AMENDPD:
                        if (amendPersonalDetails(personalDetailsUser)) {
                            cl.show(cards, (String) e.getActionCommand());
                            setExistingDetails(personalDetailsUser);
                            staffNo.setEditable(false);
                            btnConfirmPD.setActionCommand("confirmAmendPD");

                        } else {
                            JOptionPane.showMessageDialog(frame, "Invalid permissions for that action", "Invalid Permissions", JOptionPane.PLAIN_MESSAGE);
                        }
                        break;
                    case CREATEPD:
                        if (createPersonalDetails(personalDetailsUser)) {
                            cl.show(cards, AMENDPD);
                            clearExistingDetails(personalDetailsUser);
                            staffNo.setEditable(true);
                            btnConfirmPD.setActionCommand("confirmCreatePD");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Invalid permissions for that action", "Invalid Permissions", JOptionPane.PLAIN_MESSAGE);
                        }
                    default:
                        break;
                }
            }
        } else if (e.getActionCommand().equals("confirmAmendPD") || e.getActionCommand().equals("confirmCreatePD")) {

            String dobString = dob.getText();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date dateOfBirth;

            try {
                dateOfBirth = df.parse(dobString);
                String newDateString = df.format(dateOfBirth);

                if (e.getActionCommand().equals("confirmAmendPD")) {
                    database.amendUserPersonalDetails(getPersonalDetailsDocument(personalDetailsUser));
                } else {
                    database.createNewUser(getPersonalDetailsDocument(personalDetailsUser));
                }

                personalDetailsDocumentID = 0;
                cl.show(cards, MAINMENU);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Ensure staff number is an integer and date of birth is in the format: yyyy-mm-dd.", "Invalid Entry", JOptionPane.PLAIN_MESSAGE);
            }

        } else if (e.getActionCommand().equals(MAINMENU)) {
            cl.show(cards, MAINMENU);
            frame.setSize(new Dimension(640, 360));
            warningLabel.setText("");
        } else if (e.getActionCommand().equals("createReview")) {
            if(createReview()) {
                String reviewee = selectUser();
                System.out.println(reviewee);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid permissions for that action", "Invalid Permissions", JOptionPane.PLAIN_MESSAGE);
            }
        } else {
            cl.show(cards, (String)e.getActionCommand());
            warningLabel.setText("");
        }

    }

    /**
     * selectUser
     * shows the manager or director a list of their subordinates to choose from
     * @return selected user's username
     */
    private String selectUser() {

        Object[] employees = new Object[currentUser.getPosition().getSubordinates().size()];

        for(int i = 0; i < employees.length; i++) {
            employees[i] = currentUser.getPosition().getSubordinates().get(i);
        }

        String subordinate = (String)JOptionPane.showInputDialog(frame, "Select an employee", "User Required", JOptionPane.PLAIN_MESSAGE, null, employees, employees[0]);

        //If a string was returned, return it to above
        if ((subordinate != null) && (subordinate.length() > 0)) {
            return subordinate;
        } else {
            return null;
        }
    }

    /**
     * inputUser
     * creates and shows a popup window prompting the user for a username
     * @return the username if one is given, otherwise return null
     */
    private String inputUser()
    {
        String usernameInput = (String)JOptionPane.showInputDialog(frame, "Enter an employee username", "Username Required", JOptionPane.PLAIN_MESSAGE);

        //If a string was returned, return it to above
        if ((usernameInput != null) && (usernameInput.length() > 0)) {
            return usernameInput;
        } else {
            return null;
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        JTextField tempTF = (JTextField)e.getComponent();
        tempTF.selectAll();
    }

    @Override
    public void focusLost(FocusEvent e) {

    }
    //endregion
}
