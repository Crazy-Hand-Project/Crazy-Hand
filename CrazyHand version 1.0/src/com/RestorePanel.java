package com;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RestorePanel extends JPanel{
	public JButton btn102,btn102All,btn101,btn101All,btn100,btn100All,btnPAL,btnPALAll, randoBtn;
	
	
	
	public RestorePanel(){
		super();
		this.add(Box.createVerticalStrut(15));
		
		btn102 = new JButton("Restore This Character to v1.02 Defaults");
	    btn102.setActionCommand("Restore");
	    btn102.addActionListener(new L102());
	    this.add(btn102);
	    this.add(Box.createVerticalStrut(15));
	    
	    btn102All = new JButton("Restore ALL Characters to v1.02 Defaults");
	    btn102All.setActionCommand("Restore");
	    btn102All.addActionListener(new L102All());
	    this.add(btn102All);
	    
	    
	    btn101 = new JButton("Restore This Character to v1.01 Defaults");
	    btn101.setActionCommand("Restore");
	    //102Btn.addActionListener(new 102BtnListener());
	    //this.add(btn101);
	    
	    btn101All = new JButton("Restore ALL Characters to v1.01 Defaults");
	    btn101All.setActionCommand("Restore");
	    //102Btn.addActionListener(new 102BtnListener());
	    //this.add(btn101All);
	    
	    btn100 = new JButton("Restore This Character to v1.00 Defaults");
	    btn100.setActionCommand("Restore");
	    //102Btn.addActionListener(new 102BtnListener());
	    //this.add(btn100);
	    
	    btn100All = new JButton("Restore ALL Characters to v1.00 Defaults");
	    btn100All.setActionCommand("Restore");
	    //102Btn.addActionListener(new 102BtnListener());
	    //this.add(btn100All);
	    
	    btnPAL = new JButton("Restore This Character to   PAL Defaults");
	    btnPAL.setActionCommand("Restore");
	    //102Btn.addActionListener(new 102BtnListener());
	    //this.add(btnPAL);
	    
	    btnPALAll = new JButton("Restore ALL Characters to   PAL Defaults");
	    btnPALAll.setActionCommand("Restore");
	    //102Btn.addActionListener(new 102BtnListener());
	    //this.add(btnPALAll);
	    
	    this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	    
	    
	    randoBtn = new JButton("Randomize ALL Characters");
	    randoBtn.setActionCommand("Restore");
	    randoBtn.addActionListener(new LRando());
	    
	    
	    this.add(Box.createVerticalStrut(45));
	    
	    this.add(randoBtn);
	    this.add(Box.createVerticalStrut(15));
	    this.add(new JLabel("  Note: This will take approximately 10-20 seconds to complete and there's no visual cue of progress yet."));
	    
	    
	    
	    this.setPreferredSize(new Dimension(700,200));
	}
	
	
    
    
    

}

class L102 implements ActionListener {
    public void actionPerformed(ActionEvent e) {
    	try {

			File backupCopyFile = new File("def/102/Pl"
					+ Character.characters[MeleeEdit.selected].id + ".dat");
			
			FileIO.loadedISOFile.reload();

			FileIO.isoFileSystem.replaceFile(
					FileIO.isoFileSystem.getCurrentFileInfo(),
					Files.readAllBytes(backupCopyFile.toPath()));

			MeleeEdit.refreshData();

		} catch (IOException e1) {
			e1.printStackTrace();
		}


    	//FileIO.copy("def/102/Pl" + Character.characters[MeleeEdit.selected].id + ".dat","root/Pl" + Character.characters[MeleeEdit.selected].id + ".dat");
    	System.out.println("Restore Complete!");
    }
}

class L102All implements ActionListener {
    public void actionPerformed(ActionEvent e) {
    	for (int i = 0; i < Character.characters.length; i++) {

			File backupCopyFile = new File("def/102/Pl"
					+ Character.characters[i].id + ".dat");

			try {
				
				FileIO.loadedISOFile.reload();
				FileIO.isoFileSystem.replaceFile(
						FileIO.isoFileSystem.getFileInfo("Pl"
								+ Character.characters[i].id + ".dat"),
						Files.readAllBytes(backupCopyFile.toPath()));

			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
		MeleeEdit.refreshData();
    }

    /*    for(int i = 0; i < Character.characters.length; i++)
        	FileIO.copy("def/102/Pl" + Character.characters[i].id + ".dat","root/Pl" + Character.characters[i].id + ".dat");
        //FileIO.declareAnims();
        System.out.println("Restore All Complete!");
    }
    /*/
}

class LRando implements ActionListener {
    public void actionPerformed(ActionEvent e) {

        
    	FileIO.randoTize();
    }
}
