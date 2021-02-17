package com.web.pagemethods;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import web.pageobjects.OpinionPageObjects;

public class OpinionPageMethods {
	private WebDriver driver;
	private OpinionPageObjects opinionPageObjects;

	public OpinionPageMethods(WebDriver driver) {
		this.driver = driver;
		opinionPageObjects = PageFactory.initElements(driver, OpinionPageObjects.class);

	}

	public List<String> getEditorialSectionLinks() {
		List<String> listHref = new LinkedList<>();
		try {
			listHref = WebBaseMethods.getListHrefUsingJSE(opinionPageObjects.getEditorialSectionLinks());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return listHref;

	}

	public List<String> getEtCommentarySectionLinks() {
		List<String> listHref = new LinkedList<>();
		try {
			listHref = WebBaseMethods.getListHrefUsingJSE(opinionPageObjects.getEtCommentarySection());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return listHref;

	}

	public List<String> getEtInterviewSectionLinks() {
		List<String> listHref = new LinkedList<>();
		try {
			listHref = WebBaseMethods.getListHrefUsingJSE(opinionPageObjects.getEtInterviewSection());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return listHref;

	}

	public List<String> getDatawiseSectionLinks() {
		List<String> listHref = new LinkedList<>();
		try {
			listHref = WebBaseMethods.getListHrefUsingJSE(opinionPageObjects.getDatawiseSection());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return listHref;

	}

	public List<String> getEtCitingSectionLinks() {
		List<String> listHref = new LinkedList<>();
		try {
			listHref = WebBaseMethods.getListHrefUsingJSE(opinionPageObjects.getEtCitingSection());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return listHref;

	}

	public List<String> getSpeakingTreeSectionLinks() {
		List<String> listHref = new LinkedList<>();
		try {
			listHref = WebBaseMethods.getListHrefUsingJSE(opinionPageObjects.getSpeakingTreeSection());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return listHref;

	}

	public List<String> getBlogSection() {
		List<String> listHref = new LinkedList<>();
		try {
			listHref = WebBaseMethods.getListHrefUsingJSE(opinionPageObjects.getBlogSection());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return listHref;

	}

	public String getSectionHeadingLink(String sectionName) {
		String href = "";
		try {
			href = WebBaseMethods.getHrefUsingJSE(opinionPageObjects.getSectionHeading(sectionName));
			System.out.println("Section heading is: " + href);
		} catch (Exception ee) {
			ee.printStackTrace();
		}

		return href;
	}
}
