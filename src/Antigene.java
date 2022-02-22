
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

public class Antigene extends Item{
	private int x;//position
	private int y;
	public static int width=40;
	public static int height=40;
	
	private boolean estDepart=false;
	private boolean toucheUnVirus=false;
	
	public Antigene antiPrec;
	
	public Antigene (int a, int b){
		x=a;
		y=b;
	}
	
	public Antigene (int a, int b, Antigene an){
		x=a;
		y=b;
		antiPrec=an;
	}
	
	public boolean enContactVirus(Virus v)
	{
		boolean a =((this.x+width<(v.getX()+v.width) && this.x+width>(v.getX()-v.width))
				  &&(this.y+height<(v.getY()+v.height) && this.y+height>(v.getY()-v.height))); 
		return a;
	}
	public Rectangle getBounds() {
		return new Rectangle((int)this.x, (int)this.y, width, height);
	}
	
	public void setPos(int a, int b){
		x=a;
		y=b;
	}
	
	public void deviensDepart(){//point de départ qui ne doit pas toucher l'obstacle
		this.estDepart=true;
	}
	
	public boolean estDepart(){
		return estDepart;
	}
	
	public boolean toucheUnVirus()
	{
		return toucheUnVirus;
	}
	
	public void aToucheUnVirus(){
		this.toucheUnVirus=true;
	}
	
	public void setX(int a){this.x=a;}
	public void setY(int a){this.y=a;}
	public int getX(){return this.x;}
	public int getY(){return this.y;}
	public void chute()
	{
		y+=5;
	}
}

