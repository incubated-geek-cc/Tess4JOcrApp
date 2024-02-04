package app.settings;

public class MeasurementConstants {
    private static final int FRAME_WIDTH = 950;//880;
    private static final int FRAME_HEIGHT = 645;//625;
    
    private static final int MAX_ICON_LENGTH = 30;
    private static final int ICON_FONT_SIZE = 12;
    private static final int TEXT_FONT_SIZE = 12;
    private static final int FOOTER_FONT_SIZE = 12;
    private static final int PLACEHOLDER_FONT_SIZE = 15;
    
    private static final int DIVIDER_SIZE = 4;
    private static final double DIVIDER_LOCATION_MAIN_CONTENT = 0.65;
    private static final double RESIZE_WEIGHT_MAIN_CONTENT = 0.65;
    private static final int SPLIT_PANE_MAIN_CONTENT_BORDER_THICKNESS = 4;
    private static final int SCROLL_PANE_PADDING = 5;
    
    private static final int IMAGE_DPI = 300;
    private static final double ZOOM_FACTOR=0.8;
    
    
    
    public double getZoomFactor() {
        return ZOOM_FACTOR;
    }
    
    public int getScrollPanePadding() {
        return SCROLL_PANE_PADDING;
    }
    
    public int getSplitPaneMainContentBorderThickness() {
        return SPLIT_PANE_MAIN_CONTENT_BORDER_THICKNESS;
    }
    
    public int getImageDPI() {
        return IMAGE_DPI;
    }
    
    public double getResizeWeightMainContent() {
        return RESIZE_WEIGHT_MAIN_CONTENT;
    }
    
    public double getDividerLocationMainContent() {
        return DIVIDER_LOCATION_MAIN_CONTENT;
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