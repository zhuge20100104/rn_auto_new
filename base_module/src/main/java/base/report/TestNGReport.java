package base.report;

import org.testng.*;
import org.testng.xml.XmlSuite;
import base.report.beans.SuiteInfo;
import base.report.beans.TestInfo;
import base.report.testprocessers.FailedTestProcessor;
import base.report.testprocessers.PassedTestProcessor;
import base.report.testprocessers.SkippedTestProcessor;
import base.utils.FreeMarkUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestNGReport implements IReporter {


    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        List<SuiteInfo> suitInfoLists = new ArrayList<>();
        for(ISuite suite: suites) {
            SuiteInfo suiteInfo = new SuiteInfo();
            Map<String, ISuiteResult> suiteResults = suite.getResults();

            int passedSize = 0;
            int failedSize = 0;
            int skippedSize = 0;

            for (ISuiteResult suiteResult : suiteResults.values()) {
                ITestContext testContext = suiteResult.getTestContext();

                List<TestInfo> failedTests = new FailedTestProcessor().process(testContext);
                failedSize += failedTests.size();
                suiteInfo.getFailTests().addAll(failedTests);

                List<TestInfo> passedTests = new PassedTestProcessor().process(testContext);
                passedSize += passedTests.size();
                suiteInfo.getPassTests().addAll(passedTests);

                List<TestInfo> skippedTests = new SkippedTestProcessor().process(testContext);
                skippedSize += skippedTests.size();
                suiteInfo.getSkipTests().addAll(skippedTests);
            }
            suiteInfo.setFail(failedSize);
            suiteInfo.setPass(passedSize);
            suiteInfo.setSkip(skippedSize);

            suitInfoLists.add(suiteInfo);
        }
        try {
            FreeMarkUtils.processReport(suitInfoLists);
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
