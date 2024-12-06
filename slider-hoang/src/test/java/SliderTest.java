/*
 * This test class is designed to validate the functionality of the Slider entity.
 * It uses JUnit 5 (JUnit Jupiter) to test the properties and behaviors of the Slider class.
 */

import cst8218.Hoang.slider.entity.Slider;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The SliderTest class contains unit tests for the Slider entity class.
 * It verifies the correct initialization, movement, and property management of the Slider object.
 */
public class SliderTest {

    // A single instance of Slider used across all tests
    private static Slider slider;

    /**
     * Sets up the class-level resources before any test runs.
     * This method is executed once before all test cases.
     */
    @BeforeAll
    public static void setUpClass() {
        System.out.println("Setting up class...");
        slider = new Slider();
        // Initialize slider with default properties
        slider.setSize(10);
        slider.setX(0);
        slider.setY(0);
        slider.setMaxTravel(5);
    }

    /**
     * Cleans up the class-level resources after all tests have run.
     * This method is executed once after all test cases.
     */
    @AfterAll
    public static void tearDownClass() {
        System.out.println("Tearing down class...");
        slider = null; // Release the slider reference
    }

    /**
     * Sets up resources or states before each test case.
     * This method is executed before every test.
     */
    @BeforeEach
    public void setUp() {
        System.out.println("Setting up before each test...");
        // Reset slider's position before each test
        slider.setX(10);
        slider.setY(15);
    }

    /**
     * Cleans up resources or states after each test case.
     * This method is executed after every test.
     */
    @AfterEach
    public void tearDown() {
        System.out.println("Cleaning up after each test...");
    }

    /**
     * Tests that the Slider object is initialized correctly with default values.
     */
    @Test
    public void testSliderInitialization() {
        System.out.println("Running testSliderInitialization...");
        assertNotNull(slider, "Slider should not be null");
        assertEquals(10, slider.getSize(), "Slider size should be 10");
    }

    /**
     * Tests the movement of the Slider by updating its X coordinate.
     */
    @Test
    public void testSliderMovement() {
        System.out.println("Running testSliderMovement...");
        // Move the slider by 5 units along the X-axis
        slider.setX(slider.getX() + 5);
        assertEquals(15, slider.getX(), "Slider X position should be 15");
    }

    /**
     * Tests the maximum travel distance property of the Slider.
     */
    @Test
    public void testMaxTravel() {
        System.out.println("Running testMaxTravel...");
        // Set a new maximum travel distance for the slider
        slider.setMaxTravel(10);
        assertEquals(10, slider.getMaxTravel(), "Slider max travel should be 10");
    }
}
