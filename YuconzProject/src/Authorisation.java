/**
 * Created by Maximilian on 20/02/2017.
 */
public class Authorisation {

    /**
     * Constructor
     * Initialises the authorization object
     */
    public Authorisation()
    {

    }

    /**
     * authorisationCheck
     * Checks if a given user is authorised to take an action
     * @param currentUser
     * @param action
     * @return true if the user can take the action, otherwise false
     */
    public boolean authorisationCheck(User currentUser, String action)
    {
        return true;
    }
}
