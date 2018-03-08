package pantallas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import base.HiloTiempo;
import base.PanelJuego;
import base.Sonido;

public class PantallaInicial implements Pantalla {

	Color colorLetra = Color.PINK;
	Image fondo = null;
	Image fondoEscalado = null;
	PanelJuego panelJuego;
	
	
	public PantallaInicial(PanelJuego panelJuego) {
		this.panelJuego = panelJuego;
		inicializarPantalla();
		redimensionarPantalla();
	}
	
	public PantallaInicial() {
		
	}
	
	@Override
	public void inicializarPantalla() {
		panelJuego.musicaInicio = new Sonido("Sonidos/musicaInicio.mp3");
		panelJuego.musicaInicio.start();
		try {
			fondo = ImageIO.read(new File("Imagenes/fondos/pantallaInicio.jpg"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		panelJuego.valor = 0;
		
	}
	
	private void rellenarFondo(Graphics g) {
		g.drawImage(fondoEscalado, 0, 0, null);
	}

	@Override
	public void renderizarPantalla(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, panelJuego.getWidth(), panelJuego.getHeight());
		rellenarFondo(g);
	}
	
	

	@Override
	public void ejecutarFrame() {
			panelJuego.repaint();
	}

	@Override
	public void moverRaton(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pulsarRaton(MouseEvent e) {
		//Al pulsar el ratón, la pantalla cambiará a la pantalla de juego
		//panelJuego.disparo = false;
		
		
		panelJuego.hiloTiempo = new HiloTiempo();
		panelJuego.setPantallaActual(new PantallaJuego(panelJuego));
		panelJuego.musicaInicio.pararMusica();
		
	}

	@Override
	public void redimensionarPantalla() {
		
		if ((panelJuego.getWidth()==0) && (panelJuego.getHeight()==0)) {
			fondoEscalado = fondo.getScaledInstance(600, 400, Image.SCALE_SMOOTH);
		}else {
			
		fondoEscalado = fondo.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(), Image.SCALE_SMOOTH);
		}}

}
