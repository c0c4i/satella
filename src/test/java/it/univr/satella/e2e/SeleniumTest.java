package it.univr.satella.e2e;

import it.univr.satella.e2e.pages.SensorSelectPage;
import it.univr.satella.e2e.pages.SlotListPage;
import it.univr.satella.model.SensorTest;
import it.univr.satella.service.SensorServiceTest;
import it.univr.satella.service.SlotServiceTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SlotAttachTest.class,
        SlotDetachTest.class,
        SlotEmptyTest.class,
        SensorViewTest.class,
        SensorInsertTest.class,
        SensorModifyTest.class,
        SensorRemoveTest.class,
        SensorEmptyTest.class,
        SlotViewTest.class
})
public class SeleniumTest {
}
