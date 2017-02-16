import java.util.List;

/**
 * Created by Maximilian on 16/02/2017.
 */
public class Director extends Position
{
    /**
     * Constructor
     * Initializes the Operational_Staff object
     * @param subs - list of subordinates
     */
    public Director(List<User> subs)
    {
        positionName = "Director";
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
     * getSubordinates
     * Returns the list of subordinates
     * @return subordinates
     */
    public List<User> getSubordinates()
    {
        return subordinates;
    }
}
