package Base;

import java.util.Properties;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

public class ProjectConfigurations {
	
	public static String projectPath = System.getProperty("user.dir");
	public static String testConfigurationsFolder = "configurations";
	public static String testConfigurationsFile = "config.properties";
	public static String logConfigFile = "log4j.xml";
	public static String logFile = "logfile.log";
	public static String logFolder = "logs";
	public static String basePath = "/src/main/java/";
	public static String chromeDriverWinUtility = "chromedriver.exe";
	public static String chromeDriverMacUtility = "chromedriver";
	public static String chromeBrowser = "chrome";
	public static String MacOS = "Mac OS X";
	public static String CurrentOS = System.getProperty("os.name");

	public static WebDriver driver = null;
	public static Properties properties = null;
	public static SoftAssert softAssert = null;
	public static WebDriverWait wait = null;
	public static JSONObject testDataObject = null;
	public static JSONObject testCaseDataObject = null;
	
	public static int waitTime = 0;
	
	public static String waitTimeProp = "waitTime";
	public static String urlProp = "url";
	public static String browserNameProp = "browserName";
	public static String testEnvironmentProp = "testEnvironment";
	public static String jsonTestDataFileName = "testData.json";
}
