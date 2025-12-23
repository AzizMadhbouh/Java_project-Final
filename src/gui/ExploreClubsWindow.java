package src.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ExploreClubsWindow {
    private String userEmail;

    public ExploreClubsWindow(String email) {
        this.userEmail = email;
        JFrame frame = new JFrame();
        GuiUtils.setupWindow(frame, "Explore Clubs");

        JPanel root = GuiUtils.createStandardRootPanel();

        JLabel title = GuiUtils.createTitleLabel("Explore Clubs");
        title.setBorder(BorderFactory.createEmptyBorder(24, 32, 12, 32));
        root.add(title, BorderLayout.NORTH);

        JPanel list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        list.setOpaque(false);

        List<String> allClubs = src.backend.club.ClubManager.getAllClubs();
        List<String> joinedClubs = src.backend.club.ClubManager.getJoinedClubs(email);

        if (allClubs.isEmpty()) {
            JLabel noClubs = new JLabel("No clubs available to join.");
            noClubs.setBorder(BorderFactory.createEmptyBorder(20, 32, 0, 0));
            list.add(noClubs);
        } else {
            for (String clubName : allClubs) {

                if (joinedClubs.contains(clubName)) {
                    continue;
                }

                JPanel card = GuiUtils.createCardPanel(new Dimension(720, 100));
                JLabel name = new JLabel(clubName);
                name.setFont(new Font("SansSerif", Font.BOLD, 16));
                name.setAlignmentX(Component.LEFT_ALIGNMENT);

                JButton join = GuiUtils.createPrimaryButton("Join");
                join.setAlignmentX(Component.LEFT_ALIGNMENT);
                join.addActionListener(a -> {
                    boolean success = src.backend.club.ClubManager.joinClub(email, clubName);
                    if (success) {
                        JOptionPane.showMessageDialog(frame, "Successfully joined " + clubName);
                        frame.dispose();
                        new ExploreClubsWindow(email);

                    } else {
                        JOptionPane.showMessageDialog(frame, "Failed to join " + clubName + " (Limit reached or error)",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });

                card.add(name);
                card.add(Box.createRigidArea(new Dimension(0, 8)));
                card.add(join);

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
        southContainer.add(bottomPanel, BorderLayout.CENTER); // or NORTH
        southContainer.add(GuiUtils.createCreditsPanel(), BorderLayout.SOUTH);

        root.add(southContainer, BorderLayout.SOUTH);

        frame.add(root);
        frame.setVisible(true);
    }

}
