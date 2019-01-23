package base.utils.adb;

import base.search.engine.wait.utils.Result;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommandExecutor {
    public static Result<String> execCommand(String cmd) {
        Result<String> result = new Result<String>();


        try {

            if(getOsVer().equals(OSVer.WIN)) {
                cmd = "cmd /c " + cmd;
            }

            Process process = Runtime.getRuntime().exec(cmd);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            result.setCode(true);
            String msg = "";
            String tmp = "";

            while ( (tmp = bufferedReader.readLine()) != null){
                msg+=tmp;
            }
            result.setMessage(msg);
        }catch (Exception ex){
            result.setCode(false);
            result.setMessage("Error:"+ex.getMessage());
        }
        return result;
    }


    public static String getGrepCmd() {
        if(getOsVer().equals(OSVer.WIN)) {
            return "findstr";
        }else {
            return "grep";
        }
    }

    public static OSVer getOsVer() {
        String osVer = System.getProperty("os.name").toLowerCase().substring(0,3).trim();
        //for debug only
//        System.out.println(osVer);


        switch (osVer) {
            case "mac":
                return OSVer.MAC;
            case "win":
                return OSVer.WIN;
            case "lin":
                return OSVer.LINUX;
        }
        return null;
    }
    public static void main(String[] args) {
        System.out.println(execCommand("adb devices").getMessage());
    }
}
