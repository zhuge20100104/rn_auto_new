package base.search.wrapper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MobileWrapper extends BaseWrapper {
    public MobileWrapper(WebDriver driver, WebElement ele) {
        super(driver,ele);
    }

    // get content description of the current mobile element
    public String desc() {
        return attr("content-desc");
    }

    // return whether this element is checked
    public boolean checked() {
        return Boolean.parseBoolean(attr("checked"));
    }

    // return resource id of the current mobile element
    public String id() {
        return attr("resource-id");
    }

    // return package name of the current mobile element
    public String pack() {
        return attr("package");
    }

    // return class name of the current mobile element
    public String cls() {
        return attr("class");
    }



}
