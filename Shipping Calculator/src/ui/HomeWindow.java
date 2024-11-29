package ui;

import javax.swing.*;

public class HomeWindow extends JFrame {
    private JPanel contentPane;
    private JButton option1Button;
    private JButton option2Button;
    private JButton option3Button;
    private JLabel centreGraphic;

    public HomeWindow() {
        setTitle("Super Shipper Calculator - Eerily Release Edition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(contentPane);

        //Ugly logo path
        String uglyLogoPath = "./images/ui/ugly_logo.png";
        ImageIcon icon = new ImageIcon(uglyLogoPath);

        centreGraphic.setIcon(icon);
        pack();

        // Set the frame visible
        setVisible(true);
    }
}
