package com;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import com.alee.laf.WebLookAndFeel;
import com.panels.AnimationNode;
import com.panels.AnimationPanel;
import com.panels.AttributePanel;
import com.panels.ToolBar;
import com.panels.FSMPanel;
import com.panels.MoveLogicEditPane;
import com.panels.PlCoEditPane;
import com.panels.PlCoPanel;
import com.panels.ProjectileEditPane;
import com.panels.RestorePanel;
import com.panels.SpecialAttributePanel;
import com.panels.SubactionPanel;
import com.scripts.Script;
import com.scripts.ScriptComparator;
import com.scripts.ScriptUtils;
import com.scripts.SoundScript;
import com.scripts.SynchronousScript;


public class MeleeEdit extends JPanel implements ActionListener {
	

	public static final int
			MENU_ATTRIBUTES = 0, MENU_SPECIAL_ATTRIBUTES = 1,
			MENU_SUBACTIONS_COMMON = 2, MENU_SUBACTIONS_ALL = 3,
			MENU_PROJECTILE_EDIT_CHARACTER = 4,
			MENU_ANIMATION = 5, MENU_FRAME_SPEED_MODIFIERS = 6,
			MENU_COMMON_DATA = 7, MENU_MOVE_LOGIC = 8, MENU_OTHER = 9;

	public static int selected = 0, selectedMenu = MENU_FRAME_SPEED_MODIFIERS;

	

	public static JFrame frame;


	public static ToolBar toolBar;
	public static SubactionPanel subactionPanel;
	public static AttributePanel attributePanel;
	public static SpecialAttributePanel specialAttributePanel;
	public static RestorePanel restorePane;
	public static AnimationPanel animationPanel;
	public static FSMPanel fsmPanel;
	public static ProjectileEditPane projectilePanel;
	public static PlCoPanel plCoPanel;
	public static MoveLogicEditPane subactionInterruptPanel;
	
	public static FileMenu fileMenu;
	
	

	public MeleeEdit() {
		super(new BorderLayout());

		//TODO decide what does and doesn't need to be initialized
		toolBar = new ToolBar();
		attributePanel = new AttributePanel();
		//projectilePanel = new ProjectileEditPane();
		restorePane = new RestorePanel();
		//plCoPanel = new PlCoEditPane();
		//specialAttributePanel = new SpecialAttributePanel();
		//animationPanel = new AnimationPanel();
		//subactionPanel = new SubactionPanel();
		//fsmPanel = new FSMPanel();

		fileMenu = new FileMenu();
		
		
		
		
		hardUpdate();
	
	}
	
	public void save(){
		try {
			FileIO.loadedISOFile.reload();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return;

		}
		
		switch(selectedMenu){
		case MENU_ATTRIBUTES:
			attributePanel.save();
			break;
		case MENU_SPECIAL_ATTRIBUTES:
			specialAttributePanel.save();
			break;
		case MENU_SUBACTIONS_COMMON:
			FileIO.init();
			for (Script script : Script.scripts) {
				script.save();
			}

			FileIO.init();
			MeleeEdit.subactionPanel.readScripts();
			frame.pack();
			break;
		case MENU_SUBACTIONS_ALL:
			FileIO.init();
			for (Script script : Script.scripts) {
				script.save();
			}

			FileIO.init();
			MeleeEdit.subactionPanel.readScripts();
			frame.pack();
			break;
		case MENU_PROJECTILE_EDIT_CHARACTER:
			projectilePanel.save();
			break;
		case MENU_ANIMATION:
			for (AnimationNode n : animationPanel.nodes) {
				n.save();
			}
			break;
		case MENU_FRAME_SPEED_MODIFIERS:
			fsmPanel.save();
			break;
		case MENU_COMMON_DATA:
			plCoPanel.save();
			break;
		case MENU_MOVE_LOGIC:
			try {
				subactionInterruptPanel.applyChanges();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;
		case MENU_OTHER:

			break;
		}

		

		try {
			FileIO.isoFileSystem.replaceFile(FileIO.isoFileSystem.getCurrentFile(),FileIO.f.array());

		} catch (IOException e2) {
			e2.printStackTrace();
		}

		FileIO.loadedISOFile.close();

		Options.saveOptions();
		System.out.println("Save Complete");
		
		
		MeleeEdit.toolBar.setButton(false);
		
		frame.pack();
	}
	
	//this function updates the data of the selected menu but does completely generate new panels,
	public void softUpdate(){
		switch(selectedMenu){
		case MENU_ATTRIBUTES:
			attributePanel.update();
			break;
		case MENU_SPECIAL_ATTRIBUTES:
			hardUpdate();
			break;
		case MENU_SUBACTIONS_COMMON:
			subactionPanel.updateSubactions();
			break;
		case MENU_SUBACTIONS_ALL:
			subactionPanel.updateSubactions();
			break;
		case MENU_PROJECTILE_EDIT_CHARACTER:
			//nothing
			break;
		case MENU_ANIMATION:
			animationPanel.updateAnimations();
			break;
		case MENU_FRAME_SPEED_MODIFIERS:
			//nothing
			break;
		case MENU_COMMON_DATA:
			//nothing
			break;
		case MENU_MOVE_LOGIC:
			if(subactionInterruptPanel!=null){
				subactionInterruptPanel.refresh();
			}
			break;
		case MENU_OTHER:
			//nothing
			break;
		}
		frame.pack();
		FileIO.loadedISOFile.close();
		System.out.println("Soft Update");
	}
	
	//this function will completely refresh panels, creating new objects. Much slower, use sparingly.
	public void hardUpdate(){
		removeAll();
		add(toolBar, BorderLayout.PAGE_START);

		switch(selectedMenu){
			case MENU_ATTRIBUTES:
				add(attributePanel, BorderLayout.CENTER);
				attributePanel.update();
				break;
			case MENU_SPECIAL_ATTRIBUTES:
				specialAttributePanel = new SpecialAttributePanel();
				add(specialAttributePanel, BorderLayout.CENTER);
				break;
			case MENU_SUBACTIONS_COMMON:
				subactionPanel = new SubactionPanel();
				add(subactionPanel, BorderLayout.CENTER);
				subactionPanel.update();
				break;
			case MENU_SUBACTIONS_ALL:
				subactionPanel = new SubactionPanel();
				add(subactionPanel, BorderLayout.CENTER);
				subactionPanel.update();
				break;
			case MENU_PROJECTILE_EDIT_CHARACTER:
				projectilePanel = new ProjectileEditPane();
				add(projectilePanel);
				break;
			case MENU_ANIMATION:
				animationPanel = new AnimationPanel();
				add(animationPanel, BorderLayout.CENTER);
				//animationPanel.updateAnimations();
				break;
			case MENU_FRAME_SPEED_MODIFIERS:
				fsmPanel = new FSMPanel();
				add(fsmPanel, BorderLayout.CENTER);
				fsmPanel.updateUI();
				updateUI();
				break;
			case MENU_COMMON_DATA:
				plCoPanel = new PlCoPanel();
				add(plCoPanel);
				break;
			case MENU_MOVE_LOGIC:
				subactionInterruptPanel = new MoveLogicEditPane();
				add(subactionInterruptPanel);
				break;
			case MENU_OTHER:
				add(restorePane, BorderLayout.CENTER);
				break;
		}
		
		Options.saveOptions();
		frame.pack();
	}
	
	

	

	public static MeleeEdit contentPane;

	public static void main(String[] args) throws IOException {
		
		//Debug.act();//TODO remove!

		
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		    	//System.out.println(info.getName());
		        if ("Nimbus".equals(info.getName())) {
		            //UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		}
		
		WebLookAndFeel.install ();
		
		Options.loadOptions();
		FileIO.loadISOFile();
		AttributePanel.initAttributes();

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frame = new JFrame();
				
				updateTitle(FileIO.loadedISOFile.getChosenISOFile().getName());
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				ImageIcon img = new ImageIcon("img/hand.png");
				frame.setIconImage(img.getImage());

				
				contentPane = new MeleeEdit();
				contentPane.setOpaque(true);
				frame.setContentPane(contentPane);
				

				frame.setJMenuBar(fileMenu);//TODO should this go here?
			
				//frame.setMaximizedBounds(new Rectangle(50,50,50,45));
				frame.setMaximumSize(new Dimension(600, 1000));
				frame.setPreferredSize(new Dimension(600, 700));
		        frame.setMinimumSize(new Dimension(600, 300));
				frame.pack();
				frame.setVisible(true);
			}
		});

		Options.saveOptions();
		
		

	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// not used, but required

	}
	
	


	public static void updateTitle(String isoPath) {
		frame.setTitle("Crazy Hand v" + Config.VERSION + " [" + isoPath + "]");
	}

}