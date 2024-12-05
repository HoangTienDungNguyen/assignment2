import React, { useEffect, useRef } from "react";
import { useTranslation } from "react-i18next";

const SliderCanvas = ({ sliders, onBack }) => {
  const { t } = useTranslation();
  const canvasRef = useRef(null);
  const animationRef = useRef(null);

  // Function to draw sliders on the canvas
  const drawSliders = (ctx, sliders) => {
    ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height); // Clear the canvas
    sliders.forEach((slider) => {
      ctx.fillStyle = "blue";
      ctx.fillRect(slider.x, slider.y, slider.size, slider.size);
    });
  };

  // Animation loop for sliders
  const animateSliders = () => {
    const canvas = canvasRef.current;
    if (!canvas) return;

    const ctx = canvas.getContext("2d");

    sliders.forEach((slider) => {
      // Update slider position
      slider.x += slider.currentTravel * 0.1 * slider.mvtDirection;

      // Reverse direction when hitting canvas boundaries
      if (slider.x + slider.size > canvas.width || slider.x < 0) {
        slider.mvtDirection *= -1;
      }
    });

    // Redraw sliders
    drawSliders(ctx, sliders);

    // Continue animation
    animationRef.current = requestAnimationFrame(animateSliders);
  };

  useEffect(() => {
    const canvas = canvasRef.current;
    const ctx = canvas.getContext("2d");

    // Draw initial sliders
    drawSliders(ctx, sliders);

    // Start animation
    animationRef.current = requestAnimationFrame(animateSliders);

    // Cleanup animation on component unmount
    return () => cancelAnimationFrame(animationRef.current);
  }, [sliders]); // Re-run animation whenever sliders are updated

  return (
    <div>
      <button onClick={onBack} className="btn">
      {t("backToList")}
      </button>
      <canvas ref={canvasRef} width="800" height="600" style={{ border: "1px solid black" }} />
    </div>
  );
};

export default SliderCanvas;
