package base.sync.util;


import base.sync.ZKWatcher;
import base.search.engine.wait.utils.Result;
import config.utils.ConfigUtil;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZKPoolSync implements JSync {

    private static ZKPoolSync zPool = new ZKPoolSync();
    public static ZKPoolSync getPool() {
        return zPool;
    }
    private Map<String,ZooKeeper> packageZkMap = new HashMap<String,ZooKeeper>();
    private static String defaultConnectionString = "127.0.0.1:2181";
    private static int defaultSessionTimeout = 30000;

    private static String getConnectionString() {
        try {
            String globalConnectString = ConfigUtil.getZKConnectionString();
            defaultConnectionString = globalConnectString == null ? defaultConnectionString : globalConnectString;
            return defaultConnectionString;
        }catch (Exception ex) {
            System.out.println("Get customized connectString failure,use defaultConnectString:" +defaultConnectionString+"!!");
            return defaultConnectionString;
        }
    }


    private static int getSessionTimeout() {
        try {
            String globalSessionTimeout = ConfigUtil.getZKSessionTimeout();
            defaultSessionTimeout = globalSessionTimeout == null ? defaultSessionTimeout : Integer.parseInt(globalSessionTimeout);
            return defaultSessionTimeout;
        }catch (Exception ex) {
            System.out.println("Get customized session timeout failure,use defaultSessionTimeout:" +defaultSessionTimeout+"!!");
            return defaultSessionTimeout;
        }
    }

    public ZooKeeper getZookeeper(String key) throws Exception{
        if(packageZkMap.get(key) == null) {
            ZooKeeper zk = new ZooKeeper(getConnectionString(), getSessionTimeout(), new ZKWatcher());
            packageZkMap.put(key,zk);
        }
        return packageZkMap.get(key);
    }


    public boolean isNodeExists(String packageKey) {
        try {
            ZooKeeper zKeeper = getZookeeper(packageKey);
            byte[] data = zKeeper.getData("/" + packageKey, null, null);
            return true;
        }catch (Exception ex) {
            return false;
        }
    }

    public void sendSignal(String packageKey, String signal) throws Exception{

        ZooKeeper zKeeper = getZookeeper(packageKey);
        if(!isNodeExists(packageKey)) {
            zKeeper.create("/"+packageKey,signal.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }

        System.out.println("/"+packageKey+"/"+signal);
        zKeeper.create("/"+packageKey+"/"+signal,signal.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public void sendSignalWithValue(String packageKey, String signal, String value) throws Exception {
        ZooKeeper zKeeper = getZookeeper(packageKey);
        if(!isNodeExists(packageKey)) {
            zKeeper.create("/"+packageKey,signal.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }

        System.out.println("/"+packageKey+"/"+signal);
        zKeeper.create("/"+packageKey+"/"+signal, value.getBytes() , ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }



    public boolean getSignal(String packageKey,String signal)  {

        try {
            ZooKeeper zKeeper = getZookeeper(packageKey);
            byte[] data = zKeeper.getData("/" + packageKey + "/" + signal, true, null);
            return true;
        }catch (Exception ex) {
            return false;
        }
    }


    public Result<Boolean> getSignalData(String packageKey, String signal)  {

        Result<Boolean> signalValueResult = new Result<>();
        try {
            ZooKeeper zKeeper = getZookeeper(packageKey);
            byte[] data = zKeeper.getData("/" + packageKey + "/" + signal, true, null);
            signalValueResult.setCode(true);
            signalValueResult.setMessage(new String(data));
            return signalValueResult;
        }catch (Exception ex) {
            signalValueResult.setCode(false);
            signalValueResult.setMessage("Get signal data failed:["+packageKey+"/"+signal+"]!");
            return signalValueResult;
        }
    }



    public List<String> getAllChildSignals(String key) throws Exception {
        ZooKeeper zKeeper = getZookeeper(key);
        if(!isNodeExists(key)) {
            return null;
        }
        List<String> childrens = zKeeper.getChildren("/"+key,true);
        return childrens;
    }

    public void clearASignal(String key,String signal) throws Exception {
        ZooKeeper zKeeper = getZookeeper(key);
        if(!isNodeExists(key)) {
            return;
        }

        String childFullPath = "/"+key+"/"+signal;
        zKeeper.delete(childFullPath,-1);
    }


    public synchronized void clearAllSignals(String packageKey) throws Exception {
        ZooKeeper zKeeper = getZookeeper(packageKey);
        if(!isNodeExists(packageKey)) {
            return;
        }


        List<String> childrens = zKeeper.getChildren("/" + packageKey, true);
        for (String child : childrens) {
            String childFullPath = "/" + packageKey + "/" + child;
            zKeeper.delete(childFullPath, -1);
        }

        zKeeper.delete("/"+packageKey,-1);
    }
}
