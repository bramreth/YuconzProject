import javafx.geometry.Pos;

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
     * Initializes the User object
     * @param userID - ID of the currecnt user
     * @param name
     * @param surname
     * @param pos - position
     * @param dept - department
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

    public String getUserInfo()
    {
        return ("User ID:     " + getUserID() + "\n" +
                "Username:    " + getUsername() + "\n" +
                "Name:        " + getName() + "\n" +
                "Position:    " + getPosition().getPositionName() + "\n" +
                "Supervisor:  " + getSupervisorID() + "\n" +
                "Department   " + getDepartment()
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

    public Position getPosition() {
        return pos;
    }

    public String getSupervisorID() {
        return pos.getSupervisor();
    }
}
