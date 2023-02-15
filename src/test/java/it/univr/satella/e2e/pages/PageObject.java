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
    }

    public boolean isCurrentPage() {
        return false;
    }

    public boolean hasNotificationWithId(String id) {
        return driver.findElement(By.id(id)) != null;
    }
}
