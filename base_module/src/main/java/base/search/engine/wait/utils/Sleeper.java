package base.search.engine.wait.utils;

public class Sleeper {

    public static void sleep(int secs) {
        try{
            Thread.sleep(secs * 1000);
        }catch (InterruptedException ex) {
            System.out.println("Sleep encounter interrupted exception!!");
        }
    }

    public static void sleep(float secs) {
        try{
            Thread.sleep((int)(secs * 1000));
        }catch (InterruptedException ex) {
            System.out.println("Sleep encounter interrupted exception!!");
        }
    }
}
