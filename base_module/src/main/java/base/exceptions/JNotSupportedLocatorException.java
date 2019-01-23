package base.exceptions;

/**
 *  Not supported locator
 */
public class JNotSupportedLocatorException extends JBaseException {
    private String type;
    private String value;

    public JNotSupportedLocatorException(String message, String type,String value) {
        super(message);
        this.type = type;
        this.value = value;
    }

    @Override
    public String getMessage() {
        return  super.getMessage() + "\r\n not supported locator exception,current locator is:["+this.type +", "+
                this.value+"]";
    }

}
