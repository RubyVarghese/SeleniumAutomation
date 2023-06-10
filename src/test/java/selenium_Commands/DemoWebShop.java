package selenium_Commands;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DemoWebShop {
	WebDriver driver;

	public void testInitialise(String browser) {
		if (browser.equals("Chrome")) {
			driver = new ChromeDriver();
		} else if (browser.equals("FireFox")) {
			driver = new FirefoxDriver();
		} else if (browser.equals("Edge")) {
			driver = new EdgeDriver();
		} else {
			try {
				throw new Exception("Invalid Browser");
			} catch (Exception e) {
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
		// driver.close();
		driver.quit();

	}

	@Test
	public void TC_001_verifyObsquraTitle() {
		driver.get("https://selenium.obsqurazone.com/index.php");
		String actualTitle = driver.getTitle();
		System.out.println(actualTitle);
		String expectedTitle = "Obsqura Testing";
		Assert.assertEquals(actualTitle, expectedTitle, "Invalid tilte found");
	}

	@Test
	public void TC_002_verifyLogin() {
		driver.get("https://demowebshop.tricentis.com/");
		WebElement login = driver.findElement(By.xpath("//a[text()='Log in']"));
		login.click();
		String email = "ruby123@yopmail.com";
		WebElement emailid = driver.findElement(By.xpath("//input[@id='Email']"));
		emailid.sendKeys(email);
		WebElement password = driver.findElement(By.xpath("//input[@id='Password']"));
		password.sendKeys("ruby123");
		WebElement Submitbutton = driver.findElement(By.xpath("//input[@class='button-1 login-button']"));
		Submitbutton.click();
		WebElement useracntemail = driver.findElement(By.xpath("//div[@class='header-links']//a[@class='account']"));
		String acttxt = useracntemail.getText();
		System.out.println(acttxt);
		Assert.assertEquals(email, acttxt, "Invalid userlogin");

	}

	@Test
	public void TC_003_verifyRegistration() {
		driver.get("https://demowebshop.tricentis.com/");
		WebElement register = driver.findElement(By.xpath("//a[text()='Register']"));
		register.click();
		List<WebElement> gender = driver.findElements(By.xpath("//input[@name='Gender']"));
		selectGender("F", gender);
		WebElement firstName = driver.findElement(By.xpath("//input[@id='FirstName']"));
		firstName.sendKeys("Ruby");
		WebElement lastName = driver.findElement(By.xpath("//input[@id='LastName']"));
		lastName.sendKeys("V");
		String email = "ruby91@yopmail.com";
		WebElement emailid = driver.findElement(By.xpath("//input[@id='Email']"));
		emailid.sendKeys(email);
		WebElement password = driver.findElement(By.xpath("//input[@id='Password']"));
		password.sendKeys("ruby123");
		WebElement confirmPassword = driver.findElement(By.xpath("//input[@id='ConfirmPassword']"));
		confirmPassword.sendKeys("ruby123");
		WebElement submitButton = driver.findElement(By.xpath("//input[@id='register-button']"));
		submitButton.click();
		WebElement successfulMsg = driver.findElement(By.xpath("//div[@class='result']"));
		String actualMsg = successfulMsg.getText();
		String expectedMsg = "Your registration completed";
		Assert.assertEquals(actualMsg, expectedMsg, "Invalid message");
		WebElement continueButton = driver.findElement(By.xpath("//input[@class='button-1 register-continue-button']"));
		continueButton.click();
		WebElement actUserAcnt = driver.findElement(By.xpath("//div[@class='header-links']//a[@class='account']"));
		String userAccount = actUserAcnt.getText();
		Assert.assertEquals(email, userAccount, "Invalid User Account");
	}

	public void selectGender(String gen, List<WebElement> gender) {
		for (int i = 0; i < gender.size(); i++) {
			String genderValue = gender.get(i).getAttribute("value");
			if (genderValue.equals(gen)) {
				gender.get(i).click();
			}
		}

	}
	@Test
	public void TC_004_verifyTitleFromExcelSheet() throws IOException {
		driver.get("https://demowebshop.tricentis.com/");
		String actualTitle=driver.getTitle();
		String excelPath="\\src\\test\\resources\\TestData.xlsx";
		String sheetName="Homepage";
		String expTitle=ExcelUtility.readStringData(excelPath, sheetName, 0, 1);
		Assert.assertEquals(actualTitle,expTitle,"Invalid Title");
	}
	@Test
	public void TC_005_verifyRegistrationFromExcelSheet() throws IOException {
		driver.get("https://demowebshop.tricentis.com/");
		String excelPath="\\src\\test\\resources\\TestData.xlsx";
		String sheetName="Register";
		String expTitle=ExcelUtility.readStringData(excelPath, sheetName, 0, 1);
		WebElement register = driver.findElement(By.xpath("//a[text()='Register']"));
		register.click();
		String actualTitle=driver.getTitle();
		List<WebElement> gender=driver.findElements(By.xpath("//input[@name='Gender']"));
		selectGender(ExcelUtility.readStringData(excelPath, sheetName, 6, 1),gender);
		Assert.assertEquals(actualTitle,expTitle,"Invalid Title");
		WebElement firstName = driver.findElement(By.xpath("//input[@id='FirstName']"));
		firstName.sendKeys(ExcelUtility.readStringData(excelPath, sheetName, 1, 1));
		WebElement lastName = driver.findElement(By.xpath("//input[@id='LastName']"));
		lastName.sendKeys(ExcelUtility.readStringData(excelPath, sheetName, 2, 1));
		WebElement emailid = driver.findElement(By.xpath("//input[@id='Email']"));
		emailid.sendKeys(ExcelUtility.readStringData(excelPath, sheetName, 3, 1));
		WebElement password = driver.findElement(By.xpath("//input[@id='Password']"));
		password.sendKeys(ExcelUtility.readStringData(excelPath, sheetName, 4, 1));
		WebElement confirmPassword = driver.findElement(By.xpath("//input[@id='ConfirmPassword']"));
		confirmPassword.sendKeys(ExcelUtility.readStringData(excelPath, sheetName, 5, 1));
		WebElement submitButton = driver.findElement(By.xpath("//input[@id='register-button']"));
		submitButton.click();
		WebElement actUserAcnt = driver.findElement(By.xpath("//div[@class='header-links']//a[@class='account']"));
		String userAccount = actUserAcnt.getText();
		Assert.assertEquals(userAccount, ExcelUtility.readStringData(excelPath, sheetName, 3, 1), "Invalid User Account");
	}
	@Test(dataProvider="InvalidCredentials")
	public void TC_006_verifyLoginwithInvalidDatas(String email, String psword) {
		driver.get("https://demowebshop.tricentis.com/");
		WebElement login = driver.findElement(By.xpath("//a[text()='Log in']"));
		login.click();
		WebElement emailid = driver.findElement(By.xpath("//input[@id='Email']"));
		emailid.sendKeys(email);
		WebElement password = driver.findElement(By.xpath("//input[@id='Password']"));
		password.sendKeys(psword);
		WebElement Submitbutton = driver.findElement(By.xpath("//input[@class='button-1 login-button']"));
		Submitbutton.click();
		WebElement errorMsg = driver.findElement(By.xpath("//div[@class='validation-summary-errors']//span"));
		String acttxt = errorMsg.getText();
		String expMsg="Login was unsuccessful. Please correct the errors and try again.";
		Assert.assertEquals( acttxt,expMsg, "Invalid userlogin");	
	}
	@DataProvider(name="InvalidCredentials")
	public Object[][] userCredentials() {
		Object[][] data= {{"ruby123@yopmail.com","ruby128"},{"ruby1@yopmail.com","ruby123"},{"ruby1@yopmail.com","ruby001"}};
		return data;
	}
	@Test
	public void TC_007_verifyRegistrationUsingRandomGenerator() {
		driver.get("https://demowebshop.tricentis.com/");
		WebElement register = driver.findElement(By.xpath("//a[text()='Register']"));
		register.click();
		String fName= RandomDataUtility.getfName();
		String lName=RandomDataUtility.getlName();
		String email= RandomDataUtility.getRandomEmail();
		String psword= fName+"@123";
		List<WebElement> gender = driver.findElements(By.xpath("//input[@name='Gender']"));
		selectGender("F", gender);
		WebElement firstName = driver.findElement(By.xpath("//input[@id='FirstName']"));
		firstName.sendKeys(fName);
		WebElement lastName = driver.findElement(By.xpath("//input[@id='LastName']"));
		lastName.sendKeys(lName);
		WebElement emailid = driver.findElement(By.xpath("//input[@id='Email']"));
		emailid.sendKeys(email);
		WebElement password = driver.findElement(By.xpath("//input[@id='Password']"));
		password.sendKeys(psword);
		WebElement confirmPassword = driver.findElement(By.xpath("//input[@id='ConfirmPassword']"));
		confirmPassword.sendKeys(psword);
		WebElement submitButton = driver.findElement(By.xpath("//input[@id='register-button']"));
		submitButton.click();
		WebElement successfulMsg = driver.findElement(By.xpath("//div[@class='result']"));
		String actualMsg = successfulMsg.getText();
		String expectedMsg = "Your registration completed";
		Assert.assertEquals(actualMsg, expectedMsg, "Invalid message");
		WebElement continueButton = driver.findElement(By.xpath("//input[@class='button-1 register-continue-button']"));
		continueButton.click();
		WebElement actUserAcnt = driver.findElement(By.xpath("//div[@class='header-links']//a[@class='account']"));
		String userAccount = actUserAcnt.getText();
		Assert.assertEquals(email, userAccount, "Invalid User Account");	
	}
	@Test(dataProvider="ValidCredentials")
	public void TC_008_verifyLoginwithValidCredentials(String email, String psword) {
		driver.get("https://demowebshop.tricentis.com/");
		WebElement loginLink=driver.findElement(By.xpath("//a[@class='ico-login']"));
		loginLink.click();
		WebElement emailid = driver.findElement(By.xpath("//input[@id='Email']"));
		emailid.sendKeys(email);
		WebElement password = driver.findElement(By.xpath("//input[@id='Password']"));
		password.sendKeys(psword);
		WebElement Submitbutton = driver.findElement(By.xpath("//input[@class='button-1 login-button']"));
		Submitbutton.click();
		WebElement Useracntemail=driver.findElement(By.xpath("//div[@class='header-links']//a[@class='account']"));
		String actualUserAcnt=Useracntemail.getText();
		Assert.assertEquals(email, actualUserAcnt,"Invalid Useraccount");	
	}
	@DataProvider(name="ValidCredentials")
	public Object[][] validuserCredentials() {
		Object[][] data= {{"ruby123@yopmail.com","ruby123"},{"ruby91@yopmail.com","ruby123"}};
		return data;
	}	
	
}
