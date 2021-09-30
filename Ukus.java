package sladoleddzinica;

import java.awt.Color;

public class Ukus{
	private String naziv;
	private Color boja;

	public Ukus(String naziv, Color boja) {
		this.naziv = naziv;
		this.boja = boja;
	}

	public String dohvatiNaziv() {
		return naziv;
	}

	public void postaviNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Color dohvatiBoja() {
		return boja;
	}

	public void postaviBoja(Color boja) {
		this.boja = boja;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Ukus)) return false;
		Ukus u = (Ukus) obj;
		return u.naziv.equals(naziv);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(naziv);
		sb.append("]");
		return sb.toString();
	}

}
