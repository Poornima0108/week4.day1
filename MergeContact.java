package week4.day1.assignments;

import java.time.Duration;
import java.util.*;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MergeContact {
	static ChromeDriver driver;
	
	public void launchBrowser() {
		WebDriverManager.chromedriver().setup();
		 driver= new ChromeDriver();
	}
	
	public void loginLeafTaps() {
		driver.get("http://leaftaps.com/opentaps/control/login");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.findElement(By.id("username")).sendKeys("Demosalesmanager");
		driver.findElement(By.id("password")).sendKeys("crmsfa");
		driver.findElement(By.xpath("//input[@class='decorativeSubmit']")).click();
		driver.findElement(By.xpath("//a[contains(text(),'CRM/SFA')]")).click();
	}
	
	public void mergeContact() {
		driver.findElement(By.linkText("Contacts")).click();
		driver.findElement(By.xpath("//a[text()='Merge Contacts']")).click();
		
		//Click on widget of From contact
		driver.findElement(By.xpath("(//input[@name='ComboBox_partyIdFrom']//following::img)[1]")).click();
		
		//Get window handles
		Set<String> windowHandles = driver.getWindowHandles();
		System.out.println(windowHandles);
		List<String> allwindowHandlesList = new ArrayList<String>();
		allwindowHandlesList.addAll(windowHandles);
		String oldWindow = allwindowHandlesList.get(0);
		String newWindow = allwindowHandlesList.get(1);
		
		//Switch to new window
		driver.switchTo().window(newWindow);
		
		//Click on first resulting contact
		driver.findElement(By.xpath("(//div[text()='Contact ID']/following::a[@class='linktext'])[1]")).click();
		
		//Switch back to old window
		driver.switchTo().window(oldWindow);
		
		//Click on widget of To contact
		driver.findElement(By.xpath("(//input[@id='ComboBox_partyIdTo']//following::img)[1]")).click();
		
		//Get window handles
		Set<String> windowHandles1 = driver.getWindowHandles();
		System.out.println(windowHandles1);
		List<String> allwindowHandlesList1 = new ArrayList<String>();
		allwindowHandlesList1.addAll(windowHandles1);
		String oldWindow1 = allwindowHandlesList1.get(0);
		String newWindow1 = allwindowHandlesList1.get(1);
		
		//Switch to new window
		driver.switchTo().window(newWindow1);
		
		//Click on second resulting contact
		driver.findElement(By.xpath("(//div[text()='Contact ID']/following::a[@class='linktext'])[6]")).click();
		driver.switchTo().window(oldWindow1);
		
		//Click on merge
		driver.findElement(By.xpath("//a[text()='Merge']")).click();
		
		//Accept the alert
		Alert alert = driver.switchTo().alert();
		
		alert.accept();
		//Verify title
		String title = driver.getTitle();
		if(title.equals("View Contact | opentaps CRM")) {
			System.out.println("Title verified");
		}
		else {
			System.out.println("Incorrect title");
		}
	}
	public static void main(String[] args) {
		MergeContact mc = new MergeContact();
		mc.launchBrowser();
		mc.loginLeafTaps();
		mc.mergeContact();

	}

}
