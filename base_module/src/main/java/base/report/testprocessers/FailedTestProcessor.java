package base.report.testprocessers;

import base.annotations.TestCaseInfo;
import org.testng.IResultMap;
import org.testng.ITestContext;
import org.testng.ITestResult;
import base.report.beans.TestInfo;
import base.report.handlers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FailedTestProcessor implements IProcessor {

    private List<AbsThrowableHandler> getAllThrowableHandlers() {
        List<AbsThrowableHandler> throwableHandlers = new ArrayList<>();
        throwableHandlers.add(new JElementNotFoundExceptionHandler());
        throwableHandlers.add(new JNoDeviceAvailableExceptionHandler());
        throwableHandlers.add(new JNotSupportedLocatorExceptionHandler());
        throwableHandlers.add(new JNotSupportedSearchEngineExceptionHandler());
        throwableHandlers.add(new AssertionErrorHandler());
        throwableHandlers.add(new CommonExceptionHandler());
        return throwableHandlers;
    }


    private List<TestInfo> listFailedTestInfo(IResultMap resultMap){
        Set<ITestResult> results = resultMap.getAllResults();
        List<AbsThrowableHandler> throwableHandlers = getAllThrowableHandlers();

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

            if(result.getThrowable()!=null) {
                ThrowableContext context = new ThrowableContext();
                context.setTestInfo(testInfo);
                Throwable throwable= result.getThrowable();
                context.setThrowable(throwable);

                for(AbsThrowableHandler handler:throwableHandlers) {
                    handler.handle(context);
                    if(context.isHandled()) {
                        break;
                    }
                }
            }
            testInfos.add(testInfo);
        }

        return testInfos;
    }

    @Override
    public List<TestInfo> process(ITestContext testContext) {
        IResultMap failedTests = testContext.getFailedTests();
        IResultMap failedConfig = testContext.getFailedConfigurations();

        List<TestInfo> failedTestInfo = listFailedTestInfo(failedTests);
        List<TestInfo> failedConfigInfo = listFailedTestInfo(failedConfig);
        failedTestInfo.addAll(failedConfigInfo);
        return failedTestInfo;
    }
}
