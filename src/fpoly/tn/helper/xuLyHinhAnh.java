package fpoly.tn.helper;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class xuLyHinhAnh {
    
    public static Icon LayAnhTuSource(JComponent comp,String url){
      //  JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(comp);
        return new javax.swing.ImageIcon(comp.getClass().getResource(url));
    }

    // Hàm plugin
    public static ImageIcon taoAnhVienTron(String Path, int newSize) throws IOException {

        BufferedImage masked;
        BufferedImage orignImage = getImageFromPath(Path);
        BufferedImage master = resizeImage(orignImage, newSize,false);
        int diameter = Math.min(master.getWidth(), master.getHeight());
        BufferedImage mask = new BufferedImage(master.getWidth(), master.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = mask.createGraphics();
        applyQualityRenderingHints(g2d);
        g2d.fillOval(0, 0, diameter - 1, diameter - 1);
        g2d.dispose();

        masked = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        g2d = masked.createGraphics();
        applyQualityRenderingHints(g2d);
        int x = (diameter - master.getWidth()) / 2;
        int y = (diameter - master.getHeight()) / 2;
        g2d.drawImage(master, x, y, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
        g2d.drawImage(mask, 0, 0, null);
        g2d.dispose();
        return new ImageIcon(masked);
    }

    public static Icon doiDoSangIcon(Icon icon, float brightnessPercentage) {

        Image source = iconToImage(icon);

        BufferedImage bi = new BufferedImage(
                source.getHeight(null),
                source.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        int[] pixel = {0, 0, 0, 0};
        float[] hsbvals = {0, 0, 0};

        bi.getGraphics().drawImage(source, 0, 0, null);

        // recalculare every pixel, changing the brightness
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {

                // get the pixel data
                bi.getRaster().getPixel(j, i, pixel);

                // converts its data to hsb to change brightness
                Color.RGBtoHSB(pixel[0], pixel[1], pixel[2], hsbvals);

                // calculates the brightness component.
                float newBrightness = hsbvals[2] * brightnessPercentage;
                if (newBrightness > 1f) {
                    newBrightness = 1f;
                }

                // create a new color with the new brightness
                Color c = new Color(Color.HSBtoRGB(hsbvals[0], hsbvals[1], newBrightness));

                // set the new pixel
                bi.getRaster().setPixel(j, i, new int[]{c.getRed(), c.getGreen(), c.getBlue(), pixel[3]});

            }

        }

        return new ImageIcon(bi);
    }

    // Hàm private
    private static void applyQualityRenderingHints(Graphics2D g2d) {

        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    }

    private static BufferedImage getImageFromPath(String path) throws IOException {
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(path));
        } catch (IOException ex) {
            URL url = new URL(path);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.connect();
            InputStream urlStream = conn.getInputStream();
            image = ImageIO.read(urlStream);
        }

        return image;
    }
    
    // thay đổi kích thước ảnh
    public static BufferedImage resizeImage(BufferedImage img, int boundSize , boolean giuTiLe) {

        Dimension newSize = new Dimension(boundSize,boundSize);
        
        if(giuTiLe)
             newSize = layKichThuocMoi(new Dimension(img.getWidth(),img.getHeight()),new Dimension(boundSize,boundSize));
        
        Image tmp = img.getScaledInstance(newSize.width,newSize.height, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newSize.width,newSize.height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
    
    private static Dimension layKichThuocMoi(Dimension imgSize, Dimension newSize) {

    int original_width = imgSize.width;
    int original_height = imgSize.height;
    int bound_width = newSize.width;
    int bound_height = newSize.height;
    int new_width = original_width;
    int new_height = original_height;

    if (original_width > bound_width) {
        new_width = bound_width;
        new_height = (new_width * original_height) / original_width;
    }

    if (new_height > bound_height) {
        new_height = bound_height;
        new_width = (new_height * original_width) / original_height;
    }

    return new Dimension(new_width, new_height);
}

    private static BufferedImage iconToImage(Icon icon) {
        BufferedImage bi = new BufferedImage(
                icon.getIconWidth(),
                icon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();

        // paint the Icon to the BufferedImage.
        icon.paintIcon(null, g, 0, 0);
        g.dispose();

        return bi;
    }

    public static BufferedImage imageToBufferedImage(Image img) {
        
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }
}
