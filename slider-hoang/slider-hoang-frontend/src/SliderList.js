// Import React and hooks for state management and lifecycle handling
import React, { useState, useEffect } from "react";
// Import Axios for making HTTP requests
import axios from "axios";
// Import the useTranslation hook for internationalization
import { useTranslation } from "react-i18next";

/**
 * SliderList Component
 * Displays a list of sliders, allows editing, deleting, and creating new sliders.
 * 
 * @param {Object} props - Component properties
 * @param {Function} props.onEditSlider - Callback to trigger slider editing
 * @param {Function} props.onCreateNewSlider - Callback to trigger slider creation
 */
const SliderList = ({ onEditSlider, onCreateNewSlider }) => {
  const { t } = useTranslation(); // Hook for translations
  const [sliders, setSliders] = useState([]); // State to hold the list of sliders
  const [error, setError] = useState(null); // State to handle error messages

  /**
   * useEffect Hook
   * Fetches the list of sliders when the component is mounted.
   */
  useEffect(() => {
    axios
      .get("/slider-hoang/resources/sliders") // GET request to fetch sliders
      .then((response) => setSliders(response.data)) // Update the sliders state with the response data
      .catch((err) => setError(`${t("errorFetching")}: ${err.message}`)); // Set an error message if the request fails
  }, [t]); // Dependencies include the translation function to ensure language consistency

  /**
   * Deletes a slider by its ID and updates the sliders list.
   * 
   * @param {number} id - The ID of the slider to delete
   */
  const handleDelete = (id) => {
    axios
      .delete(`/slider-hoang/resources/sliders/${id}`) // DELETE request to remove the slider
      .then(() => setSliders(sliders.filter((slider) => slider.id !== id))) // Filter out the deleted slider from the list
      .catch((err) => setError(`${t("noSlidersFound")}: ${err.message}`)); // Handle errors during deletion
  };

  return (
    <div>
      {/* Page title */}
      <h1>{t("title")}</h1>

      {/* Display error message if any */}
      {error && <p style={{ color: "red" }}>{error}</p>}

      {/* Table to display the list of sliders */}
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>{t("size")}</th>
            <th>{t("xPosition")}</th>
            <th>{t("yPosition")}</th>
            <th>{t("maxTravel")}</th>
            <th>{t("actions")}</th>
          </tr>
        </thead>
        <tbody>
          {/* If sliders are available, display them; otherwise, show a "No sliders found" message */}
          {sliders.length > 0 ? (
            sliders.map((slider) => (
              <tr key={slider.id}>
                {/* Each slider's properties are displayed in table cells, clickable to edit */}
                <td>
                  <button
                    onClick={() => onEditSlider(slider)}
                    style={{ background: "none", border: "none", color: "blue", cursor: "pointer", textDecoration: "underline" }}
                  >
                    {slider.id}
                  </button>
                </td>
                <td>
                  <button
                    onClick={() => onEditSlider(slider)}
                    style={{ background: "none", border: "none", color: "blue", cursor: "pointer", textDecoration: "underline" }}
                  >
                    {slider.size}
                  </button>
                </td>
                <td>
                  <button
                    onClick={() => onEditSlider(slider)}
                    style={{ background: "none", border: "none", color: "blue", cursor: "pointer", textDecoration: "underline" }}
                  >
                    {slider.x}
                  </button>
                </td>
                <td>
                  <button
                    onClick={() => onEditSlider(slider)}
                    style={{ background: "none", border: "none", color: "blue", cursor: "pointer", textDecoration: "underline" }}
                  >
                    {slider.y}
                  </button>
                </td>
                <td>
                  <button
                    onClick={() => onEditSlider(slider)}
                    style={{ background: "none", border: "none", color: "blue", cursor: "pointer", textDecoration: "underline" }}
                  >
                    {slider.maxTravel}
                  </button>
                </td>
                {/* Action buttons for editing or deleting sliders */}
                <td>
                  <button onClick={() => onEditSlider(slider)}>{t("edit")}</button>
                  <button onClick={() => handleDelete(slider.id)}>{t("delete")}</button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              {/* Display message if no sliders are found */}
              <td colSpan="6">{t("noSlidersFound")}</td>
            </tr>
          )}
        </tbody>
      </table>

      {/* Button to create a new slider */}
      <button onClick={onCreateNewSlider}>{t("createNewSlider")}</button>
    </div>
  );
};

export default SliderList; // Export the component for use in other parts of the application
