package it.univr.satella.e2e;

import it.univr.satella.e2e.pages.PageObject;
import it.univr.satella.repository.SensorRepository;
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

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SensorEmptyTest {

    private static WebDriver driver;

    @Autowired
    public SensorRepository sensorRepository;

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
    public void testListSensorsEmpty() {
        sensorRepository.deleteAll();
        driver.get("http://localhost:8080/sensors");
        PageObject page = new PageObject(driver);
        assertTrue(page.hasNotificationWithId("no-sensors-available"));
    }
}
