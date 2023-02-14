package it.univr.satella.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SensorSelectPage extends PageObject {

    @FindBy(id = "cancel-selection")
    private WebElement cancelBtn;

    public SensorSelectPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean correctPage() {
        return driver.findElement(By.id("title"))
                .getText().equals("Seleziona un sensore");
    }

    public SlotListPage clickSelect(String sensorModel) {
        driver.findElement(By.id("select-sensor-" + sensorModel)).click();
        return new SlotListPage(driver);
    }

    public boolean hasSensorWithModel(String sensorModel) {
        return driver.findElements(By.xpath("/html/body/div/div[2]/div/div/div[1]/h4"))
                .stream().map(WebElement::getText)
                .anyMatch(x -> x.equals(sensorModel));
    }

    public SlotListPage clickCancel() {
        cancelBtn.click();
        return new SlotListPage(driver);
    }
}
