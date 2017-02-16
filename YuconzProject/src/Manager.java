import java.util.List;

/**
 * Created by Maximilian on 16/02/2017.
 */
public class Manager extends Position
{
    /**
     * Constructor
     * Initializes the Operational_Staff object
     * @param dir - manager of the employee
     * @param subs - list of subordinates
     */
    public Manager(User dir, List<User> subs)
    {
        positionName = "Manager";
        director = dir;
        subordinates = subs;
    }

    /**
     * getPositionName
     * Returns the position name
     * @return positionName
     */
    public String getPositionName()
    {
        return positionName;
    }

    /**
     * getManager
     * Returns the manager
     * @return manager
     */
    public User getDirector()
    {
        return director;
    }

    /**
     * getSubordinates
     * Returns the list of subordinates
     * @return rubordinates
     */
    public List<User> getSubordinates()
    {
        return subordinates;
    }
}
