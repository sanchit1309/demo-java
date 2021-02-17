package pwa.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ETPrimeArticleshowObjects {

	private WebDriver driver;

	public ETPrimeArticleshowObjects(WebDriver driver) {
		this.driver = driver;

	}

	@FindBy(xpath = "(//a[@id='prime-plan'])[1]")
	private WebElement etPrimePaywallBlocker;

	/////////////////// getters//////////////////////////

	public WebElement getEtPrimePaywallBlocker() {
		return etPrimePaywallBlocker;
	}

}
