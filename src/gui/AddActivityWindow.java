package src.gui;

import src.backend.activity.AddActivity;

import javax.swing.*;
import java.awt.*;

public class AddActivityWindow {
    public AddActivityWindow(Runnable onBack, String presidentEmail) {
        JFrame frame = new JFrame();
        GuiUtils.setupWindow(frame, "Add Activity");

        JPanel main = new JPanel(new GridBagLayout());
        main.setBackground(new Color(240, 244, 248));
        frame.add(main);

        String myClub = src.backend.club.ClubManager.getPresidentClub(presidentEmail);

        JPanel card = GuiUtils.createCardPanel(new Dimension(420, 440));

        JLabel title = GuiUtils.createTitleLabel("Add Activity");

        JTextField activityField = GuiUtils.createLabeledField("Activity");
        JTextField typeField = GuiUtils.createLabeledField("Type");
        DateSelector dateSelector = new DateSelector();

        JButton addBtn = GuiUtils.createPrimaryButton("ADD ACTIVITY");
        addBtn.addActionListener(e -> {
            if (myClub == null) {
                JOptionPane.showMessageDialog(frame, "You do not manage a club.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            AddActivity a = new AddActivity();
            a.clubName = myClub;
            a.activity = activityField.getText();
            a.type = typeField.getText();
            a.date = dateSelector.getSelectedDate();
            try {
                boolean saved = a.save();
                if (saved) {
                    JOptionPane.showMessageDialog(frame, "Activity added to " + myClub, "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                    if (onBack != null)
                        onBack.run();
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to save activity. Check terminal for details.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton backBtn = GuiUtils.createBackButton(e -> {
            frame.dispose();
            if (onBack != null)
                onBack.run();
        });

        card.add(title);
        card.add(Box.createRigidArea(new Dimension(0, 30)));

        card.add(activityField);
        card.add(Box.createRigidArea(new Dimension(0, 15)));

        card.add(typeField);
        card.add(Box.createRigidArea(new Dimension(0, 15)));

        card.add(dateSelector);
        card.add(Box.createRigidArea(new Dimension(0, 25)));

        card.add(addBtn);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(backBtn);

        main.add(card);
        frame.add(main, BorderLayout.CENTER);
        frame.add(GuiUtils.createCreditsPanel(), BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
