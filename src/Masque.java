import java.lang.Math; 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Masque extends Item {
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Image img = toolkit.getImage("../Image/Item/masque.gif");
	private boolean visible;
	
	public Masque(int a, int b, boolean v)
	{
		super(a,b);
		this.width=100;
		this.height=60;
		visible=v;
	}
	
	public boolean getVisible(){return visible;}
	public void setVisible(boolean a){this.visible=a;}//on rend visible le masque 
	
	public Image getImage(){return this.img;}
}

