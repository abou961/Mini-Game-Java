import javax.swing.*;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
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
import java.awt.geom.QuadCurve2D;

public class Niveau3 extends JFrame implements MouseListener, MouseMotionListener, ActionListener, KeyListener{
	BufferedImage bufD;
	Insets insets;
	Thread thread;
	private int width = 830;
	private int height = 800;
    private int compteur2=0;
    private int dx=0;
	private int dy=0;
	private int phase=1;//il y a 2 phases dans la création du vaccin
    
    private int origX, origY;//utiles pour la création des lignes: gestion de la souris
    private int currX, currY;
    boolean dejaPerdu = false;//permet d'afficher ou non les messages de game over

    private LinkedList<Antigene> anti = new LinkedList<Antigene>();
    private LinkedList<Antigene> ptsFixes = new LinkedList<Antigene>();//les points qui vont toucher le sol en premier
    /*les points fixes font parties des améliorations possibles que nous voulions ajouter mais que nous n'avons pas eu le temps
    de changer, en effet l'idéal aurait été que la chaîne d'antigène arrête sa chute seuelemnt quand elle se serait stabilisé à terre
    donc si son barycentre était positionné entre 2 ou plus points fixes, nous avions déjà codés la classe barycentre et elle était 
    fonctionnelle.*/
    private LinkedList<Virus> vAAtteindre = new LinkedList<Virus>();
    private LinkedList<Item> aEviter = new LinkedList<Item>();//ce sont les obstacles qui ne doivent pas toucher l'antigène de départ afin d'éviter pour ne pas provoquer une thrombose
    private Antigene depart = new Antigene(300,250);//antigène de départ

    private boolean enChute=false;
    private boolean aFinisChute=false;
    private boolean reactionImmunitaire=false;//si antigène de départ touche un obstacle
    private int nombreVirusTouche=0;

    
    private Image main;
    private Image fond;
    private Image obstacles;
    private Image obstacles2;
    
    private FenetreRegle f2;//Les règles doivent être claires dès le début dans ce niveau c'est pourquoi nous avons fait le choix de rajputer une fenêtre dédiée à l'explication des règles
	
	public Niveau3(){
		
		super("Fenetre2");
		setSize(width,height);
		setLocation(200,00);
		
		bufD= new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		insets= getInsets();
		this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);
        
        dejaPerdu=false;
        reactionImmunitaire=false;
        
         try {
			main = ImageIO.read(new File("../Image/autre/main.png"));
			fond = ImageIO.read(new File("../Image/autre/fondNiv3.png"));
			obstacles = ImageIO.read(new File("../Image/Item/probleme.png"));
			obstacles2 = ImageIO.read(new File("../Image/Item/probleme2.png"));
		}
		catch(IOException exc) {
			exc.printStackTrace();
		}
        
        vAAtteindre.add(new Virus(100,650,0));
        vAAtteindre.add(new Virus(600,650,0));
        vAAtteindre.add(new Virus(500,500,0));
        
        aEviter.add(new Item(300, 650, obstacles));
		
		f2 = new FenetreRegle(this);
		
        Timer mt=new Timer(20, this);
		mt.start();
		
        depart.deviensDepart();
        anti.add(depart);
		
		Toolkit T=Toolkit.getDefaultToolkit();
		setVisible(true);
		this.setResizable(false);
		JOptionPane.showMessageDialog(null, 
			"Objectif: creer un vaccin (regles a droite!), fleche du bas pour faire tomber les antigenes", 
			"NIVEAU 3", 
		JOptionPane.INFORMATION_MESSAGE, null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public void paint(Graphics g)
	{	
		Graphics g2 = bufD.getGraphics();	

		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, width,height/2);
		g2.setColor(Color.WHITE);
		g2.fillRect(0, height/2, width,20);
		g2.setColor(Color.darkGray);
		g2.fillRect(0, height/2+20, width,height/2-20);//l'écran est divisé en 2, on ne peut créer des antigène que sur la partie haute du "flocon"
		g2.setColor(Color.WHITE);
		
		for(Virus i: vAAtteindre)
		{
			g2.drawImage(i.getImage(),i.getX(),i.getY(),60,60, null);			
		}	
		for(Item i : aEviter)
		{
			g2.drawImage(i.getImage(),i.getX(),i.getY(),60,60, null);			
		}	
		
		for(Antigene l : anti)
		{
			if(l.antiPrec!=null)
			g2.drawLine(l.antiPrec.getX(), l.antiPrec.getY(),l.getX(), l.getY());
		}
		if(enChute==false && aFinisChute==false){
			g2.setColor(Color.GREEN);//la ligne change de couleur quand elle est validée par un clique
			g2.drawLine(anti.get(anti.size()-1).getX(), anti.get(anti.size()-1).getY(), currX, currY);
		}
		for(Antigene l : anti)
		{
			if(l.estDepart()){g2.setColor(Color.RED);}
			else{g2.setColor(Color.BLUE);}
			g2.fillOval(l.getX()-(int)(l.width/2), l.getY()-(int)(l.height/2), l.width, l.height);	
		}

		g2.drawImage(main,currX-300,currY-145,300,300, null);
		g2.drawImage(fond,-35,10,width+90,height, null);
		g.drawImage(bufD, insets.left, insets.top, this);
	}
	
	
	public void chute()
	{
		for(Antigene a : anti)
		{
			if(a.getY()>=700)
			{
				aFinisChute=true;
				if(ptsFixes.size()==0)//la gestion des points fixes est utile pour savoir quand il faut arreter la chute et il faudrait plus l'étoffer pour gérer une chute de solide plus réaliste(cf. l.40)
				{
					ptsFixes.add(a);

				}
			}
			else if(ptsFixes.size()==0 && a.getY()<700)
			{
				a.chute();//on fait chuter les antigènes en même temps que les lignes
			}
		}
		repaint();		
	}
	
	
	public void checkContact(){
		for(Antigene a: anti)
		{
			Rectangle r3 = a.getBounds();
			for (Virus i : vAAtteindre) {
				Rectangle r2 = i.getBounds();
				if (r3.intersects(r2) && !a.toucheUnVirus() && !i.estTouche()) {//gestion d'un antigene qui touche un virus
					a.aToucheUnVirus();
					i.touche();
				}
			}
		}
		Rectangle r4 = depart.getBounds();
		for(Item i : aEviter)//gestion d'un contact entre l'antigène de départ et l'obstacle
		{
			Rectangle r5 = i.getBounds();
			reactionImmunitaire = (r4.intersects(r5));
			if(reactionImmunitaire) break;
		}
		
		for (Virus i : vAAtteindre)
		{
			if(i.estTouche())nombreVirusTouche++;
		}
		
		if(dejaPerdu==false)
		{
			if(reactionImmunitaire)//si départ touche obstacle
			{
				this.setVisible(false);
				f2.setVisible(false);
				reactionImmunitaire=false;
				JOptionPane.showMessageDialog(null, 
					"Tu as perdu, Thrombose", 
					"Reesaie avec un autre flacon", 
				JOptionPane.INFORMATION_MESSAGE, null);
				new Niveau3();
				dejaPerdu=true;
			}
			
			else if(!reactionImmunitaire){//si on a pas touché tous les objectifs
				if(nombreVirusTouche<vAAtteindre.size())
				{
					this.setVisible(false);
					f2.setVisible(false);
					reactionImmunitaire=false;
					JOptionPane.showMessageDialog(null, 
						"Tu as perdu, pas assez d'antigenes", 
						"Reesaie avec un autre flacon", 
					JOptionPane.INFORMATION_MESSAGE, null);
					new Niveau3();
					dejaPerdu=true;
				}
				if(phase==1 && nombreVirusTouche>=vAAtteindre.size())//si tout est validé, on passe a la phase 2
				{
					JOptionPane.showMessageDialog(null, 
						"Phase 2", 
						"Bravo", 
					JOptionPane.INFORMATION_MESSAGE, null);
					phase=2;// on passe à la deuxieme phase du vaccin
					anti.removeAll(anti);
					vAAtteindre.removeAll(vAAtteindre);
			
					vAAtteindre.add(new Virus(150,500,0));
					vAAtteindre.add(new Virus(400,650,0));
					vAAtteindre.add(new Virus(500,460,0));
					aEviter.add(new Item(300, 500, obstacles2));
            
					depart.setPos(300,215);
            
					anti.add(depart);
					compteur2=0;
					ptsFixes.removeAll(ptsFixes);
 
					nombreVirusTouche=0;
					aFinisChute=false;
					enChute=false;
					reactionImmunitaire=false;
					dejaPerdu=false;
				}
			
				if(phase==2 && nombreVirusTouche>=vAAtteindre.size())//si on a tout validé, on passe à l'écran de fin
				{
					JOptionPane.showMessageDialog(null, 
						"Bravo!", 
						"Bravo", 
					JOptionPane.INFORMATION_MESSAGE, null);
					dejaPerdu=true;
					this.setVisible(false);
					f2.setVisible(false);
					new Fin();
				}
		}
	}
}
	
	
    public void mouseClicked(MouseEvent e) {//ajouter un antigene et ligne à chaque clique
		if(!enChute && currY<=height/2 && currY>80 && currX>55 && currX<width-50){
			
			anti.add(new Antigene(currX, currY, anti.get(anti.size()-1)));
			compteur2++;
			origX=currX;
			origY=currY;		
			repaint();
		}
		if(currY>height/2)//si on veut créer en dessous de l'espace autorisé
		{
			JOptionPane.showMessageDialog(null, 
				"Attention rester dans l'espace de travail!", 
				"PROBLEME!", 
			JOptionPane.INFORMATION_MESSAGE, null);
		}
    }
    
    public void mouseMoved(MouseEvent e) {
        repaint();//il faut actualiser la position de la souris même quand il n'y a pas de clique 
		currX = e.getX();
		currY = e.getY();
    }
    
    public void actionPerformed(ActionEvent e) 
	{	
		if(enChute){chute();}
		if(aFinisChute)
		{
			checkContact();
		}
		repaint();
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_DOWN)
		{
			enChute=true;
		}
	}
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	@Override
    public void mouseReleased(MouseEvent arg0) {}
    @Override
    public void mouseDragged(MouseEvent arg0) {}
	public void mousePressed(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public static void main (String[] args) {
		
		new Niveau3();
	}
    
}

