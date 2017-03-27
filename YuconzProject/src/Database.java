import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Maximilian on 17/02/2017.
 */
public class Database {

    private boolean ready = false;
    private String host, username, password;
    private Connection con;

    /**
     * Constructor
     * Initializes the database connection for logging in and out
     * @param dbName database name
     *
     */
    public Database(String dbName)
    {
        con = null;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection(dbName);
            ready = true;
        } catch ( Exception e ) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
}

    /**
     * Returns the connection object
     * @return connection
     */
    public Connection getConnection()
    {
        return con;
    }

    /**
     * Returns true if the db connection is successful or false if it failed
     * @return true if connection was established, false otherwise
     */
    public boolean isReady()
    {
        return ready;
    }

    /**
     * Returns a user object
     * @param username username
     * @return user object of the given username
     */
    public User getUser(String username)
    {
        try {
            Statement s = con.createStatement();
            String sql = "SELECT * FROM Employee_Data WHERE username='" + username + "'";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                return new User(username, rs.getString("userID"), rs.getString("name"),
                        rs.getString("surname"), rs.getString("department"),
                        rs.getString("position"), rs.getString("supervisor"));
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return null;
    }

    /**
     * Returns the staff ID for the user
     * @param username username
     * @return staffID of the user
     */
    public int getStaffID(String username)
    {
        try {
            Statement s = con.createStatement();
            String sql = "SELECT userID FROM Employee_Data WHERE username='" + username + "'";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                return rs.getInt("userID");
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return 0;
    }

    /**
     * Returns a list of subordinates for a user
     * @param  username username
     * @return an ArrayList of subordinates
     */
    public ArrayList<String> getSubordinates(String username)
    {
        ArrayList<String> subordinatesID = new ArrayList<>();
        try {
            Statement s = con.createStatement();
            String sql = "SELECT username FROM Employee_Data WHERE supervisor='" + username + "'";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                subordinatesID.add(rs.getString("username"));
            }
            return subordinatesID;
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return null;
    }

    /**
     * Returns a personal details document of a user
     * @param username username
     * @return the personal details Document
     */
    public Document fetchPersonalDetails(String username)
    {

        try {
            Statement s = con.createStatement();
            String sql = "SELECT * FROM Personal_Details WHERE username='" + username + "'";
            ResultSet rs = s.executeQuery(sql);
            Document doc = new Document(username);
            while(rs.next()){
                //will overwrite doc each time it loops...
                doc.populateDocument(rs.getInt("documentID"), rs.getInt("userID"),
                        rs.getString("name"), rs.getString("surname"),
                        rs.getString("dateofbirth"), rs.getString("address"),
                        rs.getString("town"), rs.getString("county"),
                        rs.getString("postcode"), rs.getString("telephonenumber"),
                        rs.getString("mobilenumber"), rs.getString("emergencycontact"),
                        rs.getString("emergencycontactnumber"));
            }
            return doc;
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return null;
    }

    /**
     * check whether or not there is a personal details entry for the given user
     * @param userIn username
     * @return true if found, false otherwise
     */
    public boolean checkExistsPersonalDetails(String userIn)
    {
        try {
            Statement s = con.createStatement();
            String sql = "SELECT * FROM Personal_Details";
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()){
                if(rs.getString("username").equals(userIn)){
                    return true;
                }
            }
            return false;
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            return false;
        }
    }

    /**
     * Returns a list off all users with personal details documents
     * @return an ArrayList of usernames
     */
    public ArrayList<String> getPersonalDetailsUserList()
    {
        ArrayList<String> personalDetailsUserList = new ArrayList<>();
        try {
            Statement s = con.createStatement();
            String sql = "SELECT username FROM Personal_Details";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                personalDetailsUserList.add(rs.getString("username"));
            }
            return personalDetailsUserList;
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return null;
    }

    /**
     * Returns a list of users who do not have a personal details file
     * @return an ArrayList of usernames
     */
    public ArrayList<String> getUsersWithoutPersonalDetails()
    {
        ArrayList<String> personalDetailsUserList = new ArrayList<>();
        try {
            Statement s = con.createStatement();
            String sql = "SELECT Employee_Data.username FROM Employee_Data " +
                    "WHERE NOT EXISTS (SELECT Personal_Details.username FROM Personal_Details " +
                    "WHERE Personal_Details.username = Employee_Data.username)";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                personalDetailsUserList.add(rs.getString("username"));
            }
            return personalDetailsUserList;
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return null;
    }

    /**
     * Check whether or not there is a employee data entry for the given user
     * @param userIn username
     * @return true if there is a personal details file for the user, false otherwise
     */
    public boolean checkExistsEmployeeData(String userIn)
    {
        try {
            Statement s = con.createStatement();
            String sql = "SELECT * FROM Employee_Data";
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()){
                if(rs.getString("username").equals(userIn)){
                    return true;
                }
            }
            return false;
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            return false;
        }
    }

    /**
     * Creates a new personal details row for a user in the database based on a personal details document
     * @param document Document of personal details
     */
    public void createNewUser(Document document)
    {
        try {
            Statement s = con.createStatement();
            String sql = "INSERT INTO Personal_Details " +
                    "(documentID, name, surname, dateofbirth, address, town, County, postcode, telephonenumber, " +
                    "mobilenumber, emergencycontact, emergencycontactnumber, username, userID) " +
                    "values ('" + document.getDocumentID() + "','" + document.getName() + "','" + document.getSurname() +
                    "','" + document.getDob() + "','" + document.getAddress() + "','" + document.getTownCity() + "','" +
                    document.getCounty() + "','" + document.getPostcode() + "','" + document.getTelephoneNumber() + "','" +
                    document.getMobileNumber() + "','" + document.getEmergencyContact() + "','" +
                    document.getEmergencyContactNumber() + "','" + document.getUsername() + "','" +
                    getStaffID(document.getUsername()) + "')";
            s.executeUpdate(sql);
            System.out.println("Document has been created successfully");
            con.commit();
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    /**
     * Amends Personal Details of a user by dropping the row from the database and then recreating it as if it were
     * a new use
     * @param document Document of personal details
     */
    public void amendUserPersonalDetails(Document document)
    {
        try {
            Statement s = con.createStatement();
            String sql = "DELETE FROM Personal_Details WHERE username='" + document.getUsername() + "'";
            s.executeUpdate(sql);
            createNewUser(document);
            con.commit();
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }

    }

    /**
     * Counts the number of rows in a table
     * @param table name of a table in the database
     * @return an integer
     */
    public int countRows(String table)
    {
        int count = 0;
        try {
            Statement s = con.createStatement();
            String sql = "SELECT count(*) FROM " + table;
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()){
                count = rs.getInt("count(*)");
            }
            return count;
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            return count;
        }
    }

    /**
     * Creates a new review and adds in a review ID, username of the reviewee, staff ID of the
     * reviewee and the username of the supervisor
     * @param supervisor Supervisor's username
     * @param reviewee Reviewee's username
     */
    public void createNewReview (String supervisor, String reviewee)
    {
        try {
            Statement s = con.createStatement();
            String table = "Review_Details";
            String sql = "INSERT INTO Review_Details (reviewID, username, userID, manager) values " +
                    "(" + (countRows(table)+1) + ", '" + reviewee + "', " + getStaffID(reviewee) + ", '" + supervisor + "')";
            s.executeUpdate(sql);
            System.out.println("Review has been created successfully");
            con.commit();
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    /**
     * Gets a list of all reviews with no second manager listed
     * @return an Arraylist of reviews
     */
    public ArrayList<String> getReviewsMissingSecondManager()
    {
        ArrayList<String> reviews = new ArrayList<>();
        try {
            Statement s = con.createStatement();
            String sql = "SELECT username,reviewID,manager FROM Review_Details WHERE secondManager IS NULL";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                reviews.add(rs.getInt("reviewID") + ", Name: " + rs.getString("username") +
                        ", Manager: " + rs.getString("manager"));
            }
            return reviews;
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return null;
    }

    /**
     * Gets a list of all reviews with a second manager listed
     * @param  username
     * @return an ArrayList of reviews
     */
    public ArrayList<String> getReviewsWithSecondManager(String username)
    {
        ArrayList<String> supervisors = new ArrayList<>();
        try {
            Statement s = con.createStatement();
            String sql = "SELECT username,reviewID,manager,secondManager FROM Review_Details WHERE secondManager IS NOT NULL " +
                    "AND revieweeSignature IS NULL AND reviewerComments IS NULL AND managerSignature IS NULL AND secondManagerSignature IS NULL " +
                    "AND manager='" + username + "'";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                supervisors.add(rs.getInt("reviewID") + ", Name: " + rs.getString("username") + ", Supervisor: " +
                        rs.getString("manager") + ", Second Supervisor: " + rs.getString("secondManager"));
            }
            return supervisors;
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return null;
    }

    /**
     * Returns a review object to be used for amending
     * @param reviewID review ID
     * @return a Review
     */
    public Review getReviewForAmending(int reviewID)
    {
        try {
            Statement s = con.createStatement();
            String sql = "SELECT * FROM Review_Details WHERE reviewID=" + reviewID;
            ResultSet rs = s.executeQuery(sql);
            Review review = null;
            while(rs.next()){
                User reviewUser = getUser(rs.getString("username"));
                //will overwrite doc each time it loops...
                review = new Review(rs.getInt("reviewID"), rs.getInt("userID"), rs.getString("username"),
                        rs.getString("manager"), rs.getString("secondManager"), reviewUser.getDepartment(),
                        reviewUser.getPosition().getPositionName());
            }
            return review;
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return null;
    }

    /**
     * Gets a review to be read by the user
     * @param reviewID review ID
     * @return a Review
     */
    public Review getReviewForReading(int reviewID)
    {
        try {
            Statement s = con.createStatement();
            String sql = "SELECT * FROM Review_Details WHERE reviewID=" + reviewID;
            ResultSet rs = s.executeQuery(sql);
            Review review = null;
            while(rs.next()){
                User reviewUser = getUser(rs.getString("username"));
                review = new Review(reviewID,rs.getInt("userID"), rs.getString("username"), rs.getString("manager"),
                        rs.getString("secondManager"), reviewUser.getDepartment(), reviewUser.getPosition().getPositionName());
                review.addPerformanceSummary(rs.getString("performanceSummary"));
                review.addReviewerComment(rs.getString("reviewerComments"));
                review.addRecommendation(rs.getString("reviewerRecommendation"));
                if(rs.getString("revieweeSignature") != null){
                    review.sign("reviewee", rs.getString("revieweeSignature"));
                }
                if(rs.getString("managerSignature") != null) {
                    review.sign("manager", rs.getString("managerSignature"));
                }
                if(rs.getString("secondManagerSignature") != null) {
                    review.sign("secondManager", rs.getString("secondManagerSignature"));
                }
            }
            sql = "SELECT * FROM Performance_Details WHERE reviewID = " + reviewID;
            rs = s.executeQuery(sql);
            int i = 1;
            while(rs.next()){
                review.addPerformance(new Review.PastPerformance(i, rs.getString("objective"), rs.getString("achievement")));
                i++;
            }
            sql = "SELECT * FROM Goal_List WHERE reviewID = " + reviewID;
            rs = s.executeQuery(sql);
            int j = 1;
            while(rs.next()){
                review.addGoal(new Review.GoalList(j, rs.getString("goal")));
                j++;
            }
            return review;
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return null;
    }

    /**
     * returns all the readable reviews to be displayed as a list
     * @return an ArrayList of reviews
     */
    public ArrayList<String> getReadableReviews(String username)
    {
        ArrayList<String> listOfReviews = new ArrayList<>();
        try {
            Statement s = con.createStatement();
            String sql = "SELECT * FROM Review_Details WHERE username='" + username + "' OR manager='" + username +
                    "' OR secondManager= '" + username+"'";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                String complete;
                if(rs.getString("revieweeSignature")!=null && rs.getString("managerSignature")!=null
                        && rs.getString("secondManagerSignature")!=null) {
                    complete = "[Complete]";
                }else{
                    complete = "[Incomplete]";
                }
                listOfReviews.add(rs.getInt("reviewID") + ", Name: " + rs.getString("username") +
                        ", Supervisor: " + rs.getString("manager") + ", Second Supervisor: " +
                        rs.getString("secondManager") + ", " + complete);
            }
            return listOfReviews;
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return null;
    }

    /**
     * Returns the User who is the manager in a review
     * @param reviewID review ID
     * @return a User
     */
    public User getReviewManager(int reviewID)
    {
        try {
            Statement s = con.createStatement();
            String sql = "SELECT manager FROM Review_Details WHERE reviewID=" + reviewID;
            ResultSet rs = s.executeQuery(sql);
            User supervisor = null;
            while (rs.next()) {
                supervisor = getUser(rs.getString("manager"));
            }
            return supervisor;
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return null;
    }

    /**
     * Gets all employees with the same position (Manager, Director, Operational Staff)
     * @param position Position
     * @param reveiwID review ID
     * @return an ArrayList of usernames
     */
    public ArrayList<String> getEmployeesWithPosition(String position, int reveiwID)
    {
        ArrayList<String> employees = new ArrayList<>();
        try {
            Statement s = con.createStatement();
            String sql = "SELECT username FROM Employee_Data WHERE position='" + position + "' AND username <> '" +
                    getReviewManager(reveiwID).getUsername() + "'";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                employees.add(rs.getString("username"));
            }
            return employees;
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return null;
    }

    /**
     * Adds a second manager to a review
     * @param reviewID review ID
     * @param username username
     */
    public void addSecondManager(int reviewID, String username)
    {
        try {
            Statement s = con.createStatement();
            String sql = "UPDATE Review_Details SET secondManager = '" + username + "' WHERE reviewID=" + reviewID;
            s.executeUpdate(sql);
            con.commit();
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    /**
     * Adds a full review without the signatures to the database
     * Calls the method that will add the Performance Review list to the database
     * @param review a Review
     */
    public void submitReview(Review review)
    {
        try {
            Statement s = con.createStatement();
            String sql = "UPDATE Review_Details SET section = '" + review.getSection() + "', jobTitle = '" +
                    review.getJobTitle() + "', performanceSummary = '" + review.getPerformanceSummary() + "', reviewerComments = '" +
                    review.getReviewerComments() + "', reviewerRecommendation = '" + review.getReviewerRecommendation() + "' WHERE reviewID=" +
                    review.getReviewID();
            s.executeUpdate(sql);

            submitReviewPerformance(review);
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    /**
     * Adds the Performance Review list to the database
     * Calls the method that adds the goals to the database
     * @param review a Review
     */
    public void submitReviewPerformance(Review review)
    {
        try {
            Statement s = con.createStatement();
            ArrayList<Review.PastPerformance> pastPerformances = review.getPerformanceArrayList();
            System.out.println(pastPerformances.size());
            int startingNum = countRows("Performance_Details") + 1;

            for(Review.PastPerformance performance : pastPerformances) {
                String sql = "INSERT INTO Performance_Details VALUES (" + startingNum + ", " + review.getReviewID() + ", '" +
                        performance.getObjectives() + "', '" + performance.getAchievements() + "')";
                s.executeUpdate(sql);
                startingNum++;
            }

            submitReviewGoal(review);
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    /**
     * Adds the goals to the database
     * @param review a review
     */
    public void submitReviewGoal(Review review) {
        try {
            Statement s = con.createStatement();
            ArrayList<Review.GoalList> goals = review.getGoalArrayList();
            System.out.println(goals.size());
            int startingNum = countRows("Goal_List") + 1;

            for (Review.GoalList goal : goals) {
                String sql = "INSERT INTO Goal_List VALUES (" + startingNum + ", " + review.getReviewID() + ", '" + goal.getGoal() + "')";
                s.executeUpdate(sql);
                startingNum++;
            }

            con.commit();

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    /**
     * Gets an Arraylist of all unsigned reviews that a user is involved in
     * @param username a username
     * @return an Arraylist
     */
    public ArrayList<String> getUnsignedReviews(String username)
    {
        ArrayList<String> reviews = new ArrayList<>();
        try {
            Statement s = con.createStatement();
            String sql = "SELECT reviewID,username,manager,secondmanager FROM Review_Details WHERE secondManager IS NOT NULL " +
                    "AND ((username='" + username + "' AND revieweeSignature IS NULL) OR (manager='" + username + "' AND managerSignature IS NULL) " +
                    "OR (secondManager='" + username + "' AND secondManagerSignature IS NULL))";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                reviews.add(rs.getInt("reviewID") + ", Name: " + rs.getString("username") + ", Supervisor: " +
                        rs.getString("manager") + ", Second Supervisor: " + rs.getString("secondManager"));
            }
            return reviews;
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return null;
    }

    /**
     * Signs the review as a user
     * @param reviewID a review ID
     * @param user a column name
     * @param date a date
     */
    public void signReview(int reviewID, String user, String date)
    {
        try {
            Statement s = con.createStatement();
            String sql = "UPDATE Review_Details SET " + user + "='" + date + "' WHERE reviewID = " + reviewID;
            s.executeUpdate(sql);
            con.commit();
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }
}


