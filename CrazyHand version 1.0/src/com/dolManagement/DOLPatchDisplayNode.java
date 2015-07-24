package com.dolManagement;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

//Currently unused. Will be polished and implemented once there is more work done on a dol with a gecko codehandler built in.

public class DOLPatchDisplayNode extends JPanel implements ActionListener{
	
	DOLPatch patch;
	JTextField name;
	JButton btn;
	DOLPatchManager parent;
	
	public JCheckBox shouldApply;
	
	public DOLPatchDisplayNode(DOLPatchManager pm, DOLPatch p) {
		super();
		this.parent = pm;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		patch=p;
		shouldApply = new JCheckBox();
		shouldApply.setSelected(true);
		name = new JTextField(p.name);
		
		btn = new JButton("Edit...");
		btn.setActionCommand("editcode");
		btn.addActionListener(this);
		
		Box b = Box.createHorizontalBox();
		b.add(btn);
		b.add(new JLabel(patch.name));
		b.add(shouldApply);
		
		this.add(b);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String cmd = arg0.getActionCommand();
		if(cmd=="editcode"){
			JFrame frame = new JFrame();
			frame.setTitle("Edit code");
			Dimension dim = new Dimension(800,600);
			frame.setPreferredSize(dim);
			frame.setMaximumSize(dim);
			frame.setMinimumSize(dim);
			frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
			
			DOLPatchCreationPane node = new DOLPatchCreationPane(parent,true);
			node.patchName.setText(patch.name);
			
			String newvalopt = "";
			
			for(int bt : patch.newValues[0]){
				newvalopt += Integer.valueOf(""+bt,16);
			}
			
			node.code.setText(newvalopt);
			
			System.out.println(newvalopt);
			
			frame.add(node);
			frame.setVisible(true);
		}
	}

	public void applyPatch() {
		if(shouldApply.isSelected()){
			patch.applyPatch();
		}
	}

}
