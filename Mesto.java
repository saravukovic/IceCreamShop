package sladoleddzinica;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Mesto extends Canvas implements Runnable {

	Aparat vlasnik;
	public static int velicinaCase = 200;
	private Sladoled sladoled;
	private boolean tocenjeUToku;
	private int interval = 500;
	private Thread mojaNit;
	private int umnozak;
	private List<Ukus> ukusiUSladoledu = new ArrayList<>();
	private Ukus trenutniUkus;
	private int granica;

	public Mesto(Aparat vlasnik) {
		this.vlasnik = vlasnik;
	}

	public void postaviUkus(Ukus u) {
		trenutniUkus = u;
	}

	public synchronized void pokreniTocenje() {
		if (mojaNit == null) {
			mojaNit = new Thread(this);
			tocenjeUToku = true;
			sladoled = new Sladoled(velicinaCase);
			umnozak++;
			mojaNit.start();
		} else {
			tocenjeUToku = true;
			notify();
		}
	}

	public synchronized void zaustaviTocenje() {
		tocenjeUToku = false;
	}

	public synchronized void nastaviTocenje() {
		if (!tocenjeUToku) {
			tocenjeUToku = true;
			notify();
		}
	}

	public synchronized void zavrsiTocenje() {
		tocenjeUToku = false;
		trenutniUkus = null;
		ukusiUSladoledu.clear();
		umnozak = 0;
		Graphics g = getGraphics();
		g.clearRect(0, 0, getWidth(), getHeight());
		mojaNit.interrupt();
	}

	@Override
	public void run() {
		try {
			while (!mojaNit.isInterrupted()) {
				synchronized (this) {
					while (!tocenjeUToku)
						wait();
				}
				while (tocenjeUToku) {
					Thread.sleep(interval);
					if (mojaNit.isInterrupted())
						break;
					sladoled.dodajUkus(20, trenutniUkus);
					vlasnik.postaviSladoled(sladoled.toString());
					repaint();
					if(20*umnozak<velicinaCase) umnozak++;
				}
			}
		} catch (InterruptedException e) {
		}
		synchronized (this) {
			mojaNit = null;
			notify();
		}

	}

	@Override
	public void paint(Graphics g) {
		try {
		if (trenutniUkus != null) {
			Ukus u = new Ukus(trenutniUkus.dohvatiNaziv(), trenutniUkus.dohvatiBoja());
			ukusiUSladoledu.add(u);
			for (int i = 0; i < ukusiUSladoledu.size(); i++) {
				g.setColor(ukusiUSladoledu.get(i).dohvatiBoja());

				/*if ((i) * 20 > velicinaCase)
					g.fillRect(0, 0, getWidth(),
							(int) (getHeight() - (int) ((i) * getHeight() * (20 * 1.0 / velicinaCase))));
				else*/
					g.fillRect(0, getHeight() - (int)((i + 1) * getHeight() * (20 * 1.0 / velicinaCase)), getWidth(),
							(int) (getHeight() * (20 * 1.0 / velicinaCase) + 1));
			}
			if (ukusiUSladoledu.size() * 20 >= velicinaCase)
				vlasnik.omoguciProdaju();
		}
		} catch (Exception e) {
			System.out.println(ukusiUSladoledu.size());
		}
	}

	public void zavrsi() {
		tocenjeUToku = false;
		if (mojaNit != null)
			mojaNit.interrupt();
		while (mojaNit != null) {
			try {
				wait();
			} catch (Exception e) {
			}
		}

	}

	public Sladoled dohvatiSladoled() {
		return sladoled;
	}

	public boolean jelTocenjeUToku() {
		return tocenjeUToku;
	}

}
