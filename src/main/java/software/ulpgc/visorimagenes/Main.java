package software.ulpgc.visorimagenes;

import javax.swing.*;
import java.io.File;

//visor de imagenes listo
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            Image image = new FileImageLoader(new File("D:\\Users\\Carlos\\Desktop\\visor-de-imagenes\\imagenes")).load();
            frame.imageDisplay().show(image);
            frame.add("Anterior", new PreviousImageCommand(frame.imageDisplay()));
            frame.add("Siguiente", new NextImageCommand(frame.imageDisplay()));
            frame.setVisible(true);
        });
    }

}