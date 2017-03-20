import java.util.ArrayList;
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
    public boolean authorisationCheck(User currentUser, String userIn, String action) {
        switch (action) {
            case "readPersonalDetails":
                if (currentUser.getDepartment().equals("Human Resources") || currentUser.getDepartment().equals("Director") || currentUser.getUsername().equals(userIn)) {
                    return true;
                }
                break;
            case "createPersonalDetails":
                if (currentUser.getDepartment().equals("Human Resources")) {
                    return true;
                }
                break;
            case "amendPersonalDetails":
                if (currentUser.getDepartment().equals("Human Resources")) {
                    return true;
                }
                break;
            case "createReviewRecord":
                if (currentUser.getDepartment().equals(userIn) || currentUser.getPosition().getPositionName().equals("Director")) {
                    return true;
                }
                for (String str : currentUser.getPosition().getSubordinates()) {
                    if(str.equals(userIn)){
                        return  true;
                    }
                }
                break;
        }
        return false;
    }

}

