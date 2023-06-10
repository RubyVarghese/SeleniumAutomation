package selenium_Commands;





import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITest;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LocatorsAndCommands {

	WebDriver driver;
	public void testInitialise(String browser) {
		if(browser.equals("Chrome")) {
			driver= new ChromeDriver();
		}else if(browser.equals("FireFox")){
			driver= new FirefoxDriver();
		}
		else if(browser.equals("Edge")) {
			driver= new EdgeDriver();
		}
		else
		{
			try
			{
				throw new Exception("Invalid Browser");
			}
			catch(Exception e) {
				throw new RuntimeException(e);
			}
		}
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	}
	@BeforeMethod
	public void setUp() {
		testInitialise("Chrome");
	}
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {
		if (result.getStatus() == ITestResult.FAILURE) {
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            File screenshot = takesScreenshot.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File("./Screenshots/" + result.getName() + ".png"));
        }
		//driver.close();
		driver.quit();
		
	}
	@Test
	public void TC_001_verifyObsquraTitle() {
		driver.get("https://selenium.obsqurazone.com/index.php");
		String actualTitle= driver.getTitle();
		System.out.println(actualTitle);
		String expectedTitle="Obsqura Testing1";
		Assert.assertEquals(actualTitle, expectedTitle ,"Invalid tilte found");
		
	}
	@Test
	public void TC_002_verifySingleInputFieldMessage() {
		driver.get("https://selenium.obsqurazone.com/simple-form-demo.php");
		WebElement messageField= driver.findElement(By.id("single-input-field"));
		messageField.sendKeys("test");
		WebElement showButton= driver.findElement(By.id("button-one"));
		showButton.click();
		WebElement message= driver.findElement(By.id("message-one"));
		String actmessagetext= message.getText();
		String expMessage="Your Message : test";
		Assert.assertEquals(actmessagetext, expMessage,"Invalid message");
	}
	@Test
	public void TC_003_verifyTwoInputFieldMessage(){
		driver.get("https://selenium.obsqurazone.com/simple-form-demo.php");
		WebElement inputfield1= driver.findElement(By.id("value-a"));
		inputfield1.sendKeys("25");
		WebElement inputfield2= driver.findElement(By.id("value-b"));
		inputfield2.sendKeys("30");
		WebElement getTotalButton= driver.findElement(By.id("button-two"));
		getTotalButton.click();
		WebElement buttonMessage= driver.findElement(By.id("message-two"));
		String actbuttonClick= buttonMessage.getText();
		String expectresultonclick="Total A + B : 55";
		Assert.assertEquals(actbuttonClick, expectresultonclick,"Invalid input");
	}
		@Test
		public void TC_004_verifyEmptyFieldValidation() 
		{	
		driver.get("https://selenium.obsqurazone.com/form-submit.php");
		String expectedfname="Please enter First name.";
		String expectedlname= "Please enter Last name.";
		String expectedUname= "Please choose a username.";
		String expectedCity= "Please provide a valid city.";
		String expectedState= "Please provide a valid state.";
		String expectedZipmsg= "Please provide a valid zip.";
		
		WebElement submitButton= driver.findElement(By.xpath("//button[@class='btn btn-primary']"));
		submitButton.click();
		WebElement firstNameValidationmsg= driver.findElement(By.xpath("//input[@id='validationCustom01']/following-sibling::div[1]"));
		String actualfnamemsg= firstNameValidationmsg.getText();
		Assert.assertEquals(actualfnamemsg, expectedfname,"Invalid FirstName");
		WebElement lastNamemsg= driver.findElement(By.xpath("//input[@id='validationCustom02']/following-sibling::div[1]"));
		String actuallastmsg= lastNamemsg.getText();
		Assert.assertEquals(actuallastmsg, expectedlname,"Invalid lastname");
		WebElement usernamemsg= driver.findElement(By.xpath("//input[@id='validationCustomUsername']/following-sibling::div[1]"));
		String actualUname= usernamemsg.getText();
		Assert.assertEquals(actualUname, expectedUname,"Invalid UserName ");
		WebElement cityValidationmsg =driver.findElement(By.xpath("//input[@id='validationCustom03']/following-sibling::div[1]"));
		String actualCitymsg= cityValidationmsg.getText();
		Assert.assertEquals(actualCitymsg, expectedCity,"Invalid city");
		WebElement statevalidation = driver.findElement(By.xpath("//input[@id='validationCustom04']//following-sibling::div[1]"));
		String actualStatemsg= statevalidation.getText();
		Assert.assertEquals(actualStatemsg, expectedState,"Invalid state");
		WebElement zipvalidation= driver.findElement(By.xpath("//input[@id='validationCustom05']//following-sibling::div[1]"));
		String actualzipmsg=zipvalidation.getText();
		Assert.assertEquals(actualzipmsg, expectedZipmsg,"Invalid zip");
		}
		
		@Test
		public void TC_005_submitFormwithoutTwofield() {
			driver.get("https://selenium.obsqurazone.com/form-submit.php");
			WebElement fname= driver.findElement(By.xpath("//input[@id='validationCustom01'and @class='form-control']"));
			fname.sendKeys("Ruby");
			
			WebElement lastname= driver.findElement(By.xpath("//input[@id='validationCustom02' and @class='form-control']"));
			lastname.sendKeys("Varghese");
			WebElement Uname= driver.findElement(By.xpath("//input[@id='validationCustomUsername' and @class='form-control']"));
			Uname.sendKeys("Rubyv");
			WebElement city=driver.findElement(By.xpath("//input[@id='validationCustom03' and @class='form-control']"));
			city.sendKeys("MVK");
			
			WebElement submitButton=driver.findElement(By.xpath("//button[@class='btn btn-primary']"));
			submitButton.click();
			String expectedState= "Please provide a valid state.";
			String expectedZipmsg= "Please provide a valid zip.";
			WebElement statevalidation = driver.findElement(By.xpath("//input[@id='validationCustom04']//following-sibling::div[1]"));
			String actualStatemsg= statevalidation.getText();
			Assert.assertEquals(actualStatemsg, expectedState,"Invalid state");
			WebElement zipvalidation= driver.findElement(By.xpath("//input[@id='validationCustom05']//following-sibling::div[1]"));
			String actualzipmsg=zipvalidation.getText();
			Assert.assertEquals(actualzipmsg, expectedZipmsg,"Invalid zip");
			
		}
		@Test
		public void Tc_006_verifyFormSubmission() {
			driver.get("https://selenium.obsqurazone.com/form-submit.php");
			WebElement fname = driver.findElement(By.xpath("//input[@id='validationCustom01']"));
			fname.sendKeys("Ruby");
			WebElement lastname= driver.findElement(By.xpath("//input[@id='validationCustom02']"));
			lastname.sendKeys("Varghese");
			WebElement Uname= driver.findElement(By.xpath("//input[@id='validationCustomUsername']"));
			Uname.sendKeys("RubyV");
			WebElement city= driver.findElement(By.xpath("//input[@id='validationCustom03']"));
			city.sendKeys("MVK");
			WebElement state= driver.findElement(By.xpath("//input[@id='validationCustom04']"));
			state.sendKeys("Kerala");
			WebElement Zip= driver.findElement(By.xpath("//input[@id=\"validationCustom05\"]"));
			Zip.sendKeys("689511");
			WebElement checkbox= driver.findElement(By.xpath("//input[@id='invalidCheck']"));
			checkbox.click();
			WebElement submitForm= driver.findElement(By.xpath("//button[@class='btn btn-primary']"));
			submitForm.click();
			String expectedmsg= "Form has been submitted successfully!";
			WebElement sucessMsg= driver.findElement(By.xpath("//div[@id='message-one']"));
			String actualMsg=sucessMsg.getText();
			Assert.assertEquals(actualMsg, expectedmsg,"Invalid message");
		}
		
		@Test
		public void Tc_007_verifyNewsLetterSubcription() {
			driver.get("https://demowebshop.tricentis.com");
			WebElement newsletterEmail= driver.findElement(By.cssSelector("input#newsletter-email"));
			newsletterEmail.sendKeys("ruby@gmail.com");
			WebElement submittButton= driver.findElement(By.cssSelector("input#newsletter-subscribe-button"));
			submittButton.click();
			String expectedMsg= "Thank you for signing up! A verification email has been sent. We appreciate your interest.";
			WebElement validationmsg= driver.findElement(By.cssSelector("div#newsletter-result-block"));
			String actualValdmsg=validationmsg.getText();
			Assert.assertEquals(actualValdmsg, expectedMsg,"Invalid Message ");
		}
		
		
		@Test
		public void TC_008_verifyInputvaluesandIntergerConversion() throws InterruptedException {
			driver.get("https://phptravels.com/demo/");
			WebElement fname= driver.findElement(By.cssSelector("input.first_name.input.mb1"));
			fname.sendKeys("Ruby");
			WebElement lastname= driver.findElement(By.cssSelector("input.last_name.input.mb1"));
			lastname.sendKeys("VARGHESE");
			WebElement Busname= driver.findElement(By.cssSelector("input.business_name.input.mb1"));
			Busname.sendKeys("ABC");
			WebElement email= driver.findElement(By.cssSelector("input.email.input.mb1"));
			email.sendKeys("ruby@yopmail.com");			
			WebElement num1= driver.findElement(By.cssSelector("span#numb1"));
			String tempnum1=num1.getText();
			WebElement num2=driver.findElement(By.cssSelector("span#numb2"));
			String tempnum2=num2.getText();
			int a=Integer.parseInt(tempnum1);
			int b= Integer.parseInt(tempnum2);
			int sum=a+b;
			String result=Integer.toString(sum);
			WebElement resulttextbox= driver.findElement(By.cssSelector("input#number"));
			resulttextbox.sendKeys(result);
			System.out.println(result);
			WebElement submitbutton= driver.findElement(By.cssSelector("button#demo"));
			submitbutton.click();
			WebElement completedboxmsg= driver.findElement(By.cssSelector("div.completed"));
			Thread.sleep(5000);
			boolean status= completedboxmsg.isDisplayed();
			System.out.println(status);		
		}	
		@Test
		public void TC_009_verifyQuitandClose() {
			driver.get("https://demo.guru99.com/popup.php");
			WebElement clickhereButton= driver.findElement(By.xpath("//a[text()='Click Here']"));
			clickhereButton.click();
		}
		@Test
		public void TC_10_verifyNavigateTo() {
			driver.navigate().to("https://demowebshop.tricentis.com");	
		}
		@Test
		public void TC_011_verifyRefresh(){
			driver.get("https://demowebshop.tricentis.com");
			WebElement newsletterFied= driver.findElement(By.xpath("//input[@id='newsletter-email']"));
			newsletterFied.sendKeys("ruby@yopmail.com");
			driver.navigate().refresh();	
		}
		@Test
		public void TC_012_verifyForwardAndBackwardNavigation() throws InterruptedException {
			driver.get("https://demowebshop.tricentis.com");
			WebElement loginlink=driver.findElement(By.xpath("//a[text()='Log in']"));
			loginlink.click();
			Thread.sleep(5000);
			driver.navigate().forward();
			Thread.sleep(5000);
			driver.navigate().back();
		}
		@Test 
		public void Tc_0014_verifyIsDisplayed(){
			driver.get("https://selenium.obsqurazone.com/ajax-form-submit.php");
			WebElement subject= driver.findElement(By.xpath("//input[@id='subject']"));
			subject.sendKeys("selenium");
			boolean status=subject.isDisplayed();
			System.out.println(status);
			Assert.assertTrue(status,"Subject field is not displayed");	
		}
		@Test
		public void TC_0015_verify() {
			driver.get("https://selenium.obsqurazone.com/check-box-demo.php");
			WebElement singlecheckbox= driver.findElement(By.xpath("//input[@id='gridCheck']"));
			boolean statusforclick=singlecheckbox.isSelected();
			System.out.println(statusforclick);
			Assert.assertFalse(statusforclick,"Checkbox is selected");
			singlecheckbox.click();
			boolean statusafterClick=singlecheckbox.isSelected();
			System.out.println(statusafterClick);
			Assert.assertTrue(statusafterClick,"Checkbox is not selected");	
		}
		@Test
		public void verifyIsenabled() {
			driver.get("https://selenium.obsqurazone.com/ajax-form-submit.php");
			WebElement submitbutton= driver.findElement(By.xpath("//input[@class='btn btn-primary']"));
			boolean status=submitbutton.isEnabled();
			System.out.println(status);
			Assert.assertTrue(status,"Submit button is not enabled");
			Point point=submitbutton.getLocation();
			System.out.println(point.x+","+point.y);
			Dimension dim=submitbutton.getSize();
			System.out.println(dim.height+","+dim.width);
			String bgcolor=submitbutton.getCssValue("background-color");
			System.out.println(bgcolor);
			WebElement element=driver.findElement(By.tagName("input"));
			System.out.println(element);
			List<WebElement> elements=driver.findElements(By.tagName("input"));
			System.out.println(elements);
		}
		@Test
		public void TC_016_verifytheMessageDisplayedInNewWindow() {
			driver.get("https://demoqa.com/browser-windows");
			String parentWindow= driver.getWindowHandle();
			System.out.println("parent window= "+parentWindow);
			WebElement newWindow= driver.findElement(By.xpath("//button[@id='windowButton']"));
			newWindow.click();
			Set<String> handles= driver.getWindowHandles();
			System.out.println(handles);
			Iterator<String>handleids=handles.iterator();
			while(handleids.hasNext()) {
				String childWindow=handleids.next();
				if(!childWindow.equals(parentWindow)) {
					driver.switchTo().window(childWindow);
					WebElement samplepage=driver.findElement(By.xpath("//h1[@id='sampleHeading']"));
					String actmsg=samplepage.getText();
					String expmsg= "This is a sample page";
					Assert.assertEquals(actmsg, expmsg,"Invalid Message ");
					//driver.close();
					
				}
					
			}
			driver.switchTo().window(parentWindow);
			
		}
		@Test
		public void TC_017_verifySimpleAlert() throws InterruptedException {
			driver.get("https://selenium.obsqurazone.com/javascript-alert.php");
			WebElement clickme= driver.findElement(By.xpath("//button[@class='btn btn-success']"));
			clickme.click();
			Thread.sleep(5000);
			Alert alert=driver.switchTo().alert();
			String alertText=alert.getText();
			alert.accept();
		}
		@Test
		public void TC_018_verifyConfirmAlert() {
			driver.get("https://selenium.obsqurazone.com/javascript-alert.php");
			WebElement clickme= driver.findElement(By.xpath("//button[@class='btn btn-warning']"));
			clickme.click();
			Alert alert= driver.switchTo().alert();
			String alertText= alert.getText();
			System.out.println(alertText);
			alert.dismiss();
		}
		@Test
		public void TC_019_verifyPromptAlert() throws InterruptedException {
			driver.get("https://selenium.obsqurazone.com/javascript-alert.php");
			WebElement clickforPrompt= driver.findElement(By.xpath("//button[@class='btn btn-danger']"));
			clickforPrompt.click();
			Alert alert= driver.switchTo().alert();
			alert.sendKeys("Ruby");
			String alertText=alert.getText();
			System.out.println(alertText);
			alert.accept();
			
		}
		@Test
		public void TC_020_verifyTextinaFrame() {
			driver.get("https://demoqa.com/frames");
			List<WebElement> frames=driver.findElements(By.tagName("iframe"));
			int numoFFrames= frames.size();
			System.out.println(numoFFrames);
			//driver.switchTo().frame(3);//Using Index to find 3rd frame in the webpage
			//driver.switchTo().frame("frame1");//Using name orID
			WebElement frame1= driver.findElement(By.id("frame1"));//Using web element
			driver.switchTo().frame(frame1);
			WebElement heading=driver.findElement(By.id("sampleHeading"));
			String headingText=heading.getText();
			System.out.println(headingText);
			driver.switchTo().parentFrame();
			//driver.switchTo().defaultContent();		
		}
		@Test
		public void TC_021_verifyRightClick() {
			driver.get("https://demo.guru99.com/test/simple_context_menu.html");
			WebElement rightclickMe= driver.findElement(By.xpath("//span[@class='context-menu-one btn btn-neutral']"));
			Actions action= new Actions(driver);
			action.contextClick(rightclickMe).build().perform();
		}
		@Test
		public void TC_022_verifyDoubleClick() {
			driver.get("https://demo.guru99.com/test/simple_context_menu.html");
			WebElement doubleClickMe= driver.findElement(By.xpath("//button[text()='Double-Click Me To See Alert']"));
			Actions action= new Actions(driver);
			action.doubleClick(doubleClickMe).build().perform();
			Alert alert= driver.switchTo().alert();
			alert.accept();
			
		}
		@Test
		public void TC_023_verifyMouseHover() {
			driver.get("https://demoqa.com/menu/");
			WebElement mainitem1=driver.findElement(By.xpath("//a[text()='Main Item 1']"));
			Actions action= new Actions(driver);
			action.moveToElement(mainitem1).build().perform();
			
		}
		@Test
		public void TC_025_verifyClickandHold() {
			driver.get("https://demoqa.com/resizable");
			WebElement dragme= driver.findElement(By.id("draggable"));
			WebElement dropme= driver.findElement(By.id("droppable"));
			Actions action=new Actions(driver);
			action.dragAndDrop(dragme, dropme).build().perform();
		}
		@Test
		public void TC_024_verifyDragandDrop() {
			driver.get("https://demoqa.com/droppable");
			WebElement resizable= driver.findElement(By.xpath("//div[text()='Resizable box,starting at 200*200.Min']"));
			Actions action=new Actions(driver);
			action.clickAndHold(resizable).build().perform();
			action.dragAndDropBy(resizable, 100, 100).build().perform();
		}
		@Test
		public void TC_026_verifyDropDown() {
			driver.get("https://demo.guru99.com/test/newtours/register.php");
			WebElement dropdownCountry=driver.findElement(By.xpath("//select[@name='country']"));
			Select select =new Select(dropdownCountry);
			select.selectByVisibleText("INDIA");
			//select.selectByIndex(23);
			//select.selectByValue("ICELAND");	
		}
		@Test
		public void TC_028_verifyFileUpload() {
			driver.get("https://demo.guru99.com/test/upload/");
			WebElement chooseFileupload= driver.findElement(By.id("uploadfile_0"));
			chooseFileupload.sendKeys("D:\\Selenium\\test.txt");
			WebElement acceptterms= driver.findElement(By.id("terms"));
			acceptterms.click();
			WebElement submit= driver.findElement(By.id("submitbutton"));
			submit.click();
		}
		@Test
		public void TC_029_verifyClickandSendKeysUsingJavascriptExecutor() {
			driver.get("https://demowebshop.tricentis.com/");
			JavascriptExecutor js= (JavascriptExecutor)driver;
			js.executeScript("document.getElementById('newsletter-email').value='test@test.com'");
			js.executeScript("document.getElementById('newsletter-subscribe-button').click()");	
		}
		@Test
		public void TC_030_verifyScrolldownOfaWebElement() {
			driver.get("https://demo.guru99.com/test/guru99home/");
			JavascriptExecutor js= (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,1000)");
			
		}
		@Test
		public void TC_031_verifyScrollIntoViewOfaWebElement() {
			driver.get("https://demo.guru99.com/test/guru99home/");
			WebElement linuxelement=driver.findElement(By.linkText("Linux"));
			JavascriptExecutor js=(JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView();",linuxelement);
		}
		@Test
		public void TC_032_verifyHorizontalScroll() {
			driver.get("https://demo.guru99.com/test/guru99home/");
			WebElement vBScriptElement=driver.findElement(By.linkText("VBScript"));
			JavascriptExecutor js=(JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView();",vBScriptElement);
			
		}
		@Test
		public void TC_033_verifyTable() throws IOException {
			driver.get("https://www.w3schools.com/html/html_tables.asp");
			List<WebElement> rowelements =driver.findElements(By.xpath("//table[@id='customers']//tbody//tr"));
			List<WebElement> columnelements =driver.findElements(By.xpath("//table[@id='customers']//tbody//td"));
			List<ArrayList<String>> actGridData=TableUtility.get_Dynamic_TwoDimension_TablElemnts(rowelements, columnelements);
			List<ArrayList<String>> expGridDate=ExcelUtility.excelDataReader("\\src\\test\\resources\\TestData.xlsx","Table");
			Assert.assertEquals(actGridData, expGridDate,"Invalid data found");
		}
		@Test
		public void TC_034_verifyTable2() throws IOException {
			driver.get("https://selenium.obsqurazone.com/table-sort-search.php");
			WebElement searchbutton= driver.findElement(By.xpath("//div[@id='dtBasicExample_filter']//input[@type='search']"));
			searchbutton.sendKeys("Caesar Vance");
			List<WebElement> rowelements= driver.findElements(By.xpath("//table[@id='dtBasicExample']//tr"));
			List<WebElement> columnelements =driver.findElements(By.xpath("//table[@id='dtBasicExample']//tr//td"));
			List<ArrayList<String>>actGridData=TableUtility.get_Dynamic_TwoDimension_ObscuraTablElemnts(rowelements, columnelements);
			System.out.println(actGridData);
			List<ArrayList<String>> expGridDate=ExcelUtility.excelDataReader("\\src\\test\\resources\\TestData.xlsx","Table3");
			System.out.println(expGridDate);
			Assert.assertEquals(actGridData, expGridDate,"Invalid data found");
				
		}
		@Test
		public void TC_035_verifyFileUploadUsingRobotClass() throws AWTException, InterruptedException {
			driver.get("https://www.foundit.in/seeker/registration");
			StringSelection s = new StringSelection("D:\\Selenium\\testdoc.docx");
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);
			WebElement uploadFile=driver.findElement(By.xpath("//div[@class='uploadResume']"));
			uploadFile.click();
			Robot r=new Robot();
			r.keyPress(KeyEvent.VK_ENTER);
			r.keyRelease(KeyEvent.VK_ENTER);
			Thread.sleep(2000);
			r.keyPress(KeyEvent.VK_CONTROL);
			r.keyPress(KeyEvent.VK_V);
			r.keyRelease(KeyEvent.VK_V);
			r.keyRelease(KeyEvent.VK_CONTROL);
			Thread.sleep(2000);
			r.keyPress(KeyEvent.VK_ENTER);
			r.keyRelease(KeyEvent.VK_ENTER);
		}
		@Test
		public void TC_036_verifyWaitinSelenium() {
			driver.get("https://demowebshop.tricentis.com/");
			/*pageload wait*/
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
			/*implicit wait*/
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			WebElement email= driver.findElement(By.xpath("//input[@id='newsletter-email']"));
			email.sendKeys("test@gmail.com");
			/*explicit wait*/
			WebElement subscribeButton= driver.findElement(By.xpath("//input[@id='newsletter-subscribe-button']"));
			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(subscribeButton));
			/*fluentwait*/
			FluentWait fwait=new FluentWait<WebDriver>(driver);
			fwait.withTimeout(Duration.ofSeconds(10));
			fwait.pollingEvery(Duration.ofSeconds(1));
			fwait.until(ExpectedConditions.visibilityOf(subscribeButton));
			subscribeButton.click();
		
		}
		
		
		
			
		
		
}
