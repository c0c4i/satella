package it.univr.satella.e2e.pages;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class InsertSensorPage extends SensorPage {

    @FindBy(id = "action-create")
    private WebElement insertBtn;

    @FindBy(id = "action-cancel")
    private WebElement cancelBtn;

    public InsertSensorPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isCurrentPage() {
        return driver.findElement(By.id("title"))
                .getText().equals("Nuovo sensore");
    }

    public SensorListPage clickInsert() {
        insertBtn.click();
        return new SensorListPage(driver);
    }

    public SensorListPage clickCancel() {
        cancelBtn.click();
        return new SensorListPage(driver);
    }
}
