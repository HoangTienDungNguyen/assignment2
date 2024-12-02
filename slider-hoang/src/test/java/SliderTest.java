/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
import cst8218.Hoang.slider.entity.Slider;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class SliderTest {

    private static Slider slider;

    @BeforeAll
    public static void setUpClass() {
        // Runs once before all tests
        System.out.println("Setting up class...");
        slider = new Slider();
        slider.setSize(10);
        slider.setX(0);
        slider.setY(0);
        slider.setMaxTravel(5);
    }

    @AfterAll
    public static void tearDownClass() {
        // Runs once after all tests
        System.out.println("Tearing down class...");
        slider = null;
    }

    @BeforeEach
    public void setUp() {
        // Runs before each test
        System.out.println("Setting up before each test...");
        slider.setX(10);
        slider.setY(15);
    }

    @AfterEach
    public void tearDown() {
        // Runs after each test
        System.out.println("Cleaning up after each test...");
    }

    @Test
    public void testSliderInitialization() {
        System.out.println("Running testSliderInitialization...");
        assertNotNull(slider, "Slider should not be null");
        assertEquals(10, slider.getSize(), "Slider size should be 10");
    }

    @Test
    public void testSliderMovement() {
        System.out.println("Running testSliderMovement...");
        slider.setX(slider.getX() + 5);
        assertEquals(15, slider.getX(), "Slider X position should be 15");
    }

    @Test
    public void testMaxTravel() {
        System.out.println("Running testMaxTravel...");
        slider.setMaxTravel(10);
        assertEquals(10, slider.getMaxTravel(), "Slider max travel should be 10");
    }
}
