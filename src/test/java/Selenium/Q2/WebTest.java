package Selenium.Q2;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

public class WebTest {
	WebDriver driver;
	String Search_word;
	String Lang;
  @Test
  public void main() {
	  //open the wikipedia website
	  driver.get("https://www.wikipedia.org/");
	  //Wait for the WebElement to display
	  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	  WebElement search_input = driver.findElement(By.id("search-input"));
	  
	  //Workaround for Chromedriver issue. It cannot use search_input.sendKeys("test");
	  Actions actions = new Actions(driver);
	  actions.moveToElement(search_input);
	  actions.click();
	  actions.sendKeys(Search_word);
	  actions.build().perform();
	  
	  //Select English
	  Select dropdown = new Select(driver.findElement(By.id("searchLanguage")));
	  dropdown.selectByValue("en");
	  
	  //Click search button
	  driver.findElement(By.className("pure-button")).click();
	  
	  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	  //Validate the first word of the title
	  String title = driver.getTitle();
	  String[] words = title.split(" ");
	  System.out.println(words[0]);
	  Assert.assertEquals(true, words[0].equalsIgnoreCase(Search_word), "Wrong Website title");
	  
	  //Verify the language is available
	  System.out.println("Selected language is " + Lang);
	  WebElement Select_Lang = driver.findElement(By.cssSelector("a[lang*='it']"));
	  System.out.println("The selected language is available");
	  
	  //Click that language
	  Select_Lang.click();
	  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	  
	  //Verify English is available
	  driver.findElement(By.cssSelector("a[lang*='en']"));
	  System.out.println("English is available");
	  
	  
	
  }
  @Parameters({ "Search_word", "Lang" })
  @BeforeTest
  public void beforeTest(String value, String value2) {
	  Search_word = value;
	  Lang = value2;
	  System.out.println("The search word parameter is " + Search_word);
	  System.out.print("The language parameter is " + Lang);
	  System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
	  this.driver = new ChromeDriver();
  }

  @AfterTest
  public void afterTest() {
	  driver.quit();
  }

}
