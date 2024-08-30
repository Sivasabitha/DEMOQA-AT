package Book_Store_Application;

import java.time.Duration;

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

public class Log_Out {
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
    public void registrationAndLoginTest() {
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

            try {
                Thread.sleep(35000);  // 35 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

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

            WebElement logoutButtonElement = wait.until(ExpectedConditions.elementToBeClickable(By.id("submit")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", logoutButtonElement);
            System.out.println("Clicked on Logout Button");

            WebElement loginButtonAfterLogout = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login")));
            Assert.assertTrue(loginButtonAfterLogout.isDisplayed(), "Login button is not displayed. Logout might have failed.");
            System.out.println("Navigated to Login Page after logout.");

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
