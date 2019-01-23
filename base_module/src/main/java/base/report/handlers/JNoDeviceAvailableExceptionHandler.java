package base.report.handlers;

import base.exceptions.JNoDeviceAvailableException;
import base.report.beans.TestInfo;

public class JNoDeviceAvailableExceptionHandler extends AbsThrowableHandler {
    public void handle(ThrowableContext context) {
        Throwable throwable = context.getThrowable();
        TestInfo testInfo = context.getTestInfo();
        if(throwable instanceof JNoDeviceAvailableException) {
            JNoDeviceAvailableException notAvailableExcept = (JNoDeviceAvailableException)throwable;
            testInfo.setReason("JNoDeviceAvailableException:["+notAvailableExcept.getMessage()+"]");
            testInfo.setExceptionDetails(notAvailableExcept.getStackTraceStr());
            context.setHandled(true);
        }
    }
}
