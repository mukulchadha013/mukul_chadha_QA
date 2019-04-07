package features;

import static features.ElementLocators.edtUserName;
import static features.ElementLocators.edtPassword;
import static features.ElementLocators.btnSignIn;
import static features.ElementLocators.textErrMsg;
import org.apache.log4j.Logger;
import Base.BaseSteps;

public class OrmuLoginPage extends BaseSteps{
	
	private static Logger log = Logger.getLogger(new Throwable().getStackTrace()[0].getClassName());
	
	public void validateLoginPage() {
		storeTestInfo("Validating the Ormuco login page.");
		existAfterWait(edtUserName);
		existAfterWait(edtPassword);
		existAfterWait(btnSignIn);
	}
	
	public void enterUserName(String username) {
		if (username.isEmpty()) {
			storeTestError("Username is empty.");
		}else {
			sendKeysByxPath(edtUserName, username);
			storeTestInfo("Username entered: " + username);
		}
	}
	
	public void enterPassword(String password) {
		if (password.isEmpty()) {
			storeTestError("Password is empty.");
		}else {
			sendKeysByxPath(edtPassword, password);
			storeTestInfo("Password entered: " + password);
		}
	}
	
	public void clickSignInButton() {
		storeTestInfo("Click Signin button.");
		clickByXpath(btnSignIn);
	}
	
	public void validateLoginError() {
		storeTestInfo("Validating login error message.");
		waitForVisibility(textErrMsg);
	}
}
