import React, { useState, useEffect } from "react";
import SliderList from "./SliderList";
import SliderForm from "./SliderForm";
import SliderCanvas from "./SliderCanvas";
import LanguageSwitcher from "./LanguageSwitcher";
import { useTranslation } from "react-i18next";
import "./i18n"; 

import axios from "axios";

/**
 * App Component
 * Acts as the main entry point for the Slider application.
 * Manages views, language switching, and interaction with the backend API.
 */
const App = () => {
  const { t } = useTranslation(); // Hook for translations
  const [currentView, setCurrentView] = useState("list"); // Current view: 'list', 'form', or 'canvas'
  const [selectedSlider, setSelectedSlider] = useState(null); // Currently selected slider for editing
  const [sliders, setSliders] = useState([]); // List of sliders fetched from the backend

  const apiBase = "http://localhost:8080/slider-hoang/resources/sliders"; // Base URL for the API

  /**
   * Fetch sliders from the backend and update the state.
   */
  const fetchSliders = async () => {
    try {
      const response = await axios.get(apiBase); // Fetch sliders using Axios
      setSliders(response.data); // Update the state with the fetched sliders
    } catch (error) {
      console.error(`${t("Error fetching sliders")}: ${error.message}`); // Log error if fetching fails
    }
  };

  /**
   * Handles the creation of a new slider by switching to the form view.
   */
  const handleCreateNewSlider = () => {
    setSelectedSlider(null); // Clear the selected slider
    setCurrentView("form"); // Switch to the form view
  };

  /**
   * Handles the editing of a slider by setting the selected slider and switching to the form view.
   * 
   * @param {Object} slider - The slider object to edit
   */
  const handleEditSlider = (slider) => {
    setSelectedSlider(slider); // Set the slider to be edited
    setCurrentView("form"); // Switch to the form view
  };

  /**
   * Switches back to the list view from either the form or canvas views.
   */
  const handleBackToList = () => {
    setCurrentView("list"); // Set the view to 'list'
  };

  /**
   * Switches to the canvas view for visualizing sliders.
   */
  const handleCanvasView = () => {
    setCurrentView("canvas"); // Set the view to 'canvas'
  };

  /**
   * useEffect Hook
   * Fetches the sliders from the backend when the component mounts.
   */
  useEffect(() => {
    fetchSliders(); // Fetch sliders on component mount
  }, []); // Empty dependency array ensures this runs only once

  return (
    <div>
      {/* LanguageSwitcher component for changing the application language */}
      <LanguageSwitcher />

      {/* Conditionally render views based on the currentView state */}
      {currentView === "list" && (
        <div>
          {/* SliderList component for displaying the list of sliders */}
          <SliderList
            sliders={sliders} // Pass the list of sliders as a prop
            onEditSlider={handleEditSlider} // Pass the edit handler as a prop
            onCreateNewSlider={handleCreateNewSlider} // Pass the create handler as a prop
          />
          {/* Button to switch to the canvas view */}
          <button onClick={handleCanvasView} className="btn">
            {t("viewCanvas")} {/* Translate "View Canvas" */}
          </button>
        </div>
      )}

      {/* Render the SliderForm component if the view is 'form' */}
      {currentView === "form" && (
        <SliderForm 
          slider={selectedSlider} // Pass the selected slider for editing
          onBack={handleBackToList} // Pass the back-to-list handler
        />
      )}

      {/* Render the SliderCanvas component if the view is 'canvas' */}
      {currentView === "canvas" && (
        <SliderCanvas
          sliders={sliders} // Pass the list of sliders
          onBack={handleBackToList} // Pass the back-to-list handler
        />
      )}
    </div>
  );
};

export default App; // Export the App component as the default export
