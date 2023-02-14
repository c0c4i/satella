package it.univr.satella.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SensorSelectPage extends PageObject {

    public SensorSelectPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean correctPage() {
        return driver.findElement(By.id("title"))
                .getText().equals("Seleziona un sensore");
    }

    public SlotsViewPage clickSelect(String sensorModel) {
        driver.findElement(By.id("select-sensor-" + sensorModel)).click();
        return new SlotsViewPage(driver);
    }
}
