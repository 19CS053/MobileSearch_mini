package Miniproject;
 
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
 
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.Select;
import io.github.bonigarcia.wdm.WebDriverManager;
 
public class MobileSearch {
	
	String link;
	public static WebDriver driver;
	public static WebDriver getWebDriver(String Browser)
	{
		if(Browser.equalsIgnoreCase("Chrome"))
		{
			System.out.println(Browser + " is selected for Automation");
			driver = new ChromeDriver();
		}
		else if(Browser.equalsIgnoreCase("Edge"))
		{
			System.out.println(Browser + " is selected for Automation");
			driver = new EdgeDriver();		
		}
	return driver;
	}
	public static void LaunchUrl(String link) throws IOException
	{
		// Launch Amazon website
		driver.get(link);
		MobileSearch.ScreenShot("./snaps/1)Launch.png");
		System.out.println("1)Website Link\n	"+driver.getCurrentUrl() +"\n");
 
	}
	public static void MaximizeWindow() throws IOException, InterruptedException
	{
 
		//maximize browser window
		driver.manage().window().maximize();
		MobileSearch.ScreenShot("./snaps/2)Maximized.png");
		System.out.println("2)Window Maximized\n");
		Thread.sleep(2000);
	}
	public static void toSearch(String toSearch) throws InterruptedException, IOException
	{
 
		// wait till the browser loads
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		//Search “mobile Smart phones under 30000”
		WebElement ToSearch = driver.findElement(By.id("twotabsearchtextbox"));
		ToSearch.sendKeys(toSearch);
		MobileSearch.ScreenShot("./snaps/3)ToSearch.png");
		Thread.sleep(2000);
		//Click Search Button
		driver.findElement(By.id("nav-search-submit-button")).click();
		System.out.println("3)To Search\n	"+toSearch+"\n");
	}
	public static boolean Validation(String toSearch) throws IOException, InterruptedException
	{
 
		//Validation
		WebElement searchElement = driver.findElement(By.className("a-color-state"));
		//Get searchString
		String searchString = searchElement.getText();
		WebElement page_Items= driver.findElement(By.className("a-section"));
		String page_Items_text = page_Items.getText();
		System.out.println("4)Validation Display Message\n	"+  page_Items_text+"\n");
		//Check Validation message
		MobileSearch.ScreenShot("./snaps/4)Validation.png");
		Thread.sleep(1000);
		if(toSearch.equals(searchString.substring(1,searchString.length()-1)))
		{
 
			System.out.println("\ti)Search string VALIDATION SUCCESSFUL\n\t\tExpected: "+
					searchString.substring(1,searchString.length()-1)+
					"\n\t\tActual: "+ toSearch+"\n");
 
			// Get Search Page & No_Of_Items
			String SearchPage_Items = driver.findElement(By.className("sg-col-inner")).getText();
 
			if(SearchPage_Items.startsWith("1") && SearchPage_Items.contains("of over") && SearchPage_Items.contains("results for"))
			{
				System.out.println("\tii)No of pages and items 'VALIDATION SUCCESSFUL'");
			}
			else
			{
				System.out.println("\tii)No of pages and items 'VALIDATION UNSUCCESSFUL'");
 
			}
			return true;
		}
		else
		{
			System.out.println("\ti)Search string VALIDATION UNSUCCESFUL/n\t\tExpected: "+
					searchString.substring(1,searchString.length()-1)+
					"\n\t\tActual: "+ toSearch+ "\n");
			// Get Search Page & No_Of_Items
			String SearchPage_Items = driver.findElement(By.className("sg-col-inner")).getText();
			if(SearchPage_Items.startsWith("1") && SearchPage_Items.contains("of over") && SearchPage_Items.contains("results for")) {
				System.out.println("\tii)No of pages and items 'VALIDATION SUCCESSFUL'");
			}else {
				System.out.println("\tii)No of pages and items 'VALIDATION UNSUCCESSFUL'");
 
			}
			return false;
		}	
	}
 
	public static void dropSelect(String toSelect) throws  IOException, InterruptedException {
 
		//Click on Sort By
		WebElement sortOpt =driver.findElement(By.id("s-result-sort-select"));
		Select selectOpt = new Select(sortOpt);
 
		// Select Option  "Newest Arrivals"
		selectOpt.selectByVisibleText(toSelect);
		//Total Select Option count and options available
		List<WebElement> sortOptions = selectOpt.getOptions();
		System.out.println("\n5)Number of option in Sort by Dropdown: \n\t Total ="+sortOptions.size()+"\n");
		System.out.println("  Options available  : " + sortOpt.getText());
		System.out.println("---------------------------------------------------------------------------");
		//Check the Selected option
		String selectedOption=driver.findElement(By.className("a-dropdown-prompt")).getText();
		if(selectedOption.equals(toSelect))
		{
			System.out.println("TEST CASE PASSED"+"\n"+"\tSelected option: "+selectedOption);
			System.out.println("---------------------------------------------------------------------------");
		}
		else
		{
			System.out.println("TEST CASE FAILED"+"\n"+"\tSelected option: "+selectedOption);
			System.out.println("---------------------------------------------------------------------------");
		}
		 // Take the screenshot of every operations
		Thread.sleep(2000);
		MobileSearch.ScreenShot("./snaps/5)DropSelect.png");
	}
	public static void ScreenShot(String img ) throws IOException
	{
		File source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		File target =  new File(img);
		FileHandler.copy(source,target);
	}
	 //Close the browser
	public static void closeBrowser() throws InterruptedException {
		Thread.sleep(3000);
		driver.quit();
	}
}