package cst8218.Hoang.slider.presentation;

import cst8218.Hoang.slider.business.SliderFacade;
import cst8218.Hoang.slider.entity.Slider;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import jakarta.faces.context.FacesContext;

/**
 * Backing bean for managing Slider entities through JSF pages.
 * Provides methods to interact with Slider entities such as creating,
 * editing, listing, and deleting sliders.
 * Includes language switching for internationalization.
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

    // Internationalization support
    private Locale locale;

    /**
     * Initializes the list of sliders and sets the default locale.
     */
    @PostConstruct
    public void init() {
        refreshSliderList();
        locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
    }

    /**
     * Refreshes the list of sliders by retrieving all sliders from the database.
     */
    public void refreshSliderList() {
        sliders = sliderFacade.findAll();
    }

    /**
     * Retrieves all sliders from the database.
     * Uses lazy loading to fetch sliders only when needed.
     * 
     * @return List of Slider entities.
     */
    public List<Slider> getSliders() {
        if (sliders == null) {
            refreshSliderList();
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
        if (slider.getId() == null) {
            sliderFacade.create(slider);
        } else {
            sliderFacade.edit(slider);
        }
        refreshSliderList();  // Refresh the list after saving
        slider = new Slider();  // Reset the form
        return "sliderList.xhtml?faces-redirect=true";  // Redirect to refresh the page
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
        refreshSliderList(); // Refresh the list after deletion
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

    /**
     * Retrieves the current locale.
     * 
     * @return The current locale.
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Sets the current locale and updates the FacesContext.
     * 
     * @param language The language code (e.g., "en", "zh", "vi").
     */
    public void setLanguage(String language) {
        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }

    /**
     * Retrieves the current language code.
     * 
     * @return The current language code.
     */
    public String getLanguage() {
        return locale.getLanguage();
    }
}
