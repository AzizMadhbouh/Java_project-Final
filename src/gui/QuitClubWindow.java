package src.gui;

import src.backend.club.ClubManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class QuitClubWindow {
    private String userEmail;

    public QuitClubWindow(String email) {
        this.userEmail = email;
        JFrame frame = new JFrame();
        GuiUtils.setupWindow(frame, "Quit Club");

        JPanel root = GuiUtils.createStandardRootPanel();

        JLabel title = GuiUtils.createTitleLabel("Your Clubs");
        title.setBorder(BorderFactory.createEmptyBorder(24, 32, 12, 32));
        root.add(title, BorderLayout.NORTH);

        JPanel list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        list.setOpaque(false);

        List<String> joinedClubs = ClubManager.getJoinedClubs(email);

        if (joinedClubs.isEmpty()) {
            JLabel noClubs = new JLabel("You haven't joined any clubs.");
            noClubs.setBorder(BorderFactory.createEmptyBorder(20, 32, 0, 0));
            list.add(noClubs);
        } else {
            for (String clubName : joinedClubs) {
                JPanel card = GuiUtils.createCardPanel(new Dimension(720, 100));
                JLabel name = new JLabel(clubName);
                name.setFont(new Font("SansSerif", Font.BOLD, 16));
                name.setAlignmentX(Component.LEFT_ALIGNMENT);

                JButton quitBtn = GuiUtils.createSecondaryButton("Quit Club");
                quitBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
                quitBtn.setForeground(new Color(185, 28, 28));

                quitBtn.addActionListener(a -> {
                    int confirm = JOptionPane.showConfirmDialog(frame,
                            "Are you sure you want to quit " + clubName + "?", "Confirm", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        boolean success = ClubManager.quitClub(email, clubName);
                        if (success) {
                            JOptionPane.showMessageDialog(frame, "You have left " + clubName);
                            frame.dispose();
                            new QuitClubWindow(email);

                        } else {

                            JOptionPane.showMessageDialog(frame,
                                    "Failed to quit. You cannot quit a club you are the President of.", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                card.add(name);
                card.add(Box.createRigidArea(new Dimension(0, 8)));
                card.add(quitBtn);

                list.add(card);
                list.add(Box.createRigidArea(new Dimension(0, 12)));
            }
        }

        JScrollPane scroll = new JScrollPane(list);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        root.add(scroll, BorderLayout.CENTER);

        JButton backBtn = GuiUtils.createBackButton(e -> frame.dispose());
        JPanel bottomPanel = GuiUtils.createBottomPanel(backBtn);

        JPanel southContainer = new JPanel(new BorderLayout());
        southContainer.setOpaque(false);
        southContainer.add(bottomPanel, BorderLayout.CENTER);
        southContainer.add(GuiUtils.createCreditsPanel(), BorderLayout.SOUTH);

        root.add(southContainer, BorderLayout.SOUTH);

        frame.add(root);
        frame.setVisible(true);
    }
}
