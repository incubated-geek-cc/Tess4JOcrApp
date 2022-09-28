package app.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.apache.commons.io.FileUtils;

public class Helpers {
    public String getCurrentTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmaa");
        Date date = new Date();
        String timestamp = sdf.format(date);

        return timestamp;
    }

    public String getCurrentYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String year = sdf.format(date);
        return year;
    }
    
    public BufferedImage getBufferedImage(ImageIcon imgIcon) {
        Image tmpImage = imgIcon.getImage();
        BufferedImage bImg = new BufferedImage(imgIcon.getIconWidth(), imgIcon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
        bImg.getGraphics().drawImage(tmpImage, 0, 0, null);
        tmpImage.flush();

        return bImg;
    }

    public ImageIcon getImageIcon(String imageIconURI, int maxWidth, int maxHeight) {
        ImageIcon imgIcon = null;
        byte[] fileBytes = Base64.getDecoder().decode(imageIconURI);
        try {
            Image img = ImageIO.read(new ByteArrayInputStream(fileBytes));
            imgIcon = new ImageIcon(img);
            Image scaledImg = img.getScaledInstance((int) maxWidth, (int) maxHeight, Image.SCALE_SMOOTH);
            imgIcon.setImage(scaledImg);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return imgIcon;
    }

    public String getImageFileURI(File imageFile) throws IOException {
        String base64File = "";
        try (FileInputStream imageInFile = new FileInputStream(imageFile)) {
            byte fileData[] = new byte[(int) imageFile.length()];
            imageInFile.read(fileData);
            base64File = Base64.getEncoder().encodeToString(fileData);
        } catch (FileNotFoundException e) {
            System.out.println("File not found " + e);
        }
        return base64File;
    }
    
    public BufferedImage getProcessedBufferImg(BufferedImage ipimage) { // ipimage is an image buffer of input image
        float scaleFactor=3f;
        float offset=-10f;
        // getting RGB content of the whole image file
        double d = ipimage.getRGB(ipimage.getTileWidth() / 2, ipimage.getTileHeight() / 2);
        if (d >= -1.4211511E7 && d < -7254228) {
            scaleFactor=3f;
            offset=-10f;
        } else if (d >= -7254228 && d < -2171170) {
            scaleFactor=1.455f;
            offset=-47f;
        } else if (d >= -2171170 && d < -1907998) {
            scaleFactor=1.35f;
            offset=-10f;
        } else if (d >= -1907998 && d < -257) {
            scaleFactor=1.19f;
            offset=0.5f;
        } else if (d >= -257 && d < -1) {
            scaleFactor=1f;
            offset=0.5f;
        } else if (d >= -1 && d < 2) {
            scaleFactor=1f;
            offset=0.35f;
        }
        // rescale OP object for gray scaling images
        RescaleOp rescale = new RescaleOp(scaleFactor, offset, null);
        // Making an empty image buffer to store image later 
        BufferedImage opimage = new BufferedImage(1050, 1024, ipimage.getType());
        // creating a 2D platform on the buffer image for drawing the new image
        Graphics2D graphic = opimage.createGraphics();
        // drawing new image starting from 0 0 of size 1050 x 1024 (zoomed images) null is the ImageObserver class object
        graphic.drawImage(ipimage, 0, 0, 1050, 1024, null);
        graphic.dispose();
        // performing scaling
        BufferedImage fopimage = rescale.filter(opimage, null);
        
        return fopimage;
    }
}