package com.colin.wielga;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

//TODO :
// put on the git
// add over
// remover tool bar
// fix the fact that you have to do the resizing thing
// make "x" on dialog work
// "" can not be an over or an under if show up if we have no over overs and unders

public class Editor extends JFrame {
	private TextArea textArea = new TextArea("", 0, 0,
			TextArea.SCROLLBARS_VERTICAL_ONLY);
	// private MenuBar menuBar = new MenuBar(); // first, create a MenuBar item
	// private Menu file = new Menu(); // our File menu
	// what's going in File? let's see...
	// private MenuItem openFile = new MenuItem(); // an open option
	// private MenuItem saveFile = new MenuItem(); // a save option
	// private MenuItem close = new MenuItem(); // and a close option!

	// colins vars
	private Button newover = new Button();
	private Button newunder = new Button();
	private ArrayList<Button> overs = new ArrayList<Button>();
	private TextField title = new TextField();
	private Panel overspanel = new Panel();
	private Panel underspanel = new Panel();
	private ArrayList<Button> unders = new ArrayList<Button>();
	private String currentOpen = "main";
	private String[] overnams;
	private String[] undernams;
	private Dialog newunderD = new Dialog(this, "add under", false);
	private Button newunderConf = new Button();
	private TextField newoverTitle = new TextField();
	private Dialog newoverD = new Dialog(this, "add over", false);
	private Button newoverConf = new Button();
	private TextField newunderTitle = new TextField();
	private int width = 800;
	private int hieght = 600;

	public Editor() {
		// for now
		this.setSize(width, hieght); // set the initial size of the window
		this.setTitle("Java Notepad Tutorial"); // set the title of the window
		setDefaultCloseOperation(EXIT_ON_CLOSE); // set the default close
													// operation (exit when it
													// gets closed)
		this.textArea.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		// set up the main frame
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(textArea, BorderLayout.CENTER);
		this.getContentPane().add(overspanel, BorderLayout.WEST);
		this.getContentPane().add(underspanel, BorderLayout.EAST);
		// set up the add under dialog
		this.newunderConf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO if the title is not teh empty String
				newunderD.setVisible(false);
				String lastat = currentOpen;
				addUnder(newunderTitle.getText());
				saveAs(currentOpen);// this may not be needed since we save in
									// addUnder
				openFile(newunderTitle.getText());
				addOver(lastat);
				update();
			}
		});
		newunderTitle.setSize(200, 25);
		newunderConf.setSize(200, 25);
		newunderConf.setLabel("add under");
		newunderD.setSize(200, 50);
		this.newunderD.add(newunderTitle, BorderLayout.CENTER);
		this.newunderD.add(newunderConf, BorderLayout.SOUTH);

		this.newover.setLabel("add");
		this.newover.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveAs(currentOpen);// this may not be needed since we save whent they click on add
				newoverD.setVisible(true);
			}
		});

		// this.newover.setSize(50, 50);
		this.newoverConf.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveAs(currentOpen);
				// TODO if the title is not teh empty String
				newoverD.setVisible(false);
				String lastat = currentOpen;
				addOver(newoverTitle.getText());
				saveAs(currentOpen);// this may not be needed since we save in
									// addOver
				openFile(newoverTitle.getText());
				addUnder(lastat);
				update();
			}
		});
		newoverTitle.setSize(200, 25);
		newoverConf.setSize(200, 25);
		newoverConf.setLabel("add under");
		newoverD.setSize(200, 50);
		this.newoverD.add(newoverTitle, BorderLayout.CENTER);
		this.newoverD.add(newoverConf, BorderLayout.SOUTH);

		this.newunder.setLabel("add");
		// this.newunder.setSize(50, 50);
		this.newunder.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveAs(currentOpen);
				newunderD.setVisible(true);
			}

		});

		this.openFile(currentOpen);
		this.update();
		// add our menu bar into the GUI
		// this.setMenuBar(this.menuBar);
		// this.menuBar.add(this.file); // we'll configure this later

		// first off, the design of the menuBar itself. Pretty simple, all we
		// need to do
		// is add a couple of menus, which will be populated later on
		// this.file.setLabel("File");

		// now it's time to work with the menu. I'm only going to add a basic
		// File menu
		// but you could add more!

		// now we can start working on the content of the menu~ this gets a
		// little repetitive,
		// so please bare with me!

		// time for the repetitive stuff. let's add the "Open" option
		// this.openFile.setLabel("Open"); // set the label of the menu item
		// this.openFile.addActionListener(this); // add an action listener (so
		// we
		// know when it's been clicked
		// this.openFile.setShortcut(new MenuShortcut(KeyEvent.VK_O, false)); //
		// set
		// a
		// keyboard
		// shortcut
		// this.file.add(this.openFile); // add it to the "File" menu

		// and the save...
		// this.saveFile.setLabel("Save");
		// this.saveFile.addActionListener(this);
		// this.saveFile.setShortcut(new MenuShortcut(KeyEvent.VK_S, false));
		// this.file.add(this.saveFile);

		// and finally, the close option
		// this.close.setLabel("Close");
		// along with our "CTRL+F4" shortcut to close the window, we also have
		// the default closer, as stated at the beginning of this tutorial.
		// this means that we actually have TWO shortcuts to close:
		// 1) the default close operation (example, Alt+F4 on Windows)
		// 2) CTRL+F4, which we are about to define now: (this one will appear
		// in the label)
		// this.close.setShortcut(new MenuShortcut(KeyEvent.VK_F4, false));
		// this.close.addActionListener(this);
		// this.file.add(this.close);
	}

	protected void addOver(String file) {
		saveAs(currentOpen);
		try {
			Scanner scan = new Scanner(new FileReader(getFilePath(currentOpen)));
			textArea.setText("");
			if (scan.hasNext()) {
				String s = scan.nextLine();
				if (s.equals("")) {
					textArea.append(s + file + "\n");
				} else {
					textArea.append(s + "," + file + "\n");
				}
				while (scan.hasNext()) {
					this.textArea.append(scan.nextLine() + "\n");
				}
			} else {
				textArea.setText(file + "\n" + "\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		saveAs(currentOpen);
	}

	protected void addUnder(String file) {
		saveAs(currentOpen);
		try {
			Scanner scan = new Scanner(new FileReader(getFilePath(currentOpen)));
			textArea.setText("");
			if (scan.hasNext()) {
				this.textArea.append(scan.nextLine() + "\n");
			}
			if (scan.hasNext()) {
				String s = scan.nextLine();
				if (s.equals("")) {
					textArea.append(s + file + "\n");
				} else {
					textArea.append(s + "," + file + "\n");
				}

				while (scan.hasNext()) {
					this.textArea.append(scan.nextLine() + "\n");
				}
			} else {
				textArea.setText("\n" + file + "\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		saveAs(currentOpen);
	}

	private ArrayList<Button> updateunders() {
		ArrayList<Button> result = new ArrayList<Button>();
		for (int i = 0; i < undernams.length; i++) {
			Button b = new Button();
			b.setLabel(undernams[i]);
			// b.setSize(50, 50);
			final String name = undernams[i];
			b.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					saveAs(currentOpen);
					openFile(name);
					update();
				}

			});
			result.add(b);
		}
		result.add(newunder);
		return result;
	}

	private ArrayList<Button> updateovers() {
		ArrayList<Button> result = new ArrayList<Button>();
		for (int i = 0; i < overnams.length; i++) {
			Button b = new Button();
			b.setLabel(overnams[i]);
			// b.setSize(50, 50);
			result.add(b);
			final String name = overnams[i];
			b.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					saveAs(currentOpen);
					openFile(name);
					update();
				}

			});
			// TODO add on click listeners
		}
		result.add(newover);
		return result;
	}

	protected void update() {
		try {
			// create a scanner to read the file (getSelectedFile().getPath()
			// will get the path to the file)
			Scanner scan = new Scanner(new FileReader(getFilePath(currentOpen)));
			if (scan.hasNext()) {
				overnams = scan.nextLine().split(",");
			} else {
				overnams = new String[0];
			}
			overs = updateovers();
			overspanel.removeAll();
			overspanel.setLayout(new GridLayout(overs.size(), 1));
			for (int i = 0; i < overs.size(); i++) {
				overspanel.add(overs.get(i));
			}
			if (scan.hasNext()) {
				undernams = scan.nextLine().split(",");
			} else {
				undernams = new String[0];
			}
			unders = updateunders();
			underspanel.removeAll();
			underspanel.setLayout(new GridLayout(unders.size(), 1));
			for (int i = 0; i < unders.size(); i++) {
				underspanel.add(unders.get(i));
			}
			underspanel.setSize(75, 200);
		} catch (Exception ex) { // catch any exceptions, and...
			// ...write to the debug console
			System.out.println(ex.getMessage());
		}
		pack();
		setSize(width, hieght);
	}

	protected void openFile(String name) {
		this.textArea.setText(""); // clear the TextArea before applying the
									// file contents
		this.currentOpen = name;
		try {
			// create a scanner to read the file (getSelectedFile().getPath()
			// will get the path to the file)
			Scanner scan = new Scanner(new FileReader(getFilePath(name)));
			// TODO does not read the first two lines?
			while (scan.hasNext())
				// while there's still something to read
				this.textArea.append(scan.nextLine() + "\n"); // append the line
																// to the
																// TextArea
		} catch (Exception ex) { // catch any exceptions, and...
			// ...write to the debug console
			System.out.println(ex.getMessage());
		}
	}

	protected void saveAs(String name) {
		try {
			// create a buffered writer to write to a file
			BufferedWriter out = new BufferedWriter(new FileWriter(
					getFilePath(name)));
			out.write(this.textArea.getText()); // write the contents of the
												// TextArea to the file
			out.close(); // close the file stream
		} catch (Exception ex) { // again, catch any exceptions and...
			// ...write to the debug console
			System.out.println(ex.getMessage());
		}
	}

	private String getFilePath(String name) {
		return "//home//colin//Desktop//WeBasic//" + name + ".txt";
	}

	// public void actionPerformed(ActionEvent e) {
	// if the source of the event was our "close" option
	// if (e.getSource() == this.close)
	// this.dispose(); // dispose all resources and close the application

	// if the source was the "open" option
	// else if (e.getSource() == this.openFile) {
	// JFileChooser open = new JFileChooser(); // open up a file chooser (a
	// // dialog for the user to
	// // browse files to open)
	// int option = open.showOpenDialog(this); // get the option that the
	// // user selected (approve or
	// // cancel)
	// // NOTE: because we are OPENing a file, we call showOpenDialog~
	// // if the user clicked OK, we have "APPROVE_OPTION"
	// // so we want to open the file
	// if (option == JFileChooser.APPROVE_OPTION) {
	// this.textArea.setText(""); // clear the TextArea before applying
	// // the file contents
	// try {
	// // create a scanner to read the file
	// // (getSelectedFile().getPath() will get the path to the
	// // file)
	// Scanner scan = new Scanner(new FileReader(open
	// .getSelectedFile().getPath()));
	// while (scan.hasNext())
	// // while there's still something to read
	// this.textArea.append(scan.nextLine() + "\n"); // append
	// // the
	// // line
	// // to
	// // the
	// // TextArea
	// } catch (Exception ex) { // catch any exceptions, and...
	// // ...write to the debug console
	// System.out.println(ex.getMessage());
	// }
	// }
	// }

	// and lastly, if the source of the event was the "save" option
	// else if (e.getSource() == this.saveFile) {
	// JFileChooser save = new JFileChooser(); // again, open a file
	// // chooser
	// int option = save.showSaveDialog(this); // similar to the open file,
	// // only this time we call
	// // showSaveDialog instead of showOpenDialog
	// // if the user clicked OK (and not cancel)
	// if (option == JFileChooser.APPROVE_OPTION) {
	// try {
	// // create a buffered writer to write to a file
	// BufferedWriter out = new BufferedWriter(new FileWriter(save
	// .getSelectedFile().getPath()));
	// out.write(this.textArea.getText()); // write the contents of
	// // the TextArea to the
	// // file
	// out.close(); // close the file stream
	// } catch (Exception ex) { // again, catch any exceptions and...
	// // ...write to the debug console
	// System.out.println(ex.getMessage());
	// }
	// }
	// }
	// }

	// the main method, for actually creating our notepad and setting it to
	// visible.
	public static void main(String args[]) {
		Editor app = new Editor();
		app.setVisible(true);
	}

}
