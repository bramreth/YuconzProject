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
            case "allocateReviewer":
                if (currentUser.getDepartment().equals("Human Resources")) {
                    return true;
                }
                break;
            case "createReviewRecord":
                if (currentUser.getPosition().getPositionName().equals("Manager")) {
                    for(String temp: currentUser.getPosition().getSubordinates()){
                        if(temp.equals(userIn)){
                            return true;
                        }
                    }
                }
                if(currentUser.getPosition().getPositionName().equals("Director")) {
                    return true;
                }
                break;
        }
        return false;
    }

    /**
     * checks whether the user has permission to read a given review
     * @param userIn
     * @param reviewIn
     * @return
     */
    public boolean readReviewAthorisationCheck(String userIn, Review reviewIn){
        if(reviewIn.getName().equals(userIn) || reviewIn.getManager().equals(userIn)||reviewIn.getSecondManager().equals(userIn)) {
            return true;
        }else{
            return false;
        }
    }

    /**
     * checks whether the user has permission to sign a given review
     * @param userIn
     * @param reviewIn
     * @return
     */
    public boolean signReviewAthorisationCheck(String userIn, Review reviewIn){
        if(reviewIn.getName().equals(userIn) || reviewIn.getManager().equals(userIn)||reviewIn.getSecondManager().equals(userIn)) {
            return true;
        }else{
            return false;
        }
    }

}

