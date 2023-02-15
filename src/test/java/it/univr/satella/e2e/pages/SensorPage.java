package it.univr.satella.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class SensorPage extends PageObject {

    @FindBy(id = "modelName")
    private WebElement modelNameField;

    @FindBy(id = "minAmperage")
    private WebElement minAmpereField;

    @FindBy(id = "maxAmperage")
    private WebElement maxAmpereField;

    @FindBy(id = "minVoltage")
    private WebElement minVoltageField;

    @FindBy(id = "maxVoltage")
    private WebElement maxVoltageField;

    public SensorPage(WebDriver driver) {
        super(driver);
    }

    public void setModelName(String modelName) {
        modelNameField.clear();
        modelNameField.sendKeys(modelName);
    }

    public String getModelName() {
        return modelNameField.getText();
    }

    public void setMinAmperage(float value) {
        minAmpereField.clear();
        minAmpereField.sendKeys(String.valueOf(value));
    }

    public String getMinAmperage() {
        return minAmpereField.getAttribute("value");
    }

    public void setMaxAmperage(float value) {
        maxAmpereField.clear();
        maxAmpereField.sendKeys(String.valueOf(value));
    }

    public String getMaxAmperage() {
        return maxAmpereField.getAttribute("value");
    }

    public void setMinVoltage(float value) {
        minVoltageField.clear();
        minVoltageField.sendKeys(String.valueOf(value));
    }

    public String getMinVoltage() {
        return minVoltageField.getAttribute("value");
    }

    public void setMaxVoltage(float value) {
        maxVoltageField.clear();
        maxVoltageField.sendKeys(String.valueOf(value));
    }

    public String getMaxVoltage() {
        return maxVoltageField.getAttribute("value");
    }

    public void setSensorData(String modelName, float minAmpere, float maxAmpere, float minVoltage, float maxVoltage) {
        setModelName(modelName);
        setMinAmperage(minAmpere);
        setMaxAmperage(maxAmpere);
        setMinVoltage(minVoltage);
        setMaxVoltage(maxVoltage);
    }
}
