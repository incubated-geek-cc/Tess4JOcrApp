package app.gui;

import app.settings.ColorConstants;
import app.settings.MeasurementConstants;
import app.settings.ContentConstants;
import app.settings.IconConstants;
import app.util.TextPrompt;
import com.formdev.flatlaf.FlatDarkLaf;
import app.util.TextPrompt.Show;
import app.util.UtilityManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultCaret;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

public class Main {
    // https://www.klippa.com/en/blog/information/tesseract-ocr/
    // https://www.klippa.com/en/blog/information/what-is-ocr/
    // ==================== OUTPUT LOGS  ===================================
    private static JTextArea outputLogsTextArea,ocrResultsTextArea,hocrResultsTextArea;
    private static JScrollPane scrollPaneOutputLogs,imagePreviewScrollPane,ocrTextScrollPane,hocrScrollPane,jScrollPane1InputPicsListItems,infoScrollPane;
    // ==================== OUTPUT LOGS  ===================================
    private static Tesseract tesseractInstance;
    
    static ByteArrayOutputStream ocrBos = null;
    static ZipOutputStream ocrZipOs = null;
    static ByteArrayInputStream ocrBArrIs = null;
    
    static ByteArrayOutputStream hocrBos = null;
    static ZipOutputStream hocrZipOs = null;
    static ByteArrayInputStream hocrBArrIs = null;
    
    private static IconConstants iconConstants;
    private static ContentConstants contentConstants;
    private static MeasurementConstants measurementConstants;
    private static ColorConstants colorConstants;
    private static UtilityManager utilityMgr;
    // ==================== DECLARE ALL VARIABLE CONSTANTS HERE ===================================  
    private static JFrame appFrame;
    private static JToolBar toolbar;
    private static TextPrompt ocrTextPrompt,hocrTextPrompt;
    
    private static String appIconURI,openImgIconURI,uploadPDFIconURI,runOCRIconURI,runOCRAllIconURI,resetAllIconURI,quickTipsIconURI,loadingIconURI;
        
    private static int frameWidth, frameHeight,
                       imageDPI,
                       midIconLength, maxIconLength,iconFontSize,textFontSize,placeholderFontSize,footerFontSize,
                       dividerSize,splitPaneMainContentBorderThickness,scrollPanePadding;
    
    private static double dividerLocationMainContent,resizeWeightMainContent,zoomFactor;
    
    private static String appTitle,quickTipsTitle,displayedProfileLink,profileLink,
    placeholderText,imagePreviewPlaceholder,ocrTextareaPlaceholder,hocrTextareaPlaceholder;

    private static String openImgBtnTxt,uploadPDFBtnText,runOCRBtnText,runOCRAllBtnText,resetAllBtnText,quickTipsBtnText,
    openImgBtnTooltip,uploadPDFBtnTooltip,runOCRBtnTooltip,runOCRAllBtnTooltip,saveTextBtnTooltip,copyTextBtnTooltip,resetAllBtnTooltip,quickTipsBtnTooltip;
    
    private static String prevPageBtnTooltip,nextPageBtnTooltip,zoomInBtnTooltip,zoomOutBtnTooltip,rotateLeftBtnTooltip,rotateRightBtnTooltip,fitImageBtnTooltip;

    private static Color appBgColor,appFontColor,splitPaneMainContentBgColor,splitPaneMainContentFontColor,splitPaneMainContentParentPaneObjBgColor,profileLinkColor;
    // ==================== //END DECLARE ALL VARIABLE CONSTANTS HERE ===================================
    
    private static final Cursor POINTER_CURSOR = new Cursor(Cursor.HAND_CURSOR);
    
    private static String[] MONOSPACE_DIGITS = null;
    private static String[] BOLD_DIGITS = null;
    private static String PENDING_STATUS_TEXT = null;
    private static String RUNNING_TASK_TEXT = null;
    private static final String LINE_SEPARATOR = System.lineSeparator();
    // ============================ For the main content ================================
    // ============================ GUI Components ================================
    private static JLabel imagePreview,labelEndNote,labelProfileLink,statusDisplay,paginationDisplay;

    private static JSplitPane splitPaneMainContent,rightSplitPane;
    private static DefaultListModel jListInputPicsModel;
    private static JList<String> jListInputPics;
    
    private static JPanel leftMainPanel,panelObj,ocrPanel,hocrPanel;
    private static int v1,h1,v2,h2;

    private static File browse_dir = new File(System.getProperty("user.dir"));
    private static JFileChooser selectFileChooser = null;
    private static JFileChooser saveFileChooser = null;

    private static String extractedOutput = null;
    // ============================// For the main content ================================
    
    private static JButton openImgBtn,uploadPDFBtn,
    runOCRBtn,runOCRAllBtn,
    resetAllBtn,quickTipsBtn,
    saveOCRBtn,copyOCRBtn,
    saveHOCRBtn,copyHOCRBtn,
    // ============================ For the pagination pane content =======================
    prevPageBtn,nextPageBtn,zoomInBtn,zoomOutBtn,rotateLeftBtn,rotateRightBtn,fitImageBtn;
    // ============================ // For the pagination pane content ====================

    private static JPanel paginationPanel,bottomPanel,ocrSubPanel,hocrSubPanel,infoPanel,navigationPanel;
    private static JDialog infoDialog;

    private static int maxImgWidth,maxImgHeight;

    private static Border buttonBorder,imagePreviewBorder,bottomPanelBorder,splitPaneMsinContentBorder,panelObjBorder,
    scrollPaneBorder,toolbarBorder,scrollPanePaddingBorder;
    
    private static Font iconFont,placeholderFont,textFont,footerFont;

    private static GridBagConstraints gc;
    private static double currentImageScale = 1.0;
    private static int selectedImgWidth = 0;
    private static int selectedImgHeight = 0;
    
    private static final ArrayList<String> INPUT_IMG_URI_LIST = new ArrayList<String>();
    private static FileNameExtensionFilter filter;
    private static int option,selectedIndex,totalNoOfPages;
    private static String imageURI;
   
    private static PDFRenderer pdfRenderer;
    private static JProgressBar progressBar;
    private static JTabbedPane rightMainTabbedPane;
    
    private static ImageIcon ocrIcon,hocrIcon,saveIcon,copyIcon,
    zoomInIcon,zoomOutIcon,prevPageIcon,nextPageIcon,rotateLeftIcon,rotateRightIcon,fitImageIcon;
    
    private static void addComponentsToPane(Container contentPane) {
        appIconURI = iconConstants.getAppIconURI();
        openImgIconURI = iconConstants.getOpenImgIconURI();
        uploadPDFIconURI = iconConstants.getUploadPDFIconURI();
        runOCRIconURI = iconConstants.getRunOCRIconURI();
        runOCRAllIconURI = iconConstants.getRunOCRAllIconURI();
        
        resetAllIconURI = iconConstants.getResetAllIconURI();
        quickTipsIconURI = iconConstants.getQuickTipsIconURI();
        
        frameWidth = measurementConstants.getFrameWidth();
        frameHeight = measurementConstants.getFrameHeight();

        maxIconLength = measurementConstants.getMaxIconLength();
        midIconLength = measurementConstants.getMidIconLength();
        iconFontSize = measurementConstants.getIconFontSize();
        textFontSize = measurementConstants.getTextFontSize();
        footerFontSize = measurementConstants.getFooterFontSize();
        placeholderFontSize = measurementConstants.getPlaceholderFontSize();

        dividerSize = measurementConstants.getDividerSize();
        dividerLocationMainContent = measurementConstants.getDividerLocationMainContent();
        resizeWeightMainContent = measurementConstants.getResizeWeightMainContent();
        splitPaneMainContentBorderThickness = measurementConstants.getSplitPaneMainContentBorderThickness();
        scrollPanePadding = measurementConstants.getScrollPanePadding();

        imageDPI = measurementConstants.getImageDPI();
        zoomFactor = measurementConstants.getZoomFactor();
        
        MONOSPACE_DIGITS = contentConstants.getMONOSPACE_DIGITS();
        BOLD_DIGITS = contentConstants.getBOLD_DIGITS();
        PENDING_STATUS_TEXT = contentConstants.getPENDING_STATUS_TEXT();
        RUNNING_TASK_TEXT = contentConstants.getRUNNING_TASK_TEXT();
    
        placeholderText = contentConstants.getPlaceholderText();
        displayedProfileLink = contentConstants.getDisplayedProfileLink();
        profileLink = contentConstants.getProfileLink();
        quickTipsTitle = contentConstants.getQuickTipsTitle();

        openImgBtnTxt = contentConstants.getOpenImgBtnText();
        uploadPDFBtnText = contentConstants.getUploadPDFBtnText();
        runOCRBtnText = contentConstants.getRunOCRBtnText();
        runOCRAllBtnText = contentConstants.getOCRAllBtnText();
        resetAllBtnText = contentConstants.getResetAllBtnText();
        quickTipsBtnText = contentConstants.getQuickTipsBtntext();

        openImgBtnTooltip = contentConstants.getOpenImgBtnTooltip();
        uploadPDFBtnTooltip = contentConstants.getUploadPDFBtnTooltip();
        runOCRBtnTooltip = contentConstants.getRunOCRBtnTooltip();
        runOCRAllBtnTooltip = contentConstants.getOCRAllBtnTooltip();
        
        saveTextBtnTooltip = contentConstants.getSaveTextBtnTooltip();
        copyTextBtnTooltip = contentConstants.getCopyTextBtnTooltip();
        resetAllBtnTooltip = contentConstants.getResetAllBtnTooltip();
        quickTipsBtnTooltip = contentConstants.getQuickTipsBtnTooltip();
        prevPageBtnTooltip = contentConstants.getPrevPageBtnTooltip();
        nextPageBtnTooltip = contentConstants.getNextPageBtnTooltip();
        zoomInBtnTooltip = contentConstants.getZoomInBtnTooltip();
        zoomOutBtnTooltip = contentConstants.getZoomOutBtnTooltip();
        rotateLeftBtnTooltip = contentConstants.getRotateLeftBtnTooltip();
        rotateRightBtnTooltip = contentConstants.getRotateRightBtnTooltip();
        fitImageBtnTooltip = contentConstants.getFitImageBtnTooltip();
        
        appBgColor = colorConstants.getAppBgColor();
        appFontColor = colorConstants.getAppFontColor();
        splitPaneMainContentBgColor = colorConstants.getSplitPaneBgColor();
        splitPaneMainContentFontColor = colorConstants.getSplitPaneFontColor();
        splitPaneMainContentParentPaneObjBgColor = colorConstants.getSplitPaneParentPaneObjBgColor();
        profileLinkColor = colorConstants.getProfileLinkColor();
        
        imagePreviewPlaceholder = contentConstants.getImagePreviewPlaceholder();
        ocrTextareaPlaceholder = contentConstants.getOCRTextareaPlaceholder();
        hocrTextareaPlaceholder = contentConstants.getHOCRTextareaPlaceholder();
        
        labelEndNote = new JLabel(contentConstants.getFooterNote(utilityMgr.getCurrentYear()));
        buttonBorder = BorderFactory.createEtchedBorder();
        bottomPanelBorder = BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, splitPaneMainContentParentPaneObjBgColor, splitPaneMainContentParentPaneObjBgColor), BorderFactory.createEmptyBorder(1, 1, 1, 1));
        toolbarBorder = BorderFactory.createBevelBorder(BevelBorder.RAISED, appBgColor, appBgColor);
        panelObjBorder = toolbarBorder;

        imagePreviewBorder = new EtchedBorder(EtchedBorder.RAISED, splitPaneMainContentParentPaneObjBgColor, splitPaneMainContentParentPaneObjBgColor);
        scrollPanePaddingBorder = BorderFactory.createLineBorder(splitPaneMainContentBgColor, scrollPanePadding);
        splitPaneMsinContentBorder = BorderFactory.createLineBorder(appBgColor, splitPaneMainContentBorderThickness);
        scrollPaneBorder = new SoftBevelBorder(BevelBorder.LOWERED, splitPaneMainContentBgColor, splitPaneMainContentBgColor, splitPaneMainContentBgColor, splitPaneMainContentBgColor);
        
        iconFont = new Font("Arial Unicode MS", Font.ROMAN_BASELINE, iconFontSize);
        textFont = new Font("Arial Nova Light", Font.ROMAN_BASELINE, textFontSize);
        placeholderFont = new Font("Segoe UI Emoji", Font.ROMAN_BASELINE, placeholderFontSize);
        footerFont = new Font("Arial Unicode MS", Font.ROMAN_BASELINE, footerFontSize);

        labelEndNote.putClientProperty("FlatLaf.styleClass", "default");
        labelEndNote.setForeground(appFontColor);
        labelEndNote.setFont(footerFont);

        contentPane = appFrame.getContentPane();
        contentPane.setLayout(new BorderLayout());

        toolbar = new JToolBar(JToolBar.HORIZONTAL);
        toolbar.setBorder(toolbarBorder);
        toolbar.setBackground(appBgColor);
        toolbar.setLayout(new BoxLayout(toolbar,BoxLayout.X_AXIS));
        toolbar.setAlignmentY(SwingConstants.CENTER);
        toolbar.setRollover(true);
        
        labelProfileLink = new JLabel(" " + displayedProfileLink);
        labelProfileLink.putClientProperty("FlatLaf.styleClass", "default");
        labelProfileLink.setForeground(profileLinkColor);
        labelProfileLink.setCursor(POINTER_CURSOR);

        openImgBtn = new JButton(openImgBtnTxt);
        uploadPDFBtn = new JButton(uploadPDFBtnText);
        runOCRBtn = new JButton(runOCRBtnText);
        runOCRAllBtn = new JButton(runOCRAllBtnText);
        resetAllBtn = new JButton(resetAllBtnText);
        quickTipsBtn = new JButton(quickTipsBtnText);

        prevPageBtn = new JButton();
        nextPageBtn = new JButton();
        zoomInBtn = new JButton();
        zoomOutBtn = new JButton();
        rotateLeftBtn = new JButton();
        rotateRightBtn = new JButton();
        fitImageBtn = new JButton();
        
        JButton[] mainButtonsArr = {
            openImgBtn, uploadPDFBtn,
            runOCRBtn, runOCRAllBtn,
            resetAllBtn,
            quickTipsBtn,
            prevPageBtn, nextPageBtn,
            zoomInBtn, zoomOutBtn,
            rotateLeftBtn, rotateRightBtn,
            fitImageBtn
        };
        for (JButton iconButton : mainButtonsArr) {
            iconButton.setBorder(buttonBorder);
            iconButton.setFont(iconFont);
            iconButton.setForeground(appFontColor);
            iconButton.setCursor(POINTER_CURSOR);
            iconButton.setHorizontalTextPosition(SwingConstants.CENTER);
            iconButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        }
        
        loadingIconURI=iconConstants.getLoadingIconURI();
        statusDisplay=new JLabel(contentConstants.getTASK_STATUS_TEXT());
        statusDisplay.setForeground(appFontColor);
        statusDisplay.setVerticalAlignment(SwingConstants.CENTER);
        statusDisplay.setHorizontalAlignment(SwingConstants.RIGHT);
        try {
            statusDisplay.setIcon(utilityMgr.getDataURIToImageIcon(loadingIconURI, maxIconLength, maxIconLength));
            openImgBtn.setIcon(utilityMgr.getDataURIToImageIcon(openImgIconURI, maxIconLength, maxIconLength));
            uploadPDFBtn.setIcon(utilityMgr.getDataURIToImageIcon(uploadPDFIconURI, maxIconLength, maxIconLength));
            runOCRBtn.setIcon(utilityMgr.getDataURIToImageIcon(runOCRIconURI, maxIconLength, maxIconLength));
            runOCRAllBtn.setIcon(utilityMgr.getDataURIToImageIcon(runOCRAllIconURI, maxIconLength, maxIconLength));
            resetAllBtn.setIcon(utilityMgr.getDataURIToImageIcon(resetAllIconURI, maxIconLength, maxIconLength));
            quickTipsBtn.setIcon(utilityMgr.getDataURIToImageIcon(quickTipsIconURI, maxIconLength, maxIconLength));
            
            ocrIcon=utilityMgr.getDataURIToImageIcon(iconConstants.getPLAIN_TEXT_URI(), midIconLength, midIconLength);
            hocrIcon=utilityMgr.getDataURIToImageIcon(iconConstants.getHTML_TEXT_URI(), midIconLength, midIconLength);
            
            saveIcon=utilityMgr.getDataURIToImageIcon(iconConstants.getSAVE_ICON_URI(), midIconLength, midIconLength);
            copyIcon=utilityMgr.getDataURIToImageIcon(iconConstants.getCOPY_ICON_URI(), midIconLength, midIconLength);
            
            zoomInIcon=utilityMgr.getDataURIToImageIcon(iconConstants.getZOOM_IN_URI(), midIconLength, midIconLength);
            zoomOutIcon=utilityMgr.getDataURIToImageIcon(iconConstants.getZOOM_OUT_URI(), midIconLength, midIconLength);
            prevPageIcon=utilityMgr.getDataURIToImageIcon(iconConstants.getPREV_PAGE_URI(), midIconLength, midIconLength);
            nextPageIcon=utilityMgr.getDataURIToImageIcon(iconConstants.getNEXT_PAGE_URI(), midIconLength, midIconLength);
            rotateLeftIcon=utilityMgr.getDataURIToImageIcon(iconConstants.getROTATE_LEFT_URI(), midIconLength, midIconLength);
            rotateRightIcon=utilityMgr.getDataURIToImageIcon(iconConstants.getROTATE_RIGHT_URI(), midIconLength, midIconLength);
            fitImageIcon=utilityMgr.getDataURIToImageIcon(iconConstants.getFIT_IMAGE_URI(), midIconLength, midIconLength);
        } catch (IOException e) {
            utilityMgr.getLogger().log(Level.SEVERE, null, e);
        }
        
        prevPageBtn.setIcon(prevPageIcon);
        nextPageBtn.setIcon(nextPageIcon);
        zoomInBtn.setIcon(zoomInIcon);
        zoomOutBtn.setIcon(zoomOutIcon);
        rotateLeftBtn.setIcon(rotateLeftIcon);
        rotateRightBtn.setIcon(rotateRightIcon);
        fitImageBtn.setIcon(fitImageIcon);
        
        toolbar.addSeparator();
        toolbar.add(openImgBtn);
        toolbar.add(uploadPDFBtn);
        toolbar.addSeparator();
        toolbar.add(runOCRBtn);
        toolbar.add(runOCRAllBtn);
        toolbar.addSeparator();
        toolbar.add(resetAllBtn);
        toolbar.addSeparator();
        toolbar.add(quickTipsBtn);
        toolbar.addSeparator();
        
        progressBar = new JProgressBar();
        progressBar.setBorderPainted(true);
        progressBar.setStringPainted(true);
        progressBar.setString(PENDING_STATUS_TEXT);
        toolbar.add(statusDisplay);
        toolbar.add(progressBar);
        toolbar.addSeparator();
        
        contentPane.add(toolbar, BorderLayout.NORTH);

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setFont(footerFont);
        bottomPanel.setBorder(bottomPanelBorder);
        bottomPanel.setBackground(appBgColor);
        bottomPanel.setForeground(appFontColor);
        bottomPanel.add(labelEndNote);
        bottomPanel.add(labelProfileLink);
        
        contentPane.add(bottomPanel, BorderLayout.SOUTH);
        // =============================== Split Panel Content ==================================
        v1 = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
        h1 = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;

        leftMainPanel = new JPanel();
        leftMainPanel.setLayout(new BorderLayout());
        imagePreview = new JLabel(imagePreviewPlaceholder);
        imagePreview.setBackground(splitPaneMainContentBgColor);
        imagePreview.setForeground(splitPaneMainContentFontColor);
        imagePreview.setFont(placeholderFont);
        imagePreview.setBorder(scrollPanePaddingBorder);
        imagePreview.setHorizontalAlignment(JLabel.CENTER);
        imagePreview.setHorizontalTextPosition(JLabel.CENTER);
        imagePreviewScrollPane = new JScrollPane(imagePreview, v1, h1);

        paginationDisplay = new JLabel();
        paginationDisplay.setForeground(appFontColor);
        paginationDisplay.setBackground(splitPaneMainContentParentPaneObjBgColor);
        paginationDisplay.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        paginationDisplay.setHorizontalAlignment(JLabel.CENTER);
        paginationDisplay.setHorizontalTextPosition(JLabel.CENTER);

        leftMainPanel.add(paginationDisplay, BorderLayout.NORTH);
        leftMainPanel.add(imagePreviewScrollPane, BorderLayout.CENTER);
        navigationPanel = new JPanel();
        navigationPanel.setForeground(appFontColor);
        navigationPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        navigationPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        navigationPanel.add(new JLabel("Controls  "));
        
        JButton[] controlBtns = {
            prevPageBtn,nextPageBtn,
            zoomInBtn,zoomOutBtn,
            rotateLeftBtn,rotateRightBtn,
            fitImageBtn
        };
        
        for(JButton controlBtn:controlBtns) {
            controlBtn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedSoftBevelBorder(), BorderFactory.createEmptyBorder(2, 2, 2, 2)));
            navigationPanel.add(controlBtn);
        }
        
        
        leftMainPanel.add(navigationPanel, BorderLayout.SOUTH);
        // ================================= Set up page navigation panel =============================
        jListInputPicsModel = new DefaultListModel<>();
        jListInputPics = new JList<>(jListInputPicsModel);
        jScrollPane1InputPicsListItems = new JScrollPane(jListInputPics, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jListInputPics.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jListInputPics.setEnabled(false);

        paginationPanel = new JPanel();
        paginationPanel.setBackground(appBgColor);
        paginationPanel.setBorder(toolbarBorder);
        
        paginationPanel.setLayout(new GridLayout());
        paginationPanel.add(jScrollPane1InputPicsListItems);
        
        paginationDisplay.setText(placeholderText);
        jListInputPicsModel.addElement(placeholderText);
        jListInputPics.setSelectedIndex(0);

        leftMainPanel.add(paginationPanel, BorderLayout.WEST);
        v2 = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
        h2 = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

        ocrPanel = new JPanel();
        ocrPanel.setLayout(new BorderLayout());
        
        hocrPanel = new JPanel();
        hocrPanel.setLayout(new BorderLayout());
        
        ocrResultsTextArea = new JTextArea();
        hocrResultsTextArea = new JTextArea();
        JTextArea[] tas = {
            hocrResultsTextArea,ocrResultsTextArea
        };
        for(JTextArea ta:tas) {
            ta.setFont(textFont);
            ta.setBorder(scrollPanePaddingBorder);
            ta.setForeground(Color.BLACK);
            ta.setBackground(appFontColor);
            ta.setLineWrap(true);
            ta.setWrapStyleWord(true);
            ta.setEditable(false);
            ta.setFocusable(false);
            
            ((DefaultCaret)ta.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
            ta.setOpaque(true);
        }
        
        ocrTextPrompt = new TextPrompt(ocrTextareaPlaceholder, ocrResultsTextArea, Show.FOCUS_LOST);
        ocrTextScrollPane = new JScrollPane(ocrResultsTextArea, v2, h2);
        
        hocrTextPrompt = new TextPrompt(hocrTextareaPlaceholder, hocrResultsTextArea, Show.FOCUS_LOST);
        hocrScrollPane = new JScrollPane(hocrResultsTextArea, v2, h2);
        
        TextPrompt[] tps = {
            ocrTextPrompt,hocrTextPrompt
        };
        for(TextPrompt tp:tps) {
            tp.changeAlpha(128);
            tp.changeStyle(Font.BOLD);
            tp.setHorizontalAlignment(JLabel.CENTER);
            tp.setHorizontalTextPosition(JLabel.CENTER);
        }
        saveOCRBtn = new JButton("Save  ");
        saveOCRBtn.setIcon(saveIcon);
        copyOCRBtn = new JButton("Copy  ");
        copyOCRBtn.setIcon(copyIcon);
        
        saveHOCRBtn = new JButton("Save  ");
        saveHOCRBtn.setIcon(saveIcon);
        copyHOCRBtn = new JButton("Copy  ");
        copyHOCRBtn.setIcon(copyIcon);
        
        JButton[] subBtns={
            saveOCRBtn,saveHOCRBtn,
            copyOCRBtn,copyHOCRBtn
        };
        for(JButton subBtn:subBtns) {
            subBtn.setBorder(BorderFactory.createRaisedSoftBevelBorder());
            subBtn.setFont(iconFont);
            subBtn.setForeground(appFontColor);
            subBtn.setCursor(POINTER_CURSOR);
            subBtn.setHorizontalTextPosition(SwingConstants.RIGHT);
        }
        ocrSubPanel = new JPanel();
        ocrSubPanel.add(saveOCRBtn);
        ocrSubPanel.add(copyOCRBtn);
        ocrPanel.add(ocrSubPanel,BorderLayout.NORTH);
        ocrPanel.add(ocrTextScrollPane,BorderLayout.CENTER);
        
        hocrSubPanel = new JPanel();        
        hocrSubPanel.add(saveHOCRBtn);
        hocrSubPanel.add(copyHOCRBtn);
        hocrPanel.add(hocrSubPanel,BorderLayout.NORTH);
        hocrPanel.add(hocrScrollPane,BorderLayout.CENTER);
        
        JPanel[] subpanels = {
            ocrSubPanel,hocrSubPanel
        };
        for(JPanel subpanel:subpanels) {
            subpanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        }
        
        JScrollPane[] sps={
            imagePreviewScrollPane,ocrTextScrollPane,hocrScrollPane
        };
        for(JScrollPane sp:sps) {
            sp.setBorder(scrollPaneBorder);
            sp.setForeground(splitPaneMainContentFontColor);
        }
        
        splitPaneMainContent = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        JPanel[] mainPanes = {
            leftMainPanel,ocrPanel,hocrPanel
        };
        for(JPanel mainPane:mainPanes) {
            mainPane.setBackground(splitPaneMainContentBgColor);
            mainPane.setBorder(imagePreviewBorder);
        }
        splitPaneMainContent.setBorder(splitPaneMsinContentBorder);
        splitPaneMainContent.setBackground(appBgColor);
        splitPaneMainContent.setDividerSize(dividerSize);
        splitPaneMainContent.setResizeWeight(resizeWeightMainContent);
        splitPaneMainContent.setDividerLocation(dividerLocationMainContent);
        splitPaneMainContent.setContinuousLayout(true);
        
        splitPaneMainContent.setLeftComponent(leftMainPanel);
        leftMainPanel.setPreferredSize(new Dimension(frameWidth/2,frameHeight));
        rightMainTabbedPane = new JTabbedPane();
        rightMainTabbedPane.setPreferredSize(new Dimension(frameWidth/2,frameHeight*(3/4)));
        scrollPaneOutputLogs.setPreferredSize(new Dimension(frameWidth/2,frameHeight*(1/4)));
        rightMainTabbedPane.addTab("OCR", ocrIcon, ocrPanel); 
        rightMainTabbedPane.addTab("HOCR", hocrIcon, hocrPanel); 
        
        rightSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        rightSplitPane.setPreferredSize(new Dimension(frameWidth/2,frameHeight));
        
        rightSplitPane.setBorder(splitPaneMsinContentBorder);
        rightSplitPane.setBackground(appBgColor);
        rightSplitPane.setDividerSize(dividerSize);
        rightSplitPane.setBorder(splitPaneMsinContentBorder);
        rightSplitPane.setResizeWeight(resizeWeightMainContent);
        rightSplitPane.setDividerLocation(dividerLocationMainContent);
        rightSplitPane.setContinuousLayout(true);
        
        rightSplitPane.setTopComponent(rightMainTabbedPane);
        rightSplitPane.setBottomComponent(scrollPaneOutputLogs);
                
        splitPaneMainContent.setRightComponent(rightSplitPane);
        
        panelObj = new JPanel();
        panelObj.setLayout(new BorderLayout());
        panelObj.setBackground(splitPaneMainContentBgColor);
        panelObj.setBorder(panelObjBorder);
        panelObj.add(splitPaneMainContent, BorderLayout.CENTER);
        
        leftMainPanel.setOpaque(true);
        paginationDisplay.setOpaque(true);
        imagePreview.setOpaque(true);
        
        contentPane.add(panelObj, BorderLayout.CENTER);

        openImgBtn.setToolTipText(openImgBtnTooltip);
        uploadPDFBtn.setToolTipText(uploadPDFBtnTooltip);
        runOCRBtn.setToolTipText(runOCRBtnTooltip);
        runOCRAllBtn.setToolTipText(runOCRAllBtnTooltip);
        
        saveOCRBtn.setToolTipText(saveTextBtnTooltip);
        copyOCRBtn.setToolTipText(copyTextBtnTooltip);
        
        saveHOCRBtn.setToolTipText(saveTextBtnTooltip);
        copyHOCRBtn.setToolTipText(copyTextBtnTooltip);
        
        resetAllBtn.setToolTipText(resetAllBtnTooltip);
        quickTipsBtn.setToolTipText(quickTipsBtnTooltip);

        prevPageBtn.setToolTipText(prevPageBtnTooltip);
        nextPageBtn.setToolTipText(nextPageBtnTooltip);
        zoomInBtn.setToolTipText(zoomInBtnTooltip);
        zoomOutBtn.setToolTipText(zoomOutBtnTooltip);
        rotateLeftBtn.setToolTipText(rotateLeftBtnTooltip);
        rotateRightBtn.setToolTipText(rotateRightBtnTooltip);
        fitImageBtn.setToolTipText(fitImageBtnTooltip);
    }
    
    private static void resetAllAction() {
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            @Override
            protected Boolean doInBackground() {
                jListInputPicsModel.clear();
                INPUT_IMG_URI_LIST.clear();
                currentImageScale = 1.0;
                selectedImgWidth = 0;
                selectedImgHeight = 0;

                selectFileChooser = null;
                saveFileChooser = null;
                extractedOutput = null;
                return true;
            }

            @Override
            protected void done() { // Can safely update the GUI from this method.
                try {
                    boolean status = get(); // Retrieve the return value of doInBackground.
                    if (status) {
                        jListInputPics.clearSelection();
                        paginationDisplay.setText(placeholderText);
                        jListInputPicsModel.addElement(placeholderText);
                        jListInputPics.setSelectedIndex(0);

                        imagePreview.setIcon(null);
                        imagePreview.setText(imagePreviewPlaceholder);
                        ocrResultsTextArea.setText("");
                        hocrResultsTextArea.setText("");

                        setSelectionStatus();
                    }
                } catch (InterruptedException | ExecutionException e) {
                    utilityMgr.getLogger().log(Level.SEVERE, null, e);
                }
            }
        };
        worker.execute();
    }

    private static void openImageAction() {
        SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
            @Override
            protected Boolean doInBackground() {
                File selectedFile = null;
                File[] selectedFiles = null;

                selectFileChooser = new JFileChooser();
                selectFileChooser.setCurrentDirectory(browse_dir);
                selectFileChooser.setDialogTitle("Select Input Image(s):");
                selectFileChooser.setMultiSelectionEnabled(true);
                selectFileChooser.setAcceptAllFileFilterUsed(false);
                filter = new FileNameExtensionFilter("JPG and PNG Images", "jpg", "png");
                selectFileChooser.addChoosableFileFilter(filter);

                option = selectFileChooser.showOpenDialog(appFrame);
                if (option == JFileChooser.APPROVE_OPTION) {
                    selectedFiles = selectFileChooser.getSelectedFiles();
                    totalNoOfPages = selectedFiles.length;
                    for (int p = 0; p < totalNoOfPages; p++) {
                        try { // FOR-EACH PAGE
                            selectedFile = selectedFiles[p];
                            imageURI = utilityMgr.getFileToDataURI(selectedFile);
                            INPUT_IMG_URI_LIST.add(imageURI);
                            publish(p); // to be received by process | 2nd parameter
                            jListInputPicsModel.addElement("Page " + (p+1));
                        } catch (IOException ex) {
                            utilityMgr.getLogger().log(Level.SEVERE, null, ex);
                        }
                    }
                    return true;
                }
                return false;
            }

            @Override
            protected void done() { // Can safely update the GUI from this method.
                try {
                    boolean status = get(); // Retrieve the return value of doInBackground.
                    if (status) {
                        browse_dir = selectFileChooser.getCurrentDirectory();
                        updatePreviewedPageNo();
                        setSelectionStatus();
                        JOptionPane.showMessageDialog(appFrame, 
                            contentConstants.getDISPLAY_UPLOAD_COMPLETION_DIALOG_MSG(), 
                            contentConstants.getDIALOG_TITLE(), 
                            JOptionPane.PLAIN_MESSAGE);
                    } else {
                        paginationDisplay.setText(placeholderText);
                        jListInputPicsModel.addElement(placeholderText);
                        jListInputPics.setSelectedIndex(0);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    utilityMgr.getLogger().log(Level.SEVERE, null, e);
                }
            }

            @Override
            protected void process(List<Integer> chunks) { // Can safely update the GUI from this method.
                int mostRecentValue = chunks.get(chunks.size() - 1);
                selectedIndex = mostRecentValue;
                jListInputPics.setSelectedIndex(selectedIndex);
                renderPreviewImage();
            }
        };
        worker.execute();
    }
    private static void loadPDFAction() {
        SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
            @Override
            protected Boolean doInBackground() {
                File selectedFile = null;
                PDDocument document = null;
                Splitter splitter = null;
                List<PDDocument> pages = null;
                PDDocument page = null;
                
                BufferedImage tempPageImg = null;
                ByteArrayOutputStream tempBos = null;

                selectFileChooser = new JFileChooser();
                selectFileChooser.setCurrentDirectory(browse_dir);
                selectFileChooser.setDialogTitle("Select PDF File:");
                selectFileChooser.setMultiSelectionEnabled(false);
                selectFileChooser.setAcceptAllFileFilterUsed(false);
                filter = new FileNameExtensionFilter("Pdf File (.pdf)", "pdf");
                selectFileChooser.addChoosableFileFilter(filter);

                option = selectFileChooser.showOpenDialog(appFrame);
                if (option == JFileChooser.APPROVE_OPTION) {
                    selectedFile = selectFileChooser.getSelectedFile();
                    try {
                        document = Loader.loadPDF(selectedFile);
                        totalNoOfPages=document.getNumberOfPages();
                        splitter = new Splitter();
                        pages = splitter.split(document);
                        for (int p = 0; p < totalNoOfPages; p++) { // FOR-EACH FILE
                            page = pages.get(p);
                            pdfRenderer = new PDFRenderer(page);
                            tempPageImg = pdfRenderer.renderImageWithDPI(0, imageDPI, ImageType.RGB);
                            tempBos = new ByteArrayOutputStream();
                            ImageIOUtil.writeImage(tempPageImg, "jpg", tempBos, imageDPI);
                            byte[] fileData=tempBos.toByteArray();
                            imageURI = Base64.getEncoder().encodeToString(fileData);
                            INPUT_IMG_URI_LIST.add(imageURI);
                            publish(p); // to be received by process | 2nd parameter
                            jListInputPicsModel.addElement("Page " + (p+1));
                        }
                    } catch (IOException ex) {
                        utilityMgr.getLogger().log(Level.SEVERE, null, ex);
                    }
                    return true;
                }
                return false;
            }

            @Override
            protected void done() { // Can safely update the GUI from this method.
                try {
                    boolean status = get(); // Retrieve the return value of doInBackground.
                    if (status) {
                        browse_dir = selectFileChooser.getCurrentDirectory();
                        updatePreviewedPageNo();
                        setSelectionStatus();
                        JOptionPane.showMessageDialog(appFrame, 
                            contentConstants.getDISPLAY_UPLOAD_COMPLETION_DIALOG_MSG(), 
                            contentConstants.getDIALOG_TITLE(), 
                            JOptionPane.PLAIN_MESSAGE);
                    } else {
                        paginationDisplay.setText(placeholderText);
                        jListInputPicsModel.addElement(placeholderText);
                        jListInputPics.setSelectedIndex(0);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    utilityMgr.getLogger().log(Level.SEVERE, null, e);
                }
            }

            @Override
            protected void process(List<Integer> chunks) { // Can safely update the GUI from this method.
                int mostRecentValue = chunks.get(chunks.size() - 1);
                selectedIndex = mostRecentValue;
                jListInputPics.setSelectedIndex(selectedIndex);
                renderPreviewImage();
            }
        };
        worker.execute();
    }
    
    private static void renderPreviewImage() {
        imageURI = INPUT_IMG_URI_LIST.get(selectedIndex);
        if (imageURI != null) {
            imagePreview.setIcon(null);
            imagePreview.setText("");
            try {
                byte[] fileBytes = Base64.getDecoder().decode(imageURI);
                Image img = ImageIO.read(new ByteArrayInputStream(fileBytes));
                ImageIcon imgIcon = new ImageIcon(img);

                BufferedImage ipImg = utilityMgr.getImageIconToBufferedImg(imgIcon);

                int iconWidth = imgIcon.getIconWidth();
                int iconHeight = imgIcon.getIconHeight();
                selectedImgWidth = iconWidth;
                selectedImgHeight = iconHeight;

                int iconLength = iconWidth;
                int maxImgLength = maxImgWidth;
                if (iconHeight > iconWidth) {
                    iconLength = iconHeight;
                    maxImgLength = maxImgHeight;
                }
                double ratio = (maxImgLength * 1.0) / (iconLength * 1.0);
                long newWidth = (long) (Math.round(ratio * iconWidth) * currentImageScale);
                long newHeight = (long) (Math.round(ratio * iconHeight) * currentImageScale);
                /*
                 //  IMAGE ENHANCEMENTS
                 BufferedImage bImg = utilityMgr.getGrayscaledBufferImg(ipImg);
                 BufferedImage bImg = utilityMgr.getProcessedBufferImg(ipImg);
                 BufferedImage bImg = utilityMgr.medianFilter(ipImg);
                 Image outputImg = bImg.getScaledInstance((int) newWidth, (int) newHeight, Image.SCALE_SMOOTH);
                 */
                Image outputImg = ipImg.getScaledInstance((int) newWidth, (int) newHeight, Image.SCALE_SMOOTH);
                imgIcon.setImage(outputImg);
                imagePreview.setIcon(imgIcon);
            } catch (IOException ex) {
                utilityMgr.getLogger().log(Level.SEVERE, null, ex);
            } finally {
                updatePreviewedPageNo();
            }
        }
    }
    private static void updatePreviewedPageNo() {
        String totalPagesMonospaceStr = "";
        String totalPages = jListInputPicsModel.getSize() + "";
        String[] totalPagesStr = totalPages.split("");
        for (String c : totalPagesStr) {
            totalPagesMonospaceStr += MONOSPACE_DIGITS[Integer.parseInt(c)];
        }
        String currentPageBoldStr = "";
        String currentPage = (selectedIndex + 1) + "";
        String[] currentPageStr = currentPage.split("");
        for (String c : currentPageStr) {
            currentPageBoldStr += BOLD_DIGITS[Integer.parseInt(c)];
        }
        String displayStr = contentConstants.getPaginationDisplayText(currentPageBoldStr,totalPagesMonospaceStr);
        paginationDisplay.setText(displayStr);
    }

    private static void fitImage() {
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            @Override
            protected Boolean doInBackground() {
                currentImageScale = 1.0;
                return true;
            }

            @Override
            protected void done() { // Can safely update the GUI from this method.
                try {
                    boolean status = get(); // Retrieve the return value of doInBackground.
                    if (status) {
                        renderPreviewImage();
                    }
                } catch (InterruptedException | ExecutionException e) {
                    utilityMgr.getLogger().log(Level.SEVERE, null, e);
                }
            }
        };
        worker.execute();
    }
    
    private static void zoomImage(boolean zoomIn) {
        SwingWorker<Boolean, Boolean> worker = new SwingWorker<Boolean, Boolean>() {
            @Override
            protected Boolean doInBackground() {
                if(zoomIn) {
                    int selectedImgLength = selectedImgWidth;
                    int maxLength = maxImgWidth;
                    if (selectedImgHeight > selectedImgWidth) {
                        selectedImgLength = selectedImgHeight;
                        maxLength = maxImgHeight;
                    }
                    int updatedImgLength = (int) ((currentImageScale * (1.0/zoomFactor) ) * maxLength);
                    if (updatedImgLength < selectedImgLength) {
                        currentImageScale = currentImageScale * (1.0/zoomFactor);
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    if (currentImageScale > Math.pow(zoomFactor, 4)) { 
                        currentImageScale = currentImageScale * zoomFactor;
                        return true;
                    } else {
                        return false;
                    }
                }
            }

            @Override
            protected void done() { // Can safely update the GUI from this method.
                try {
                    boolean status = get(); // Retrieve the return value of doInBackground.
                    if (status) {
                        renderPreviewImage();
                        utilityMgr.outputConsoleLogsBreakline("Image zooms "+ ( zoomIn ? "in" : "out") );
                    } else {
                        utilityMgr.outputConsoleLogsBreakline("Can't zoom " + ( zoomIn ? "in" : "out") + " any further");
                    }
                } catch (InterruptedException | ExecutionException e) {
                    utilityMgr.getLogger().log(Level.SEVERE, null, e);
                }
            }
        };
        worker.execute();
    }  
    private static void toggleItemAction(boolean togglePrev) {
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            @Override
            protected Boolean doInBackground() {
                if(togglePrev) {
                    if (selectedIndex > 0) {
                        selectedIndex = selectedIndex - 1;
                        return true;
                    }
                } else {
                    if (selectedIndex < (jListInputPicsModel.getSize() - 1)) {
                        selectedIndex = selectedIndex + 1;
                        return true;
                    }
                }
                return false;
            }

            @Override
            protected void done() { // Can safely update the GUI from this method.
                try {
                    boolean status = get(); // Retrieve the return value of doInBackground.
                    if (status) {
                        jListInputPics.setSelectedIndex(selectedIndex);
                        renderPreviewImage();
                        updatePreviewedPageNo();
                    }
                } catch (InterruptedException | ExecutionException e) {
                    utilityMgr.getLogger().log(Level.SEVERE, null, e);
                }
            }
        };
        worker.execute();
    }
    private static void rotateImgAction(boolean isClockwise) {
        SwingWorker<String, String> worker = new SwingWorker<String, String>() {
            double rotationDegree=-90.0d;
            @Override
            protected String doInBackground() {
                if(isClockwise) {
                    rotationDegree=90.0d;
                }
                String rotatedImageURI = null;
                imageURI = INPUT_IMG_URI_LIST.get(selectedIndex);
                if (imageURI != null) {
                    try {
                        byte[] fileBytes = Base64.getDecoder().decode(imageURI);
                        Image img = ImageIO.read(new ByteArrayInputStream(fileBytes));
                        ImageIcon imgIcon = new ImageIcon(img);
                        BufferedImage ipImg = utilityMgr.getImageIconToBufferedImg(imgIcon);
                        BufferedImage rotatedRImg = utilityMgr.getRotatedBufferImg(ipImg, rotationDegree);
                        rotatedImageURI = utilityMgr.getBufferImgToDataURI(rotatedRImg, "jpg");
                        INPUT_IMG_URI_LIST.set(selectedIndex, rotatedImageURI);
                    } catch (IOException e) {
                        utilityMgr.getLogger().log(Level.SEVERE, null, e);
                    }
                }
                return rotatedImageURI;
            }

            @Override
            protected void done() { // Can safely update the GUI from this method.
                try {
                    imageURI = get(); // Retrieve the return value of doInBackground.
                    renderPreviewImage();
                } catch (InterruptedException | ExecutionException e) {
                    utilityMgr.getLogger().log(Level.SEVERE, null, e);
                }
            }
        };
        worker.execute();
    }
    
    private static void copyTextToClipboardAction() {
        String str = ocrResultsTextArea.getText();
        ocrResultsTextArea.selectAll();
        copyOCRBtn.transferFocusBackward();
        StringSelection stringSelection = new StringSelection(str);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
    private static void copyHTMLToClipboardAction() {
        String str = hocrResultsTextArea.getText();
        hocrResultsTextArea.selectAll();
        copyHOCRBtn.transferFocusBackward();
        StringSelection stringSelection = new StringSelection(str);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
    
    private static void saveTextAction() {
        saveFileChooser = new JFileChooser();
        saveFileChooser.setDialogTitle("Save OCR Output To File:");
        saveFileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        saveFileChooser.setCurrentDirectory(browse_dir);
        saveFileChooser.setMultiSelectionEnabled(false);
        saveFileChooser.setFileFilter(new FileNameExtensionFilter("Zip Archive (*.zip)", "zip"));
        
        String destinationFilename="TesseractOCR_Output_" + utilityMgr.getCurrentTimeStamp() + ".zip";
        File ocrOutputFile = new File(destinationFilename);
        saveFileChooser.setSelectedFile(ocrOutputFile);
        
        option = saveFileChooser.showSaveDialog(appFrame);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = saveFileChooser.getSelectedFile();
            if (selectedFile != null) {
                ocrOutputFile = selectedFile;
            }
            try {
                if(ocrBos != null) {
                    browse_dir = saveFileChooser.getCurrentDirectory();
                    String outputFilepath = ocrOutputFile.getAbsolutePath();
                    File resultFile=new File(outputFilepath);
                    utilityMgr.convertInputStreamToFileOutputStream(ocrBos.toByteArray(), resultFile);
                    utilityMgr.outputConsoleLogsBreakline("Zip file: " + resultFile.getName() + " has been saved");
                    utilityMgr.outputConsoleLogsBreakline("at: " + outputFilepath);
                    
                    Desktop.getDesktop().open(resultFile);
                }
            } catch (IOException e) {
                utilityMgr.getLogger().log(Level.SEVERE, null, e);
            }
        }
    }
    
    private static void saveHTMLAction() {
        saveFileChooser = new JFileChooser();
        saveFileChooser.setDialogTitle("Save HOCR Output To File:");
        saveFileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        saveFileChooser.setCurrentDirectory(browse_dir);
        saveFileChooser.setMultiSelectionEnabled(false);
        saveFileChooser.setFileFilter(new FileNameExtensionFilter("Zip Archive (*.zip)", "zip"));
        
        String destinationFilename="TesseractHOCR_Output_" + utilityMgr.getCurrentTimeStamp() + ".zip";
        File hocrOutputFile = new File(destinationFilename);
        saveFileChooser.setSelectedFile(hocrOutputFile);
        
        option = saveFileChooser.showSaveDialog(appFrame);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = saveFileChooser.getSelectedFile();
            if (selectedFile != null) {
                hocrOutputFile = selectedFile;
            }
            try {
                if(hocrBos != null) {
                    browse_dir = saveFileChooser.getCurrentDirectory();
                    String outputFilepath = hocrOutputFile.getAbsolutePath();
                    File resultFile=new File(outputFilepath);
                    utilityMgr.convertInputStreamToFileOutputStream(hocrBos.toByteArray(), resultFile);
                    utilityMgr.outputConsoleLogsBreakline("Zip file: " + resultFile.getName() + " has been saved");
                    utilityMgr.outputConsoleLogsBreakline("at: " + outputFilepath);
                    
                    Desktop.getDesktop().open(resultFile);
                }
            } catch (IOException e) {
                utilityMgr.getLogger().log(Level.SEVERE, null, e);
            }
        }
    }
    
    private static void viewInfoAction() {
        infoDialog = new JDialog(appFrame, quickTipsTitle, true);

        infoPanel = new JPanel();
        infoPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        infoPanel.setLayout(new GridBagLayout());
        infoPanel.setForeground(appFontColor);
        
        gc = new GridBagConstraints();
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.ipadx = 5;
        gc.ipady = 5;
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.insets = new Insets(2, 15, 2, 15); // T L B R

        String[] quickTips = contentConstants.getQuickTips();
        for (int q = 0; q < quickTips.length; q++) {
            gc.gridy = q;
            infoPanel.add(new JLabel(quickTips[q]), gc);
        }
        infoScrollPane = new JScrollPane(
            infoPanel,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        infoScrollPane.setBorder(buttonBorder);

        infoDialog.getRootPane().putClientProperty("JRootPane.titleBarBackground", appBgColor);
        infoDialog.getRootPane().putClientProperty("JRootPane.titleBarForeground", appFontColor);
        
        infoDialog.getContentPane().add(infoScrollPane);
        infoDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        infoDialog.pack();
        infoDialog.setLocationRelativeTo(appFrame);
        infoDialog.setResizable(false);
        infoDialog.setVisible(true);
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
     */
    private static void createAndShowGUI() {
        measurementConstants = new MeasurementConstants();
        iconConstants = new IconConstants();
        contentConstants = new ContentConstants();
        colorConstants = new ColorConstants();
        
        appTitle = contentConstants.getAppTitle();
        appFrame = new JFrame(appTitle);
        addComponentsToPane(appFrame.getContentPane());
        appFrame.getRootPane().putClientProperty("JRootPane.titleBarBackground", appBgColor);
        appFrame.getRootPane().putClientProperty("JRootPane.titleBarForeground", appFontColor);
        try {
            ImageIcon imgIcon = utilityMgr.getDataURIToImageIcon(appIconURI, 25, 25);
            appFrame.setIconImage(imgIcon.getImage());
        } catch (IOException e) {
            utilityMgr.getLogger().log(Level.SEVERE, null, e);
        } finally {
            setSelectionStatus();
            Path currentRelativePath = Paths.get("");
            String s = currentRelativePath.toAbsolutePath().toString();
            File dataDir = new File(s, "tessdata");
            tesseractInstance = new Tesseract();
            tesseractInstance.setDatapath(dataDir.getAbsolutePath());
            tesseractInstance.setLanguage("eng+osd+equ");
            tesseractInstance.setOcrEngineMode(1);
            tesseractInstance.setTessVariable("user_defined_dpi", imageDPI+"");
            /*
            OCR engine modes (oem) --oem 1
            0. Legacy engine only
            1. Neural nets LSTM engine only
            2. Legacy + LSTM engines
            3. Default, based on what is available
            
            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath("src/main/resources/tessdata");
            tesseract.setLanguage("eng");
            tesseract.setOcrEngineMode(1);
            */
            appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            appFrame.setSize(frameWidth, frameHeight);
            appFrame.setPreferredSize(appFrame.getSize());

            maxImgWidth = ((frameWidth - dividerSize - ((2 * scrollPanePadding) + (4 * splitPaneMainContentBorderThickness))) / 2) - 105;
            maxImgHeight = frameHeight - placeholderFontSize - maxIconLength - iconFontSize - ((2 * scrollPanePadding) + (2 * splitPaneMainContentBorderThickness)) - 215;

            splitPaneMainContent.setResizeWeight(resizeWeightMainContent);
            splitPaneMainContent.setDividerLocation(dividerLocationMainContent);

            appFrame.setLocationRelativeTo(null);
        }
    }
    private static void setSelectionStatus() {
        if (INPUT_IMG_URI_LIST.isEmpty()) {
            runOCRBtn.setEnabled(false);
            runOCRAllBtn.setEnabled(false);
            resetAllBtn.setEnabled(false);

            prevPageBtn.setEnabled(false);
            nextPageBtn.setEnabled(false);
            zoomInBtn.setEnabled(false);
            zoomOutBtn.setEnabled(false);
            rotateRightBtn.setEnabled(false);
            rotateLeftBtn.setEnabled(false);
            fitImageBtn.setEnabled(false);
            
            openImgBtn.setEnabled(true);
            uploadPDFBtn.setEnabled(true);
            quickTipsBtn.setEnabled(true);
        } else {
            String displayedTop = (String) jListInputPicsModel.firstElement();
            if (displayedTop.contains("Page")) {
                openImgBtn.setEnabled(false);
                uploadPDFBtn.setEnabled(false);
                runOCRBtn.setEnabled(true);
                runOCRAllBtn.setEnabled(true);
            }
            resetAllBtn.setEnabled(true);
            prevPageBtn.setEnabled(true);
            nextPageBtn.setEnabled(true);
            zoomInBtn.setEnabled(true);
            zoomOutBtn.setEnabled(true);
            rotateRightBtn.setEnabled(true);
            rotateLeftBtn.setEnabled(true);
            fitImageBtn.setEnabled(true);
            quickTipsBtn.setEnabled(true);
        }
    }
    
    private static class RunOCRTask extends SwingWorker<Boolean, String[]> {
        @Override
        public Boolean doInBackground() { // Main task. Executed in background thread.
            try {
                ocrBos = null;
                ocrZipOs = null;
                ocrBArrIs = null;
                
                ocrBos = new ByteArrayOutputStream();
                ocrZipOs = new ZipOutputStream(ocrBos);
                
                hocrBos = null;
                hocrZipOs = null;
                hocrBArrIs = null;
                
                hocrBos = new ByteArrayOutputStream();
                hocrZipOs = new ZipOutputStream(hocrBos);
                
                jListInputPics.setSelectedIndex(selectedIndex);
                imageURI = INPUT_IMG_URI_LIST.get(selectedIndex);
                if (imageURI != null) {
                    byte[] fileBytes = Base64.getDecoder().decode(imageURI);
                    Image img = ImageIO.read(new ByteArrayInputStream(fileBytes));
                    ImageIcon imgIcon = new ImageIcon(img);
                    BufferedImage ipImg = utilityMgr.getImageIconToBufferedImg(imgIcon);

                    String[] resultArr=new String[2];
                    tesseractInstance.setHocr(false);
                    extractedOutput = tesseractInstance.doOCR(ipImg);
                    resultArr[0]=extractedOutput;

                    ocrBArrIs = new ByteArrayInputStream(extractedOutput.getBytes(Charset.forName("UTF-8")));
                    String fileNameInZip = (selectedIndex+1) + ".txt";
                    ZipEntry zipEntry = new ZipEntry(fileNameInZip);
                    ocrZipOs.putNextEntry(zipEntry);
                    byte[] bytes = new byte[1024];
                    int len;
                    while ((len = ocrBArrIs.read(bytes)) >= 0) {
                        ocrZipOs.write(bytes, 0, len);
                    }
                    ocrZipOs.closeEntry();

                    tesseractInstance.setHocr(true);
                    extractedOutput = tesseractInstance.doOCR(ipImg);
                    resultArr[1]=extractedOutput;
                    hocrBArrIs = new ByteArrayInputStream(extractedOutput.getBytes(Charset.forName("UTF-8")));
                    fileNameInZip = (selectedIndex+1) + ".html";
                    zipEntry = new ZipEntry(fileNameInZip);
                    hocrZipOs.putNextEntry(zipEntry);
                    bytes = new byte[1024];
                    while ((len = hocrBArrIs.read(bytes)) >= 0) {
                        hocrZipOs.write(bytes, 0, len);
                    }
                    hocrZipOs.closeEntry();

                    publish(resultArr); // to be received by process | 2nd parameter
                } else {
                    utilityMgr.outputConsoleLogsBreakline("Input source does not exists/is invalid.");
                }
                return true;
            } catch (TesseractException | IOException e) {
                utilityMgr.getLogger().log(Level.SEVERE, null, e);
            }
            return false;
        }
        @Override
        public void done() { // Can safely update the GUI from this method.
             try {
                boolean status = get(); // Retrieve the return value of doInBackground.
                if (status) {
                    updatePreviewedPageNo();
                    setSelectionStatus();
                    progressBar.setString(PENDING_STATUS_TEXT);
                    progressBar.setIndeterminate(false);
                    JOptionPane.showMessageDialog(appFrame, 
                            contentConstants.getDISPLAY_OCR_DIALOG_MSG(), 
                            contentConstants.getDIALOG_TITLE(), 
                            JOptionPane.PLAIN_MESSAGE);
                }
                if(ocrZipOs != null) {
                    ocrZipOs.close();
                }
                if(hocrZipOs != null) {
                    hocrZipOs.close();
                }
            } catch (InterruptedException | ExecutionException | IOException ex) {
                utilityMgr.getLogger().log(Level.SEVERE, null, ex);
            }
        }
        @Override
        public void process(List<String[]> chunks) { // Can safely update the GUI from this method.
            renderPreviewImage();
            String[] mostRecentValue = chunks.get(chunks.size() - 1);
            extractedOutput = mostRecentValue[0];
            ocrResultsTextArea.append(extractedOutput + LINE_SEPARATOR + LINE_SEPARATOR);
            extractedOutput = mostRecentValue[1];
            hocrResultsTextArea.append(extractedOutput + LINE_SEPARATOR + LINE_SEPARATOR);
        }
    }
       
    private static class RunOCRAllTask extends SwingWorker<Boolean, String[]> {
        @Override
        public Boolean doInBackground() { // Main task. Executed in background thread.
            try {
                ocrBos = null;
                ocrZipOs = null;
                ocrBArrIs = null;
                
                ocrBos = new ByteArrayOutputStream();
                ocrZipOs = new ZipOutputStream(ocrBos);
                
                hocrBos = null;
                hocrZipOs = null;
                hocrBArrIs = null;
                
                hocrBos = new ByteArrayOutputStream();
                hocrZipOs = new ZipOutputStream(hocrBos);
                
                for (int p = 0; p < INPUT_IMG_URI_LIST.size(); p++) {
                    jListInputPics.setSelectedIndex(p);
                    selectedIndex = p;
                    imageURI = INPUT_IMG_URI_LIST.get(p);
                    if (imageURI != null) {
                        byte[] fileBytes = Base64.getDecoder().decode(imageURI);
                        Image img = ImageIO.read(new ByteArrayInputStream(fileBytes));
                        ImageIcon imgIcon = new ImageIcon(img);
                        BufferedImage ipImg = utilityMgr.getImageIconToBufferedImg(imgIcon);

                        String[] resultArr=new String[2];
                        tesseractInstance.setHocr(false);
                        extractedOutput = tesseractInstance.doOCR(ipImg);
                        resultArr[0]=extractedOutput;
                        
                        ocrBArrIs = new ByteArrayInputStream(extractedOutput.getBytes(Charset.forName("UTF-8")));
                        String fileNameInZip = (selectedIndex+1) + ".txt";
                        ZipEntry zipEntry = new ZipEntry(fileNameInZip);
                        ocrZipOs.putNextEntry(zipEntry);
                        byte[] bytes = new byte[1024];
                        int len;
                        while ((len = ocrBArrIs.read(bytes)) >= 0) {
                            ocrZipOs.write(bytes, 0, len);
                        }
                        ocrZipOs.closeEntry();
                        
                        tesseractInstance.setHocr(true);
                        extractedOutput = tesseractInstance.doOCR(ipImg);
                        resultArr[1]=extractedOutput;
                        hocrBArrIs = new ByteArrayInputStream(extractedOutput.getBytes(Charset.forName("UTF-8")));
                        fileNameInZip = (selectedIndex+1) + ".html";
                        zipEntry = new ZipEntry(fileNameInZip);
                        hocrZipOs.putNextEntry(zipEntry);
                        bytes = new byte[1024];
                        while ((len = hocrBArrIs.read(bytes)) >= 0) {
                            hocrZipOs.write(bytes, 0, len);
                        }
                        hocrZipOs.closeEntry();

                        publish(resultArr); // to be received by process | 2nd parameter
                    } else {
                        utilityMgr.outputConsoleLogsBreakline("Input source does not exists/is invalid.");
                    }
                }
                return true;
            } catch (TesseractException | IOException e) {
                utilityMgr.getLogger().log(Level.SEVERE, null, e);
            }
            return false;
        }
        @Override
        public void done() { // Can safely update the GUI from this method.
            try {
                boolean status = get(); // Retrieve the return value of doInBackground.
                if (status) {
                    updatePreviewedPageNo();
                    setSelectionStatus();
                    progressBar.setString(PENDING_STATUS_TEXT);
                    progressBar.setIndeterminate(false);
                    JOptionPane.showMessageDialog(appFrame, 
                            contentConstants.getDISPLAY_OCR_ALL_DIALOG_MSG(), 
                            contentConstants.getDIALOG_TITLE(), 
                            JOptionPane.PLAIN_MESSAGE);
                }
                if(ocrZipOs != null) {
                    ocrZipOs.close();
                }
                if(hocrZipOs != null) {
                    hocrZipOs.close();
                }
            } catch (InterruptedException | ExecutionException | IOException ex) {
                utilityMgr.getLogger().log(Level.SEVERE, null, ex);
            }
        }
        @Override
        public void process(List<String[]> chunks) { // Can safely update the GUI from this method.
            renderPreviewImage();
            String[] mostRecentValue = chunks.get(chunks.size() - 1);
            extractedOutput = mostRecentValue[0];
            ocrResultsTextArea.append(extractedOutput + LINE_SEPARATOR + LINE_SEPARATOR);
            extractedOutput = mostRecentValue[1];
            hocrResultsTextArea.append(extractedOutput + LINE_SEPARATOR + LINE_SEPARATOR);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf()); // new FlatLightLaf()
            UIManager.put("ProgressBar.arc", 20);
            UIManager.put("Component.arrowType", "triangle");
            UIManager.put("ScrollBar.showButtons", true);
            
            UIManager.put("TabbedPane.showTabSeparators", true);
            UIManager.put("TabbedPane.tabWidthMode", "equal");
            UIManager.put("TabbedPane.showContentSeparator", false);
            UIManager.put("TabbedPane.tabType", "underlined");
            UIManager.put("TabbedPane.hasFullBorder", true);

            ToolTipManager.sharedInstance().setInitialDelay(0);
        } catch (UnsupportedLookAndFeelException ex) {
            System.out.println(ex.getMessage());
        }
        
        SwingUtilities.invokeLater(() -> { // Event dispatch thread For GUI code (asynchronously)
            outputLogsTextArea = new JTextArea();
            scrollPaneOutputLogs = new JScrollPane(outputLogsTextArea);
            utilityMgr = new UtilityManager(outputLogsTextArea, scrollPaneOutputLogs); // so all logs are handled by the same panel
            createAndShowGUI();
            utilityMgr.getLogger().info(() -> "Application is running.");
            appFrame.setVisible(true);
            
            openImgBtn.addActionListener((ActionEvent evt) -> {
                jListInputPicsModel = (DefaultListModel) jListInputPics.getModel();
                jListInputPicsModel.clear();
                INPUT_IMG_URI_LIST.clear();
                jListInputPics.clearSelection();
                openImageAction();
            });
            uploadPDFBtn.addActionListener((ActionEvent evt) -> {
                jListInputPicsModel = (DefaultListModel) jListInputPics.getModel();
                jListInputPicsModel.clear();
                INPUT_IMG_URI_LIST.clear();
                jListInputPics.clearSelection();
                loadPDFAction();
            });
            
            prevPageBtn.addActionListener((ActionEvent evt) -> toggleItemAction(true));
            nextPageBtn.addActionListener((ActionEvent evt) -> toggleItemAction(false));
            rotateRightBtn.addActionListener((ActionEvent evt) -> rotateImgAction(true));
            rotateLeftBtn.addActionListener((ActionEvent evt) -> rotateImgAction(false));
            zoomInBtn.addActionListener((ActionEvent evt) -> zoomImage(true));
            zoomOutBtn.addActionListener((ActionEvent evt) -> zoomImage(false));
            fitImageBtn.addActionListener((ActionEvent evt) -> fitImage());
            
            runOCRBtn.addActionListener(new ActionListener() {
                private RunOCRTask activeTask;
                @Override
                public void actionPerformed(ActionEvent evt){
                    progressBar.setString(RUNNING_TASK_TEXT);
                    progressBar.setIndeterminate(true);

                    activeTask=new RunOCRTask();
                    activeTask.execute();
               }
            });
            
            runOCRAllBtn.addActionListener(new ActionListener() {
                private RunOCRAllTask activeTask;
                @Override
                public void actionPerformed(ActionEvent evt){
                    progressBar.setString(RUNNING_TASK_TEXT);
                    progressBar.setIndeterminate(true);

                    activeTask=new RunOCRAllTask();
                    activeTask.execute();
               }
            });
            
            saveOCRBtn.addActionListener((ActionEvent evt) -> saveTextAction());
            copyOCRBtn.addActionListener((ActionEvent evt) -> copyTextToClipboardAction());

            saveHOCRBtn.addActionListener((ActionEvent evt) -> saveHTMLAction());
            copyHOCRBtn.addActionListener((ActionEvent evt) -> copyHTMLToClipboardAction());

            resetAllBtn.addActionListener((ActionEvent evt) -> resetAllAction());
            quickTipsBtn.addActionListener((ActionEvent evt) -> viewInfoAction());

            labelProfileLink.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    try {
                        Desktop.getDesktop().browse(new URI(profileLink));
                    } catch (IOException | URISyntaxException ex) {
                        utilityMgr.getLogger().log(Level.SEVERE, null, ex);
                    }
                }
            });
        }); // Java JNA-based wrapper for Tesseract OCR DLL 2.04 |  support for more image formats (PNG, BMP, GIF, PDF, JPEG) | Update Tesseract to Tesseract 4.1.1
    }
}