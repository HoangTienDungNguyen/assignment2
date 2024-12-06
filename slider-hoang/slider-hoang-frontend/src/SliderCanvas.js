// Import React and necessary hooks for rendering and managing component lifecycle
import React, { useEffect, useRef } from "react";
// Import the useTranslation hook for internationalization
import { useTranslation } from "react-i18next";

/**
 * SliderCanvas Component
 * Displays a canvas with animated sliders and a button to navigate back to the list view.
 * 
 * @param {Object} props - Component properties
 * @param {Array} props.sliders - Array of slider objects to render on the canvas
 * @param {Function} props.onBack - Callback to navigate back to the list view
 */
const SliderCanvas = ({ sliders, onBack }) => {
  const { t } = useTranslation(); // Hook for translations
  const canvasRef = useRef(null); // Ref for accessing the canvas DOM element
  const animationRef = useRef(null); // Ref for managing the animation frame

  /**
   * Draws sliders on the canvas.
   * 
   * @param {CanvasRenderingContext2D} ctx - Canvas drawing context
   * @param {Array} sliders - Array of slider objects with x, y, and size properties
   */
  const drawSliders = (ctx, sliders) => {
    ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height); // Clear the canvas
    sliders.forEach((slider) => {
      ctx.fillStyle = "blue"; // Set slider color
      ctx.fillRect(slider.x, slider.y, slider.size, slider.size); // Draw a rectangle for each slider
    });
  };

  /**
   * Animation loop to update and redraw sliders on the canvas.
   */
  const animateSliders = () => {
    const canvas = canvasRef.current; // Get the canvas element
    if (!canvas) return;

    const ctx = canvas.getContext("2d"); // Get the 2D drawing context

    sliders.forEach((slider) => {
      // Update the slider's X position
      slider.x += slider.currentTravel * 0.1 * slider.mvtDirection;

      // Reverse direction if the slider hits the canvas boundaries
      if (slider.x + slider.size > canvas.width || slider.x < 0) {
        slider.mvtDirection *= -1;
      }
    });

    drawSliders(ctx, sliders); // Redraw sliders on the canvas

    // Request the next animation frame
    animationRef.current = requestAnimationFrame(animateSliders);
  };

  /**
   * useEffect Hook
   * Initializes the canvas drawing and starts the animation when the component mounts.
   * Cleans up the animation when the component unmounts.
   */
  useEffect(() => {
    const canvas = canvasRef.current; // Get the canvas element
    const ctx = canvas.getContext("2d"); // Get the 2D drawing context

    drawSliders(ctx, sliders); // Draw the initial sliders on the canvas
    animationRef.current = requestAnimationFrame(animateSliders); // Start the animation loop

    // Cleanup function to cancel the animation on component unmount
    return () => cancelAnimationFrame(animationRef.current);
  }, [sliders]); // Re-run the effect whenever the sliders array changes

  return (
    <div>
      {/* Button to navigate back to the list view */}
      <button onClick={onBack} className="btn">
        {t("backToList")} {/* Translate "Back to List" */}
      </button>

      {/* Canvas element for rendering the sliders */}
      <canvas
        ref={canvasRef} // Attach the canvasRef to this canvas element
        width="800" // Set canvas width
        height="600" // Set canvas height
        style={{ border: "1px solid black" }} // Add a border around the canvas
      />
    </div>
  );
};

export default SliderCanvas; // Export the component for use in other parts of the application
