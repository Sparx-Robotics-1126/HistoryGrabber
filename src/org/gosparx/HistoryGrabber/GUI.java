package org.gosparx.HistoryGrabber;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GUI extends JFrame implements ActionListener{
	
	/**
	 * The Static instance of GUI to support the singleton instance
	 */
	private static GUI gui;
	
	/**
	 * The UID 
	 */
	private static final long serialVersionUID = 1841730985723945L;

	/**********************************************************************************************************************
	 *											Components 
	 **********************************************************************************************************************/
	
	/**
	 * The "Download Files" Button 
	 */
	private JButton confirm;
	
	/**
	 * The Field for the Team number
	 */
	private JTextField teamNumber;
	
	/**
	 * The Field for the match number
	 */
	private JTextField matchNumber;
	
	/**
	 * The Checkbox for get all
	 */
	private JCheckBox getAll;
	
	/**
	 * The JFrame
	 */
	private JFrame frame;
	
	/**
	 * The Panel that contains teamNumber and teamLabel
	 */
	private JPanel teamPanel;
	
	/**
	 * The Panel that contains matchNumber and matchLabel
	 */
	private JPanel matchPanel;
	
	/**
	 * The Panel that contains getAll and getAllLabel
	 */
	private JPanel getAllPanel;
	
	/**
	 * The label for the team number
	 */
	private JLabel teamLabel;
	
	/**
	 * The label for the match number 
	 */
	private JLabel matchLabel;
	
	/**
	 * The label for the get all checkbox
	 */
	private JLabel getAllLabel;
	
	/**
	 * Supports the singleton model
	 * @return an instance of GUI
	 */
	public static GUI getInstance(){
		if(gui == null){
			gui = new GUI();
		}
		return gui;
	}
	
	/**
	 * Inits and adds all components. Then makes the frame visable
	 */
	private GUI(){
		frame = new JFrame("cRIO HistoryGrabber");
		frame.setBounds(0, 0, 300, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(4, 1));
		frame.setResizable(false);
		confirm = new JButton("Download Files");
		teamNumber =  new JTextField(4);
		matchNumber = new JTextField(3);
		getAll = new JCheckBox();
		teamLabel = new JLabel("Team Number:", JLabel.CENTER);
		matchLabel = new JLabel("Match Number:", JLabel.CENTER);
		getAllLabel = new JLabel("Get All:", JLabel.CENTER);
		teamPanel = new JPanel(new GridLayout(1, 2));
		matchPanel = new JPanel(new GridLayout(1, 2));
		getAllPanel = new JPanel(new GridLayout(1, 2));
		teamPanel.add(teamLabel);
		teamPanel.add(teamNumber);
		matchPanel.add(matchLabel);
		matchPanel.add(matchNumber);
		getAllPanel.add(getAllLabel);
		getAllPanel.add(getAll);
		frame.add(teamPanel);
		frame.add(matchPanel);
		frame.add(getAllPanel);
		frame.add(confirm);
		confirm.addActionListener(this);
		frame.setVisible(true);
	}

	/**
	 * Detects when an action is performed and acts appropietly
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(confirm)){
			HistoryGrabber.hg = new HistoryGrabber(HistoryGrabber.downloadables, Integer.parseInt(teamNumber.getText()), Integer.parseInt(matchNumber.getText()));
			HistoryGrabber.hg.setGetAll(getAll.isSelected());
			HistoryGrabber.hg.commenceDownloading();
		}
	}

}
