package it.univr.satella.e2e;

import it.univr.satella.e2e.pages.PageObject;
import it.univr.satella.repository.SensorRepository;
import it.univr.satella.service.SlotService;
import org.junit.AfterClass;
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
public class SensorEmptyTest {

    private static WebDriver driver;

    @Autowired
    public SensorRepository sensorRepository;

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
    public void testListSensorsEmpty() {
        sensorRepository.deleteAll();
        driver.get("http://localhost:8080/sensors");
        PageObject page = new PageObject(driver);
        assertTrue(page.hasNotificationWithId("no-sensors-available"));
    }
}
