package base.report.handlers;

import base.exceptions.JNotSupportedLocatorException;
import base.report.beans.TestInfo;

public class JNotSupportedLocatorExceptionHandler extends AbsThrowableHandler {
    public void handle(ThrowableContext context) {
        Throwable throwable = context.getThrowable();
        TestInfo testInfo = context.getTestInfo();
        if(throwable instanceof JNotSupportedLocatorException) {
            JNotSupportedLocatorException notSupportedLocatorExcept = (JNotSupportedLocatorException)throwable;
            testInfo.setReason("JNotSupportedLocatorException:["+notSupportedLocatorExcept.getMessage()+"]");
            testInfo.setExceptionDetails(notSupportedLocatorExcept.getStackTraceStr());
            context.setHandled(true);
        }
    }
}
