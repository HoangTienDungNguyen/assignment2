import React from "react";
import { useTranslation } from "react-i18next";

const LanguageSwitcher = () => {
  const { i18n, t } = useTranslation(); // Use both `i18n` and `t`

  const changeLanguage = (lng) => {
    i18n.changeLanguage(lng);
  };

  return (
    <div>
      <button onClick={() => changeLanguage("en")}>{t("language.english")}</button>
      <button onClick={() => changeLanguage("vi")}>{t("language.vietnamese")}</button>
      <button onClick={() => changeLanguage("zh")}>{t("language.chinese")}</button>
    </div>
  );
};

export default LanguageSwitcher;
