package pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import base.HiloTiempo;
import base.PanelJuego;

/**
 * Esta pantalla controla la transici�n entre un enemigo y otro. Aparece un cartel en la pantalla con el enemigo
 * @author Aitor Costumero
 *
 */
public class PantallaSiguiente implements Pantalla{
	
	Image fondo = null;
	Image fondoEscalado = null;
	PanelJuego panelJuego;
	String tiempo;
	public static String [] carteles = {"Imagenes/carteles/cartel01.jpg","Imagenes/carteles/cartel02.jpg","Imagenes/carteles/cartel03.jpg","Imagenes/carteles/cartel04.jpg"};
	
	public PantallaSiguiente(PanelJuego panelJuego) {
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
			fondo = ImageIO.read(new File(carteles[panelJuego.enemigoEscogido]));
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
		
			panelJuego.repaint();
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	@Override
	public void moverRaton(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pulsarRaton(MouseEvent e) {
		panelJuego.hiloTiempo = new HiloTiempo();
		panelJuego.ganaMaquina=false;
		panelJuego.heGanado=false;
		panelJuego.disparo = false;
		panelJuego.setPantalla(new PantallaJuego(panelJuego));
		
	}

	@Override
	public void redimensionarPantalla() {
		fondoEscalado = fondo.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(), Image.SCALE_SMOOTH);
		
	}

}
