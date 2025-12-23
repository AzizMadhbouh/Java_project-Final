package src.gui;

import src.backend.auth.Register;

import javax.swing.*;
import java.awt.*;

public class RegisterWindow {
    public RegisterWindow() {
        JFrame frame = new JFrame();
        GuiUtils.setupWindow(frame, "Register");

        JPanel main = new JPanel(new GridBagLayout());
        main.setBackground(new Color(240, 244, 248));
        frame.add(main, BorderLayout.CENTER);
        frame.add(GuiUtils.createCreditsPanel(), BorderLayout.SOUTH);

        JPanel card = GuiUtils.createCardPanel(new Dimension(400, 500));

        JLabel title = GuiUtils.createTitleLabel("Create Account");

        JTextField lastNameField = GuiUtils.createLabeledField("Last Name");
        JTextField firstNameField = GuiUtils.createLabeledField("First Name");
        JTextField emailField = GuiUtils.createLabeledField("Email");

        JButton registerBtn = GuiUtils.createPrimaryButton("REGISTER");
        registerBtn.addActionListener(e -> {
            Register r = new Register();
            r.lastName = lastNameField.getText();
            r.firstName = firstNameField.getText();
            r.email = emailField.getText();
            r.role = "Normal User";
            boolean success = r.save();
            if (success) {
                String msg = "Registered successfully.\n" +
                        "Username: " + r.email + "\n" +
                        "Password: " + r.generatedPassword;
                JTextArea textArea = new JTextArea(msg);
                textArea.setEditable(false);
                JOptionPane.showMessageDialog(frame, textArea, "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
                new LoginWindow();
            } else {
                JOptionPane.showMessageDialog(frame, "Please enter valid information or existing email", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        JLabel loginLink = GuiUtils.createLinkLabel("already have an account? login here.");
        loginLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                frame.dispose();
                new LoginWindow();
            }
        });

        card.add(title);
        card.add(Box.createRigidArea(new Dimension(0, 30)));

        card.add(lastNameField);
        card.add(Box.createRigidArea(new Dimension(0, 15)));

        card.add(firstNameField);
        card.add(Box.createRigidArea(new Dimension(0, 15)));

        card.add(emailField);
        card.add(Box.createRigidArea(new Dimension(0, 25)));

        card.add(registerBtn);
        card.add(Box.createRigidArea(new Dimension(0, 20)));

        card.add(loginLink);
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
