package gui;

import com.formdev.flatlaf.FlatDarkLaf;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class Main {

    private final static String appTitle = "🔎 𝗧𝗲𝘀𝘀𝗲𝗿𝗮𝗰𝘁 𝗢𝗖𝗥 :: 𝐈𝐦𝐚𝐠𝐞-𝐭𝐨-𝐓𝐞𝐱𝐭 𝐄𝐱𝐭𝐫𝐚𝐜𝐭𝐢𝐨𝐧 🛠𝐓𝐨𝐨𝐥";
    
    private final static String APP_ICON_URI = "iVBORw0KGgoAAAANSUhEUgAAABkAAAAZCAYAAADE6YVjAAAAAXNSR0IArs4c6QAAAgJJREFUSEvtls9rE0EUxz/bldS42vSgiyhiAy5eBCOCQujB5uTN+B8E/Ae86EVq0oPQiiDoSUFqbhFEe/AHXhJFKlTE9mAMirKRILVrwSVt0ybZ3chENqbJ0m4vOWgfLOy+9+Z9Zr4z81iJHpjUAwbbkC2p/P/JNQgc99DolR/d/MgVAXKAAHXaFHB+M5AfyJSmaefi8fi6WoZhkE6nhW8EeLkRqBMiZnsTGGobFIlGo4OappHNZptuVVWJxWJkMhlKpVIREI9rJjAGzLmOdogA5I7JgcjZwO51ExNFFUVB1/WmX7yHw+E/3ysryEjk7VozVnTqzFhrAnTChbdDEiGpb/Jt6DChPnkzmZtxy3GYtdewOrJHK4t8sGtCy4QItUNS0R3B5KOBg74AIqns2OTtalf+g+oSmdqSOHlnuiDD8s7kuLKPwOmTWGOX+f75C7vGb6HO/2wVqj6+T800+Xb7LupcntfB7lXPlk1eLP/yhozIweSdPfth+BT1iasUCgX2jk5wYH6xBalPP6H8Y4HitRsMvf/IO9npWsnzismz1bI3xJWrcfQIzsN7TGdzRK5cZ2C50ipkv3nKV2OB+qUUhz7pW5erJ3uyDenc+Y2OcEJBmrzQH6Jf8tPS/pauNhoYDbvlmLFW0R3L8zKKpJR7gXzfSO9E0csuAqK9/EM/Er8BjC7lGlROE4wAAAAASUVORK5CYII=";

    private final static String OPEN_IMG_ICON_URI = "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";
    
    private final static String RUN_OCR_ICON_URI = "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";
    
    private final static String MORE_INFO_ICON_URI = "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";
    
    private final static String SAVE_FILE_ICON_URI = "iVBORw0KGgoAAAANSUhEUgAAAGQAAABkCAYAAABw4pVUAAAAAXNSR0IArs4c6QAAA/ZJREFUeF7tnb1uGkEQx4cvUxiXUaJETuhoUri3xGtEKXgBJJcRHbLsAqM0SDwFeQxKyrxAFCtlpKRBELAhWsRJwO3hnb27ZW7vf40Lz87M/X87+3FfFAiHKAUKorJBMgQgwjoBgACIMAWEpYMKARBhCghLBxUCIMIUEJYOKgRAhCkgLB1UCIAIU0BYOqgQT4CshZ2H63TU+RfTCGpbIXkHolikAgVA4nXzxKEASDwgiVcKgMQHEniw1XIvA1snoTmk0+nQarXacz4ej6lQ2A/RbDZpvdZPQY1Gg66urpKTKIan0WgUal0sFqnf70d5TWT4ApAIeXVAlGnaUHIBRIl4c3MTWS+DwSBUyVFAlJNyuUy9Xi+VSskNkHa7HQlkOByygKQJBUCIyAaIwfClTNj6shtsu5nRpM6dU9Oa1NWQlXSFBOf2wpzChgIgMSokgFIqlejh4SGq/7E0ZhnvRESFHMh/cXFB3W5XB4WlMcsYQKIH4fPzc7q9vQUQk3kqzTkkiA8gJiS2NgDCEGvXNIurLFSIBWxUiIVoqgkqxGIniY2hvrdhUmdUIYYshliY1PfFysXGUJ2yGlKijul0GvrXscvvOj8Ysiyr0LQZgKS4yjKFsGsHIDaqCWrj5ZAlSF92KgDClizdBgCSrr5s75kCojZm6kmNrB+LxSLyFDIFpF6vU6vVyjoPur+/BxBJFAFEEg0iVIgwHn4DUQ9cHz6ILQ3AYT5eD1lPT080m83EMlCP9QCIIDy5A6K0lzxk6d5h8XrIElQMxqkAiLFUbgzv7u4iqxo7dTcM9qKgQk4g+rGQXldIFpe9AHLCCtEte70HMp/PTyj58dC1Wi1k4DUQsSSOJAYgwqgBCIAYK8B6x9CXO4ZeV4j6Lsrz87NxD3BtWKlU8jWpYx+i72JOHrbWDVlZBIJLJ67HqRfiAQiAGCuAVdaBVLj8btx3kjP0etmbnEzuPHkNJIurLABx1/lDkXJ3+V3t0lWVSD2q1Wq+dupSQeT2Fi6ACLt0AiAAErsPeLPK+jA7o89/38UW5NQOem9+kPpy+nK5pOvr6006k8lk8zdTO/X3Pxf06dufU+sZO/7XL683PtS7hsEresEHBgAktrx8BwDC1yzVFgEQXRBUSKrS651nCsjl5aX2d0HUexZn/1b06rfc++embH+91b9rr95zUV+3fnx81Lli3ZVlGe9Ew4+CmVJkfkYRQMyFtbVkacwyRoVYMWFpzDIGELlAPm5/WNEqwxw1KhHRd8752lYIJwZsGQoACEMsF6YA4kJlRgwAYYjlwhRAXKjMiAEgDLFcmAKIC5UZMQCEIZYLUwBxoTIjBoAwxHJhCiAuVGbE+A/apEeS705lFwAAAABJRU5ErkJggg==";
    
    private final static String CLEAR_TXT_ICON_URI = "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";

    private static final int MAX_ICON_LENGTH = 35;
    private static final String PROFILE_LINK = "https://medium.com/@geek-cc";
    private static final String TITLE_COLON = ":";
    private static final String BULLET_POINT = "⮞";
    private static final Color TITLE_BAR_COLOR = new Color(91, 94, 96);
    private static final Color TITLE_BAR_FONT_COLOR = new Color(255, 255, 255);
    private static final JLabel LABEL_END_NOTE = new JLabel("📌 Created by ξ(🎀˶❛◡❛) 🇹🇭🇪 🇷🇮🇧🇧🇴🇳 🇬🇮🇷🇱— More at"+TITLE_COLON);
    
    private static JLabel labelProfileLink;
    
    private static JFrame appFrame;
    private static JFileChooser saveFileChooser;
    
    private static GridBagConstraints c;
    private static Insets insets;
    
    private static byte[] decodedBytes;
    private static Image resizedImg;
    private static Image newImg;
    private static BufferedImage iconImg;
    private static ImageIcon icon;
    
    private static Border buttonBorder;
    private static Border imagePreviewBorder;
    private static Border bottomPanelBorder;
    private static Border footerBorder;
    
    private static Font iconFont;
    private static Font placeholderFont;
    private static Font textFont;
    private static Font footerFont;
    
    private static JButton openImgBtn;
    private static JButton runOCRBtn;

    private static JButton saveTxtBtn;
    private static JButton clearTxtBtn;
    private static JButton moreInfoBtn;
    
    private static JSplitPane splitPane;
    
    private static int maxImgWidth;
    private static int maxImgHeight;

    private static JScrollPane textScrollPane;
    private static JTextArea textArea;
    
    private static JScrollPane imagePreviewScrollPane;
    private static JLabel imagePreview;
    
    private static JDialog infoDialog;
    private static JPanel infoPane;
    
     private static JPanel p1;
    private static int v1;
    private static int h1;

    private static JPanel p2;
    private static int v2;
    private static int h2;

    private static int dividerSize;
    
    private static JFileChooser fileChooser;
    private static JPanel bottomPanel;
    private static JPanel panelObj;
    
    private static File inputFile = null;
    private static File outputFile = null;
    private static String extractedOutput = null;
    
    public static void addComponentsToPane(Container pane) {
        //construct components
        bottomPanelBorder = BorderFactory.createEtchedBorder();
        buttonBorder = BorderFactory.createRaisedSoftBevelBorder();
        imagePreviewBorder = BorderFactory.createLineBorder(new Color(60, 63, 65), 10);
        
        iconFont = new Font("Consolas", Font.PLAIN, 11);
        textFont = new Font("Arial", Font.ROMAN_BASELINE, 12);
        placeholderFont = new Font("Segoe UI Emoji", Font.BOLD, 15);
        footerFont = new Font("Arial Nova Light", Font.ROMAN_BASELINE, 12);
        
        LABEL_END_NOTE.putClientProperty("FlatLaf.styleClass", "default");
        LABEL_END_NOTE.setForeground(TITLE_BAR_FONT_COLOR);
        LABEL_END_NOTE.setFont(footerFont);
        
        labelProfileLink = new JLabel(" "+PROFILE_LINK);
        labelProfileLink.putClientProperty("FlatLaf.styleClass", "default");
        labelProfileLink.setForeground(new Color(207, 120, 149));
        labelProfileLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        labelProfileLink.setBorder(bottomPanelBorder);
        
        openImgBtn = new JButton("Open Image");
        runOCRBtn = new JButton(" Run OCR » ");
        saveTxtBtn = new JButton(" Save Text");
        clearTxtBtn = new JButton("Clear Text");
        moreInfoBtn = new JButton(" More Info");
       
        pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        pane.setLayout(new GridBagLayout());
        
        JButton[] mainButtonsArr = {
            openImgBtn,
            runOCRBtn,
            saveTxtBtn,
            clearTxtBtn,
            moreInfoBtn
        };
        // set common attributes accross all iconButtons
        for (JButton iconButton : mainButtonsArr) {
            iconButton.setBorder(buttonBorder);
            iconButton.setFont(iconFont);
            iconButton.setForeground(TITLE_BAR_FONT_COLOR);
            iconButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            iconButton.setHorizontalTextPosition(SwingConstants.CENTER);
            iconButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        }

        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;
        c.ipadx = 25;
        c.ipady = 10;
        c.gridwidth = 1;
        c.gridheight = 1;

        insets = new Insets(2, 2, 1, 0); // T L B R
        c.gridx = 0;
        c.insets = insets;
        openImgBtn.setIcon(getImageIcon(OPEN_IMG_ICON_URI));
        pane.add(openImgBtn, c);

        insets = new Insets(2, 1, 1, 0); // T L B R
        c.gridx = 1;
        c.insets = insets;
        runOCRBtn.setIcon(getImageIcon(RUN_OCR_ICON_URI));
        pane.add(runOCRBtn, c);
        
        c.gridx = 2;
        saveTxtBtn.setIcon(getImageIcon(SAVE_FILE_ICON_URI));
        pane.add(saveTxtBtn, c);
        
        c.gridx = 3;
        clearTxtBtn.setIcon(getImageIcon(CLEAR_TXT_ICON_URI));
        pane.add(clearTxtBtn, c);
        
        insets = new Insets(2, 250, 1, 2); // T L B R
        c.gridx = 4;
        c.gridwidth = 1;
        c.insets = insets;
        moreInfoBtn.setIcon(getImageIcon(MORE_INFO_ICON_URI));
        pane.add(moreInfoBtn, c);
        
        v1 = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
        h1 = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
        
        p1 = new JPanel();
        p1.setLayout(new BorderLayout());
        imagePreview = new JLabel("🖼 Image Preview");
        imagePreview.setForeground(TITLE_BAR_FONT_COLOR);
        imagePreview.setFont(placeholderFont);
        imagePreview.setHorizontalAlignment(JLabel.CENTER);
        imagePreview.setHorizontalTextPosition(JLabel.CENTER);
        imagePreviewScrollPane = new JScrollPane(imagePreview, v1, h1);
        p1.add(imagePreviewScrollPane, BorderLayout.CENTER);
        
        v2 = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
        h2 = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
        
        p2 = new JPanel();
        p2.setLayout(new BorderLayout());
        String text = "";
        textArea = new JTextArea();
        textArea.setText(text);
        textArea.setFont(textFont);
        textArea.setForeground(new Color(0, 0, 0));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(TITLE_BAR_FONT_COLOR);
        textScrollPane = new JScrollPane(textArea, v2, h2);
        p2.add(textScrollPane, BorderLayout.CENTER);
        
        dividerSize = 20;
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setBackground(TITLE_BAR_COLOR);
        splitPane.setResizeWeight(0.5);
        splitPane.setDividerSize(dividerSize);
        splitPane.setBorder(imagePreviewBorder);
        splitPane.setContinuousLayout(true);
        
        p1.setOpaque(true);
        p2.setOpaque(true);
        splitPane.setLeftComponent(p1);
        splitPane.setRightComponent(p2);
        
        panelObj = new JPanel();
        panelObj.setLayout(new BorderLayout());
        panelObj.add(splitPane, BorderLayout.CENTER);
        
        insets = new Insets(0, 0, 0, 0); // T L B R
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 5;
        c.gridheight = 1;
        c.insets = insets;
        c.ipady = 420;
        pane.add(panelObj, c);

        bottomPanel = new JPanel();
        bottomPanel.setFont(footerFont);
        
        insets = new Insets(0, 0, 0, 0); // T L B R
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 5;
        c.gridheight = 1;
        c.insets = insets;
        c.ipady = 12;
        
        footerBorder=BorderFactory.createLoweredBevelBorder();
        bottomPanel.setBorder(footerBorder);
        pane.add(bottomPanel, c);
        
        openImgBtn.setToolTipText("📖 Select an image for text extraction");
        runOCRBtn.setToolTipText("📖 Initates image OCR processing");
        saveTxtBtn.setToolTipText("📖 Saves all text content to a plain text(.txt) file");
        clearTxtBtn.setToolTipText("📖 Permanently removes all text present and resets text area to its initial blank state");
        moreInfoBtn.setToolTipText("📖 View other miscellaneous details about application");
        labelProfileLink.setToolTipText("📖 Select to open hyperlink");
        imagePreviewScrollPane.setToolTipText("📖 Renders a thumbnail of input image");
        textArea.setToolTipText("📖 Contains all text extracted from image uploaded");
        
        // Add actions performed by each selectable component
        openImgBtn.addActionListener((ActionEvent evt) -> {
            openImageAction(evt);
        });
        runOCRBtn.addActionListener((ActionEvent evt) -> {
            runOcrAction(evt);
        });
        clearTxtBtn.addActionListener((ActionEvent evt) -> {
            runClearTextAction(evt);
        });
        saveTxtBtn.addActionListener((ActionEvent evt) -> {
            saveTextAction(evt);
        });
        
        moreInfoBtn.addActionListener((ActionEvent evt) -> {
            viewInfoAction(evt);
        });
        
        labelProfileLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(PROFILE_LINK));
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
    
    /**
     * Set image icon for the first line of main buttons
     * Used in addComponentsToPanel to instantiate icons
    */
    private static ImageIcon getImageIcon(String iconImageURI) {
        decodedBytes = Base64.getDecoder().decode(iconImageURI);
        try {
            iconImg = ImageIO.read(new ByteArrayInputStream(decodedBytes));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        icon = new ImageIcon(iconImg);
        resizedImg = icon.getImage();
        newImg = resizedImg.getScaledInstance((int) MAX_ICON_LENGTH, (int) MAX_ICON_LENGTH, Image.SCALE_SMOOTH);
        icon.setImage(newImg);

        return icon;
    }
    
    /**
     * Prompts user to select an input image file
     */
    private static void openImageAction(ActionEvent e) {
        fileChooser = new JFileChooser();

        fileChooser.setDialogTitle("Select Input Image(s)");
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG and PNG Images", "jpg", "png");
        fileChooser.addChoosableFileFilter(filter);

        int option = fileChooser.showOpenDialog(appFrame);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            inputFile = selectedFile.getAbsoluteFile();
            if (inputFile != null) {
                imagePreview.setText("");
                try {
                    iconImg = ImageIO.read(inputFile);
                    icon = new ImageIcon(iconImg);

                    int iconWidth = icon.getIconWidth();
                    int iconHeight = icon.getIconHeight();
                    int iconLength = iconWidth;
                    if (iconHeight > iconWidth) {
                        iconLength = iconHeight;
                    }
                    double ratio = (maxImgWidth * 1.0) / (iconLength * 1.0);
                    long newWidth = Math.round(ratio * iconWidth);
                    long newHeight = Math.round(ratio * iconHeight);

                    resizedImg = icon.getImage();
                    newImg = resizedImg.getScaledInstance((int)newWidth, (int)newHeight, Image.SCALE_SMOOTH);
                    icon.setImage(newImg);
                    imagePreview.setIcon(icon);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    private static String getCurrentTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmaa");
        Date date = new Date();
        String timestamp = sdf.format(date);

        return timestamp;
    }
    
    private static void viewInfoAction(ActionEvent e) {
        infoDialog = new JDialog(appFrame, "🗦💡🗧More Info", true);
        infoPane = new JPanel();
        
        infoPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        infoPane.setLayout(new GridBagLayout());
        
        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 5;
        c.ipady = 5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.insets = new Insets(5, 10, 5, 10); // T L B R
        
        c.gridy = 0;
        infoPane.add(new JLabel(BULLET_POINT+" 💻 Built with Tess4J— A Tesseract OCR JNA Wrapper in Java"), c);
        
        c.gridy = 1;
        infoPane.add(new JLabel(BULLET_POINT+" 📂 Only file(s) with extensions '.png' and '.jpg' can be processed"), c);
        
        c.gridy = 3;
        infoPane.add(new JLabel(BULLET_POINT+" 📤 Current application enables only single file selection"), c);
        
        c.gridy = 4;
        infoPane.add(new JLabel(BULLET_POINT+" 📍 Application supports only the English language"), c);
        
        infoDialog.getContentPane().add(infoPane);
        infoDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        infoDialog.pack();
        infoDialog.setLocationRelativeTo(appFrame);
        infoDialog.setVisible(true);    
    }
    
    /* Save Text to local
    /* text file .txt
    */
    private static void saveTextAction(ActionEvent e) {
        saveFileChooser = new JFileChooser();
        saveFileChooser.setDialogTitle("💾 Save Output To File");
        saveFileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        outputFile = new File("output_" + getCurrentTimeStamp() + ".txt");
        saveFileChooser.setSelectedFile(outputFile);
        saveFileChooser.setFileFilter(new FileNameExtensionFilter("Text Documents (*.txt)", "txt"));

        int option = saveFileChooser.showSaveDialog(appFrame);
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
                writer.write(extractedOutput);
                writer.close();
                Desktop.getDesktop().open(outputFile);
            } catch (Exception ex1) {
                System.out.println("Error: " + ex1);
            }
        }
    }
    
    
    /**
     * Clears all text extracted on the right
     */
    private static void runClearTextAction(ActionEvent e) {
        textArea.setText("");
    }
    
    /**
     * Perform OCR Text Extraction after image is imported
    */
    private static void runOcrAction(ActionEvent e) {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        File dataDir = new File(s, "tessdata");
        Tesseract tesseract = new Tesseract();
        try {
            tesseract.setDatapath(dataDir.getAbsolutePath());
            if (inputFile.exists()) {
                extractedOutput = tesseract.doOCR(inputFile);
                extractedOutput = extractedOutput.trim();
                textArea.setText(extractedOutput);
            } else {
                System.out.println("Input source does not exists/is invalid.");
            }
        } catch (TesseractException err) {
            err.printStackTrace();
        }
    }
    
    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
     */
    private static void createAndShowGUI() {
        appFrame = new JFrame(appTitle);
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appFrame.getRootPane().putClientProperty("JRootPane.titleBarBackground", TITLE_BAR_COLOR);
        appFrame.getRootPane().putClientProperty("JRootPane.titleBarForeground", TITLE_BAR_FONT_COLOR);
        decodedBytes = Base64.getDecoder().decode(APP_ICON_URI);
        try {
            iconImg = ImageIO.read(new ByteArrayInputStream(decodedBytes));
            icon = new ImageIcon(iconImg);
            appFrame.setIconImage(icon.getImage());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        addComponentsToPane(appFrame.getContentPane());
        appFrame.pack();

        maxImgWidth = (splitPane.getWidth() - 10 - 10 - dividerSize) / 2;
        maxImgHeight = splitPane.getHeight() - 10;
        imagePreviewScrollPane.setBounds(5, 5, maxImgWidth, maxImgHeight);
        textScrollPane.setBounds(5, 5, maxImgWidth, maxImgHeight);
        
        maxImgWidth=maxImgWidth-20;
        maxImgHeight=maxImgHeight-20;
        
        bottomPanel.add(LABEL_END_NOTE);
        bottomPanel.add(labelProfileLink);
        LABEL_END_NOTE.setBounds(0, 0, 270, 30);
        labelProfileLink.setBounds(290, 0, 180, 30);
        splitPane.setDividerLocation(0.5);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf()); // new FlatLightLaf()
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // Event dispatch thread
        // For GUI code
        SwingUtilities.invokeLater(() -> { // asynchronously
            createAndShowGUI();
            appFrame.setLocationRelativeTo(null);
            appFrame.setVisible(true);
        });
    }
}