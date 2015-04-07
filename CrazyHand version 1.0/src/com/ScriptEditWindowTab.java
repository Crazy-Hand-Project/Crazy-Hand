package com;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
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
		
		this.id = i;
		
		editor = win;
		scriptsInTab = new ArrayList<Script>();
		Dimension tabSize = new Dimension(80,34);
		this.setPreferredSize(tabSize);
		this.setMinimumSize(getPreferredSize());
		this.setMaximumSize(getPreferredSize());
		this.button = new JButton("ERR");
		this.button.setOpaque(false);
		button.setBackground(new Color(0xEBEBEB));
		//setBorder(BorderFactory.createEtchedBorder(1, new Color(0x878787), Color.DARK_GRAY));
		this.scriptName = "New Script (" + id + ")";
		this.updateName();
		button.addActionListener(editor);
		button.setActionCommand("tab_"+this.id);
		this.add(button);
		button.setBorder(BorderFactory.createEmptyBorder());
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
		s.setStandalone(this.isLinkedToCharacter());
		scriptsInTab.add(s);
		updateScripts();
		markChanged();
	}
	
	boolean linkedToCharacter = false;
	Character linkedCharacter = null;
	
	int subactionLocation = 0x0;
	
	public ScriptEditWindowTab setLinkedToSubaction(int loc, Character c){
		this.linkedCharacter = c;
		this.linkedToCharacter = true;
		this.subactionLocation = loc;
		updateScripts();
		return this;
	}
	
	public boolean isLinkedToCharacter(){
		return linkedToCharacter;
	}
	
	public void setName(String s){
		this.scriptName = s;
		this.updateName();
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
	
	public ArrayList<Script> cloneScriptArray(ArrayList<Script> toClone){
		ArrayList<Script> result = new ArrayList<Script>();
		
		for(Script script : toClone){
			
			result.add(script);
		}
		
		return result;
	}


	public void updateScripts(){
		//ScriptUtils.updateScripts(scriptsInTab);
		if(linkedToCharacter){
			FileIO.init(linkedCharacter.getPlaceInArray(), subactionLocation);
			boolean flag = true;
			for(int i = 0; i < scriptsInTab.size(); i ++){
				Script scr = scriptsInTab.get(i);
				if(!scr.linkedToCharacterFile){
					flag = false;
				}
			}
			FileIO.readScripts(scriptsInTab);
			for (Script script : scriptsInTab) {
				script.save();
			}
			
			if(!flag){
				FileIO.readScripts(scriptsInTab);
				//scriptsInTab = cloneScriptArray(Script.scripts);
				for(int i = 0; i < scriptsInTab.size(); i ++){
					//if(!scriptsInTab.get(i).linkedToCharacterFile){
						scriptsInTab.get(i).setInEditor(editor);
						scriptsInTab.get(i).setLinkedToCharacterSubaction(linkedCharacter, subactionLocation);
						//scriptsInTab.get(i).arrayPlacement = i;
						scriptsInTab.get(i).updateData();
						scriptsInTab.get(i).updateScriptBoxInfo();
					if(scriptsInTab.get(i).subactionOffset != subactionLocation){
						System.out.println("Something went wrong loading scripts in a tab! :(");
						System.out.println("Details for nerds: Linked character in tab: " + linkedCharacter.name + " Linked character in script: " + scriptsInTab.get(i).linkedCharacter.name + System.lineSeparator() +
										   "Subaction location in tab: " + subactionLocation + " Subaction location in script: " + scriptsInTab.get(i).linkedCharacter.name + System.lineSeparator() +
										   "If this is a frequent issue, please give us your error logs and a description about your actions leading up to this error on github or smashboards."
										  );
					}
				}
			}
			
			

			FileIO.init(linkedCharacter.getPlaceInArray(), subactionLocation);
			FileIO.readScripts(scriptsInTab);
			
		}
		
		Collections.sort(scriptsInTab, new ScriptComparator());
	}


	public void refresh() {
		if(editor.getCurrentTab() == this){
			updateScripts();
			updateName();
			//button.setBackground(Color.BLUE);
			button.setBorder(BorderFactory.createSoftBevelBorder(1, getBackground(), getForeground()));
		}
		else{
			//button.setBackground(Color.DARK_GRAY);
			button.setBorder(BorderFactory.createEmptyBorder());
		}
	}
	
}
