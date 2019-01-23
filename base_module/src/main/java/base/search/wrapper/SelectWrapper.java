package base.search.wrapper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SelectWrapper extends BaseWrapper {
    public SelectWrapper(WebDriver driver, WebElement ele) {
        super(driver,ele);
    }

    public void selectByText(String text){
        Select select = new Select((element));
        select.selectByVisibleText(text);
    }

    public void selectByIndex(int index) {
        Select select = new Select((element));
        select.selectByIndex(index);
    }
}
