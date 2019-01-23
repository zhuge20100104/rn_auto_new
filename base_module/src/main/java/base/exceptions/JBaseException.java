package base.exceptions;

import org.apache.commons.lang.exception.ExceptionUtils;

public class JBaseException extends Exception{
    public JBaseException(String message) {
        super(message);
    }

    public String getStackTraceStr() {
        return ExceptionUtils.getStackTrace(this);
    }
}
