// Importing i18n (internationalization library), react-i18next for React compatibility, and a language detector plugin
import i18n from "i18next"; // Core library for internationalization
import { initReactI18next } from "react-i18next"; // React bindings for i18next
import LanguageDetector from "i18next-browser-languagedetector"; // Automatically detects the user's browser language

// Initialize i18n with the following configuration
i18n
  .use(LanguageDetector) // Automatically detects the user's language settings from the browser
  .use(initReactI18next) // Connects i18next with React
  .init({
    // Language resource translations
    resources: {
      en: { // English translations
        translation: {
          title: "All Sliders", // Titles and labels
          size: "Size",
          xPosition: "X Position",
          yPosition: "Y Position",
          maxTravel: "Max Travel",
          actions: "Actions", // Table actions
          edit: "Edit",
          delete: "Delete",
          createNewSlider: "Create New Slider", // Button labels
          backToList: "Back to List",
          viewCanvas: "View Canvas",
          errorSaving: "Error saving slider", // Error messages
          errorFetching: "Error fetching sliders",
          noSlidersFound: "No sliders found.", // Empty state message
          language: { // Language selection options
            english: "English",
            vietnamese: "Vietnamese",
            chinese: "Chinese",
          },
        },
      },
      vi: { // Vietnamese translations
        translation: {
          title: "Tất cả thanh trượt",
          size: "Kích thước",
          xPosition: "Vị trí X",
          yPosition: "Vị trí Y",
          maxTravel: "Khoảng cách tối đa",
          actions: "Hành động",
          edit: "Chỉnh sửa",
          delete: "Xóa",
          createNewSlider: "Tạo thanh trượt mới",
          backToList: "Quay lại danh sách",
          viewCanvas: "Xem Canvas",
          errorSaving: "Lỗi khi lưu thanh trượt",
          errorFetching: "Lỗi khi tải danh sách thanh trượt",
          noSlidersFound: "Không tìm thấy thanh trượt.",
          language: {
            english: "Tiếng Anh",
            vietnamese: "Tiếng Việt",
            chinese: "Tiếng Trung Quốc",
          },
        },
      },
      zh: { // Chinese translations
        translation: {
          title: "所有滑块",
          size: "大小",
          xPosition: "X 位置",
          yPosition: "Y 位置",
          maxTravel: "最大行程",
          actions: "操作",
          edit: "编辑",
          delete: "删除",
          createNewSlider: "创建新滑块",
          backToList: "返回列表",
          viewCanvas: "查看画布",
          errorSaving: "保存滑块时出错",
          errorFetching: "获取滑块列表时出错",
          noSlidersFound: "未找到滑块。",
          language: {
            english: "英语",
            vietnamese: "越南语",
            chinese: "中文",
          },
        },
      },
    },
    fallbackLng: "en", // Fallback language if the user's preferred language is not available
    interpolation: {
      escapeValue: false, // React already protects against XSS, so escaping is not necessary
    },
  });

export default i18n; // Export the configured i18n instance for use across the application
