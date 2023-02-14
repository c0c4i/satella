package it.univr.satella.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SensorListPage extends PageObject {

    @FindBy(id = "action-new-sensor")
    private WebElement insertSensorBtn;

    @FindBy(xpath = "/html/body/div/div[2]/div/div/div[1]/h4")
    private List<WebElement> sensorsNames;

    public SensorListPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean correctPage() {
        return driver.findElement(By.id("title"))
                .getText().equals("Tutti i sensori");
    }

    public InsertSensorPage clickInsertSensor() {
        insertSensorBtn.click();
        return new InsertSensorPage(driver);
    }

    public boolean hasSensorWithModel(String sensorModel) {
        return sensorsNames.stream().map(WebElement::getText)
                .anyMatch(x -> x.equals(sensorModel));
    }

}
