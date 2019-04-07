package features;

import static Base.ProjectConfigurations.softAssert;
import org.apache.log4j.Logger;
import Base.BaseSteps;


@SuppressWarnings("unused")
public class SignInPage extends BaseSteps {
	
	private static Logger log = Logger.getLogger(new Throwable().getStackTrace()[0].getClassName());
	
	public void validatePageTitle(String expectedTitle){
		String actualTitle = driver.getTitle();
		if(!actualTitle.trim().equalsIgnoreCase(expectedTitle.trim())){
			softAssert.fail("Actual page title: " + actualTitle + " not equals Expected page title : " + expectedTitle);
			log.error("Actual page title: " + actualTitle + " not equals Expected page title : " + expectedTitle);
		}
	}
}
