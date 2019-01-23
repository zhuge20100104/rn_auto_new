package base.report.handlers;

import org.apache.commons.lang.exception.ExceptionUtils;
import base.exceptions.JBaseException;
import base.report.beans.TestInfo;

public abstract class AbsThrowableHandler {
    public void handle(ThrowableContext context){
        Throwable throwable = context.getThrowable();
        TestInfo testInfo = context.getTestInfo();

        if(throwable instanceof JBaseException) {
            JBaseException exception = (JBaseException)throwable;
            testInfo.setReason("JBaseException:[" + exception.getMessage()+"]");
            testInfo.setExceptionDetails(exception.getStackTraceStr());
            context.setHandled(true);
        }else if(throwable instanceof Exception) {
            Exception exception = (Exception)throwable;
            testInfo.setReason("System Exception:["+exception.getMessage()+"]");
            testInfo.setExceptionDetails(ExceptionUtils.getStackTrace(exception));
            context.setHandled(true);
        }
    }
}
