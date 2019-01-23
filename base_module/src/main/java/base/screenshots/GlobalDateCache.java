package base.screenshots;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GlobalDateCache {
    static String currentDate = null;

    private static final String getDateString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        return dateFormat.format(new Date());
    }


    public static String getCurrentDate() {
        if(currentDate == null) {
            currentDate = getDateString();
        }
        return currentDate;
    }
}
