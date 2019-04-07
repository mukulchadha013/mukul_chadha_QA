package features;

import org.apache.log4j.Logger;

public class FeaturesManagerFactory {
	private static Logger log = Logger.getLogger(new Throwable().getStackTrace()[0].getClassName());

	private SignInPage signInPage;
	private OrmuLoginPage ormuLoginPage;
	// declaring an instance of class FeaturesManagerFactory which is null initially means not initialized. 
	private static FeaturesManagerFactory instanceFeaturesManagerFactory = null;


	// Declaring constructor as private to restrict object creation outside of class
	private FeaturesManagerFactory()
	{
		log.info("FeaturesManagerFactory object created.");
	}

	// static method to create instance of class SingletonClassExample
	public static FeaturesManagerFactory getInstance()
	{
		if (instanceFeaturesManagerFactory == null)
			instanceFeaturesManagerFactory = new FeaturesManagerFactory();

		return instanceFeaturesManagerFactory;
	}

	public SignInPage getSignInPage(){
		return (signInPage == null) ? signInPage = new SignInPage() : signInPage;
	}

	public OrmuLoginPage getOrmuLoginPage(){
		return (ormuLoginPage == null) ? ormuLoginPage = new OrmuLoginPage() : ormuLoginPage;
	}
}
