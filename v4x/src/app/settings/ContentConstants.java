package app.settings;

public class ContentConstants {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    
    private static final String APP_NAME = "『🔎 𝗧𝗲𝘀𝘀𝗲𝗿𝗮𝗰𝘁 𝗢𝗖𝗥』:: 𝐈𝐦𝐚𝐠𝐞/𝐏𝐃𝐅 𝐓𝐞𝐱𝐭 𝐄𝐱𝐭𝐫𝐚𝐜𝐭𝐢𝐨𝐧 🛠𝐓𝐨𝐨𝐥";
    private static final String PLACEHOLDER_TEXT = "- 𝙽𝙸𝙻 -";
    private static final String DISPLAYED_PROFILE_LINK = " h̲t̲t̲p̲s̲://g̲e̲e̲k̲-c̲c̲.m̲e̲d̲i̲u̲m̲.c̲o̲m̲̲";
    private static final String PROFILE_LINK = "https://medium.com/@geek-cc";
    private static final String QUICK_TIPS_TITLE =    "🗦💡🗧𝚀𝚞𝚒𝚌𝚔 𝚃𝚒𝚙𝚜";
    private static final String OPEN_IMG_BTN_TEXT =   " 𝙾𝚙𝚎𝚗 𝙸𝚖𝚊𝚐𝚎 ";
    private static final String UPLOAD_PDF_BTN_TEXT = " 𝚄𝚙𝚕𝚘𝚊𝚍 𝙿𝙳𝙵 ";
    private static final String RUN_OCR_BTN_TEXT =    "𝚂𝚝𝚊𝚛𝚝 𝚁𝚞𝚗 ≫";
    private static final String OCR_ALL_BTN_TEXT =    " 𝚁𝚞𝚗 𝙰𝚕𝚕 ≫ ";
    private static final String RESET_ALL_BTN_TEXT =  " 𝚁𝚎𝚜𝚎𝚝 𝙰𝚕𝚕  ";
    private static final String QUICK_TIPS_BTN_TEXT = " 𝚀𝚞𝚒𝚌𝚔 𝚃𝚒𝚙𝚜 ";
    private static final String TASK_STATUS_TEXT =    "𝚃𝚊𝚜𝚔 𝚂𝚝𝚊𝚝𝚞𝚜   ";
    
    private static final String OPEN_IMG_BTN_TOOLTIP = "📖 Select image file(s)";
    private static final String UPLOAD_PDF_BTN_TOOLTIP = "📖 Select a PDF document";
    private static final String RUN_OCR_BTN_TOOLTIP = "📖 Processes current selection and outputs/appends extracted text in the textarea";
    private static final String OCR_ALL_BTN_TOOLTIP = "📖 Processes all uploads and outputs/appends extracted text in the textarea";
    private static final String SAVE_TEXT_BTN_TOOLTIP = "📖 Saves textarea content to a output file";
    private static final String COPY_TEXT_BTN_TOOLTIP = "📖 Selects and copies textarea content";
    private static final String CLEAR_TEXT_BTN_TOOLTIP = "📖 Clears textarea content";
    private static final String RESET_ALL_BTN_TOOLTIP = "📖 Resets the application to its default state";
    private static final String QUICK_TIPS_BTN_TOOLTIP = "📖 Displays features & functionalities of application";

    private static final String PREV_PAGE_BTN_TEXT =    "";
    private static final String NEXT_PAGE_BTN_TEXT =    ""; 
    private static final String ZOOM_IN_BTN_TEXT =      ""; 
    private static final String ZOOM_OUT_BTN_TEXT =     "";
    private static final String ROTATE_LEFT_BTN_TEXT =  "⭯"; 
    private static final String ROTATE_RIGHT_BTN_TEXT = "⭮";  
    private static final String FIT_IMAGE_BTN_TEXT =    "";  
    
    private static final String[] MONOSPACE_DIGITS = {"𝟶", "𝟷", "𝟸", "𝟹", "𝟺", "𝟻", "𝟼", "𝟽", "𝟾", "𝟿"};
    private static final String[] BOLD_DIGITS = {"𝟬", "𝟭", "𝟮", "𝟯", "𝟰", "𝟱", "𝟲", "𝟳", "𝟴", "𝟵"};
    private static final String PENDING_STATUS_TEXT = "";
    private static final String RUNNING_TASK_TEXT = " Running…";
    
    private static final String PREV_PAGE_BTN_TOOLTIP = "📖 Prev Page";
    private static final String NEXT_PAGE_BTN_TOOLTIP = "📖 Next Page";
    private static final String ZOOM_IN_BTN_TOOLTIP = "📖 Zoom in";
    private static final String ZOOM_OUT_BTN_TOOLTIP = "📖 Zoom out";
    private static final String ROTATE_LEFT_BTN_TOOLTIP = "📖 Rotates Image anti-clockwise";
    private static final String ROTATE_RIGHT_BTN_TOOLTIP = "📖 Rotates Image clockwise";
    private static final String FIT_IMAGE_BTN_TOOLTIP = "📖 Fit Image";

    private static final String IMAGE_PREVIEW_PLACEHOLDER = "🖼 Preview";
    private static final String OCR_TEXTAREA_PLACEHOLDER = "Note: Auto-appends OCR results here";
    private static final String HOCR_TEXTAREA_PLACEHOLDER = "Note: Auto-appends HOCR results here";
    
    private static final String DIALOG_TITLE = "🔍 𝐎𝐂𝐑 𝐏𝐫𝐨𝐜𝐞𝐬𝐬𝐢𝐧𝐠";
    
    private static final String DISPLAY_OCR_ALL_DIALOG_MSG =  "🔋 𝗘𝘅𝘁𝗿𝗮𝗰𝘁𝗶𝗼𝗻 𝗦𝘁𝗮𝘁𝘂𝘀: 🗹 𝖲𝖴𝖢𝖢𝖤𝖲𝖲" 
        + LINE_SEPARATOR
        + "𝖳𝖾𝗑𝗍 𝖽𝖺𝗍𝖺 𝖾𝗑𝗍𝗋𝖺𝖼𝗍𝗂𝗈𝗇 𝗁𝖺𝗌 𝖻𝖾𝖾𝗇 𝖼𝗈𝗆𝗉𝗅𝖾𝗍𝖾𝖽 𝖿𝗈𝗋 𝖺𝗅𝗅 𝗎𝗉𝗅𝗈𝖺𝖽𝗌."
        + LINE_SEPARATOR
        + "𝖯𝗅𝖾𝖺𝗌𝖾 𝗋𝖾𝖿𝖾𝗋 𝗍𝗈 𝗍𝖾𝗑𝗍𝖺𝗋𝖾𝖺 𝗈𝗇 𝗍𝗁𝖾 𝗋𝗂𝗀𝗁𝗍 𝗍𝗈 𝖺𝖼𝖼𝖾𝗌𝗌 𝖮𝖢𝖱 𝗋𝖾𝗌𝗎𝗅𝗍𝗌."
        + LINE_SEPARATOR
        + LINE_SEPARATOR;
    
    private static final String DISPLAY_OCR_DIALOG_MSG =     "🔋 𝗘𝘅𝘁𝗿𝗮𝗰𝘁𝗶𝗼𝗻 𝗦𝘁𝗮𝘁𝘂𝘀: 🗹 𝖲𝖴𝖢𝖢𝖤𝖲𝖲"
        + LINE_SEPARATOR
        + "𝖳𝖾𝗑𝗍 𝖽𝖺𝗍𝖺 𝖾𝗑𝗍𝗋𝖺𝖼𝗍𝗂𝗈𝗇 𝗁𝖺𝗌 𝖻𝖾𝖾𝗇 𝖼𝗈𝗆𝗉𝗅𝖾𝗍𝖾𝖽 𝖿𝗈𝗋 𝖼𝗎𝗋𝗋𝖾𝗇𝗍 𝗌𝖾𝗅𝖾𝖼𝗍𝗂𝗈𝗇."
        + LINE_SEPARATOR
        + "𝖯𝗅𝖾𝖺𝗌𝖾 𝗋𝖾𝖿𝖾𝗋 𝗍𝗈 𝗍𝖾𝗑𝗍𝖺𝗋𝖾𝖺 𝗍𝗈 𝖺𝖼𝖼𝖾𝗌𝗌 𝗋𝖾𝗌𝗎𝗅𝗍𝗌."
        + LINE_SEPARATOR
        + LINE_SEPARATOR;
    
    private static final String DISPLAY_UPLOAD_COMPLETION_DIALOG_MSG =  "📤 𝗨𝗽𝗹𝗼𝗮𝗱 𝗦𝘁𝗮𝘁𝘂𝘀: 𝖢𝖮𝖬𝖯𝖫𝖤𝖳𝖤!"
        + LINE_SEPARATOR
        + "𝖠𝗅𝗅 𝗂𝗇𝗉𝗎𝗍𝗌 𝗁𝖺𝗏𝖾 𝖻𝖾𝖾𝗇 𝗎𝗉𝗅𝗈𝖺𝖽𝖾𝖽 𝗌𝗎𝖼𝖼𝖾𝗌𝗌𝖿𝗎𝗅𝗅𝗒."
        + LINE_SEPARATOR
        + "𝖮𝖢𝖱 𝖾𝗑𝗍𝗋𝖺𝖼𝗍𝗂𝗈𝗇 𝗉𝗋𝗈𝖼𝖾𝗌𝗌 𝗂𝗌 𝗇𝗈𝗐 𝗋𝖾𝖺𝖽𝗒 𝗍𝗈 𝗋𝗎𝗇."
        + LINE_SEPARATOR
        + LINE_SEPARATOR;

    public String getLINE_SEPARATOR() {
        return LINE_SEPARATOR;
    }
    
    public String getPaginationDisplayText(String currentPageBoldStr,String totalPagesMonospaceStr) {
        return "𝙿𝚊𝚐𝚎 " + currentPageBoldStr + " 𝚘𝚏 " + totalPagesMonospaceStr;
    }
    public String getDIALOG_TITLE() {
        return DIALOG_TITLE;
    }

    public String getDISPLAY_OCR_ALL_DIALOG_MSG() {
        return DISPLAY_OCR_ALL_DIALOG_MSG;
    }

    public String getDISPLAY_OCR_DIALOG_MSG() {
        return DISPLAY_OCR_DIALOG_MSG;
    }

    public String getDISPLAY_UPLOAD_COMPLETION_DIALOG_MSG() {
        return DISPLAY_UPLOAD_COMPLETION_DIALOG_MSG;
    }
    
    
    public String getTASK_STATUS_TEXT() {
        return TASK_STATUS_TEXT;
    }
    
    public String getFooterNote(String yearDisplay) {
        return "© 𝖢𝗈𝗉𝗒𝗋𝗂𝗀𝗁𝗍 " + yearDisplay + " 𝖻𝗒 ξ(🎀˶❛◡❛) ᵀᴴᴱ ᴿᴵᴮᴮᴼᴺ ᴳᴵᴿᴸ ➤";
    }
    public String[] getMONOSPACE_DIGITS() {
        return MONOSPACE_DIGITS;
    }

    public String[] getBOLD_DIGITS() {
        return BOLD_DIGITS;
    }

    public String getPENDING_STATUS_TEXT() {
        return PENDING_STATUS_TEXT;
    }

    public String getRUNNING_TASK_TEXT() {
        return RUNNING_TASK_TEXT;
    }

    public String getOCRTextareaPlaceholder() {
        return OCR_TEXTAREA_PLACEHOLDER;
    }
    public String getOCRAllBtnText() {
        return OCR_ALL_BTN_TEXT;
    }
    public String getHOCRTextareaPlaceholder() {
        return HOCR_TEXTAREA_PLACEHOLDER;
    }
    public String getImagePreviewPlaceholder() {
        return IMAGE_PREVIEW_PLACEHOLDER;
    }

    public String getPrevPageBtnText() {
        return PREV_PAGE_BTN_TEXT;
    }

    public String getNextPageBtnText() {
        return NEXT_PAGE_BTN_TEXT;
    }

    public String getZoomInBtnText() {
        return ZOOM_IN_BTN_TEXT;
    }

    public String getZoomOutBtnText() {
        return ZOOM_OUT_BTN_TEXT;
    }

    public String geRotateLeftText() {
        return ROTATE_LEFT_BTN_TEXT;
    }

    public String geRotateRightText() {
        return ROTATE_RIGHT_BTN_TEXT;
    }
    
     public String getOCRAllBtnTooltip() {
        return OCR_ALL_BTN_TOOLTIP;
    }
    public String getFitImageText() {
        return FIT_IMAGE_BTN_TEXT;
    }

    public String getPrevPageBtnTooltip() {
        return PREV_PAGE_BTN_TOOLTIP;
    }

    public String getNextPageBtnTooltip() {
        return NEXT_PAGE_BTN_TOOLTIP;
    }

    public String getZoomInBtnTooltip() {
        return ZOOM_IN_BTN_TOOLTIP;
    }

    public String getZoomOutBtnTooltip() {
        return ZOOM_OUT_BTN_TOOLTIP;
    }

    public String getRotateLeftBtnTooltip() {
        return ROTATE_LEFT_BTN_TOOLTIP;
    }

    public String getRotateRightBtnTooltip() {
        return ROTATE_RIGHT_BTN_TOOLTIP;
    }

    public String getFitImageBtnTooltip() {
        return FIT_IMAGE_BTN_TOOLTIP;
    }

    public String getOpenImgBtnText() {
        return OPEN_IMG_BTN_TEXT;
    }

    public String getUploadPDFBtnText() {
        return UPLOAD_PDF_BTN_TEXT;
    }

    public String getRunOCRBtnText() {
        return RUN_OCR_BTN_TEXT;
    }
    
    public String getResetAllBtnText() {
        return RESET_ALL_BTN_TEXT;
    }

    public String getQuickTipsBtntext() {
        return QUICK_TIPS_BTN_TEXT;
    }

    public String getOpenImgBtnTooltip() {
        return OPEN_IMG_BTN_TOOLTIP;
    }

    public String getUploadPDFBtnTooltip() {
        return UPLOAD_PDF_BTN_TOOLTIP;
    }

    public String getRunOCRBtnTooltip() {
        return RUN_OCR_BTN_TOOLTIP;
    }

    public String getSaveTextBtnTooltip() {
        return SAVE_TEXT_BTN_TOOLTIP;
    }

    public String getCopyTextBtnTooltip() {
        return COPY_TEXT_BTN_TOOLTIP;
    }

    public String getClearTextBtnTooltip() {
        return CLEAR_TEXT_BTN_TOOLTIP;
    }

    public String getResetAllBtnTooltip() {
        return RESET_ALL_BTN_TOOLTIP;
    }

    public String getQuickTipsBtnTooltip() {
        return QUICK_TIPS_BTN_TOOLTIP;
    }

    public String getAppTitle() {
        return APP_NAME;
    }

    public String getPlaceholderText() {
        return PLACEHOLDER_TEXT;
    }

    public String getDisplayedProfileLink() {
        return DISPLAYED_PROFILE_LINK;
    }

    public String getProfileLink() {
        return PROFILE_LINK;
    }

    public String getQuickTipsTitle() {
        return QUICK_TIPS_TITLE;
    }

    public String[] getQuickTips() {
        String[] quickTips = {
            "",
            "𝗚𝗲𝘁𝘁𝗶𝗻𝗴 𝘀𝘁𝗮𝗿𝘁𝗲𝗱 𝘄𝗶𝘁𝗵 " + APP_NAME,
            "𝖠𝗉𝗉𝗅𝗂𝖼𝖺𝗍𝗂𝗈𝗇 𝗐𝖺𝗌 𝖻𝗎𝗂𝗅𝗍 𝗐𝗂𝗍𝗁 𝖳𝖾𝗌𝗌𝟦𝖩 (𝟦.𝟧.𝟣), 𝖺 𝖩𝖺𝗏𝖺 𝗐𝗋𝖺𝗉𝗉𝖾𝗋 𝖿𝗈𝗋 𝗧𝗲𝘀𝘀𝗲𝗿𝗮𝗰𝘁 𝟰.𝟭.𝟭—",
            "𝖺𝗇 𝗈𝗉𝖾𝗇-𝗌𝗈𝗎𝗋𝖼𝖾𝖽 𝖮𝖢𝖱 𝖤𝗇𝗀𝗂𝗇𝖾.",
            "",
            "𝗣𝗮𝗿𝘁 𝗜. 𝗨𝗽𝗹𝗼𝗮𝗱 𝗶𝗺𝗮𝗴𝗲 𝗼𝗿 𝗣𝗗𝗙 𝗳𝗼𝗿 𝗢𝗖𝗥 𝗽𝗿𝗼𝗰𝗲𝘀𝘀𝗶𝗻𝗴",
            "￭ 𝖬𝗎𝗅𝗍𝗂𝗉𝗅𝖾 𝗂𝗆𝖺𝗀𝖾𝗌 (.𝗃𝗉𝗀 𝖺𝗇𝖽 .𝗉𝗇𝗀 𝖾𝗑𝗍𝖾𝗇𝗌𝗂𝗈𝗇𝗌 𝗈𝗇𝗅𝗒) 𝖼𝖺𝗇 𝖻𝖾 𝗌𝖾𝗅𝖾𝖼𝗍𝖾𝖽 𝗉𝖾𝗋 𝗎𝗉𝗅𝗈𝖺𝖽",
            "￭ 𝖮𝗇𝗅𝗒 𝟣 𝖯𝖣𝖥 𝖿𝗂𝗅𝖾 𝖼𝖺𝗇 𝖻𝖾 𝗌𝗎𝖻𝗆𝗂𝗍𝗍𝖾𝖽 𝗉𝖾𝗋 𝗎𝗉𝗅𝗈𝖺𝖽 𝖿𝗈𝗋 𝗉𝖺𝗀𝖾𝗌 𝗍𝗈 𝖻𝖾 𝗉𝗋𝗈𝖼𝖾𝗌𝗌𝖾𝖽",
            "￭ 𝖥𝗈𝗋 𝗈𝗉𝗍𝗂𝗆𝖺𝗅 𝗉𝖾𝗋𝖿𝗈𝗋𝗆𝖺𝗇𝖼𝖾, 𝗉𝗋𝖾𝖿𝖾𝗋𝗋𝖾𝖽 𝗂𝗆𝖺𝗀𝖾 𝗋𝖾𝗌𝗈𝗅𝗎𝗍𝗂𝗈𝗇 𝗂𝗌 𝟥𝟢𝟢𝖣𝖯𝖨",
            "",
            "𝗣𝗮𝗿𝘁 𝗜𝗜. 𝗖𝗼𝗻𝘁𝗿𝗼𝗹 𝗳𝘂𝗻𝗰𝘁𝗶𝗼𝗻𝗮𝗹𝗶𝘁𝗶𝗲𝘀",
            "￭ 𝖳𝗈𝗀𝗀𝗅𝖾 𝗉𝖺𝗀𝖾 𝗌𝖾𝗅𝖾𝖼𝗍𝗂𝗈𝗇: " + PREV_PAGE_BTN_TEXT + " & " + NEXT_PAGE_BTN_TEXT,
            
            "￭ 𝖳𝗈 𝗓𝗈𝗈𝗆 𝗂𝗇𝗍𝗈 𝗂𝗆𝖺𝗀𝖾: " + ZOOM_IN_BTN_TEXT,
            "￭ 𝖳𝗈 𝗓𝗈𝗈𝗆 𝗈𝗎𝗍 𝗈𝖿 𝗂𝗆𝖺𝗀𝖾: " + ZOOM_OUT_BTN_TEXT,
            
            "￭ 𝖱𝗈𝗍𝖺𝗍𝖾 𝗂𝗆𝖺𝗀𝖾 𝖺𝗇𝗍𝗂-𝖼𝗅𝗈𝖼𝗄𝗐𝗂𝗌𝖾: " + ROTATE_LEFT_BTN_TEXT,
            "￭ 𝖱𝗈𝗍𝖺𝗍𝖾 𝗂𝗆𝖺𝗀𝖾 𝖼𝗅𝗈𝖼𝗄𝗐𝗂𝗌𝖾: " + ROTATE_RIGHT_BTN_TEXT,
            "￭ 𝖥𝗂𝗍 𝗂𝗆𝖺𝗀𝖾 𝗍𝗈 𝗉𝖺𝗇𝖾𝗅: " + FIT_IMAGE_BTN_TEXT,
            "",
            "𝗣𝗮𝗿𝘁 𝗜𝗜𝗜. 𝗥𝘂𝗻𝗻𝗶𝗻𝗴 𝘁𝗵𝗲 𝗢𝗖𝗥 𝗽𝗿𝗼𝗰𝗲𝘀𝘀",
            "￭ 𝖲𝖾𝗅𝖾𝖼𝗍〚" + RUN_OCR_BTN_TEXT + "〛𝗍𝗈 𝗉𝗋𝗈𝖼𝖾𝗌𝗌 𝖼𝗎𝗋𝗋𝖾𝗇𝗍 𝗌𝖾𝗅𝖾𝖼𝗍𝗂𝗈𝗇 𝗈𝗇𝗅𝗒",
            "￭ 𝖤𝗅𝗌𝖾, 𝗉𝗋𝗈𝖼𝖾𝗌𝗌 𝖺𝗅𝗅 𝗂𝗆𝖺𝗀𝖾𝗌/𝗉𝖺𝗀𝖾𝗌 𝖺𝗍 𝖺 𝗀𝗈 𝗐𝗂𝗍𝗁〚" + OCR_ALL_BTN_TEXT + "〛𝗂𝗇𝗌𝗍𝖾𝖺𝖽",
            "",
            ""
        };
        return quickTips;
    }
}