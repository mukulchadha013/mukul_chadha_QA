package examples;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import features.FeaturesManagerFactory;
import Base.BaseSteps;

public class TestSuiteHooks extends BaseSteps{
	private static Logger log = Logger.getLogger(new Throwable().getStackTrace()[0].getClassName());
	FeaturesManagerFactory featuresManagerFactory =  null;
	@BeforeSuite 
	public void beforeSuite() {
		System.out.println("This will execute before the Test Suite");
		log.info("This will execute before the Test Suite");
		setUp();
		
		featuresManagerFactory = FeaturesManagerFactory.getInstance();
		
	}

	@AfterSuite
	public void afterSuite() {
		System.out.println("This will execute after the Test Suite");
		log.info("This will execute after the Test Suite");
		tearDown();
		softAssert.assertAll();
	}
}
