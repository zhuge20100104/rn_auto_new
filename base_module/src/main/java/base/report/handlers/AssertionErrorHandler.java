package base.report.handlers;

import org.apache.commons.lang.exception.ExceptionUtils;
import base.report.beans.TestInfo;

public class AssertionErrorHandler extends AbsThrowableHandler {

    @Override
    public void handle(ThrowableContext context) {
        Throwable throwable = context.getThrowable();
        TestInfo testInfo = context.getTestInfo();

        if(throwable instanceof AssertionError) {
            AssertionError ae = (AssertionError)throwable;
            testInfo.setReason("Assertion Error:[" + ae.getMessage()+"]");
            testInfo.setExceptionDetails(ExceptionUtils.getStackTrace(ae));
            context.setHandled(true);
        }
    }
}
