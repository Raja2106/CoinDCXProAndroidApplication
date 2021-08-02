package tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.common.collect.Table.Cell;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.offset.PointOption;

public class MainClass {

	static String coinType,firstName,lastName,email,phNo,password,country,referralCode;
	AndroidDriver driver;
	@BeforeSuite
	public void setup() throws MalformedURLException, InterruptedException	{

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
		capabilities.setCapability("deviceName", "OnePlus 6T");
		capabilities.setCapability("platformVersion", "10");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("appPackage", "com.coindcx");
		capabilities.setCapability("appActivity", "com.coindcx.security.ScreenLock");
		driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

		synchronized(driver) {
			driver.wait(10000);
		}
	}

	@BeforeTest
	public void dataTable() throws IOException {

		File file = new File("D:\\Maven\\CoinDCXPro\\src\\test\\resources\\testData\\TestData.xlsx");
		FileInputStream inputStream = new FileInputStream(file);
		XSSFWorkbook wb=new XSSFWorkbook(inputStream);
		XSSFSheet sheet=wb.getSheet("Market");
		XSSFSheet sheetSignUp=wb.getSheet("SignUp");

		coinType=sheet.getRow(1).getCell(0).getStringCellValue();
		firstName=sheetSignUp.getRow(1).getCell(0).getStringCellValue();
		lastName=sheetSignUp.getRow(1).getCell(1).getStringCellValue();
		email=sheetSignUp.getRow(1).getCell(2).getStringCellValue();
		password=sheetSignUp.getRow(1).getCell(3).getStringCellValue();
		phNo=sheetSignUp.getRow(1).getCell(5).getStringCellValue();
		//		country=sheetSignUp.getRow(1).getCell(5).getStringCellValue();
		referralCode=sheetSignUp.getRow(1).getCell(6).getStringCellValue();
	}


	@Test
	public void disablePopUpScreen() {

		if(driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ImageView")).isDisplayed()) {

			driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ImageView")).click();
		}

	}

	@Test
	public void signUp() throws InterruptedException {


		driver.findElement(By.xpath("(//android.view.View[@content-desc=\"REGISTER\"])[2]/android.widget.TextView")).click();

		driver.findElement(By.id("com.coindcx:id/next_btn")).click();
		assertEquals(driver.findElement(By.id("com.coindcx:id/tvFirstNameError")).getText(),"Enter First Name");
		assertEquals(driver.findElement(By.id("com.coindcx:id/tvLastNameError")).getText(),"Enter Last Name");
		assertEquals(driver.findElement(By.id("com.coindcx:id/tvEmailError")).getText(),"Enter Email Address");
		assertEquals(driver.findElement(By.id("com.coindcx:id/tvPasswordError")).getText(),"Enter Password");

		driver.findElement(By.id("com.coindcx:id/first_name_input")).sendKeys(firstName);
		driver.findElement(By.id("com.coindcx:id/last_name_input")).sendKeys(lastName);

		driver.findElement(By.id("com.coindcx:id/email_input")).sendKeys("test");
		assertEquals(driver.findElement(By.id("com.coindcx:id/tvEmailError")).getText(),"Please enter valid email");
		driver.findElement(By.id("com.coindcx:id/email_input")).sendKeys(email);

		driver.findElement(By.id("com.coindcx:id/password_input")).sendKeys("testing");
		assertEquals(driver.findElement(By.id("com.coindcx:id/tvPasswordError")).getText(),"At least 8 characters with a uppercase,lowercase and a number is required");
		driver.findElement(By.id("com.coindcx:id/password_input")).sendKeys(password);

		driver.findElement(By.id("com.coindcx:id/next_btn")).click();
		driver.findElement(By.id("com.coindcx:id/register")).click();
		assertEquals(driver.findElement(By.id("com.coindcx:id/tvCountryNameError")).getText(),"Enter Country Name");
		assertEquals(driver.findElement(By.id("com.coindcx:id/tvPhoneNumberError")).getText(),"Enter Phone Number");

		driver.findElement(By.id("com.coindcx:id/country_input")).sendKeys("India");


		int x = driver.findElement(By.id("com.coindcx:id/country_input")).getLocation().getX();
		int y = driver.findElement(By.id("com.coindcx:id/country_input")).getLocation().getY();

		TouchAction action = new TouchAction(driver).tap(PointOption.point(x+60, y+150)).release();
		action.perform();


		driver.findElement(By.id("com.coindcx:id/phone_no_input")).sendKeys("66666");
		driver.findElement(By.id("com.coindcx:id/register")).click();
		assertEquals(driver.findElement(By.id("com.coindcx:id/tvPhoneNumberError")).getText(),"Invalid phone number");
		driver.findElement(By.id("com.coindcx:id/phone_no_input")).clear();
		driver.findElement(By.id("com.coindcx:id/phone_no_input")).sendKeys(phNo);

		driver.findElement(By.id("com.coindcx:id/referral_code_input")).sendKeys(referralCode);
		synchronized(driver) {
			driver.wait(10000);
		}

		driver.findElement(By.id("com.coindcx:id/register")).click();

		synchronized(driver) {
			driver.wait(10000);
		}

		driver.findElement(By.id("com.coindcx:id/edtEmailOtp")).sendKeys("4567");
		driver.findElement(By.id("com.coindcx:id/edtPhoneOtp")).sendKeys("4567");

		driver.findElement(By.id("com.coindcx:id/btnSubmit")).click();

	}

	@Test
	public void market() throws InterruptedException {


		driver.findElement(By.xpath("//android.view.View[@content-desc=\"MARKETS\"]/android.widget.Image")).click();

		synchronized(driver) {
			driver.wait(10000);
		}



		driver.findElement(By.xpath(
				"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.RelativeLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.view.ViewGroup/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[3]/android.view.View/android.widget.EditText"))
		.sendKeys(coinType);




		List<WebElement> listOfElements = driver.findElements(By.xpath("//android.view.Vi"
				+ "ew[contains(@content-desc,\"BTC/\")]"));

		for(int i=0;i<listOfElements.size();i++) {

			String volume=driver.findElement(By.xpath("(//android.view.View[contains(@content-desc,\"BTC/\")])[" +(i+1)+"]/android.view.View[2]")).getText();
			String Price=driver.findElement(By.xpath("(//android.view.View[contains(@content-desc,\"BTC/\")])[" +(i+1)+"]/android.view.View[4]")).getText();
			String ChangePercentage=driver.findElement(By.xpath("(//android.view.View[contains(@content-desc,\"BTC/\")])[" +(i+1)+"]/android.view.View[5]/android.view.View")).getText();
			System.out.println(volume +"|| Price : " +Price + "|| Change Percentage : "+ ChangePercentage);

			assertNotNull(volume);
			assertNotNull(Price);
			assertNotNull(ChangePercentage);

			driver.findElement(By.xpath("(//android.view.View[contains(@content-desc,\"BTC/\")])[" +(i+1)+"]/android.view.View[2]")).click();
			synchronized(driver) {
				driver.wait(5000);
			}

			String detailedPrice=driver.findElement(By.xpath("(//android.widget.FrameLayout//android.widget.RelativeLayout//android.widget.FrameLayout/android.view.ViewGroup/android.webkit.WebView//android.view.View/android.view.View[2]/android.view.View[1])[1]")).getText();
			String detailedVol=driver.findElement(By.xpath("//android.widget.FrameLayout/android.widget.LinearLayout//android.widget.FrameLayout//android.view.ViewGroup/android.webkit.WebView/android.webkit.WebView//android.view.View[2]/android.view.View[3]")).getText();
			String detailedChangePer=driver.findElement(By.xpath("(//android.widget.FrameLayout/android.widget.LinearLayout//android.widget.RelativeLayout//android.view.ViewGroup/android.webkit.WebView//android.view.View//android.view.View[2]/android.widget.TextView)[1]")).getText();

			System.out.println(detailedVol +"|| Detailed Price : " +detailedPrice + "||Detailed  Change Percentage : "+ detailedChangePer);

			assertNotNull(detailedVol);
			assertNotNull(detailedPrice);
			assertNotNull(detailedChangePer);


			driver.navigate().back();

			driver.findElement(By.xpath("//android.view.View[@content-desc=\"MARKETS\"]/android.widget.Image")).click();

			synchronized(driver) {
				driver.wait(5000);
			}



			driver.findElement(By.xpath(
					"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.RelativeLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.view.ViewGroup/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[3]/android.view.View/android.widget.EditText"))
			.sendKeys(coinType);

			synchronized(driver) {
				driver.wait(5000);
			}


		}

		driver.findElement(By.xpath("//android.view.View[@content-desc=\"HOME\"]/android.widget.Image")).click();

	}



	@AfterSuite
	public void endSession() throws InterruptedException {

		synchronized(driver) {
			driver.wait(10000);
		}
		System.out.println("Session Ended");
		driver.quit();
	}

}
