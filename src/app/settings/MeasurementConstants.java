package app.settings;

public class MeasurementConstants {
    private static final int FRAME_WIDTH = 880;
    private static final int FRAME_HEIGHT = 625;
    
    private static final int MAX_ICON_LENGTH = 30;
    
    private static final int ICON_FONT_SIZE = 12;
    private static final int TEXT_FONT_SIZE = 12;
    private static final int FOOTER_FONT_SIZE = 12;
    private static final int PLACEHOLDER_FONT_SIZE = 15;
    
    private static final int DIVIDER_SIZE = 15;
    private static final double DIVIDER_LOCATION = 0.5;
    private static final double RESIZE_WEIGHT = 0.5;
    private static final int SPLIT_PANE_BORDER_THICKNESS = 10;
    private static final int SCROLL_PANEL_PADDING = 5;
    
    private static final int IMAGE_DPI = 300;
    
    public int getScrollPanePadding() {
        return SCROLL_PANEL_PADDING;
    }
    
    public int getSplitPaneBorderThickness() {
        return SPLIT_PANE_BORDER_THICKNESS;
    }
    
    public int getImageDPI() {
        return IMAGE_DPI;
    }
    
    public double getResizeWeight() {
        return RESIZE_WEIGHT;
    }
    
    public double getDividerLocation() {
        return DIVIDER_LOCATION;
    }
    
    public int getMaxIconLength() {
        return MAX_ICON_LENGTH;
    }
    
    public int getFrameWidth() {
        return FRAME_WIDTH;
    }
    
    public int getFrameHeight() {
        return FRAME_HEIGHT;
    }
    
    public int getDividerSize() {
        return DIVIDER_SIZE;
    }
    
    public int getIconFontSize() {
        return ICON_FONT_SIZE;
    }
    
    public int getTextFontSize() {
        return TEXT_FONT_SIZE;
    }
    
    public int getFooterFontSize() {
        return FOOTER_FONT_SIZE;
    }
    
    public int getPlaceholderFontSize() {
        return PLACEHOLDER_FONT_SIZE;
    }
}
