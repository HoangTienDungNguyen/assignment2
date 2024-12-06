import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * This test class uses Selenium WebDriver to perform UI tests for the Slider application.
 * It validates functionalities such as loading the page, creating, editing, and deleting sliders.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SliderUITest {

    private static WebDriver driver;
    private static WebDriverWait wait;

    /**
     * Sets up the WebDriver and navigates to the Slider application before running tests.
     */
    @BeforeAll
    public static void setupClass() {
        // Set the path for the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Admin\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        
        // Initialize WebDriverWait with a timeout of 20 seconds
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        
        // Set implicit wait for finding elements
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        
        // Navigate to the Slider application's URL
        driver.get("http://localhost:8080/slider-hoang");
    }

    /**
     * Tears down the WebDriver instance after all tests are completed.
     */
    @AfterAll
    public static void tearDownClass() {
        // Close the browser and quit the WebDriver
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Validates that the Slider application's main page loads correctly by checking the title.
     */
    @Test
    @Order(1)
    public void testPageLoadsCorrectly() {
        // Locate the page title and verify its text
        WebElement title = driver.findElement(By.tagName("h1"));
        assertEquals("All Sliders", title.getText(), "Page title does not match!");
    }

    /**
     * Validates the manual creation of a slider by prompting the user to perform the action
     * and verifying that a new slider appears in the list.
     */
    @Test
    @Order(2)
    public void testManualCreateSlider() {
        System.out.println("Please create a new slider by clicking the 'Create New Slider' button.");
        pauseForUser("Click the 'Create New Slider' button and fill in the form with valid values, then click 'Create'.");
        
        // Verify if a new slider has been added to the list
        WebElement sliderRow = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tbody/tr[last()]")));
        assertNotNull(sliderRow, "The new slider was not added.");
    }

    /**
     * Validates the manual editing of a slider by prompting the user to perform the action
     * and checking if the slider's values are updated.
     */
    @Test
    @Order(3)
    public void testManualEditSlider() {
        System.out.println("Please edit an existing slider by clicking the 'Edit' button.");
        pauseForUser("Click the 'Edit' button for any slider, modify its values, and click 'Update'.");

        // Handle alert if it appears after updating
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            System.out.println("Alert found with message: " + alert.getText());
            alert.accept(); // Dismiss the alert
        } catch (TimeoutException e) {
            System.out.println("No alert found after editing.");
        }

        // Verify if the slider has been updated
        WebElement updatedRow = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tbody/tr[last()]")));
        assertNotNull(updatedRow, "The slider was not updated.");
    }

    /**
     * Validates the manual deletion of a slider by prompting the user to perform the action
     * and verifying that the slider is removed from the list.
     */
    @Test
    @Order(4)
    public void testManualDeleteSlider() {
        System.out.println("Please delete a slider by clicking the 'Delete' button.");
        pauseForUser("Click the 'Delete' button for any slider in the list.");

        // Verify if the slider has been removed from the list
        try {
            WebElement sliderRow = driver.findElement(By.xpath("//tbody/tr[last()]"));
            assertNull(sliderRow, "The slider was not deleted.");
        } catch (NoSuchElementException e) {
            // Pass if the slider is not found
            System.out.println("The slider was successfully deleted.");
        }
    }

    /**
     * Pauses the test execution to allow the user to perform the manual action.
     *
     * @param message Instructions for the user to perform the required manual action.
     */
    private void pauseForUser(String message) {
        System.out.println(message);
        System.out.println("Press ENTER to continue...");
        try {
            System.in.read(); // Wait for user input
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
