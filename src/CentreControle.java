import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList; 
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


public class CentreControle extends JFrame implements ActionListener {
	
	private JPanel p1;
	private	JPanel p2;
	private	JPanel p3;
	private JButton sc1;
	private JButton sc2;
	private JButton reinitialiser;
	private static JLabel backgroundImage;
	private int indicePersoActif;
	private Personnage personnageActif;// pointeur vers le personnage actif
	private FenetreJeu f;
	private boolean persoDebloque;
	
	public static final Color BROWN = new Color(153,102,0);
	public static final Color GREY = new Color(127,127,127);

	private ArrayList<Personnage> listePerso = new ArrayList<Personnage>();
	private LinkedList<JButton> bouttonsPerso = new LinkedList<JButton>();
	private LinkedList<JPanel> listeChoix;//ce sont les cases sur lesquelles on ne pourra pas se téléporter avec Hawking: cases déja validées, caisses ou mur
	private LinkedList<JButton> listeChoixBoutons;//ce sont les cases où on pourra se téléporter
	
	public CentreControle(FenetreJeu fenetre, Personnage actif, Personnage nonActif){
		super("Centre de controle");
		setSize(400,600);
		setLocation(1025,200);
		f=fenetre;
		
		persoDebloque= false;
		listePerso.add(actif);
		listePerso.add(nonActif);
		
		for(int i=0; i<2; i++)
		{
			if(listePerso.get(i).estActif()){
				this.personnageActif=listePerso.get(i);
			}
		}
		
		p1= new JPanel();
		p2= new JPanel();
		p3= new JPanel();
		p1.setLayout(null);
		p2.setLayout(null);
		p3.setLayout(null);
		
		this.initListePerso();
		
		p3.setSize(400,400);
		p3.setBackground(Color.BLACK);
		
		p1.setBounds(10,25,150,325);
		p1.setBackground(Color.BLACK);
		
		p2.setBounds(170,25,198,198);
		p2.setBackground(Color.WHITE);
		
		
		
		this.carteChoix(f);
		//backgroundImage=new JLabel(new ImageIcon(getClass().getResource("../Image/autre/fondCentre.png")));
		backgroundImage=new JLabel(new ImageIcon("../Image/autre/fondCentre.png"));
		backgroundImage.setBounds(0,0,150,54);
		
		p1.add(backgroundImage);
		
		
		for(int i =0; i<this.listePerso.size(); i++)
		{
			if(this.listePerso.get(i).estActif()){bouttonsPerso.get(i).setBackground(Color.GREEN);}
			bouttonsPerso.get(i).setBounds(10, 70+i*30, 120, 25);
			bouttonsPerso.get(i).addActionListener(this);
			p1.add(bouttonsPerso.get(i));	
		}
		p1.setVisible(true);
		p2.setVisible(true);
		bouttonsPerso.get(1).setVisible(false);
		
		p3.add(p1);
		p3.add(p2);
		
		
		reinitialiser = new JButton("Reinitialiser!");
		reinitialiser.setBounds(75, 500, 250, 50);
		reinitialiser.setBackground(Color.RED);
		reinitialiser.addActionListener(this);
		p3.add(reinitialiser);
		
		add(p3);
		this.setVisible(true);
		this.setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public void MAJEtat(FenetreJeu f2)
	{//actualisation de l'état du niveau: perso débloqué, niveau finis etc.
		if(this.f.getMap().nombreCaseRestante() ==1 && persoDebloque==false)
		{
			this.bouttonsPerso.get(1).setVisible(true);
			JOptionPane.showMessageDialog(null, 
				"Regarde ta liste de scientifiques", 
				"Tu as debloque un nouveau perso!", 
			JOptionPane.INFORMATION_MESSAGE, null);
			this.personnageActif.estStable();
			this.persoDebloque=true;
		}
		if(this.f.getMap().nombreCaseRestante() ==0)
		{
			this.f.effacer();
			JOptionPane.showMessageDialog(null, 
				"Place au niveau 2", 
				"NIVEAU FINI!", 
			JOptionPane.INFORMATION_MESSAGE, null);
			this.setVisible(false);
			Niveau2 g = new Niveau2();
		}
		this.listeChoixBoutons.clear();
		this.listeChoix.clear();
		this.p2.removeAll();
		this.carteChoix(f2);
		repaint();
	}
	
	
	public ArrayList<Personnage> getListePerso()
	{
		return this.listePerso;
	}
	
	public void changerEtat(int i, boolean b)
	{
		this.listePerso.get(i).setActif(b);
	}
	
	public int getActif()
	{
		int indice=0;
		for(int i=0; i<listePerso.size(); i++)
		{
			if(listePerso.get(i).estActif())
			{
				indice=i;
			}
	    }
	    return indice;
	}
	
	public void initListePerso()
	{
		for(int i =0; i<this.listePerso.size(); i++)
		{
			bouttonsPerso.add(new JButton(this.listePerso.get(i).getNom()));
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==reinitialiser)
		{
			f.reinitialiserMap();
			repaint();
		}
		if(e.getSource()==bouttonsPerso.get(0))//choix de Einstein
		{
			changerEtat(this.getActif(),false);
			this.listePerso.get(0).setActif(true);
			this.bouttonsPerso.get(0).setBackground(Color.GREEN);
			this.bouttonsPerso.get(1).setBackground(Color.WHITE);
			f.changerActif(this.listePerso.get(0));
		}
		
		if(e.getSource()==bouttonsPerso.get(1))//choix de hawking
		{
			changerEtat(this.getActif(),false);
			this.listePerso.get(1).setActif(true);
			this.bouttonsPerso.get(1).setBackground(Color.GREEN);
			this.bouttonsPerso.get(0).setBackground(Color.WHITE);
			f.changerActif(this.listePerso.get(1));
			for(int i=0; i<listeChoixBoutons.size(); i++)
			{
				listeChoixBoutons.get(i).addActionListener(this);//les boutons peuvent alors être utilisés pour se téléporter
			}
			JOptionPane.showMessageDialog(null, 
				"Stephen Hawking peut se teleporter grace au centre de controle", 
				"Tu as debloque un nouveau perso!", 
			JOptionPane.INFORMATION_MESSAGE, null);
		}
		
		for(int i=0; i<listeChoixBoutons.size(); i++)
		{
			if(e.getSource()==listeChoixBoutons.get(i) && this.getActif()==1)
			{
				int x = (listeChoixBoutons.get(i).getX()/18)*75;
				int y = (listeChoixBoutons.get(i).getY()/18)*75;//deplacement du joueur si téléportation possible
				f.deplacerPerso(x,y);
			}
		}
		repaint();
	}
	
	
	public void carteChoix(FenetreJeu f2)//création de la petite map
	{
		listeChoix= new LinkedList<JPanel>();
		listeChoixBoutons = new LinkedList<JButton>();
		int nb=0;
		int nbBoutons=0;
		for(int i=0; i<11; i++)
		{
			for(int j=0; j<11; j++)
			{
				if(f2.getMap().getCase(i,j)==4 || f2.getMap().getCase(i,j)==5)
				{
					listeChoix.add(new JPanel());
					listeChoix.get(nb).setBackground(Color.GREEN);
					listeChoix.get(nb).setBounds(i*18, j*18, 18, 18);
					listeChoix.get(nb).setLayout(null);
					p2.add(listeChoix.get(nb));
					nb++;
					
				}	
				if(f2.getMap().getCase(i,j)==2)
				{
					listeChoix.add(new JPanel());
					listeChoix.get(nb).setBackground(BROWN);
					listeChoix.get(nb).setBounds(i*18, j*18, 18, 18);
					listeChoix.get(nb).setLayout(null);
					p2.add(listeChoix.get(nb));
					nb++;
				}
				if(f2.getMap().getCase(i,j)==1)
				{
					listeChoix.add(new JPanel());
					listeChoix.get(nb).setBackground(GREY);
					listeChoix.get(nb).setBounds(i*18, j*18, 18, 18);
					listeChoix.get(nb).setLayout(null);
					p2.add(listeChoix.get(nb));
					nb++;
					
				}
				if(f2.getMap().getCase(i,j)==0)
				{	
					if(this.getActif()==1);
					{
						listeChoixBoutons.add(new JButton(""));
						listeChoixBoutons.get(nbBoutons).setBackground(Color.WHITE);
						listeChoixBoutons.get(nbBoutons).addActionListener(this);
						listeChoixBoutons.get(nbBoutons).setBounds(i*18, j*18, 18, 18);
						p2.add(listeChoixBoutons.get(nbBoutons));
					}
					nbBoutons++;
				}
			}
		}
	}
}

