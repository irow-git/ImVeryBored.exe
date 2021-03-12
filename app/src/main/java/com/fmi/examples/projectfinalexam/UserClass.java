package com.fmi.examples.projectfinalexam;

public class UserClass {
    String userNameClass, userEmailClass, userPassClass;
    int userHighScoreClass, userTimeTrialBestScoreClass;

    public UserClass(){

    }


    public UserClass(String userNameClass, String userEmailClass, String userPassClass, int userHighScoreClass, int userTimeTrialBestScoreClass) {
        this.userNameClass = userNameClass;
        this.userEmailClass = userEmailClass;
        this.userPassClass = userPassClass;
        this.userHighScoreClass = userHighScoreClass;
        this.userTimeTrialBestScoreClass = userTimeTrialBestScoreClass;
    }


    public String getUserNameClass() {
        return userNameClass;
    }

    public void setUserNameClass(String userNameClass) {
        this.userNameClass = userNameClass;
    }

    public String getUserEmailClass() {
        return userEmailClass;
    }

    public void setUserEmailClass(String userEmailClass) {
        this.userEmailClass = userEmailClass;
    }

    public String getUserPassClass() {
        return userPassClass;
    }

    public int getUserTimeTrialBestScoreClass() {
        return userTimeTrialBestScoreClass;
    }

    public void setUserTimeTrialBestScoreClass(int userTimeTrialBestScoreClass) {
        this.userTimeTrialBestScoreClass = userTimeTrialBestScoreClass;
    }

    public void setUserPassClass(String userPassClass) {
        this.userPassClass = userPassClass;
    }

    public int getUserHighScoreClass() {
        return userHighScoreClass;
    }

    public void setUserHighScoreClass(int userHighScoreClass) {
        this.userHighScoreClass = userHighScoreClass;
    }
    public String toStrings(){
        return "USER - " + this.userNameClass + "\n" + "HIGH SCORE - " + userHighScoreClass + "\n" + "CPM - " + userTimeTrialBestScoreClass;
    }
}
