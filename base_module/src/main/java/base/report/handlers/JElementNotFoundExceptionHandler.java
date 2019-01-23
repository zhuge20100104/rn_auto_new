package base.report.handlers;

import base.exceptions.JElementNotFoundException;
import base.report.beans.TestInfo;

public class JElementNotFoundExceptionHandler extends AbsThrowableHandler {
    public void handle(ThrowableContext context) {
        Throwable throwable = context.getThrowable();
        TestInfo testInfo = context.getTestInfo();
        if(throwable instanceof JElementNotFoundException) {
            JElementNotFoundException notFoundExcept = (JElementNotFoundException)throwable;
            testInfo.setReason("JElementNotFoundException:["+notFoundExcept.getMessage()+"]");
            testInfo.setExceptionDetails(notFoundExcept.getStackTraceStr());
            context.setHandled(true);
        }
    }
}
