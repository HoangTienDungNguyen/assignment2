import React, { useState, useEffect } from "react";
import SliderList from "./SliderList";
import SliderForm from "./SliderForm";
import SliderCanvas from "./SliderCanvas";
import LanguageSwitcher from "./LanguageSwitcher";
import { useTranslation } from "react-i18next";
import "./i18n"; 

import axios from "axios";

const App = () => {
  const { t } = useTranslation();
  const [currentView, setCurrentView] = useState("list"); // 'list', 'form', or 'canvas'
  const [selectedSlider, setSelectedSlider] = useState(null); // Slider being edited
  const [sliders, setSliders] = useState([]); // List of sliders

  const apiBase = "http://localhost:8080/slider-hoang/resources/sliders";

  // Fetch sliders from the backend
  const fetchSliders = async () => {
    try {
      const response = await axios.get(apiBase);
      setSliders(response.data);
    } catch (error) {
      console.error(`${t("Error fetching sliders")}: ${err.message}`);
    }
  };

  // Handle slider creation
  const handleCreateNewSlider = () => {
    setSelectedSlider(null);
    setCurrentView("form");
  };

  // Handle slider editing
  const handleEditSlider = (slider) => {
    setSelectedSlider(slider);
    setCurrentView("form");
  };

  // Return to list view
  const handleBackToList = () => {
    setCurrentView("list");
  };

  // Switch to canvas view
  const handleCanvasView = () => {
    setCurrentView("canvas");
  };

  // Fetch sliders on component mount
  useEffect(() => {
    fetchSliders();
  }, []);

  return (
    <div>
      <LanguageSwitcher />
      {currentView === "list" && (
        <div>
          <SliderList
            sliders={sliders}
            onEditSlider={handleEditSlider}
            onCreateNewSlider={handleCreateNewSlider}
          />
          <button onClick={handleCanvasView} className="btn">
          {t("viewCanvas")}
          </button>
        </div>
      )}
      {currentView === "form" && (
        <SliderForm slider={selectedSlider} onBack={handleBackToList} />
      )}
      {currentView === "canvas" && (
        <SliderCanvas sliders={sliders} onBack={handleBackToList} />
      )}
    </div>
  );
};

export default App;