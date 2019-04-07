package examples;

import org.testng.annotations.Test;

import features.OrmuLoginPage;

import org.testng.annotations.BeforeMethod;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

public class JavaTest1 extends TestSuiteHooks{
	
	private static Logger log = Logger.getLogger(new Throwable().getStackTrace()[0].getClassName());
	
	@BeforeMethod
	public void beforeMethod(ITestResult result) {
		System.out.println("Before Test method.....");
		log.info("Before Test method.....");
		loadUrl();
		String testMethodName = result.getMethod().getMethodName();
		testCaseDataObject = (JSONObject) testDataObject.get(testMethodName.trim());
	}
	
	@AfterMethod
	public void afterMethod() {
		System.out.println("After Test method.....");
		log.info("After Test method.....");
		closeBrowser();
	}
	
	@Test
	public void firstTest() {
		System.out.println("Test Started.....");
		log.info("Test Started.....");
		String Username = (String) testCaseDataObject.get("Username");
		String Password = (String) testCaseDataObject.get("Username");
		featuresManagerFactory.getOrmuLoginPage().validateLoginPage();
		featuresManagerFactory.getOrmuLoginPage().enterUserName(Username);
		featuresManagerFactory.getOrmuLoginPage().enterPassword(Password);
		featuresManagerFactory.getOrmuLoginPage().clickSignInButton();
		featuresManagerFactory.getOrmuLoginPage().validateLoginError();
		
	}
}
