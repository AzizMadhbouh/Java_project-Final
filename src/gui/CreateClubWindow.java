package src.gui;

import src.backend.club.ClubManager;

import javax.swing.*;
import java.awt.*;

public class CreateClubWindow {
    public CreateClubWindow(String email, Runnable onSuccess) {
        JFrame frame = new JFrame();
        GuiUtils.setupWindow(frame, "Create a Club");

        JPanel main = new JPanel(new GridBagLayout());
        main.setBackground(new Color(240, 244, 248));
        frame.add(main);

        JPanel card = GuiUtils.createCardPanel(new Dimension(450, 450));

        JLabel title = GuiUtils.createTitleLabel("Start Your Club");

        JTextField nameField = GuiUtils.createLabeledField("Club Name");
        JTextField categoryField = GuiUtils.createLabeledField("Category (e.g. Sports, Tech)");
        JTextField maxMembersField = GuiUtils.createLabeledField("Max Members");

        JButton createBtn = GuiUtils.createPrimaryButton("CREATE CLUB");
        createBtn.addActionListener(e -> {
            String clubName = nameField.getText();
            String category = categoryField.getText();
            String maxStr = maxMembersField.getText();

            if (clubName.isEmpty() || category.isEmpty() || maxStr.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int maxMembers;
            try {
                maxMembers = Integer.parseInt(maxStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Max members must be a number", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success = ClubManager.createClub(email, clubName, maxMembers, category);
            if (success) {
                JOptionPane.showMessageDialog(frame, "Club created successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
                if (onSuccess != null) {
                    onSuccess.run();
                }
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Failed to create club. Name taken or limit reached (Max 2 clubs).", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        card.add(title);
        card.add(Box.createRigidArea(new Dimension(0, 30)));

        card.add(nameField);
        card.add(Box.createRigidArea(new Dimension(0, 15)));

        card.add(categoryField);
        card.add(Box.createRigidArea(new Dimension(0, 15)));

        card.add(maxMembersField);
        card.add(Box.createRigidArea(new Dimension(0, 25)));

        card.add(createBtn);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        JButton backBtn = GuiUtils.createBackButton(e -> frame.dispose());
        card.add(backBtn);

        main.add(card);
        frame.add(main, BorderLayout.CENTER);
        frame.add(GuiUtils.createCreditsPanel(), BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
