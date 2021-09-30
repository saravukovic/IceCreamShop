package sladoleddzinica;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class Sladoled {
	private HashMap<Ukus, Integer> ukusi = new HashMap<>();
	private int velicinaCase;
	private int trenutnoUCasi;

	public Sladoled(int velicinaCase) {
		this.velicinaCase = velicinaCase;
	}

	public void dodajUkus(int kolicina, Ukus ukus) {
		if (trenutnoUCasi + kolicina > velicinaCase) {
			if (ukusi.get(ukus) == null)
				ukusi.put(ukus, velicinaCase - trenutnoUCasi);
			else
				ukusi.replace(ukus, ukusi.get(ukus) + (velicinaCase - trenutnoUCasi));
			trenutnoUCasi = velicinaCase;
		} else {
			if (ukusi.get(ukus) == null)
				ukusi.put(ukus, kolicina);
			else
				ukusi.replace(ukus, ukusi.get(ukus) + kolicina);
			trenutnoUCasi += kolicina;
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Iterator<Entry<Ukus, Integer>> iter = ukusi.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<Ukus, Integer> e = iter.next();
			sb.append(e.getValue());
			sb.append("ml");
			sb.append(e.getKey());
			if (iter.hasNext())
				sb.append(" ");
		}
		return sb.toString();
	}

}
