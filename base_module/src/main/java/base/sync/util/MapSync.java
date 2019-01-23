package base.sync.util;

import base.search.engine.wait.utils.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapSync implements JSync{


    private static MapSync mapPool = new MapSync();
    public static MapSync getPool() {
        return mapPool;
    }

    Map<String,Map<SyncObj,String>> packageHashMaps = new HashMap<>();

    public Map<SyncObj,String> getSyncMap(String key) throws Exception{
        if(packageHashMaps.get(key) == null) {
            Map<SyncObj,String> syncMap = new HashMap<>();
            packageHashMaps.put(key,syncMap);
        }
        return packageHashMaps.get(key);
    }


    @Override
    public void sendSignal(String packageKey, String signal) throws Exception {
        Map<SyncObj,String> syncMap = getSyncMap(packageKey);
        SyncObj syncObj = new SyncObj();
        syncObj.setKey(packageKey);
        syncObj.setSignal(signal);
        syncMap.put(syncObj,signal);
    }

    @Override
    public void sendSignalWithValue(String packageKey, String signal, String value) throws Exception {
        Map<SyncObj,String> syncMap = getSyncMap(packageKey);
        SyncObj syncObj = new SyncObj();
        syncObj.setKey(packageKey);
        syncObj.setSignal(signal);
        syncMap.put(syncObj,value);
    }

    @Override
    public void clearASignal(String key, String signal) throws Exception {
        Map<SyncObj,String> syncMap = getSyncMap(key);
        SyncObj obj = new SyncObj();

        obj.setKey(key);
        obj.setSignal(signal);

        if(syncMap.containsKey(obj)) {
            syncMap.remove(obj);
        }
    }

    @Override
    public List<String> getAllChildSignals(String key) throws Exception {

        List<String> childSignals = new ArrayList<>();
        Map<SyncObj,String> syncMap = getSyncMap(key);
        for(Map.Entry<SyncObj,String> entry : syncMap.entrySet()) {
            childSignals.add(entry.getValue());
        }
        return childSignals;
    }

    @Override
    public void clearAllSignals(String packageKey) throws Exception {
        Map<SyncObj,String> syncMap = getSyncMap(packageKey);
        syncMap.clear();
    }

    @Override
    public boolean getSignal(String packageKey, String signal) {

        Map<SyncObj, String> syncMap = null;

        try {
            syncMap = getSyncMap(packageKey);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        SyncObj syncObj = new SyncObj();
        syncObj.setKey(packageKey);
        syncObj.setSignal(signal);
        return syncMap.containsKey(syncObj);
    }

    @Override
    public Result<Boolean> getSignalData(String packageKey, String signal) {
        Result<Boolean> signalValueResult = new Result<>();

        Map<SyncObj, String> syncMap = null;
        try {
            syncMap = getSyncMap(packageKey);
        }catch (Exception ex) {
            ex.printStackTrace();
        }

        SyncObj syncObj = new SyncObj();
        syncObj.setKey(packageKey);
        syncObj.setSignal(signal);

        if(syncMap.containsKey(syncObj)) {
            signalValueResult.setCode(true);
            signalValueResult.setMessage(syncMap.get(syncObj));
        }else {
            signalValueResult.setCode(false);
            signalValueResult.setMessage("Get signal data failed:["+packageKey+"/"+signal+"]!");
        }

        return signalValueResult;
    }

    public static void main(String[] args)  throws Exception{
        MapSync.getPool().sendSignal("aa","bbSignal");
        MapSync.getPool().sendSignal("aa","bbSignal");
        MapSync.getPool().sendSignal("aa","ccSignal");
        System.out.println(MapSync.getPool().getSyncMap("aa").size());
        MapSync.getPool().clearASignal("aa","bbSignal");
        System.out.println(MapSync.getPool().getSyncMap("aa").size());
        MapSync.getPool().sendSignalWithValue("aa","ddSignal","myValue");


    }
}
