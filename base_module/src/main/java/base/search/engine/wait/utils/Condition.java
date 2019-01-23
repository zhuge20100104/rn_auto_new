package base.search.engine.wait.utils;

/**
 * The condition you require to check
 * @param <T>  Type parameter to specify what type of element you want to return
 */
public interface Condition<T> {
    /**
     * The check method
     * @param r  The returned result will be passed in as a parameter
     * @return  The check result
     */
    public boolean check(Result<T> r);
}
