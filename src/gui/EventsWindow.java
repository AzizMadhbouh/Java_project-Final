package src.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EventsWindow {
    public EventsWindow() {
        JFrame frame = new JFrame("Events");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(240, 244, 248));

        JLabel title = GuiUtils.createTitleLabel("Events");
        title.setBorder(BorderFactory.createEmptyBorder(24, 32, 12, 32));
        root.add(title, BorderLayout.NORTH);

        JPanel listContainer = new JPanel();
        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.Y_AXIS));
        listContainer.setOpaque(false);

        List<EventModel> events = sampleEvents();
        for (EventModel e : events) {
            JPanel card = GuiUtils.createCardPanel(new Dimension(720, 140));
            JLabel t = new JLabel(e.title);
            t.setFont(new Font("SansSerif", Font.BOLD, 16));
            t.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel meta = new JLabel(e.date + "  —  " + e.location);
            meta.setFont(new Font("SansSerif", Font.PLAIN, 12));
            meta.setForeground(Color.GRAY);
            meta.setAlignmentX(Component.LEFT_ALIGNMENT);

            JTextArea desc = new JTextArea(e.description);
            desc.setLineWrap(true);
            desc.setWrapStyleWord(true);
            desc.setEditable(false);
            desc.setBackground(Color.WHITE);
            desc.setBorder(null);
            desc.setAlignmentX(Component.LEFT_ALIGNMENT);

            JButton viewBtn = GuiUtils.createSecondaryButton("View Details");
            viewBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
            viewBtn.addActionListener(a -> JOptionPane.showMessageDialog(frame, e.title + "\n" + e.description));

            card.add(t);
            card.add(Box.createRigidArea(new Dimension(0, 6)));
            card.add(meta);
            card.add(Box.createRigidArea(new Dimension(0, 8)));
            card.add(desc);
            card.add(Box.createRigidArea(new Dimension(0, 10)));
            card.add(viewBtn);

            listContainer.add(card);
            listContainer.add(Box.createRigidArea(new Dimension(0, 12)));
        }

        JScrollPane scroll = new JScrollPane(listContainer);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        root.add(scroll, BorderLayout.CENTER);
        root.add(GuiUtils.createCreditsPanel(), BorderLayout.SOUTH);

        frame.add(root);
        frame.setVisible(true);
    }

    private List<EventModel> sampleEvents() {
        List<EventModel> l = new ArrayList<>();
        l.add(new EventModel("Spring Soccer Meetup", "2026-03-12", "Field A", "Friendly matches and sign-ups."));
        l.add(new EventModel("Coding Workshop", "2026-01-28", "Lab 204", "Intro to Java and Swing - bring laptop."));
        l.add(new EventModel("Charity Bake Sale", "2026-02-14", "Main Hall",
                "Bake sale to raise funds for local charity."));
        return l;
    }

    private static class EventModel {
        String title, date, location, description;

        EventModel(String t, String d, String l, String desc) {
            title = t;
            date = d;
            location = l;
            description = desc;
        }
    }
}
