package config.utils;

import java.util.ArrayList;
import java.util.List;

public class CaseElement {

    private String testName = "";

    private List<String> caseNames = new ArrayList<>();

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public List<String> getCaseNames() {
        return caseNames;
    }

    public void setCaseNames(List<String> caseNames) {
        this.caseNames = caseNames;
    }

    public void addCase(String caseName){
        this.caseNames.add(caseName);
    }

    public int getCount() {
        return caseNames.size();
    }
}
