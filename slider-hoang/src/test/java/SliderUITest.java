
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SliderUITest {

    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeAll
    public static void setupClass() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Admin\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.get("http://localhost:8080/slider-hoang"); 
    }

    @AfterAll
    public static void tearDownClass() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Order(1)
    public void testPageLoadsCorrectly() {
        WebElement title = driver.findElement(By.tagName("h1"));
        assertEquals("All Sliders", title.getText(), "Page title does not match!");
    }

    @Test
    @Order(2)
    public void testManualCreateSlider() {
        System.out.println("Please create a new slider by clicking the 'Create New Slider' button.");
        pauseForUser("Click the 'Create New Slider' button and fill in the form with valid values, then click 'Create'.");
        
        // Verify if a new slider has been added to the list
        WebElement sliderRow = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tbody/tr[last()]")));
        assertNotNull(sliderRow, "The new slider was not added.");
    }

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
     * Pause the test execution to allow the user to perform the manual action.
     *
     * @param message Instructions for the user.
     */
    private void pauseForUser(String message) {
        System.out.println(message);
        System.out.println("Press ENTER to continue...");
        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
 