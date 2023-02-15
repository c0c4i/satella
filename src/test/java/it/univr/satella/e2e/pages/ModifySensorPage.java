package it.univr.satella.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

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
        WebDriverWait wait = new WebDriverWait(driver, Duration.of(10, ChronoUnit.SECONDS));
        WebElement title = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("title")));
        return title.getText().equals(sensorModel);
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
