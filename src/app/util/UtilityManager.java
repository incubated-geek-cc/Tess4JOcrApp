package app.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.text.DefaultCaret;

public class UtilityManager {
    private static final String LOGGER_NAME = MethodHandles.lookup().lookupClass().getName();
    private static final Logger LOGGER = Logger.getLogger(LOGGER_NAME);

    private final JTextArea LOG_TEXT_AREA;
    private final JScrollPane JSCROLL_PANEL_OUTPUT_LOGS;
    
    public UtilityManager(JTextArea LOG_TEXT_AREA, JScrollPane JSCROLL_PANEL_OUTPUT_LOGS) {
        this.LOG_TEXT_AREA = LOG_TEXT_AREA;
        LOG_TEXT_AREA.setForeground(new Color(255,255,255));
        LOG_TEXT_AREA.setFont(new Font("Arial Nova Light", Font.PLAIN, 11));
        this.JSCROLL_PANEL_OUTPUT_LOGS = JSCROLL_PANEL_OUTPUT_LOGS;
        JSCROLL_PANEL_OUTPUT_LOGS.setHorizontalScrollBar(null);
        DefaultCaret caret = (DefaultCaret) LOG_TEXT_AREA.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        
        LOGGER.setUseParentHandlers(false);
        LOGGER.setLevel(Level.ALL);
        LOGGER.addHandler(new TextAreaHandler(new TextAreaOutputStream(LOG_TEXT_AREA)));
    }
    private void updateLogs() {
        JScrollBar vScrollBar = JSCROLL_PANEL_OUTPUT_LOGS.getVerticalScrollBar();
        vScrollBar.setValue(vScrollBar.getMaximum());
    }
    private static void addTextToOutputLogs(String logString) {
        LOGGER.info(() -> logString);
    }
    public String join(final String[] arr, final String separator) {
        if (arr.length < 2) {
            return (arr.length == 1 ? arr[0] : "");
        }
        final StringBuffer sb = new StringBuffer(arr[0]);
        for (int i = 1; i < arr.length; ++i) {
            sb.append(separator);
            sb.append(arr[i]);
        }
        return sb.toString();
    }
    public Logger getLogger() {
        return LOGGER;
    }
    public void outputConsoleLogsBreakline(String consoleCaption) {
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            @Override
            protected Boolean doInBackground() {
                String logString = "";
                int charLimit = 150;
                if (consoleCaption.length() > charLimit) {
                    logString = consoleCaption.substring(0, charLimit - 4) + " ...";
                } else {
                    String result = "";
                    if (consoleCaption.isEmpty()) {
                        for (int i = 0; i < charLimit; i++) {
                            result += "=";
                        }
                        logString = result;
                    } else {
                        charLimit = (charLimit - consoleCaption.length() - 1);
                        for (int i = 0; i < charLimit; i++) {
                            result += "-";
                        }
                        logString = consoleCaption + " " + result;
                    }
                }
                logString = logString + "\n";
                publish(logString);
                return true;
            }
            @Override
            protected void done() { // Can safely update the GUI from this method.
                try {
                    boolean status = get(); // Retrieve the return value of doInBackground.
                    if(status) {
                        updateLogs();
                    }
                } catch (InterruptedException | ExecutionException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            }
            @Override
            protected void process(List<String> chunks) { // Can safely update the GUI from this method.
                String mostRecentValue = chunks.get(chunks.size() - 1);
                addTextToOutputLogs(mostRecentValue);
            }
        };
        worker.execute();
    }
    
    public String getCurrentTimeStamp() {
        String timestamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        return timestamp;
    }
    public String getCurrentYear() {
        String year = new SimpleDateFormat("yyyy").format(new Date());
        return year;
    }
    public BufferedImage getImageIconToBufferedImg(ImageIcon imgIcon) {
        Image tmpImage = imgIcon.getImage();
        BufferedImage bImg = new BufferedImage(imgIcon.getIconWidth(), imgIcon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
        bImg.getGraphics().drawImage(tmpImage, 0, 0, null);
        tmpImage.flush();

        return bImg;
    }
    public ImageIcon getDataURIToImageIcon(String dataURI, int maxWidth, int maxHeight) throws IOException {
        ImageIcon imgIcon = null;
        byte[] fileBytes = Base64.getDecoder().decode(dataURI);
      
        Image img = ImageIO.read(new ByteArrayInputStream(fileBytes));
        imgIcon = new ImageIcon(img);
        Image scaledImg = img.getScaledInstance((int) maxWidth, (int) maxHeight, Image.SCALE_SMOOTH);
        imgIcon.setImage(scaledImg);

        return imgIcon;
    }
    public static byte[] getBufferImgtoByteArray(BufferedImage img, String imageFileType) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(img, imageFileType, baos);
            return baos.toByteArray();
        } catch (Throwable e) {
            throw new RuntimeException();
        }
    }
    public String getFileToDataURI(File imageFile) throws FileNotFoundException, IOException {
        String base64File = "";
        FileInputStream imageInFile = new FileInputStream(imageFile);
        byte fileData[] = new byte[(int) imageFile.length()];
        imageInFile.read(fileData);
        base64File = Base64.getEncoder().encodeToString(fileData);
        imageInFile.close();
        
        return base64File;
    }
    public String getBufferImgToDataURI(BufferedImage bImg, String type) throws IOException {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
    
        ImageIO.write(bImg, type, bos);
        byte[] imageBytes = bos.toByteArray();
        imageString = Base64.getEncoder().encodeToString(imageBytes);
        bos.close();
        return imageString;
    }
    public BufferedImage getRotatedBufferImg(BufferedImage bImg, Double degrees) {
        // Calculate the new size of the bImg based on the angle of rotaion
        double radians = Math.toRadians(degrees);
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));
        int newW =(int)Math.round(bImg.getWidth() * cos + bImg.getHeight() * sin);
        int newH=(int)Math.round(bImg.getWidth() * sin + bImg.getHeight() * cos);

        BufferedImage rotatedImg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotatedImg.createGraphics();
        
        int x=(newW - bImg.getWidth()) / 2;
        int y=(newH - bImg.getHeight()) / 2;
        
        AffineTransform at = new AffineTransform(); // Transform the origin point around the anchor point
        at.setToRotation( radians, (x + (bImg.getWidth()/2)), (y + (bImg.getHeight()/2)) );
        at.translate(x, y);

        g2d.setTransform(at);
        g2d.drawImage(bImg, 0, 0, null);
        g2d.dispose();
        
        return rotatedImg;
    }
    public void displayOCRAllCompletionDialog(JFrame appFrame) {
        String message = 
        "🔋 𝗘𝘅𝘁𝗿𝗮𝗰𝘁𝗶𝗼𝗻 𝗦𝘁𝗮𝘁𝘂𝘀: 🗹 𝖲𝖴𝖢𝖢𝖤𝖲𝖲\n" 
        + "𝖳𝖾𝗑𝗍 𝖽𝖺𝗍𝖺 𝖾𝗑𝗍𝗋𝖺𝖼𝗍𝗂𝗈𝗇 𝗁𝖺𝗌 𝖻𝖾𝖾𝗇 𝖼𝗈𝗆𝗉𝗅𝖾𝗍𝖾𝖽 𝖿𝗈𝗋 𝖺𝗅𝗅 𝗎𝗉𝗅𝗈𝖺𝖽𝗌.\n"
        + "𝖯𝗅𝖾𝖺𝗌𝖾 𝗋𝖾𝖿𝖾𝗋 𝗍𝗈 𝗍𝖾𝗑𝗍𝖺𝗋𝖾𝖺 𝗈𝗇 𝗍𝗁𝖾 𝗋𝗂𝗀𝗁𝗍 𝗍𝗈 𝖺𝖼𝖼𝖾𝗌𝗌 𝖮𝖢𝖱 𝗋𝖾𝗌𝗎𝗅𝗍𝗌."
        + "\n"
        + "\n";
        JOptionPane.showMessageDialog(appFrame, message, "🔍 𝐎𝐂𝐑 𝐏𝐫𝐨𝐜𝐞𝐬𝐬𝐢𝐧𝐠", JOptionPane.PLAIN_MESSAGE);
    }
    
    public void displayOCRCompletionDialog(JFrame appFrame) {
        String message = 
        "🔋 𝗘𝘅𝘁𝗿𝗮𝗰𝘁𝗶𝗼𝗻 𝗦𝘁𝗮𝘁𝘂𝘀: 🗹 𝖲𝖴𝖢𝖢𝖤𝖲𝖲\n"
        + "𝖳𝖾𝗑𝗍 𝖽𝖺𝗍𝖺 𝖾𝗑𝗍𝗋𝖺𝖼𝗍𝗂𝗈𝗇 𝗁𝖺𝗌 𝖻𝖾𝖾𝗇 𝖼𝗈𝗆𝗉𝗅𝖾𝗍𝖾𝖽 𝖿𝗈𝗋 𝖼𝗎𝗋𝗋𝖾𝗇𝗍 𝗌𝖾𝗅𝖾𝖼𝗍𝗂𝗈𝗇.\n"
        + "𝖯𝗅𝖾𝖺𝗌𝖾 𝗋𝖾𝖿𝖾𝗋 𝗍𝗈 𝗍𝖾𝗑𝗍𝖺𝗋𝖾𝖺 𝗈𝗇 𝗍𝗁𝖾 𝗋𝗂𝗀𝗁𝗍 𝗍𝗈 𝖺𝖼𝖼𝖾𝗌𝗌 𝖮𝖢𝖱 𝗋𝖾𝗌𝗎𝗅𝗍𝗌."
        + "\n"
        + "\n";
        JOptionPane.showMessageDialog(appFrame, message, "🔍 𝐎𝐂𝐑 𝐏𝐫𝐨𝐜𝐞𝐬𝐬𝐢𝐧𝐠", JOptionPane.PLAIN_MESSAGE);
    }
    
    public void displayUploadCompletionDialog(JFrame appFrame) {
        String message = 
        "📤 𝗨𝗽𝗹𝗼𝗮𝗱 𝗦𝘁𝗮𝘁𝘂𝘀: 𝖢𝖮𝖬𝖯𝖫𝖤𝖳𝖤!\n"
        + "𝖠𝗅𝗅 𝗂𝗇𝗉𝗎𝗍𝗌 𝗁𝖺𝗏𝖾 𝖻𝖾𝖾𝗇 𝗎𝗉𝗅𝗈𝖺𝖽𝖾𝖽 𝗌𝗎𝖼𝖼𝖾𝗌𝗌𝖿𝗎𝗅𝗅𝗒.\n"
        + "𝖮𝖢𝖱 𝖾𝗑𝗍𝗋𝖺𝖼𝗍𝗂𝗈𝗇 𝗉𝗋𝗈𝖼𝖾𝗌𝗌 𝗂𝗌 𝗇𝗈𝗐 𝗋𝖾𝖺𝖽𝗒 𝗍𝗈 𝗋𝗎𝗇."
        + "\n"
        + "\n";
        JOptionPane.showMessageDialog(appFrame, message, "🔍 𝐎𝐂𝐑 𝐏𝐫𝐨𝐜𝐞𝐬𝐬𝐢𝐧𝐠", JOptionPane.PLAIN_MESSAGE);
    }
    
       
//    public BufferedImage getGrayscaledBufferImg(BufferedImage ipimage) {
//        Color color[]; // Declaring an array to hold color spectrum
//        BufferedImage output = new BufferedImage(ipimage.getWidth(), ipimage.getHeight(), BufferedImage.TYPE_INT_RGB);
//        // Setting attributes to image
//        int i = 0;
//        int max = 400, rad = 10;
//        int a1 = 0, r1 = 0, g1 = 0, b1 = 0;
//        color = new Color[max];
// 
//        // core section responsible for blurring of image
//        int x = 1, y = 1, x1, y1, ex = 5, d = 0;
// 
//        // Running loop for each pixel and blurring it
//        // Nested for loops
//        for (x = rad; x < ipimage.getHeight() - rad; x++) {
//            for (y = rad; y < ipimage.getWidth() - rad; y++) {
//                for (x1 = x - rad; x1 < x + rad; x1++) {
//                    for (y1 = y - rad; y1 < y + rad; y1++) {
//                        color[i++] = new Color(ipimage.getRGB(y1, x1));
//                    }
//                }
//                // Smoothing colors of image to
//                // get grayscaled corresponding image
//                i = 0;
//                for (d = 0; d < max; d++) {
//                    a1 = a1 + color[d].getAlpha();
//                }
//                
//                a1 = a1 / (max);
//                for (d = 0; d < max; d++) {
//                    r1 = r1 + color[d].getRed();
//                }
// 
//                r1 = r1 / (max);
//                for (d = 0; d < max; d++) {
//                    g1 = g1 + color[d].getGreen();
//                }
// 
//                g1 = g1 / (max);
//                for (d = 0; d < max; d++) {
//                    b1 = b1 + color[d].getBlue();
//                }
// 
//                b1 = b1 / (max);
//                int sum1 = (a1 << 24) + (r1 << 16) + (g1 << 8) + b1;
//                output.setRGB(y, x, (int)(sum1));
//            }
//        }
// 
//        // Finally writing the blurred image on the disc
//        // specifying type and location
//        // to be written on machine
//        return output;
//    }
//    
//    public BufferedImage getProcessedBufferImg(BufferedImage ipimage) { // ipimage is an image buffer of input image
//        float scaleFactor=3f;
//        float offset=-10f;
//        double d = ipimage.getRGB(ipimage.getTileWidth() / 2, ipimage.getTileHeight() / 2); // getting RGB content of the whole image file
//        if (d >= -1.4211511E7 && d < -7254228) {
//            scaleFactor=3f;
//            offset=-10f;
//        } else if (d >= -7254228 && d < -2171170) {
//            scaleFactor=1.455f;
//            offset=-47f;
//        } else if (d >= -2171170 && d < -1907998) {
//            scaleFactor=1.35f;
//            offset=-10f;
//        } else if (d >= -1907998 && d < -257) {
//            scaleFactor=1.19f;
//            offset=0.5f;
//        } else if (d >= -257 && d < -1) {
//            scaleFactor=1f;
//            offset=0.5f;
//        } else if (d >= -1 && d < 2) {
//            scaleFactor=1f;
//            offset=0.35f;
//        }
//        RescaleOp rescale = new RescaleOp(scaleFactor, offset, null); // rescale OP object for gray scaling images
//        BufferedImage opimage = new BufferedImage(1050, 1024, ipimage.getType()); // Making an empty image buffer to store image later 
//        Graphics2D graphic = opimage.createGraphics(); // creating a 2D platform on the buffer image for drawing the new image
//        
//        graphic.drawImage(ipimage, 0, 0, 1050, 1024, null); // drawing new image starting from 0 0 of size 1050 x 1024 (zoomed images) 
//        graphic.dispose();
//        BufferedImage fopimage = rescale.filter(opimage, null); // performing scaling
//        return fopimage;
//    }
//    
//    // Good at removing salt and pepper noise 
//    // also cause little blurring of edges
//    // often used in computer vision applications.
//    public BufferedImage medianFilter(BufferedImage image) {
//        int width = image.getWidth();
//        int height = image.getHeight();
//        int minx = image.getMinX();
//        int miny = image.getMinY();
//        BufferedImage outImage = new BufferedImage(width, height, image.getType());
//
//        for (int x = minx; x < width; x++) {
//            int[] order = new int[9];
//            for (int y = miny; y < height; y++) {
//                Color color = new Color(image.getRGB(x, y));
//                if (x - 1 >= width && y - 1 >= height) {
//                    order[0] = new Color(image.getRGB(x - 1, y - 1)).getRed();
//                }
//                if (x - 1 >= width && y - 1 >= height) {
//                    order[1] = new Color(image.getRGB(x - 1, y)).getRed();
//                }
//                if (x - 1 >= width && y + 1 <= height) {
//                    order[2] = new Color(image.getRGB(x - 1, y + 1)).getRed();
//                }
//                if (x + 1 <= width - 1) {
//                    order[3] = new Color(image.getRGB(x + 1, y)).getRed();
//                }
//                if (x + 1 <= width - 1 && y + 1 <= height - 1) {
//                    order[4] = new Color(image.getRGB(x + 1, y + 1)).getRed();
//                }
//                if (y + 1 <= height - 1) {
//                    order[5] = new Color(image.getRGB(x, y + 1)).getRed();
//                }
//                if (x - 1 >= width && y + 1 <= height - 1) {
//                    order[6] = new Color(image.getRGB(x - 1, y + 1)).getRed();
//                }
//                if (x - 1 >= width) {
//                    order[7] = new Color(image.getRGB(x - 1, y)).getRed();
//                }
//                order[8] = new Color(image.getRGB(x, y)).getRed();
//                Arrays.sort(order);
//                int rgb = order[order.length / 2];
//                color = new Color(rgb, rgb, rgb, color.getAlpha());			
//                outImage.setRGB(x, y, color.getRGB());
//            }
//        }
//        return outImage;
//    }
//    
}
