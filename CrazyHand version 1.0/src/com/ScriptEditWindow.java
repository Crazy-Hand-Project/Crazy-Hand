package com;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.MeleeEdit.SaveListener;
import com.scripts.Script;
import com.scripts.ScriptComparator;
import com.scripts.ScriptUtils;

public class ScriptEditWindow extends JFrame implements ActionListener {
	
	public ArrayList<ScriptEditWindowTab> tabs = new ArrayList<ScriptEditWindowTab>();
	
	JMenuBar fileMenu;
	
	JPanel tabList;
	
	JScrollPane scripts;
	JPanel scriptInner;
	JPanel editorShell;
	JButton saveButton;
	
	int currentTab = 0;
	
	//Total size of the full subaction
	int size = 0x0;
	
	JPanel scriptPanel;
	
	public ScriptEditWindow(){
		Dimension windowSize = new Dimension(900, 800);
		this.setPreferredSize(windowSize);
		this.setMinimumSize(getPreferredSize());
		this.setEnabled(true);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Crazy Hand Script Editor");
		editorShell = new JPanel();
		
		editorShell.setPreferredSize(new Dimension(900, 800));
		editorShell.setMinimumSize(editorShell.getPreferredSize());
		editorShell.setLayout(new BoxLayout(editorShell, BoxLayout.Y_AXIS));
		
		tabList = new JPanel();
		tabList.setLayout(new BoxLayout(tabList, BoxLayout.X_AXIS));
		tabList.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 10));
		//this.setJMenuBar(fileMenu);
		createNewTab();
		scriptInner = new JPanel();
		scriptInner.setLayout(new BoxLayout(scriptInner, BoxLayout.PAGE_AXIS));
		
		scripts = new JScrollPane(scriptInner);
		scripts.setPreferredSize(new Dimension(700, 600));
		scripts.setMaximumSize(scripts.getPreferredSize());
		scripts.getVerticalScrollBar().setUnitIncrement(10);

		scriptPanel = new JPanel();
		scriptPanel.setLayout(new BoxLayout(scriptPanel, BoxLayout.PAGE_AXIS));
		
		scriptPanel.add(scripts);
		
		//this.add(Box.createVerticalStrut(100));

		editorShell.add(tabList);
		editorShell.add(scriptPanel);
		this.add(editorShell);
		
		ScriptUtils.updateScripts(getCurrentTab().scriptsInTab);
		this.updateScripts();
		
		FML menuListener = new FML();
		
		fileMenu = new JMenuBar();
			JMenu file = new JMenu("File");
				JMenuItem ntab = new JMenuItem("Create blank subaction");
				ntab.setActionCommand("newSubaction");
				ntab.addActionListener(menuListener);
				file.add(ntab);
				
			fileMenu.add(file);
		
		this.setJMenuBar(fileMenu);
		
		saveButton = new JButton("Save");
		saveButton.setActionCommand("save");
		saveButton.addActionListener(this);
		
		add(saveButton, BorderLayout.PAGE_END);
		
		pack();
	}
	
	public void updateScripts(){
		scriptInner.removeAll();
		int start = ScriptUtils.getLowestPointerInScriptList(getCurrentTab().scriptsInTab);
		int tmp = 0x0;
		
		int loopScript = 0;
		
		System.out.println("Updating script edit window");
		for(int i = 0; i < getCurrentTab().scriptsInTab.size(); i ++){
			
			Script scr = getCurrentTab().scriptsInTab.get(i);
			
			scriptInner.add(scr);
			
			scriptInner.add(scr);
			
			JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
			sep.setBackground(Color.BLUE);
			scriptInner.add(sep);
			// JPanel box = Box.createVerticalStrut(10);
			scriptInner.add(Box.createVerticalStrut(30));
			
			sep = new JSeparator(SwingConstants.HORIZONTAL);
			sep.setBackground(Color.BLUE);
			
			tmp += Event.getEvent(scr.id).length;
		}
		Collections.sort(getCurrentTab().scriptsInTab, new ScriptComparator());
		size = tmp;
		tabs.get(currentTab).updateUI();
		scriptInner.updateUI();
		scripts.updateUI();
		scriptPanel.updateUI();
		pack();
	}
	
	public void createNewTab(){
		ScriptEditWindowTab tab = new ScriptEditWindowTab(this, this.tabs.size());
		this.tabs.add(tab);
		tabList.removeAll();
		for(int i = 0; i < tabs.size(); i ++){
			tabList.add(tabs.get(i));
		}
		this.update(getGraphics());
		tabList.add(Box.createHorizontalGlue());
		tabList.updateUI();
		pack();
	}
	
	public void removeTab(ScriptEditWindowTab t){
		//TODO add unsaved changes check + popup window here
		this.tabs.remove(t);
		tabList.removeAll();
		for(int i = 0; i < tabs.size(); i ++){
			tabs.get(i).id = i;
			tabList.add(tabs.get(i));
		}
		tabList.add(Box.createHorizontalGlue());
		tabList.updateUI();
		pack();
	}
	
	public ScriptEditWindowTab getCurrentTab(){
		return this.tabs.get(currentTab);
	}
	
	public int getScriptTabs(){
		int r = 0;
		
		for(int i = 0; i < this.getComponentCount(); i ++){
			if(this.getComponent(i) instanceof ScriptEditWindowTab){
				r ++;
			}
		}
		
		return r;
	}
	
	public void dispose(){
		MeleeEdit.scriptEditor = null;
		super.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().startsWith("addscript_")){
			String[] spl = e.getActionCommand().split("addscript_");
			this.tabs.get(currentTab).addScriptAt(size+0x4, ScriptUtils.createBaseScript(size+0x4, Integer.parseInt(spl[1])));
			updateScripts();
		}
	}
	
	
	public class FML implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			
			if(cmd=="newSubaction"){
				System.out.println("si");
				createNewTab();
			}
		}
		
	}
	
}
