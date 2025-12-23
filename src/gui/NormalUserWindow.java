package src.gui;

import javax.swing.*;
import java.awt.*;

public class NormalUserWindow {
    public NormalUserWindow() {
        this("User", null);
    }

    private String userEmail;

    public NormalUserWindow(String displayName, String email) {
        this.userEmail = email;
        JFrame frame = new JFrame();
        GuiUtils.setupWindow(frame, "User Dashboard");

        JPanel root = GuiUtils.createStandardRootPanel();

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(24, 32, 12, 32));

        JLabel greeting = new JLabel("Hello, " + displayName);
        greeting.setFont(new Font("SansSerif", Font.BOLD, 28));

        JButton signOutBtn = GuiUtils.createSignOutButton();
        signOutBtn.addActionListener(e -> GuiUtils.handleSignOut(frame));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setOpaque(false);
        btnPanel.add(signOutBtn);

        topPanel.add(greeting, BorderLayout.WEST);
        topPanel.add(btnPanel, BorderLayout.EAST);

        root.add(topPanel, BorderLayout.NORTH);

        JPanel containerCard = GuiUtils.createContainerCard(new Dimension(800, 600));

        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(2, 2, 12, 12));
        grid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        grid.setOpaque(false);

        grid.add(GuiUtils.createFunctionCard("See Events", "View events of clubs you joined",
                new Dimension(220, 150), e -> {
                    frame.dispose();
                    new CalendarWindow(() -> new NormalUserWindow(displayName, email), email);
                }));

        grid.add(GuiUtils.createFunctionCard("Join a Club", "Discover and join new clubs",
                new Dimension(220, 150), e -> {
                    new ExploreClubsWindow(email);
                }));

        grid.add(GuiUtils.createFunctionCard("Quit a Club", "Leave a club you joined",
                new Dimension(220, 150), e -> {
                    new QuitClubWindow(email);
                }));

        grid.add(GuiUtils.createFunctionCard("Create a Club", "Start your own club",
                new Dimension(220, 150), e -> {
                    new CreateClubWindow(email, () -> {
                        frame.dispose();
                        String newDisplayName = src.backend.auth.Login.getDisplayName(email);
                        new ClubPresidentWindow(newDisplayName, email);
                    });
                }));

        containerCard.add(grid, BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(containerCard);
        root.add(centerPanel, BorderLayout.CENTER);
        root.add(GuiUtils.createCreditsPanel(), BorderLayout.SOUTH);

        frame.add(root);
        frame.setVisible(true);
    }

}
