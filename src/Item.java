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

public class Item {
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	protected Image img ;//image associé
	public int width=60;//taille
	public int height=60;
	protected int y;//position
	protected int x;
	
	public Item()
	{}
	public Item(int a, int b) {	
		y=b;
		x=a;	
	}
	public Item(int a, int b, Image i) {	
		this(a,b);
		img=i;	
	}
	
	public int getX(){return this.x;}
	public int getY(){return this.y;}
	public Image getImage(){return this.img;}
	
	public Rectangle getBounds() {//on utilise la classe getBounds() de Rectangle pour gérer les collisions (code plus simple que de gérer nous mêmes les paramètres de tailles/positions...
		return new Rectangle((int)this.x, (int)this.y, width, height);
	}
	public void setImage(Image a){this.img = a;}
}

