package base.search.wrapper;

import base.search.engine.wait.base.CoreWait;
import base.search.engine.wait.utils.Condition;
import base.search.engine.wait.utils.Result;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AlertWrapper extends BaseWrapper {

    public AlertWrapper(WebDriver driver, WebElement ele){
        super(driver,ele);
    }

    public boolean isAlertPresent() {
        Alert alert = ExpectedConditions.alertIsPresent().apply(driver);
        return alert != null;
    }

    public Alert getExistAlert() {
        return  ExpectedConditions.alertIsPresent().apply(driver);
    }


    public void handleAlert(int tryCount) {
        CoreWait cWait = new CoreWait();
        cWait.waitForConditon(new Condition<Boolean>() {
            public boolean check(Result<Boolean> r){
                Alert alert = getExistAlert();
                if(alert!=null) {
                    alert.accept();
                    r.setCode(true);
                    r.setMessage("Alert is Handled!");
                    return true;
                }

                r.setCode(false);
                r.setMessage("There is no alert present!");
                return  false;
            }
        },1000,tryCount);
    }
}
