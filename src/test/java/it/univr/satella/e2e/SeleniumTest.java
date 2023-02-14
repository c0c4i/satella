package it.univr.satella.e2e;

import it.univr.satella.e2e.pages.SensorSelectPage;
import it.univr.satella.e2e.pages.SlotsViewPage;
import org.junit.Before;
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

    @Test
    public void testSlotAttachCorrect() {

        driver.get("http://localhost:8080/slots");
        SlotsViewPage slotsViewPage = new SlotsViewPage(driver);
        assertEquals(2, slotsViewPage.getSlotsCount());

        SensorSelectPage sensorSelectPage = slotsViewPage.clickAttachSensor(0);
        slotsViewPage = sensorSelectPage.clickSelect("sensor-1");

        assertTrue(slotsViewPage.slotHasAttachedSensor(0, "sensor-1"));
    }
}
