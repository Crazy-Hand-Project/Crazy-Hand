package com;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DropDownMenu extends JFrame implements MouseListener {//Still VERY buggy.
																   //Not even sure if this will be used.
	
	String[] options;
	public DDMButton[] buttons;
	public DropDownMenu(String[] s, String[] s2)
	{
		super();
		setUndecorated(true);
		setVisible(true);
		setEnabled(true);
		
		int txtLength = 0;
		
		this.options = s;
		int tx = 0;
		int ty = 12;
		buttons = new DDMButton[options.length];
		for(int i = 0; i < options.length; i ++)
		{
			System.out.println(options[i]);
			buttons[i] = new DDMButton(options[i], tx, ty, s2[i],this);
			if(options[i].length() > txtLength){
				txtLength=options[i].length();
			}
			ty+=18;
		}
		this.setFocusable(true);
		this.requestFocus();
		this.addMouseListener(this);
		
		setSize(txtLength*8, s.length*18+2);
		int mx = MouseInfo.getPointerInfo().getLocation().x;
		int my = MouseInfo.getPointerInfo().getLocation().y;
		setLocation(mx-(txtLength*6)/2, my-10);
		
		System.out.println(options.length);
	}
	
	int ticksExisted;
	
	public void paint(Graphics g)
	{
		super.paint(g);
		g.setColor(Color.BLACK);
		g.drawRect(this.getX()-4, this.getY()-4, this.getX()+this.getWidth()+4, this.getY()+this.getHeight()+4);
		g.setColor(Color.LIGHT_GRAY);
		g.drawRect(this.getX(),this.getY(),this.getWidth(),this.getHeight());
		g.setColor(Color.BLACK);
		for(int i = 0; i < buttons.length; i ++){
			buttons[i].paint(g);
		}
	}
	
	public void update(Graphics g){
		super.update(g);
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		Rectangle mouse = new Rectangle(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y,8,8);
		Rectangle r = new Rectangle(this.getX(),this.getY(),this.getWidth(),this.getHeight());

			for(int i = 0; i < buttons.length; i ++)
			{
				DDMButton button = buttons[i];
				Rectangle bt = new Rectangle(button.x-2,button.y-2,button.name.length()*8+2,button.y+2);
				if(bt.intersects(mouse)){
					button.highlighted=true;
				}
				else{
					button.highlighted=false;
				}
			}
			
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		Rectangle mouse = new Rectangle(MouseInfo.getPointerInfo().getLocation());
		Rectangle r = new Rectangle(this.getX(),this.getY(),this.getWidth(),this.getHeight());
		if(!r.intersects(mouse)){
			this.dispose();
		}
		else{
			for(int i = 0; i < buttons.length; i ++)
			{
				DDMButton button = buttons[i];
				Rectangle bt = new Rectangle(button.x-2,button.y-2,button.name.length()*8+2,button.y+2);
				if(bt.intersects(mouse)){
					button.highlighted=true;
				}
				else{
					button.highlighted=false;
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}
	
	public void onButtonPressed(String action) {
		
	}
	
	class DDMButton extends JComponent {
		String name;
		String action;
		int x;
		int y;
		boolean highlighted;
		DropDownMenu menu;
		public DDMButton(String s, int i, int j, String a, DropDownMenu m)
		{
			this.name=s;this.x=i;this.y=j;this.action=a;menu=m;
		}
		
		public void paint(Graphics g)
		{
			g.setColor(this.highlighted ? Color.BLUE : Color.LIGHT_GRAY);
			g.drawRect(x-2, y-2, name.length()*8+2, y+2);
			g.setColor(Color.black);
			g.drawString(name, x, y);
		}
	}	
	
}
