import java.util.List;

/**
 * Created by bramreth on 2/13/17.
 */
public class User
{
    private String username, userID, name, surname, dept;
    private Position pos;
    //private Position pos;
    /**
     * Constructor
     * @param username the username of the  user
     * @param userID the userID of the user
     * @param name the name of the user
     * @param surname the surname of the user
     * @param dept the department of the user
     * @param pos the position of the user
     * @param supervisor the supervisor of the user
     */
    public User(String username, String userID, String name, String surname, String dept, String pos, String supervisor)
    {
        this.username = username;
        this.userID = userID;
        this.name = name;
        this.surname = surname;
        this.dept = dept;

        if(pos.equalsIgnoreCase("Operational Staff")) {
            this.pos = new Position(pos, supervisor);
        } else if (pos.equalsIgnoreCase("manager")) {
            this.pos = new Position(pos, supervisor);
        } else {
            this.pos = new Position(pos, null);
        }
    }

    /**
     * Returns the user info in a formatted string
     * @return a formatted string
     */
    public String getUserInfo()
    {
        return ("<HTML>User ID:      " + getUserID() + "<BR>" +
                "Username:     " + getUsername() + "<BR>" +
                "Name:         " + getName() + "<BR>" +
                "Position:     " + getPosition().getPositionName() + "<BR>" +
                "Supervisor:   " + getSupervisorID() + "<BR>" +
                "Subordinates: " + getSubordinatesString() + "<BR>" +
                "Department:   " + getDepartment() + "</HTML>"
                );
    }



    /*
     * Accessors for the private variables
     */

    /**
     * Returns the username
     * @return a string
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * Returns the userID
     * @return a string
     */
    public String getUserID()
    {
        return userID;
    }

    /**
     * Returns the full name formatted
     * @return a formatted string
     */
    public String getName()
    {
        return surname + ", " + name;
    }

    /**
     * Returns the first name
     * @return a string
     */
    public String getFirstName()
    {
        return name;
    }


    /**
     * Returns the surname
     * @return a string
     */
    public String getSurname()
    {
        return surname;
    }

    /**
     * Returns the department
     * @return a string
     */
    public String getDepartment()
    {
        return dept;
    }

    /**
     * Returns the position
     * @return a position
     */
    public Position getPosition()
    {
        return pos;
    }

    /**
     * Returns the supervisor username
     * @return a string
     */
    public String getSupervisorID()
    {
        return pos.getSupervisor();
    }

    /**
     * Formats and returns a list of the subordinates of the user (if there are any)
     * @return a formatted string
     */
    private String getSubordinatesString()
    {
        String subs = "";
        boolean first = true;
        for(String sub : getPosition().getSubordinates())
        {
            if(first) {
                subs += sub;
                first = false;
            } else {
                subs += ", " + sub;
            }
        }

        if (subs.equalsIgnoreCase("")) {
            return "none";
        } else {
            return subs;
        }
    }

    /**
     * Sets the department for privilege purposes
     * @param dept department
     */
    public void setDept (String dept)
    {
        this.dept = dept;
    }
}
