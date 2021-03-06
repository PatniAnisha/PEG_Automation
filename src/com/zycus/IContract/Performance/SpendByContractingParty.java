package com.zycus.IContract.Performance;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Node;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import common.Functions.eInvoice_CommonFunctions;

public class SpendByContractingParty extends eInvoice_CommonFunctions {

	private WebDriver driver;
	private ExtentTest logger;
	
	public SpendByContractingParty(WebDriver driver, ExtentTest logger) {
		super(driver, logger);
		this.driver = driver;
		this.logger = logger;
	}

	public boolean searchContracts(String searchBy, String searchValue) {
		boolean result = false;
		try {
			findElement(By.xpath("//select[@name='searchSelect']/option[text()='"+searchBy+"']")).click();
			findElement(By.id("searchValue")).sendKeys(searchValue);
			result = clickAndWaitUntilElementAppears(By.xpath("//div[@id='search-bar']//input[@value='Go']"), By.id("gridMessageDiv"))?true:false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
