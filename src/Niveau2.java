import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Niveau2 extends JFrame implements KeyListener, ActionListener {
	
	
	public final int OBJECTIF = 3;//objectif de masques
	public final int width = 830;
	public final int height = 800;
	
	private int masqueRecup=0;//nombre de masques recupérés <=objectifs
	private Image mur;
	private Image fils;
	
	Timer mt;
	BufferedImage bufD;
	Insets insets;
	Thread thread;
	
	private Tesla tesla;//le personnage actif dans ce niveau
	private LinkedList<Virus> listeItems;
	private LinkedList<Masque> listeObjectifs;
	
	
	public Niveau2()
	{
		
		super("Fenetre2");
		setSize(width,height);
		setLocation(200,00);
		
		bufD= new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		insets= getInsets();
		
		listeItems = new LinkedList<Virus>();
		listeItems.add(new Virus(50,300,1));//virus horizontaux
		listeItems.add(new Virus(265,365,1));
		
		listeItems.add(new Virus(600,200,2));//virus verticaux
		listeItems.add(new Virus(665,265,2));
		
		listeItems.add(new Virus(200,265,2));
		listeItems.add(new Virus(265,200,2));
		
		listeObjectifs = new LinkedList<Masque>();//masques à récupérer
		listeObjectifs.add(new Masque(700,400, true));//en premier seul celui-ci est visible
		listeObjectifs.add(new Masque(100,400, false));
		listeObjectifs.add(new Masque(400,500, false));

		
		try {
			mur = ImageIO.read(new File("../Image/autre/background_Back.jpg"));
			fils = ImageIO.read(new File("../Image/autre/background_Front.png"));
			
		}
		catch(IOException exc) {
			exc.printStackTrace();
		}
		Toolkit T=Toolkit.getDefaultToolkit();
		addKeyListener(this);
		mt=new Timer(20, this);
		mt.start();
		
		tesla= new Tesla();
		
		setVisible(true);
		this.setResizable(false);
		JOptionPane.showMessageDialog(null, 
			"Objectif: recupere les masques en evitant le virus", 
			"NIVEAU 2", 
		JOptionPane.INFORMATION_MESSAGE, null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	

	public void paint(Graphics g)
	{	
		Graphics g2 = bufD.getGraphics();
		g2.drawImage(mur,0,0,width,height, null);
		g2.setColor(Color.BLACK);
		tesla.estStable();
		tesla.gestionTesla(this);//on actualise l'état du perso
		g2.setColor(Color.BLACK);
		for(Virus i: listeItems)//affiche les virus
		{
			g2.drawImage(i.getImage(),i.getX(),i.getY(),60,60, null);
			
		}
		for(Masque m: listeObjectifs)//affiche les masques
		{
			if(m.getVisible())
			g2.drawImage(m.getImage(),m.getX(),m.getY(),120,60, null);
			
		}
		g2.drawImage(tesla.getImage(),tesla.getX(),tesla.getY(),86,116, null);
		g2.drawImage(fils,0,0,width,height, null);
		g2.drawImage(tesla.foudre,0,0,width,height, null);//gif de foudre chaque 10s
		
		g2.fillRect(20, 50, 210,50);//vie
		if(tesla.getVie()>=300){
			g2.setColor(Color.GREEN);//en vert tant que la vue est >=30%
		}
		else{
			g2.setColor(Color.RED);//sinon rouge
		}
		g2.fillRect(25, 60, (int)(tesla.getVie()*0.2),30);
		
		g2.setColor(Color.WHITE);//masques recuperes
		g2.fillRect(595, 50 , 200,45);
		for(int i=0; i<masqueRecup; i++)
		{
			g2.drawImage(listeObjectifs.get(i).getImage(),720-i*65,60,60,25, null);
		}
		//eviter le clignotement
		g.drawImage(bufD, insets.left, insets.top, this);	
	}
	

	
	public void actionPerformed(ActionEvent e) 
	{ 
		tesla.gestionTemps();//actualise le compteur=temps
		tesla.gestionTesla(this);//gere déplacement et image associé
		for(Virus i: listeItems)
		{
			i.deplacement();
		}
		checkCollisions();//gestion collisions/bonus/vie -> actualisation
		checkBonus();
		checkVie();
		repaint();
	}


	public void keyPressed(KeyEvent evt){
		if(evt.getKeyCode()== KeyEvent.VK_LEFT && tesla.dansEcran(this))
		{
			tesla.versGauche();
		}
		if(evt.getKeyCode()== KeyEvent.VK_RIGHT && tesla.dansEcran(this))
		{
			tesla.versDroite();
		}
		if(evt.getKeyCode()== KeyEvent.VK_UP)
		{
			tesla.setMonte(true);
			tesla.setStable(false);
			tesla.phase=1;
		}
		
		repaint();
	}
	public void keyReleased(KeyEvent evt){

		if(evt.getKeyCode()== KeyEvent.VK_UP)
		{
			tesla.commenceChute();			
			tesla.gestionTesla(this);
		}
		if(evt.getKeyCode()== KeyEvent.VK_LEFT){if(tesla.persoStable()){tesla.setDx(0.0);}}
		if(evt.getKeyCode()== KeyEvent.VK_RIGHT){if(tesla.persoStable()){tesla.setDx(0.0);}}
		repaint();
	} 
	public void keyTyped(KeyEvent evt) {}

	public void checkCollisions()
	{
		Rectangle r3 = this.tesla.getBounds(this);
        for (Virus i : listeItems) {
            
            Rectangle r2 = i.getBounds();
            if (r3.intersects(r2)) {
                tesla.touche();
            }
        }
	}
	
	public void checkBonus()
	{
		Rectangle r3 = this.tesla.getBounds(this);//GERER DERNIER MASQUE
		int index=0;
		if(masqueRecup==3)
		{
			JOptionPane.showMessageDialog(null, 
			"Place au niveau 3", 
			"NIVEAU FINI!", 
			JOptionPane.INFORMATION_MESSAGE, null);
			this.setVisible(false);
			Niveau3 ni=new Niveau3();
			masqueRecup=0;
			
		}
		
        for (Masque i : listeObjectifs) {
            Rectangle r2 = i.getBounds();
            if (r3.intersects(r2) && i.getVisible()) {
                i.setVisible(false);
                if(index<2){
                listeObjectifs.get(index+1).setVisible(true);}
                masqueRecup++;
            }
            index++;
        }
	}
	
	public void checkVie(){
		if(tesla.getVie()<=0){//cas de la vie nulle on recommence au même endroit, on perd juste les masques récupérés jusque la
			JOptionPane.showMessageDialog(null, 
				"Tu as echoue, tu es maintenant infecte par le COVID-19. RESSAIE!", 
				"REESSAIE", 
			JOptionPane.INFORMATION_MESSAGE, null);
			this.listeObjectifs.get(0).setVisible(true);//on reinitialise les masques
			this.listeObjectifs.get(1).setVisible(false);
			this.listeObjectifs.get(2).setVisible(false);
			masqueRecup=0;
			this.tesla.recommence();
		}
	}
	
	
}
