package ro.ucv.main.maps;

import org.jdesktop.swingx.mapviewer.Waypoint;

@SuppressWarnings("serial")
public class Punct {
	String eticheta = "";
	int latitudine = 0;
	int longitudine = 0;
	private Waypoint wayPoint = null;
	
	public Punct(int latitudine, int longitudine, String eticheta, Waypoint wp) {
		this.latitudine = latitudine;
		this.longitudine = longitudine;
		this.eticheta = eticheta;
		this.wayPoint = wp;
	}
	
	public Waypoint getWaypoint() {
		return wayPoint;
	}
}
