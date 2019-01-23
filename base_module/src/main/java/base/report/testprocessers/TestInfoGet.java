package base.report.testprocessers;

import base.annotations.TestCaseInfo;
import org.testng.ITestResult;

import java.lang.reflect.Method;

public class TestInfoGet {
    public TestCaseInfo getCaseInfo(ITestResult result) {
        try {
            Class cls = Class.forName(result.getTestClass().getName());
            Method method = cls.getDeclaredMethod(result.getMethod().getMethodName());
            TestCaseInfo testCaseInfo= method.getAnnotation(TestCaseInfo.class);
            return testCaseInfo;
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
