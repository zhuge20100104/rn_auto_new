package base.search.wrapper;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WebWrapper extends BaseWrapper {
    public WebWrapper(WebDriver driver, WebElement ele) {
        super(driver,ele);
    }

    public String tagName() {
        return element.getTagName();
    }

    public void scrollToView() {
        if(this.element!=null && !this.element.isEnabled()) {
            ((JavascriptExecutor) this.driver).executeScript("arguments[0].scrollIntoView(true);", new Object[]{this.element});
        }else  if(this.element!=null && this.element.isEnabled()) {
            ((JavascriptExecutor) this.driver).executeScript("arguments[0].scrollIntoView(false);", new Object[]{this.element});
        }
    }
}
