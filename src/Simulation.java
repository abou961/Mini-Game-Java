/*
KARKACHE,ABOU MOUSSA, BEN MOUH, BELLAGNECH

V1.3 du jeu SCIENTIFIQUES_VS_COVID
2021


Remarque avant lecture, les niveaux sont chainés simplement (quand le niveau 1 est finit on va créer un niveau 2 etc.)
*/
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


public class Simulation extends JFrame implements ActionListener{
	public static final int TAILLE_MUR = 75;
	Image fond;
	JButton button;
	JPanel p1;
	ImageIcon iconA;
	
	public Simulation()
	{
		super("Fenetre2");
		setSize(939,683);
		setLocation(200,00);
		
	
		try {
			fond = ImageIO.read(new File("../Image/autre/FondMenu.jpg"));
			iconA = new ImageIcon("../Image/autre/play.png");
		}
		catch(IOException exc) {
			exc.printStackTrace();
		}
		
		p1=new JPanel();
		p1.setLayout(null);
		p1.setBounds(400,400,100,20);
		
		button = new JButton("", iconA);
		button.setRolloverIcon(iconA);
		button.setBounds(500, 450, 200, 51);
		button.addActionListener(this);
		
		
		p1.add(button);
		add(p1);

		
		setVisible(true);
		this.setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void paint(Graphics g)
	{
		g.drawImage(fond, 0, 30, getWidth(), getHeight()-30, this);
	}
	
	public static void main (String[] args) {
		
		new Simulation();
	}
	
	public void actionPerformed(ActionEvent e)
	{	
		if(e.getSource()==button)
		{
			//Création des 2 personnages utilisables
			Einstein p1=new Einstein(2*TAILLE_MUR,6*TAILLE_MUR);
			Hawking p2= new Hawking(p1.getX(), p1.getY(), false);
			//Création de la fenêtre de jeu
			FenetreJeu f = new FenetreJeu(p1, p2);
			this.setVisible(false);
		}
	}
}

