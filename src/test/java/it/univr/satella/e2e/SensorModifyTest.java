package it.univr.satella.e2e;

import it.univr.satella.e2e.pages.ModifySensorPage;
import it.univr.satella.e2e.pages.PageObject;
import it.univr.satella.e2e.pages.SensorListPage;
import it.univr.satella.model.Sensor;
import it.univr.satella.service.SensorService;
import it.univr.satella.service.SlotService;
import org.apache.commons.lang3.SystemUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SensorModifyTest {

    private static WebDriver driver;

    @Autowired private SensorService sensorService;
    @Autowired private SlotService slotService;

    @BeforeClass
    public static void initializeDriver() {

        String filepath = "none";
        if (SystemUtils.IS_OS_WINDOWS)
            filepath = "chromedriver_win32_110";
        else if (SystemUtils.IS_OS_MAC)
            filepath = "chromedriver_mac64_110";
        else if (SystemUtils.IS_OS_LINUX)
            filepath = "chromedriver_linux64_110";

        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/" + filepath + "/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @AfterClass
    public static void shutdownDriver() {
        driver.close();
    }

    @Before
    public void setUp() {
        sensorService.loadSensorsAtDefaultPath();
        slotService.loadSlotsAtDefaultPath();
    }

    @Test
    public void testSensorModifyCorrect() {

        driver.get("http://localhost:8080/sensors");
        SensorListPage sensorListPage = new SensorListPage(driver);
        assertTrue(sensorListPage.isCurrentPage());

        ModifySensorPage modifySensorPage = sensorListPage.clickModifySensor("sensor-1");
        assertTrue(modifySensorPage.isCurrentPage());

        modifySensorPage.setMaxAmperage(100.0f);
        sensorListPage = modifySensorPage.clickModify();
        assertTrue(sensorListPage.isCurrentPage());

        modifySensorPage = sensorListPage.clickModifySensor("sensor-1");
        assertTrue(modifySensorPage.isCurrentPage());

        String maxAmperageValue = modifySensorPage.getMaxAmperage();
        assertEquals(100.0f, Float.parseFloat(maxAmperageValue), 0.0f);
    }

    @Test
    public void testSensorModifyInvalid() {

        driver.get("http://localhost:8080/sensors");
        SensorListPage sensorListPage = new SensorListPage(driver);
        assertTrue(sensorListPage.isCurrentPage());

        ModifySensorPage modifySensorPage = sensorListPage.clickModifySensor("sensor-1");
        assertTrue(modifySensorPage.isCurrentPage());

        modifySensorPage.setMinAmperage(100.0f);
        modifySensorPage.clickModify(); // We should remain in the same page
        assertTrue(modifySensorPage.isCurrentPage());

        assertTrue(modifySensorPage.hasNotificationWithId("form-validation-error-3"));
    }

    @Test
    public void testSensorNotFoundModify() {
        driver.get("http://localhost:8080/sensors/not-existent");
        PageObject page = new PageObject(driver);
        assertTrue(page.hasNotificationWithId("form-validation-error"));
    }

    @Test
    public void testSensorModifyNotCompatible() {

        Sensor sensor = sensorService.findSensorByModelName("sensor-1");
        slotService.attachSensorToSlot(0, sensor);

        driver.get("http://localhost:8080/sensors/sensor-1");
        ModifySensorPage modifySensorPage = new ModifySensorPage(driver, "sensor-1");
        assertTrue(modifySensorPage.isCurrentPage());

        modifySensorPage.setMinVoltage(6.0f);
        modifySensorPage.clickModify(); // We should remain on the same page
        assertTrue(modifySensorPage.isCurrentPage());

        assertTrue(modifySensorPage.hasNotificationWithId("form-validation-error-7"));
    }

    @Test
    public void testSensorModifyCancel() {

        driver.get("http://localhost:8080/sensors");
        SensorListPage sensorListPage = new SensorListPage(driver);
        assertTrue(sensorListPage.isCurrentPage());

        ModifySensorPage modifySensorPage = sensorListPage.clickModifySensor("sensor-1");
        assertTrue(modifySensorPage.isCurrentPage());

        sensorListPage = modifySensorPage.clickCancel();
        assertTrue(sensorListPage.isCurrentPage());
    }
}
