package it.univr.satella.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SlotListPage extends PageObject {

    @FindBy(xpath = "//*[@id=\"slot-number\"]")
    private List<WebElement> slotsList;

    public SlotListPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean correctPage() {
        return driver.findElement(By.id("title"))
                .getText().equals("Station Manager");
    }

    public int getSlotsCount() {
        return slotsList.size();
    }

    public SensorSelectPage clickAttachSensor(int slot) {
        driver.findElement(By.id("action-connect-sensor-" + slot)).click();
        return new SensorSelectPage(driver);
    }

    public boolean slotHasAttachedSensor(int slot, String sensorModel) {
        return driver.findElement(By.id("sensor-model-" + slot))
                .getText().equals(sensorModel);
    }

    public SlotListPage clickDetachSensor(int slot) {
        driver.findElement(By.id("action-remove-sensor-" + slot)).click();
        return new SlotListPage(driver);
    }
}
