package StepDefinitions;

import java.io.IOException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import utilities.DeleteReports;
import utilities.ZipTestResults;

@RunWith(Cucumber.class)
@CucumberOptions(
    features="src/test/resources/Features/vamshi.feature",
    glue= {"StepDefinitions","pages"},
    monochrome = true,
    plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"} // Correct plugin specification
)
public class TestRunnerTest {
    
	@BeforeClass
	public static void start_of_the_suite() {
		System.out.println("Start of the Suite");
		DeleteReports.deleteTestReportsFolder();
		SetupClass.getJiraFlagStatus();
	}
	
	@AfterClass
	public static void zipTestReportsFolder() throws IOException {
		ZipTestResults.zipTestReportsFolder();
		System.out.println("Finished zipping results folder");
		
	}    
    
}
