package ro.ucv.main.maps;

import java.util.ArrayList;

public class Suprafata {
	private ArrayList<Punct> listaPuncte = new ArrayList<Punct>();
	private int surfaceRadius = 0;
	
	Suprafata(int surfaceRadius) {
		this.surfaceRadius = surfaceRadius;
	}
	
	public void add(Punct punct) {
		listaPuncte.add(punct);
	}
	
	public ArrayList<Punct> getArray() {
		return listaPuncte;
	}
	
	public double getRaza() {
		return surfaceRadius;
	}
}
