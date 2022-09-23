package app.settings;

import java.awt.Color;

public class ColorConstants {
    private static final Color APP_BG_COLOR = new Color(91, 94, 96); // #5b5e60 | Very Dark Grey
    private static final Color APP_FONT_COLOR = new Color(255, 255, 255); // White 
    
    private static final Color SPLIT_PANE_BG_COLOR = new Color(135, 160, 192); // GRAYISH_BLUE
    private static final Color SPLIT_PANE_FONT_COLOR = new Color(32, 56, 100); // DARKER BLUE
    
    private static final Color SPLIT_PANE_PARENT_PANE_OBJ_BG_COLOR = new Color(60, 63, 65); // #3c3f41 | nearly black
   
    public Color getAppBgColor() {
        return APP_BG_COLOR;
    }
    public Color getAppFontColor() {
        return APP_FONT_COLOR;
    }
    
    public Color getSplitPaneBgColor() {
        return SPLIT_PANE_BG_COLOR;
    }
    public Color getSplitPaneFontColor() {
        return SPLIT_PANE_FONT_COLOR;
    }
    
    public Color getSplitPaneParentPaneObjBgColor() {
        return SPLIT_PANE_PARENT_PANE_OBJ_BG_COLOR;
    }
}
