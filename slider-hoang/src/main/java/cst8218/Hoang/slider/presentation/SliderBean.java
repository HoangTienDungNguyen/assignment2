package cst8218.Hoang.slider.presentation;

import cst8218.Hoang.slider.business.SliderFacade;
import cst8218.Hoang.slider.entity.Slider;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Backing bean for managing Slider entities through JSF pages.
 * Provides methods to interact with Slider entities such as creating,
 * editing, listing, and deleting sliders.
 * Uses dependency injection to access the SliderFacade.
 * 
 * @author Hoang
 */
@Named("sliderBean")
@SessionScoped
public class SliderBean implements Serializable {
    @Inject
    private SliderFacade sliderFacade;
    private Slider slider = new Slider();
    private List<Slider> sliders;

    /**
     * Retrieves all sliders from the database.
     * Uses lazy loading to fetch sliders only when needed.
     * 
     * @return List of Slider entities.
     */
    public List<Slider> getSliders() {
        if (sliders == null) {
            sliders = sliderFacade.findAll();
        }
        return sliders;
    }

    /**
     * Saves a new Slider or updates an existing one.
     * Redirects to the slider list page after saving.
     * 
     * @return Navigation string for redirection.
     */
    public String save() {
    if (slider.getId() != null) {
        slider.setId(null); // Ensure ID is null to avoid conflicts with existing IDs
    }
    sliderFacade.create(slider);
    return "sliderList.xhtml?faces-redirect=true";
    }

    /**
     * Loads a slider for editing based on its ID.
     * Redirects to the edit page.
     * 
     * @param id The ID of the slider to edit.
     * @return Navigation string for redirection.
     */
    public String edit(Long id) {
        slider = sliderFacade.find(id);
        return "createSlider.xhtml";
    }

    /**
     * Deletes a slider based on its ID.
     * Refreshes the list and redirects to the slider list page.
     * 
     * @param id The ID of the slider to delete.
     * @return Navigation string for redirection.
     */
    public String delete(Long id) {
        Slider sliderToDelete = sliderFacade.find(id);
        sliderFacade.remove(sliderToDelete);
        sliders = null; // Refresh the list after deletion
        return "sliderList.xhtml?faces-redirect=true";
    }

    /**
     * Retrieves the current slider.
     * 
     * @return The current slider instance.
     */
    public Slider getSlider() {
        return slider;
    }

    /**
     * Sets the current slider.
     * 
     * @param slider The slider instance to set.
     */
    public void setSlider(Slider slider) {
        this.slider = slider;
    }
}
