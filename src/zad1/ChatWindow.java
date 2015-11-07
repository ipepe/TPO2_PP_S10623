package zad1;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
public class ChatWindow extends JPanel implements ActionListener {
    protected JTextField textField;
    protected JTextArea textArea;
    private final static String newline = "\n";
    private Send sendw;
    private JButton buttonDc;
    
    public ChatWindow() {
        super(new BorderLayout(0, 0));
        textField = new JTextField(20);
        textField.addActionListener(this);
        textArea = new JTextArea(5, 5);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        buttonDc = new JButton("Disconnect");
        buttonDc.setActionCommand("disconnect");
        buttonDc.addActionListener(this);
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        setPreferredSize (new Dimension (500, 350));

        //add components
        add (buttonDc, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(textField, BorderLayout.SOUTH);
        
    }
    public void setSend(Send sendw){
        this.sendw=sendw;
    }
    
    public void addText(String text){
        //RECEIVE KLASA
        textArea.append(text + newline);
        textArea.setCaretPosition(textArea.getDocument().getLength());
        textArea.update(textArea.getGraphics());
    }
    public void actionPerformed(ActionEvent evt) {
        //Button action
        if ("disconnect".equals(evt.getActionCommand())) {
            System.exit(0);
        }
        
        //SEND KLASA
        String text = textField.getText();
        sendw.sendMsg(text);
        
        //Wykomentowa� przy wspolpracy gui z send.class
        //textArea.append(text + newline);
        //textArea.setCaretPosition(textArea.getDocument().getLength());
        //Wykomentowac przy wspolpracy gui z send.class
        
        textField.setText("");
    }
    public void createAndShowGUI(String[] strTab) {
        this.setSend(new Send(strTab[0], strTab[2], strTab[1]));
        Receive receiveo = new Receive(strTab[0],strTab[2]);
        receiveo.setGui(this);
        JFrame frame = new JFrame("Projekt zaliczeniowy - Patryk Ptasinski - Chat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.pack();
        frame.setVisible(true);
    }
}