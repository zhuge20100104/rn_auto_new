package base.report.beans;

import java.util.ArrayList;
import java.util.List;

public class SuiteInfo {
    private int total;
    private int pass;
    private int fail;
    private int skip;

    private List<TestInfo> passTests = new ArrayList<>();
    private List<TestInfo> failTests = new ArrayList<>();
    private List<TestInfo> skipTests = new ArrayList<>();


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPass() {
        return pass;
    }

    public void setPass(int pass) {
        this.pass = pass;
    }

    public int getFail() {
        return fail;
    }

    public void setFail(int fail) {
        this.fail = fail;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public List<TestInfo> getPassTests() {
        return passTests;
    }

    public void setPassTests(List<TestInfo> passTests) {
        this.passTests = passTests;
    }

    public List<TestInfo> getFailTests() {
        return failTests;
    }

    public void setFailTests(List<TestInfo> failTests) {
        this.failTests = failTests;
    }

    public List<TestInfo> getSkipTests() {
        return skipTests;
    }

    public void setSkipTests(List<TestInfo> skipTests) {
        this.skipTests = skipTests;
    }
}
