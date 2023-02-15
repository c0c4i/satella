package it.univr.satella.e2e;

import it.univr.satella.e2e.pages.SensorSelectPage;
import it.univr.satella.e2e.pages.SlotListPage;
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
public class SlotAttachTest {

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
    public void testSlotAttachCorrect() {
        driver.get("http://localhost:8080/slots");
        SlotListPage slotListPage = new SlotListPage(driver);
        assertTrue(slotListPage.isCurrentPage());

        SensorSelectPage sensorSelectPage = slotListPage.clickAttachSensor(0);
        assertTrue(sensorSelectPage.isCurrentPage());

        slotListPage = sensorSelectPage.clickSelect("sensor-1");
        assertTrue(slotListPage.isCurrentPage());

        assertTrue(slotListPage.slotHasAttachedSensor(0, "sensor-1"));
        assertTrue(slotListPage.hasNotificationWithId("alert-success-1"));
    }

    @Test
    public void testSlotNotFoundAttach() {
        driver.get("http://localhost:8080/slots/999/disconnect");
        SlotListPage slotListPage = new SlotListPage(driver);
        assertTrue(slotListPage.isCurrentPage());

        assertTrue(slotListPage.hasNotificationWithId("alert-error-4"));
    }

    @Test
    public void testSlotAttachSensorNotFound() {
        driver.get("http://localhost:8080/slots/0/connect/not-existent");
        SlotListPage slotListPage = new SlotListPage(driver);
        assertTrue(slotListPage.isCurrentPage());

        assertTrue(slotListPage.hasNotificationWithId("alert-error-2"));
    }

    @Test
    public void testSlotAttachSensorNotCompatible() {
        driver.get("http://localhost:8080/slots/0/connect/sensor-3");
        SlotListPage slotListPage = new SlotListPage(driver);
        assertTrue(slotListPage.isCurrentPage());

        assertTrue(slotListPage.hasNotificationWithId("alert-error-3"));
    }

    @Test
    public void testSlotAttachCancel() {
        driver.get("http://localhost:8080/slots/0/connect");
        SensorSelectPage sensorSelectPage = new SensorSelectPage(driver);
        assertTrue(sensorSelectPage.isCurrentPage());

        SlotListPage slotListPage = sensorSelectPage.clickCancel();
        assertTrue(slotListPage.isCurrentPage());
    }

    @Test
    public void testSlotAttachShowOnlyCompatible() {
        driver.get("http://localhost:8080/slots/0/connect");
        SensorSelectPage sensorSelectPage = new SensorSelectPage(driver);
        assertTrue(sensorSelectPage.isCurrentPage());

        assertFalse(sensorSelectPage.hasSensorWithModel("sensor-3"));
    }
}
