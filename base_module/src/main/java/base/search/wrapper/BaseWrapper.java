package base.search.wrapper;

import org.openqa.selenium.*;

public abstract class BaseWrapper {
    protected WebDriver driver;
    protected WebElement element;

    public BaseWrapper(WebDriver driver, WebElement ele) {
        this.driver = driver;
        this.element = ele;
    }

    public String attr(String attr){
            return element.getAttribute(attr);
    }

    public String text() {
        return element.getText();
    }

    public Point loc() {
        return element.getLocation();
    }

    public Dimension size() {
        return element.getSize();
    }

    public Rectangle rect() {
        return element.getRect();
    }

}
