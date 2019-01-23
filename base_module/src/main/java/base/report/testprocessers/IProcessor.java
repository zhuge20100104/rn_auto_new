package base.report.testprocessers;

import org.testng.ITestContext;
import base.report.beans.TestInfo;

import java.util.List;

public interface IProcessor {
    List<TestInfo> process(ITestContext testContext);
}
