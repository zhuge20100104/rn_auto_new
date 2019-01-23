package base.sync.util;

import base.search.engine.wait.utils.Result;

import java.util.List;

public interface JSync {
    void sendSignal(String packageKey, String signal) throws Exception;
    void sendSignalWithValue(String packageKey, String signal, String value) throws Exception;
    void clearASignal(String key,String signal) throws Exception;
    List<String> getAllChildSignals(String key) throws Exception;
    void clearAllSignals(String packageKey) throws Exception;
    boolean getSignal(String packageKey,String signal) ;
    Result<Boolean> getSignalData(String packageKey, String signal) ;
}
