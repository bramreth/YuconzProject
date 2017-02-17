/**
 * Created by Maximilian on 16/02/2017.
 */
public class Operational_Staff extends Position
{
    /**
     * Constructor
     * Initializes the Operational_Staff object
     * @param managerID - manager of the employee
     */
    public Operational_Staff(String managerID)
    {
        positionName = "Operational_Staff";
        this.managerID = managerID;
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
     * @return manager ID
     */
    public String getManager()
    {
        return managerID;
    }
}
