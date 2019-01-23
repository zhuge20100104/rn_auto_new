package base.search.wrapper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

//Wrapper class for web list  view
public class ListWrapper extends BaseWrapper {
    public ListWrapper(WebDriver driver, WebElement ele) {
        super(driver,ele);
    }


    public void selectListByIndex(int index) {
        List<WebElement> elements = element.findElements(By.tagName("li"));
        Assert.assertTrue(elements.size() > index, "Index is greater than list element size,please check again!");
        elements.get(index).click();
    }


    public List<WebElement> getListElements() {
        return  element.findElements(By.tagName("li"));
    }

    public WebElement getListElementByIndex(int index) {
        List<WebElement> elements = element.findElements(By.tagName("li"));
        Assert.assertTrue(elements.size() > index, "Index is greater than list element size,please check again!");
        return elements.get(index);
    }

    public void selectListByText(String text) {
        List<WebElement> elements = element.findElements(By.tagName("li"));
        for(WebElement ele: elements) {
            if(ele.getText().equals(text)){
                ele.click();
                break;
            }
        }
    }

    public void selectListContainsText(String text) {
        List<WebElement> elements = element.findElements(By.tagName("li"));
        for(WebElement ele: elements) {
            if(ele.getText().contains(text)){
                ele.click();
                break;
            }
        }
    }

    public int getListSize() {
        return getListElements().size();
    }
}
