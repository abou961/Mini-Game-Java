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



public class FenetreRegle extends JFrame implements ActionListener {
	private Image consignes;
	private JButton nouveauFlacon;
	private JPanel p1;
	private Niveau3 niveau;
	
	public FenetreRegle(Niveau3 n) {
		super("Regles");
		setSize(300,600);
		setLocation(1025,200);
		
		niveau=n;
        try {
			consignes = ImageIO.read(new File("../Image/autre/regles.png"));
			
		}
		catch(IOException exc) {
			exc.printStackTrace();
		}
		
		p1= new JPanel();
		p1.setLayout(null);
		p1.setVisible(true);
		
		p1.setBounds(0,0,getWidth(),getHeight());
		p1.setBackground(Color.BLACK);
		
		nouveauFlacon = new JButton("Nouveau Flacon");
		nouveauFlacon.setBounds(0, 480, getWidth(), 80);
		nouveauFlacon.setBackground(Color.RED);
		nouveauFlacon.addActionListener(this);
		nouveauFlacon.setVisible(true);
		p1.add(nouveauFlacon);
		this.add(p1);
		this.setVisible(true);
		
		setVisible(true);
		this.setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public void paint(Graphics g){
            Graphics2D g2d = (Graphics2D)g;
            g2d.drawImage(consignes, 0, 30, getWidth(), 500, null);
    }
    
    public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==nouveauFlacon)
		{
			niveau.setVisible(false);
			niveau=new Niveau3();
		}
	}
}

