package app.gui;

import app.settings.ColorConstants;
import app.settings.MeasurementConstants;
import app.settings.ContentConstants;
import app.settings.IconConstants;
import app.util.Helpers;
import app.util.TextPrompt;
import com.formdev.flatlaf.FlatDarkLaf;
import app.util.TextPrompt.Show;
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
    private static IconConstants iconConstants;
    private static ContentConstants contentConstants;
    private static MeasurementConstants measurementConstants;
    private static ColorConstants colorConstants;
    private static Helpers helpers;
    // ==================== DECLARE ALL CONSTANTS HERE ===================================
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
    private static double dividerLocation;
    private static double resizeWeight;
    private static int splitPaneBorderThickness;
    private static int scrollPanelPadding;
    private static int imageDPI;

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
    private static String fitImageBtnText;

    private static String imagePreviewPlaceholder;
    private static String textareaPlaceholder;
    // ==================== //DECLARE ALL CONSTANTS HERE ===================================
    private static final String[] MONOSPACE_DIGITS = {"𝟶", "𝟷", "𝟸", "𝟹", "𝟺", "𝟻", "𝟼", "𝟽", "𝟾", "𝟿"};
    private static final String[] BOLD_DIGITS = {"𝟬", "𝟭", "𝟮", "𝟯", "𝟰", "𝟱", "𝟲", "𝟳", "𝟴", "𝟵"};

    private static Color appBgColor;
    private static Color appFontColor;
    private static Color splitPaneBgColor;
    private static Color splitPaneFontColor;
    private static Color splitPaneParentPaneObjBgColor;

    private static JFrame appFrame;
    private static JToolBar toolbar;
    private static TextPrompt tpTextArea;

    private static JLabel labelEndNote;
    private static JLabel labelProfileLink;
    private static JLabel paginationDisplay;
    // ============================ For the main content ================================
    private static JSplitPane splitPane;

    private static int maxImgWidth;
    private static int maxImgHeight;

    private static JScrollPane textScrollPane;
    private static JTextArea textArea;

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
    private static JButton fitImageBtn;
    // ============================ // For the pagination pane content ====================
    private static JPanel bottomPanel;
    private static JDialog infoDialog;
    private static JPanel infoPane;

    private static Border buttonBorder;
    private static Border imagePreviewBorder;
    private static Border bottomPanelBorder;
    private static Border splitPaneBorder;
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
        labelEndNote = new JLabel("© " + helpers.getCurrentYear() + " 𝚋𝚢 ξ(🎀˶❛◡❛) ᵀᴴᴱ ᴿᴵᴮᴮᴼᴺ ᴳᴵᴿᴸ ╰┈➤");

        buttonBorder = BorderFactory.createEtchedBorder();
        bottomPanelBorder = BorderFactory.createBevelBorder(BevelBorder.RAISED, splitPaneParentPaneObjBgColor, splitPaneParentPaneObjBgColor);
        toolbarBorder = BorderFactory.createBevelBorder(BevelBorder.RAISED, appBgColor, appBgColor);
        panelObjBorder = toolbarBorder;

        imagePreviewBorder = new EtchedBorder(EtchedBorder.RAISED, splitPaneParentPaneObjBgColor, splitPaneParentPaneObjBgColor);
        scrollPanePaddingBorder = BorderFactory.createLineBorder(splitPaneBgColor, scrollPanelPadding);
        splitPaneBorder = BorderFactory.createLineBorder(appBgColor, splitPaneBorderThickness);
        scrollPaneBorder = new SoftBevelBorder(BevelBorder.LOWERED, splitPaneBgColor, splitPaneBgColor, splitPaneBgColor, splitPaneBgColor);

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
        labelProfileLink.setForeground(new Color(238, 63, 134));
        labelProfileLink.setCursor(new Cursor(Cursor.HAND_CURSOR));

        openImgBtn = new JButton(openImgBtnTxt); // 12 
        uploadPDFBtn = new JButton(uploadPDFBtnText); // 12
        runOCRBtn = new JButton(runOCRBtnText); // 6
        runOCRAllBtn = new JButton(ocrAllBtnText);

        saveTxtBtn = new JButton(saveTextBtnText); // 8
        copyTxtBtn = new JButton(copyTextBtnText); // 8

        clearTxtBtn = new JButton(clearTextBtnText); // 9
        resetAllBtn = new JButton(resetAllBtnText); // 8

        quickTipsBtn = new JButton(quickTipsBtnText); // 9

        prevPageBtn = new JButton(prevPageBtnText);
        nextPageBtn = new JButton(nextPageBtnText);

        zoomInBtn = new JButton(zoomInBtnText);
        zoomOutBtn = new JButton(zoomOutBtnText);

        fitImageBtn = new JButton(fitImageBtnText);

        JButton[] mainButtonsArr = {
            openImgBtn, uploadPDFBtn,
            runOCRBtn, runOCRAllBtn,
            saveTxtBtn, copyTxtBtn, clearTxtBtn,
            resetAllBtn,
            quickTipsBtn,
            prevPageBtn, nextPageBtn,
            zoomInBtn, zoomOutBtn, fitImageBtn
        };
        for (JButton iconButton : mainButtonsArr) {
            iconButton.setBorder(buttonBorder);
            iconButton.setFont(iconFont);
            iconButton.setForeground(appFontColor);
            iconButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            iconButton.setHorizontalTextPosition(SwingConstants.CENTER);
            iconButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        }
        openImgBtn.setIcon(helpers.getImageIcon(openImgIconURI, maxIconLength, maxIconLength));
        uploadPDFBtn.setIcon(helpers.getImageIcon(uploadPDFIconURI, maxIconLength, maxIconLength));

        runOCRBtn.setIcon(helpers.getImageIcon(runOCRIconURI, maxIconLength, maxIconLength));
        runOCRAllBtn.setIcon(helpers.getImageIcon(runOCRAllIconURI, maxIconLength, maxIconLength));

        saveTxtBtn.setIcon(helpers.getImageIcon(saveTextIconURI, maxIconLength, maxIconLength));
        copyTxtBtn.setIcon(helpers.getImageIcon(copyTextIconURI, maxIconLength, maxIconLength));

        clearTxtBtn.setIcon(helpers.getImageIcon(clearTextIconURI, maxIconLength, maxIconLength));
        resetAllBtn.setIcon(helpers.getImageIcon(resetAllIconURI, maxIconLength, maxIconLength));

        quickTipsBtn.setIcon(helpers.getImageIcon(quickTipsIconURI, maxIconLength, maxIconLength));

        //adding combo box and buttons to our toolbar
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
        imagePreview.setBackground(splitPaneBgColor);
        imagePreview.setForeground(splitPaneFontColor);
        imagePreview.setFont(placeholderFont);
        imagePreview.setBorder(scrollPanePaddingBorder);
        imagePreview.setHorizontalAlignment(JLabel.CENTER);
        imagePreview.setHorizontalTextPosition(JLabel.CENTER);
        imagePreviewScrollPane = new JScrollPane(imagePreview, v1, h1);

        paginationDisplay = new JLabel();
        paginationDisplay.setForeground(splitPaneFontColor);
        paginationDisplay.setBackground(splitPaneBgColor);
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

        gc.fill = GridBagConstraints.BOTH;
        gc.ipadx = 30;
        gc.ipady = 0;
        gc.insets = new Insets(2, 4, 2, 4); // T L B R
        gc.gridx = 0;
        gc.gridy = 2;
        gc.gridwidth = 2;
        gc.gridheight = 1;
        paginationPanel.add(fitImageBtn, gc);

        gc.fill = GridBagConstraints.BOTH;
        gc.ipadx = 30;
        gc.ipady = 200;
        gc.insets = new Insets(2, 4, 2, 4); // T L B R
        gc.gridx = 0;
        gc.gridy = 3;
        gc.gridwidth = 2;
        gc.gridheight = 1;
        paginationPanel.add(jScrollPane1InputPicsListItems, gc);

        paginationDisplay.setText(placeholderText);
        jListInputPicsModel.addElement(placeholderText);
        jListInputPics.setSelectedIndex(0);

        p1.add(paginationPanel, BorderLayout.WEST);

        v2 = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
        h2 = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

        p2 = new JPanel();
        p2.setLayout(new BorderLayout());
        textArea = new JTextArea();
        textArea.setFont(textFont);
        textArea.setBorder(scrollPanePaddingBorder);
        textArea.setForeground(splitPaneFontColor);
        textArea.setBackground(appFontColor);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        tpTextArea = new TextPrompt(textareaPlaceholder, textArea, Show.FOCUS_LOST);
        tpTextArea.changeAlpha(128);
        tpTextArea.changeStyle(Font.BOLD);
        tpTextArea.setHorizontalAlignment(JLabel.CENTER);
        tpTextArea.setHorizontalTextPosition(JLabel.CENTER);
        
        textScrollPane = new JScrollPane(textArea, v2, h2);
        p2.add(textScrollPane, BorderLayout.CENTER);

        paginationDisplay.setOpaque(true);
        textArea.setOpaque(true);
        imagePreview.setOpaque(true);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        imagePreviewScrollPane.setBorder(scrollPaneBorder);
        textScrollPane.setBorder(scrollPaneBorder);
        imagePreviewScrollPane.setForeground(splitPaneFontColor);
        textScrollPane.setForeground(splitPaneFontColor);

        p1.setBackground(splitPaneBgColor);
        p2.setBackground(splitPaneBgColor);
        p1.setBorder(imagePreviewBorder);
        p2.setBorder(imagePreviewBorder);

        splitPane.setBorder(splitPaneBorder);
        splitPane.setLeftComponent(p1);
        splitPane.setRightComponent(p2);

        panelObj = new JPanel();
        panelObj.setLayout(new BorderLayout());
        panelObj.setBackground(splitPaneBgColor);
        panelObj.setBorder(panelObjBorder);
        panelObj.add(splitPane, BorderLayout.CENTER);

        splitPane.setBackground(appBgColor);
        splitPane.setResizeWeight(resizeWeight);
        splitPane.setDividerSize(dividerSize);
        splitPane.setDividerLocation(dividerLocation);
        splitPane.setContinuousLayout(true);

        p1.setOpaque(true);
        p2.setOpaque(true);
        panelObj.setOpaque(true);
        contentPane.add(panelObj, BorderLayout.CENTER);

        openImgBtn.setToolTipText("📖 Select image file(s)");
        uploadPDFBtn.setToolTipText("📖 Select a PDF document");

        runOCRBtn.setToolTipText("📖 Processes current selection and outputs extracted text in the textarea");
        runOCRAllBtn.setToolTipText("📖 Processes all uploads and outputs all extracted text in the textarea");

        saveTxtBtn.setToolTipText("📖 Saves all text to a text(.txt) file");
        copyTxtBtn.setToolTipText("📖 Selects and copies textarea content");

        clearTxtBtn.setToolTipText("📖 Clears all text in the textarea");
        resetAllBtn.setToolTipText("📖 Resets the application to its default state");
        quickTipsBtn.setToolTipText("📖 Displays features & functionalities of application");

        prevPageBtn.setToolTipText("📖 Prev Page");
        nextPageBtn.setToolTipText("📖 Next Page");

        zoomInBtn.setToolTipText("📖 Zoom in");
        zoomOutBtn.setToolTipText("📖 Zoom out");

        fitImageBtn.setToolTipText("📖 Fit Image");

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

        prevPageBtn.addActionListener((ActionEvent evt) -> prevItemAction());
        nextPageBtn.addActionListener((ActionEvent evt) -> nextItemAction());

        zoomInBtn.addActionListener((ActionEvent evt) -> zoomInImage());
        zoomOutBtn.addActionListener((ActionEvent evt) -> zoomOutImage());
        fitImageBtn.addActionListener((ActionEvent evt) -> fitImage());

        saveTxtBtn.addActionListener((ActionEvent evt) -> saveTextAction());
        copyTxtBtn.addActionListener((ActionEvent evt) -> copyTextToClipboardAction());
        clearTxtBtn.addActionListener((ActionEvent evt) -> runClearTextAction());

        quickTipsBtn.addActionListener((ActionEvent evt) -> viewInfoAction());

        runOCRBtn.addActionListener((ActionEvent evt) -> runOcrAction());
        runOCRAllBtn.addActionListener((ActionEvent evt) -> runOcrAllAction());

        resetAllBtn.addActionListener((ActionEvent evt) -> resetAllAction());

        labelProfileLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(profileLink));
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private static void resetAllAction() {
        jListInputPicsModel.clear();
        INPUT_IMG_URI_LIST.clear();
        jListInputPics.clearSelection();

        currentImageScale = 1.0;
        selectedImgWidth = 0;
        selectedImgHeight = 0;

        selectFileChooser = null;
        saveFileChooser = null;
        outputFile = null;
        extractedOutput = null;

        paginationDisplay.setText(placeholderText);
        jListInputPicsModel.addElement(placeholderText);
        jListInputPics.setSelectedIndex(0);

        imagePreview.setIcon(null);
        imagePreview.revalidate();
        imagePreview.setText(imagePreviewPlaceholder);
        textArea.setText("");
    }

    private static void openImageAction() {
        SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                File tempPdfPageFile=null;
                String tempPdfPageFileName=null;
                File selectedFile=null;
                File[] selectedFiles=null;
                
                String filename=null;
                String fileExt=null;
                
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
                    for (int p = 0; p < totalNoOfPages; p++) {  // FOR-EACH PAGE
                        selectedFile=selectedFiles[p];
                        filename=selectedFile.getName();
                        fileExt=filename.substring(filename.lastIndexOf(".")+1);
                        tempPdfPageFileName = String.format(helpers.getCurrentTimeStamp() + "_tempImgPage_%d.%s", p + 1, fileExt);
                        tempPdfPageFile = new File(tempPdfPageFileName);
                        FileUtils.copyFile(selectedFile, tempPdfPageFile);
                        
                        if (tempPdfPageFile.exists()) {
                            publish(p); // to be received by process | 2nd parameter
                            try {
                                imageURI = helpers.getImageFileURI(tempPdfPageFile);
                                INPUT_IMG_URI_LIST.add(imageURI);
                                jListInputPicsModel.addElement("Page " + p);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            } finally {
                                boolean tempFileRemoved = tempPdfPageFile.delete();
                                if (tempFileRemoved) {
                                    System.out.println("Temp file: " + tempPdfPageFileName + " removed successfully.");
                                }
                            }
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
                    if(status) {
                        updatePreviewedPageNo();
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void process(List<Integer> chunks) { // Can safely update the GUI from this method.
                int mostRecentValue = chunks.get(chunks.size() - 1);
                selectedIndex=mostRecentValue;
                jListInputPics.setSelectedIndex(selectedIndex);
                renderPreviewImage();
            }
        };
        worker.execute();
    }

    private static void loadPDFAction() {
        SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                File selectedFile=null;
                File tempPdfPageFile=null;
                BufferedImage tempPageImg=null;
                String tempPdfPageFileName=null;
                
                selectFileChooser = new JFileChooser();
                selectFileChooser.setCurrentDirectory(WORK_DIR);
                selectFileChooser.setDialogTitle("Upload PDF File:");
                selectFileChooser.setMultiSelectionEnabled(false);
                selectFileChooser.setAcceptAllFileFilterUsed(false);
                filter = new FileNameExtensionFilter("Pdf File (.pdf)", "pdf");
                selectFileChooser.addChoosableFileFilter(filter);
                
                option = selectFileChooser.showOpenDialog(appFrame);
                if (option == JFileChooser.APPROVE_OPTION) {
                    selectedFile = selectFileChooser.getSelectedFile();
                    document = PDDocument.load(selectedFile);
                    pdfRenderer = new PDFRenderer(document);
                    
                    if(selectedFile!=null) {
                        totalNoOfPages = document.getNumberOfPages();
                        for (int p = 0; p < totalNoOfPages; p++) {  // FOR-EACH PAGE
                            tempPageImg = pdfRenderer.renderImageWithDPI(p, imageDPI, ImageType.RGB);
                            tempPdfPageFileName = String.format(helpers.getCurrentTimeStamp() + "_tempPdfPage_%d.%s", p + 1, "jpg");
                            ImageIOUtil.writeImage(tempPageImg, tempPdfPageFileName, imageDPI);
                            tempPdfPageFile = new File(tempPdfPageFileName);
                            
                            if (tempPdfPageFile.exists()) {
                                publish(p); // to be received by process | 2nd parameter
                                try {
                                    imageURI = helpers.getImageFileURI(tempPdfPageFile);
                                    INPUT_IMG_URI_LIST.add(imageURI);
                                    jListInputPicsModel.addElement("Page " + p);
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                } finally {
                                    boolean tempFileRemoved = tempPdfPageFile.delete();
                                    if (tempFileRemoved) {
                                        System.out.println("Temp file: " + tempPdfPageFileName + " removed successfully.");
                                    }
                                }
                            }
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
                    if(status) {
                        updatePreviewedPageNo();
                        document.close();
                    }
                } catch (InterruptedException | ExecutionException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void process(List<Integer> chunks) { // Can safely update the GUI from this method.
                int mostRecentValue = chunks.get(chunks.size() - 1);
                selectedIndex=mostRecentValue;
                jListInputPics.setSelectedIndex(selectedIndex);
                renderPreviewImage();
            }
        };
        worker.execute();
    }
  
    private static void runOcrAllAction() {
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                Path currentRelativePath = Paths.get("");
                String s = currentRelativePath.toAbsolutePath().toString();
                File dataDir = new File(s, "tessdata");
                Tesseract tesseract = new Tesseract();
                
                try {
                    tesseract.setDatapath(dataDir.getAbsolutePath());
                    for (int p = 0; p < INPUT_IMG_URI_LIST.size(); p++) {
                        jListInputPics.setSelectedIndex(p);
                        selectedIndex=p;
                        imageURI = INPUT_IMG_URI_LIST.get(p);
                        if (imageURI != null) {
                            byte[] fileBytes = Base64.getDecoder().decode(imageURI);
                            Image img = ImageIO.read(new ByteArrayInputStream(fileBytes));
                            ImageIcon imgIcon = new ImageIcon(img);
                            BufferedImage ipImg = helpers.getBufferedImage(imgIcon);
//                            BufferedImage bImg=helpers.getProcessedBufferImg(ipImg);
                            extractedOutput = tesseract.doOCR(ipImg);
                            publish(extractedOutput); // to be received by process | 2nd parameter
                        } else {
                            System.out.println("Input source does not exists/is invalid.");
                        }
                    }
                    return true;
                } catch (TesseractException | IOException err) {
                    err.printStackTrace();
                }
                return false;
            }

            @Override
            protected void done() { // Can safely update the GUI from this method.
                try {
                    boolean status = get(); // Retrieve the return value of doInBackground.
                    if(status) {
                        updatePreviewedPageNo();
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void process(List<String> chunks) { // Can safely update the GUI from this method.
                String mostRecentValue = chunks.get(chunks.size() - 1);
                extractedOutput=mostRecentValue;
                textArea.append(extractedOutput);
                renderPreviewImage();
            }
        };
        worker.execute();
    }

    private static void runOcrAction() {
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                Path currentRelativePath = Paths.get("");
                String s = currentRelativePath.toAbsolutePath().toString();
                File dataDir = new File(s, "tessdata");
                Tesseract tesseract = new Tesseract();
                try {
                    tesseract.setDatapath(dataDir.getAbsolutePath());
                    tesseract.setLanguage("eng"); // vie
                    imageURI = INPUT_IMG_URI_LIST.get(selectedIndex);
                    if (imageURI != null) {
                        byte[] fileBytes = Base64.getDecoder().decode(imageURI);
                        Image img = ImageIO.read(new ByteArrayInputStream(fileBytes));
                        ImageIcon imgIcon = new ImageIcon(img);
                        BufferedImage ipImg = helpers.getBufferedImage(imgIcon);
//                        BufferedImage bImg=helpers.getProcessedBufferImg(ipImg);
                        extractedOutput = tesseract.doOCR(ipImg);
                        publish(extractedOutput); // to be received by process | 2nd parameter
                    } else {
                        System.out.println("Input source does not exists/is invalid.");
                    }
                    return true;
                } catch (TesseractException | IOException err) {
                    err.printStackTrace();
                }
                return false;
            }

            @Override
            protected void done() { // Can safely update the GUI from this method.
                try {
                    boolean status = get(); // Retrieve the return value of doInBackground.
                    if(status) {
                        updatePreviewedPageNo();
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void process(List<String> chunks) { // Can safely update the GUI from this method.
                String mostRecentValue = chunks.get(chunks.size() - 1);
                extractedOutput=mostRecentValue;
                textArea.append(extractedOutput);
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
                
                BufferedImage ipImg=helpers.getBufferedImage(imgIcon);
//                BufferedImage opImg=helpers.getProcessedBufferImg(ipImg);
                Image outputImg = ipImg.getScaledInstance((int) newWidth, (int) newHeight, Image.SCALE_SMOOTH);
                imgIcon.setImage(outputImg);
                imagePreview.setIcon(imgIcon);
            } catch (IOException ex) {
                ex.printStackTrace();
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

    private static void prevItemAction() {
        if (selectedIndex > 0) {
            selectedIndex = selectedIndex - 1;
            jListInputPics.setSelectedIndex(selectedIndex);
            renderPreviewImage();
            updatePreviewedPageNo();
        }
    }
    private static void nextItemAction() {
        if (selectedIndex < (jListInputPicsModel.getSize() - 1)) {
            selectedIndex = selectedIndex + 1;
            jListInputPics.setSelectedIndex(selectedIndex);
            renderPreviewImage();
            updatePreviewedPageNo();
        }
    }
    private static void fitImage() {
        currentImageScale = 1.0;
        renderPreviewImage();
    }
    private static void zoomInImage() {
        int selectedImgLength = selectedImgWidth;
        int maxLength = maxImgWidth;

        if (selectedImgHeight > selectedImgWidth) {
            selectedImgLength = selectedImgHeight;
            maxLength = maxImgHeight;
        }
        int updatedImgLength = (int) ((currentImageScale * 1.25) * maxLength);
        if (updatedImgLength < selectedImgLength) {
            currentImageScale = currentImageScale * 1.25;
            renderPreviewImage();
        }
    }
    private static void zoomOutImage() {
        if (currentImageScale > 0.4096) { // 0.8⁴ = 0.4096 | Math.power(0.8,4)
            currentImageScale = currentImageScale * 0.8;
            renderPreviewImage();
        }
    }
    private static void copyTextToClipboardAction() {
        String str = textArea.getText();
        textArea.selectAll();
        copyTxtBtn.transferFocusBackward();

        StringSelection stringSelection = new StringSelection(str);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
    private static void saveTextAction() {
        saveFileChooser = new JFileChooser();
        saveFileChooser.setDialogTitle("Save Output To File:");
        saveFileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        outputFile = new File("output_" + helpers.getCurrentTimeStamp() + ".txt");
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
                writer.write(textArea.getText());
                writer.close();
                Desktop.getDesktop().open(outputFile);
            } catch (Exception ex1) {
                System.out.println("Error: " + ex1);
            }
        }
    }
    private static void runClearTextAction() {
        textArea.setText("");
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
        dividerLocation = measurementConstants.getDividerLocation();
        resizeWeight = measurementConstants.getResizeWeight();
        splitPaneBorderThickness = measurementConstants.getSplitPaneBorderThickness();
        scrollPanelPadding = measurementConstants.getScrollPanePadding();

        imageDPI = measurementConstants.getImageDPI();

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
        fitImageBtnText = contentConstants.getFitImageText();

        imagePreviewPlaceholder = contentConstants.getImagePreviewPlaceholder();
        textareaPlaceholder = contentConstants.getTextareaPlaceholder();
        
        appBgColor = colorConstants.getAppBgColor();
        appFontColor = colorConstants.getAppFontColor();
        splitPaneBgColor = colorConstants.getSplitPaneBgColor();
        splitPaneFontColor = colorConstants.getSplitPaneFontColor();
        splitPaneParentPaneObjBgColor = colorConstants.getSplitPaneParentPaneObjBgColor();
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
        helpers = new Helpers();
        setConstantValues();
        
        appFrame = new JFrame(appTitle);
        appFrame.getRootPane().putClientProperty("JRootPane.titleBarBackground", appBgColor);
        appFrame.getRootPane().putClientProperty("JRootPane.titleBarForeground", appFontColor);
        try {
            byte[] fileBytes = Base64.getDecoder().decode(appIconURi);
            Image img = ImageIO.read(new ByteArrayInputStream(fileBytes));
            ImageIcon imgIcon = new ImageIcon(img);
            appFrame.setIconImage(imgIcon.getImage());
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            addComponentsToPane(appFrame.getContentPane());
            appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            appFrame.setSize(frameWidth, frameHeight);
            appFrame.setPreferredSize(appFrame.getSize());

            maxImgWidth = ((frameWidth - dividerSize - ((2 * scrollPanelPadding) + (4 * splitPaneBorderThickness))) / 2) - 105;
            maxImgHeight = frameHeight - placeholderFontSize - maxIconLength - iconFontSize - ((2 * scrollPanelPadding) + (2 * splitPaneBorderThickness)) - 215;

            splitPane.setDividerLocation(dividerLocation);
            appFrame.setLocationRelativeTo(null);
        }
    }

    public static void main(String[] args) {
        Logger.getRootLogger().removeAllAppenders();
        Logger.getRootLogger().addAppender(new NullAppender());
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf()); // new FlatLightLaf()
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // Event dispatch thread For GUI code (asynchronously)
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
            appFrame.setVisible(true);
        });
    }
}