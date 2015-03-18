package ro.ucv.main.interactive;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;


public class PictureGUI extends JFrame implements MouseListener {

	private LinkedList<Point> entities= new LinkedList<Point>();

	private int ovalRadius=20;	
	
	public void paint(Graphics g) {
		super.paint(g);	//this line will ensure that paint can still draw buttons and other controls	
		
		g.setColor(Color.RED);		
		for(Point p:entities){
			g.fillOval(p.x-ovalRadius/2, p.y-ovalRadius/2, ovalRadius, ovalRadius);
		}
	}

	/**
	 * creates a GUI puts a picture in the background and draws circles on click
	 */
	public PictureGUI() {
		super("Click Anywhere");
		this.getContentPane().setLayout(new BorderLayout());
		
		JLabel label = new JLabel();
		label.setBounds(0, 0, 400, 296);
		//label.setPreferredSize(new Dimension(800, 550));
		label.setIcon(new ImageIcon("ucv.jpg"));
		
		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.add(label, BorderLayout.NORTH);
		
		JPanel centerPanel = new JPanel(new BorderLayout());
		JList listaPuncte = new JList();
		listaPuncte.add(new JButton("aaa"));
		centerPanel.add(listaPuncte);
		
		//JButton b = new JButton("dummy buton");
		//b.setBounds(200, 500, 150, 25);
		//l.add(b);
		
		this.addMouseListener(this);
		this.getContentPane().add(topPanel, BorderLayout.NORTH);
		this.getContentPane().add(centerPanel, BorderLayout.CENTER);
				
		this.setSize(400, 370);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

	}
	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Point p= new Point(e.getX(),e.getY());
		entities.add(p);
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		//do something when mouse pointer enters the frame
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// do something when mouse pointer exits the frame	
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
}
