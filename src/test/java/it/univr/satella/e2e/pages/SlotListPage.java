package it.univr.satella.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SlotListPage extends PageObject {

    @FindBy(xpath = "//*[@id=\"slot-number\"]")
    private List<WebElement> slotsList;

    @FindBy(xpath = "/html/body/div/div[1]/div[2]/a")
    private WebElement sensorListBtn;

    public SlotListPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isCurrentPage() {
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
        List<WebElement> elements = driver.findElements(By.id("sensor-model-" + slot));
        if (!elements.isEmpty()) {
            return elements.get(0).getText().equals(sensorModel);
        }
        return false;
    }

    public SlotListPage clickDetachSensor(int slot) {
        driver.findElement(By.id("action-remove-sensor-" + slot)).click();
        return new SlotListPage(driver);
    }

    public SensorListPage clickSensorList() {
        sensorListBtn.click();
        return new SensorListPage(driver);
    }
}
