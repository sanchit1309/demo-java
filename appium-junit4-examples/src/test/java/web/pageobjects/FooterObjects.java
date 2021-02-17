package web.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FooterObjects {
@FindBy(xpath="//div[@id='messageDiv']//li/a")
private List<WebElement> inCaseYouMissedItLinks;

@FindBy(xpath="//div[@id='messageDiv']//li[@class='clr homeSprite flt']//a")
private List<WebElement> inCaseYouMissedItLeftLinks;

@FindBy(xpath = "//div[@id='messageDiv']//li[@class='homeSprite flr']//a")
private List<WebElement> inCaseYouMissedItRightLinks;




////////////////////////////////////////




public List<WebElement> getInCaseYouMissedItLeftLinks() {
	return inCaseYouMissedItLeftLinks;
}

public List<WebElement> getInCaseYouMissedItRightLinks() {
	return inCaseYouMissedItRightLinks;
}

public List<WebElement> getInCaseYouMissedItLinks() {
	return inCaseYouMissedItLinks;
}





}
