import java.util.ArrayList;
import java.util.Date;

/**
 * Created by bramreth on 3/20/17.
 */
public class Review {
    private int staffNo;
    private String name, manager, secondManager, section, jobTitle;
    private ArrayList<PastPerformance> performancearrayList;
    private String performanceSummary;
    private ArrayList<GoalList> goalListArrayList;
    private String reviewerComments;
    private Recommendation reviewerRecommendation;
    private Signature revieweeSignature, managerSignature, secondReviewerSignature;
    public Review(){

    }

    /**
     * data structure for list of past performances
     */
    public class PastPerformance{
        private int no;
        private String objectives, achievements;
        public PastPerformance(int noIn, String objIn, String achieveIn){
            no = noIn;
            objectives = objIn;
            achievements = achieveIn;
        }
    }

    /**
     * data structure for list of goals
     */
    public class GoalList{
        private int no;
        private String  goal;
        public GoalList(int noIn, String goalIn){
            no = noIn;
            goal = goalIn;
        }
    }

    /**
     * data structure for signature
     */
    public class Signature{
        private boolean signed;
        private String  type;
        private Date date;
        public Signature(String typeIn){
            signed = false;
            type = typeIn;
            date = null;
        }
        public void sign(boolean signedIn, Date dateIn){
            signed = signedIn;
            date = dateIn;
        }
    }

    public enum Recommendation{
        STAY_IN_POST, SALARY_INCREASE, PROMOTION, PROBATION
    }
}
