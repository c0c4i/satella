package it.univr.satella.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ModifySensorPage extends SensorPage {

    @FindBy(id = "action-create")
    private WebElement modifyBtn;

    @FindBy(id = "action-cancel")
    private WebElement cancelBtn;

    @FindBy(id = "action-delete")
    private WebElement deleteBtn;

    private String sensorModel;

    public ModifySensorPage(WebDriver driver, String sensorModel) {
        super(driver);
        this.sensorModel = sensorModel;
    }

    @Override
    public boolean isCurrentPage() {
        return driver.findElement(By.id("title"))
                .getText().equals(sensorModel);
    }

    public SensorListPage clickModify() {
        modifyBtn.click();
        return new SensorListPage(driver);
    }

    public SensorListPage clickCancel() {
        cancelBtn.click();
        return new SensorListPage(driver);
    }

    public SensorListPage clickDelete() {
        deleteBtn.click();
        return new SensorListPage(driver);
    }
}
