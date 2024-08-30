package Widgets;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Date_Picker {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor jsExecutor;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Increased timeout to handle slow loading
        jsExecutor = (JavascriptExecutor) driver;
        driver.manage().window().maximize(); 
        driver.get("https://demoqa.com/");
    }

    @Test
    public void testDatePicker() {
       
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='home-banner']")));
        System.out.println("Home page loaded.");
      
        WebElement widgetsCard = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h5[text()='Widgets']")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", widgetsCard);
        jsExecutor.executeScript("arguments[0].click();", widgetsCard); // Click via JS
        System.out.println("Clicked on Widgets card.");
       
        WebElement datePickerOption = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Date Picker']")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", datePickerOption);
        jsExecutor.executeScript("arguments[0].click();", datePickerOption); // Click via JS
        System.out.println("Clicked on Date Picker option.");

        WebElement selectDateField = wait.until(ExpectedConditions.elementToBeClickable(By.id("datePickerMonthYearInput")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", selectDateField);
        selectDateField.click();
               
        WebElement dateToSelect = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class, 'react-datepicker__day') and text()='12']")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", dateToSelect);
        jsExecutor.executeScript("arguments[0].click();", dateToSelect);
        System.out.println("Selected date 12 Aug 2024.");
       
        String selectedDate = selectDateField.getAttribute("value");
        System.out.println("Selected date value: " + selectedDate);
        Assert.assertTrue(selectedDate.contains("08/12/2024") || selectedDate.contains("12/08/2024"), "Date was not selected correctly.");
        System.out.println("Verified selected date.");
      
        WebElement dateAndTimeField = wait.until(ExpectedConditions.elementToBeClickable(By.id("dateAndTimePickerInput")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", dateAndTimeField);
        dateAndTimeField.click();
              
        WebElement dateToSelectForDateTime = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class, 'react-datepicker__day') and text()='15']")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", dateToSelectForDateTime);
        jsExecutor.executeScript("arguments[0].click();", dateToSelectForDateTime);
        System.out.println("Selected date for Date and Time 15 Aug 2024.");
       
        WebElement timePickerOptions = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("react-datepicker__time-list")));
        System.out.println("Time picker is visible.");
      
        WebElement timeToSelect = timePickerOptions.findElement(By.xpath("//li[contains(text(), '20:00')]"));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", timeToSelect);
        jsExecutor.executeScript("arguments[0].click();", timeToSelect);
        System.out.println("Selected time 20:00.");
      
        String selectedDateTime = dateAndTimeField.getAttribute("value");
        System.out.println("Selected date and time value: " + selectedDateTime);
        Assert.assertTrue(selectedDateTime.contains("August 15, 2024"), "Date was not selected correctly.");
        Assert.assertTrue(selectedDateTime.contains("8:00 PM"), "Time was not selected correctly.");
        System.out.println("Verified selected date and time.");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
