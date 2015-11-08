package zad1;

//Generated by GuiGenie - Copyright (c) 2004 Mario Awad.
//Home Page http://guigenie.cjb.net - Check often for new versions!

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class LoginWindow extends JPanel implements ActionListener {
	private JLabel nickLabel;
	private JTextField nickInput;
	private JButton confirmButton;
	private JFrame frame;

	public LoginWindow() {
		nickLabel = new JLabel ("Pseudonim(min 3 znaki):");
		nickInput = new JTextField ();
		nickInput.addActionListener(this);
		confirmButton = new JButton ("Zatwierdz");
		confirmButton.addActionListener(this);
		setPreferredSize (new Dimension (300, 70));
		BoxLayout layout = new BoxLayout (this, BoxLayout.Y_AXIS);
		setLayout (layout);
		add (nickLabel);
		add (nickInput);
		add (confirmButton);
		frame = new JFrame ("LoginWindow");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add (this);
		frame.pack();
		frame.setVisible (true);
		frame.setResizable(false);
	}
	public void actionPerformed(ActionEvent evt){
		if(nickInput.getText().length()>3){
			new Thread()
			{
				public void run() {
					new Client(nickInput.getText());
				}
			}.start();
			frame.dispose();
		}
	}
}
