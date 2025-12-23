package src.gui;

import src.backend.auth.Login;

import javax.swing.*;
import java.awt.*;

public class LoginWindow {
    public LoginWindow() {
        JFrame frame = new JFrame();
        GuiUtils.setupWindow(frame, "Login");

        JPanel main = new JPanel(new GridBagLayout());
        main.setBackground(new Color(240, 244, 248));
        frame.add(main, BorderLayout.CENTER);
        frame.add(GuiUtils.createCreditsPanel(), BorderLayout.SOUTH);

        JPanel card = GuiUtils.createCardPanel(new Dimension(400, 450));

        JLabel title = GuiUtils.createTitleLabel("Login");

        JTextField emailField = GuiUtils.createLabeledField("Email");

        JPasswordField passField = GuiUtils.createLabeledPasswordField("Password");

        JLabel forgotLink = GuiUtils.createLinkLabel("new user? register here.");
        forgotLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                frame.dispose();
                new RegisterWindow();
            }
        });

        card.add(title);
        card.add(Box.createRigidArea(new Dimension(0, 30)));

        card.add(emailField);
        card.add(Box.createRigidArea(new Dimension(0, 20)));

        card.add(passField);
        card.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton loginBtn = GuiUtils.createPrimaryButton("CONTINUE");
        loginBtn.addActionListener(e -> {
            String email = emailField.getText();
            String pass = new String(passField.getPassword());
            String role = Login.authenticate(email, pass);
            if (role == null) {
                JOptionPane.showMessageDialog(frame, "Invalid credentials", "Login failed", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String displayName = Login.getDisplayName(email);
            frame.dispose();
            if ("Club President".equalsIgnoreCase(role)) {
                new ClubPresidentWindow(displayName, email);
            } else {
                new NormalUserWindow(displayName, email);
            }
        });

        card.add(loginBtn);
        card.add(Box.createRigidArea(new Dimension(0, 12)));

        card.add(forgotLink);
        card.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton backBtn = GuiUtils.createBackButton(e -> {
            frame.dispose();
            new HomeWindow();
        });
        card.add(backBtn);

        main.add(card);

        frame.setVisible(true);
    }
}