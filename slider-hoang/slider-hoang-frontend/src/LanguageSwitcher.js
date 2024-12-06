// Import React for building components
import React from "react";
// Import the useTranslation hook from react-i18next for translations
import { useTranslation } from "react-i18next";

/**
 * LanguageSwitcher Component
 * Provides buttons to switch the application's language.
 */
const LanguageSwitcher = () => {
  // Destructure i18n (for changing language) and t (for translations) from the useTranslation hook
  const { i18n, t } = useTranslation();

  /**
   * Changes the application's language by calling i18n.changeLanguage.
   * 
   * @param {string} lng - The language code (e.g., "en", "vi", "zh").
   */
  const changeLanguage = (lng) => {
    i18n.changeLanguage(lng); // Update the current language setting
  };

  return (
    <div>
      {/* Button to switch to English */}
      <button onClick={() => changeLanguage("en")}>
        {t("language.english")} {/* Translate "English" */}
      </button>
      {/* Button to switch to Vietnamese */}
      <button onClick={() => changeLanguage("vi")}>
        {t("language.vietnamese")} {/* Translate "Vietnamese" */}
      </button>
      {/* Button to switch to Chinese */}
      <button onClick={() => changeLanguage("zh")}>
        {t("language.chinese")} {/* Translate "Chinese" */}
      </button>
    </div>
  );
};

export default LanguageSwitcher; // Export the component for use in other parts of the application
