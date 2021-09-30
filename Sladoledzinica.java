package sladoleddzinica;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class Sladoledzinica extends Frame {
	private Aparat aparat = new Aparat();
	private Panel bottomPanel = new Panel();

	public Sladoledzinica() {
		setBounds(400, 200, 500, 500);
		
		setLayout(new BorderLayout(0, 0));

		setResizable(true);

		setTitle("Sladoleddzinica");

		populateWindow();
		//pack();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				
				dispose();
			}
		});
		
		setVisible(true);
	}

	private void populateWindow() {
		TextField noviNaziv = new TextField(10);
		TextField novaBoja = new TextField(6);
		Button dodaj = new Button("Dodaj ukus");

		dodaj.addActionListener(ae -> {
			if (noviNaziv.getText() == null || novaBoja.getText() == null)
				return;
			aparat.dodajUkus(noviNaziv.getText(), Integer.parseInt(novaBoja.getText(),16));
		});
		
		bottomPanel.setBackground(new Color(Integer.parseInt("81FDF5",16)));
		bottomPanel.add(new Label("Naziv: "));
		bottomPanel.add(noviNaziv);
		bottomPanel.add(new Label("Boja: "));
		bottomPanel.add(novaBoja);
		bottomPanel.add(dodaj);
		
		aparat.setSize(getWidth(), getHeight()-bottomPanel.getHeight());
		
		add(aparat,BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
	}

	public static void main(String[] args) {
		Sladoledzinica s = new Sladoledzinica();
	}

}
