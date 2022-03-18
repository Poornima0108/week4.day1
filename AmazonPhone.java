package week4.day1.assignments;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AmazonPhone {

	public static void main(String[] args) {
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		driver.get("https://www.amazon.in/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']")).sendKeys("oneplus 9 pro", Keys.ENTER);

		// Print price for 1st product
		WebElement elementPrice = driver.findElement(By.xpath("(//span[@class='a-price'])[1]"));
		String price = elementPrice.getText();
		System.out.println("Price: " + price);

		// Print no of customer ratings for 1st product
		String ratings = driver.findElement(By.xpath("(//span[@class='a-declarative']/parent::span)[1]")).getAttribute("aria-label");
		System.out.println("Ratings: " + ratings);

		// click on the stars
		driver.findElement(By.xpath("(//span[@class='a-declarative'])[1]")).click();

		// Percentage of ratings for the 5 star
		String ratingPercentage = driver.findElement(By.xpath("(//*[@id=\"histogramTable\"]//span//a)[1]")).getAttribute("title");
		System.out.println("Percentage of ratings for the 5 star: " + ratingPercentage);

		// Click the first text link of the first image
		driver.findElement(By.xpath("(//div[@class='sg-col-inner']//span[contains(text(),'OnePlus 9 Pro')])[1]")).click();

		// Get window handles
		Set<String> windowHandles = driver.getWindowHandles();
		System.out.println(windowHandles);
		List<String> allwindowHandlesList = new ArrayList<String>();
		allwindowHandlesList.addAll(windowHandles);
		String parentWindow1 = allwindowHandlesList.get(0);
		String window2 = allwindowHandlesList.get(1);
		
		//Switch to new window
		driver.switchTo().window(window2);
		
		//Click 'Add to Cart' button
		driver.findElement(By.xpath("//input[@id='add-to-cart-button']")).click();
		
		//Get the cart sub-total
		WebElement elementTotal = driver.findElement(By.xpath("((//span[@id='attach-accessory-cart-total-string'])[1]/following::span//span)[1]"));
		//WebElement elementTotal = driver.findElement(By.xpath("(//span[contains(@class,'a-size-base-plus a-color-price')]//span)[1]"));
		String subTotal = elementTotal.getText();
		System.out.println("SubTotal: "+subTotal);
		
	}

}
