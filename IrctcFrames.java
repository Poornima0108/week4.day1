package week4.day1.assignments;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class IrctcFrames {

	public static void main(String[] args) throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		driver.get("https://www.irctc.co.in/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.findElement(By.xpath("//button[text()='OK']")).click();
		Thread.sleep(10000);
		driver.findElement(By.partialLinkText("FLIGHTS")).click();
		
		//Get window handles
		Set<String> windowHandles = driver.getWindowHandles();
		System.out.println(windowHandles);
		List<String> allwindowHandlesList = new ArrayList<String>();
		allwindowHandlesList.addAll(windowHandles);
		String parentWindow1 = allwindowHandlesList.get(0);
		String window2 = allwindowHandlesList.get(1);
		
		//Switch to new window
		driver.switchTo().window(window2);
		
		//Thread.sleep(30000);
		//Cancel notification
		WebElement elementNotification = driver.findElement(By.xpath("//button[text()='Later']"));
		if(elementNotification.isDisplayed()) {
			elementNotification.click();
		}
		
		//Get customer care email id
		driver.findElement(By.xpath("//a[contains(text(),'Contact Us')]")).click();
		String email = driver.findElement(By.partialLinkText("flights@irctc.co.in")).getText();
		System.out.println("Customer care email: "+email);
		driver.switchTo().window(parentWindow1);
		driver.close();
		

	}

}
