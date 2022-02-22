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

public class Map {
	
	private int[][] tab={{1,1,1,1,1,1,1,1,1,1,1},{1,1,1,1,1,4,4,4,1,1,1},{1,1,1,1,1,0,0,0,1,4,1},{1,1,1,0,0,0,0,0,1,2,1},{1,1,0,0,0,1,2,1,1,0,1},{1,0,0,0,0,0,0,0,1,1,1},{1,0,0,0,1,1,0,0,0,0,1},{1,1,0,0,0,0,1,0,1,0,1},{1,1,0,1,0,0,2,0,2,0,1},{1,1,0,0,0,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1,1,1}};
	private static final int[][] tabFinal={{1,1,1,1,1,1,1,1,1,1,1},{1,1,1,1,1,4,4,4,1,1,1},{1,1,1,1,1,0,0,0,1,4,1},{1,1,1,0,0,0,0,0,1,2,1},{1,1,0,0,0,1,2,1,1,0,1},{1,0,0,0,0,0,0,0,1,1,1},{1,0,0,0,1,1,0,0,0,0,1},{1,1,0,0,0,0,1,0,1,0,1},{1,1,0,1,0,0,2,0,2,0,1},{1,1,0,0,0,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1,1,1}};
	//cf partie reiTab()
	
	public Map(){	
	}
	
	public Map(int [][] t){
		this.tab=t;		
	}
	
	public int[][] getTab(){return this.tab;}
	public void reiTab(){
		int[][] tabInit2 = {{1,1,1,1,1,1,1,1,1,1,1},{1,1,1,1,1,4,4,4,1,1,1},{1,1,1,1,1,0,0,0,1,4,1},{1,1,1,0,0,0,0,0,1,2,1},{1,1,0,0,0,1,2,1,1,0,1},{1,0,0,0,0,0,0,0,1,1,1},{1,0,0,0,1,1,0,0,0,0,1},{1,1,0,0,0,0,1,0,1,0,1},{1,1,0,1,0,0,2,0,2,0,1},{1,1,0,0,0,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1,1,1}};
	/*ce choix peut paraitre étonnant mais le probleme si nous avions poser un final static est que au bout d'une reinitialisation, le tableau était susceptible d'être modifié par 
	la classe FenetreJeu, j'ai laissé en commentaire si vous vouliez essayer*/
		//this.tab=tabFinal;
		this.tab=tabInit2;
	}
	
	public int nombreCaseRestante()
	{
		int compteur=0;
		for(int i=0; i<this.tab.length; i++)
		{
			for(int j=0; j<tab[0].length; j++)
			{
				if(this.tab[i][j]==4)
				{compteur++;}
			}
		}
		return compteur;
	}
	
	public boolean versBas(int x, int y){return (this.tab[x][y+1]==0 || this.tab[x][y+1]==4);}//dans le cas ou le deplacement ne concerne pas une case a deplacer
	public boolean versHaut(int x, int y){return (this.tab[x][y-1]==0 || this.tab[x][y-1]==4);}
	public boolean versDroite(int x, int y){return (this.tab[x+1][y]==0 || this.tab[x+1][y]==4);}
	public boolean versGauche(int x, int y){return (this.tab[x-1][y]==0 || this.tab[x-1][y]==4);}
	
	
	public boolean PousserVersBas(int x, int y, Personnage p){// dans le cas ou le prochain déplacement du joueur permet de pousser une caisse
		if(this.tab[x][y+2]==0 && this.tab[x][y+1]==2)
		{
			this.tab[x][y+1]=0;
			this.tab[x][y+2]=2;
			p.deplacerB();
		}
		
		if(this.tab[x][y+2]==4 && this.tab[x][y+1]!=5)
		{
			this.tab[x][y+1]=0;
			this.tab[x][y+2]=5;
			p.deplacerB();
		}
		if(this.tab[x][y+1]==5 && this.tab[x][y+2]==0)
		{
			this.tab[x][y+1]=4;
			this.tab[x][y+2]=2;
			p.deplacerB();
		}
		if(this.tab[x][y+1]==5 && this.tab[x][y+2]==4)
		{
			this.tab[x][y+1]=4;
			this.tab[x][y+2]=5;
			p.deplacerB();
		}
		return (this.tab[x][y+2]==0);
	}
	public boolean PousserVersHaut(int x, int y, Personnage p){
		if(this.tab[x][y-2]==0 && this.tab[x][y-1]==2)
		{
			this.tab[x][y-1]=0;
			this.tab[x][y-2]=2;
			p.deplacerH();	
		}
		if(this.tab[x][y-2]==4 && this.tab[x][y-1]!=5)
		{
			this.tab[x][y-1]=0;
			this.tab[x][y-2]=5;
			p.deplacerH();			
		}
		if(this.tab[x][y-1]==5 && this.tab[x][y-2]==0)
		{
			this.tab[x][y-1]=4;
			this.tab[x][y-2]=2;
			p.deplacerH();
		}
		if(this.tab[x][y-1]==5 && this.tab[x][y-2]==4)
		{
			this.tab[x][y-1]=4;
			this.tab[x][y-2]=5;
			p.deplacerH();
		}
		return (this.tab[x][y-2]==0);
	}
	public boolean PousserVersDroite(int x, int y, Personnage p){
		if(this.tab[x+2][y]==0 && this.tab[x+1][y]==2)
		{
			this.tab[x+1][y]=0;
			this.tab[x+2][y]=2;
			p.deplacerD();
		}
		if(this.tab[x+2][y]==4 && this.tab[x+1][y]!=5 )
		{
			this.tab[x+1][y]=0;
			this.tab[x+2][y]=5;
			p.deplacerD();
		}
		if(this.tab[x+1][y]==5 && this.tab[x+2][y]==0)
		{
			this.tab[x+1][y]=4;
			this.tab[x+2][y]=2;
			p.deplacerD();
		}
		if(this.tab[x+1][y]==5 && this.tab[x+2][y]==4)
		{
			this.tab[x+1][y]=4;
			this.tab[x+2][y]=5;
			p.deplacerD();
		}
		return (this.tab[x+2][y]==0);
		
	}
	public boolean PousserVersGauche(int x, int y, Personnage p){
		if(this.tab[x-2][y]==0 && this.tab[x-1][y]==2)
		{
			this.tab[x-1][y]=0;
			this.tab[x-2][y]=2;
			p.deplacerG();
		}
		if(this.tab[x-2][y]==4 && this.tab[x-1][y]!=5)
		{
			this.tab[x-1][y]=0;
			this.tab[x-2][y]=5;	
			p.deplacerG();
		}
		if(this.tab[x-1][y]==5 && this.tab[x-2][y]==0)
		{
			this.tab[x-1][y]=4;
			this.tab[x-2][y]=2;
			p.deplacerG();
		}
		if(this.tab[x-1][y]==5 && this.tab[x-2][y]==4)
		{
			this.tab[x-1][y]=4;
			this.tab[x-2][y]=5;
			p.deplacerG();
		}
		
		return (this.tab[x-2][y]==0);
		
	}
	
	public int getTailleX(){
		return this.tab.length;
	}
	
	public int getTailleY(){
		return this.tab[0].length;
	}
	
	public int getCase(int x, int y)
	{
		return tab[x][y];
	}
}

