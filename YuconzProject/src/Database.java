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
    public Database(String host, String username, String password)
    {
        try {
            this.host = host;
            this.username = username;
            this.password = password;
            con = DriverManager.getConnection(host,username,password);
            ready = true;
        }
        catch ( SQLException err ) {
        System.out.println( err.getMessage( ) );
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
                doc.populateDocument(rs.getInt("documentID"), rs.getString("name"), rs.getString("surname"), rs.getString("dateofbirth"), rs.getString("address"), rs.getString("town"), rs.getString("County"), rs.getString("postcode"), rs.getString("telephonenumber"), rs.getString("mobilenumber"), rs.getString("emergencycontact"), rs.getString("emergencycontactnumber"));
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
    public boolean checkExists(String userIn)
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
     * check whether or not there is a employee data entry for the given user
     * @param userIn
     * @return
     */
    public boolean checkExistsEmployee(String userIn)
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
            String sql = "INSERT INTO Personal_Details (name, surname, dateofbirth, address, town, county, postcode, telephonenumber, mobilenumber, emergencycontact,emergencycontactnumber,username,userID) values ('"+ document.getName() + "','" + document.getSurname() + "','" + document.getDob() + "','" + document.getAddress() + "','" + document.getTownCity() + "','" + document.getCounty() + "','" + document.getPostcode() + "','" + document.getTelephoneNumber() + "','" +document.getMobileNumber() + "','" + document.getEmergencyContact() + "','" + document.getEmergencyContactNumber() + "','" + document.getUsername() + "','" + getStaffID(document.getUsername()) + "')";
            ResultSet rs = s.executeQuery(sql);
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public void amendUserPersonalDetails(Document document)
    {
        try {
            Statement s = con.createStatement();
            String sql = "DELETE FROM Personal_Details WHERE username='" + document.getUsername() + "'";
            ResultSet rs = s.executeQuery(sql);
            createNewUser(document);
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }

    }
}
