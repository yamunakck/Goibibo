package com.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.firefox.FirefoxDriver;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;



public class TestBase {
	public static WebDriver driver;
	public static Properties prop;
	public static ExtentReports extent;
	public static ExtentSparkReporter reporter; 
	
	public TestBase() {
		prop = new Properties();
		try {
			FileInputStream fis = new FileInputStream("./src/main/java/com/config/config.properties");
			prop.load(fis);
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void initialization() {
		
		System.setProperty("webdriver.gecko.driver",prop.getProperty("driverpath"));
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.get(prop.getProperty("url"));		
	}
	
	public void ExtentReportSetup() {
		String reportpath = System.getProperty("user.dir") + "/extentreport/index1.html";
		reporter = new ExtentSparkReporter(reportpath);
		extent = new ExtentReports();
		extent.attachReporter(reporter);
	}
	
	public void CloseReportSetup() {
		extent.flush();
	}
	
	public static String capture(WebDriver driver, String name) throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File Dest = new File("./Screenshots/" + name + System.currentTimeMillis()+ ".png");
		String errflpath = Dest.getAbsolutePath();
		FileUtils.copyFile(scrFile, Dest);
		return errflpath;
	}
	
	public void ScrollIntoView(WebElement path) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", path);
	}
	
	public static void teardown() {
		driver.quit();
	}


}
