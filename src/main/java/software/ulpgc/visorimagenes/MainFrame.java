package software.ulpgc.visorimagenes;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MainFrame extends JFrame {
    private ImageDisplay imageDisplay;
    private final Map<String, Command> commands;

    public MainFrame() {
        this.commands = new HashMap<>();
        setTitle("Image Viewer");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(createImageDisplay(), BorderLayout.CENTER);  // Ajuste al centro
        add(createToolbar(), BorderLayout.SOUTH);
        pack(); // Ajustar el tamaño de la ventana al contenido
        setLocationRelativeTo(null);
    }

    private Component createToolbar() {
        JPanel panel = new JPanel();
        panel.add(createButton("Anterior"));
        panel.add(createButton("Siguiente"));
        panel.add(createButton("Rotar"));
        return panel;
    }

    private Component createButton(String label) {
        JButton button = new JButton(label);
        button.addActionListener(e -> commands.get(label).execute());

        if ("Rotar".equals(label)) {
            button.addActionListener(e -> rotateImage());
        }

        return button;
    }

    private Component createImageDisplay() {
        SwingImageDisplay display = new SwingImageDisplay();
        this.imageDisplay = display;
        return display;
    }

    public void add(String name, Command command) {
        commands.put(name, command);
    }

    public ImageDisplay imageDisplay() {
        return imageDisplay;
    }

    private void rotateImage() {
        ((SwingImageDisplay) imageDisplay).rotateImage();
    }

}
