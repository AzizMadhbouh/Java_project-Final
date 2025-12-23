package src.gui;

import src.backend.activity.Calendar;
import src.backend.activity.Calendar.ActivityRecord;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CalendarWindow {
    public CalendarWindow(Runnable onBack, String userEmail) {
        JFrame frame = new JFrame();
        GuiUtils.setupWindow(frame, "Calendar");

        JPanel root = GuiUtils.createStandardRootPanel();

        JLabel title = GuiUtils.createTitleLabel("Calendar");
        title.setBorder(BorderFactory.createEmptyBorder(24, 32, 12, 32));
        root.add(title, BorderLayout.NORTH);

        JPanel listContainer = new JPanel();
        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.Y_AXIS));
        listContainer.setOpaque(false);
        listContainer.setBorder(BorderFactory.createEmptyBorder(0, 32, 32, 32));

        List<ActivityRecord> activities = Calendar.getActivities(userEmail);
        if (activities.isEmpty()) {
            JLabel empty = new JLabel("No events found from your joined clubs.");
            empty.setFont(new Font("SansSerif", Font.ITALIC, 16));
            empty.setAlignmentX(Component.CENTER_ALIGNMENT);
            listContainer.add(Box.createVerticalGlue());
            listContainer.add(empty);
            listContainer.add(Box.createVerticalGlue());
        } else {
            for (ActivityRecord a : activities) {
                JPanel card = GuiUtils.createCardPanel(new Dimension(800, 100));
                card.setLayout(new BorderLayout());
                card.setMaximumSize(new Dimension(1000, 110));

                JPanel infoPanel = new JPanel();
                infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
                infoPanel.setOpaque(false);

                JLabel nameLabel = new JLabel("Event: " + a.name);
                nameLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
                nameLabel.setForeground(new Color(17, 24, 39));

                JLabel clubLabel = new JLabel("Club: " + a.clubName);
                clubLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
                clubLabel.setForeground(Color.GRAY);

                infoPanel.add(nameLabel);
                infoPanel.add(Box.createRigidArea(new Dimension(0, 4)));
                infoPanel.add(clubLabel);

                JPanel sidePanel = new JPanel();
                sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
                sidePanel.setOpaque(false);
                sidePanel.setAlignmentX(Component.RIGHT_ALIGNMENT);

                Color typeColor = a.type.toLowerCase().contains("sport") ? new Color(16, 185, 129)
                        : a.type.toLowerCase().contains("culture") ? new Color(245, 158, 11) : new Color(59, 130, 246);

                JLabel badge = GuiUtils.createBadge("TYPE: " + a.type.toUpperCase(), typeColor);
                badge.setAlignmentX(Component.RIGHT_ALIGNMENT);

                JLabel dateLabel = new JLabel("Date: " + a.date);
                dateLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
                dateLabel.setForeground(new Color(107, 114, 128));
                dateLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

                sidePanel.add(badge);
                sidePanel.add(Box.createRigidArea(new Dimension(0, 8)));
                sidePanel.add(dateLabel);

                card.add(infoPanel, BorderLayout.WEST);
                card.add(sidePanel, BorderLayout.EAST);

                listContainer.add(card);
                listContainer.add(Box.createRigidArea(new Dimension(0, 16)));
            }
        }

        JScrollPane scroll = new JScrollPane(listContainer);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        root.add(scroll, BorderLayout.CENTER);

        JButton backBtn = GuiUtils.createBackButton(e -> {
            frame.dispose();
            if (onBack != null)
                onBack.run();
        });
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
