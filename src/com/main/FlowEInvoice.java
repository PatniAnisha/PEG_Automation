package com.main;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import DataProviders.eInvoice_DataProviderTestNG;
import Framework.ConfigurationProperties;
import Framework.FrameworkUtility;
import SanityDefault.Login;
import common.Functions.eInvoice_CommonFunctions;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.zycus.eInvoice.Approval.Approval;
import com.zycus.eInvoice.Approval.MySettings;
import com.zycus.eInvoice.Invoice.Invoices;
import com.zycus.eInvoice.PO.PurchaseOrder;
import com.zycus.eInvoice.Payment.Batches;
import com.zycus.eInvoice.Payment.NewPaymentBatch;
import com.zycus.eInvoice.Reports.ReportDetail;
import com.zycus.eInvoice.Reports.Reports;
import com.zycus.eInvoice.eForms.AllForms;
import com.zycus.eInvoice.eForms.FormWizard;
import com.zycus.eInvoice.Reconciliation.ReconcileNewStatement;
import com.zycus.eInvoice.Reconciliation.Statements;
import com.zycus.eInvoice.Uploads.Uploads;
import com.zycus.eInvoice.Workflow.Workflow;
import com.zycus.eInvoice.Workflow.WorkflowWizard;

public class FlowEInvoice {

	private WebDriver driver;
	ExtentReports extent;
	ExtentTest logger;
	FrameworkUtility objFrameworkUtility = new FrameworkUtility();
	eInvoice_CommonFunctions objFunctions = new eInvoice_CommonFunctions(driver, logger);
	ConfigurationProperties configurationProperties = ConfigurationProperties.getInstance();
	private String Customer;
	private String invoiceNo = null;

	public FlowEInvoice() throws Exception {
		super();
	}

	@BeforeTest
	public void startReport() throws Exception {
		this.driver = objFrameworkUtility.getWebDriverInstance(
				System.getProperty("user.dir") + configurationProperties.getProperty("chromedriverpath"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		extent = new ExtentReports(System.getProperty("user.dir") + configurationProperties.getProperty("reportpath")
				+ "//Execution_Report_eInvoice " + sdf.format(timestamp) + ".html", false);
		extent.loadConfig(new File(System.getProperty("user.dir") + "/config/extent-config.xml"));
		/*
		 * modifiedExtent = new ModifiedExtentReport(extent,
		 * configurationProperties.getProperty("reportpath") +
		 * "//Execution_Report_ZSN.html");
		 */
		objFunctions = new eInvoice_CommonFunctions(driver, logger);
		//eInvoice_CommonFunctions objCommon = new eInvoice_CommonFunctions(driver, logger);
	}

	/**
	 * @param Product
	 * @param Username
	 * @param Password
	 * @param Customer
	 * @throws Exception
	 */
	@Test(dataProviderClass = eInvoice_DataProviderTestNG.class, dataProvider = "Login", priority = 1, alwaysRun = true)
	public void Login(String Product, String Username, String Password, String Customer, String userAccount) throws Exception {
		this.Customer = Customer;
		logger = extent.startTest("Login");
		Login objLogin = new Login(driver, logger, Product, Username, Password, Customer, userAccount);
		callAndLog(objLogin.Login_via_PwdMgr(configurationProperties), "login successful", "Not logged in");
	}
	
	/*@Test(dataProviderClass = eInvoice_DataProviderTestNG.class, dependsOnMethods = "Login", dataProvider = "Invoices", alwaysRun = true, priority = 2)
	public void Invoices(String DocStatus, String DocNo, String DocType, String DocDateFrom, String DocDateTo,
			String DueDateFrom, String DueDateTo, String FromAmtRange, String ToAmtRange, String Currency,
			String Reference, String Supplier, String NavigatetoPageNo, String RecordsperPage) throws Exception {

		Invoices objInvoice = new Invoices(driver, logger);
		objFunctions.navigate_path("Invoice", "Invoices");
		callAndLog(objInvoice.changeNoOfRecordsPerPage(Integer.valueOf(RecordsperPage)), "able to change no of records",
				"unable to change no of records");

		callAndLog(objInvoice.navigateToPageNo(NavigatetoPageNo), "able to navigate to another page",
				"unable to navigate to another page");

		callAndLog(objInvoice.saveViewAsFavorite(), "able to save ViewAsFavorite", "unable to save ViewAsFavorite");
		callAndLog(objInvoice.revertToDefaultView(), "able to revertToDefaultView", "unable to revertToDefaultView");
		 
		objFunctions.clrAllFilters();
		callAndLog(objInvoice.filterByStatus(DocStatus), "able to filter on doc status " + DocStatus,
				"unable to filter on doc status" + DocStatus);

		objFunctions.clrAllFilters();
		callAndLog(objInvoice.filterByDocType(DocType), "able to filter on doc type " + DocType,
				"unable to filter on doc type" + DocType);

		objFunctions.clrAllFilters();
		callAndLog(objInvoice.filterByReference(Reference, ""), "able to filter on Reference " + Reference,
				"unable to filter on Reference " + Reference);

		objFunctions.clrAllFilters();
		callAndLog(objInvoice.filterBySupplier(Supplier), "able to search supplier" + Supplier,
				"unable to search supplier" + Supplier);

		objFunctions.clrAllFilters();
		callAndLog(objInvoice.filterByAmount(Float.valueOf(FromAmtRange), Float.valueOf(ToAmtRange), Currency),
				"able to filter on amount range", "able to filter on amount range");
		// TODO Need to add Document No
		objFunctions.clrAllFilters();
		callAndLog(
				objInvoice.filterByDocumentDt(new SimpleDateFormat("dd/MM/yyyy").parse(DocDateFrom),
						new SimpleDateFormat("dd/MM/yyyy").parse(DocDateTo)),
				"able to filter on doc date", "unable to filter on doc date");
		objFunctions.clrAllFilters();
		callAndLog(
				objInvoice.filterByDueDt(new SimpleDateFormat("dd/MM/yyyy").parse(DueDateFrom),
						new SimpleDateFormat("dd/MM/yyyy").parse(DueDateTo)),
				"able to filter on Due Date", "unable to filter on Due Date");

	}
	
	 @Test(alwaysRun = true, dependsOnMethods = "Login",priority=3)
		public void ApprovalAction() throws Exception {
			logger = extent.startTest("Approve|Reject|Delegate an invoice");
			objFunctions.navigate_path("Approval", "All Requests");
			objFunctions.clrAllFilters();
			Approval objApproval = new Approval(driver, logger);
			objApproval.filterByStatus("Pending");
			Thread.sleep(3000);
			objApproval.performActionOnInvoice("Approve");
			Thread.sleep(3000);
			objApproval.performActionOnInvoice("Reject");
			Thread.sleep(3000);
			objApproval.performActionOnInvoice("Delegate");

		}
	 
		@Test(dataProviderClass = eInvoice_DataProviderTestNG.class, dataProvider = "InvoiceNonPO", alwaysRun = true, dependsOnMethods = "Login", priority = 4)
		public void InvoiceNonPO(String supplierName, String paymentTerm, String currency_value, String invoiceDate,
				String purchaseType,String description, String product_cat, String market_prc,
				String quantity, String GLType) throws Exception {
			logger = extent.startTest("Invoice Non PO");
			Invoices objInvoice = new Invoices(driver, logger, supplierName, paymentTerm, currency_value, invoiceDate,
					purchaseType, description, product_cat, market_prc, quantity, GLType);
			objFunctions.navigate_path("Invoice", "Invoices");
			callAndLog(objInvoice.createInvoiceNonPO(), "able to create invoice non PO", "unable to create invoice non PO");

		}*/
	
	@Test(alwaysRun = true, dependsOnMethods = "Login",priority=5)
	public void InvoiceAction() throws Exception {
		logger = extent.startTest("Perform action on Invoice");
		objFunctions.navigate_path("Invoice", "Invoices");
		Invoices objInvoice = new Invoices(driver, logger);
		callAndLog(objInvoice.editInvoice(), "able to edit the invoice", "unable to edit the invoice");
		
		objFunctions.navigate_path("Invoice", "Invoices");
		objInvoice.filterByStatus("Approved");
		callAndLog(objInvoice.takeActionOnInvoice("Close"), "able to close invoice", "unable to close invoice");
		callAndLog(objInvoice.takeActionOnInvoice("Void Invoice"), "able to Void invoice", "unable to Void invoice");
		callAndLog(objInvoice.takeActionOnInvoice("Return"), "able to Return invoice", "unable to Return invoice");
		// callAndLog(objInvoice.takeActionOnInvoice("Restrict Payment"),"able
		// to restrict payment for invoice","unable to restrict payment for
		// invoice");

		// TODO Adjust credit, Restrict Payment pending - check with Hinal

	}
	
	
	
	
	/*@Test(dataProviderClass = eInvoice_DataProviderTestNG.class, dependsOnMethods = "Login", dataProvider = "PurchaseOrders", alwaysRun = true,priority=6)
	public void searchPurchaseOrders(String PONumber, String SupplierName, String BuyerName) throws Exception {
		logger = extent.startTest("Search PO based on PONumber,Supplier Name and Buyer Name");
		objFunctions.navigate_path("PO");
		PurchaseOrder objPurchaseOrder = new PurchaseOrder(driver, logger);
		// objPurchaseOrder.addInvoicebyHoveringPO();

		callAndLog(objPurchaseOrder.filterByPONumber(PONumber), "able to search by PO number",
				"unable to search by PO number");
		objFunctions.clrAllFilters();
		Thread.sleep(3000);

		callAndLog(objPurchaseOrder.filterBySupplier(SupplierName), "able to search by supplier name",
				"unable to search by supplier name");
		objFunctions.clrAllFilters();
		Thread.sleep(3000);

		callAndLog(objPurchaseOrder.filterByBuyer(BuyerName), "able to search by buyer name",
				"unable to search by buyer name");
		objFunctions.clrAllFilters();
		Thread.sleep(3000);
		callAndLog(objPurchaseOrder.downloadPO(), "able to download PO", "unable to download PO");
	}
	
	
	@Test(alwaysRun = true, dependsOnMethods = "Login",priority=7)
	public void ConfigureOOO() throws Exception {
		logger = extent.startTest("ConfigureOOO");
		objFunctions.navigate_path("Approval", "My Settings");
		MySettings objSetting = new MySettings(driver, logger);
		callAndLog(objSetting.configureOOO(), "able to configure OOO", "unable to configure OOO");

	}
	
	@Test(dataProviderClass = eInvoice_DataProviderTestNG.class, dependsOnMethods = "Login", dataProvider = "CreditMemowithoutReference", priority = 8)
	public void CreditMemowithoutReference(String supplierName, String currency_value, String creditMemoDate,
			String purchaseType, String description, String product_cat, String market_prc,
			String quantity, String GLType) throws Exception {

		logger = extent.startTest("CreditMemo without Reference");
		Invoices objInvoice = new Invoices(driver, logger, supplierName, currency_value, creditMemoDate, purchaseType,
				 description, product_cat, market_prc, quantity, GLType);
		objFunctions.navigate_path("Invoice", "Invoices");
		callAndLog(objInvoice.createCreditMemowithoutReference(), "able to create CreditMemowithoutReference",
				"unable to create CreditMemowithoutReference");
	}
	
	@Test(alwaysRun = true, dependsOnMethods = "Login", priority = 9)
	public void CreditMemoagainstPO() throws Exception {
		logger = extent.startTest("CreditMemo against PO");
		Invoices objInvoice = new Invoices(driver, logger);
		objFunctions.navigate_path("Invoice", "Invoices");
		callAndLog(objInvoice.createCreditMemoagainstPO(), "able to create creditMemo against PO",
				"unable to create creditMemo against PO");
	}
	
	@Test(alwaysRun = true, dependsOnMethods = "Login", priority = 10)
	public void InvoiceagainstPO() throws Exception {
		logger = extent.startTest("Invoice against PO");
		Invoices objInvoice = new Invoices(driver, logger);
		objFunctions.navigate_path("Invoice", "Invoices");
		callAndLog(objInvoice.createInvoiceagainstPO(), "able to create invoice against PO",
				"unable to create invoice against PO");
	}
	
	@Test(dataProviderClass = eInvoice_DataProviderTestNG.class, dependsOnMethods = "Login", dataProvider = "Reconciliation", alwaysRun = true, priority = 11)
	public void Statements(String tabToNavigate, String batchName, String bankName, String statementDate, String actionOnStatement) throws Exception {
		logger = extent.startTest("Statements");
		eInvoice_CommonFunctions objCommon = new eInvoice_CommonFunctions(driver, logger);
		objCommon.navigate_path(tabToNavigate);
		Statements objStatements = new Statements(driver, logger);
		if(objStatements.searchBatchName(batchName))
			logger.log(LogStatus.INFO, "Batch Name searched successfully");
		if(objStatements.searchBank(bankName))
			logger.log(LogStatus.INFO, "Bank Name searched successfully");
		if(objStatements.takeAction(actionOnStatement))
			logger.log(LogStatus.INFO, "Report reviewed successfully");	
	}
	
	@Test(dataProviderClass = eInvoice_DataProviderTestNG.class, dependsOnMethods = "Login", dataProvider = "Reconciliation", alwaysRun = true, priority = 12)
	public void AddReconciliationStatements(String tabToNavigate, String batchName, String bankName, String statementDate, String actionOnStatement) throws Exception {
		logger = extent.startTest("Add Reconciliation Statements");
		eInvoice_CommonFunctions objCommon = new eInvoice_CommonFunctions(driver, logger);
		objCommon.navigate_path(tabToNavigate);
		Statements objStatements = new Statements(driver, logger);
		if(objStatements.addNewStmt()){
			ReconcileNewStatement objReconStmt = new ReconcileNewStatement(driver, logger,batchName, bankName, statementDate); 
			ConfigurationProperties config = ConfigurationProperties.getInstance();
			if(objReconStmt.createNewStmt(true, System.getProperty("user.dir")+config.getProperty("upload_csv_path")))
					logger.log(LogStatus.INFO, "New Statement created successfully");
		}
	}

	@Test(dataProviderClass = eInvoice_DataProviderTestNG.class, dependsOnMethods = "Login", dataProvider = "Uploads", alwaysRun = true, priority = 13)
	public void Uploads(String tabToNavigate) throws Exception {
		logger = extent.startTest("Uploads");
		eInvoice_CommonFunctions objCommon = new eInvoice_CommonFunctions(driver, logger);
		objCommon.navigate_path(tabToNavigate);
		Uploads objUploads = new Uploads(driver, logger);
		ConfigurationProperties config = ConfigurationProperties.getInstance();
		if(objUploads.uploadNewFile(System.getProperty("user.dir")+config.getProperty("upload_Jpgfile_path")))
			logger.log(LogStatus.PASS, "File uploaded successfully");
		else
			logger.log(LogStatus.FAIL, "File not uploaded successfully");
	}
	

	
	@Test(dataProviderClass = eInvoice_DataProviderTestNG.class, dependsOnMethods = "Login", dataProvider = "Batches", alwaysRun = true, priority = 14)
	public void Batches(String tabToNavigate, String subTabToNavigate, String batchNo, String createdBy, String bankAcct) throws Exception {
		logger = extent.startTest("Batches");
		eInvoice_CommonFunctions objCommon = new eInvoice_CommonFunctions(driver, logger);
		objCommon.navigate_path(tabToNavigate, subTabToNavigate);
		Batches objbatches = new Batches(driver, logger);
		if(objbatches.searchBatchNo(batchNo))
			logger.log(LogStatus.INFO, "Batch Number searched successfully");
		if(objbatches.searchCreatedBy(createdBy))
			logger.log(LogStatus.INFO, "Created By searched successfully");
		if(objbatches.searchBankAccount(bankAcct))
			logger.log(LogStatus.INFO, "Bank Account searched successfully");
	}
	
	@Test(dataProviderClass = eInvoice_DataProviderTestNG.class, dependsOnMethods = "Login", dataProvider = "MyReports", alwaysRun = true, priority = 15)
	public void shareMyReports(String tabToNavigate, String myReportsName, String sharedUserEmail) throws Exception {
		logger = extent.startTest("Share My Reports");
		eInvoice_CommonFunctions objCommon = new eInvoice_CommonFunctions(driver, logger);
		objCommon.navigate_path(tabToNavigate);
		Reports objReports = new Reports(driver, logger);
		if(objReports.selectMyReports(myReportsName))
			logger.log(LogStatus.INFO, "My Report searched & viewed successfully");
		ReportDetail objRepDetail = new ReportDetail(driver, logger);
		if(objRepDetail.shareMyReports(myReportsName, sharedUserEmail));
			logger.log(LogStatus.INFO, "My Report shared successfully");
	}
	
	@Test(dataProviderClass = eInvoice_DataProviderTestNG.class, dependsOnMethods = "Login", dataProvider = "NewBatch", alwaysRun = true, priority = 16)
	public void NewBatch(String tabToNavigate, String subTabToNavigate, String organizationUnit, String bankAcct,
			String approver, String notes, String reviewer) throws Exception {
		logger = extent.startTest("New Batch Creation");
		eInvoice_CommonFunctions objCommon = new eInvoice_CommonFunctions(driver, logger);
		objCommon.navigate_path(tabToNavigate, subTabToNavigate);
		Batches objBatches = new Batches(driver, logger);
		if(objBatches.createNewBatch()){
			NewPaymentBatch objNewPayment = new NewPaymentBatch(driver, logger, organizationUnit, bankAcct);
			objNewPayment.enterBatchDetails(approver, notes, reviewer);
		}
	}
		
	
	
		
	@Test(dataProviderClass = eInvoice_DataProviderTestNG.class, dependsOnMethods = "Login", dataProvider = "ProcessForm", alwaysRun = true, priority = 17)
	public void createProcessForm(String tabToNavigate, String subTabToNavigate, String formCreationProcess, String formName, String formType,
			String formRelatedProcess, String businessUnit, String sectionName, String fieldToDisplayInSection, String fieldName,
			String formDescription, String sectionDescription, String sectionLayout, String defaultValue, String maxChar, String hideField, String enterSpace, String enterSplChar,
			String mandatory) throws Exception {
		logger = extent.startTest("Create Process Form");
		eInvoice_CommonFunctions objCommon = new eInvoice_CommonFunctions(driver, logger);
		objCommon.navigate_path(tabToNavigate, subTabToNavigate);
		AllForms objAllForms = new AllForms(driver, logger);
		objAllForms.selectNewFormCreationProcess(formCreationProcess);
		FormWizard objWizard = new FormWizard(driver, logger, formName, formType, formRelatedProcess,
				businessUnit, sectionName, fieldToDisplayInSection, fieldName, "Auto_display_Name"); 
		objWizard.createNewForm(formDescription, sectionDescription, sectionLayout, defaultValue, Integer.parseInt(maxChar), Boolean.parseBoolean(hideField), Boolean.parseBoolean(enterSpace), Boolean.parseBoolean(enterSplChar), Boolean.parseBoolean(mandatory));
	}
	
		
	@Test(dataProviderClass = eInvoice_DataProviderTestNG.class, dependsOnMethods = "Login", dataProvider = "Reports", alwaysRun = true, priority = 18)
	public void Reports(String tabToNavigate, String reportType, String reportName) throws Exception {
		logger = extent.startTest("Reports");
		eInvoice_CommonFunctions objCommon = new eInvoice_CommonFunctions(driver, logger);
		objCommon.navigate_path(tabToNavigate);
		Reports objReports = new Reports(driver, logger);
		if(objReports.searchReport(reportType, reportName)){
			logger.log(LogStatus.INFO, "Report searched successfully");
			if(objReports.viewReportDetails(reportName)){
				logger.log(LogStatus.INFO, "Report viewed successfully");
				ReportDetail objRepDetail = new ReportDetail(driver, logger);
				objRepDetail.closeReportDetails();
			}
		}
	}

	
	@Test(dataProviderClass = eInvoice_DataProviderTestNG.class, dependsOnMethods = "Login", dataProvider = "Workflow", alwaysRun = true, priority = 19)
	public void Workflow(String tabToNavigate, String workflowProcess, String workflowDescription, String workflowType, String approveMoreThanOnce) throws Exception {
		logger = extent.startTest("Workflow");
		eInvoice_CommonFunctions objCommon = new eInvoice_CommonFunctions(driver, logger);
		objCommon.navigate_path(tabToNavigate);
		Workflow objWorkflow = new Workflow(driver, logger);
		objWorkflow.clickCreateWorkflow();
		WorkflowWizard objWizard = new WorkflowWizard(driver, logger, workflowProcess);
		objWizard.createWorkflow(workflowDescription, workflowType, Boolean.parseBoolean(approveMoreThanOnce));
	}*/
	

	@AfterMethod
	public void getResult(ITestResult result) {

		if (result.getStatus() == ITestResult.FAILURE) {
			logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getName());
			logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getThrowable());
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(LogStatus.SKIP, "Test Case Skipped is " + result.getName());
		}
		extent.endTest(logger);
	}

	@AfterTest
	public void tearDown() throws Exception {
		extent.flush();
		extent.close();
		// driver.quit();
	}

	/**
	 * call and log
	 * 
	 * @throws Exception
	 */
	public void callAndLog(boolean condition, String passMsg, String failMsg) throws Exception {

		String msg = condition ? passMsg : failMsg;
		if (condition)
			logger.log(LogStatus.PASS, msg);
		else {
			String screenshotPath = objFunctions.getScreenhot(driver, "screenshot_error");
			logger.log(LogStatus.FAIL, msg + logger.addScreenCapture(screenshotPath));
		}
	}

}
