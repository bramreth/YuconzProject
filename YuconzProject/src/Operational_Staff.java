/**
 * Created by Maximilian on 16/02/2017.
 */
public class Operational_Staff extends Position
{
    /**
     * Constructor
     * Initializes the Operational_Staff object
     * @param man - manager of the employee
     */
    public Operational_Staff(User man)
    {
        positionName = "Operational_Staff";
        manager = man;
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
    public User getManager()
    {
        return manager;
    }
}
