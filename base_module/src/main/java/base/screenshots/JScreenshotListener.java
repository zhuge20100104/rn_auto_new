package base.screenshots;

import base.BaseWebTestCase;
import base.utils.driver.MobileDriverManager;
import base.utils.driver.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JScreenshotListener implements ITestListener {

    protected Logger logger = Logger.getLogger(JScreenshotListener.class);
    private final  String SEP = File.separator;




    private boolean isWebTestCase(ITestResult result) {
        Object  instance = result.getInstance();
        return (instance  instanceof BaseWebTestCase);
    }

    @Override
    public void onTestStart(ITestResult result) {
    }

    @Override
    public void onTestSuccess(ITestResult result) {

    }


    /**
     * Get screenshot location methods,
     * result location likes: screenshots/20180817065036/20180817065036_LoginTest.test1.png
     * @param result
     * @return
     */
    private String getScreenshotFileLocation(ITestResult result) {
        String folderStr = GlobalDateCache.getCurrentDate();
        File location = new File("screenshots"+SEP+folderStr);
        String dest = result.getMethod().getRealClass().getSimpleName()+"."+result.getMethod().getMethodName();

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        dest =  dateFormat.format(new Date())+"_"+dest;

        return  location.getAbsolutePath()+SEP+dest+".png";
    }

    @Override
    public void onTestFailure(ITestResult result) {


        WebDriver driver = null;

        try {
            if (isWebTestCase(result)) {
                driver = WebDriverManager.getDriver("http://www.baidu.com");
            }else {
                driver = MobileDriverManager.getDriver(null);
            }
        }catch (Exception ex){

        }


        try {
            logger.info("**************Start create screenshot**************************");
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String screenShotLocation = getScreenshotFileLocation(result);
            File targetFile = new File(screenShotLocation);

            FileUtils.copyFile(srcFile, targetFile);

            logger.info("**************create screenshot Successfully*******************");
        } catch (Exception e) {
            logger.info("**************create screenshot failure************************");
            logger.debug(e);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {

    }
}
