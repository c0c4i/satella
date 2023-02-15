package it.univr.satella.e2e;

import it.univr.satella.e2e.pages.PageObject;
import it.univr.satella.service.SlotService;
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

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SlotEmptyTest {

    private static WebDriver driver;

    @Autowired
    private SlotService slotService;

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
    public void testSlotEmpty() {
        slotService.loadSlots("src/test/resources/sensors_empty.json");
        driver.get("http://localhost:8080/slots");
        PageObject page = new PageObject(driver);
        assertTrue(page.hasNotificationWithId("alert-slots-empty"));
    }
}
