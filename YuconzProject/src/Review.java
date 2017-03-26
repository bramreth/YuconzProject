import java.util.ArrayList;
import java.util.Date;

/**
 * Created by bramreth on 3/20/17.
 */
public class Review {
    private int staffNo, reviewID;
    private String name, manager, secondManager, section, jobTitle;
    private ArrayList<PastPerformance> performanceArrayList;
    private String performanceSummary;
    private ArrayList<GoalList> goalArrayList;
    private String reviewerComments;
    private String reviewerRecommendation;
    private Signature revieweeSignature, managerSignature, secondManagerSignature;
    private boolean signedOff;

    /**
     * sets all information except for performance summaries, goal summaries, the recommendation and signatures
     * and second manager
     * @param staffNoIn
     * @param nameIn
     * @param managerIn
     * @param secondManagerIn
     * @param sectionIn
     * @param jobTitleIn
     */
    public Review(int reviewIDIn, int staffNoIn, String nameIn, String managerIn, String secondManagerIn,
                  String sectionIn, String jobTitleIn){
        staffNo = staffNoIn;
        name = nameIn;
        manager = managerIn;
        secondManager =secondManagerIn;
        section = sectionIn;
        jobTitle = jobTitleIn;
        reviewID = reviewIDIn;
        revieweeSignature = new Signature("reviewee");
        managerSignature = new Signature("manager");
        secondManagerSignature = new Signature("secondManager");
    }

    public String getFullReview(){
        return "b";
    }

    public boolean checkComplete(){
        if(revieweeSignature.signed && managerSignature.signed && secondManagerSignature.signed){
            return true;
        }
        return false;
    }

    //region addInformation
    /**
     * add a performance summary
     * @param performanceSummaryIn
     */
    public void addPerformanceSummary(String performanceSummaryIn){
       performanceSummary = performanceSummaryIn;
    }

    /**
     * add performances to the performance list
     * @param performIn
     */
    public void addPerformance(PastPerformance performIn){
        performanceArrayList.add(performIn);
    }

    /**
     * add goals to the goal list
     * @param goalIn
     */
    public void addGoal(GoalList goalIn){
        goalArrayList.add(goalIn);
    }

    /**
     * add a reviewer comment
     * @param commentIn
     */
    public void addReviewerComment(String commentIn){
        reviewerComments = commentIn;
    }

    /**
     * add the reviewers recommendation
     * @param recommendationIn
     */
    public void addRecommendation(String recommendationIn){
        reviewerRecommendation = recommendationIn;
    }

    public void sign(String type, Date dateIn){
        switch (type){
            case "reviewee":
                revieweeSignature.sign(true,dateIn);
                break;
            case "manager":
                managerSignature.sign(true,dateIn);
                break;
            case "secondManager":
                secondManagerSignature.sign(true,dateIn);
                break;
        }
    }

    public void signOff(boolean signedOffIn){
        signedOff = signedOffIn;
    }

    public int getStaffNo() {
        return staffNo;
    }

    public String getName() {
        return name;
    }

    public String getManager() {
        return manager;
    }

    public String getSecondManager() {
        return secondManager;
    }

    public String getSection() {
        return section;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getReviewerComments() {
        return reviewerComments;
    }

    public String getReviewerRecommendation() {
        return reviewerRecommendation;
    }

    public String getPerformanceSummary() {
        return performanceSummary;
    }

    public int getReviewID() {
        return reviewID;
    }

    public ArrayList<PastPerformance> getPerformanceArrayList()
    {
        return performanceArrayList;
    }

    public ArrayList<GoalList> getGoalArrayList()
    {
        return goalArrayList;
    }
    //endregion

    //region data structures
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

        public String getObjectives()
        {
            return objectives;
        }

        public String getAchievements()
        {
            return achievements;
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

        public String getGoal()
        {
            return goal;
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
            date = null;
            type = typeIn;
        }
        public void sign(boolean signedIn, Date dateIn){
            signed = signedIn;
            date = dateIn;
        }
    }
//endregion

    //region Enums
    public enum Recommendation{
        STAY_IN_POST, SALARY_INCREASE, PROMOTION, PROBATION, TERMINATION
    }

    //endregion
}
