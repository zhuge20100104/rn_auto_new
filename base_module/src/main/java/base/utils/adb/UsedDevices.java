package base.utils.adb;

import java.util.ArrayList;
import java.util.List;

public class UsedDevices {
    private static List<String> usedList = new ArrayList<>();

    public static synchronized void putDevice(String deviceName) {
        usedList.add(deviceName);
    }

    public static void removeADevice(String deviceName) {
        usedList.remove(deviceName);
    }

    public  static void removeAllDevices() {
        usedList.clear();
    }

    public static List<String> getUsedList() {
        return usedList;
    }
}
