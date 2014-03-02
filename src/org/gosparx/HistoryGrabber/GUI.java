package org.gosparx.HistoryGrabber;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;

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
	 * @throws MalformedURLException 
	 */
	private GUI(){
		System.out.println("Making GUI");
		System.out.println("Set Icon");
		frame = new JFrame("cRIO HistoryGrabber");
		frame.setBounds(0, 0, 200, 1234);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		frame.setLayout(gridbag);
		confirm = new JButton("Download Files");
		c.gridx = 0;
		c.gridy = 4;
		c.gridheight = 1;
		c.gridwidth = 3;
		gridbag.setConstraints(confirm, c);
		teamNumber = new JTextField("1126" ,10);
		c.gridx = 1;
		c.gridy = 1;
		c.gridheight = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(teamNumber, c);
		matchNumber = new JTextField(10);
		c.gridx = 1;
		c.gridy = 2;
		c.gridheight = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(matchNumber, c);
		getAll = new JCheckBox();
		c.gridx = 1;
		c.gridy = 3;
		c.gridheight = 1;
		c.gridwidth = 1;
		gridbag.setConstraints(getAll, c);
		teamLabel = new JLabel("Team Number:", JLabel.CENTER);
		c.gridx = 0;
		c.gridy = 1;
		c.gridheight = 1;
		c.gridwidth = 1;
		gridbag.setConstraints(teamLabel, c);
		matchLabel = new JLabel("Match Number:", JLabel.CENTER);
		c.gridx = 0;
		c.gridy = 2;
		c.gridheight = 1;
		c.gridwidth = 1;
		gridbag.setConstraints(matchLabel, c);
		getAllLabel = new JLabel("Get All:", JLabel.CENTER);
		c.gridx = 0;
		c.gridy = 3;
		c.gridheight = 1;
		c.gridwidth = 1;
		gridbag.setConstraints(getAllLabel, c);
		frame.add(teamLabel);
		frame.add(teamNumber);
		frame.add(matchNumber);
		frame.add(matchLabel);
		frame.add(getAllLabel);
		frame.add(getAll);
		frame.add(confirm);
		frame.pack();
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

	public void popupBox(int completed, int total){
		JOptionPane.showMessageDialog(frame, completed + " of " + total + " files downloaded sucessfully");
	}

}
