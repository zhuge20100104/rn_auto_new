package base.report.handlers;

import base.exceptions.JNotSupportedSearchEngineException;
import base.report.beans.TestInfo;

public class JNotSupportedSearchEngineExceptionHandler extends AbsThrowableHandler {
    public void handle(ThrowableContext context) {
        Throwable throwable = context.getThrowable();
        TestInfo testInfo = context.getTestInfo();
        if(throwable instanceof JNotSupportedSearchEngineException) {
            JNotSupportedSearchEngineException notSupportedSearchEngineExcept = (JNotSupportedSearchEngineException)throwable;
            testInfo.setReason("JNotSupportedSearchEngineException:["+notSupportedSearchEngineExcept.getMessage()+"]");
            testInfo.setExceptionDetails(notSupportedSearchEngineExcept.getStackTraceStr());
            context.setHandled(true);
        }
    }
}
