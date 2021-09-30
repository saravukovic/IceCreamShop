package sladoleddzinica;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Aparat extends Panel {

	private Panel rightPanel = new Panel(new GridLayout(0, 1, 0, 0));
	private Panel bottomPanel = new Panel();
	private Mesto mesto = new Mesto(this);
	private ArrayList<Ukus> dostupniUkusi = new ArrayList<>();
	private ArrayList<Button> dugmadZaUkuse = new ArrayList<>();
	private Button prodaj = new Button("Prodaj");
	private Panel centerPanel = new Panel(new GridLayout());
	private Label sladoled = new Label("");
	private int rows;

	public void postaviSladoled(String s) {
		sladoled.setText(s);
		bottomPanel.revalidate();
	}

	public Aparat() {
		setPreferredSize(new Dimension(500, 470));
		this.setLayout(new BorderLayout());
		populatePanel();
		setVisible(true);
	}

	private void populatePanel() {
		prodaj.setEnabled(false);
		prodaj.addActionListener(ae -> {
			mesto.zavrsiTocenje();
			System.out.println(mesto.dohvatiSladoled());
			prodaj.setEnabled(false);
			sladoled.setText("");
		});
		rightPanel.add(prodaj);
		rightPanel.add(mesto);
		rightPanel.setPreferredSize(new Dimension(200, 470));
		add(rightPanel, BorderLayout.EAST);

		Label lSl = new Label("Sladoled: ");
		bottomPanel.add(lSl);
		bottomPanel.add(sladoled);
		bottomPanel.setBackground(Color.GRAY);

		add(bottomPanel, BorderLayout.SOUTH);

		centerPanel.setPreferredSize(new Dimension(300, 470));
		centerPanel.setBackground(Color.LIGHT_GRAY);
		add(centerPanel, BorderLayout.CENTER);
	}

	public void dodajUkus(String naziv, int boja) {
		Ukus ukus = new Ukus(naziv, new Color(boja));
		if (dostupniUkusi.contains(ukus)) {
			return;
		} else {
			dostupniUkusi.add(ukus);
			if (dostupniUkusi.size() % 2 == 1)
				rows++;
			Button dugme = new Button(ukus.dohvatiNaziv());
			dugme.setBackground(new Color(boja));
			dugme.addMouseListener(new MouseAdapter() {

				@Override
				public synchronized void mousePressed(MouseEvent e) {
						mesto.postaviUkus(ukus);
						mesto.pokreniTocenje();
				}

				@Override
				public synchronized void mouseReleased(MouseEvent e) {
					mesto.zaustaviTocenje();
				}

			});
			dugmadZaUkuse.add(dugme);
			centerPanel.add(dugme);
			resizeButtons();
		}
	}

	public void omoguciProdaju() {
		prodaj.setEnabled(true);
	}

	private void resizeButtons() {
		for (Button b : dugmadZaUkuse) {
			if (dugmadZaUkuse.size() == 1)
				b.setPreferredSize(new Dimension(centerPanel.getWidth(), centerPanel.getHeight()));
			else if (dugmadZaUkuse.size() == 2) {
				b.setPreferredSize(new Dimension(centerPanel.getWidth(), (int) (centerPanel.getHeight() * 1.0 / 2)));
				centerPanel.setLayout(new GridLayout(2, 1));
			} else {
				if (dugmadZaUkuse.size() == 3)
					centerPanel.setLayout(new GridLayout(0, 2, 0, 0));
				b.setPreferredSize(new Dimension((int) (centerPanel.getWidth() * 1.0 / 2),
						(int) (centerPanel.getHeight() * 1.0 / rows)));
			}
		}
		centerPanel.revalidate();
	}
}
