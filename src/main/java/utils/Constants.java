package utils;

import java.util.ArrayList;

public class Constants {
	
	private Constants() {
	  }

    public static final String CHROME="chrome";
    public static final String FIREFOX="firefox";
    public static final String CHROMEDRIVER="chromeDriver";
    public static final String GECKODRIVER="geckoDriver";
    public static final String EDGEDRIVER="edgeDriver";
    public static final String BROWSER="browser";
    public static final String URL="url";
    public static final String MAX_RETRY = "maxTry";
    public static final String EMAIL_ID = "email_id";
    public static final String EMAIL_PASSWORD= "email_password";
    public static String reportNAME = "";
    public static String loginPAGEURL = "loginpageUrl";
    public static String searchPageURL = "searchpageUrl";
    public static String historyPAGEURL = "historypageUrl";
    public static String profilePAGEURL = "profilepageUrl";
    public static String signupPAGEURL = "signuppagepageUrl";
    public static ArrayList<String> TEST_RESULT_COUNT = new ArrayList<>();
    public static final String MAIL_RECIPIENT = "mailRecipient";
    public static final String JIRA_FLAG = "raiseIssue";
    public static final String TESTLINK_FLAG = "updateTestLink";
    public static final String MAIL_FLAG = "sendMail";


}
