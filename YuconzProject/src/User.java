/**
 * Created by bramreth on 2/13/17.
 */
public class User
{
    private String username, userID, name, surname, dept, pos;
    /**
     * Constructor
     * Initializes the User object
     * @param userID - ID of the currecnt user
     * @param name
     * @param surname
     * @param pos - position
     * @param dept - department
     */
    public User(String username, String userID, String name, String surname, String dept, String pos)
    {
        this.username = username;
        this.userID = userID;
        this.name = name;
        this.surname = surname;
        this.dept = dept;
        this.pos = pos;
    }

    public String getUserInfo()
    {
        return ("User ID:   " + getUserID() + "\n" +
                "Username:  " + getUsername() + "\n" +
                "Name:      " + getName() + "\n" +
                "Position:  " + getPosition() + "\n" +
                "Department " + getDepartment()
                );
    }

    /*
     * Accessors for the private variables
     */
    public String getUsername()
    {
        return username;
    }

    public String getUserID()
    {
        return userID;
    }

    public String getName()
    {
        return surname + ", " + name;
    }

    public String getDepartment()
    {
        return dept;
    }

    public String getPosition()
    {
        return pos;
    }
}
