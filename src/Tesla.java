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

public class Tesla extends Personnage {
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	
	public static double VITESSE=0.6;//constante pour jetPack et gravité
	public static double GRAVITE=0.9;
	
	public double xo=300;//position initiale
	public double yo=570;
	
	private double dx=0;//dérivées utiles pour pour la gestion de la gravité
	private double dy=0;
	
	public Image foudre;//image chaque 10 secondes de foudre qui frappe
	private int width=86;
	private int height=111;
	private int vie = 1000;//vie max
	
	private boolean monte;//Etat du joueur pour les animations
	private boolean stable = true;
	private boolean atteris=false;
	
	public int phase=0;//nous utilisons les phases pour les animations
	
	private int compteur;//les compteurs utilisés pour les animations
	public int cpt=0;
	public int cpt2=0;
	
	public Tesla(){
		super(300,570,true);
		super.perso = toolkit.getImage("Image/Tesla/tesla.gif");
		foudre = toolkit.getImage("Image/autre/foudre3.gif");
			
	}
	
	public int getCompteur(){return compteur;}
	public void gestionTemps(){this.compteur++;}
	public void setCompteur(int a){this.compteur=a;}
	
	public void setX(double a){x=(int)a;}
	public void setY(double a){y=(int)a;}
	
	public int getX(){return this.x;}
	public int getY(){return this.y;}
	
	public void setDx(double a){dx=a;}
	public void setDy(double a){dy=a;}
	
	public double getDx(){return this.dx;}
	public double getDy(){return this.dy;}
	
	public boolean persoMonte(){return monte;}
	public boolean persoAtteris(){return atteris;}
	public boolean persoStable(){return stable;}
	
	public void setMonte(boolean b){monte=b;}
	public void setAterris(boolean b){atteris=b;}
	public void setStable(boolean b){stable=b;}
	
	
	public void deplacerVDroite(){super.x+=12;}
	public void deplacerVGauche(){super.x-=6;}
	public void estStable(){
		this.perso = toolkit.getImage("../Image/Tesla/tesla.gif");
	}
	public void atteris(){
		super.perso = toolkit.getImage("../Image/Tesla/teslaamorti.gif");
	}
	public void chute(){
		super.perso = toolkit.getImage("../Image/Tesla/chuteT.gif");
	}
	public void chuteV2(){
		super.perso = toolkit.getImage("../Image/Tesla/chuteT2.gif");
	}
	public void versDroite(){
		this.dx+=2.0*VITESSE ;
		phase=6;
		if(this.y>=560){//déplacement au sol
			this.x+=15 ;
		}
	}
	public void versGauche(){
		this.dx-=2.0*VITESSE ;
		phase=7;
		if(this.y>=560)//déplacement au sol
		{
			this.x-=15 ;
		}
	}
	public void commenceChute(){
			this.dx=0.0;
		    this.dy=-5.0*VITESSE;
			
			xo=this.x;//dans le cas vie =0 et reinitialisation au même endroit on a besoin d'enregistrer la derniere position 
			yo=this.y;
			
			this.compteur=0;
			this.monte=false;
			phase=4;//phase 4, le jet pack est arrêté, plus tard il écartera les jambes, puis il aterriras c'est la variable "phase" qui nous permet de nous repérer
			cpt=this.compteur;
	}
	public Rectangle getBounds(Niveau2 g) {
		return new Rectangle(x, y, 86, 116);
	}
	public int getVie(){return this.vie;}
	public void recommence(){this.vie=1000;}//on remet la vie au max si on reinitialise
	public void touche(){this.vie-=5;}//si on touche un virus
	
	public boolean dansEcran(Niveau2 n)
	{
		if(this.x<=0){this.x+=5;}
		if((this.x+this.width)>=n.width){this.x-=5;}
		return(this.x>0 && (this.x+this.width)<n.width);
	}
	
	public void pesanteur()
	{	//gestion de la gravité, les facteurs sont arbitraires, nous les avons changé en tatonnant jusqu'à obtenir un résultat cohérent
		this.dy+=0.003*GRAVITE*this.compteur;      
		this.x+=this.dx;                           
		this.y+=this.dy;                           
	}
	
	public void gestionTesla(Niveau2 n)
	{//gestion des phases graphiques pour les diffÃ©rentes animations 
	//on utilise 3 timer differents pour lancer les animations possibles (autre que dÃ©placement droite gauche)
		if(phase==0)
		{
			estStable();
		}
		if(phase==1)
		{	
			estStable();
		}
		if(phase==2)//differentes phases de chute
		{
			chuteV2();
		}
		if(phase==4)
		{
			chute();
		}
		if(phase==5)
		{
			atteris();
		}
		if(phase==6) //vers la droite
		{
			super.perso = toolkit.getImage("../Image/Tesla/teslaD.gif");
		}
		if(phase==7)//vers la gauche
		{
			super.perso = toolkit.getImage("../Image/Tesla/teslaG.gif");
		}	
		
		if(persoStable()==false)//on gère maintenant les phases non-stables
		{
			if(persoMonte()==false && this.y<560)//on a arrer d'appuyer sur la flêche haut et on reste au dessus du sol
			{
				pesanteur();
			}
			else if(persoMonte() && this.y>100)//
			{
				this.y-=5 ;       
			}
			else if(this.y<=50)//utile pour garder fluidité tout en empéchant de dépasser le haut de l'écran
			{
				this.y=50;     
				this.dy=3.0;     
			}
			else if(this.y>=560){//aterissage
				
				stable=true;
				monte=false;
				atteris=true;
				compteur=0;
				this.dx=0.0;
				this.dy=0.0;
				this.phase=5;
				cpt2=compteur;
			}
			if(this.x>=760)//contact bord droit
			{
				this.dx=-4.0;
				this.phase=7;  
			}
			if(this.x<=15)//contact bord gauche
			{
				this.dx=4.0;
				this.phase=6;
			}
		}
		if(persoStable()){ //animations d'aterissage et fluidité
			if((compteur-cpt2)>=18 && persoAtteris()){
				cpt2=compteur;
				this.phase=0;
				atteris=false;
			}
		}
		if((compteur-cpt)>=40){//au bout d'un certain temps, phase de chute suivante
			cpt=compteur;
			this.phase=2;
		}
	}
}
