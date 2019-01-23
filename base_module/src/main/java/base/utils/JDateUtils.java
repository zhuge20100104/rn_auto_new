package base.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class JDateUtils {
    public static String getCurrentTimeStr() {
        return new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(Calendar.getInstance().getTime());
    }

    public static String getCurrentDateStr() {
        return new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime());
    }

    public static String getCurrentSubTimeStr() {
        return new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
    }
}
