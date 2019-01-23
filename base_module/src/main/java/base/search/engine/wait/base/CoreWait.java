package base.search.engine.wait.base;

import base.search.engine.wait.utils.Condition;
import base.search.engine.wait.utils.Result;

public class CoreWait {
    public <T> Result<T> waitForConditon(Condition<T> con, int interval, int tryCount) {
        Result<T> result = new Result<T>();
        int curCount = 1;

        try {
            while (!con.check(result) && curCount < tryCount) {
                //This line is just for debug
//                System.out.println("第"+curCount+"次检查!!");
                curCount++;
                Thread.sleep(interval);
            }
        }catch (InterruptedException ex) {
            System.out.println("系统中断异常");
        }

        return result;
    }
}
