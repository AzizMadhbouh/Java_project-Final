package src.gui;

import src.backend.club.ClubManager;
import src.backend.dao.ClubDAO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ViewUsersWindow {
	public ViewUsersWindow(String presidentEmail) {
		JFrame frame = new JFrame();
		GuiUtils.setupWindow(frame, "Club Members");

		JPanel root = GuiUtils.createStandardRootPanel();

		JLabel title = GuiUtils.createTitleLabel("Club Members");
		title.setBorder(BorderFactory.createEmptyBorder(24, 32, 12, 32));
		root.add(title, BorderLayout.NORTH);

		JPanel list = new JPanel();
		list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
		list.setOpaque(false);

		List<ClubDAO.MemberRecord> members = ClubManager.getMembersByPresident(presidentEmail);

		if (members.isEmpty()) {
			JLabel noMembers = new JLabel("No members found in your club(s).");
			noMembers.setBorder(BorderFactory.createEmptyBorder(20, 32, 0, 0));
			list.add(noMembers);
		} else {
			for (ClubDAO.MemberRecord m : members) {
				JPanel card = GuiUtils.createCardPanel(new Dimension(800, 100));
				card.setLayout(new GridLayout(1, 3, 20, 0));
				card.setMaximumSize(new Dimension(1000, 100));

				JLabel emailLabel = new JLabel("<html><b>Gmail:</b><br>" + m.email + "</html>");
				JLabel firstNameLabel = new JLabel("<html><b>Name:</b><br>" + m.firstName + "</html>");
				JLabel lastNameLabel = new JLabel("<html><b>Lastname:</b><br>" + m.lastName + "</html>");

				emailLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
				firstNameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
				lastNameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

				card.add(emailLabel);
				card.add(firstNameLabel);
				card.add(lastNameLabel);

				list.add(card);
				list.add(Box.createRigidArea(new Dimension(0, 10)));
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
