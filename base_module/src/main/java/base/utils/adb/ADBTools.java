package base.utils.adb;

import org.testng.Assert;
import base.exceptions.JNoDeviceAvailableException;
import base.search.engine.wait.utils.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ADBTools {

    //Get the device info str part , like  List of devices attachedDBH9XA16C1905722	device
    // or List of devices attached* daemon not running. starting it now on port 5037 ** daemon started successfully *DBH9XA1710402074	device
    private static String getDeviceInfoStr() {
        Result<String> result = CommandExecutor.execCommand("adb devices");
        Assert.assertTrue(result.Code(), "Get devices info failure!!!");
        String resultDevicesStr = result.getMessage();
        return resultDevicesStr;
    }

    // Get the device str part, like   DBH9XA16C1905722 device
    private static String getRealDeviceStr(String resultDevicesStr)  throws Exception{
        if(resultDevicesStr.contains("*"))     {
            String [] deviceStrArr =  resultDevicesStr.split("\\*");
            int len = deviceStrArr.length;
            //No device is avaliable for testing
            if(deviceStrArr[len-1].trim().equals("daemon started successfully")) {
                throw new JNoDeviceAvailableException("No Device is avaliable for testing!!");
            }

            return deviceStrArr[len-1].trim();
        }else {
            if(resultDevicesStr.trim().equals("List of devices attached")) {
                throw new JNoDeviceAvailableException("No Device is avaliable for testing!!");
            }
            System.out.println(resultDevicesStr);
            resultDevicesStr = resultDevicesStr.replace("List of devices attached","");
            return resultDevicesStr.trim();
        }
    }


    //Generate a device name list
    private static List<String> generateDevicesList(String realDeviceStr) throws Exception {
        String [] devicesArr = realDeviceStr.split("device");
        List<String> devicesStr = new ArrayList<>();
        for(String s: devicesArr) {
            devicesStr.add(s.trim());
        }
        return devicesStr;
    }

    public static List<String> getAllDevices() throws Exception{
        String resultDevicesStr = getDeviceInfoStr();
        String realDeviceStr = getRealDeviceStr(resultDevicesStr);
        return generateDevicesList(realDeviceStr);
    }

    public static synchronized Device  getDeviceDiff(int basePort) throws Exception{
        List<String> usedDevices = UsedDevices.getUsedList();
        Map<String,Device> allDevicesMap = ADBTools.getAllDevicesMap(basePort);
        List<String> allDevices = getAllDevices();
        //  Has device in used
        if(usedDevices != null && usedDevices.size() > 0) {
            allDevices.removeAll(usedDevices);
        }

        if(allDevices!=null && allDevices.size()>0) {
            UsedDevices.putDevice(allDevices.get(0));
            return allDevicesMap.get(allDevices.get(0));
        }
        return null;

    }


    public static Map<String,Device> getAllDevicesMap(int basePort) throws Exception {

        List<String> devices = getAllDevices();
        Map<String,Device> nameDeviceMap = new HashMap<>();

        int i = 0;
        for(String dev: devices) {
            Device device = new Device();
            device.setDeviceName(dev);
            device.setPort(basePort + i*10);
            device.setBp(basePort + 1000 + i*5);
            nameDeviceMap.put(dev,device);
            i++;
        }

        return nameDeviceMap;
    }





    public static void main(String[] args) throws Exception {
        List<String> deviceList = getAllDevices();
        System.out.println(deviceList.size());
        System.out.println(deviceList.get(0));
    }
}
