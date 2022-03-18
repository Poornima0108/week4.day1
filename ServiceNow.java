package week4.day1.assignments;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ServiceNow {
static ChromeDriver driver;
	
	public void launchBrowser() {
		WebDriverManager.chromedriver().setup();
		 driver= new ChromeDriver();
	}
	
	public void loginServiceNow() {
		driver.get("https://dev41128.service-now.com/navpage.do");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		WebElement loginFrame = driver.findElement(By.xpath("//iframe[@id='gsft_main']"));
		driver.switchTo().frame(loginFrame);
		driver.findElement(By.xpath("//input[@id='user_name']")).sendKeys("admin");
		driver.findElement(By.xpath("//input[@id='user_password']")).sendKeys("1wQsyajPQ0CQ");
		driver.findElement(By.xpath("//button[@id='sysverb_login']")).click();
	}
	
	public void newIncident() throws IOException, InterruptedException {
		//Search “incident “ Filter Navigator
		driver.findElement(By.xpath("//input[@id='filter']")).sendKeys("incident");
		//Click “All”
		driver.findElement(By.xpath("(//span[text()='Incident']/following::div[@class='sn-widget-list-title' and text()='All'])[1]")).click();
		//Switch to frame
		WebElement incidentFrame = driver.findElement(By.xpath("//iframe[@title='Incidents | ServiceNow']"));
		driver.switchTo().frame(incidentFrame);
		//Click New button
		driver.findElement(By.xpath("//button[text()='New']")).click();
		//Click icon in caller field
		driver.findElement(By.xpath("//button[@id='lookup.incident.caller_id']")).click();
		//Get window handles
		Set<String> windowHandles = driver.getWindowHandles();
		System.out.println(windowHandles);
		List<String> allwindowHandlesList = new ArrayList<String>();
		allwindowHandlesList.addAll(windowHandles);
		String oldWindow = allwindowHandlesList.get(0);
		String newWindow = allwindowHandlesList.get(1);
		//Switch to new window
		driver.switchTo().window(newWindow);
		//Select a value for Caller
		driver.findElement(By.xpath("//tbody[@class='list2_body']//td//a[@role='button']")).click();
		//Switch to parent window
		driver.switchTo().window(oldWindow);
		//Switch to frame
		WebElement newincidentFrame = driver.findElement(By.xpath("//iframe[@id='gsft_main']"));
		driver.switchTo().frame(newincidentFrame);
		//Enter value for short_description
		driver.findElement(By.xpath("//input[@id='incident.short_description']")).sendKeys("Entered description");
		//Read the incident number and save it a variable
		String incidentNumber = driver.findElement(By.xpath("//input[@id='incident.number']")).getAttribute("value");
		//Click on Submit button
		driver.findElement(By.xpath("(//button[text()='Submit'])[1]")).click();
		//Thread.sleep(5000);
		//Search the same incident number in the next search screen
		driver.findElement(By.xpath("//span[@id='incident_hide_search']//input[@placeholder='Search']")).sendKeys(incidentNumber,Keys.ENTER);
		String createdIncident = driver.findElement(By.xpath("//a[@class='linked formlink']")).getText();
		//Verify the incident is created successful and take snapshot 
		if(incidentNumber.equals(createdIncident)) {
			System.out.println("Incident created successfully");
			File screenshot = driver.getScreenshotAs(OutputType.FILE);
			File image = new File("./snaps/img001.jpg");
			FileUtils.copyFile(screenshot, image);
		}
		else {
			System.out.println("Incident not created");
		}
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		ServiceNow sn = new ServiceNow();
		sn.launchBrowser();
		sn.loginServiceNow();
		sn.newIncident();

	}

}
