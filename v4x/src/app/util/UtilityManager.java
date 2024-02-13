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
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
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
        LOG_TEXT_AREA.setForeground(Color.WHITE);
        LOG_TEXT_AREA.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        LOG_TEXT_AREA.setEditable(false);
        LOG_TEXT_AREA.setFocusable(false);
        LOG_TEXT_AREA.setWrapStyleWord(true);
        LOG_TEXT_AREA.setLineWrap(true);
        
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
                logString = consoleCaption + System.lineSeparator();
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
    public void writeStringToFile(String filepath, String content) throws IOException {
        File outputFile = new File(filepath);
        try (FileWriter fileWriter = new FileWriter(outputFile)) {
            fileWriter.write(content);
        }
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
    public void convertInputStreamToFileOutputStream(byte[] buffer, File outputFile) throws FileNotFoundException, IOException {
        InputStream inStream = new ByteArrayInputStream(buffer);
        FileOutputStream fos = new FileOutputStream(outputFile);
        int read;
        byte[] bytes = new byte[1024];
        while ((read = inStream.read(bytes)) != -1) {
            fos.write(bytes, 0, read);
        }
        fos.close();
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
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
    public byte[] getFileToBytes(File f) throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(f);
        byte buffer[] = new byte[1024];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        for (int len; (len = fis.read(buffer)) != -1;) {
            bos.write(buffer, 0, len);
        }
        return bos.toByteArray();
    }
    public String getFileToDataURI(File imageFile) throws FileNotFoundException, IOException {
        String base64File = "";
        try (FileInputStream imageInFile = new FileInputStream(imageFile)) {
            byte fileData[] = new byte[(int) imageFile.length()];
            imageInFile.read(fileData);
            base64File = Base64.getEncoder().encodeToString(fileData);
        }
        
        return base64File;
    }
    public String getBufferImgToDataURI(BufferedImage bImg, String type) throws IOException {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImg, type, bos);
        /*
        im - a RenderedImage to be written. 
        formatName - a String containing the informal name of the format. 
        output - a File to be written to.
        */
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
    
    
//    public void unzip(String zipFilePath, String destDirectory) {
//        ZipInputStream zipIn = null;
//        try {
//            File destDir = new File(destDirectory);
//            if (!destDir.exists()) {
//                destDir.mkdir();
//            }   zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
//            ZipEntry entry = zipIn.getNextEntry();
//            // iterates over entries in the zip file
//            while (entry != null) {
//                String filePath = destDirectory + File.separator + entry.getName();
//                if (!entry.isDirectory()) {
//                    // if the entry is a file, extracts it
//                    extractFile(zipIn, filePath, (int) entry.getSize());
//                } else {
//                    // if the entry is a directory, make the directory
//                    File dir = new File(filePath);
//                    dir.mkdirs();
//                }
//                zipIn.closeEntry();
//                entry = zipIn.getNextEntry();
//            }   zipIn.close();
//        } catch (FileNotFoundException ex) {
//            ex.printStackTrace();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        } finally {
//            try {
//                if(zipIn != null) {
//                    zipIn.close();
//                }
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
//    /**
//     * Extracts a zip entry (file entry)
//     * @param zipIn
//     * @param filePath
//     * @throws IOException
//     */
//    private void extractFile(ZipInputStream zipIn, String filePath, int bufferSize) throws IOException {
//        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
//        byte[] bytesIn = new byte[bufferSize];
//        int read = 0;
//        while ((read = zipIn.read(bytesIn)) != -1) {
//            bos.write(bytesIn, 0, read);
//        }
//        bos.close();
//    }
       
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
