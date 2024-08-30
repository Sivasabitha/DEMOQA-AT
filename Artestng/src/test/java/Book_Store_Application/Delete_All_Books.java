package Book_Store_Application;

import java.time.Duration;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class Delete_All_Books {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(70));
        driver.manage().window().maximize();
        driver.get("https://demoqa.com/");
        System.out.println("Navigated to DemoQA homepage");
    }

    @Test
    public void registrationLoginAndGoToBookStoreTest() {
        String registeredUsername = "sabitha12@gmail.com";
        String registeredPassword = "SabithaK@123";

        try {
            WebElement bookStoreCard = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='card-body'][contains(., 'Book Store Application')]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", bookStoreCard);
            System.out.println("Navigated to Book Store Application");

            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("login")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", loginButton);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginButton);
            System.out.println("Navigated to Login Page");

            WebElement newUserButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("newUser")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", newUserButton);
            System.out.println("Navigated to Registration Page");

            WebElement firstName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstname")));
            firstName.sendKeys("sabitha");
            WebElement lastName = driver.findElement(By.id("lastname"));
            lastName.sendKeys("k");
            WebElement userName = driver.findElement(By.id("userName"));
            userName.sendKeys(registeredUsername);
            WebElement password = driver.findElement(By.id("password"));
            password.sendKeys(registeredPassword);
            System.out.println("Filled in registration details");

            WebElement captchaCheckbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[@title='reCAPTCHA']")));
            driver.switchTo().frame(captchaCheckbox);
            WebElement captchaClickBox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".recaptcha-checkbox")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", captchaClickBox);
            System.out.println("Clicked on 'I'm not a robot' Checkbox");
            driver.switchTo().defaultContent();

            Thread.sleep(40000); 

            WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("register")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", registerButton);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", registerButton);
            System.out.println("Clicked on Register Button");

            WebElement registrationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
            String messageText = registrationMessage.getText();
            System.out.println("Registration message: " + messageText);

            if (messageText.contains("User exists")) {
                System.out.println("User already exists. Skipping registration.");
            } else {
                Assert.assertTrue(messageText.contains("Registration successful"), "Registration was not successful.");
                System.out.println("Registration confirmed.");
            }

            WebElement backToLoginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("gotologin")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", backToLoginButton);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", backToLoginButton);
            System.out.println("Clicked on 'Back to login' Button");

            WebElement userNameLogin = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userName")));
            userNameLogin.clear();
            userNameLogin.sendKeys(registeredUsername);
            WebElement passwordLogin = driver.findElement(By.id("password"));
            passwordLogin.clear();
            passwordLogin.sendKeys(registeredPassword);

            WebElement loginButtonSubmit = wait.until(ExpectedConditions.elementToBeClickable(By.id("login")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", loginButtonSubmit);
            loginButtonSubmit.click();
            System.out.println("Attempted to login.");

            WebElement logoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submit")));
            Assert.assertTrue(logoutButton.isDisplayed(), "Logout button is not displayed. Login might have failed.");
            System.out.println("Test passed. User logged in successfully.");

            WebElement deleteAllBooksButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Delete All Books']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteAllBooksButton);
            System.out.println("Clicked on 'Delete All Books' Button");

            try {
                WebElement confirmationPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='closeSmallModal-ok']")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", confirmationPopup);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", confirmationPopup);
                System.out.println("Clicked 'OK' on delete all book confirmation popup");
            } catch (Exception e) {
                System.out.println("No confirmation popup found or unable to handle popup");
            }

            try {
                Alert alert = wait.until(ExpectedConditions.alertIsPresent());
                String alertText = alert.getText();
                System.out.println("Alert message: " + alertText);
                alert.accept();
                System.out.println("Accepted the alert");
            } catch (Exception e) {
                System.out.println("No alert found or unable to handle alert: " + e.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test Failed: " + e.getMessage());
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
