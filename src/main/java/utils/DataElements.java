package utils;

public class DataElements {

    private String testMethodName = null;
    private String params = null;
    private String runStatus = null;

    public DataElements(String testMethodName, String runStatus, String params) {
        this.testMethodName = testMethodName;
        this.runStatus = runStatus;
        this.params = params;
    }


    public String getTestMethodName() {
        return testMethodName;
    }

    public void setTestMethodName(String testMethodName) {
        this.testMethodName = testMethodName;
    }

    public String getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(String runStatus) {
        this.runStatus = runStatus;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
