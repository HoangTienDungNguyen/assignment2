import React, { useState, useEffect } from "react";
import axios from "axios";

const SliderList = ({ onEditSlider, onCreateNewSlider }) => {
  const [sliders, setSliders] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    axios
      .get("/slider-hoang/resources/sliders")
      .then((response) => setSliders(response.data))
      .catch((err) => setError("Error fetching sliders: " + err.message));
  }, []);

  const handleDelete = (id) => {
    axios
      .delete(`/slider-hoang/resources/sliders/${id}`)
      .then(() => setSliders(sliders.filter((slider) => slider.id !== id)))
      .catch((err) => setError("Error deleting slider: " + err.message));
  };

  return (
    <div>
      <h1>All Sliders</h1>
      {error && <p style={{ color: "red" }}>{error}</p>}
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Size</th>
            <th>X Position</th>
            <th>Y Position</th>
            <th>Max Travel</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {sliders.length > 0 ? (
            sliders.map((slider) => (
              <tr key={slider.id}>
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
                <td>
                  <button onClick={() => onEditSlider(slider)}>Edit</button>
                  <button onClick={() => handleDelete(slider.id)}>Delete</button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="6">No sliders found.</td>
            </tr>
          )}
        </tbody>
      </table>
      <button onClick={onCreateNewSlider}>Create New Slider</button>
    </div>
  );
};

export default SliderList;
