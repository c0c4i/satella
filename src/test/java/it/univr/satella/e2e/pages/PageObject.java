package it.univr.satella.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.assertTrue;

public abstract class PageObject {

    protected WebDriver driver;

    public PageObject(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;

        assertTrue(correctPage());
    }

    public boolean correctPage() {
        return false;
    }

    public boolean hasNotificationWithId(String id) {
        String xpath = "//*[@id=\"" + id + "\"][@role=\"alert\"]";
        return driver.findElement(By.xpath(xpath)) != null;
    }
}
