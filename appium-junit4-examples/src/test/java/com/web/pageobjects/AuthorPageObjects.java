package com.web.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AuthorPageObjects {

	@FindBy(xpath = "//p[@id='full_bio']")
	private WebElement authorFullBio;

	@FindBy(xpath = "//div[@class='stry_item']//a[string-length()>0 and contains(@href,'show')]")
	private List<WebElement> articleList;

	@FindBy(xpath = "//p[@class='name']")
	private WebElement authorName;

	@FindBy(xpath = "//div[@class='time_blk']//span[contains(@class,'normal')]")
	private List<WebElement> dateTimeofArticles;

	public WebElement getAuthorFullBio() {
		return authorFullBio;
	}

	public List<WebElement> getArticleList() {
		return articleList;
	}

	public WebElement getAuthorName() {
		return authorName;
	}

	public List<WebElement> getDateTimeofArticles() {
		return dateTimeofArticles;
	}

	public WebElement getDateTimeOfAticle(WebElement we) {

		return we.findElement(By.xpath("./following-sibling::div[@class='time_blk']//span[contains(@class,'normal')]"));

	}

}
