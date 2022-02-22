import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList; // import the ArrayList class
import java.util.LinkedList; // import the ArrayList class
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Fin extends JFrame{ //MERCI d'avoir lu tout ce code:)
	
	private Image fin;
	
	public Fin()
	{
		super("Fin");
		setSize(600,630);
		setLocation(800,100);
		
		try {
			fin = ImageIO.read(new File("../Image/autre/fin.png"));
		}
		catch(IOException exc) {
			exc.printStackTrace();
		}
		
		
		setVisible(true);
		this.setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void paint(Graphics g)
	{
		g.drawImage(fin, 0, 30, getWidth(), getHeight()-30, this);
	}
}

