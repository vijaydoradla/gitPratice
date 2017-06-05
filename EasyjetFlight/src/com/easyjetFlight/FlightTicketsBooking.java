package com.easyjetFlight;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FlightTicketsBooking {
	  WebDriver driver;
	  String applicationURL="https://www.easyjet.com/en/";
	  String homepage="Book direct for our guaranteed cheapest prices | easyJet.com";
	  String DeparturePlace="Stockholm";
	  String expDepartureName="Arlanda";
	  String destinationPlace="London (All Airports)";
	  String departuringmonthYear="June 2017";
	  int departuringDate=16;
	 
	  String returingmonthYear="June 2017";
	  int returingDate=25;
	  String searchResultsPage="Book cheap flights and find last minute flight deals – easyJet.com";
	  String fromDate="16 Jun";
	  String returnDate="25 Jun";
	  String currencyType="kr";
	 
	 @BeforeMethod
	 public void launchBrowser()
	 {
		 
		// 1.Open main application page https://www.easyjet.com/en/
		 //Set Path for Chrome Driver
		 System.setProperty("webdriver.chrome.driver", "Resources\\chromedriver.exe");
		 driver=new ChromeDriver();
		 //launch browser and open easyjet application URL
		 driver.get(applicationURL);
		 driver.manage().timeouts().pageLoadTimeout(100,TimeUnit.SECONDS);
		 //Maximize browser window
		 driver.manage().window().maximize();
		// Implicitly WebDriver wait
		 driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		 //if Cookie Policy Popup displayed, close the popup 
		 if(driver.findElement(By.xpath("//button[text()='Accept & Close']")).isDisplayed())
		{
		 driver.findElement(By.xpath("//button[text()='Accept & Close']")).click();
		}
	 }
	 
	 
  @Test
  public void login() throws Exception {
	  
	 // 2.Verify correct page is opened
	 Assert.assertEquals(homepage,driver.getTitle());
	 
	 if(driver.getTitle().trim().contains(homepage)){
 		  
         System.out.println("Home "+driver.getTitle()+"  "+"page opened");
     }else{
         System.out.println("Home "+driver.getTitle()+"Page not opened");
     }
	 
	 
	 
	 
	 //3.Under "Flights" tab, in "From" field type city "Stockholm"
	 WebElement FromDeparture=driver.findElement(By.name("origin"));
	 if(FromDeparture.isDisplayed())
	 {
	   FromDeparture.click();
	   FromDeparture.sendKeys(Keys.DELETE);
	   Thread.sleep(1000);
	   FromDeparture.sendKeys(DeparturePlace);
	   FromDeparture.sendKeys(Keys.ENTER);
	 }
	 
	 
	 
	 
	 //4.Verify the value of "From" field contains airport name "Arlanda" 
	 
	 if(FromDeparture.getAttribute("value")!= null)
	 {
	       String selectedFromText = FromDeparture.getAttribute("value");
	 
	       if(selectedFromText.trim().contains(expDepartureName))
	        {
	 
              System.out.println("From field contains airport name as"+" "+expDepartureName);
             }else{
              System.out.println("From field not contains airport name as "+" "+selectedFromText);
            }
	 }
	 
	 
	 
	 
	// 5.In "To" field choose city "London" using custom selector (button at the right side from input field)
	 
	 WebElement Todestination=driver.findElement(By.xpath("//label[text()='To:']/following-sibling::a"));
	 if(Todestination.isDisplayed())
	 {
	   Todestination.click();
	   Thread.sleep(1000);
	   driver.findElement(By.xpath("//a[text()='London (All Airports)']")).click();
	 
	
	 }
	 
	 
	 if(Todestination.getAttribute("value")!= null)
	 {
	      String selectedToText = Todestination.getAttribute("value");
	 
	       if(selectedToText.trim().equals(destinationPlace))
	        {
	    	   Assert.assertEquals(selectedToText,destinationPlace);
              System.out.println("From field contains airport name "+" "+Todestination);
             }else{
              System.out.println("From field not contains airport name"+" "+Todestination);
            }
	 }
	 
	 
	 
	  
	 //6.Choose any departure dates

	 WebElement DeparturingDatepicker=driver.findElement(By.xpath("//div[contains(text(),'Departing')]/following-sibling::a"));
	 if(DeparturingDatepicker.isDisplayed())
	 {
	    Actions actions = new Actions(driver);
	    actions.moveToElement(DeparturingDatepicker);
	    Thread.sleep(2000);
	    actions.click().build().perform();
	    Thread.sleep(2000);
	    WebElement DeparturDateSelection = driver.findElement(By.xpath("//h3[contains(text(),'"+departuringmonthYear+"')]/following-sibling::div//a/span[text()='"+departuringDate+"']")); 
	    Thread.sleep(2000);
	    actions.moveToElement(DeparturDateSelection);
	    Thread.sleep(2000);
	    actions.click().build().perform();
	 }
	  
	//Choose any  return dates
	   WebElement ReturnDatePickerIcon=driver.findElement(By.xpath("//div[contains(text(),'Returning')]/following-sibling::a"));
	   if (ReturnDatePickerIcon.isDisplayed())
	   {
	       Actions actions = new Actions(driver);
	       Thread.sleep(2000);
	       actions.moveToElement(ReturnDatePickerIcon);
	       actions.click().build().perform(); 
	       Thread.sleep(1000);
	       WebElement ReturnDateSelection = driver.findElement(By.xpath("//h3[contains(text(),'"+returingmonthYear+"')]/following-sibling::div//a/span[text()='"+returingDate+"']"));  
		   actions.moveToElement(ReturnDateSelection);
		   Thread.sleep(1000);
		   actions.click().build().perform();
	   } 	
		
	   
	   
	 // 7.Verify 1 adult passenger is added automatically
		 if(driver.findElement(By.xpath("//input[@name='Adults']")).isDisplayed())
		 {
		     String defaultadultsTicket= driver.findElement(By.xpath("//input[@name='Adults']")).getAttribute("value");
		     int ticketbyDefault = Integer.parseInt(defaultadultsTicket);
		         if(ticketbyDefault==1){
			  
	                  System.out.println("By Default"+" "+defaultadultsTicket+ " "+" adult passenger Selected");
	               }else{
	                   System.out.println("By Default"+" "+defaultadultsTicket+ " "+"adult passenger not Selected");
	               }
		
		 }
		
		
		 
		 
		//8.Add 1 one more passenger of type "Children"
		 
		 WebElement childPassengerAddButton=driver.findElement(By.xpath("//div[@passenger-label='Children (2-15)']//button[@class='quantity-button-add']"));
		if(childPassengerAddButton.isDisplayed())
		{
			   childPassengerAddButton.click();
		        Thread.sleep(1000);
		
		        String bookedChildTickets= driver.findElement(By.xpath("//input[@name='Children']")).getAttribute("value");
		        int bookedChildTicketsCount = Integer.parseInt(bookedChildTickets);
		            if(bookedChildTicketsCount==1)
		            {
		        	    Assert.assertEquals(bookedChildTicketsCount,1);
	                    System.out.println("booked Child Tickets"+"  "+bookedChildTicketsCount);
	                    }else{
	                    System.out.println("booked Child Tickets"+"  "+bookedChildTicketsCount);
	               }
	
		}
		
		
		
		
		//9.Click "Show flights" button
		if(driver.findElement(By.xpath("//button[text()='Show flights']")).isDisplayed())
		{
          driver.findElement(By.xpath("//button[text()='Show flights']")).click();
          Thread.sleep(1000);
		}
         
		
		
       // 10. Verify that search results page is opened
   	     Assert.assertEquals(searchResultsPage,driver.getTitle());
   	      
   	   if(driver.getTitle().trim().contains(searchResultsPage))
   	     {
  		  
             System.out.println("Search Results  "+driver.getTitle()+"  Page opened");
              }else{
              System.out.println("Search Results  "+driver.getTitle()+"  Page not opened");
          }
   	      
   	      
   	      
   	   
   	 
   	  // 11. Verify that search results contain prices for outbound and return flights
    	//Verify that search results contain prices for outbound flights
   	    if(driver.findElement(By.xpath("//span[text()='"+fromDate+"']/ancestor::div[1]//span[@class='price price-sek']//span[@class='before']")).isDisplayed())
   	    {
         String outboundCurrencyType=driver.findElement(By.xpath("//span[text()='"+fromDate+"']/ancestor::div[1]//span[@class='price price-sek']//span[@class='before']")).getText();
         String outboundPrice=driver.findElement(By.xpath("//span[text()='"+fromDate+"']/ancestor::div[1]//span[@class='price price-sek']//span[@class='major']")).getText();
         String outboundTicketPrice=outboundCurrencyType+outboundPrice;
         
         
               if(outboundTicketPrice.trim().contains(currencyType))
               {
   		  
                        System.out.println("Outbound Flights Price "+"  "+outboundTicketPrice+"  "+"  details displayed");
                     }else{
                         System.out.println("Outbound Flights Price details not displayed");
                }
         
   	    }
         
         //Verify that search results contain prices for return flights
   	    if(driver.findElement(By.xpath("//span[text()='"+returnDate+"']/ancestor::div[1]//span[@class='price price-sek']//span[@class='before']")).isDisplayed())
   	    {
              String returnFlightTicketCurrencyType=driver.findElement(By.xpath("//span[text()='"+returnDate+"']/ancestor::div[1]//span[@class='price price-sek']//span[@class='before']")).getText();
              String returnFlightTicketPrice=driver.findElement(By.xpath("//span[text()='"+returnDate+"']/ancestor::div[1]//span[@class='price price-sek']//span[@class='major']")).getText();
              String returnTicketPrice=returnFlightTicketCurrencyType+returnFlightTicketPrice;
         

               if(returnTicketPrice.trim().contains(currencyType))
               {
      		  
                  System.out.println("Return Flights Price"+"  "+returnTicketPrice+"  "+"details displayed");
                   }else{
                 System.out.println("Return Flights Price details not displayed");
               }
   	    }
   	     
  }
  
  
  @AfterMethod
	 public void closeBrowser()
	 {
		 driver.close();
	 }
}
