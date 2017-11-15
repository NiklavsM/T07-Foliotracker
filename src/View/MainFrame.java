package View;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        JPanel panel = new JPanel();
        JButton button = new JButton("Add");
        JLabel label1 = new JLabel("Ticketer Symbol:");
        JLabel label2 = new JLabel("Number of Shares:");
        TextField tf1 = new TextField("", 20);
        TextField tf2 = new TextField("", 20);
        this.add(panel);
        panel.add(button);
        panel.add(label1);
        panel.add(tf1);
        panel.add(label2);
        panel.add(tf2);
        this.add(panel);
        this.setSize(800, 300);
        this.setVisible(true);
    }


}
