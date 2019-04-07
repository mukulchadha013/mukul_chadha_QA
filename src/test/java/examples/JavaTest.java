package examples;

import org.testng.annotations.Test;


import org.testng.annotations.BeforeMethod;
import org.apache.log4j.Logger;

import org.testng.annotations.AfterMethod;

public class JavaTest extends TestSuiteHooks{
	private static Logger log = Logger.getLogger(new Throwable().getStackTrace()[0].getClassName());
	@BeforeMethod
	public void beforeMethod() {
		System.out.println("Before Test method.....");
		log.info("Before Test method.....");
		loadUrl();
	}
	
	@AfterMethod
	public void afterMethod() {
		System.out.println("After Test method.....");
		log.info("After Test method.....");
	}
	
	@Test
	public void firstTest() {
		System.out.println("Test Started.....");
		log.info("Test Started.....");
		featuresManagerFactory.getSignInPage().validatePageTitle("Gmails");
		
	}
}
