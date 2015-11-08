package zad1;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainChatWindow extends JPanel implements ActionListener {
	protected JTextField inputField;
	protected JTextArea chatArea;
	private JButton logoutButton;
	private final static String newline = "\n";
	private Client client;

	public MainChatWindow(Client c) {
		super(new BorderLayout(0, 0));
		this.client = c;
		inputField = new JTextField(20);
		inputField.addActionListener(this);
		chatArea = new JTextArea(5, 5);
		chatArea.setEditable(false);
		chatArea.setLineWrap(true);
		logoutButton = new JButton("Wyloguj: "+this.client.getNickname());
		logoutButton.setActionCommand("logout");
		logoutButton.addActionListener(this);
		JScrollPane scrollPane = new JScrollPane(chatArea);
		setPreferredSize (new Dimension (800, 600));
		add(logoutButton, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		add(inputField, BorderLayout.SOUTH);
		this.createAndShowGUI();
	}

	public void addChatText(String text){
		chatArea.append(text + newline);
		chatArea.setCaretPosition(chatArea.getDocument().getLength());
		chatArea.update(chatArea.getGraphics());
	}
	public void actionPerformed(ActionEvent evt) {
		if ("logout".equals(evt.getActionCommand())) System.exit(0);
		if(inputField.getText().length() > 0)
			this.client.sendMessage(inputField.getText());
		inputField.setText("");
	}
	public void createAndShowGUI() {
		JFrame frame = new JFrame("TPO2_PP_S10623 - Chat client");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.pack();
		frame.setVisible(true);
	}
}
