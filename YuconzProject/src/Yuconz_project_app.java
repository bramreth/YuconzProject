import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private JButton btnLogin;
    private JTextField tfUsername = new JTextField("Username", 20);
    private JPasswordField tfPassword = new JPasswordField("Password", 20);
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
    private JTextField staffNo = new JTextField("staff number", 20);
    private JButton btnConfirmPD;
    private JButton handleReview;
    private JButton amendReview;
    private JTextField reviewStaffNo = new JTextField("staff number", 20);
    private JTextField reviewName = new JTextField("reviewee name", 20);
    private JTextField reviewManager = new JTextField("manager username", 20);
    private JTextField reviewSecondManager = new JTextField("second manager username", 20);
    private JTextField reviewSection = new JTextField("reviewee section", 20);
    private JTextField reviewJobTitle = new JTextField("reviewee job title", 20);
    private JTextField reviewPerformanceNumber = new JTextField("performance number", 20);
    private JTextField reviewPerformanceObjective = new JTextField("performance objective", 20);
    private JTextField reviewPerformanceAchievement = new JTextField("performance achievement", 20);
    private JTextField reviewPerformanceSummary = new JTextField("performance summary", 20);
    private JTextField reviewGoalNumber = new JTextField("goal number", 20);
    private JTextField reviewGoal = new JTextField("goal description", 20);
    private JTextField reviewComments = new JTextField("reviewer comments", 20);
    String[] choices = {"Stay in post","Salary increase","Promotion", "Probation", "Termination"};
    private JComboBox<String> reviewRecommendation = new JComboBox<String>(choices);
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
            userDetails.populateDocument((database.countRows("Personal_Details")+1), Integer.parseInt(staffNo.getText()), name.getText(), surname.getText(), dob.getText(), address.getText(), townCity.getText(), county.getText(), postcode.getText(), telephoneNumber.getText(), mobileNumber.getText(), emergencyContact.getText(), emergencyContactNumber.getText());
        }

        return userDetails;
    }

    /**
     * setExistingDetails
     * sets the text fields' text to the details found in the database
     * @param username
     */
    private void setExistingDetails(String username)   {
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
     * setExistingReviewDetails
     * sets the text fields' text to the details found in the database
     * @param review
     */
    private void setExistingReviewDetails(Review review)
    {
        reviewStaffNo.setText("" + review.getStaffNo());
        reviewName.setText(review.getName());
        reviewManager.setText(review.getManager());
        reviewSecondManager.setText(review.getSecondManager());
        reviewSection.setText(review.getSection());
        reviewJobTitle.setText(review.getJobTitle());
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
    private final static String AMENDREVIEW = "Amend Review";

    /**
     * display login menu
     * Prompts the user for login details
     */
    private static void displayLoginMenu(Yuconz_project_app app)
    {
        //Create and set up the window.
        frame = new JFrame("Yuconz File App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);

        //Create and set up the content pane.
        app.addComponentsToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setSize(new Dimension(300, 150));
        centerFrame();
        frame.setVisible(true);
    }

    /**
     * centerFrame
     * centers the frame
     */
    public static void centerFrame()
    {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int xPos = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int yPos = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(xPos, yPos);
    }

    /**
     * addComponentsToPane
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
        JPanel card4 = createAmendCard();
        JPanel card5 = createReviewCard();
        JPanel card6 = createAmendReviewCard();

        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add(card1, LOGIN);
        cards.add(card2, MAINMENU);
        cards.add(card3, VIEWPD);
        cards.add(card4, AMENDPD);
        cards.add(card5, REVIEW);
        cards.add(card6, AMENDREVIEW);

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

        handleReview = new JButton("Handle existing review");
        handleReview.setFont(normalFont);
        handleReview.addActionListener(this);
        handleReview.setActionCommand("handle review");
        handleReview.setVisible(false);

        JButton backButton = new JButton("back");
        backButton.setFont(normalFont);
        backButton.addActionListener(this);
        backButton.setActionCommand(MAINMENU);

        JButton btnReview = new JButton("Create a new review");
        btnReview.setFont(normalFont);
        btnReview.addActionListener(this);
        btnReview.setActionCommand("createReview");

        amendReview = new JButton("Amend a review");
        amendReview.setFont(normalFont);
        amendReview.addActionListener(this);
        amendReview.setActionCommand(AMENDREVIEW);
        amendReview.setVisible(false);

        reviewPanel.add(btnReview);
        reviewPanel.add(handleReview);
        reviewPanel.add(amendReview);
        reviewPanel.add(backButton);
        reviewPanel.setBackground(OOCCOO);

        return reviewPanel;
    }

    /**
     * createAmendReviewCard
     * creates the JPanel of the amend review screen
     * @return
    */
    private JPanel createAmendReviewCard() {
        JPanel amendReviewPanel = new JPanel(new FlowLayout());

        JButton backButton = new JButton("back");
        backButton.addActionListener(this);
        backButton.setActionCommand(REVIEW);

        JButton confirmButton = new JButton("confirm");
        confirmButton.addActionListener(this);
        confirmButton.setActionCommand("submit review");//neeeds implementing

        reviewName.addFocusListener(this);
        reviewName.setEditable(false);
        reviewStaffNo.addFocusListener(this);
        reviewStaffNo.setEditable(false);
        reviewManager.addFocusListener(this);
        reviewManager.setEditable(false);
        reviewSecondManager.addFocusListener(this);
        reviewSecondManager.setEditable(false);
        reviewSection.addFocusListener(this);
        reviewJobTitle.addFocusListener(this);


        reviewPerformanceNumber.addFocusListener(this);
        reviewPerformanceNumber.setEditable(false);
        reviewPerformanceObjective.addFocusListener(this);
        reviewPerformanceAchievement.addFocusListener(this);
        JButton addPerformance = new JButton("add performance");
        addPerformance.addActionListener(this);
        addPerformance.setActionCommand("addPerformance");//implement!

        reviewGoalNumber.addFocusListener(this);
        reviewGoalNumber.setEditable(false);
        reviewGoal.addFocusListener(this);
        JButton addGoal = new JButton("add goal");
        addGoal.addActionListener(this);
        addGoal.setActionCommand("addGoal");//implement!
        reviewComments.addFocusListener(this);
        reviewPerformanceSummary.addFocusListener(this);

        amendReviewPanel.add(reviewName);
        amendReviewPanel.add(reviewStaffNo);
        amendReviewPanel.add(reviewManager);
        amendReviewPanel.add(reviewSecondManager);
        amendReviewPanel.add(reviewJobTitle);
        amendReviewPanel.add(reviewSection);
        amendReviewPanel.add(reviewPerformanceNumber);
        amendReviewPanel.add(reviewPerformanceObjective);
        amendReviewPanel.add(reviewPerformanceAchievement);
        amendReviewPanel.add(addPerformance);
        amendReviewPanel.add(reviewPerformanceSummary);
        amendReviewPanel.add(reviewGoalNumber);
        amendReviewPanel.add(reviewGoal);
        amendReviewPanel.add(reviewComments);
        amendReviewPanel.add(reviewRecommendation);


        amendReviewPanel.add(backButton);
        amendReviewPanel.setBackground(OOCCOO);

        return amendReviewPanel;
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

        userInfo.setHorizontalAlignment(SwingConstants.CENTER);
        userInfo.setVerticalAlignment(SwingConstants.CENTER);
        userInfo.setFont(normalFont);
        menuRight.add(btnViewPD);
        menuRight.add(btnCreatePD);
        menuRight.add(btnAmendPD);
        menuRight.add(btnReview);
        menuRight.add(btnLogout);

        menu.add(userInfo);
        menu.add(menuRight);
        menu.setBackground(OOCCOO);
        menuRight.setBackground(OOCCOO);

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
     * createAmendCard
     * creates the JPanel of the amend and create personal details screen
     * @return a JPanel main menu screen
     */
    private JPanel createAmendCard()
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
        btnLogin = new JButton("Login");
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
        frame.getRootPane().setDefaultButton(btnLogin);
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
                /*
                if the user is in hr, set the hr specific refiew button to be visible
                 */
                frame.getRootPane().setDefaultButton(null);
                if(currentUser.getDepartment().equals("Human Resources")){
                    handleReview.setVisible(true);
                }else{
                    handleReview.setVisible(false);
                }
                if(currentUser.getPosition().getPositionName().equals("Manager") ||currentUser.getPosition().getPositionName().equals("Director") ){
                    amendReview.setVisible(true);
                }else{
                    amendReview.setVisible(false);
                }
                cl.show(cards, MAINMENU);
                frame.setSize(new Dimension(640, 360)); //shows main menu and resizes
                centerFrame();

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
            frame.getRootPane().setDefaultButton(btnLogin);
             centerFrame();

        } else if (e.getActionCommand().equals(CREATEPD) || e.getActionCommand().equals(AMENDPD) || e.getActionCommand().equals(VIEWPD)) { //working with personal details files

            switch (e.getActionCommand()) {
                case VIEWPD:
                    personalDetailsUser = selectPDUser(VIEWPD); //popup window returns string the user entered (should be a username)
                    if(personalDetailsUser == null || personalDetailsUser.equals("None found")) {
                        break;
                    }

                    if (readPersonalDetails(personalDetailsUser)) {
                        viewDetailsField.setText(detailsDocument.read());
                        cl.show(cards, (String) e.getActionCommand());
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid permissions for that action", "Invalid Permissions", JOptionPane.PLAIN_MESSAGE);
                    }
                    break;
                case AMENDPD:
                    personalDetailsUser = selectPDUser(AMENDPD); //popup window returns string the user entered (should be a username)
                    if(personalDetailsUser == null || personalDetailsUser.equals("None found")) {
                        break;
                    }

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
                    personalDetailsUser = selectPDUser(CREATEPD); //popup window returns string the user entered (should be a username)
                    if(personalDetailsUser == null || personalDetailsUser.equals("None found")) {
                        break;
                    }

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

        //confirming edits to or brand new personal details files
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

        //return to the main menu
        } else if (e.getActionCommand().equals(MAINMENU)) {
            cl.show(cards, MAINMENU);
            warningLabel.setText("");

        //amend a review with all review required people present
        } else if (e.getActionCommand().equals(AMENDREVIEW)) {
            String unfinishedReviewString = selectUnfinishedReviews(e.getActionCommand());

            if(!(unfinishedReviewString == null) && !(unfinishedReviewString.equals("None found"))) {
                int reviewID = Integer.parseInt(unfinishedReviewString.substring(0,unfinishedReviewString.indexOf(",")));
                cl.show(cards, AMENDREVIEW);
                Review review = database.getReviewForAmending(reviewID);
                setExistingReviewDetails(review);
            }

        //handle an existing review
        }else if(e.getActionCommand().equals("handle review")) {
            String unfinishedReviewString = selectUnfinishedReviews(e.getActionCommand());

            if(!(unfinishedReviewString == null) && !(unfinishedReviewString.equals("None found"))) {
                int reviewID = Integer.parseInt(unfinishedReviewString.substring(0,unfinishedReviewString.indexOf(",")));
                database.addSecondManager(reviewID, secondManagerSelection(database.getReviewManager(reviewID).getPosition().getPositionName(), reviewID));
            }

        //create a new review
        } else if (e.getActionCommand().equals("createReview")) {
            if(createReview()) {
                String reviewee = selectReviewee();
                database.createNewReview(currentUser.getUsername(), reviewee);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid permissions for that action", "Invalid Permissions", JOptionPane.PLAIN_MESSAGE);
            }

        //failsafe that catches any stray button clicks that takes the user to where the button action command would take it
        } else {
            cl.show(cards, (String)e.getActionCommand());
            warningLabel.setText("");
        }

    }

    /**
     *
     */
    private String secondManagerSelection(String position, int reviewID)
    {
        ArrayList<String> supervisorList = database.getEmployeesWithPosition(position, reviewID);

        Object[] supervisors = new Object[supervisorList.size()];

        if(supervisorList.size() > 0) {
            for(int i = 0; i < supervisors.length; i++) {
                supervisors[i] = supervisorList.get(i);
            }
        } else {
            supervisors = new Object[1];
            supervisors[0] = "None found";
        }

        String supervisor = (String)JOptionPane.showInputDialog(frame, "Select an employee", "User Required", JOptionPane.PLAIN_MESSAGE, null, supervisors, supervisors[0]);

        //If a string was returned, return it to above
        if ((supervisor != null) && (supervisor.length() > 0)) {
            return supervisor;
        }
        return null;
    }

    /**
     * selectUser
     * shows the manager or director a list of their subordinates to choose from
     * @return selected user's username
     */
    private String selectReviewee() {

        Object[] employees = new Object[currentUser.getPosition().getSubordinates().size()];

        for(int i = 0; i < employees.length; i++) {
            employees[i] = currentUser.getPosition().getSubordinates().get(i);
        }

        String subordinate = (String)JOptionPane.showInputDialog(frame, "Select an employee", "User Required", JOptionPane.PLAIN_MESSAGE, null, employees, employees[0]);

        //If a string was returned, return it to above
        if ((subordinate != null) && (subordinate.length() > 0)) {
            return subordinate;
        }
        return null;
    }

    /**
     * selectUser
     * shows the manager or director a list of their subordinates to choose from
     * @return selected user's username
     */
    private String selectUnfinishedReviews(String action) {

        ArrayList<String> reviewsList = new ArrayList<String>();

        if (action.equals("handle review")) {
            reviewsList = database.getReviewsMissingSecondManager();
        } else {
            reviewsList = database.getReviewsWithSecondManager(currentUser.getUsername());
        }

        Object[] reviews = new Object[reviewsList.size()];

        if(reviewsList.size() > 0) {
            for(int i = 0; i < reviews.length; i++) {
                reviews[i] = reviewsList.get(i);
            }
        } else {
            reviews = new Object[1];
            reviews[0] = "None found";
        }

        String subordinate = (String)JOptionPane.showInputDialog(frame, "Select an review", "Review Required", JOptionPane.PLAIN_MESSAGE, null, reviews, reviews[0]);

        //If a string was returned, return it to above
        if ((subordinate != null) && (subordinate.length() > 0)) {
            return subordinate;
        }
        return null;
    }

    /**
     * selectPDUser
     * creates and shows a popup window prompting the user for a username
     * @return the username if one is given, otherwise return null
     */
    private String selectPDUser(String action)
    {
        ArrayList<String> employeesArrayList;
        Object[] employees;
        if (action.equals(AMENDPD) || action.equals(VIEWPD)) { //viewing and amending requires the same list of usernames
            employeesArrayList = database.getPersonalDetailsUserList();
            employees = new Object[employeesArrayList.size()];

        } else if (action.equals(CREATEPD)) { //creating requires a list of all users without personal details files
            employeesArrayList = database.getUsersWithoutPersonalDetails();
            employees = new Object[employeesArrayList.size()];

        } else { //if action is an unknown option no users are found
            employeesArrayList = new ArrayList<String>();
            employeesArrayList.set(0, "None found");
            employees = new Object[1];
        }

        for(int i = 0; i < employees.length; i++) {
            employees[i] = employeesArrayList.get(i);
        }

        String subordinate = (String)JOptionPane.showInputDialog(frame, "Select an employee", action, JOptionPane.PLAIN_MESSAGE, null, employees, employees[0]);

        //If a string was returned, return it to above
        if ((subordinate != null) && (subordinate.length() > 0)) {
            return subordinate;
        }
        return null;
    }

    @Override
    public void focusGained(FocusEvent e) {
        JTextField tempTF = (JTextField)e.getComponent();
        tempTF.selectAll();
    }

    @Override
    public void focusLost(FocusEvent e) {
        //nothing to see here
    }
    //endregion
}
