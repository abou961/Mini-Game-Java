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

public class Einstein extends Personnage{
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	
	public Einstein(int a, int b, boolean bo)
	{
		super(a,b,bo);
		super.perso = toolkit.getImage("../Image/Einstein/EISS.gif");
		this.nom="Einstein";
	}
	
	public Einstein(int a, int b)
	{
		this(a,b, true);
	}
	
	public String toString() //utile pour une amélioration du centre de contrôle, nous avons laissé cette méthode au cas où
	{
		return "Albert Einstein! \n un grand scientifique!\n il te sera d'une grande utilite pour \ndeplacer les caisses par sa \nforte intelligence\n"+ super.toString();
	}
	
	public void estStable(){
		super.perso = toolkit.getImage("../Image/Einstein/EISS.gif");
	}
	
	public void deplacerB(){
		super.deplacerB();
		super.perso = toolkit.getImage("../Image/Einstein/EIB.gif");
	}
	public void deplacerG()
	{
		super.deplacerG();
		super.perso = toolkit.getImage("../Image/Einstein/EIG.gif");
	}
	public void deplacerD(){
		super.deplacerD();
		super.perso = toolkit.getImage("../Image/Einstein/EID2.gif");
	}
}

