package pantallas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import base.PanelJuego;

public class PantallaDerrota implements Pantalla{

	private BufferedImage buffer;
	Image fondo = null;
	Image fondoEscalado = null;
	PanelJuego panelJuego;
	String tiempo;
	
	public PantallaDerrota(PanelJuego panelJuego) {
		this.panelJuego = panelJuego;
		inicializarPantalla();
		redimensionarPantalla();
		
	}
	
	
	private void rellenarFondo(Graphics g) {
		g.drawImage(fondoEscalado, 0, 0, null);
	}
	
	@Override
	public void inicializarPantalla() {
		try {
			fondo = ImageIO.read(new File("Imagenes/fondos/gameover.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void renderizarPantalla(Graphics g) {
		rellenarFondo(g);
		
	}

	@Override
	public void ejecutarFrame() {
		while (true) {

			panelJuego.repaint();
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}}
		
	}

	@Override
	public void moverRaton(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pulsarRaton(MouseEvent e) {
		panelJuego.ganaMaquina=false;
		panelJuego.heGanado=false;
		panelJuego.disparo= false;
		panelJuego.setPantalla(new PantallaInicial(panelJuego));
		
	}

	@Override
	public void redimensionarPantalla() {
		fondoEscalado = fondo.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(), Image.SCALE_SMOOTH);
		
	}

}
