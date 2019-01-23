package base.report.testprocessers;

import base.annotations.TestCaseInfo;
import org.testng.IResultMap;
import org.testng.ITestContext;
import org.testng.ITestResult;
import base.report.beans.TestInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SkippedTestProcessor  implements IProcessor{
    @Override
    public List<TestInfo> process(ITestContext testContext) {
        IResultMap resultMap = testContext.getSkippedTests();
        Set<ITestResult> results = resultMap.getAllResults();
        List<TestInfo> testInfos = new ArrayList<>();
        for(ITestResult result : results){
            TestInfo testInfo = new TestInfo();
            testInfo.setClassName(result.getTestClass().getName());
            testInfo.setMethodName(result.getMethod().getMethodName());

            TestInfoGet tGet = new TestInfoGet();
            TestCaseInfo caseInfo = tGet.getCaseInfo(result);
            if(caseInfo != null) {
                testInfo.setTestOwner(caseInfo.owner());
                testInfo.setCaseNo(caseInfo.caseNo());
            }else {
                testInfo.setTestOwner("UNKNOWN");
                testInfo.setCaseNo("UNKNOWN");
            }


            long duration = (result.getEndMillis()-result.getStartMillis())/1000;
            testInfo.setDuration(duration);
            testInfo.setReason("SKIPPED");
            testInfo.setExceptionDetails("NONE");
            testInfos.add(testInfo);
        }
        return testInfos;
    }
}
