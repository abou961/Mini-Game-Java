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

public abstract class Personnage{
	
	protected int x;
	protected int y;
	protected Image perso;
	protected boolean actif;
	protected String nom;
	public static final int TAILLE_MUR = 75;
	
	
	public Personnage(int a, int b, boolean bo){//le boolean permet de savoir si c'est le personnage actif ou non
		x=a;
		y=b;
		this.actif=bo;
	}
	
	public Personnage(int a, int b){
		this(a,b,true);
	}
	
	public String getNom(){return this.nom;}
	
	public Image getImage(){return perso;}
	
	public int getX(){return x;}
	public int getY(){return y;}
	
	public void setX(int a){this.x=a;}
	public void setY(int a){this.y=a;}
	
	
	public abstract void estStable();//utile pour animations etc.
	public void deplacerH(){ //les déplacements se font de cases en cases
		this.y-=TAILLE_MUR;
	}
	public void deplacerB(){
		this.y+=TAILLE_MUR;
	}
	public void deplacerG()
	{
		this.x-=TAILLE_MUR;
	}
	public void deplacerD(){
		this.x+=TAILLE_MUR;
	}
	
	public void setPos(int a, int b){this.x=a;this.y=b;}
	
	public boolean estActif(){return actif;}
	public void setActif(boolean b){this.actif=b;}
	
	public String toString()//cf toString de Eistein pour excplications du fait qu'on l'ai gardé
	{
		return "\nBonne Chance!";//peu utile ici mais utile pour les futures modifications et améliorations du centre de contrôle notamment.cf
	}
}

