package osvc.pageobjects.osm_web;

import base.search.SearchTools;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OSMSearchTools extends SearchTools {

    WebDriver driver;

    public OSMSearchTools(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public WebElement getElementInWebElementParent(WebElement parentElement, By childby) {
        return parentElement.findElement(childby);
    }
}
