package it.univr.satella.e2e;

import it.univr.satella.e2e.pages.InsertSensorPage;
import it.univr.satella.e2e.pages.SensorListPage;
import org.junit.AfterClass;
import org.junit.Before;
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
public class SensorInsertTest {

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
    public void testSensorInsertCorrect() {
        driver.get("http://localhost:8080/sensors");
        SensorListPage sensorListPage = new SensorListPage(driver);
        assertTrue(sensorListPage.isCurrentPage());

        InsertSensorPage insertSensorPage = sensorListPage.clickInsertSensor();
        assertTrue(insertSensorPage.isCurrentPage());

        insertSensorPage.setSensorData("inserted", 0.0f, 1.0f, 0.0f, 1.0f);
        sensorListPage = insertSensorPage.clickInsert();
        assertTrue(sensorListPage.isCurrentPage());

        assertTrue(sensorListPage.hasSensorWithModel("inserted"));
        assertTrue(sensorListPage.hasNotificationWithId("alert-sensor-connect-success-1"));
    }

    @Test
    public void testSensorInsertInvalid() {
        driver.get("http://localhost:8080/sensors");
        SensorListPage sensorListPage = new SensorListPage(driver);
        assertTrue(sensorListPage.isCurrentPage());

        InsertSensorPage insertSensorPage = sensorListPage.clickInsertSensor();
        assertTrue(insertSensorPage.isCurrentPage());

        insertSensorPage.setSensorData("inserted", 10.0f, 1.0f, 0.0f, 1.0f);
        insertSensorPage.clickInsert(); // Should remain in the same page
        assertTrue(insertSensorPage.isCurrentPage());

        assertTrue(insertSensorPage.hasNotificationWithId("form-validation-error-4"));
    }

    @Test
    public void testSensorInsertCancel() {
        driver.get("http://localhost:8080/sensors");
        SensorListPage sensorListPage = new SensorListPage(driver);
        assertTrue(sensorListPage.isCurrentPage());

        InsertSensorPage insertSensorPage = sensorListPage.clickInsertSensor();
        assertTrue(insertSensorPage.isCurrentPage());

        sensorListPage = insertSensorPage.clickCancel();
        assertTrue(sensorListPage.isCurrentPage());
    }
}
