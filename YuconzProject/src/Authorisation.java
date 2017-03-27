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
     * Checks if a given user is authorised to take an action
     * @param currentUser user
     * @param action action the wish to take
     * @return true if the user can take the action, otherwise false
     */
    public boolean authorisationCheck(User currentUser, String userIn, String action) {
        switch (action) {
            case "readPersonalDetails":
                if (currentUser.getDepartment().equals("Human Resources") || currentUser.getPosition().getPositionName().equals("Director") || currentUser.getUsername().equals(userIn)) {
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
                    for(String temp: currentUser.getPosition().getSubordinates()){
                        if(temp.equals(userIn)){
                            return true;
                        }
                    }
                }
                break;
            case "amendReviewRecord":
                if (currentUser.getPosition().getPositionName().equals("Manager")) {
                    for(String temp: currentUser.getPosition().getSubordinates()){
                        if(temp.equals(userIn)){
                            return true;
                        }
                    }
                }
        }
        return false;
    }

    /**
     * checks whether the user has permission to read a given review
     * @param userIn user
     * @param reviewIn review
     * @return true if allowed, false otherwise
     */
    public boolean readReviewAthorisationCheck(User userIn, Review reviewIn){
        if(reviewIn.getName().equals(userIn.getUsername()) || reviewIn.getManager().equals(userIn.getUsername())||reviewIn.getSecondManager().equals(userIn.getUsername()) || userIn.getPosition().getPositionName().equals("Director")) {
            return true;
        }else{
            return false;
        }
    }

    /**
     * checks whether the user has permission to sign a given review
     * @param userIn username
     * @param reviewIn review
     * @return true if allowed, false otherwise
     */
    public boolean signReviewAthorisationCheck(String userIn, Review reviewIn){
        if(reviewIn.getName().equals(userIn) || reviewIn.getManager().equals(userIn)||reviewIn.getSecondManager().equals(userIn)) {
            return true;
        }else{
            return false;
        }
    }

}

