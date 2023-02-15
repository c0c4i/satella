package it.univr.satella.e2e;

import it.univr.satella.e2e.pages.ModifySensorPage;
import it.univr.satella.e2e.pages.SensorListPage;
import it.univr.satella.e2e.pages.SlotListPage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SensorRemoveTest {

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
    public void testSensorRemoveCorrectAttached() {

        driver.get("http://localhost:8080/slots/0/connect/sensor-1");
        driver.get("http://localhost:8080/sensors/sensor-1/delete");

        driver.get("http://localhost:8080/slots");
        SlotListPage slotListPage = new SlotListPage(driver);
        assertFalse(slotListPage.slotHasAttachedSensor(0, "sensor-1"));
    }

    @Test
    public void testSensorNotFoundRemove() {
        driver.get("http://localhost:8080/sensors/not-existent/delete");
        SensorListPage sensorListPage = new SensorListPage(driver);
        assertTrue(sensorListPage.isCurrentPage());
        assertTrue(sensorListPage.hasNotificationWithId("alert-error-1"));
    }
}
