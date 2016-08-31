/*	Program name:	Lab 04 Employee Schedules
	Programmer:		Marcus Ross
	Date Due:		3 Mar, 2014
	Description:	This is an update of lab 03 in which the list object is made to operate on a file instead of an array.	*/

package lab04;

import lab04.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;

public class Main {
	public static void main(String[] args) {
		Frame frame = new Frame();
		List schedule = new List();
		LoadFrame(schedule, frame);
		DoLoadMenu(schedule, frame);
	}

	public static void LoadFrame(List schedule, Frame frame) { // initial frame settings, run once
		frame.setResizable(false);
		frame.addWindowListener(new WinListen(frame));
		frame.setSize(275, 225);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void MainMenu(List schedule, Frame frame) { // go to main menu
		// frame methods
		frame.removeAll();
		frame.setSize(275, 225);
		frame.setTitle("Schedule Manager");

		// components init
		Panel panel = new Panel(new GridLayout(5, 1));
		Button btnLoad = new Button("Load a schedule from a file");
		Button btnAdd = new Button("Add an entry to the schedule");
		Button btnRemove = new Button("Remove an entry from the schedule");
		Button btnDispSched = new Button("Display a range of the schedule");
		Button btnDispEntry = new Button("Display an entry from the schedule");

		// component methods
		btnLoad.addActionListener(new LoadMenu(schedule, frame));
		btnAdd.addActionListener(new AddMenu(schedule, frame));
		btnRemove.addActionListener(new RemoveMenu(schedule, frame));
		btnDispSched.addActionListener(new DispRangeMenu(schedule, frame));
		btnDispEntry.addActionListener(new DispEntryMenu(schedule, frame));

		// adding components
		panel.add(btnLoad);			panel.add(btnAdd);			panel.add(btnRemove);
		panel.add(btnDispSched);	panel.add(btnDispEntry);	frame.add(panel);

		frame.setVisible(true);
	}

	public static void DoLoadMenu(List schedule, Frame frame) { // used both on startup and by "Load a schedule" button
		// frame methods
		frame.removeAll();
		frame.setTitle("Load Schedule");

		// component init
		SpringLayout layout = new SpringLayout();
		Panel panel = new Panel(layout);

		Label fileNL = new Label("File name", Label.RIGHT);
		TextField fileNF = new TextField();
		Button btnOk = new Button("OK");
		Button btnCancel = new Button("Cancel");
		Label errorL = new Label("", Label.CENTER);

		// component methods
		errorL.setForeground(Color.red);
		btnOk.addActionListener(new LoadOk(schedule, frame, errorL, fileNF));
		btnCancel.addActionListener(new LoadOk(schedule, frame, errorL, fileNF));

		layout.putConstraint("HorizontalCenter", errorL, 0, "HorizontalCenter", panel);
		layout.putConstraint("West", errorL, 10, "West", panel);
		layout.putConstraint("East", errorL, -10, "East", panel);
		layout.putConstraint("South", fileNL, 0, "VerticalCenter", panel);
		layout.putConstraint("South", fileNF, 0, "VerticalCenter", panel);
		layout.putConstraint("West", fileNF, 10, "East", fileNL);
		layout.putConstraint("East", fileNF, -10, "East", panel);
		layout.putConstraint("South", btnOk, -10, "South", panel);
		layout.putConstraint("West", btnOk, -50, "HorizontalCenter", panel);
		layout.putConstraint("South", btnCancel, -10, "South", panel);
		layout.putConstraint("East", btnCancel, 50, "HorizontalCenter", panel);
		layout.putConstraint("West", btnCancel, 10, "East", btnOk);

		// add components
		panel.add(errorL);	panel.add(fileNL);		panel.add(fileNF);
		panel.add(btnOk);	panel.add(btnCancel);	frame.add(panel);

		frame.setVisible(true);
	}

	public static void LoadFile(List schedule, Frame frame, Label errorL, String fileN) { // called from "Load a schedule" menu
		switch(schedule.create(fileN)) {
			case 0:	MainMenu(schedule, frame);	return;
			case 1: errorL.setText("Schedule file could not be created");	return;
			case 2:	errorL.setText("Schedule file incorrectly formatted");
		}
	}

	public static void DoAddMenu(List schedule, Frame frame) { // called to load "Add an entry" menu
		// frame methods
		frame.removeAll();
		frame.setTitle("Add Entry");

		// component init
		SpringLayout layout = new SpringLayout();
		Panel panel = new Panel(layout);

		Label errorL = new Label("", Label.CENTER);
		Label locationL = new Label("Location", Label.RIGHT);
		Label dateL = new Label("Date (mm/dd/yyyy)", Label.RIGHT);
		Label beginL = new Label("Begin Time (hh:mm)", Label.RIGHT);
		Label endL = new Label("End Time (hh:mm)", Label.RIGHT);
		Label commentL = new Label("Comment", Label.RIGHT);
		TextField locationF = new TextField();
		TextField dateF = new TextField();
		TextField beginF = new TextField();
		TextField endF = new TextField();
		TextField commentF = new TextField();
		Button btnOk = new Button("OK");
		Button btnCancel = new Button("Cancel");

		// component methods
		errorL.setForeground(Color.red);
		btnOk.addActionListener(new AddOk(schedule, frame, errorL, locationF, dateF, beginF, endF, commentF));
		btnCancel.addActionListener(new AddOk(schedule, frame, errorL, locationF, dateF, beginF, endF, commentF));

		layout.putConstraint("HorizontalCenter", errorL, 0, "HorizontalCenter", panel);
		layout.putConstraint("North", locationL, 2, "South", errorL);
		layout.putConstraint("North", locationF, 2, "South", errorL);
		layout.putConstraint("North", dateL, 2, "South", locationF);
		layout.putConstraint("North", dateF, 2, "South", locationF);
		layout.putConstraint("North", beginL, 2, "South", dateF);
		layout.putConstraint("North", beginF, 2, "South", dateF);
		layout.putConstraint("North", endL, 2, "South", beginF);
		layout.putConstraint("North", endF, 2, "South", beginF);
		layout.putConstraint("North", commentL, 2, "South", endF);
		layout.putConstraint("North", commentF, 2, "South", endF);
		layout.putConstraint("West", errorL, 10, "West", panel);
		layout.putConstraint("West", locationF, -25, "HorizontalCenter", panel);
		layout.putConstraint("West", dateF, -110, "East", panel);
		layout.putConstraint("West", beginF, -110, "East", panel);
		layout.putConstraint("West", endF, -110, "East", panel);
		layout.putConstraint("West", commentF, -25, "HorizontalCenter", panel);
		layout.putConstraint("East", errorL, -10, "East", panel);
		layout.putConstraint("East", locationL, -10, "West", locationF);
		layout.putConstraint("East", dateL, -10, "West", dateF);
		layout.putConstraint("East", beginL, -10, "West", beginF);
		layout.putConstraint("East", endL, -10, "West", endF);
		layout.putConstraint("East", commentL, -10, "West", commentF);
		layout.putConstraint("East", locationF, -10, "East", panel);
		layout.putConstraint("East", dateF, -10, "East", panel);
		layout.putConstraint("East", beginF, -10, "East", panel);
		layout.putConstraint("East", endF, -10, "East", panel);
		layout.putConstraint("East", commentF, -10, "East", panel);
		layout.putConstraint("South", btnOk, -10, "South", panel);
		layout.putConstraint("West", btnOk, -50, "HorizontalCenter", panel);
		layout.putConstraint("South", btnCancel, -10, "South", panel);
		layout.putConstraint("East", btnCancel, 50, "HorizontalCenter", panel);
		layout.putConstraint("West", btnCancel, 10, "East", btnOk);

		// adding components
		panel.add(errorL);		panel.add(locationL);	panel.add(dateL);
		panel.add(beginL);		panel.add(endL);		panel.add(commentL);
		panel.add(locationF);	panel.add(dateF);		panel.add(beginF);
		panel.add(endF);		panel.add(commentF);	panel.add(btnOk);
		panel.add(btnCancel);	frame.add(panel);

		frame.setVisible(true);
	}

	public static void AddEntry(List schedule, Frame frame, String location, String begin, String end, String comment, Label errorL) { // called from "Add an entry" menu
		SimpleDateFormat formatTime = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		Date beginD, endD;
		Element entry;


		if(location.isEmpty()) { // stop if location TextField not filled
			errorL.setText("Enter a location");
			return;
		}

		try { // stop if begin time or end time TextFields not valid
			beginD = formatTime.parse(begin);
			endD = formatTime.parse(end);
		} catch(ParseException p) {
			errorL.setText("Date or time is incorrectly formatted");
			return;
		}

		entry = new Element(location, beginD, endD, comment); // new entry using values
		try {
			schedule.add(entry); // add entry to list
		} catch(IOException e) {
			errorL.setText("I/O error");
		}

		MainMenu(schedule, frame); // return to menu
	}

	public static void DoRemoveMenu(List schedule, Frame frame) { // called to load "Remove an entry" menu
		// frame methods
		frame.removeAll();
		frame.setTitle("Remove Entry");

		// component init
		SpringLayout layout = new SpringLayout();
		Panel panel = new Panel(layout);

		Label errorL = new Label("", Label.CENTER);
		Label dateL = new Label("Date of entry to remove (mm/dd/yyyy)", Label.RIGHT);
		TextField dateF = new TextField();
		Button btnOk = new Button("OK");
		Button btnCancel = new Button("Cancel");

		// component methods
		errorL.setForeground(Color.red);
		btnOk.addActionListener(new RemoveOk(schedule, frame, errorL, dateF));
		btnCancel.addActionListener(new RemoveOk(schedule, frame, errorL, dateF));

		layout.putConstraint("HorizontalCenter", errorL, 0, "HorizontalCenter", panel);
		layout.putConstraint("South", errorL, -5, "North", dateL);
		layout.putConstraint("West", errorL, 10, "West", panel);
		layout.putConstraint("East", errorL, -10, "East", panel);
		layout.putConstraint("HorizontalCenter", dateL, 0, "HorizontalCenter", panel);
		layout.putConstraint("South", dateL, -5, "North", dateF);
		layout.putConstraint("South", dateF, 0, "VerticalCenter", panel);
		layout.putConstraint("West", dateF, 10, "West", panel);
		layout.putConstraint("East", dateF, -10, "East", panel);
		layout.putConstraint("South", btnOk, -10, "South", panel);
		layout.putConstraint("West", btnOk, -50, "HorizontalCenter", panel);
		layout.putConstraint("South", btnCancel, -10, "South", panel);
		layout.putConstraint("East", btnCancel, 50, "HorizontalCenter", panel);
		layout.putConstraint("West", btnCancel, 10, "East", btnOk);

		// add components
		panel.add(errorL);	panel.add(dateL);		panel.add(dateF);
		panel.add(btnOk);	panel.add(btnCancel);	frame.add(panel);

		frame.setVisible(true);
	}

	public static void RemoveEntry(List schedule, Frame frame, Label errorL, String date) { // called from "Remove an entry" menu
		SimpleDateFormat formatTime = new SimpleDateFormat("MM/dd/yyyy");

		try { // stop if date TextField not valid date
			formatTime.parse(date);
		} catch(ParseException p) {
			errorL.setText("Date is incorrectly formatted");
			return;
		}

		try {
			if(schedule.delete(date)) // if successful deletion, return to menu
				Main.MainMenu(schedule, frame);
			else { // otherwise, report unsuccessful deletion
				errorL.setText("Entry not found");
				return;
			}
		} catch (IOException e) {
			errorL.setText("I/O error");
		}
	}

	public static void DoDispRangeMenu(List schedule, Frame frame) { // called to load "Display a range" menu
		// frame methods
		frame.removeAll();
		frame.setTitle("Display entries");

		// component init
		SpringLayout layout = new SpringLayout();
		Panel panel = new Panel(layout);

		Label errorL = new Label("", Label.CENTER);
		Label date1L = new Label("Earliest date of entries to display (mm/dd/yyyy)", Label.CENTER);
		Label date2L = new Label("Latest date of entries to display (mm/dd/yyyy)", Label.CENTER);
		TextField date1F = new TextField();
		TextField date2F = new TextField();
		Button btnOk = new Button("OK");
		Button btnCancel = new Button("Cancel");

		// component methods
		errorL.setForeground(Color.red);
		btnOk.addActionListener(new DispRangeOk(schedule, frame, errorL, date1F, date2F));
		btnCancel.addActionListener(new DispRangeOk(schedule, frame, errorL, date1F, date2F));

		layout.putConstraint("HorizontalCenter", errorL, 0, "HorizontalCenter", panel);
		layout.putConstraint("West", errorL, 10, "West", panel);
		layout.putConstraint("East", errorL, -10, "East", panel);
		layout.putConstraint("North", date1L, 5, "South", errorL);
		layout.putConstraint("HorizontalCenter", date1L, 0, "HorizontalCenter", panel);
		layout.putConstraint("North", date1F, 5, "South", date1L);
		layout.putConstraint("West", date1F, 10, "West", panel);
		layout.putConstraint("East", date1F, -10, "East", panel);
		layout.putConstraint("North", date2L, 5, "South", date1F);
		layout.putConstraint("HorizontalCenter", date2L, 0, "HorizontalCenter", panel);
		layout.putConstraint("North", date2F, 5, "South", date2L);
		layout.putConstraint("West", date2F, 10, "West", panel);
		layout.putConstraint("East", date2F, -10, "East", panel);
		layout.putConstraint("South", btnOk, -10, "South", panel);
		layout.putConstraint("West", btnOk, -50, "HorizontalCenter", panel);
		layout.putConstraint("South", btnCancel, -10, "South", panel);
		layout.putConstraint("East", btnCancel, 50, "HorizontalCenter", panel);
		layout.putConstraint("West", btnCancel, 10, "East", btnOk);

		// add components
		panel.add(errorL);	panel.add(date1L);	panel.add(date2L);		panel.add(date1F);
		panel.add(date2F);	panel.add(btnOk);	panel.add(btnCancel);	frame.add(panel);

		frame.setVisible(true);
	}

	public static void DispRange(List schedule, Frame frame, Date dateLow, Date dateHigh) { // called from "Display a range" menu
		Date entryDateLow, entryDateHigh;
		Font font = new Font("Lucida Console", Font.PLAIN, 10);
		String entries = ""; // will contain all text pertaining to entries; used in TextArea
		Element entry;

		try {
			schedule.reset();
		} catch(FileNotFoundException e) {
			System.out.println("good job; you deleted the list file during operation");
		}
		do {
			entry = schedule.retrieve(); // to simplify the sequence diagram; 9 calls of retrieve() in a row...
			entryDateLow = new Date(entry.getTimeBegin().getTime() - entry.getTimeBegin().getTime() % 86400000 + 14400000); // this rounds the begin time Date to the nearest time of midnight in GMT, from EST
			entryDateHigh = new Date(entry.getTimeEnd().getTime() - entry.getTimeEnd().getTime() % 86400000 + 18000000); // rounds the end time Date as above, but from EDT
			// if begin or end time are within the range, then add entry's data to entries String
			if(entryDateHigh.getTime() >= dateLow.getTime() && entryDateLow.getTime() <= dateHigh.getTime())
{
				entries += String.format("%-10.10s %-11s %-5s - %-6s %-15.15s\n", entry.getLocation(), entry.getDate(), entry.getTimeBegin(true), entry.getTimeEnd(true), entry.getComment());
System.out.format("%-10.10s %-11s %-5s - %-6s %-15.15s\n", entry.getLocation(), entry.getDate(), entry.getTimeBegin(true), entry.getTimeEnd(true), entry.getComment());
}
		} while(schedule.getNext()); // traverse list until at end

		// frame methods
		frame.removeAll();
		frame.setSize(350, 225);

		// component init
		SpringLayout layout = new SpringLayout();
		Panel panel = new Panel(layout);

		JTextArea entryTA = new JTextArea(entries);
		JScrollPane scrollPane = new JScrollPane(entryTA);
		JLabel header = new JLabel(String.format("%-10s %-11s %-14s %-15s", "Location", "Date", "Time", "Comment"));
		Button btnOk = new Button("OK");

		// component methods
		scrollPane.setColumnHeaderView(header);
		header.setFont(font);
		entryTA.setFont(font);
		entryTA.setEditable(false);
		btnOk.addActionListener(new MainMenu(schedule, frame));

		layout.putConstraint("North", scrollPane, 0, "North", panel);
		layout.putConstraint("West", scrollPane, 0, "West", panel);
		layout.putConstraint("East", scrollPane, 0, "East", panel);
		layout.putConstraint("South", scrollPane, -5, "North", btnOk);
		layout.putConstraint("South", btnOk, -5, "South", panel);
		layout.putConstraint("HorizontalCenter", btnOk, 0, "HorizontalCenter", panel);

		// add components
		panel.add(scrollPane);	panel.add(btnOk);	frame.add(panel);


		frame.setVisible(true);
	}

	public static void DoDispEntryMenu(List schedule, Frame frame) { // called to load "Display an entry" menu
		// frame methods
		frame.removeAll();
		frame.setTitle("Display Entry");

		// component init
		SpringLayout layout = new SpringLayout();
		Panel panel = new Panel(layout);

		Label errorL = new Label("", Label.CENTER);
		Label promptL = new Label("Date of entry to display (mm/dd/yyyy)", Label.RIGHT);
		TextField dateF = new TextField();
		Label locationL = new Label("", Label.CENTER);
		Label dateL = new Label("", Label.CENTER);
		Label timeL = new Label("", Label.CENTER);
		Label commentL = new Label("", Label.CENTER);
		Button btnOk = new Button("Search");
		Button btnCancel = new Button("Back");

		// component methods
		errorL.setForeground(Color.red);
		btnOk.addActionListener(new DispEntryOk(schedule, frame, errorL, dateF, locationL, dateL, timeL, commentL));
		btnCancel.addActionListener(new DispEntryOk(schedule, frame, errorL, dateF, locationL, dateL, timeL, commentL));

		layout.putConstraint("North", locationL, 0, "South", dateF);
		layout.putConstraint("North", dateL, 0, "South", locationL);
		layout.putConstraint("North", timeL, 0, "South", dateL);
		layout.putConstraint("North", commentL, 0, "South", timeL);
		layout.putConstraint("West", locationL, 10, "West", panel);
		layout.putConstraint("West", dateL, 10, "West", panel);
		layout.putConstraint("West", timeL, 10, "West", panel);
		layout.putConstraint("West", commentL, 10, "West", panel);
		layout.putConstraint("East", locationL, -10, "East", panel);
		layout.putConstraint("East", dateL, -10, "East", panel);
		layout.putConstraint("East", timeL, -10, "East", panel);
		layout.putConstraint("East", commentL, -10, "East", panel);
		layout.putConstraint("West", errorL, 10, "West", panel);
		layout.putConstraint("East", errorL, -10, "East", panel);
		layout.putConstraint("HorizontalCenter", promptL, 0, "HorizontalCenter", panel);
		layout.putConstraint("North", promptL, 0, "South", errorL);
		layout.putConstraint("North", dateF, 0, "South", promptL);
		layout.putConstraint("West", dateF, 10, "West", panel);
		layout.putConstraint("East", dateF, -10, "East", panel);
		layout.putConstraint("South", btnOk, -10, "South", panel);
		layout.putConstraint("West", btnOk, -50, "HorizontalCenter", panel);
		layout.putConstraint("South", btnCancel, -10, "South", panel);
		layout.putConstraint("East", btnCancel, 50, "HorizontalCenter", panel);
		layout.putConstraint("West", btnCancel, 10, "East", btnOk);

		// add components
		panel.add(errorL);		panel.add(promptL);		panel.add(dateF);	panel.add(btnOk);
		panel.add(btnCancel);	panel.add(locationL);	panel.add(dateL);	panel.add(timeL);
		panel.add(commentL);	frame.add(panel);

		frame.setVisible(true);
	}

	public static void DispEntry(List schedule, Frame frame, Label errorL, Label locationL, Label dateL, Label timeL, Label commentL, String date) { // called from "Display an entry" menu
		SimpleDateFormat formatTime = new SimpleDateFormat("MM/dd/yyyy");
		Element entry;

		try { // stop, update error label, and blank the data labels if date TextField not a valid date
			formatTime.parse(date);
		} catch(ParseException p) {
			errorL.setText("Date is incorrectly formatted");
			locationL.setText("");
			dateL.setText("");
			timeL.setText("");
			commentL.setText("");
			return;
		}

		try {
			if(schedule.search(date)) { // if search successful, blank error label and update data labels with entry data
				entry = schedule.retrieve();

				errorL.setText("");
				locationL.setText(entry.getLocation());
				dateL.setText(entry.getDate());
				timeL.setText(entry.getTimeBegin(true) + " to " + entry.getTimeEnd(true));
				commentL.setText(entry.getComment());
			} else { // stop, update error label, and blank the data labels if search failed
				errorL.setText("Entry not found");
				locationL.setText("");
				dateL.setText("");
				timeL.setText("");
				commentL.setText("");
				return;
			}
		} catch(FileNotFoundException e) {
			errorL.setText("I/O error");
		}
	}
}

class LoadMenu implements ActionListener { // used when pressing "Load a schedule" button
	private List schedule;
	private Frame frame;

	public LoadMenu(List schedule, Frame frame) {
		this.schedule = schedule;	this.frame = frame;
	}

	public void actionPerformed(ActionEvent e) {
		Main.DoLoadMenu(schedule, frame);
	}
}

class LoadOk implements ActionListener { // used when pressing "OK" or "Cancel" from "Load a schedule" menu
	private List schedule;
	private Frame frame;
	private TextField fileNF;
	private Label errorL;

	public LoadOk(List schedule, Frame frame, Label errorL, TextField fileNF) {
		this.schedule = schedule;
		this.frame = frame;
		this.errorL = errorL;
		this.fileNF = fileNF;
	}

	public void actionPerformed(ActionEvent e) {
		String fileN = fileNF.getText();
		if(e.getActionCommand().equals("OK")) // if "OK" button pressed
			Main.LoadFile(schedule, frame, errorL, fileN); // load from file
		else
			if(schedule.isEmpty()) { // close program if list is empty (i.e. if program has just started and no schedule loaded)
				frame.setVisible(false);
				frame.dispose();
				System.exit(0);
			} else // return to menu if list exists
				Main.MainMenu(schedule, frame);
	}
}

class AddMenu implements ActionListener { // used by "Add a schedule" button
	private List schedule;
	private Frame frame;

	public AddMenu(List schedule, Frame frame) {
		this.schedule = schedule;	this.frame = frame;
	}

	public void actionPerformed(ActionEvent e) {
		Main.DoAddMenu(schedule, frame);
	}
}

class AddOk implements ActionListener { // used when pressing "OK" or "Cancel" from "Add a schedule" menu
	private List schedule;
	private Frame frame;
	private Label errorL;
	private TextField locationF, dateF, beginF, endF, commentF;

	public AddOk(List schedule, Frame frame, Label errorL, TextField locationF, TextField dateF, TextField beginF, TextField endF, TextField commentF) {
		this.schedule = schedule;	this.frame = frame;			this.errorL = errorL;
		this.locationF = locationF;	this.dateF = dateF;			this.beginF = beginF;
		this.endF = endF;			this.commentF = commentF;
	}

	public void actionPerformed(ActionEvent e) {
		String location, begin, end, comment;

		location = locationF.getText(); // get values for new entry
		begin = dateF.getText() + " " + beginF.getText();
		end = dateF.getText() + " " + endF.getText();
		comment = commentF.getText();

		if(e.getActionCommand().equals("OK"))
			Main.AddEntry(schedule, frame, location, begin, end, comment, errorL);
		else
			Main.MainMenu(schedule, frame);
	}
}

class RemoveMenu implements ActionListener { // used by "Remove an entry" button
	private List schedule;
	private Frame frame;

	public RemoveMenu(List schedule, Frame frame) {
		this.schedule = schedule;	this.frame = frame;
	}

	public void actionPerformed(ActionEvent e) {
		Main.DoRemoveMenu(schedule, frame);
	}
}

class RemoveOk implements ActionListener { // used by "OK" and "Cancel" buttons in "Remove an entry" menu
	private List schedule;
	private Frame frame;
	private Label errorL;
	private TextField dateF;

	public RemoveOk(List schedule, Frame frame, Label errorL, TextField dateF) {
		this.schedule = schedule;	this.frame = frame;
		this.errorL = errorL;		this.dateF = dateF;
	}

	public void actionPerformed(ActionEvent e) {
		String date = dateF.getText();;

		if(e.getActionCommand().equals("OK")) // if "OK" button pressed and not "Cancel"
			Main.RemoveEntry(schedule, frame, errorL, date);
		else
			Main.MainMenu(schedule, frame);
	}
}

class DispRangeMenu implements ActionListener { // used by "Display a range" button
	private List schedule;
	private Frame frame;

	public DispRangeMenu(List schedule, Frame frame) {
		this.schedule = schedule;	this.frame = frame;
	}

	public void actionPerformed(ActionEvent e) {
		Main.DoDispRangeMenu(schedule, frame);
	}
}

class DispRangeOk implements ActionListener { // used by "OK" or "Cancel" buttons in "Display a range" menu
	private List schedule;
	private Frame frame;
	private Label errorL;
	private TextField date1F, date2F;

	public DispRangeOk(List schedule, Frame frame, Label errorL, TextField date1F, TextField date2F) {
		this.schedule = schedule;	this.frame = frame;
		this.errorL = errorL;		this.date1F = date1F;	this.date2F = date2F;
	}

	public void actionPerformed(ActionEvent e) {
		SimpleDateFormat formatTime;
		Date dateLow, dateHigh;

		if(e.getActionCommand().equals("OK")) { // if "OK" button pressed and not "Cancel"
			formatTime = new SimpleDateFormat("MM/dd/yyyy");

			try { // stop if low date or high date TextFields not valid
				dateLow = formatTime.parse(date1F.getText());
				dateHigh = formatTime.parse(date2F.getText());
			} catch(ParseException p) {
				errorL.setText("A date is incorrectly formatted");
				return;
			}

			Main.DispRange(schedule, frame, dateLow, dateHigh);
		} else
			Main.MainMenu(schedule, frame);
	}
}

class MainMenu implements ActionListener { // used when pressing "OK" button while viewing entries from "Display a range" menu
	private List schedule;
	private Frame frame;

	public MainMenu(List schedule, Frame frame) {
		this.schedule = schedule;	this.frame = frame;		
	}

	public void actionPerformed(ActionEvent e) {
		Main.MainMenu(schedule, frame);
	}
}

class DispEntryMenu implements ActionListener { // used by "Display an entry" button
	private List schedule;
	private Frame frame;

	public DispEntryMenu(List schedule, Frame frame) {
		this.schedule = schedule;	this.frame = frame;		
	}

	public void actionPerformed(ActionEvent e) {
		Main.DoDispEntryMenu(schedule, frame);
	}
}

class DispEntryOk implements ActionListener { // used by "Search" button in "Display an entry" menu
	private List schedule;
	private Frame frame;
	private Label errorL, locationL, dateL, timeL, commentL;
	private TextField dateF;

	public DispEntryOk(List schedule, Frame frame, Label errorL, TextField dateF, Label locationL, Label dateL, Label timeL, Label commentL) {
		this.schedule = schedule;	this.frame = frame;			this.errorL = errorL;
		this.dateF = dateF;			this.locationL = locationL;	this.dateL = dateL;
		this.timeL = timeL;			this.commentL = commentL;
	}

	public void actionPerformed(ActionEvent e) {
		String date = dateF.getText();

		if(e.getActionCommand().equals("Search")) //if "Search" button pressed and not "Back"
			Main.DispEntry(schedule, frame, errorL, locationL, dateL, timeL, commentL, date);
		else
			Main.MainMenu(schedule, frame);
	}
}

class WinListen implements WindowListener {
	private Frame frame;

	public WinListen(Frame frame) {
		this.frame = frame;
	}

	public void windowClosing(WindowEvent e) {
		frame.setVisible(false);
		frame.dispose();
		System.exit(0);
	}

	public void windowOpened(WindowEvent e) {}
	public void windowClosed(WindowEvent e) { }
	public void windowIconified(WindowEvent e) { }
	public void windowDeiconified(WindowEvent e) { }
	public void windowActivated(WindowEvent e) { }
	public void windowDeactivated(WindowEvent e) { }
}