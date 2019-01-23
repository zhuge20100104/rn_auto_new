package base.exceptions;

/**
 * element not found exception
 */
public class JElementNotFoundException extends JBaseException {

    private String type;
    private String value;

    public JElementNotFoundException(String message,String type,String value) {
        super(message);
        this.type = type;
        this.value = value;
    }

    @Override
    public String getMessage() {
        return  super.getMessage() + "\r\n element not found exception,current locator is:["+this.type+","+this.value+"]";
    }
}
