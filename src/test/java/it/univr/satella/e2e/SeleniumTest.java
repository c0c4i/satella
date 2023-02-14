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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SeleniumTest {

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
        assertEquals(2, slotListPage.getSlotsCount());

        SensorSelectPage sensorSelectPage = slotListPage.clickAttachSensor(0);
        slotListPage = sensorSelectPage.clickSelect("sensor-1");

        assertTrue(slotListPage.slotHasAttachedSensor(0, "sensor-1"));
    }

    @Test
    public void testSlotNotFoundAttach() {

        driver.get("http://localhost:8080/slots");

    }
}
