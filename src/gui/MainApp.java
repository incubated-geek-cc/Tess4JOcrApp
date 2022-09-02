package gui;

import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.*;
import java.awt.event.*;
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
import java.util.Base64;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultCaret;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class MainApp extends JPanel  {
    private static JFrame APP_FRAME;
    
    private static int MAINFRAME_HEIGHT = 502;
    private static int MAINFRAME_WIDTH = 550;
    
    private JFileChooser FILE_CHOOSER;
    private JTextArea OUTPUT_TEXT_AREA;
    private JScrollPane JSCROLL_PANEL_OUTPUT;
    
    private static BufferedImage img;
    private static ImageIcon icon;
    private static int maxImgLength=195;
    private static JLabel jLabelImgDisplay;

    private JButton jButtonChooseFile;
    private JLabel jLabelFileTitle;
    private JLabel jLabelAppTitle;
    private JLabel jLabelFileName;
    private JLabel jLabelExtractedTextTitle;
    private JButton jButtonSaveFile;
    private JButton jButtonResetAll;
    
    private static JLabel jLabelEndNote;
    private static JLabel jLabelHyperlink;
    
    private static File inputFile = null;
    private static File outputFile = null;
    private static String extractedOutput = null;

    private static Font placeholderFont = new Font("Segoe UI Emoji", Font.BOLD, 15); 
    private static Font labelFont = new Font("Arial Nova Cond", Font.BOLD, 12);
    
    private static Font tipFont = new Font("Arial Unicode MS", Font.ROMAN_BASELINE, 12);
    private static Font btnFont = new Font("Apple Color Emoji", Font.ROMAN_BASELINE, 11); // Arial Nova LightFreesiaUPC  
    
    private static Border etchedBorder = new EtchedBorder(EtchedBorder.RAISED);
    private static Border imgDisplayBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED); //.createBevelBorder(BevelBorder.LOWERED); // .createLineBorder(new Color(54, 55, 56), 1);
    
    private static final String PROFILE_LINK = "https://medium.com/@geek-cc";
    private static final String TITLE_COLON = ":";
    
    public MainApp() {
        //construct components
        jLabelEndNote = new JLabel("📌 Created by ξ(🎀˶❛◡❛) 🇹🇭🇪 🇷🇮🇧🇧🇴🇳 🇬🇮🇷🇱— More at"+TITLE_COLON);
        jLabelEndNote.putClientProperty("FlatLaf.styleClass", "default");
        jLabelEndNote.setForeground(Color.WHITE);
        
        jLabelHyperlink = new JLabel(" "+PROFILE_LINK);
        jLabelHyperlink.putClientProperty("FlatLaf.styleClass", "default");
        jLabelHyperlink.setForeground(new Color(207, 120, 149));
        jLabelHyperlink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Border btnBorder = BorderFactory.createEtchedBorder();
        jLabelHyperlink.setBorder(btnBorder);
        
        jButtonChooseFile = new JButton ("📂 Choose File…");
        jButtonChooseFile.setFont(btnFont);
        jButtonChooseFile.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        jButtonSaveFile = new JButton ("💾 Save As…");
        jButtonSaveFile.setFont(btnFont);
        jButtonSaveFile.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        jButtonResetAll = new JButton ("🔄 Reset All");
        jButtonResetAll.setFont(btnFont);
        jButtonResetAll.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        jLabelFileTitle = new JLabel ("  📤  Image Upload"+TITLE_COLON);
        jLabelFileTitle.setFont(labelFont);
        
        jLabelAppTitle = new JLabel ("🕮 Current application version supports only the English language.");
        jLabelAppTitle.setFont(tipFont);
        
        jLabelFileName = new JLabel ("No File Chosen");
        jLabelFileName.setFont(tipFont);
        
        jLabelExtractedTextTitle = new JLabel ("🗦💡🗧OCR Results"+TITLE_COLON); 
        jLabelExtractedTextTitle.setFont(labelFont);
        
        jLabelImgDisplay = new JLabel("🖼 Image Preview");
        jLabelImgDisplay.setFont(placeholderFont);
        jLabelImgDisplay.setHorizontalAlignment(JLabel.CENTER);
        jLabelImgDisplay.setHorizontalTextPosition(JLabel.CENTER);
        
        jLabelImgDisplay.setBorder(imgDisplayBorder);
        
        OUTPUT_TEXT_AREA = new JTextArea(5, 5);
        OUTPUT_TEXT_AREA.setWrapStyleWord(true);
        JSCROLL_PANEL_OUTPUT = new JScrollPane(OUTPUT_TEXT_AREA);
        
        jButtonResetAll.setEnabled(true);
        jButtonChooseFile.setEnabled(true);
        jButtonSaveFile.setEnabled(false);
        OUTPUT_TEXT_AREA.setEditable(true);
        
        JSCROLL_PANEL_OUTPUT.setHorizontalScrollBar(null);
        DefaultCaret caret = (DefaultCaret) OUTPUT_TEXT_AREA.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        
        //set component bounds (only needed by Absolute Positioning)
        int leftMargin=20;
        int btnHeight=25;
        int btnWidth=120;
        
        int gapLength=5;
        
        int panelWidth=444; 
        
        jLabelAppTitle.setBounds(leftMargin, 10, 400, btnHeight);
        
        jLabelFileTitle.setBounds(leftMargin, 40+gapLength, 400, btnHeight);
        jButtonChooseFile.setBounds(leftMargin, 40+btnHeight+gapLength, btnWidth, btnHeight);
        jLabelFileName.setBounds(gapLength+leftMargin+btnWidth, 40+btnHeight+gapLength, 245, btnHeight);
        
        jLabelImgDisplay.setBounds(leftMargin+(panelWidth-maxImgLength)/2, 40+btnHeight+gapLength+btnHeight+gapLength+gapLength+gapLength, maxImgLength, maxImgLength);
        
        jLabelExtractedTextTitle.setBounds(leftMargin, 300+gapLength+gapLength+gapLength+gapLength+gapLength, btnWidth, btnHeight);
        JSCROLL_PANEL_OUTPUT.setBounds(leftMargin, 330+gapLength+gapLength+gapLength+gapLength, panelWidth, 105); 
        
        jButtonSaveFile.setBounds(leftMargin+panelWidth-btnWidth-btnWidth-gapLength, 300+gapLength+gapLength+gapLength+gapLength, btnWidth, btnHeight);
        jButtonResetAll.setBounds(leftMargin+panelWidth-btnWidth, 300+gapLength+gapLength+gapLength+gapLength, btnWidth, btnHeight);
        
        int endNoteYPox=330+gapLength+gapLength+gapLength+gapLength+105+gapLength+gapLength;
        jLabelEndNote.setBounds(leftMargin, endNoteYPox, 270, 30);
        jLabelHyperlink.setBounds(leftMargin+265, endNoteYPox, 180, 30);
        
        //adjust size and set layout
        setLayout(null);
        setPreferredSize(new Dimension(MAINFRAME_HEIGHT, MAINFRAME_WIDTH));
        
        //add components
        add(jButtonChooseFile);
        add(jLabelFileTitle);
        add(jLabelAppTitle);
        add(jLabelFileName);
        add(jLabelImgDisplay);
        add(JSCROLL_PANEL_OUTPUT);
        add(jLabelExtractedTextTitle);
        add(jButtonSaveFile);
        add(jButtonResetAll);
        add(jLabelEndNote);
        add(jLabelHyperlink);
        
        // Add actions performed by each selectable component
        jButtonChooseFile.addActionListener((java.awt.event.ActionEvent evt) -> {
            selectFileAction(evt);
        });

        jButtonSaveFile.addActionListener((java.awt.event.ActionEvent evt) -> {
            saveOutputFileAction(evt);
        });
        
        jButtonResetAll.addActionListener((java.awt.event.ActionEvent evt) -> {
            resetAllAction(evt);
        });
        
        jLabelHyperlink.addMouseListener(new MouseAdapter() {
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
    
    private void selectFileAction(ActionEvent e) {
        FILE_CHOOSER = new JFileChooser();

        FILE_CHOOSER.setDialogTitle("📂 Select Input Image");
        FILE_CHOOSER.setMultiSelectionEnabled(false);
        FILE_CHOOSER.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, GIF, and PNG Images", "jpg", "gif", "png");
        FILE_CHOOSER.addChoosableFileFilter(filter);

        int option = FILE_CHOOSER.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = FILE_CHOOSER.getSelectedFile();
            inputFile = selectedFile.getAbsoluteFile();
            if (inputFile != null) {
                jLabelImgDisplay.setText("");
                try {
                    img = ImageIO.read(inputFile);
                    icon = new ImageIcon(img);
                   
                    int iconWidth=icon.getIconWidth();
                    int iconHeight=icon.getIconHeight();
                    int iconLength=iconWidth;
                    if(iconHeight>iconWidth) {
                        iconLength=iconHeight;
                    }
                    double ratio=(maxImgLength*1.0)/(iconLength*1.0);
                    long newWidth=Math.round(ratio*iconWidth);
                    long newHeight=Math.round(ratio*iconHeight);
                    
                    Image resizedImg = icon.getImage();
                    Image newImg = resizedImg.getScaledInstance((int) newWidth, (int) newHeight, java.awt.Image.SCALE_SMOOTH); 
                    icon.setImage(newImg);
                    jLabelImgDisplay.setIcon(icon);
                    
                    jButtonChooseFile.setEnabled(false);
                    jButtonSaveFile.setEnabled(true);
                    OUTPUT_TEXT_AREA.setEditable(true);
                } catch (IOException ex) {
                }
                jLabelFileName.setText(inputFile.getName());
                Path currentRelativePath = Paths.get("");
                String s = currentRelativePath.toAbsolutePath().toString();
                File dataDir=new File(s, "tessdata");
                Tesseract tesseract = new Tesseract();
                try {
                    tesseract.setDatapath(dataDir.getAbsolutePath());
                    if(inputFile.exists()) {
                        extractedOutput = tesseract.doOCR(inputFile);
                        extractedOutput=extractedOutput.trim();
                        OUTPUT_TEXT_AREA.setText(extractedOutput);

                        jButtonChooseFile.setEnabled(false);
                        jButtonSaveFile.setEnabled(true);
                        OUTPUT_TEXT_AREA.setEditable(true);
                    } else {
                        System.out.println("Input source does not exists/is invalid.");
                    }
                } catch (TesseractException err) {
                    err.printStackTrace();
                }
            }
        }
    }
    
    private void resetAllAction(ActionEvent e) {
        jButtonChooseFile.setEnabled(true);
        jButtonSaveFile.setEnabled(false);
        OUTPUT_TEXT_AREA.setEditable(true);
        
        OUTPUT_TEXT_AREA.setText("");
        jLabelImgDisplay.setIcon(null);
        jLabelImgDisplay.revalidate();
        jLabelImgDisplay.setText("🖼 Image Preview");
        img = null;
        icon = null;
        jLabelFileName.setText("No File Chosen");
        
        inputFile = null;
        outputFile = null;
        extractedOutput = null;
    }
    
    private void saveOutputFileAction(ActionEvent e) {
        JFileChooser saveFileChooser = new JFileChooser();
        saveFileChooser.setDialogTitle("💾 Save Output To File");
        saveFileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        outputFile = new File("output_" + getCurrentTimeStamp() + ".txt");
        saveFileChooser.setSelectedFile(outputFile);
        saveFileChooser.setFileFilter(new FileNameExtensionFilter("Text Documents (*.txt)", "txt"));

        int option = saveFileChooser.showSaveDialog(this);
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
    
    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmaa");
        Date date = new Date();
        String timestamp = sdf.format(date);

        return timestamp;
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel( new FlatDarkLaf() );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override 
            public void run() {
                try {
                    String iconURI = "iVBORw0KGgoAAAANSUhEUgAAABkAAAAZCAYAAADE6YVjAAAAAXNSR0IArs4c6QAAAgJJREFUSEvtls9rE0EUxz/bldS42vSgiyhiAy5eBCOCQujB5uTN+B8E/Ae86EVq0oPQiiDoSUFqbhFEe/AHXhJFKlTE9mAMirKRILVrwSVt0ybZ3chENqbJ0m4vOWgfLOy+9+Z9Zr4z81iJHpjUAwbbkC2p/P/JNQgc99DolR/d/MgVAXKAAHXaFHB+M5AfyJSmaefi8fi6WoZhkE6nhW8EeLkRqBMiZnsTGGobFIlGo4OappHNZptuVVWJxWJkMhlKpVIREI9rJjAGzLmOdogA5I7JgcjZwO51ExNFFUVB1/WmX7yHw+E/3ysryEjk7VozVnTqzFhrAnTChbdDEiGpb/Jt6DChPnkzmZtxy3GYtdewOrJHK4t8sGtCy4QItUNS0R3B5KOBg74AIqns2OTtalf+g+oSmdqSOHlnuiDD8s7kuLKPwOmTWGOX+f75C7vGb6HO/2wVqj6+T800+Xb7LupcntfB7lXPlk1eLP/yhozIweSdPfth+BT1iasUCgX2jk5wYH6xBalPP6H8Y4HitRsMvf/IO9npWsnzismz1bI3xJWrcfQIzsN7TGdzRK5cZ2C50ipkv3nKV2OB+qUUhz7pW5erJ3uyDenc+Y2OcEJBmrzQH6Jf8tPS/pauNhoYDbvlmLFW0R3L8zKKpJR7gXzfSO9E0csuAqK9/EM/Er8BjC7lGlROE4wAAAAASUVORK5CYII=";
                    APP_FRAME = new JFrame(" 🔎 𝗧𝗲𝘀𝘀𝗲𝗿𝗮𝗰𝘁 𝗢𝗖𝗥 :: 𝐈𝐦𝐚𝐠𝐞-𝐭𝐨-𝐓𝐞𝐱𝐭 𝐄𝐱𝐭𝐫𝐚𝐜𝐭𝐢𝐨𝐧 🛠𝐓𝐨𝐨𝐥 ");
                    APP_FRAME.getRootPane().putClientProperty("JRootPane.titleBarBackground", new Color(70,73,75));
                    
                    byte[] decodedBytes = Base64.getDecoder().decode(iconURI);
                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(decodedBytes));
                    ImageIcon icon = new ImageIcon(image);
                    APP_FRAME.setIconImage(icon.getImage());
                    
                    APP_FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    APP_FRAME.getContentPane().add(new MainApp());
                    APP_FRAME.pack();
                    
                    APP_FRAME.setPreferredSize(new Dimension(MAINFRAME_HEIGHT+40, MAINFRAME_WIDTH+40));
                    APP_FRAME.setSize(MAINFRAME_HEIGHT, MAINFRAME_WIDTH);
                    APP_FRAME.setLocationRelativeTo(null);
                    APP_FRAME.setVisible(true);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });;
    }
}
        
        