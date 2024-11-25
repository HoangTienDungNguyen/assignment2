/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package cst8218.Hoang.slider.business;

import cst8218.Hoang.slider.entity.Slider;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import java.util.List;

@Singleton
@Startup
public class SliderGame {

    @Inject
    private SliderFacade sliderFacade;

    private static final double CHANGE_RATE = 30.0; // Frames per second

    @PostConstruct
    public void go() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // The game runs indefinitely
                while (true) {
                    // Update all sliders and save changes to the database
                    List<Slider> sliders = sliderFacade.findAll();
                    for (Slider slider : sliders) {
                        slider.timeStep();
                        sliderFacade.edit(slider);
                    }
                    // Sleep while waiting to process the next frame of the animation
                    try {
                        // Wake up roughly CHANGE_RATE times per second
                        Thread.sleep((long) (1000 / CHANGE_RATE));
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
