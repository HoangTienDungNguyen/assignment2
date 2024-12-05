import React, { useState, useEffect } from "react";
import axios from "axios";
import { useTranslation } from "react-i18next";

const SliderList = ({ onEditSlider, onCreateNewSlider }) => {
  const { t } = useTranslation();
  const [sliders, setSliders] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    axios
      .get("/slider-hoang/resources/sliders")
      .then((response) => setSliders(response.data))
      .catch((err) => setError(`${t("errorFetching")}: ${err.message}`));
  }, [t]);

  const handleDelete = (id) => {
    axios
      .delete(`/slider-hoang/resources/sliders/${id}`)
      .then(() => setSliders(sliders.filter((slider) => slider.id !== id)))
      .catch((err) => setError(`${t("noSlidersFound")}: ${err.message}`));
  };

  return (
    <div>
      <h1>{t("title")}</h1>
      {error && <p style={{ color: "red" }}>{error}</p>}
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
                  <button onClick={() => onEditSlider(slider)}>{t("edit")}</button>
                  <button onClick={() => handleDelete(slider.id)}>{t("delete")}</button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="6">{t("noSlidersFound")}</td>
            </tr>
          )}
        </tbody>
      </table>
      <button onClick={onCreateNewSlider}>{t("createNewSlider")}</button>
    </div>
  );
};

export default SliderList;
