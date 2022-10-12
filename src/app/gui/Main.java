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
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.varia.NullAppender;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

public class Main {

    // ==================== OUTPUT LOGS  ===================================
    private static JTextArea outputLogsTextArea;
    private static JScrollPane scrollPaneOutputLogs;
    private static JSplitPane splitPaneOutputLogs;
    // ==================== OUTPUT LOGS  ===================================
    private static Tesseract tesseractInstance;

    private static IconConstants iconConstants;
    private static ContentConstants contentConstants;
    private static MeasurementConstants measurementConstants;
    private static ColorConstants colorConstants;
    private static UtilityManager utilityMgr;
    // ==================== DECLARE ALL VARIABLE CONSTANTS HERE ===================================  
    private static String appIconURi;
    private static String openImgIconURI;
    private static String uploadPDFIconURI;
    private static String runOCRIconURI;
    private static String runOCRAllIconURI;
    private static String clearTextIconURI;
    private static String saveTextIconURI;
    private static String copyTextIconURI;
    private static String resetAllIconURI;
    private static String quickTipsIconURI;

    private static int frameWidth;
    private static int frameHeight;

    private static int maxIconLength;
    private static int iconFontSize;
    private static int textFontSize;
    private static int footerFontSize;
    private static int placeholderFontSize;

    private static int dividerSize;
    private static double dividerLocationMainContent;
    private static double resizeWeightMainContent;
    private static double dividerLocationOutputLogs;
    private static double resizeWeightOutputLogs;
    private static int splitPaneMainContentBorderThickness;
    private static int scrollPanePadding;
    private static int imageDPI;
    
    private static double zoomFactor;
    
    private static String appTitle;
    private static String placeholderText;
    private static String displayedProfileLink;
    private static String profileLink;

    private static String quickTipsTitle;
    private static String openImgBtnTxt;
    private static String uploadPDFBtnText;
    private static String runOCRBtnText;
    private static String ocrAllBtnText;
    private static String saveTextBtnText;
    private static String copyTextBtnText;
    private static String clearTextBtnText;
    private static String resetAllBtnText;
    private static String quickTipsBtnText;

    private static String prevPageBtnText;
    private static String nextPageBtnText;
    private static String zoomInBtnText;
    private static String zoomOutBtnText;
    private static String rotateLeftBtnText;
    private static String rotateRightBtnText;
    private static String fitImageBtnText;

    private static String openImgBtnTooltip;
    private static String uploadPDFBtnTooltip;
    private static String runOCRBtnTooltip;
    private static String runOCRAllBtnTooltip;
    private static String saveTextBtnTooltip;
    private static String copyTextBtnTooltip;
    private static String clearTextBtnTooltip;
    private static String resetAllBtnTooltip;
    private static String quickTipsBtnTooltip;

    private static String prevPageBtnTooltip;
    private static String nextPageBtnTooltip;
    private static String zoomInBtnTooltip;
    private static String zoomOutBtnTooltip;
    private static String rotateLeftBtnTooltip;
    private static String rotateRightBtnTooltip;
    private static String fitImageBtnTooltip;

    private static String imagePreviewPlaceholder;
    private static String textareaPlaceholder;

    private static Color appBgColor;
    private static Color appFontColor;
    private static Color splitPaneMsinContentBgColor;
    private static Color splitPaneMsinContentFontColor;
    private static Color splitPaneMsinContentParentPaneObjBgColor;
    private static Color profileLinkColor;
    // ==================== //END DECLARE ALL VARIABLE CONSTANTS HERE ===================================

    private static final String[] MONOSPACE_DIGITS = {"𝟶", "𝟷", "𝟸", "𝟹", "𝟺", "𝟻", "𝟼", "𝟽", "𝟾", "𝟿"};
    private static final String[] BOLD_DIGITS = {"𝟬", "𝟭", "𝟮", "𝟯", "𝟰", "𝟱", "𝟲", "𝟳", "𝟴", "𝟵"};

    // ============================ For the main content ================================
    // ============================ GUI Components ================================
    private static JFrame appFrame;
    private static JToolBar toolbar;
    private static TextPrompt tpTextArea;

    private static JLabel labelEndNote;
    private static JLabel labelProfileLink;
    private static JLabel paginationDisplay;

    private static JSplitPane splitPaneMainContent;

    private static JScrollPane textScrollPane;
    private static JTextArea ocrResultsTextArea;

    private static JScrollPane imagePreviewScrollPane;
    private static JLabel imagePreview;
    private static JPanel p1;
    private static int v1;
    private static int h1;

    private static JPanel panelObj;
    private static JPanel p2;
    private static int v2;
    private static int h2;

    private static final File WORK_DIR = new File(System.getProperty("user.dir"));
    private static JFileChooser selectFileChooser = null;
    private static JFileChooser saveFileChooser = null;

    private static File outputFile = null;
    private static String extractedOutput = null;
    // ============================// For the main content ================================

    private static JButton openImgBtn;
    private static JButton uploadPDFBtn;

    private static JButton runOCRBtn;
    private static JButton runOCRAllBtn;

    private static JButton saveTxtBtn;
    private static JButton copyTxtBtn;

    private static JButton clearTxtBtn;
    private static JButton resetAllBtn;
    private static JButton quickTipsBtn;

    // ============================ For the pagination pane content =======================
    private static JButton prevPageBtn;
    private static JButton nextPageBtn;
    private static JButton zoomInBtn;
    private static JButton zoomOutBtn;
    private static JButton rotateLeftBtn;
    private static JButton rotateRightBtn;
    private static JButton fitImageBtn;
    // ============================ // For the pagination pane content ====================

    private static JPanel bottomPanel;
    private static JDialog infoDialog;
    private static JPanel infoPane;

    private static int maxImgWidth;
    private static int maxImgHeight;

    private static Border buttonBorder;
    private static Border imagePreviewBorder;
    private static Border bottomPanelBorder;
    private static Border splitPaneMsinContentBorder;
    private static Border panelObjBorder;
    private static Border scrollPaneBorder;
    private static Border toolbarBorder;
    private static Border scrollPanePaddingBorder;

    private static Font iconFont;
    private static Font placeholderFont;
    private static Font textFont;
    private static Font footerFont;

    private static JPanel paginationPanel;
    private static DefaultListModel jListInputPicsModel;
    private static JList<String> jListInputPics;
    private static JScrollPane jScrollPane1InputPicsListItems;

    private static JScrollPane infoScrollPane;

    private static GridBagConstraints gc;
    private static double currentImageScale = 1.0;

    private static int selectedImgWidth = 0;
    private static int selectedImgHeight = 0;

    private static final ArrayList<String> INPUT_IMG_URI_LIST = new ArrayList<String>();
    private static GridBagLayout gbl;
    private static double[][] weights;

    private static FileNameExtensionFilter filter;
    private static int option;
    private static int selectedIndex;
    private static String imageURI;

    private static PDDocument document;
    private static PDFRenderer pdfRenderer;
    private static int totalNoOfPages;

    private static void addComponentsToPane(Container contentPane) {
        labelEndNote = new JLabel("© " + utilityMgr.getCurrentYear() + " 𝚋𝚢 ξ(🎀˶❛◡❛) ᵀᴴᴱ ᴿᴵᴮᴮᴼᴺ ᴳᴵᴿᴸ ╰┈➤");

        buttonBorder = BorderFactory.createEtchedBorder();
        bottomPanelBorder = BorderFactory.createBevelBorder(BevelBorder.RAISED, splitPaneMsinContentParentPaneObjBgColor, splitPaneMsinContentParentPaneObjBgColor);
        toolbarBorder = BorderFactory.createBevelBorder(BevelBorder.RAISED, appBgColor, appBgColor);
        panelObjBorder = toolbarBorder;

        imagePreviewBorder = new EtchedBorder(EtchedBorder.RAISED, splitPaneMsinContentParentPaneObjBgColor, splitPaneMsinContentParentPaneObjBgColor);
        scrollPanePaddingBorder = BorderFactory.createLineBorder(splitPaneMsinContentBgColor, scrollPanePadding);
        splitPaneMsinContentBorder = BorderFactory.createLineBorder(appBgColor, splitPaneMainContentBorderThickness);
        scrollPaneBorder = new SoftBevelBorder(BevelBorder.LOWERED, splitPaneMsinContentBgColor, splitPaneMsinContentBgColor, splitPaneMsinContentBgColor, splitPaneMsinContentBgColor);

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
        toolbar.setRollover(true);

        labelProfileLink = new JLabel(" " + displayedProfileLink);
        labelProfileLink.putClientProperty("FlatLaf.styleClass", "default");
        labelProfileLink.setForeground(profileLinkColor);
        labelProfileLink.setCursor(new Cursor(Cursor.HAND_CURSOR));

        openImgBtn = new JButton(openImgBtnTxt);
        uploadPDFBtn = new JButton(uploadPDFBtnText);
        runOCRBtn = new JButton(runOCRBtnText);
        runOCRAllBtn = new JButton(ocrAllBtnText);
        saveTxtBtn = new JButton(saveTextBtnText);
        copyTxtBtn = new JButton(copyTextBtnText);
        clearTxtBtn = new JButton(clearTextBtnText);
        resetAllBtn = new JButton(resetAllBtnText);
        quickTipsBtn = new JButton(quickTipsBtnText);

        prevPageBtn = new JButton(prevPageBtnText);
        nextPageBtn = new JButton(nextPageBtnText);
        zoomInBtn = new JButton(zoomInBtnText);
        zoomOutBtn = new JButton(zoomOutBtnText);
        rotateLeftBtn = new JButton(rotateLeftBtnText);
        rotateRightBtn = new JButton(rotateRightBtnText);
        fitImageBtn = new JButton(fitImageBtnText);

        JButton[] mainButtonsArr = {
            openImgBtn, uploadPDFBtn,
            runOCRBtn, runOCRAllBtn,
            saveTxtBtn, copyTxtBtn, clearTxtBtn,
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
            iconButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            iconButton.setHorizontalTextPosition(SwingConstants.CENTER);
            iconButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        }
        try {
            openImgBtn.setIcon(utilityMgr.getDataURIToImageIcon(openImgIconURI, maxIconLength, maxIconLength));
            uploadPDFBtn.setIcon(utilityMgr.getDataURIToImageIcon(uploadPDFIconURI, maxIconLength, maxIconLength));
            runOCRBtn.setIcon(utilityMgr.getDataURIToImageIcon(runOCRIconURI, maxIconLength, maxIconLength));
            runOCRAllBtn.setIcon(utilityMgr.getDataURIToImageIcon(runOCRAllIconURI, maxIconLength, maxIconLength));
            saveTxtBtn.setIcon(utilityMgr.getDataURIToImageIcon(saveTextIconURI, maxIconLength, maxIconLength));
            copyTxtBtn.setIcon(utilityMgr.getDataURIToImageIcon(copyTextIconURI, maxIconLength, maxIconLength));
            clearTxtBtn.setIcon(utilityMgr.getDataURIToImageIcon(clearTextIconURI, maxIconLength, maxIconLength));
            resetAllBtn.setIcon(utilityMgr.getDataURIToImageIcon(resetAllIconURI, maxIconLength, maxIconLength));
            quickTipsBtn.setIcon(utilityMgr.getDataURIToImageIcon(quickTipsIconURI, maxIconLength, maxIconLength));
        } catch (IOException e) {
            utilityMgr.getLogger().log(Level.SEVERE, null, e);

        }
        toolbar.addSeparator();
        toolbar.add(openImgBtn);
        toolbar.add(uploadPDFBtn);
        toolbar.addSeparator();
        toolbar.add(runOCRBtn);
        toolbar.add(runOCRAllBtn);
        toolbar.addSeparator();
        toolbar.add(saveTxtBtn);
        toolbar.add(copyTxtBtn);
        toolbar.addSeparator();
        toolbar.add(clearTxtBtn);
        toolbar.add(resetAllBtn);
        toolbar.addSeparator();
        toolbar.add(quickTipsBtn);
        contentPane.add(toolbar, BorderLayout.NORTH);

        bottomPanel = new JPanel();
        bottomPanel.setFont(footerFont);
        bottomPanel.setBorder(bottomPanelBorder);
        bottomPanel.setBackground(appBgColor);
        bottomPanel.add(labelEndNote);
        bottomPanel.add(labelProfileLink);
        labelEndNote.setBounds(0, 0, 270, 30);
        labelProfileLink.setBounds(290, 0, 180, 30);

        contentPane.add(bottomPanel, BorderLayout.SOUTH);
        // =============================== Split Panel Content ==================================
        v1 = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
        h1 = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;

        p1 = new JPanel();
        p1.setLayout(new BorderLayout());
        imagePreview = new JLabel(imagePreviewPlaceholder);
        imagePreview.setBackground(splitPaneMsinContentBgColor);
        imagePreview.setForeground(splitPaneMsinContentFontColor);
        imagePreview.setFont(placeholderFont);
        imagePreview.setBorder(scrollPanePaddingBorder);
        imagePreview.setHorizontalAlignment(JLabel.CENTER);
        imagePreview.setHorizontalTextPosition(JLabel.CENTER);
        imagePreviewScrollPane = new JScrollPane(imagePreview, v1, h1);

        paginationDisplay = new JLabel();
        paginationDisplay.setForeground(splitPaneMsinContentFontColor);
        paginationDisplay.setBackground(splitPaneMsinContentBgColor);
        paginationDisplay.setBorder(BorderFactory.createEmptyBorder());
        paginationDisplay.setHorizontalAlignment(JLabel.CENTER);
        paginationDisplay.setHorizontalTextPosition(JLabel.CENTER);

        p1.add(paginationDisplay, BorderLayout.NORTH);
        p1.add(imagePreviewScrollPane, BorderLayout.CENTER);
        // ================================= Set up page navigation panel =============================
        jListInputPicsModel = new DefaultListModel<>();
        jListInputPics = new JList<>(jListInputPicsModel);
        jScrollPane1InputPicsListItems = new JScrollPane(jListInputPics, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jListInputPics.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jListInputPics.setEnabled(false);

        paginationPanel = new JPanel();
        paginationPanel.setBackground(appBgColor);
        paginationPanel.setBorder(toolbarBorder);

        gbl = new GridBagLayout();
        paginationPanel.setLayout(gbl);
        gbl.layoutContainer(paginationPanel);
        weights = gbl.getLayoutWeights();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                weights[i][j] = 1;
            }
        }
        gbl.columnWeights = weights[0];
        gbl.rowWeights = weights[1];

        gc = new GridBagConstraints();
        gc.anchor = GridBagConstraints.FIRST_LINE_START;

        gc.fill = GridBagConstraints.CENTER;
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.ipadx = 15;
        gc.ipady = 0;
        gc.insets = new Insets(2, 4, 2, 2); // T L B R
        paginationPanel.add(prevPageBtn, gc);

        gc.fill = GridBagConstraints.CENTER;
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.gridx = 1;
        gc.gridy = 0;
        gc.ipadx = 15;
        gc.ipady = 0;
        gc.insets = new Insets(2, 2, 2, 4); // T L B R
        paginationPanel.add(nextPageBtn, gc);

        gc.fill = GridBagConstraints.CENTER;
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.gridx = 0;
        gc.gridy = 1;
        gc.ipadx = 15;
        gc.ipady = 0;
        gc.insets = new Insets(2, 4, 2, 2); // T L B R
        paginationPanel.add(zoomInBtn, gc);

        gc.fill = GridBagConstraints.CENTER;
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.gridx = 1;
        gc.gridy = 1;
        gc.ipadx = 15;
        gc.ipady = 0;
        gc.insets = new Insets(2, 2, 2, 4); // T L B R
        paginationPanel.add(zoomOutBtn, gc);

        gc.fill = GridBagConstraints.CENTER;
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.gridx = 0;
        gc.gridy = 2;
        gc.ipadx = 15;
        gc.ipady = 0;
        gc.insets = new Insets(2, 4, 2, 2); // T L B R
        paginationPanel.add(rotateLeftBtn, gc);

        gc.fill = GridBagConstraints.CENTER;
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.gridx = 1;
        gc.gridy = 2;
        gc.ipadx = 15;
        gc.ipady = 0;
        gc.insets = new Insets(2, 2, 2, 4); // T L B R
        paginationPanel.add(rotateRightBtn, gc);

        gc.fill = GridBagConstraints.BOTH;
        gc.ipadx = 30;
        gc.ipady = 0;
        gc.insets = new Insets(2, 4, 2, 4); // T L B R
        gc.gridx = 0;
        gc.gridy = 3;
        gc.gridwidth = 2;
        gc.gridheight = 1;
        paginationPanel.add(fitImageBtn, gc);

        gc.fill = GridBagConstraints.BOTH;
        gc.ipadx = 30;
        gc.ipady = 200;
        gc.insets = new Insets(2, 4, 2, 4); // T L B R
        gc.gridx = 0;
        gc.gridy = 4;
        gc.gridwidth = 2;
        gc.gridheight = 1;
        paginationPanel.add(jScrollPane1InputPicsListItems, gc);

        paginationDisplay.setText(placeholderText);
        jListInputPicsModel.addElement(placeholderText);
        jListInputPics.setSelectedIndex(0);

        p1.add(paginationPanel, BorderLayout.WEST);
        v2 = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
        h2 = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

        splitPaneOutputLogs = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPaneOutputLogs.setBorder(BorderFactory.createEmptyBorder());
        splitPaneOutputLogs.setBackground(appBgColor);
        splitPaneOutputLogs.setDividerSize(dividerSize);
        splitPaneOutputLogs.setResizeWeight(resizeWeightOutputLogs);
        splitPaneOutputLogs.setDividerLocation(dividerLocationOutputLogs);
        splitPaneOutputLogs.setContinuousLayout(true);

        p2 = new JPanel();
        p2.setLayout(new BorderLayout());
        ocrResultsTextArea = new JTextArea();
        ocrResultsTextArea.setFont(textFont);
        ocrResultsTextArea.setBorder(scrollPanePaddingBorder);
        ocrResultsTextArea.setForeground(splitPaneMsinContentFontColor);
        ocrResultsTextArea.setBackground(appFontColor);
        ocrResultsTextArea.setLineWrap(true);
        ocrResultsTextArea.setWrapStyleWord(true);
        tpTextArea = new TextPrompt(textareaPlaceholder, ocrResultsTextArea, Show.FOCUS_LOST);
        tpTextArea.changeAlpha(128);
        tpTextArea.changeStyle(Font.BOLD);
        tpTextArea.setHorizontalAlignment(JLabel.CENTER);
        tpTextArea.setHorizontalTextPosition(JLabel.CENTER);

        textScrollPane = new JScrollPane(ocrResultsTextArea, v2, h2);
        p2.add(textScrollPane, BorderLayout.CENTER);

        paginationDisplay.setOpaque(true);
        ocrResultsTextArea.setOpaque(true);
        imagePreview.setOpaque(true);

        splitPaneMainContent = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        imagePreviewScrollPane.setBorder(scrollPaneBorder);
        textScrollPane.setBorder(scrollPaneBorder);
        imagePreviewScrollPane.setForeground(splitPaneMsinContentFontColor);
        textScrollPane.setForeground(splitPaneMsinContentFontColor);

        p1.setBackground(splitPaneMsinContentBgColor);
        p2.setBackground(splitPaneMsinContentBgColor);
        p1.setBorder(imagePreviewBorder);
        p2.setBorder(imagePreviewBorder);

        splitPaneMainContent.setBorder(splitPaneMsinContentBorder);
        splitPaneMainContent.setLeftComponent(p1);

        splitPaneOutputLogs.setBorder(splitPaneMsinContentBorder);
        splitPaneOutputLogs.setTopComponent(p2);
        splitPaneOutputLogs.setBottomComponent(scrollPaneOutputLogs);
        splitPaneMainContent.setRightComponent(splitPaneOutputLogs); // p2

        panelObj = new JPanel();
        panelObj.setLayout(new BorderLayout());
        panelObj.setBackground(splitPaneMsinContentBgColor);
        panelObj.setBorder(panelObjBorder);
        panelObj.add(splitPaneMainContent, BorderLayout.CENTER);

        splitPaneMainContent.setBackground(appBgColor);
        splitPaneMainContent.setDividerSize(dividerSize);
        splitPaneMainContent.setResizeWeight(resizeWeightMainContent);
        splitPaneMainContent.setDividerLocation(dividerLocationMainContent);
        splitPaneMainContent.setContinuousLayout(true);

        p1.setOpaque(true);
        p2.setOpaque(true);
        panelObj.setOpaque(true);
        contentPane.add(panelObj, BorderLayout.CENTER);

        openImgBtn.setToolTipText(openImgBtnTooltip);
        uploadPDFBtn.setToolTipText(uploadPDFBtnTooltip);
        runOCRBtn.setToolTipText(runOCRBtnTooltip);
        runOCRAllBtn.setToolTipText(runOCRAllBtnTooltip);
        saveTxtBtn.setToolTipText(saveTextBtnTooltip);
        copyTxtBtn.setToolTipText(copyTextBtnTooltip);
        clearTxtBtn.setToolTipText(clearTextBtnTooltip);
        resetAllBtn.setToolTipText(resetAllBtnTooltip);
        quickTipsBtn.setToolTipText(quickTipsBtnTooltip);

        prevPageBtn.setToolTipText(prevPageBtnTooltip);
        nextPageBtn.setToolTipText(nextPageBtnTooltip);
        zoomInBtn.setToolTipText(zoomInBtnTooltip);
        zoomOutBtn.setToolTipText(zoomOutBtnTooltip);
        rotateLeftBtn.setToolTipText(rotateLeftBtnTooltip);
        rotateRightBtn.setToolTipText(rotateRightBtnTooltip);
        fitImageBtn.setToolTipText(fitImageBtnTooltip);

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

        runOCRBtn.addActionListener((ActionEvent evt) -> runOcrAction());
        runOCRAllBtn.addActionListener((ActionEvent evt) -> runOcrAllAction());

        saveTxtBtn.addActionListener((ActionEvent evt) -> saveTextAction());
        copyTxtBtn.addActionListener((ActionEvent evt) -> copyTextToClipboardAction());
        clearTxtBtn.addActionListener((ActionEvent evt) -> runClearTextAction());
        resetAllBtn.addActionListener((ActionEvent evt) -> resetAllAction());

        quickTipsBtn.addActionListener((ActionEvent evt) -> viewInfoAction());

        labelProfileLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                try {
                    Desktop.getDesktop().browse(new URI(profileLink));
                } catch (IOException | URISyntaxException e) {
                    utilityMgr.getLogger().log(Level.SEVERE, null, e);
                }
            }
        });
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
                outputFile = null;
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
                File tempImgPageFile = null;
                String tempImgPageFileName = null;
                File selectedFile = null;
                File[] selectedFiles = null;

                String filename = null;
                String fileExt = null;

                selectFileChooser = new JFileChooser();
                selectFileChooser.setCurrentDirectory(WORK_DIR);
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
                            filename = selectedFile.getName();
                            fileExt = filename.substring(filename.lastIndexOf(".") + 1);
                            tempImgPageFileName = String.format(utilityMgr.getCurrentTimeStamp() + "_tempImgPage_%d.%s", p + 1, fileExt);
                            tempImgPageFile = new File(tempImgPageFileName);
                            FileUtils.copyFile(selectedFile, tempImgPageFile);
                            if (tempImgPageFile.exists()) {
                                publish(p); // to be received by process | 2nd parameter
                                imageURI = utilityMgr.getFileToDataURI(tempImgPageFile);
                                INPUT_IMG_URI_LIST.add(imageURI);
                                jListInputPicsModel.addElement("Page " + p);

                                boolean tempFileDeleted = tempImgPageFile.delete();
                                if (tempFileDeleted) {
                                    utilityMgr.outputConsoleLogsBreakline(tempImgPageFileName + " has been deleted successfully");
                                }
                            }
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
                        updatePreviewedPageNo();
                        setSelectionStatus();
                        utilityMgr.displayUploadCompletionDialog(appFrame);
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
                File tempPdfPageFile = null;
                BufferedImage tempPageImg = null;
                String tempPdfPageFileName = null;

                selectFileChooser = new JFileChooser();
                selectFileChooser.setCurrentDirectory(WORK_DIR);
                selectFileChooser.setDialogTitle("Upload PDF File:");
                selectFileChooser.setMultiSelectionEnabled(false);
                selectFileChooser.setAcceptAllFileFilterUsed(false);
                filter = new FileNameExtensionFilter("Pdf File (.pdf)", "pdf");
                selectFileChooser.addChoosableFileFilter(filter);

                option = selectFileChooser.showOpenDialog(appFrame);
                if (option == JFileChooser.APPROVE_OPTION) {
                    try {
                        selectedFile = selectFileChooser.getSelectedFile();
                        document = PDDocument.load(selectedFile);
                        pdfRenderer = new PDFRenderer(document);

                        if (selectedFile != null) {
                            totalNoOfPages = document.getNumberOfPages();
                            for (int p = 0; p < totalNoOfPages; p++) {
                                try {
                                    // FOR-EACH PAGE
                                    tempPageImg = pdfRenderer.renderImageWithDPI(p, imageDPI, ImageType.RGB);
                                    tempPdfPageFileName = String.format(utilityMgr.getCurrentTimeStamp() + "_tempPdfPage_%d.%s", p + 1, "jpg");
                                    ImageIOUtil.writeImage(tempPageImg, tempPdfPageFileName, imageDPI);
                                    tempPdfPageFile = new File(tempPdfPageFileName);

                                    if (tempPdfPageFile.exists()) {
                                        publish(p); // to be received by process | 2nd parameter
                                        imageURI = utilityMgr.getFileToDataURI(tempPdfPageFile);
                                        INPUT_IMG_URI_LIST.add(imageURI);
                                        jListInputPicsModel.addElement("Page " + p);

                                        boolean tempFileDeleted = tempPdfPageFile.delete();
                                        if (tempFileDeleted) {
                                            utilityMgr.outputConsoleLogsBreakline(tempPdfPageFileName + " has been deleted successfully");
                                        }
                                    }
                                } catch (IOException ex) {
                                    utilityMgr.getLogger().log(Level.SEVERE, null, ex);

                                }
                            }
                        }
                        return true;
                    } catch (IOException ex) {
                        utilityMgr.getLogger().log(Level.SEVERE, null, ex);

                    }
                }
                return false;
            }

            @Override
            protected void done() { // Can safely update the GUI from this method.
                try {
                    boolean status = get(); // Retrieve the return value of doInBackground.
                    if (status) {
                        updatePreviewedPageNo();
                        setSelectionStatus();
                        document.close();
                        utilityMgr.displayUploadCompletionDialog(appFrame);
                    } else {
                        paginationDisplay.setText(placeholderText);
                        jListInputPicsModel.addElement(placeholderText);
                        jListInputPics.setSelectedIndex(0);
                    }
                } catch (InterruptedException | ExecutionException | IOException e) {
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
    
    private static void runOcrAllAction() {
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            @Override
            protected Boolean doInBackground() {
                try {
                    for (int p = 0; p < INPUT_IMG_URI_LIST.size(); p++) {
                        jListInputPics.setSelectedIndex(p);
                        selectedIndex = p;
                        imageURI = INPUT_IMG_URI_LIST.get(p);
                        if (imageURI != null) {
                            byte[] fileBytes = Base64.getDecoder().decode(imageURI);
                            Image img = ImageIO.read(new ByteArrayInputStream(fileBytes));
                            ImageIcon imgIcon = new ImageIcon(img);
                            BufferedImage ipImg = utilityMgr.getImageIconToBufferedImg(imgIcon);
                            /*
                             IMAGE ENHANCEMENTS
                             BufferedImage bImg = utilityMgr.getGrayscaledBufferImg(ipImg);
                             BufferedImage bImg = utilityMgr.getProcessedBufferImg(ipImg);
                             BufferedImage bImg = utilityMgr.medianFilter(ipImg);
                            
                            extractedOutput = tesseractInstance.doOCR(ipImg);
                            String[] strArr = extractedOutput.split("\\r\\n|\\r|\\n");
                            extractedOutput=join(strArr, " ") + System.lineSeparator() + "";
                            */
                            extractedOutput = tesseractInstance.doOCR(ipImg);
                            
                            String[] strArr = extractedOutput.split("\\r\\n|\\r|\\n");
                            String str = utilityMgr.join(strArr, " ");
                            str=str+System.lineSeparator()+"";
                            extractedOutput = str;
                            
                            publish(extractedOutput); // to be received by process | 2nd parameter
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
            protected void done() { // Can safely update the GUI from this method.
                try {
                    boolean status = get(); // Retrieve the return value of doInBackground.
                    if (status) {
                        updatePreviewedPageNo();
                        setSelectionStatus();
                        utilityMgr.displayOCRAllCompletionDialog(appFrame);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    utilityMgr.getLogger().log(Level.SEVERE, null, e);
                }
            }
            @Override
            protected void process(List<String> chunks) { // Can safely update the GUI from this method.
                String mostRecentValue = chunks.get(chunks.size() - 1);
                extractedOutput = mostRecentValue;
                ocrResultsTextArea.append(extractedOutput);
                ocrResultsTextArea.append(System.lineSeparator()+"");
                renderPreviewImage();
            }
        };
        worker.execute();
    }
    private static void runOcrAction() {
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            @Override
            protected Boolean doInBackground() {
                try {
                    imageURI = INPUT_IMG_URI_LIST.get(selectedIndex);
                    if (imageURI != null) {
                        byte[] fileBytes = Base64.getDecoder().decode(imageURI);
                        Image img = ImageIO.read(new ByteArrayInputStream(fileBytes));
                        ImageIcon imgIcon = new ImageIcon(img);
                        BufferedImage ipImg = utilityMgr.getImageIconToBufferedImg(imgIcon);
                        /*
                         IMAGE ENHANCEMENTS
                         BufferedImage bImg = utilityMgr.getGrayscaledBufferImg(ipImg);
                         BufferedImage bImg = utilityMgr.getProcessedBufferImg(ipImg);
                         BufferedImage bImg = utilityMgr.medianFilter(ipImg);
                         */
                        extractedOutput = tesseractInstance.doOCR(ipImg);
                        String[] strArr = extractedOutput.split("\\r\\n|\\r|\\n");
                        String str = utilityMgr.join(strArr, " ");
                        str=str+System.lineSeparator()+"";
                        extractedOutput = str;
                            
                        publish(extractedOutput); // to be received by process | 2nd parameter
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
            protected void done() { // Can safely update the GUI from this method.
                try {
                    boolean status = get(); // Retrieve the return value of doInBackground.
                    if (status) {
                        updatePreviewedPageNo();
                        setSelectionStatus();
                        utilityMgr.displayOCRCompletionDialog(appFrame);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    utilityMgr.getLogger().log(Level.SEVERE, null, e);
                }
            }
            @Override
            protected void process(List<String> chunks) { // Can safely update the GUI from this method.
                String mostRecentValue = chunks.get(chunks.size() - 1);
                extractedOutput = mostRecentValue;
                ocrResultsTextArea.append(extractedOutput);
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
        String displayStr = "𝙿𝚊𝚐𝚎 " + currentPageBoldStr + " 𝚘𝚏 " + totalPagesMonospaceStr;
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
        copyTxtBtn.transferFocusBackward();
        StringSelection stringSelection = new StringSelection(str);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
    private static void saveTextAction() {
        saveFileChooser = new JFileChooser();
        saveFileChooser.setDialogTitle("Save Output To File:");
        saveFileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        outputFile = new File("TesseractOCR_Output_" + utilityMgr.getCurrentTimeStamp() + ".txt");
        saveFileChooser.setSelectedFile(outputFile);
        saveFileChooser.setFileFilter(new FileNameExtensionFilter("Text Documents (*.txt)", "txt"));

        option = saveFileChooser.showSaveDialog(appFrame);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = saveFileChooser.getSelectedFile();
            if (selectedFile != null) {
                outputFile = selectedFile;
            }
            if (!outputFile.getName().toLowerCase().endsWith(".txt")) {
                outputFile = new File(outputFile.getParentFile(), outputFile.getName() + ".txt");
            }
            try {
                FileWriter writer = new FileWriter(outputFile, false);
                writer.write(ocrResultsTextArea.getText());
                writer.close();

                utilityMgr.outputConsoleLogsBreakline("Text file: " + outputFile.getName() + " has been saved");
                utilityMgr.outputConsoleLogsBreakline("at: " + outputFile.getPath());

                Desktop.getDesktop().open(outputFile);
            } catch (Exception e) {
                utilityMgr.getLogger().log(Level.SEVERE, null, e);
            }
        }
    }
    private static void runClearTextAction() {
        ocrResultsTextArea.setText("");
    }

    private static void viewInfoAction() {
        infoDialog = new JDialog(appFrame, quickTipsTitle, true);

        infoPane = new JPanel();
        infoPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        infoPane.setLayout(new GridBagLayout());
        infoPane.setForeground(appFontColor);

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
            infoPane.add(new JLabel(quickTips[q]), gc);
        }
        infoScrollPane = new JScrollPane(
            infoPane,
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
        infoDialog.setVisible(true);
    }
    private static void setConstantValues() {
        // ============================= initialise constants ================================
        appIconURi = iconConstants.getAppIconURI();
        openImgIconURI = iconConstants.getOpenImgIconURI();
        uploadPDFIconURI = iconConstants.getUploadPDFIconURI();
        runOCRIconURI = iconConstants.getRunOCRIconURI();
        runOCRAllIconURI = iconConstants.getRunOCRAllIconURI();
        clearTextIconURI = iconConstants.getClearTextIconURI();
        saveTextIconURI = iconConstants.getSaveTextIconURI();
        copyTextIconURI = iconConstants.getCopyTextIconURI();
        resetAllIconURI = iconConstants.getResetAllIconURI();
        quickTipsIconURI = iconConstants.getQuickTipsIconURI();

        frameWidth = measurementConstants.getFrameWidth();
        frameHeight = measurementConstants.getFrameHeight();

        maxIconLength = measurementConstants.getMaxIconLength();
        iconFontSize = measurementConstants.getIconFontSize();
        textFontSize = measurementConstants.getTextFontSize();
        footerFontSize = measurementConstants.getFooterFontSize();
        placeholderFontSize = measurementConstants.getPlaceholderFontSize();

        dividerSize = measurementConstants.getDividerSize();
        dividerLocationMainContent = measurementConstants.getDividerLocationMainContent();
        resizeWeightMainContent = measurementConstants.getResizeWeightMainContent();
        splitPaneMainContentBorderThickness = measurementConstants.getSplitPaneMainContentBorderThickness();

        dividerLocationOutputLogs = measurementConstants.getDividerLocationOutputLogs();
        resizeWeightOutputLogs = measurementConstants.getResizeWeightOutputLogs();
        scrollPanePadding = measurementConstants.getScrollPanePadding();

        imageDPI = measurementConstants.getImageDPI();
        
        zoomFactor = measurementConstants.getZoomFactor();
        
        
        appTitle = contentConstants.getAppTitle();
        placeholderText = contentConstants.getPlaceholderText();
        displayedProfileLink = contentConstants.getDisplayedProfileLink();
        profileLink = contentConstants.getProfileLink();
        quickTipsTitle = contentConstants.getQuickTipsTitle();

        openImgBtnTxt = contentConstants.getOpenImgBtnText();
        uploadPDFBtnText = contentConstants.getUploadPDFBtnText();
        runOCRBtnText = contentConstants.getRunOCRBtnText();
        ocrAllBtnText = contentConstants.getOCRAllBtnText();
        saveTextBtnText = contentConstants.getSaveTextBtnText();
        copyTextBtnText = contentConstants.getCopyTextBtnText();
        clearTextBtnText = contentConstants.getClearTextBtnText();
        resetAllBtnText = contentConstants.getResetAllBtnText();
        quickTipsBtnText = contentConstants.getQuickTipsBtntext();

        prevPageBtnText = contentConstants.getPrevPageBtnText();
        nextPageBtnText = contentConstants.getNextPageBtnText();
        zoomInBtnText = contentConstants.getZoomInBtnText();
        zoomOutBtnText = contentConstants.getZoomOutBtnText();
        rotateLeftBtnText = contentConstants.geRotateLeftText();
        rotateRightBtnText = contentConstants.geRotateRightText();
        fitImageBtnText = contentConstants.getFitImageText();

        openImgBtnTooltip = contentConstants.getOpenImgBtnTooltip();
        uploadPDFBtnTooltip = contentConstants.getUploadPDFBtnTooltip();
        runOCRBtnTooltip = contentConstants.getRunOCRBtnTooltip();
        runOCRAllBtnTooltip = contentConstants.getOCRAllBtnTooltip();
        saveTextBtnTooltip = contentConstants.getSaveTextBtnTooltip();
        copyTextBtnTooltip = contentConstants.getCopyTextBtnTooltip();
        clearTextBtnTooltip = contentConstants.getClearTextBtnTooltip();
        resetAllBtnTooltip = contentConstants.getResetAllBtnTooltip();
        quickTipsBtnTooltip = contentConstants.getQuickTipsBtnTooltip();
        prevPageBtnTooltip = contentConstants.getPrevPageBtnTooltip();
        nextPageBtnTooltip = contentConstants.getNextPageBtnTooltip();
        zoomInBtnTooltip = contentConstants.getZoomInBtnTooltip();
        zoomOutBtnTooltip = contentConstants.getZoomOutBtnTooltip();
        rotateLeftBtnTooltip = contentConstants.getRotateLeftBtnTooltip();
        rotateRightBtnTooltip = contentConstants.getRotateRightBtnTooltip();
        fitImageBtnTooltip = contentConstants.getFitImageBtnTooltip();

        imagePreviewPlaceholder = contentConstants.getImagePreviewPlaceholder();
        textareaPlaceholder = contentConstants.getTextareaPlaceholder();

        appBgColor = colorConstants.getAppBgColor();
        appFontColor = colorConstants.getAppFontColor();
        splitPaneMsinContentBgColor = colorConstants.getSplitPaneBgColor();
        splitPaneMsinContentFontColor = colorConstants.getSplitPaneFontColor();
        splitPaneMsinContentParentPaneObjBgColor = colorConstants.getSplitPaneParentPaneObjBgColor();
        profileLinkColor = colorConstants.getProfileLinkColor();

        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();

        File dataDir = new File(s, "tessdata");
        tesseractInstance = new Tesseract();
        tesseractInstance.setDatapath(dataDir.getAbsolutePath());
        tesseractInstance.setLanguage("eng+osd"); // +equ
        // ============================= // initialise constants ================================
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
        setConstantValues();

        appFrame = new JFrame(appTitle + ":: 𝐈𝐦𝐚𝐠𝐞/𝐏𝐃𝐅 𝐓𝐞𝐱𝐭 𝐄𝐱𝐭𝐫𝐚𝐜𝐭𝐢𝐨𝐧 🛠𝐓𝐨𝐨𝐥");
        appFrame.getRootPane().putClientProperty("JRootPane.titleBarBackground", appBgColor);
        appFrame.getRootPane().putClientProperty("JRootPane.titleBarForeground", appFontColor);
        try {
            byte[] fileBytes = Base64.getDecoder().decode(appIconURi);
            Image img = ImageIO.read(new ByteArrayInputStream(fileBytes));
            ImageIcon imgIcon = new ImageIcon(img);
            appFrame.setIconImage(imgIcon.getImage());
        } catch (IOException e) {
            utilityMgr.getLogger().log(Level.SEVERE, null, e);
        } finally {
            addComponentsToPane(appFrame.getContentPane());
            setSelectionStatus();

            appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            appFrame.setSize(frameWidth, frameHeight);
            appFrame.setPreferredSize(appFrame.getSize());

            maxImgWidth = ((frameWidth - dividerSize - ((2 * scrollPanePadding) + (4 * splitPaneMainContentBorderThickness))) / 2) - 105;
            maxImgHeight = frameHeight - placeholderFontSize - maxIconLength - iconFontSize - ((2 * scrollPanePadding) + (2 * splitPaneMainContentBorderThickness)) - 215;

            splitPaneMainContent.setResizeWeight(resizeWeightMainContent);
            splitPaneMainContent.setDividerLocation(dividerLocationMainContent);

            splitPaneOutputLogs.setResizeWeight(resizeWeightOutputLogs);
            splitPaneOutputLogs.setDividerLocation(dividerLocationOutputLogs);

            appFrame.setLocationRelativeTo(null);
        }
    }
    private static void setSelectionStatus() {
        if (INPUT_IMG_URI_LIST.isEmpty()) {
            runOCRBtn.setEnabled(false);
            runOCRAllBtn.setEnabled(false);
            saveTxtBtn.setEnabled(false);
            copyTxtBtn.setEnabled(false);
            clearTxtBtn.setEnabled(false);
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
            ocrResultsTextArea.setEditable(false);
        } else {
            String displayedTop = (String) jListInputPicsModel.firstElement();
            if (displayedTop.contains("Page")) {
                openImgBtn.setEnabled(false);
                uploadPDFBtn.setEnabled(false);

                runOCRBtn.setEnabled(true);
                runOCRAllBtn.setEnabled(true);
            }

            if (ocrResultsTextArea.getText().isEmpty()) {
                saveTxtBtn.setEnabled(false);
                copyTxtBtn.setEnabled(false);
                clearTxtBtn.setEnabled(false);
            } else {
                saveTxtBtn.setEnabled(true);
                copyTxtBtn.setEnabled(true);
                clearTxtBtn.setEnabled(true);

                ocrResultsTextArea.setEditable(true);
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

    public static void main(String[] args) {
        Logger.getRootLogger().removeAllAppenders();
        Logger.getRootLogger().addAppender(new NullAppender());
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf()); // new FlatLightLaf()
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        SwingUtilities.invokeLater(() -> { // Event dispatch thread For GUI code (asynchronously)
            outputLogsTextArea = new JTextArea();
            outputLogsTextArea.setEditable(false);
            outputLogsTextArea.setEnabled(false);
            outputLogsTextArea.setWrapStyleWord(true);
            scrollPaneOutputLogs = new JScrollPane(outputLogsTextArea);
            utilityMgr = new UtilityManager(outputLogsTextArea, scrollPaneOutputLogs); // so all logs are handled by the same panel
            createAndShowGUI();
            utilityMgr.getLogger().info(() -> appTitle + " application is running");
            appFrame.setVisible(true);
        });
    }
}