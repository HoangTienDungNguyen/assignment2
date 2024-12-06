import React, { useState, useEffect } from "react";
import { useTranslation } from "react-i18next";
import axios from "axios";

/**
 * SliderForm Component
 * Handles the creation or editing of a slider entity via a form interface.
 * 
 * @param {Object} props - Component props
 * @param {Object} [props.slider] - Slider data for editing, optional
 * @param {Function} props.onBack - Callback function to navigate back to the slider list
 */
const SliderForm = ({ slider, onBack }) => {
  // i18n hook for translations
  const { t } = useTranslation();

  // State to manage form data, initialized with either the provided slider or default values
  const [formData, setFormData] = useState(
    slider || { size: "", x: "", y: "", maxTravel: "" }
  );

  // State to manage error messages
  const [error, setError] = useState(null);

  /**
   * useEffect Hook
   * Updates the form data if the `slider` prop changes (e.g., when editing an existing slider).
   */
  useEffect(() => {
    if (slider) {
      setFormData(slider); // Populate form with existing slider data
    }
  }, [slider]); // Dependency array ensures this runs only when `slider` changes

  /**
   * Handles changes in input fields.
   * Updates the corresponding property in the `formData` state.
   * 
   * @param {Object} e - Event object from the input field
   */
  const handleChange = (e) => {
    const { name, value } = e.target; // Destructure name and value from the input element
    setFormData({ ...formData, [name]: value }); // Update state with the new value
  };

  /**
   * Handles form submission.
   * Sends a POST request for creating a new slider or a PUT request for updating an existing slider.
   * 
   * @param {Object} e - Form submission event
   */
  const handleSubmit = (e) => {
    e.preventDefault(); // Prevent default form submission behavior

    // Determine the request type based on whether we are editing or creating
    const request = slider
      ? axios.put(`/slider-hoang/resources/sliders/${slider.id}`, formData) // PUT request to update existing slider
      : axios.post(`/slider-hoang/resources/sliders`, formData); // POST request to create a new slider

    // Handle the request response
    request
      .then(() => {
        alert(`Slider ${slider ? t("edit") : t("createNewSlider")} ok!`); // Show success message
        onBack(); // Trigger callback to navigate back to the slider list
      })
      .catch((err) => setError(`${t("errorSaving")}: ${err.message}`)); // Set error message on failure
  };

  return (
    // Form for slider creation or editing
    <form onSubmit={handleSubmit}>
      {/* Dynamic form title based on whether editing or creating */}
      <h2>{slider ? t("edit") : t("createNewSlider")}</h2>

      {/* Display error message if present */}
      {error && <p style={{ color: "red" }}>{error}</p>}

      {/* Input field for slider size */}
      <label>
        {t("size")}
        <input
          type="number"
          name="size"
          value={formData.size}
          onChange={handleChange}
          required // Field is mandatory
        />
      </label>
      <br />

      {/* Input field for X position */}
      <label>
        {t("xPosition")}
        <input
          type="number"
          name="x"
          value={formData.x}
          onChange={handleChange}
          required // Field is mandatory
        />
      </label>
      <br />

      {/* Input field for Y position */}
      <label>
        {t("yPosition")}
        <input
          type="number"
          name="y"
          value={formData.y}
          onChange={handleChange}
          required // Field is mandatory
        />
      </label>
      <br />

      {/* Input field for max travel distance */}
      <label>
        {t("maxTravel")}
        <input
          type="number"
          name="maxTravel"
          value={formData.maxTravel}
          onChange={handleChange}
          required // Field is mandatory
        />
      </label>
      <br />

      {/* Submit button for creating or editing the slider */}
      <button type="submit">{slider ? t("edit") : t("createNewSlider")}</button>

      {/* Button to navigate back to the list view */}
      <button type="button" onClick={onBack}>
        {t("backToList")}
      </button>
    </form>
  );
};

export default SliderForm;