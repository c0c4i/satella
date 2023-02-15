package it.univr.satella.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SensorListPage extends PageObject {

    @FindBy(id = "action-new-sensor")
    private WebElement insertSensorBtn;

    @FindBy(id = "action-all-slots")
    private WebElement slotListBtn;

    public SensorListPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isCurrentPage() {
        return driver.findElement(By.id("title"))
                .getText().equals("Tutti i sensori");
    }

    public InsertSensorPage clickInsertSensor() {
        insertSensorBtn.click();
        return new InsertSensorPage(driver);
    }

    public boolean hasSensorWithModel(String sensorModel) {
        return driver.findElements(By.id("sensor-name-" + sensorModel)).size() != 0;
    }

    public SlotListPage clickSlotList() {
        slotListBtn.click();
        return new SlotListPage(driver);
    }

    public ModifySensorPage clickModifySensor(String modelName) {
        driver.findElement(By.id("action-sensor-details-" + modelName)).click();
        return new ModifySensorPage(driver, modelName);
    }

}
