package com.web.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class JobPageObjects {

	@FindBy(xpath = "//div[@class='eachStory']//a[string-length()>0]")
	private List<WebElement> jobArticleListLinks;

	@FindBy(xpath = "(//div[@class='newsCont'])[1]//div//a[string-length()>0]")
	private List<WebElement> workLifeWidgetLinks;

	@FindBy(xpath = "//span[text()='ET Prime']")
	private WebElement etPrimeSectionLink;

	@FindBy(xpath = "//div[@class='etprimedata']//div//a[string-length()>0]")
	private List<WebElement> primeSectionArticleLinks;

	//////////// Getters////////////////////////

	public List<WebElement> getJobArticleListLinks() {
		return jobArticleListLinks;
	}

	public List<WebElement> getWorkLifeWidgetLinks() {
		return workLifeWidgetLinks;
	}

	public WebElement getEtPrimeSectionLink() {
		return etPrimeSectionLink;
	}

	public List<WebElement> getPrimeSectionArticleLinks() {
		return primeSectionArticleLinks;
	}

}
