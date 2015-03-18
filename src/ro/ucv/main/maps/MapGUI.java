package ro.ucv.main.maps;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXMapKit;
import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.mapviewer.Waypoint;
import org.jdesktop.swingx.mapviewer.WaypointPainter;
import org.jdesktop.swingx.mapviewer.WaypointRenderer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

public class MapGUI extends JFrame implements MouseListener {

	private JTextField longitude = new JTextField();
	private JTextField latitude = new JTextField();
	private JTextField radius = new JTextField();
	
	private ArrayList<Punct> listaPuncte = new ArrayList<Punct>();
	private int ovalRadius = 0;
	private int nClicks = 0;
	private int n = 110;
	private Double lastZoom;
	private Point lastClick = null;
	private GeoPosition lastClickedLocation;
	private WaypointPainter waypointPainter = new WaypointPainter();
	private JXMapKit map;
	private Waypoint wayp;
	private Suprafata surface = null;
	JTextField textField = new JTextField("");
	boolean rightClick = false;

	public MapGUI() {
		super("Open Map - needs internet connection");

		this.setVisible(true);
		this.setBounds(150, 150, 850, 650);
		this.setLayout(null);

		map = (JXMapKit) (new SetupMap()).createOpenMap();
		map.setBounds(20, 20, 600, 300);
		map.getMainMap().addMouseListener(this);
		add(map);

		JLabel lblLong = new JLabel("Longitude");
		JLabel lblLat = new JLabel("Latitude");
		JLabel lblRadius = new JLabel("Radius");
		lblLong.setBounds(20, 330, 150, 25);
		lblLat.setBounds(180, 330, 150, 25);
		lblRadius.setBounds(340, 330, 150, 25);
		add(lblLong);
		add(lblLat);
		add(lblRadius);

		longitude.setBounds(20, 365, 150, 25);
		latitude.setBounds(180, 365, 150, 25);
		radius.setBounds(340, 365, 150, 25);
		textField.setBounds(500, 365, 150, 25);
		add(longitude);
		add(latitude);
		add(radius);
		add(textField);
		
		Random r = new Random();
		for(int index = 0; index < n; index++) {
			Point p = new Point();
			p.x = r.nextInt(1000);
			p.y = r.nextInt(1000);
			GeoPosition g = map.getMainMap().convertPointToGeoPosition(p);
			Waypoint curr = new Waypoint(g);
			waypointPainter.getWaypoints().add(curr);
			listaPuncte.add(new Punct(p.x, p.y, "" + index + 1, curr));
		}

		// Add a sample waypoint.
		waypointPainter.setRenderer(new WaypointRenderer() {
			@Override
			public boolean paintWaypoint(Graphics2D g, JXMapViewer map,
					Waypoint wp) {
				float alpha = 0.6f;
				int type = AlphaComposite.SRC_OVER;
				AlphaComposite composite = AlphaComposite.getInstance(type,
						alpha);
				
				Color color = new Color(1, 0, 0, alpha); // Red
				g.setColor(color);
				g.fillOval(-5, -5, 10,10);
				g.setColor(Color.BLACK);
				g.drawOval(-5, -5, 10,10);
				
				if(surface != null) {
					for(int index = 0; index < surface.getArray().size(); index++) {
						if(surface.getArray().get(index).getWaypoint() == wp) {
							g.setColor(Color.GREEN);
							g.fillOval(-5, -5, 10, 10);
							g.setColor(Color.BLACK);
							g.drawOval(-5, -5, 10,10);
						}
					}
				}
				
				if (ovalRadius > 0 && wp == wayp) {
					Double currentZoom=lastClick.distance(map.getCenter());
					Double r=currentZoom/lastZoom *ovalRadius;
					int zoomedRadius=r.intValue();
					//System.out.println("radius "+ovalRadius+"   zoomed"+zoomedRadius);
					g.setColor(color);
					g.fillOval(-zoomedRadius, -zoomedRadius, 2 * zoomedRadius,
							2 * zoomedRadius);
					//g.setColor(Color.RED);
					g.drawOval(-zoomedRadius, -zoomedRadius, 2 * zoomedRadius,
							2 * zoomedRadius);
				}
				return true;
			}

		});
		map.getMainMap().setOverlayPainter(waypointPainter);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JXMapViewer m = map.getMainMap();
		GeoPosition g = m.convertPointToGeoPosition(e.getPoint());
		String str = "";
		
		if(e.getButton() == MouseEvent.BUTTON3) {
			if(lastClick != null && !rightClick) {
				rightClick = true;
				double radius = geoDistance(lastClickedLocation, g);
				ovalRadius = ((Double) (lastClick.distance(e.getPoint()))).intValue();
				surface = new Suprafata(ovalRadius);
				
				for(int index = 0; index < listaPuncte.size(); index++) {
					Punct curr = listaPuncte.get(index);
					GeoPosition position = listaPuncte.get(index).getWaypoint().getPosition();
					double distance = geoDistance(lastClickedLocation, position);
					if(distance <= radius) {
						surface.add(curr);
						str += curr.eticheta + " (" + curr.latitudine + ", " + curr.latitudine
								+ "); ";
					}
				}
				textField.setText(str); 
				lastZoom=lastClick.distance(map.getMainMap().getCenter());
			}
			map.getMainMap().setOverlayPainter(waypointPainter);
			repaint();
		}
		
		if(e.getButton() == MouseEvent.BUTTON1) {
			rightClick = false;
			str = "";
			waypointPainter.getWaypoints().remove(wayp);
			lastClick = e.getPoint();
			lastClickedLocation = g;
			wayp = new Waypoint(g);
			ovalRadius = 0;
			surface = null;
			waypointPainter.getWaypoints().add(wayp);
			
			map.getMainMap().setOverlayPainter(waypointPainter);
			repaint();
		}
	}

	/**
	 * this draws on the map at clicked location . 
	 * @param g
	 */
	public void moveWaypoint(GeoPosition g) {
		
		//Modify this if you need to add a set of markers instead of a single one.
		if (waypointPainter.getWaypoints().isEmpty())
			waypointPainter.getWaypoints().add(
					new Waypoint(g.getLatitude(), g.getLongitude()));
		else {
			Iterator it = waypointPainter.getWaypoints().iterator();
			while (it.hasNext())
				waypointPainter.getWaypoints().remove(it.next());
			waypointPainter.getWaypoints().add(
					new Waypoint(g.getLatitude(), g.getLongitude()));
		}

		map.getMainMap().setOverlayPainter(waypointPainter);

	}

	public double geoDistance(GeoPosition g1, GeoPosition g2) {
		final int EARTHRADIUS = 6371; // The radius of the earth in kilometers

		// Get the distance between latitudes and longitudes
		double deltaLat = Math.toRadians(g1.getLatitude() - g2.getLatitude());
		double deltaLong = Math
				.toRadians(g1.getLongitude() - g2.getLongitude());

		// Apply the Haversine function
		double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
				+ Math.cos(Math.toRadians(g2.getLatitude()))
				* Math.cos(Math.toRadians(g1.getLatitude()))
				* Math.sin(deltaLong / 2) * Math.sin(deltaLong / 2);
		return EARTHRADIUS * 2 * Math.asin(Math.sqrt(a));
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		// System.out.println("entered");
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		// System.out.println("exited");

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		// System.out.println("pressed");

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		// System.out.println("releaseed");

	}
}
