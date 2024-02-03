package StepDefinitions;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utilities.BasePage;
import utilities.TestUtils;

public class SetupClass {

	private static WebDriver driver;
	private static BasePage basePage;
	private static SetupClass setup = new SetupClass();
	public static Scenario scn;

	@SuppressWarnings("deprecation")
	@Before
	public void setUp(Scenario scenario) {
		String projectPath = System.getProperty("user.dir");
		String propertyFile = projectPath + "/project.properties";
		TestUtils utils = new TestUtils(propertyFile);

		System.out.println("Setting up the browser");
		System.setProperty(utils.getProperty("Chrome"), projectPath + utils.selectFromChain("SelectedVersion"));

		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		basePage = new BasePage(driver);
		setup.beforeHooks(scenario);
	}

	private void beforeHooks(Scenario s) {
		this.scn = s;
	}

	public static Scenario getScenario() {
		return scn;
	}
	
	private void afterHooks() {
		scn.log("Current Scenario Status: " + scn.getStatus());
	}

	public static WebDriver getDriver() {
		return driver;
	}

	public static BasePage getBasePage() {
		return basePage;
	}

	@AfterStep
	public void takeScreenshotAfterStep(Scenario scenario) {
		if (driver != null && ((RemoteWebDriver) driver).getSessionId() != null) {

			final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshot, "image/png", "image");
		}
	}

	@After
	public void tearDown() throws InterruptedException {
		if (driver != null) {
			System.out.println("Closing the browser");
			driver.quit();
			setup.afterHooks();
		}
	}
}
