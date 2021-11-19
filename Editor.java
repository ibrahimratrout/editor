package edu.najah.cap;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


class FileManager{
	public static void saveAs(String dialogTitle,String textPanelStr) {
		JFileChooser dialog = new JFileChooser(System.getProperty("user.home"));
		dialog.setDialogTitle(dialogTitle);
		int dontSave= dialog.showSaveDialog(this);
		if (dontSave != 0)
			return;
		file = dialog.getSelectedFile();
		try (PrintWriter writer = new PrintWriter(file);){
			writer.write(textPanelStr);
			changed = false;
			setTitle("Editor - " + file.getName());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}


	public static void saveAsText(String dialogTitle,String textPanelStr) {
		JFileChooser dialog = new JFileChooser(System.getProperty("user.home"));
		dialog.setDialogTitle(dialogTitle);
		int result = dialog.showSaveDialog(this);
		if (result != 0)
			return;
		file = dialog.getSelectedFile();
		try (PrintWriter writer = new PrintWriter(file);){
			writer.write(textPanelStr);
			changed = false;
			setTitle("Save as Text Editor - " + file.getName());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}

@SuppressWarnings("serial")
public class Editor extends JFrame implements ActionListener, DocumentListener {

	public static  void main(String[] args) {
		new Editor();
	}

	private JEditorPane textPanel;
	private JMenuBar menu;
	private JMenuItem copy, paste, cut, move, find, sall;
	private JMenuItem n,open,save,saveas,quit;
	private boolean changed = false;
	private File file;

	public Editor() {
		super("Editor");
		textPanel = new JEditorPane();
		add(new JScrollPane(textPanel), "Center");
		textPanel.getDocument().addDocumentListener(this);
		BuildMenu();
		setSize(500, 500);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void BuildMenu() {
		menu = new JMenuBar();
		setJMenuBar(menu);
		buildFileMenu();
		buildEditMenu();
	}
	
	private void buildNew(){
		n = new JMenuItem("New");
		n.setMnemonic('N');
		n.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		n.addActionListener(this);
	}
	
	private void buildOpen(){
		open = new JMenuItem("Open");
		open.addActionListener(this);
		open.setMnemonic('O');
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
	}

	private void buildSave(){
		save = new JMenuItem("Save");
		save.setMnemonic('S');
		save.addActionListener(this);
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
	}
	
	private void buildSaveAs(){
		saveas = new JMenuItem("Save as...");
		saveas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		saveas.addActionListener(this);
	}
	
	private void buildQuit(){
		quit = new JMenuItem("Quit");
		quit.addActionListener(this);
		quit.setMnemonic('Q');
		quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
	}
	
	private void buildCut(){
		cut = new JMenuItem("Cut");
		cut.addActionListener(this);
		cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
		cut.setMnemonic('T');
	}
	
	private void buildCopy(){
		copy = new JMenuItem("Copy");
		copy.addActionListener(this);
		copy.setMnemonic('C');
		copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
	}
	
	private void buildPaste(){
		paste = new JMenuItem("Paste");
		paste.addActionListener(this);
		paste.setMnemonic('P');
		paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
	}

	private void buildFind(){
		find = new JMenuItem("Find");
		find.setMnemonic('F');
		find.addActionListener(this);
		find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
	}
	
	private void buildSelectAll(){
		sall = new JMenuItem("Select All");
		sall.setMnemonic('A');
		sall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
		sall.addActionListener(this);
	}
	
	private void buildMove(){
		move = new JMenuItem("Move");
		move.setMnemonic('M');
		move.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
		move.addActionListener(this);
	}
	
	private void buildFileMenu() {
		JMenu file = new JMenu("File");
		file.setMnemonic('F');
		menu.add(file);
		this.buildNew();
		file.add(n);
		this.buildOpen();
		file.add(open);
		this.buildSave();
		file.add(save);
		this.buildSaveAs();
		file.add(saveas);
		this.buildQuit();
		file.add(quit);
	}

	private void buildEditMenu() {
		JMenu edit = new JMenu("Edit");
		menu.add(edit);
		edit.setMnemonic('E');

		this.buildCut();
		edit.add(cut);
	
		this.buildCopy();
		edit.add(copy);
	
		this.buildPaste();
		edit.add(paste);
		//move 
		/*
		this.buildMove();
		edit.add(move);
		*/
		
		this.buildFind();
		edit.add(find);
		
		this.buildSelectAll();
		edit.add(sall);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		switch(action){
			case "Quit":
				System.exit(0);
				break;
			case "Open":
				this.loadFile();
				break;
			case "Save":
				int DontSave = 0;
				if (changed) {
					DontSave = JOptionPane.showConfirmDialog(null, "The file has changed. You want to save it?", "Save file",JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				}
				
				if (DontSave != 1) {
					if (file == null) {
						saveAs("Save");
					} else {
						String text = textPanel.getText();
						System.out.println(text);
						try (PrintWriter writer = new PrintWriter(file);){
							if (!file.canWrite())
								throw new Exception("Cannot write file!");
							writer.write(text);
							changed = false;
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
				break;
			case "New":
				if (changed) {
				 
					int DontSave = JOptionPane.showConfirmDialog(null, "The file has changed. You want to save it?", "Save file",JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					
					if (DontSave == 1)
						return;
					if (file == null) {
						FileManager.saveAs("Save");
						return;
					}
					String text = textPanel.getText();
					System.out.println(text);
					try (PrintWriter writer = new PrintWriter(file);){
						if (!file.canWrite())
							throw new Exception("Cannot write file!");
						writer.write(text);
						changed = false;
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				file = null;
				textPanel.setText("");
				changed = false;
				setTitle("Editor");
				break;
			case "Save as...":
				FileManager.saveAs("Save as...");
				break;
			case "Select all":
				textPanel.selectAll();
				break;
			case "Copy":
				textPanel.copy();
				break;
			case "Cut":
				textPanel.cut();
				break;
			case "Paste":
				textPanel.paste();
				break;
			case "Find":
				FindDialog find = new FindDialog(this, true);
				find.showDialog();
				break;
		}
	}


	private void loadFile() {
		JFileChooser dialog = new JFileChooser(System.getProperty("user.home"));
		dialog.setMultiSelectionEnabled(false);
		try {
			int result = dialog.showOpenDialog(this);
			
			if (result == 1)
				return;
			if (result == 0) {
				if (changed){
					
					if (changed) {
						int ans = JOptionPane.showConfirmDialog(null, "The file has changed. You want to save it?", "Save file",JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);//0 means yes and no question and 2 mean warning dialog
						if (ans == 1)
							return;
					}
					if (file == null) {
						saveAs("Save");
						return;
					}
					String text = textPanel.getText();
					System.out.println(text);
					try (PrintWriter writer = new PrintWriter(file);){
						if (!file.canWrite())
							throw new Exception("Cannot write file!");
						writer.write(text);
						changed = false;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				file = dialog.getSelectedFile();
				
				StringBuilder rs = new StringBuilder();
				try (	FileReader fr = new FileReader(file);		
						BufferedReader reader = new BufferedReader(fr);) {
					String line;
					while ((line = reader.readLine()) != null) {
						rs.append(line + "\n");
					}
				} catch (IOException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Cannot read file !", "Error !", JOptionPane.ERROR_MESSAGE);//0 means show Error Dialog
				}
				
				textPanel.setText(rs.toString());
				changed = false;
				setTitle("Editor - " + file.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}


	@Override
	public void insertUpdate(DocumentEvent e) {
		changed = true;
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		changed = true;
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		changed = true;
	}

}