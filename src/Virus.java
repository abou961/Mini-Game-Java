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

public class Virus extends Item {
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Image img = toolkit.getImage("../Image/Item/virus.png");
	private boolean direction;//true=gauche/haut et false=droite/bas, les virus ont 2 directions possibles
	private boolean dejaTouche=false;
	private int type;
	private int dx=-6;
	private int dy=-6;
	
	public Virus(int a, int b, int t)
	{
		super(a,b);
		this.direction=true;
		type=t;
	}
	
	public void deplacement(){//2 types de virus, ceux qui se déplacent horizontalement, et ceux qui se déplacent verticalement
		if(type==1)//horizontaux
		{
			if(this.x<=15){//gestions des bords(ne pas dépasser la taille de l'écran)
				this.direction=false;
				dx=6;
			}
			if(this.x>=815){
				this.direction=true;
				dx=-6;
			}
			this.x+=dx;
		}
		if(type==2)//verticaux
		{
			if(this.y<=15){
				this.direction=false;
				dy=6;
			}
			if(this.y>=570){
				this.direction=true;
				dy=-6;
			}
			this.y+=dy;
		}
		
	}
	public Image getImage(){return this.img;}
	public boolean dansEcran(){return ((this.x>-15)&&(this.x<815));}
	public void touche(){this.dejaTouche=true;}
	public boolean estTouche(){return this.dejaTouche;}

}

