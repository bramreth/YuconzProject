import java.util.List;

/**
 * Created by Maximilian on 16/02/2017.
 */
public class Manager extends Position
{
    /**
     * Constructor
     * Initializes the Operational_Staff object
     * @param directorID - manager of the employee
     * @param subordinatesID - list of subordinates
     */
    public Manager(String directorID, List<String> subordinatesID)
    {
        positionName = "Manager";
        this.directorID = directorID;
        this.subordinatesID = subordinatesID;
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
     * Returns the manager ID
     * @return managerID
     */
    public String getDirector()
    {
        return directorID;
    }

    /**
     * getSubordinates
     * Returns the list of subordinates IDs
     * @return subordinatesID
     */
    public List<String> getSubordinates()
    {
        return subordinatesID;
    }
}
