package base.sync;

import base.search.engine.wait.base.CoreWait;
import base.sync.util.JSync;
import base.sync.util.MapSync;
import base.sync.util.ZKPoolSync;
import base.search.engine.wait.utils.Condition;
import base.search.engine.wait.utils.Result;
import config.utils.ConfigUtil;

import java.util.List;


/**
 * Core class to send signal and wait for signal
 * <br>  It is used to do synchronization between two testing processes
 * <br>  Such as one web and one mobile, or two mobiles
 * <br>  Usage:
 * <br> SignalCore.sendSignal(packageKey,"shouldSendTheTestMessage");
 * <br> SignalCore.waitForSignal(packageKey,"shouldSendTheTestMessage");
 *
 */
public class SignalCore {

    /** Default sync wait interval*/
    private static int defaultInterval = 1000;

    /** Default sync wait timeout*/
    private static int defaultTimeout = 200;

    /** Sync interface to support local and remote sync,
     *<br> Local sync make use of java.util.HashMap
     *<br> Remote sync using Zookeeper*/
    private static JSync sync = null;


    /**
     * Get sync time out
     * <br>  If you have configured testautomation.zk.timeout in properties file, will use this value
     * <br>  Or else, will use default time out
     * @return  Sync time out
     */
    private static int getTimeout() {

        try {
            String globalTimeout = ConfigUtil.getZKTimeout();
            defaultTimeout = globalTimeout == null ? defaultTimeout : Integer.parseInt(globalTimeout);
            return defaultTimeout;
        }catch (Exception ex) {
            System.out.println("Get customized timeout failure,use defaultTimeout:" +defaultTimeout+"!!");
            return defaultTimeout;
        }
    }

    /**
     * Get Sycn pool
     * <br> Local or remote sync pool
     * @return  Current sync pool
     */
    private static JSync getSyncPool() {
        if(sync == null) {
            boolean isLocal = Boolean.parseBoolean(ConfigUtil.getZKIsLocal());
            if(isLocal) {
                sync = MapSync.getPool();
            }else {
                sync = ZKPoolSync.getPool();
            }
        }

        return sync;
    }


    /**
     * Send signal to another testing process
     * @param packageKey  The two testing process classes should be placed in the same package, Package key is the package of them
     * @param signal  Signal name
     * @throws Exception  Will throw an exception when zookeeper is not reachable
     */
    public static void sendSignal(String packageKey,String signal) throws Exception{
        getSyncPool().sendSignal(packageKey,signal);
    }

    /**
     * Send signal with value, the value can be passed to another testing process
     * @param packageKey The two testing process classes should be placed in the same package, Package key is the package of them
     * @param signal Signal name
     * @param value The value you want to pass, should be a string, if you want to pass an object, please convert it to string at first
     * @throws Exception Will throw an exception when zookeeper is not reachable
     */
    public static void sendSignalWithValue(String packageKey,String signal,String value) throws Exception{
        getSyncPool().sendSignalWithValue(packageKey,signal,value);
    }


    /**
     * Clear a signal when the cases are finished
     * @param key The two testing process classes should be placed in the same package, Package key is the package of them
     * @param signal Signal name
     * @throws Exception Will throw an exception when zookeeper is not reachable
     */
    public static void clearASignal(String key,String signal) throws Exception {
        getSyncPool().clearASignal(key,signal);
    }

    /**
     * Get all child signals for this package key
     * @param key  The two testing process classes should be placed in the same package, Package key is the package of them
     * @return All child signals
     * @throws Exception Will throw an exception when zookeeper is not reachable
     */
    public static List<String> getAllChildSignals(String key) throws Exception {
        return getSyncPool().getAllChildSignals(key);
    }


    /**
     * Clear all signals for the same package key
     * @param packageKey  The two testing process classes should be placed in the same package, Package key is the package of them
     * @throws Exception Will throw an exception when zookeeper is not reachable
     */
    public static void clearAllSignals(String packageKey) throws Exception {
        getSyncPool().clearAllSignals(packageKey);
    }


    /**
     * Wait for a signal to arrive in another process
     * @param packageKey  The two testing process classes should be placed in the same package, Package key is the package of them
     * @param signal  Signal name
     * @param interval  Waiting interval
     * @param timeout Waiting timeout
     * @return  Whether the signal is passed to another process finally
     */
    public static boolean waitForSignal(String packageKey, String signal,int interval,int timeout) {
        CoreWait wait = new CoreWait();
        Result<String> result =  wait.waitForConditon(new Condition<String>() {
            @Override
            public boolean check(Result<String> r) {
                if (getSyncPool().getSignal(packageKey, signal)) {
                    r.setCode(true);
                    return true;
                }

                return false;
            }
        }, interval, timeout);

        return result.Code();
    }


    /**
     * Wait for a signal to arrive in another process with value
     * @param packageKey The two testing process classes should be placed in the same package, Package key is the package of them
     * @param signal  Signal name
     * @param interval Waiting interval
     * @param timeout Waiting timeout
     * @return  Whether the signal is passed to another process finally
     *          <br> The string value is passed in the result's message field
     *          <br> See also  {@link base.search.engine.wait.utils.Result}
     */
    public static Result<Boolean> waitForSignalWithValue(String packageKey, String signal,int interval,int timeout) {
        CoreWait wait = new CoreWait();
        Result<Boolean> result =  wait.waitForConditon(new Condition<Boolean>() {
            @Override
            public boolean check(Result<Boolean> r) {
                Result<Boolean> resultInner = getSyncPool().getSignalData(packageKey,signal);
                if(resultInner.Code()) {
                    r.setCode(true);
                    r.setMessage(resultInner.getMessage());
                    return true;
                }else {
                    r.setCode(false);
                    return false;
                }
            }
        }, interval, timeout);

        return result;
    }


    /**
     *  Wait for a signal to arrive in another process with value
     * @param packageKey The two testing process classes should be placed in the same package, Package key is the package of them
     * @param signal Signal name
     * @return Whether the signal is passed to another process finally
     *        <br> The string value is passed in the result's message field
     *        <br> See also  {@link base.search.engine.wait.utils.Result}
     */
    public static Result<Boolean> waitForSignalWithValue(String packageKey, String signal) {
        return waitForSignalWithValue(packageKey ,signal,defaultInterval,getTimeout());
    }


    /**
     *  Wait for a signal to arrive in another process
     * @param packageKey The two testing process classes should be placed in the same package, Package key is the package of them
     * @param signal Signal name
     * @return Whether the signal is passed to another process finally
     */
    public static boolean waitForSignal(String packageKey ,String signal) {
       return waitForSignal(packageKey ,signal,defaultInterval,getTimeout());
    }

}
