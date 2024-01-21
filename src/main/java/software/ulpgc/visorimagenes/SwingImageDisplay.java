package software.ulpgc.visorimagenes;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SwingImageDisplay extends JPanel implements ImageDisplay {
    private Image image;
    private BufferedImage bufferedImage;
    private double scaleFactor = 1.0;
    private double rotationAngle = 0.0;

    public SwingImageDisplay() {
        this.setPreferredSize(new Dimension(800, 600)); // Ajusta esto según tus necesidades
    }

    @Override
    public void show(Image image) {
        this.image = image;
        if (image != null) {
            this.bufferedImage = loadImage(image.name());
        } else {
            this.bufferedImage = null;
        }
        this.repaint();

        // Actualizar el tamaño de la ventana principal
        JFrame topLevelFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (topLevelFrame != null) {
            topLevelFrame.pack();
        }
    }

    @Override
    public Image image() {
        return image;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (bufferedImage != null) {
            int originalWidth = bufferedImage.getWidth();
            int originalHeight = bufferedImage.getHeight();

            int newWidth = (int) (originalWidth * scaleFactor);
            int newHeight = (int) (originalHeight * scaleFactor);

            int x = (this.getWidth() - newWidth) / 2;
            int y = (this.getHeight() - newHeight) / 2;

            Graphics2D g2d = (Graphics2D) g.create();
            g2d.rotate(Math.toRadians(rotationAngle), x + newWidth / 2, y + newHeight / 2);
            g2d.drawImage(bufferedImage.getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_DEFAULT), x, y, this);
            g2d.dispose();
        }
    }

    private BufferedImage loadImage(String name) {
        try {
            BufferedImage originalImage = ImageIO.read(new File(name));
            int targetWidth = 800;
            int targetHeight = 600;

            // Escalar la imagen para ajustarse al tamaño deseado
            java.awt.Image scaledImage = originalImage.getScaledInstance(targetWidth, targetHeight, java.awt.Image.SCALE_SMOOTH);

            // Crear una nueva imagen BufferedImage con el formato correcto
            BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage( scaledImage, 0, 0, null);
            g2d.dispose();

            return resizedImage;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void rotateImage() {
        rotationAngle += 90.0;
        if (rotationAngle >= 360.0) {
            rotationAngle = 0.0;
        }
        repaint();
    }
}