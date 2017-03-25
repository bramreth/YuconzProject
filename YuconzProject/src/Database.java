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
     */
    public Database(String dbName)
    {
        con = null;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection(dbName);
            ready = true;
            System.out.println("Opened database successfully");
        } catch ( Exception e ) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
}

    /**
     * getConnection
     * Returns the connection object for use elsewhere
     * @return con
     */
    public Connection getConnection()
    {
        return con;
    }

    /**
     * isReady
     * Returns true if the db connection is successful or false if it failed
     * @return true if connection was established, false otherwise
     */
    public boolean isReady()
    {
        return ready;
    }

    /**
     * getUser
     * Returns a user object
     * @param username
     * @return user
     */
    public User getUser(String username)
    {
        try {
            Statement s = con.createStatement();
            String sql = "SELECT * FROM Employee_Data WHERE username='" + username + "'";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                return new User(username, rs.getString("userID"), rs.getString("name"), rs.getString("surname"), rs.getString("department"), rs.getString("position"), rs.getString("supervisor"));
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return null;
    }

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

    public Document fetchPersonalDetails(String username)
    {

        try {
            Statement s = con.createStatement();
            String sql = "SELECT * FROM Personal_Details WHERE username='" + username + "'";
            ResultSet rs = s.executeQuery(sql);
            Document doc = new Document(username);
            while(rs.next()){
                //will overwrite doc each time it loops...
                doc.populateDocument(rs.getInt("documentID"), rs.getInt("userID"), rs.getString("name"), rs.getString("surname"), rs.getString("dateofbirth"), rs.getString("address"), rs.getString("town"), rs.getString("county"), rs.getString("postcode"), rs.getString("telephonenumber"), rs.getString("mobilenumber"), rs.getString("emergencycontact"), rs.getString("emergencycontactnumber"));
            }
            return doc;
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return null;
    }

    /**
     * check whether or not there is a personal details entry for the given user
     * @param userIn
     * @return
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

    public ArrayList<String> getUsersWithoutPersonalDetails()
    {
        ArrayList<String> personalDetailsUserList = new ArrayList<>();
        try {
            Statement s = con.createStatement();
            String sql = "SELECT Employee_Data.username FROM Employee_Data WHERE NOT EXISTS (SELECT Personal_Details.username FROM Personal_Details WHERE Personal_Details.username = Employee_Data.username)";
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
     * check whether or not there is a employee data entry for the given user
     * @param userIn
     * @return
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

    public void createNewUser(Document document)
    {
        try {
            Statement s = con.createStatement();
            String sql = "INSERT INTO Personal_Details (documentID, name, surname, dateofbirth, address, town, County, postcode, telephonenumber, mobilenumber, emergencycontact, emergencycontactnumber, username, userID) values ('" + document.getDocumentID() + "','" + document.getName() + "','" + document.getSurname() + "','" + document.getDob() + "','" + document.getAddress() + "','" + document.getTownCity() + "','" + document.getCounty() + "','" + document.getPostcode() + "','" + document.getTelephoneNumber() + "','" +document.getMobileNumber() + "','" + document.getEmergencyContact() + "','" + document.getEmergencyContactNumber() + "','" + document.getUsername() + "','" + getStaffID(document.getUsername()) + "')";
            s.executeUpdate(sql);
            System.out.println("Document has been created successfully");
            con.commit();
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

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

    public void createNewReview (String supervisor, String reviewee)
    {
        try {
            Statement s = con.createStatement();
            String table = "Review_Details";
            String sql = "INSERT INTO Review_Details (reviewID, username, userID, manager) values (" + (countRows(table)+1) + ", '" + reviewee + "', " + getStaffID(reviewee) + ", '" +  supervisor + "')";
            s.executeUpdate(sql);
            System.out.println("Review has been created successfully");
            con.commit();
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public ArrayList<String> getReviewsMissingSecondManager()
    {
        ArrayList<String> reviews = new ArrayList<>();
        try {
            Statement s = con.createStatement();
            String sql = "SELECT username,reviewID,manager FROM Review_Details WHERE secondManager IS NULL";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                reviews.add(rs.getInt("reviewID") + ", Name: " + rs.getString("username") + ", Manager: " + rs.getString("manager"));
            }
            return reviews;
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return null;
    }
}
