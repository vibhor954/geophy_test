package utils;

import logger.Log;
import net.rcarz.jiraclient.*;
import java.io.File;
import java.util.Collections;


public class JiraOperationsUtil {

    public static JiraClient jiraClient;

    public static void createJiraInstance(String jiraURL, String username, String password) {
        jiraClient = new JiraClient(jiraURL, new BasicCredentials(username, password));
    }


    /**
     * This method can be used to check if a bug or task for a particular label is already exists in JIRA.
     *
     * @param jiraQuery - jira query required for the filtration of the results. e.g if project is GeoPhy and we want all bugs in the project it can be filtered as "project = GeoPhy AND issuetype = Bug"
     * @param label     - Label should be unique like a test method name so we'll not create multiple issue for the same failed test method.
     * @return
     */
    synchronized public static boolean issueAlreadyExist(String jiraQuery, String label) {
        int maxResult = 50;
        int startAt = 0;
        int processedRecord = 0;
        try {

            Issue.SearchResult searchResult = jiraClient.searchIssues(jiraQuery, Field.LABELS, maxResult, startAt);
            Log.info("Total nuber of jira tickets for the query: " + jiraQuery + " are >> " + searchResult.total);
            //testReport.get().log(LogStatus.INFO, "Total nuber of jira tickets for the query: " + jiraQuery + " are >> " + searchResult.total);

            do {
                searchResult = jiraClient.searchIssues(jiraQuery, Field.LABELS, maxResult, startAt);
                for (Issue issue : searchResult.issues) {
                    if (issue.getLabels().contains(label)) {
                        Log.info("Ticket with same label is already exist. Ticket id is: " + issue.getKey());
                        //testReport.get().log(LogStatus.INFO, "Ticket with same label is already exist. Ticket id is: " + issue.getKey());
                        Issue currentIssue = jiraClient.getIssue(issue.getKey());
                        if (currentIssue.getStatus().getName().equals("Done")) {
                           /* currentIssue.update().
                                    field(Field.STATUS, "Reopen").
                                    execute();*/
                            currentIssue.addComment("Issue still present");
                            Log.info("Please reopen the issue manually: " + currentIssue.getKey());

                        } else if (currentIssue.getStatus().getName().equals("Reopen") || currentIssue.getStatus().getName().equals("Inprogress") || currentIssue.getStatus().getName().equals("Todo")) {
                            currentIssue.addComment("Issue still present.");
                        }
                        return true;
                    }
                }
                startAt = startAt + maxResult;
                processedRecord = maxResult + 50;

            } while (processedRecord <= searchResult.total);
        } catch (JiraException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method can be used to create a new issue in JIRA.
     *
     * @param defectSummary
     * @param defectDescription
     * @param label             - label for the issue you want to create.
     * @param defectAssignee
     */
    synchronized public static void createNewIssue(String defectSummary, String defectDescription, String label, String defectAssignee) {
        try {
            String summary="project = "+ GlobalVars.prop.getProperty(Constants.JIRA_PROJECT_NAME) +
                    " AND issuetype = " + GlobalVars.prop.getProperty(Constants.JIRA_DEFECT_TYPE);
            if(issueAlreadyExist(summary, label)){
                Log.info(defectSummary+" :bug already exists");
            }

            else{
                Issue.FluentCreate newIssue = jiraClient.
                        createIssue(GlobalVars.prop.getProperty(Constants.JIRA_PROJECT_NAME),
                                GlobalVars.prop.getProperty(Constants.JIRA_DEFECT_TYPE)).
                        field(Field.SUMMARY, defectSummary).field(Field.DESCRIPTION, defectDescription).
                        field(Field.LABELS, Collections.singletonList(label)).field(Field.ASSIGNEE, defectAssignee);
                newIssue.execute();
            }

        } catch (JiraException e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    /**
     * This method can be used to create a subtask in JIRA.
     *
     * @param key             - key of the issue under which you want to create subtask.
     * @param label           - label for the subtask you want to create.
     * @param subtaskSummary
     * @param subtaskAssignee
     */
    synchronized public static void createSubtask(String key, String label, String subtaskSummary, String subtaskAssignee) {
        try {
            Issue issue = jiraClient.getIssue(key);
            System.out.println(issue.getSummary());
            Issue.FluentCreate subtask = issue.createSubtask().field(Field.SUMMARY, subtaskSummary).field(Field.ASSIGNEE, subtaskAssignee).
                    field(Field.LABELS, Collections.singletonList(label));
            subtask.execute();
        } catch (JiraException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method can be used to get issue key or Jira id
     *
     * @param jiraQuery
     * @param label
     * @return
     */
    public static String getIssueKey(String jiraQuery, String label) {
        int maxResult = 50;
        int startAt = 0;
        int processedRecord = 0;
        try {
            Issue.SearchResult searchResult = jiraClient.searchIssues(jiraQuery, Field.LABELS, maxResult, startAt);
            System.out.println("Total nuber of jira tickets for the query: " + jiraQuery + " are >> " + searchResult.total);
            do {
                searchResult = jiraClient.searchIssues(jiraQuery, Field.LABELS, maxResult, startAt);
                for (Issue issue : searchResult.issues) {
                    if (issue.getLabels().contains(label)) {
                        return issue.getKey();
                    }
                }
                startAt = startAt + maxResult;
                processedRecord = maxResult + 50;

            } while (processedRecord <= searchResult.total);
        } catch (JiraException e) {
            e.printStackTrace();
        }
        System.out.println("There are no issue with the given label.");
        return null;
    }

    /**
     * This method can be used to add an attachment to an issue
     *
     * @param filePath - path to the file you want to add as an attachment
     * @param key      - JIRA Id the issue
     */
    public static void addAttachment(String filePath, String key) {
        try {
            File file = new File(filePath);
            Issue issue = jiraClient.getIssue(key);
            issue.addAttachment(file);
        } catch (JiraException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param key     - JIRA id of the issue
     * @param comment
     */
    public static void addComment(String key, String comment) {
        try {
            Issue issue = jiraClient.getIssue(key);
            issue.addComment(comment);
        } catch (JiraException e) {
            e.printStackTrace();
        }
    }
}
