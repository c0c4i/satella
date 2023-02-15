package it.univr.satella.e2e;

import it.univr.satella.e2e.pages.ModifySensorPage;
import it.univr.satella.e2e.pages.SensorListPage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SensorRemoveSimpleTest {

    private static WebDriver driver;

    @BeforeClass
    public static void initializeDriver() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver_win32_110/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @AfterClass
    public static void shutdownDriver() {
        driver.close();
    }

    @Test
    public void testSensorRemoveCorrectNotAttached() {

        driver.get("http://localhost:8080/sensors/sensor-1");
        ModifySensorPage modifySensorPage = new ModifySensorPage(driver, "sensor-1");
        assertTrue(modifySensorPage.isCurrentPage());

        SensorListPage sensorListPage = modifySensorPage.clickDelete();
        assertTrue(sensorListPage.isCurrentPage());

        assertTrue(sensorListPage.hasNotificationWithId("alert-sensor-connect-success-2"));
        assertFalse(sensorListPage.hasSensorWithModel("sensor-1"));
    }
}
