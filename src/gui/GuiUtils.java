package src.gui;

import javax.swing.*;
import java.awt.*;

public class GuiUtils {
    private static final Color PRIMARY_COLOR = new Color(37, 99, 235);
    private static final Color SECONDARY_BG = new Color(245, 247, 250);
    private static final Color SECONDARY_TEXT = new Color(55, 65, 81);
    private static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 28);
    private static final Font SUBTITLE_FONT = new Font("SansSerif", Font.PLAIN, 14);
    private static final Font BUTTON_FONT = new Font("SansSerif", Font.BOLD, 14);

    public static JButton createPrimaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btn.setBackground(PRIMARY_COLOR);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(true);
        btn.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 1, true));
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFont(BUTTON_FONT);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(PRIMARY_COLOR.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(PRIMARY_COLOR);
            }
        });
        return btn;
    }

    public static JButton createSecondaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btn.setBackground(SECONDARY_BG);
        btn.setForeground(SECONDARY_TEXT);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 225), 1, true));
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFont(BUTTON_FONT);
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(SECONDARY_BG.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(SECONDARY_BG);
            }
        });
        return btn;
    }

    public static JPanel createCardPanel(Dimension size) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        if (size != null) {
            card.setPreferredSize(size);
        }

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 224, 228), 1),
                BorderFactory.createEmptyBorder(24, 32, 24, 32)));
        return card;
    }

    public static JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(TITLE_FONT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setForeground(new Color(17, 24, 39));
        return label;
    }

    public static JLabel createSubtitleLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(SUBTITLE_FONT);
        label.setForeground(Color.GRAY);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    public static JLabel createLinkLabel(String text) {

        JLabel label = new JLabel("<html><u>" + text + "</u></html>");
        label.setForeground(new Color(59, 130, 246));
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    public static JTextField createLabeledField(String label) {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setBorder(BorderFactory.createTitledBorder(label));
        return field;
    }

    public static JPasswordField createLabeledPasswordField(String label) {
        JPasswordField field = new JPasswordField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setBorder(BorderFactory.createTitledBorder(label));
        return field;
    }

    public static JPanel createFunctionCard(String title, String desc, Dimension size,
            java.awt.event.ActionListener action) {
        JPanel card = createCardPanel(size == null ? new Dimension(200, 140) : size);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel descLabel = new JLabel("<html>" + desc + "</html>");
        descLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        descLabel.setForeground(Color.DARK_GRAY);
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btn = createPrimaryButton("Go");
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        if (action != null)
            btn.addActionListener(action);

        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 8)));
        card.add(descLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(btn);

        return card;
    }

    public static JPanel createContainerCard(Dimension size) {
        JPanel container = createCardPanel(size);
        container.setLayout(new BorderLayout());
        return container;
    }

    public static String promptForInput(Component parent, String message) {
        String v = JOptionPane.showInputDialog(parent, message);
        if (v == null)
            return null;
        v = v.trim();
        return v.isEmpty() ? null : v;
    }

    public static void showInfo(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public static JLabel createBadge(String text, Color bgColor) {
        JLabel badge = new JLabel(" " + text + " ");
        badge.setFont(new Font("SansSerif", Font.BOLD, 12));
        badge.setForeground(Color.WHITE);
        badge.setBackground(bgColor);
        badge.setOpaque(true);
        badge.setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6));
        return badge;
    }

    public static JButton createDangerButton(String text) {
        JButton btn = createPrimaryButton(text);
        btn.setBackground(new Color(220, 38, 38));

        btn.setBorder(BorderFactory.createLineBorder(new Color(220, 38, 38), 1, true));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(185, 28, 28));
                btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(220, 38, 38));
            }
        });
        return btn;
    }

    public static JButton createBackButton(java.awt.event.ActionListener action) {
        JButton btn = createSecondaryButton("← Back");
        btn.setPreferredSize(new Dimension(120, 40));
        btn.setMaximumSize(new Dimension(120, 40));
        if (action != null) {
            btn.addActionListener(action);
        }
        return btn;
    }

    public static JButton createSignOutButton() {
        JButton btn = new JButton("Sign Out");
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setForeground(new Color(220, 38, 38));
        btn.setBackground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 38, 38), 1, true),
                BorderFactory.createEmptyBorder(8, 20, 8, 20)));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(254, 242, 242));
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(Color.WHITE);
            }
        });
        return btn;
    }

    public static JPanel createBottomPanel(JButton button) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 32, 40, 32));
        if (button != null) {
            panel.add(button);
        }
        return panel;
    }

    public static void setupWindow(JFrame frame, String title, int closeOperation) {
        frame.setTitle(title);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(closeOperation);
    }

    public static void setupWindow(JFrame frame, String title) {
        setupWindow(frame, title, JFrame.DISPOSE_ON_CLOSE);
    }

    public static JPanel createStandardRootPanel() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(240, 244, 248));
        return root;
    }

    public static JPanel createCreditsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setOpaque(false);
        JLabel credits = new JLabel("Ce projet a été réalisé par Mohamed Aziz Madhbouh et Mohamed Amine Ferjani");
        credits.setFont(new Font("SansSerif", Font.PLAIN, 11));
        credits.setForeground(new Color(107, 114, 128)); // A nice gray
        panel.add(credits);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        return panel;
    }

    public static void handleSignOut(JFrame currentFrame) {
        currentFrame.dispose();
        new HomeWindow();
    }
}
