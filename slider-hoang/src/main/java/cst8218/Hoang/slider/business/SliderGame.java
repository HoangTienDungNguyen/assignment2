/*
 * This code demonstrates the use of a singleton EJB (Enterprise Java Bean) to implement a game that updates 
 * a set of sliders in a continuous loop.
 */

package cst8218.Hoang.slider.business;

import cst8218.Hoang.slider.entity.Slider;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import java.util.List;

/**
 * The SliderGame class represents the main logic for a slider-based game.
 * It continuously updates a collection of Slider entities and persists changes
 * in the database using a SliderFacade.
 *
 * This class is annotated as a Singleton and Startup, meaning it is initialized
 * once during the application's lifecycle and starts automatically when the application starts.
 */
@Singleton
@Startup
public class SliderGame {

    // Injected dependency to handle Slider entity operations
    @Inject
    private SliderFacade sliderFacade;

    // Frame rate for updating the sliders (frames per second)
    private static final double CHANGE_RATE = 30.0;

    /**
     * Initializes the SliderGame. This method is called after the bean is constructed
     * and dependencies are injected. It starts a new thread to run the slider update loop.
     */
    @PostConstruct
    public void go() {
        // Start a new thread to handle the slider game logic
        new Thread(new Runnable() {
            @Override
            public void run() {
                // The game runs indefinitely
                while (true) {
                    // Fetch all sliders from the database
                    List<Slider> sliders = sliderFacade.findAll();
                    
                    // Update each slider's state and persist the changes
                    for (Slider slider : sliders) {
                        slider.timeStep(); // Advance the slider's state by one time step
                        sliderFacade.edit(slider); // Save the updated slider to the database
                    }
                    
                    // Sleep to maintain the desired frame rate
                    try {
                        // Pause execution for the duration of one frame
                        Thread.sleep((long) (1000 / CHANGE_RATE));
                    } catch (InterruptedException exception) {
                        // Log the exception if the thread is interrupted
                        exception.printStackTrace();
                    }
                }
            }
        }).start(); // Start the thread
    }
}
