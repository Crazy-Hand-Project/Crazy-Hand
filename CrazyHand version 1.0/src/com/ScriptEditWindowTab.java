package com;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.scripts.Script;
import com.scripts.ScriptComparator;
import com.scripts.ScriptUtils;

public class ScriptEditWindowTab extends JPanel {
	
	String scriptName = "";
	
	JLabel text;
	JButton button;
	
	public ArrayList<Script> scriptsInTab;
	
	boolean unsavedChanges = false;
	
	public ScriptEditWindow editor;
	
	public int id = 0;
	
	public ScriptEditWindowTab(ScriptEditWindow win, int i){
		super();
		editor = win;
		scriptsInTab = new ArrayList<Script>();
		Dimension tabSize = new Dimension(80,30);
		this.setPreferredSize(tabSize);
		this.setMinimumSize(getPreferredSize());
		this.setMaximumSize(getPreferredSize());
		this.button = new JButton("ERR");

		//setBorder(BorderFactory.createEtchedBorder(1, new Color(0x878787), Color.DARK_GRAY));
		this.scriptName = "New Script";
		this.updateName();
		this.add(button);
		//Script.scripts.clear();
		//Script.number = 1;
			this.addScriptAt(0x0, ScriptUtils.createBaseScript(0x0, Event.getEventPlacementFromName(Event.getEvent(0x0C).name)));
	}
	
	
	public void updateName(){
		this.button.setText(scriptName);
		Dimension tabSize = new Dimension(scriptName.length()*10,30);
		this.setPreferredSize(tabSize);
		this.setMinimumSize(getPreferredSize());
		this.setMaximumSize(getPreferredSize());
		updateUI();
	}
	
	public void addScriptAt(int loc, Script s){
		s.number = scriptsInTab.size();
		s.linkedToCharacterFile = false;
		s.setInEditor(editor);
		s.setStandalone();
		scriptsInTab.add(s);
		updateScripts();
		markChanged();
	}
	
	
	public void removeScriptAt(int loc){
		Script s = null;
		for(int i = 0; i < scriptsInTab.size(); i ++){
			s = scriptsInTab.get(i);
			if(s.location == loc){
				scriptsInTab.remove(s);
				updateScripts();
				markChanged();
				return;
			}
		}
	}
	
	
	public void markChanged() {
		this.unsavedChanges = true;
	}


	public void updateScripts(){
		Collections.sort(scriptsInTab, new ScriptComparator());
	}
	
}
