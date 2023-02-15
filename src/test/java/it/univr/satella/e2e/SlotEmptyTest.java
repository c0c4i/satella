package it.univr.satella.e2e;

import it.univr.satella.e2e.pages.PageObject;
import it.univr.satella.service.SlotService;
import org.apache.commons.lang3.SystemUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
    public void testSlotEmpty() {
        slotService.loadSlots("src/test/resources/sensors_empty.json");
        driver.get("http://localhost:8080/slots");
        PageObject page = new PageObject(driver);
        assertTrue(page.hasNotificationWithId("alert-slots-empty"));
    }
}
