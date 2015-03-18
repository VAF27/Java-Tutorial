package ro.ucv.main.interactive;

public class Punct {
	String eticheta = "";
	int latitudine = 0;
	int longitudine = 0;
	
	public Punct(int latitudine, int longitudine, String eticheta) {
		this.latitudine = latitudine;
		this.longitudine = longitudine;
		this.eticheta = eticheta;
	}
}
