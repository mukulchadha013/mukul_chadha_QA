package features;

public class ElementLocators {

	/*------ Ormuco -------------*/
	public static final String edtUserName = "//input[@id='username']";
	public static final String edtPassword = "//input[@id='password']";
	public static final String btnSignIn = "//button[@type='submit' and contains(@ng-bind,'SIGN IN')]";
	public static final String textErrMsg = "//span[text()='The user or password is incorrect.']";
}
