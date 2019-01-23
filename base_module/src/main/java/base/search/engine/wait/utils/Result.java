package base.search.engine.wait.utils;

/**
 * Used in wait for condition to hold return result
 * @param <T>   Type parameter to specify what type of element you want to return
 */
public class Result<T> {
    /** If the condition is met, the code should be set to true*/
    public boolean Code=false;

    /** Returned message*/
    public String message = "";

    /** Returned object*/
    public T obj = null;


    /**
     * Returned code
     * @return If the condition is met, the code should be set to true
     */
    public boolean Code() {
        return Code;
    }

    /**
     * Method to set code
     * @param code The code to return
     */
    public void setCode(boolean code) {
        Code = code;
    }


    /**
     * The returned message
     * @return  The returned message
     */
    public String getMessage() {
        return message;
    }


    /**
     * Set the returned message
     * @param message  message params
     */
    public void setMessage(String message) {
        this.message = message;
    }


    /**
     * Get the returned object
     * @return The returned object
     */
    public T getObj() {
        return obj;
    }

    /**
     * Set the returned object
     * @param obj  Object params
     */
    public void setObj(T obj) {
        this.obj = obj;
    }
}
