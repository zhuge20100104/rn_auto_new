package base.utils.ocr;

import base.utils.JsonUtil;
import base.utils.driver.MobileDriverManager;
import base.utils.driver.WebDriverManager;
import base.utils.ocr.beans.OciResults;
import base.utils.ocr.beans.OneWord;
import com.baidu.aip.ocr.AipOcr;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.util.HashMap;
import java.util.List;


public class OCRUtil {
    public static final String APP_ID = "14463812";
    public static final String API_KEY = "vGImawpye6Dzt7VWSM9az4Cn";
    public static final String SECRET_KEY = "5r6SRix934lkuSnusoTvHUNhwL2fExE5";
    private static AipOcr client;

    private OCRUtil() {
        client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
    }


    private static OCRUtil instance = new OCRUtil();

    public static OCRUtil getInstance(){

        synchronized (instance) {
            if (instance == null) {
                instance = new OCRUtil();
            }
        }

        return instance;
    }


    public String getTextInfo(boolean isMobile) {
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        client.setHttpProxy("www-proxy.us.oracle.com", 80);  // 设置http代理

        WebDriver driver;
        String path = "test.png";
        try {


            driver = isMobile ? MobileDriverManager.getDriver(null): WebDriverManager.getDriver("http://www.baidu.com");
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File targetFile = new File(path);
            FileUtils.copyFile(srcFile, targetFile);

            JSONObject res = client.general(path, new HashMap<String, String>());

            targetFile.delete();
            return res.toString();
        }catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<OneWord> getTextAndLoc(boolean isMobile) {
        String textInfoStr = getTextInfo(isMobile);
        OciResults ociResults = (OciResults) JsonUtil.strToObject(textInfoStr,OciResults.class);
        List<OneWord> oneWords = ociResults.getWords_result();

        //TODO: for debug
        for(OneWord oneWord: oneWords) {
            System.out.println(oneWord.getWords());
        }

        return oneWords;
    }


    public OneWord getMatchedWord(String wordRegx,boolean isMobile) {
        List<OneWord> oneWords = getTextAndLoc(isMobile);
        for(OneWord word: oneWords) {
            if(word.getWords().matches(wordRegx)) {
                return word;
            }
        }
        return null;
    }


    public OneWord getWordStartWith(String startStr,boolean isMobile) {
        List<OneWord> oneWords = getTextAndLoc(isMobile);
        for(OneWord word: oneWords) {
            if(word.getWords().startsWith(startStr)) {
                return word;
            }
        }
        return null;
    }


    public OneWord getWordEndsWith(String endStr,boolean isMobile) {
        List<OneWord> oneWords = getTextAndLoc(isMobile);
        for(OneWord word: oneWords) {
            if(word.getWords().endsWith(endStr)) {
                return word;
            }
        }
        return null;
    }


    public OneWord getExtractWord(String wordStr,boolean isMobile) {
        return getMatchedWord("^"+wordStr+"$",isMobile);
    }


    public OneWord getWordContains(String containsStr,boolean isMobile) {
        List<OneWord> oneWords = getTextAndLoc(isMobile);
        for(OneWord word: oneWords) {
            if(word.getWords().contains(containsStr)) {
                return word;
            }
        }
        return null;
    }




    public static void main(String[] args) {

        OneWord word = OCRUtil.getInstance().getWordEndsWith("请耐心等待,我们将尽快",true);
        System.out.println("--------------------------------------------->");
        System.out.println(word.getWords());
        System.out.println(word.getLocation().getCentralX());
        System.out.println(word.getLocation().getCentralY());
    }

}
