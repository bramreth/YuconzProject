import java.util.ArrayList;

/**
 * Created by Maximilian on 16/02/2017.
 */
public class Position
{

    private String positionName;
    private String managerID = null;
    private String directorID = null;
    private ArrayList<String> subordinatesID = null;

    /**
     * Constructor
     * @param positionName name of the position
     * @param supervisor username of the supervisor
     */
    public Position (String positionName, String supervisor)
    {
        this.positionName = positionName;
        if(this.positionName.equalsIgnoreCase("Operational Staff")){
            managerID = supervisor;
        } else if (this.positionName.equalsIgnoreCase("Manager")){
            directorID = supervisor;
        }
    }

    /**
     * Returns the position name
     * @return positionName
     */
    public String getPositionName()
    {
        return positionName;
    }

    /**
     * Returns the manager ID
     * @return managerID
     */
    public String getSupervisor()
    {
        if(positionName.equalsIgnoreCase("Operational Staff")){
            return managerID;
        } else if (positionName.equalsIgnoreCase("Manager")){
            return directorID;
        } else {
            return "none";
        }
    }

    /**
     * Sets the ArrayList of subordinates to the list retrieved from the db
     * @param subordinatesID username of the subordinates
     */
    public void setSubordinates(ArrayList<String> subordinatesID){
        this.subordinatesID = subordinatesID;
    }

    /**
     * Returns the list of subordinates IDs
     * @return subordinatesID
     */
    public ArrayList<String> getSubordinates()
    {
        return subordinatesID;
    }
}