package runner;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith( Cucumber.class)

@CucumberOptions(
        features = "src/test/java/features",
        glue = "steps",
        plugin = {
                "pretty",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "html:target/cucumber-report/cucumber-html-report.html",
                "json:target/cucumber-report/cucumber.json",
                "junit:target/cucumber-report/cucumber.xml",
                "usage:target/cucumber-report/cucumber-usage.json"
        }


)

public class RunTest {
}
