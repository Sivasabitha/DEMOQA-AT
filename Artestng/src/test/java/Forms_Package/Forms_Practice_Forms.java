package Forms_Package;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Forms_Practice_Forms {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test
    public void testNavigateAndSubmitPracticeForm() {
        driver.get("https://demoqa.com/");

        try {
            WebElement fixedBan = driver.findElement(By.id("fixedban"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].remove();", fixedBan);
        } catch (Exception e) {
            System.out.println("No fixed banner found or couldn't remove it.");
        }

        WebElement formsCard = driver.findElement(By.xpath("//h5[text()='Forms']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", formsCard);
        
        WebElement clickableFormsCard = wait.until(ExpectedConditions.elementToBeClickable(formsCard));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", clickableFormsCard);

        WebElement practiceFormsLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Practice Form']")));
        practiceFormsLink.click();

      
        String firstName = "Sivasabitha";
        String lastName = "K";
        String email = "sabitha@gmail.com";
        String mobile = "7789648584";
        String address = "South Street";
        String subjects = "Maths";
        String hobbies = "Reading";
        String state = "NCR";
        String city = "Delhi";
        String picturePath = "C:\\Users\\akash\\OneDrive\\Documents\\SIVASABITHA\\Personal_Details\\img2.jpg"; // Change this to the actual path of your image file
        String gender = "Female";
        String dob = "15 August 1995";

        WebElement firstNameInput = driver.findElement(By.id("firstName"));
        firstNameInput.sendKeys(firstName);

        WebElement lastNameInput = driver.findElement(By.id("lastName"));
        lastNameInput.sendKeys(lastName);

        WebElement emailInput = driver.findElement(By.id("userEmail"));
        emailInput.sendKeys(email);

        WebElement mobileInput = driver.findElement(By.id("userNumber"));
        mobileInput.sendKeys(mobile);

        WebElement addressInput = driver.findElement(By.id("currentAddress"));
        addressInput.sendKeys(address);

      
        WebElement genderRadioButton = driver.findElement(By.xpath("//label[text()='" + gender + "']"));
        if (!genderRadioButton.isSelected()) {
            genderRadioButton.click();
        }

      
        WebElement dobInput = driver.findElement(By.id("dateOfBirthInput"));
        dobInput.click();
        WebElement dobPicker = driver.findElement(By.className("react-datepicker__day--015")); // Change the day as required
        dobPicker.click();

       
        WebElement subjectsInput = driver.findElement(By.id("subjectsInput"));
        subjectsInput.sendKeys(subjects);
        subjectsInput.sendKeys(Keys.ENTER); 

       
        WebElement hobbiesCheckbox = driver.findElement(By.xpath("//label[text()='" + hobbies + "']"));
        if (!hobbiesCheckbox.isSelected()) {
            hobbiesCheckbox.click();
        }

       
        WebElement stateDropdown = driver.findElement(By.id("state"));
        stateDropdown.click();
        WebElement stateOption = driver.findElement(By.xpath("//div[text()='" + state + "']"));
        stateOption.click();

        WebElement cityDropdown = driver.findElement(By.id("city"));
        cityDropdown.click();
        WebElement cityOption = driver.findElement(By.xpath("//div[text()='" + city + "']"));
        cityOption.click();
       
        WebElement uploadPictureInput = driver.findElement(By.id("uploadPicture"));
        uploadPictureInput.sendKeys(picturePath);

        WebElement submitButton = driver.findElement(By.id("submit"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        WebElement clickableSubmitButton = wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", clickableSubmitButton);

        WebElement confirmationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-body")));
        Assert.assertTrue(confirmationMessage.isDisplayed(), "Form submission confirmation message not displayed!");

        System.out.println(driver.getTitle());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
