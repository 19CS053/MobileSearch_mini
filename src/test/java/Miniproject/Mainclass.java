package Miniproject;
 
import java.io.IOException;
import java.util.Scanner;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
 
@Listeners(Miniproject.Extentreport.class)
public class Mainclass
{
	String link;
	String toSearch;
	String toSelect;
	boolean condition;
	String filePath;
	int rows;
	String Browser;
 
	@BeforeClass
	public void Beforeclass() throws IOException
	{
		boolean bol = true;
		System.out.println("Enter the browser to Automate \n Chrome \n Edge");
		Scanner sc = new Scanner(System.in);
		Browser = sc.next();
		while(bol) {
			if(Browser.equalsIgnoreCase("Chrome") || (Browser.equalsIgnoreCase("Edge"))){
				MobileSearch.getWebDriver(Browser);
				bol =false;
				sc.close();
			}else {
				System.out.println("Enter Valid Browser name(Chrome or Edge)");		
			}	
	    }
		filePath = System.getProperty("user.dir")+"\\TestData\\Details.xlsx";
		//Get the row count from the excel
		rows = ExcelUtils.getRowCount(filePath, "Amazon");
		for(int i=1; i<=rows;i++) {
		//read data from excel
		    link = ExcelUtils.getCellData(filePath, "Amazon", rows, 0);
		    toSearch = ExcelUtils.getCellData(filePath, "Amazon", rows, 1);
		  	toSelect = ExcelUtils.getCellData(filePath, "Amazon", rows, 2);
		}
	}
	@AfterClass
    public void afterclass() throws InterruptedException {
		//Closing the browser
		MobileSearch.closeBrowser();
	}
	@Test(priority=1)
	public void launchingApplication() throws IOException
	{	
		//pass data to the driver
		MobileSearch.LaunchUrl(link);
	}
	@Test(priority=2)
	public void maximizeApplication() throws IOException, InterruptedException
	{
		//Maximizing the window
	    MobileSearch.MaximizeWindow();
	}
	@Test(priority=3)
	public void searchItem() throws InterruptedException, IOException
	{
		//Search in the application
		MobileSearch.toSearch(toSearch);
	}
	@Test(priority=4)
	public void validation() throws IOException, InterruptedException
	{
		//Validate the search string
		condition=MobileSearch.Validation(toSearch);
	}
	@Test(priority=5)
	public void selectDropdown() throws IOException, InterruptedException
	{
		//Select option “Newest Arrivals"
		MobileSearch.dropSelect(toSelect);
 
	}
	@Test(priority=6)
	public void writingExcel() throws IOException, InterruptedException
	{
		//write output in excel
		for(int i=1; i<=rows;i++)
		{
		if(condition)
		 {
		 	System.out.println("Test Result is Printed in EXCEL");
		 	ExcelUtils.setCellData(filePath, "Amazon",i,4,"Passed");					
		 	ExcelUtils.fillGreenColor(filePath, "Amazon",i,4);
		 }
		else
		 {
			System.out.println("Test Result is Printed in EXCEL");
			ExcelUtils.setCellData(filePath, "Amazon",i,4,"Failed");
			ExcelUtils.fillRedColor(filePath, "Amazon",i,4);
		 }
		}	
	}
}