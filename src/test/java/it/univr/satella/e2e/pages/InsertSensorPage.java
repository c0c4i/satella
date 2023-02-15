package it.univr.satella.e2e.pages;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class InsertSensorPage extends PageObject {

    @FindBy(id = "model-name")
    private WebElement modelNameField;

    @FindBy(id = "ampere-min")
    private WebElement minAmpereField;

    @FindBy(id = "ampere-max")
    private WebElement maxAmpereField;

    @FindBy(id = "voltage-min")
    private WebElement minVoltageField;

    @FindBy(id = "voltage-max")
    private WebElement maxVoltageField;

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

    public void setSensorData(String modelName, float minAmpere, float maxAmpere, float minVoltage, float maxVoltage) {
        modelNameField.sendKeys(modelName);
        minAmpereField.sendKeys(String.valueOf(minAmpere));
        maxAmpereField.sendKeys(String.valueOf(maxAmpere));
        minVoltageField.sendKeys(String.valueOf(minVoltage));
        maxVoltageField.sendKeys(String.valueOf(maxVoltage));
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
