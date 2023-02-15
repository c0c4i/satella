package it.univr.satella.e2e;

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

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SlotViewTest {

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
    public void testRootRedirect() {
        driver.get("http://localhost:8080/");
        SlotListPage slotListPage = new SlotListPage(driver);
        assertTrue(slotListPage.isCurrentPage());
    }
}
