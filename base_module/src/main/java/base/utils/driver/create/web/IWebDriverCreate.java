package base.utils.driver.create.web;

import org.openqa.selenium.WebDriver;

public interface IWebDriverCreate {
    WebDriver create(String url) throws Exception;
}
