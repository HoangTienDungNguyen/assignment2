import React, { useState } from "react";
import SliderList from "./SliderList";
import SliderForm from "./SliderForm";

const App = () => {
  const [currentView, setCurrentView] = useState("list"); // 'list' or 'form'
  const [selectedSlider, setSelectedSlider] = useState(null); // Slider being edited or null for a new slider

  const handleEditSlider = (slider) => {
    setSelectedSlider(slider);
    setCurrentView("form");
  };

  const handleCreateNewSlider = () => {
    setSelectedSlider(null);
    setCurrentView("form");
  };

  const handleBackToList = () => {
    setCurrentView("list");
  };

  return (
    <div>
      {currentView === "list" ? (
        <SliderList onEditSlider={handleEditSlider} onCreateNewSlider={handleCreateNewSlider} />
      ) : (
        <SliderForm slider={selectedSlider} onBack={handleBackToList} />
      )}
    </div>
  );
};

export default App;
