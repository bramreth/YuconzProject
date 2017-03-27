import java.util.ArrayList;

/**
 * Created by bramreth on 3/20/17.
 */
public class Review {
    private int staffNo, reviewID;
    private String name, manager, secondManager, section, jobTitle;
    private ArrayList<PastPerformance> performanceArrayList = new ArrayList<PastPerformance>();
    private String performanceSummary;
    private ArrayList<GoalList> goalArrayList = new ArrayList<GoalList>();
    private String reviewerComments;
    private String reviewerRecommendation;
    private Signature revieweeSignature, managerSignature, secondManagerSignature;
    private boolean signedOff;

    /**
     * sets all information except for performance summaries, goal summaries, the recommendation and signatures
     * and second manager
     * @param staffNoIn Staff number
     * @param nameIn reviewee username
     * @param managerIn manager username
     * @param secondManagerIn second manager username
     * @param sectionIn reviewee department
     * @param jobTitleIn reviewee job title
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

    /**
     * returns a string with all the information in a review
     * @return a string
     */
    public String getFullReview(){
        String fReview = "";
        fReview += "reviewID: " +reviewID + "\nusername: " + name + " staffNo: " + staffNo + "\nmanager: " + manager;
        if(secondManager==null){
            fReview+= " second manager: none assigned\n";
        }else{
            fReview+= " second manager: " +secondManager + "\n";
        }
        fReview += "section: " +section + " job title: " + jobTitle + "\nperformances:\n";
        for(PastPerformance temp: performanceArrayList){
            fReview+="no: "+temp.getNo() + "\nobjective: " + temp.getObjectives() +"\nachievements: " +temp.getAchievements() +"\n";
        }
        fReview+="performance summary: "+performanceSummary + "\ngoal list:\n";
        for(GoalList temp: goalArrayList){
            fReview+="no: "+temp.getNo() + "\ngoal: " + temp.getGoal() + "\n";
        }
        fReview+="reviewer comments: " +reviewerComments +"\nreviewer recommendation: " + reviewerRecommendation+"\n";
        if(revieweeSignature.signed){
            fReview+="reviewee signed on: "+revieweeSignature.date+"\n";
        }else{
            fReview+="reviewee has not signed\n";
        }
        if(managerSignature.signed){
            fReview+="manager signed on: "+managerSignature.date+"\n";
        }else{
            fReview+="manager has not signed\n";
        }
        if(secondManagerSignature.signed){
            fReview+="second manager signed on: "+secondManagerSignature.date+"\n";
        }else{
            fReview+="second manager has not signed\n";
        }
        return fReview;
    }

    /**
     * Checks if the review is finished and signed
     * @return true if finished and signed, otherwise false
     */
    public boolean checkComplete(){
        if(revieweeSignature.signed && managerSignature.signed && secondManagerSignature.signed){
            return true;
        }
        return false;
    }

    //region addInformation
    /**
     * add a performance summary
     * @param performanceSummaryIn performance summary
     */
    public void addPerformanceSummary(String performanceSummaryIn){
       performanceSummary = performanceSummaryIn;
    }

    /**
     * add performances to the performance list
     * @param performIn performance
     */
    public void addPerformance(PastPerformance performIn){
        performanceArrayList.add(performIn);
    }

    /**
     * add goals to the goal list
     * @param goalIn goal
     */
    public void addGoal(GoalList goalIn){
        goalArrayList.add(goalIn);
    }

    /**
     * add a reviewer comment
     * @param commentIn comment
     */
    public void addReviewerComment(String commentIn){
        reviewerComments = commentIn;
    }

    /**
     * add the reviewers recommendation
     * @param recommendationIn reccomendation
     */
    public void addRecommendation(String recommendationIn){
        reviewerRecommendation = recommendationIn;
    }

    /**
     * Allows the user to sign and tracks which signature is signed
     * @param type which of the three signatures is being added
     * @param dateIn date of signature
     */
    public void sign(String type, String dateIn){
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

    /**
     * True if finished, false if not
     * @param signedOffIn signed off
     */
    public void signOff(boolean signedOffIn){
        signedOff = signedOffIn;
    }

    /**
     * Gets the staff number
     * @return staffNo
     */
    public int getStaffNo() {
        return staffNo;
    }

    /**
     * Gets the reviewee username
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the manager username
     * @return manager
     */
    public String getManager() {
        return manager;
    }

    /**
     * Gets the second manager username
     * @return secondManager
     */
    public String getSecondManager() {
        return secondManager;
    }

    /**
     * Gets the department of the reviewee
     * @return section
     */
    public String getSection() {
        return section;
    }

    /**
     * Gets the job title of the reviewee
     * @return jobTitle
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * Gets the reviewer comments
     * @return reviewerComments
     */
    public String getReviewerComments() {
        return reviewerComments;
    }

    /**
     * Gets the reviewer recommendation
     * @return reviewerRecommendation
     */
    public String getReviewerRecommendation() {
        return reviewerRecommendation;
    }

    /**
     * Gets the performance summary
     * @return performanceSummary
     */
    public String getPerformanceSummary() {
        return performanceSummary;
    }

    /**
     * Gets the review ID
     * @return reviewID
     */
    public int getReviewID() {
        return reviewID;
    }

    /**
     * Gets the performance arraylist
     * @return performanceArrayList
     */
    public ArrayList<PastPerformance> getPerformanceArrayList()
    {
        return performanceArrayList;
    }

    /**
     * Returns the list of goals
     * @return goalArrayList
     */
    public ArrayList<GoalList> getGoalArrayList()
    {
        return goalArrayList;
    }
    //endregion

    //region data structures
    /**
     * data structure for list of past performances
     */
    public static class PastPerformance{
        private int no;
        private String objectives, achievements;

        /**
         * Constructor
         * @param noIn Past performance number
         * @param objIn objectives
         * @param achieveIn achievements
         */
        public PastPerformance(int noIn, String objIn, String achieveIn){
            no = noIn;
            objectives = objIn;
            achievements = achieveIn;
        }

        /**
         * Gets the number of the past performance
         * @return no
         */
        public int getNo()
        {
            return no;
        }

        /**
         * Gets the objectives
         * @return objectives
         */
        public String getObjectives()
        {
            return objectives;
        }

        /**
         * Gets the achievements
         * @return achievements
         */
        public String getAchievements()
        {
            return achievements;
        }
    }

    /**
     * data structure for list of goals
     */
    public static class GoalList{
        private int no;
        private String  goal;

        /**
         * Constructor
         * @param noIn Goal number
         * @param goalIn Goal
         */
        public GoalList(int noIn, String goalIn){
            no = noIn;
            goal = goalIn;
        }

        /**
         * Gets the goal number
         * @return no
         */
        public int getNo()
        {return no;}

        /**
         * Gets the goal
         * @return goal
         */
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
        private String date;

        /**
         * Constructor
         * @param typeIn type of signature
         */
        public Signature(String typeIn){
            signed = false;
            date = null;
            type = typeIn;
        }

        /**
         * Singing the signature by giving it a true or false and a date
         * @param signedIn Boolean storing if signed or not
         * @param dateIn date of signature
         */
        public void sign(boolean signedIn, String dateIn){
            signed = signedIn;
            date = dateIn;
        }
    }
//endregion

    //region Enums

    /**
     * Recommendation Enums
     */
    public enum Recommendation{
        STAY_IN_POST, SALARY_INCREASE, PROMOTION, PROBATION, TERMINATION
    }

    //endregion
}
