package com.web.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OpinionPageObjects {
	private WebDriver driver;

	public OpinionPageObjects(WebDriver driver) {
		this.driver = driver;

	}

	@FindBy(xpath = "//div[@class='editorial_section']//a[@itemprop='url' and string-length()>0]")
	private List<WebElement> editorialSectionLinks;

	@FindBy(xpath = "//div[@class='commentary_section']//li//a[ string-length()>0]")
	private List<WebElement> etCommentarySection;

	@FindBy(xpath = "//div[@class='interview_section']//li//a[ string-length()>0]")
	private List<WebElement> etInterviewSection;

	@FindBy(xpath = "//div[@class='datawise_section']//div[@class='img_ctn']//a")
	private List<WebElement> datawiseSection;

	@FindBy(xpath = "//div[@class='citing_section']//ul//li//a[string-length()>0]")
	private List<WebElement> etCitingSection;

	@FindBy(xpath = "//div[@class='commentary_section_st']//ul//li//a[string-length()>0]")
	private List<WebElement> speakingTreeSection;

	@FindBy(xpath = "//div[@class='blog_section']//ul//li//a[string-length()>0]")
	private List<WebElement> blogSection;
	
	//////getters////////////////
	

	public List<WebElement> getEditorialSectionLinks() {
		return editorialSectionLinks;
	}

	public List<WebElement> getEtCommentarySection() {
		return etCommentarySection;
	}

	public List<WebElement> getEtInterviewSection() {
		return etInterviewSection;
	}

	public List<WebElement> getDatawiseSection() {
		return datawiseSection;
	}

	public List<WebElement> getEtCitingSection() {
		return etCitingSection;
	}

	public List<WebElement> getSpeakingTreeSection() {
		return speakingTreeSection;
	}

	public List<WebElement> getBlogSection() {
		return blogSection;
	}

	public WebElement getSectionHeading(String sectionName){
		return driver.findElement(By.xpath("//span[@class='ctn_title']//a[text()='"+sectionName+"']"));
	}
}
