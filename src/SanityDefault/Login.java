package SanityDefault;

import java.nio.charset.StandardCharsets;

import javax.xml.bind.DatatypeConverter;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import Framework.ConfigurationProperties;
import common.Functions.CommonFunctions1;

public class Login extends CommonFunctions1 {

	private WebDriver driver;
	private ExtentTest logger;
	private String username;
	private String password;
	private String product;
	private String customer;
	private String userAccount;

	public Login(WebDriver driver, ExtentTest logger, String username, String password) throws Exception {
		super(driver, logger);
		this.driver = driver;
		this.logger = logger;
		this.username = username;
		this.password = password;
	}

	public Login(WebDriver driver, ExtentTest logger, String product, String username, String password, String customer)
			throws Exception {
		super(driver, logger);
		this.driver = driver;
		this.logger = logger;
		this.product = product;
		this.username = username;
		this.password = password;
		this.customer = customer;
	}
	
	public Login(WebDriver driver, ExtentTest logger, String product, String username, String password, String customer, String userAccount)
			throws Exception {
		super(driver, logger);
		this.driver = driver;
		this.logger = logger;
		this.product = product;
		this.username = username;
		this.password = password;
		this.customer = customer;
		this.userAccount = userAccount;
	}

	public boolean login(ConfigurationProperties configurationProperties) {
		boolean result = false;
		try {
			String URL = configurationProperties.getProperty("url_ZSN");

			// Launch URL
			driver.manage().window().maximize();
			driver.get(URL);

			// Enter credentials & click Login button
			sendKeys(By.id("login-email"), username);
			logger.log(LogStatus.INFO, "username entered is " + username);
			
			  sendKeys(By.id("login-password"), new
			  String(DatatypeConverter.parseBase64Binary(password),
			  StandardCharsets.UTF_8));
			 
		//	sendKeys(By.id("login-password"), password);
			findElement(By.xpath("//div/input[contains(@class,'btn btn-primary')]")).click();

			try {
				// if(findElement(By.xpath("//div[contains(@class,'zsp-wizardPage')]")).isDisplayed())
				findElement(By.xpath("//div[contains(@class,'zsp-wizardPage')]//a[@id='i_will_do']")).click();
				waitUntilInvisibilityOfElement(By.id("notification-overlay"));
			} catch (Exception e) {
			}

			/*
			 * try { if
			 * (findElement(By.xpath("//img[@class='zsp-logo-image']")).
			 * isDisplayed()) {
			 * //waitUntilInvisibilityOfElement(By.id("notification-overlay"));
			 * LogScreenshot("INFO", "logged in to ZSN portal"); result = true;
			 * } else { logger.log(LogStatus.INFO, "home page not displayed");
			 * return result; } } catch (Exception e) { return result; }
			 */
			result = true;

			/*
			 * if
			 * (findElement(By.xpath("//div[contains(@class,'error-message')]"))
			 * .isDisplayed()) logger.log(LogStatus.INFO,
			 * "unable to log in to ZSN portal");
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * <b>Function:</b> login - Login function
	 * 
	 * @author Varun Khurana
	 * @since April 2018
	 * @param configurationProperties
	 * @return none
	 */

	public boolean Login_via_PwdMgr(ConfigurationProperties configurationProperties) {
		boolean result = false;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		try {
			String URL = configurationProperties.getProperty("url_PM");
			driver.manage().window().maximize();
			System.out.println(URL);
			driver.get(URL);

			// writeText(By.id("username"), username);
			writeText(By.id("username"), "shilki.jaiswal");
			/*
			 * sendKeys(By.id("password"), new
			 * String(DatatypeConverter.parseBase64Binary(password),
			 * StandardCharsets.UTF_8));
			 */
			// findElement(By.id("password")).sendKeys("ani17sha#");
			findElement(By.id("password")).sendKeys("Aug@1811");
			findElement(By.xpath("//*[@title='Login']")).submit();
			// findElement(By.id("FRHost_Search")).sendKeys(customer +
			// Keys.ENTER);
			// findElement(By.id("FRHost_Search")).sendKeys("igt.ca@zycus.com" +
			// Keys.ENTER);
			//findElement(By.id("FRHost_Search")).sendKeys("CDK.account@zycus.com" + Keys.ENTER);
			//findElement(By.id("FRHost_Search")).sendKeys("massmutual.za@zycus.com" + Keys.ENTER);
			//findElement(By.id("FRHost_Search")).sendKeys("Securian.support.b@zycus.com" + Keys.ENTER);
			findElement(By.id("FRHost_Search")).sendKeys("user2.Lowes@zycus.com" + Keys.ENTER);
			while(!js.executeScript("return document.readyState").equals("complete")){
				System.out.println(js.executeScript("return document.readyState"));
				Thread.sleep(100);
			}
			System.out.println("now click on detailed view");
			if (findElement(By.xpath("//div[@unique_id='SearchResult' and @id='MainDAC']")) != null) {
				findElement(By.id("bedit")).click();
				while(!js.executeScript("return document.readyState").equals("complete")){
					System.out.println(js.executeScript("return document.readyState"));
					Thread.sleep(100);
				}
				System.out.println("page loaded finally");
				String parent = driver.getWindowHandle();

				/*
				 * int loginTrial = 0; boolean onProdSelectionPg = false; while
				 * (onProdSelectionPg == false) { loginTrial = loginTrial + 1;
				 * if (loginTrial < 6) { findElement(By .xpath(
				 * "//table[@id='BulkEditSearchResult_TABLE']//tr[td/a[contains(text(),'Partner')]]/td[6]//img"
				 * )) .click(); //
				 * findElement(By.xpath("//img[@id='rdp_ico_0']")).click();
				 * findElement(By.
				 * xpath("//a[contains(text(),'Open URL in browser')]")).click()
				 * ; if (switchWindowHandles(parent, "Product Selection"))
				 * onProdSelectionPg = true; else
				 * driver.switchTo().window(parent); } else
				 * logger.log(LogStatus.INFO, "Unable to login"); }
				 */

				// findElement(By.xpath("//img[@id='rdp_ico_0']")).click();
				/*By openProdImg = By.xpath(
						"//table[@id='BulkEditSearchResult_TABLE']//tr[td/a[contains(text(),'Partner')]]/td[6]//img");*/
				By openProdImg = By.xpath("//img[@id='rdp_ico_0']");
				WebDriverWait wait1 = new WebDriverWait(driver, 360);
				WebElement element1 = wait1.until(ExpectedConditions.presenceOfElementLocated(openProdImg));
				element1.click();
				findElement(By.xpath("//a[contains(text(),'Open URL in browser')]")).click();
				driver.switchTo().window(parent);
				findElement(By.xpath("//img[@id='rdp_ico_0']")).click();
				/*findElement(By
						.xpath("//table[@id='BulkEditSearchResult_TABLE']//tr[td/a[contains(text(),'Partner')]]/td[6]//img"))
								.click();*/
				findElement(By.xpath("//a[contains(text(),'Open URL in browser')]")).click();
				// switchWindowHandles(parent, "Product Selection");
				String displayStyle = getWorkingWindowTitle(parent);
				/*if (displayStyle.equals("Rainbow")) {
					if(!switchToClassic(parent))
						logger.log(LogStatus.INFO, "not switched to classic");
				}*/
				if (selectProduct(displayStyle))
					result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean Login_via_PwdMgr1(ConfigurationProperties configurationProperties) {
		boolean result = false;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebDriverWait wait = new WebDriverWait(driver, 120);
		try {
			String URL = configurationProperties.getProperty("url_PM");
			String Environment = configurationProperties.getProperty("environment");
			driver.manage().window().maximize();
			driver.get(URL);
			
			/*Long a =(Long)js.executeScript("return document.querySelector('#username').getBoundingClientRect().y;", driver.findElement(By.id("username")));
			System.out.println(a);*/ 
			writeText(By.id("username"), username);
			//writeText(By.id("username"), "shilki.jaiswal");
			/*
			 * sendKeys(By.id("password"), new
			 * String(DatatypeConverter.parseBase64Binary(password),
			 * StandardCharsets.UTF_8));
			 */
			// findElement(By.id("password")).sendKeys("ani17sha#");
			findElement(By.id("password")).sendKeys("Aug@1811");
			findElement(By.xpath("//*[@title='Login']")).submit();
			
			findElement(By.id("PassTrixGuestQuickView_1")).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='mc_loading' and @class='mcLoaded']")));
			Thread.sleep(1000);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@id='All My Passwords_anchor']"))).click();
			//findElement(By.xpath("//a[@id='All My Passwords_anchor']")).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='mc_loading' and @class='mcLoaded']")));
			Thread.sleep(1000);
			int allDisplayedResources = driver.findElements(By.xpath("//table[@id='ResourceGroupHomeFilter_TABLE']//tr[@rowidx]")).size();
			
			//((JavascriptExecutor)driver).executeScript("return document.getElementsByName('" + flashObjectName + "')[0]."+ DriverCommand.CLICK);
			
			
			//WebElement objTemp = findElement(By.xpath("//input[@class='tableSearchButton']"));
			//Long a =(Long)js.executeScript("var element = document.evaluate(\"//input[@class='tableSearchButton']\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;return element.getBoundingClientRect().y;");
			/*String someXpath = "//input[@class='tableSearchButton']";
			Long a =(Long)js.executeScript("var element = document.evaluate(\""+someXpath+"\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;return element.getBoundingClientRect().y;");
			//document.evaluate(\"//div[contains(@class,'qtip qtip-default filterPopups') and contains(@style,'block')]\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
			System.out.println(a);
			Thread.sleep(5000);
			Long a1 =(Long)js.executeScript("var element = document.evaluate(\"//input[@class='tableSearchButton']\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;return element.getBoundingClientRect().y;");
			//document.evaluate(\"//div[contains(@class,'qtip qtip-default filterPopups') and contains(@style,'block')]\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
			System.out.println(a1);*/
			
			findElement(By.xpath("//input[@class='tableSearchButton']")).click();
			driver.findElement(By.id("PTRX_RNAMEtxt")).sendKeys(customer);
			driver.findElement(By.id("PTRX_DESCtxt")).sendKeys(Environment+ Keys.ENTER);
			
			while(driver.findElements(By.xpath("//table[@id='ResourceGroupHomeFilter_TABLE']//tr[@rowidx]")).size()>=allDisplayedResources){
				Thread.sleep(100);
			}
			String parent = driver.getWindowHandle();
			if(driver.findElements(By.xpath("//table[@id='ResourceGroupHomeFilter_TABLE']//tr[@rowidx]")).size()>0){
				findElement(By.xpath("//table[@id='ResourceGroupHomeFilter_TABLE']//tr[@rowidx='0']//a")).click();
				wait.until(ExpectedConditions.presenceOfElementLocated(By.id("PasswordGroupView_TABLE")));
				findElement(By.xpath("//table[@id='PasswordGroupView_TABLE']/tbody/tr[@rowidx][td[2]/a[text()='"+userAccount+"']]/td[5]/a")).click();
				findElement(By.xpath("//a[contains(text(),'Open URL in browser')]")).click();
				driver.switchTo().window(parent);
				findElement(By.xpath("//table[@id='PasswordGroupView_TABLE']/tbody/tr[@rowidx][td[2]/a[text()='"+userAccount+"']]/td[5]/a")).click();
				findElement(By.xpath("//a[contains(text(),'Open URL in browser')]")).click();
				String displayStyle = getWorkingWindowTitle(parent);
				/*if (displayStyle.equals("Rainbow")) {
					if(!switchToClassic(parent))
						logger.log(LogStatus.INFO, "not switched to classic");
				}*/
				if (selectProduct(displayStyle))
					result = true;
			}else{
				logger.log(LogStatus.INFO, "no such user displayed for the selected environment");
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean selectProduct(String displayStyle) {
		boolean result = false;
		try {
			if (displayStyle.equals("Rainbow")) {
				findElement(By.xpath("(//div[@id='rainbowHeader']/a/span/*)[1]")).click();
				WebDriverWait wait = new WebDriverWait(driver, 20);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='rb-header-wrap-bg' and contains(@style,'block')]")));
				Actions action = new Actions(driver);
				WebElement productLink = findElement(
						By.xpath("//ul[@id='containerForZycusMenuItems']/li/a[span/text()='"+product+"']"));
				action.click(productLink).build().perform();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@id='containerForZycusMenuItems']/li/a[span/text()='"+product+"']/../ul[contains(@style,'block')]")));
			}else{
				JavascriptExecutor js = (JavascriptExecutor) driver;
				String modifiedXpath = "activePan_" + product;
				WebDriverWait wait = new WebDriverWait(driver, 120);
				WebElement elem = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(modifiedXpath)));
				// element = findElement(By.id(modifiedXpath));
				while(!js.executeScript("return document.readyState").equals("complete")){
					System.out.println(js.executeScript("return document.readyState"));
					Thread.sleep(100);
				}
				js.executeScript("arguments[0].click();", elem);
				String prodHeaderXpath = "//div[@id='newHeaderLowerPart']//label[@class='newhdrProdNm']";
				waitUntilVisibilityOfElement(By.xpath(prodHeaderXpath));
				if (driver.findElement(By.xpath(prodHeaderXpath)).getText().equals(product))
					result = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		return result; 
	}
	
	public boolean switchToClassic(String parent){
		boolean result = false;
		try{
			JavascriptExecutor js = (JavascriptExecutor) driver;
			WebElement switchView = findElement(By.xpath("//p[text()='Switch back to Classic View']"));
			if (switchView.isDisplayed()) {
				switchView.click();
				if (findElement(By.xpath(".//*[@class='rb-switchBlk rb-clearfix rb-dropbox-show']"))
						.isDisplayed()) {
					js.executeScript("document.getElementById('switchToClassicView').click();");
				}
				findElement(By.xpath("//div[@class='rb-viewalert-without-loader']//span[2]")).click();
				driver.switchTo().window(parent);
				findElement(By.xpath("//img[@id='rdp_ico_0']")).click();
				findElement(By.xpath("//a[contains(text(),'Open URL in browser')]")).click();
				// switchWindowHandles(parent, "Product Selection");
				getWorkingWindowTitle(parent);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

}
