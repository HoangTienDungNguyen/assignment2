import React, { useState, useEffect } from "react";
import axios from "axios";

const SliderForm = ({ slider, onBack }) => {
  const [formData, setFormData] = useState(
    slider || { size: "", x: "", y: "", maxTravel: "" }
  );
  const [error, setError] = useState(null);

  useEffect(() => {
    if (slider) {
      setFormData(slider);
    }
  }, [slider]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const request = slider
      ? axios.put(`/slider-hoang/resources/sliders/${slider.id}`, formData)
      : axios.post(`/slider-hoang/resources/sliders`, formData);

    request
      .then(() => {
        alert(`Slider ${slider ? "updated" : "created"} successfully!`);
        onBack(); // Return to the list view
      })
      .catch((err) => setError("Error saving slider: " + err.message));
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>{slider ? "Edit Slider" : "Create New Slider"}</h2>
      {error && <p style={{ color: "red" }}>{error}</p>}
      <label>
        Size:
        <input
          type="number"
          name="size"
          value={formData.size}
          onChange={handleChange}
          required
        />
      </label>
      <br />
      <label>
        X Position:
        <input
          type="number"
          name="x"
          value={formData.x}
          onChange={handleChange}
          required
        />
      </label>
      <br />
      <label>
        Y Position:
        <input
          type="number"
          name="y"
          value={formData.y}
          onChange={handleChange}
          required
        />
      </label>
      <br />
      <label>
        Max Travel:
        <input
          type="number"
          name="maxTravel"
          value={formData.maxTravel}
          onChange={handleChange}
          required
        />
      </label>
      <br />
      <button type="submit">{slider ? "Update" : "Create"}</button>
      <button type="button" onClick={onBack}>
        Cancel
      </button>
    </form>
  );
};

export default SliderForm;
