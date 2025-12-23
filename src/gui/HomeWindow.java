package src.gui;

import javax.swing.*;
import java.awt.*;

public class HomeWindow {

    public HomeWindow() {
        JFrame frame = new JFrame();
        GuiUtils.setupWindow(frame, "Welcome", JFrame.EXIT_ON_CLOSE);

        JPanel main = new JPanel(new GridBagLayout());
        main.setBackground(new Color(240, 244, 248));

        JPanel card = GuiUtils.createCardPanel(new Dimension(400, 380));

        JLabel title = GuiUtils.createTitleLabel("Welcome to ClubApp");
        JLabel subtitle = GuiUtils.createSubtitleLabel("Manage clubs and activities");

        JButton loginBtn = GuiUtils.createPrimaryButton("LOGIN");
        loginBtn.addActionListener(e -> {
            frame.dispose();
            new LoginWindow();
        });

        JButton registerBtn = GuiUtils.createSecondaryButton("REGISTER");
        registerBtn.addActionListener(e -> {
            frame.dispose();
            new RegisterWindow();
        });

        card.add(title);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(subtitle);
        card.add(Box.createRigidArea(new Dimension(0, 40)));
        card.add(loginBtn);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(registerBtn);

        main.add(card);
        frame.add(main, BorderLayout.CENTER);
        frame.add(GuiUtils.createCreditsPanel(), BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
