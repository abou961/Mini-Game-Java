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

public class Hawking extends Personnage{
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	
	public Hawking(int a, int b, boolean bo)
	{
		super(a,b, bo);
		super.perso = toolkit.getImage("../Image/Hawking/HawkingS1.gif");
		this.nom="Hawking";
	}
	
	public Hawking(int a, int b)
	{
		this(a,b, true);
	}
	
	public String toString()//cf toString de Eistein
	{
		return "Stephen Hawking!\n il peut se deplacer \na sa guise grace aux trous\n noirs qu'il cree!\n"+ super.toString();
	}
	
	public void estStable(){
		super.perso = toolkit.getImage("../Image/Hawking/HawkingS1.gif");
	}
	
	public void deplacerB(){
		super.deplacerB();
		super.perso = toolkit.getImage("../Image/Hawking/31.gif");
	}
	public void deplacerG()
	{
		super.deplacerG();
		super.perso = toolkit.getImage("../Image/Hawking/HawkingS3.gif");
	}
	public void deplacerD(){
		super.deplacerD();
		super.perso = toolkit.getImage("../Image/Hawking/HawkingS1.gif");
	}
}


