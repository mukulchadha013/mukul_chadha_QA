package Base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;
import test.reportInterface.ExtentTestManager;

import Utils.FileUtils;

public class BaseSteps extends ProjectConfigurations{

	private static Logger log = Logger.getLogger(new Throwable().getStackTrace()[0].getClassName());
	
	public void setUp(){
		//Please do not change the ordering of methods in setUp @author: mchadha
		softAssert = new SoftAssert();
		setLogConfig();
		readProperties();
		waitTime = Integer.valueOf(properties.getProperty(waitTimeProp));
		initializeDriver();
		testDataObject = readTestData();
	}

	public void tearDown(){
		quitDriver();
	}

	public void initializeDriver() {		
		try {			
			log.info("Initializing the driver..");
			String browserName = properties.getProperty(browserNameProp);
			if (browserName.equalsIgnoreCase(chromeBrowser)) {
				initializeChromeDriver();
			}else {				
				log.error("Invalid Browser Name");
				System.err.println("Invalid Browser Name");
				Assert.fail("Invalid browser Name");
			}
			wait = new WebDriverWait(driver, waitTime);
		}catch(Exception ex) {
			ex.printStackTrace();
			log.error("Exception occurred while initializing the driver : " + ex.getMessage());
		}
	}

	public void initializeChromeDriver() {
		String chromeDriverName = "";
		if (CurrentOS.contains(MacOS))
			chromeDriverName = chromeDriverMacUtility;
		else
			chromeDriverName = chromeDriverWinUtility;
		if (new File(getDriversPath() + "/" + chromeDriverName).exists()) {
			log.info("initializing the chrome driver " + getDriversPath() + "/" + chromeDriverName );
		}else {
			log.error("chromedriver path does not exists: " + getDriversPath() + "/" + chromeDriverName );
			Assert.fail("chromedriver path does not exists: " + getDriversPath() + "/" + chromeDriverName);
		}
		System.setProperty("webdriver.chrome.driver", getDriversPath() + "/" + chromeDriverName);
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
	}

	public String getDriversPath(){
		String driversPath = "";
		driversPath =  projectPath + basePath + "drivers";
		return driversPath;
	}

	public String getConfigPath(){
		String configPath = "";
		configPath =  projectPath + basePath + "configurations";
		return configPath;
	}
	
	public JSONObject readTestData(){
		JSONParser parser = new JSONParser();
		JSONObject testDataObject = null, jsonObject = null;
		String jsonErrMsg = "JSON Test Data file path not found" , absolutePath = "";
		try {
			File projectDir = new File(projectPath);
			absolutePath = FileUtils.findFile(jsonTestDataFileName, projectDir);
			if (absolutePath.equals("")){
				log.error(jsonErrMsg);
				System.err.println(jsonErrMsg);
				softAssert.fail(jsonErrMsg);
				Assert.fail(jsonErrMsg);
			}else {
				Object obj = parser.parse(new FileReader(absolutePath));
				jsonObject = (JSONObject) obj;
				testDataObject = (JSONObject) jsonObject.get(properties.get(testEnvironmentProp));	
			}
		}catch(FileNotFoundException ex) {
			log.error("Unable to found test data file.");
			ex.printStackTrace();
		}catch(IOException ex) {
			log.error("Unable to perform input output operation.");
			ex.printStackTrace();
		}catch(ParseException ex) {
			log.error("Unable to parse json test data.");
			ex.printStackTrace();
		}
		return testDataObject;
	}

	public void readProperties() {
		InputStream inputStream;
		try {
			properties = new Properties();
			if (new File(getConfigPath() + "/" + testConfigurationsFile).exists()) {
				log.info("reading properties from the path : " + getConfigPath() + "/" + testConfigurationsFile);
				inputStream = new FileInputStream(getConfigPath() + "/" + testConfigurationsFile);
				properties.load(inputStream);
			}else {
				log.error("properties file does not exists : " + getConfigPath() + "/" + testConfigurationsFile);
				Assert.fail("properties file does not exists : " + getConfigPath() + "/" + testConfigurationsFile);
			}		
		}catch(IOException iox) {
			log.error("Exception occurred while reading/loading the properties : " + iox.getMessage());
			iox.printStackTrace();
			Assert.fail("Input/Output exception occurred: ");
		}
	}

	public void setLogConfig(){
		String logFilePath = projectPath + "/" + logFolder + "/" + logFile;
		FileUtils.deleteFile(logFilePath);
		FileUtils.createFile(logFilePath);
		String logConfigFilePath = getConfigPath()  + "/" +  logConfigFile;
		DOMConfigurator.configure(logConfigFilePath); 
	}

	public void quitDriver() {
		log.info("Quit the driver" );
		driver.quit();
	}

	public void closeBrowser() {
		storeTestInfo("Close the browser.");
		driver.close();;
	}

	public void loadUrl(){
		log.info("loading the url: " + properties.getProperty(urlProp));
		driver.get(properties.getProperty(urlProp));
		waitForLoad(driver);
	}

	public void waitForLoad(WebDriver driver) {
		log.info("wait for page to load...");
		ExpectedCondition<Boolean> pageLoadCondition = new
				ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
			}
		};
		wait.until(pageLoadCondition);
	}

	public void existAfterWait(String elementXPath) {
		boolean found = waitForVisibility(elementXPath);
		if (!found) {
			Assert.fail("Element is not present in DOM: " + elementXPath + ", exiting current test.");
		}
	}

	public boolean waitForVisibility(String xPath) {
		boolean visible = true;
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath)));
		} catch (TimeoutException tOut) {
			storeTestError("Timed Out waiting for an app element to get visible " + xPath);
			visible = false;
		}
		return visible;
	}

	/**
	 * This method is used to enter keys in web element search via xpath
	 * @param xpath: This parameter specifies the xpath of web element
	 * @param value: This parameter specifies the value to be entered in edit text field
	 * @return keysEntered: This boolean value specifies whether the 'value' has been successfully entered or not.
	 */
	public boolean sendKeysByxPath(String xPath, String value) {
		boolean keysEntered = false;
		try {
			WebElement element = getWebElement("xpath", xPath);
			if (element != null) {
				element.clear();
				element.sendKeys(value);
				element.sendKeys(Keys.RETURN);

				keysEntered = true;
			} else {
				throw new NoSuchElementException("Unable to enter text as element is not present: " + xPath);
			}
		} catch (NoSuchElementException ex) {
			storeTestError("Unable to enter text as element is not present: " + xPath);
		}
		return keysEntered;
	}

	/**
	 * This method is used to enter keys in web element search via xpath
	 * @param xpath: This parameter specifies the xpath of web element
	 */
	public void clickByXpath(String xpath) {
		try {
			WebElement webElement = getWebElement( "xpath", xpath);
			if (webElement != null) {
				webElement.click();
			}else {
				throw new NoSuchElementException("Unable to click as element is not present: " + xpath);
			}
		}catch (NoSuchElementException ex) {
			storeTestError("Unable to enter text as element is not present: " + xpath);
		}
	}

	/**
	 * This method is used to get the web element with the help of a object locator.
	 * @param locatorType: This parameter indicates the locator type to be used for e.g. id/xpath/name etc.
	 * @param locator: This parameter specifies the actual value of a locator
	 * @return element: This is the web elements which is returned by this function
	 */
	public WebElement getWebElement(String locatorType, String locator) {
		WebElement element = null;
		try {

			List<WebElement> elementList = getWebElements(locatorType, locator);
			
			if (elementList.size() > 0) {
				element = elementList.get(0);
			}
		} catch (NoSuchElementException noSuchElement) {
			storeTestInfo("Element with " + locatorType + " : " + locator + " not found");
		}
		return element;
	}

	/**
	 * This method is used to get the web elements with the help of an object locator specified.
	 * @param locatorType: This parameter indicates the locator type to be used for e.g. id/xpath/name etc.
	 * @param locator: This parameter specifies the actual value of a locator
	 * @return elementList: This is the list of web elements which is returned by this function
	 */
	public List<WebElement> getWebElements(String locatorType, String locator) {
		List<WebElement> elementList = null;
		try {
			if (locatorType.trim().equalsIgnoreCase("xpath")) {
				elementList = driver.findElements(By.xpath(locator));
			} else if (locatorType.trim().equalsIgnoreCase("id")) {
				elementList = driver.findElements(By.id(locator));
			} else if (locatorType.trim().equalsIgnoreCase("name")) {
				elementList = driver.findElements(By.name(locator));
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return elementList;
	}

	public void storeTestError(String message) {
		softAssert.fail(message);
		log.error(message);
		ExtentTestManager.getTest().log(Status.ERROR, message);
		System.err.println(message);
	}

	public void storeTestInfo(String message) {
		log.info(message);
		System.out.println(message);
		ExtentTestManager.getTest().log(Status.INFO, message);
	}
}
