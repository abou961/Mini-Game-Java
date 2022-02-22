import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class FenetreJeu extends JFrame implements KeyListener{
	
	public static final int TAILLE_MUR = 75;
	private int [][] tabInit = {{1,1,1,1,1,1,1,1,1,1,1},{1,1,1,1,1,4,4,4,1,1,1},{1,1,1,1,1,0,0,0,1,4,1},{1,1,1,0,0,0,0,0,1,2,1},{1,1,0,0,0,1,2,1,1,0,1},{1,0,0,0,0,0,0,0,1,1,1},{1,0,0,0,1,1,0,0,0,0,1},{1,1,0,0,0,0,1,0,1,0,1},{1,1,0,1,0,0,2,0,2,0,1},{1,1,0,0,0,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1,1,1}};
	
	
	private int nbEssais = 0;
	private static int width = 830;
	private static int height = 800;
	private Personnage p1;//on initialise 2 personnages(Hawking et Einsten
	private Personnage p2;
	private Personnage personnage;//un pointeur qui correspond au personnage actif
	private Map map;
	private CentreControle c;// on associe une centre de controle pour actualiser en temps réel son état
	BufferedImage bufD;
	Insets insets;
	Thread thread;
	private int xInit;//pour réinit
	private int yInit;
	
	private Image mur;
	private Image sol;
	private Image sol2;//cases vertes
	private Image caisse;
	private Image caisse2;//caisses validées
	
	public FenetreJeu(Personnage pActif, Personnage pNonActif)
	{
		//creation de la fenetre et éléments graphiques
		super("Fenetre2");
		setSize(width,height);
		setLocation(200,00);
		
		personnage=pActif;
		p2=pNonActif;
		
		xInit=personnage.getX();
		yInit=personnage.getY();
		
		this.map=new Map(tabInit);
		
		try {
			mur = ImageIO.read(new File("../Image/Item/mur2.gif"));
			sol = ImageIO.read(new File("../Image/Item/sol.png"));
			sol2 = ImageIO.read(new File("../Image/Item/sol2.png"));
			caisse = ImageIO.read(new File("../Image/Item/caisse.gif"));
			caisse2 = ImageIO.read(new File("../Image/Item/caisse2.png"));
		}
		catch(IOException exc) {
			exc.printStackTrace();
		}
		
		bufD= new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		insets= getInsets();
		
		c = new CentreControle(this, pActif, pNonActif);
		Toolkit T=Toolkit.getDefaultToolkit();
		
		addKeyListener(this);
		
		setVisible(true);
		this.setResizable(false);
		JOptionPane.showMessageDialog(null, 
			"Ojectif: deplacer les caisses sur les cases vertes.", 
			"NIVEAU 1!", 
		JOptionPane.INFORMATION_MESSAGE, null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public FenetreJeu(){}//initialisateur à vide
	
	public void reinitialiserMap(){//utile si le joueur est bloqué
		this.personnage.setPos(xInit,yInit);
		nbEssais++;
		map.reiTab();
		
		c.MAJEtat(this);//on actualise le centre de controle
		repaint();
	}
	
	public Map getMap(){return this.map;}
	
	public void changerActif(Personnage pers1)
	{
		this.personnage=pers1;//changer de personnage actif(de einstein a hawking ou l'inverse
	}
	
	public void deplacerPerso(int x, int y)//utile pour la communication avec l'écran de contrôle
	{
		this.personnage.setX(x);
		this.personnage.setY(y);
	}

	public void effacer()
	{
		this.setVisible(false);
		this.removeAll();
	}
	public void paint(Graphics g)
	{
		Graphics g2 = bufD.getGraphics();
		Image imageADessiner=null;
		for(int i=0; i<this.map.getTailleX(); i++)
		{
			for(int j=0; j<this.map.getTailleY(); j++)
			{
				if(map.getCase(i,j)==1)
				{
					imageADessiner=mur;
				}
				else if(map.getCase(i,j)==0)
				{
					imageADessiner=sol;
				}
				else if(map.getCase(i,j)==2)
				{
					imageADessiner=caisse;
				}
				//Nous laissons l'identifiant 3 libre, il était destiné à des items BONUS, peut etre en amélioration futures...
				else if(map.getCase(i,j)==4)
				{
					imageADessiner=sol2;
				}
				else if(map.getCase(i,j)==5)
				{
					imageADessiner=caisse2;
				}
				g2.drawImage(imageADessiner,i*TAILLE_MUR,j*TAILLE_MUR,TAILLE_MUR,TAILLE_MUR, this);//affiche la map...
			}
		}
		g2.setColor(Color.BLACK);
		g2.drawImage(personnage.getImage(), personnage.getX(), personnage.getY(), TAILLE_MUR, TAILLE_MUR, this);
		g.drawImage(bufD, insets.left, insets.top, this);
	}
	
	public void keyPressed(KeyEvent evt){
		int caseX = personnage.getX()/TAILLE_MUR;
		int caseY = personnage.getY()/TAILLE_MUR;
		if (evt.getKeyCode()== KeyEvent.VK_DOWN){
			if(this.map.versBas(caseX,caseY))
			{
				this.personnage.deplacerB();
			}
			else if(this.map.getCase(caseX,caseY+1)==2 || this.map.getCase(caseX,caseY+1)==5)
			{
				if(this.map.PousserVersBas(caseX,caseY,this.personnage))
				{
					this.personnage.deplacerB();
				}
			}
		}
		else if (evt.getKeyCode()== KeyEvent.VK_UP){
			if(this.map.versHaut(caseX,caseY))
			{
				this.personnage.deplacerH();
			}
			else if(this.map.getCase(caseX,caseY-1)==2 || this.map.getCase(caseX,caseY-1)==5)
			{
				if(this.map.PousserVersHaut(caseX,caseY,this.personnage))
				{
					this.personnage.deplacerH();
				}
			}
		}
		else if (evt.getKeyCode()== KeyEvent.VK_RIGHT){
			if(this.map.versDroite(caseX,caseY))
			{
				this.personnage.deplacerD();
			}
			else if(this.map.getCase(caseX+1,caseY)==2 || this.map.getCase(caseX+1,caseY)==5)
			{
				if(this.map.PousserVersDroite(caseX,caseY,this.personnage))
				{
					this.personnage.deplacerD();
				}
			}
		}
		else if (evt.getKeyCode()== KeyEvent.VK_LEFT){
			if(this.map.versGauche(caseX,caseY))
			{
				this.personnage.deplacerG();
			}
			else if(this.map.getCase(caseX-1,caseY)==2 || this.map.getCase(caseX-1,caseY)==5)
			{
				if(this.map.PousserVersGauche(caseX,caseY,this.personnage))
				{
					this.personnage.deplacerG();
				}
			}
		}
		c.MAJEtat(this);
		repaint();
		}

	public void keyReleased(KeyEvent evt){
		this.personnage.estStable();//utile pour les animations
	} 

	public void keyTyped(KeyEvent evt) {}
	
}

