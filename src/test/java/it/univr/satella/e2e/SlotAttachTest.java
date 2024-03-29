package it.univr.satella.e2e;

import it.univr.satella.e2e.pages.PageObject;
import it.univr.satella.e2e.pages.SensorSelectPage;
import it.univr.satella.e2e.pages.SlotListPage;
import it.univr.satella.service.SlotService;
import org.apache.commons.lang3.SystemUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
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
        // Direct
        driver.get("http://localhost:8080/slots/999/connect");
        PageObject page = new PageObject(driver);
        assertTrue(page.hasNotificationWithId("form-validation-error"));

        // With redirect
        driver.get("http://localhost:8080/slots/999/connect/sensor-1");
        SlotListPage slotListPage = new SlotListPage(driver);
        assertTrue(slotListPage.isCurrentPage());
        assertTrue(slotListPage.hasNotificationWithId("alert-error-1"));
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

    @Test
    public void testSlotAttachSensorsEmpty() {
        driver.get("http://localhost:8080/slots/2/connect");
        PageObject page = new PageObject(driver);
        assertTrue(page.hasNotificationWithId("no-sensors-available"));
    }
}
