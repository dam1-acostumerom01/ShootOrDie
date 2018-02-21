package pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;

import javax.imageio.ImageIO;

import base.HiloTiempo;
import base.PanelJuego;
import base.Sprite;

public class PantallaJuego implements Pantalla {

	public PanelJuego panelJuego;
	public Image fondo = null;
	public Image fondoEscalado = null;
	public Sprite protagonista;
	public Sprite enemigo;
	public Sprite ready;
	public Sprite steady;
	public Sprite fire;
	public double tiempoProtagonista, tiempoEnemigo, tiempoInicial, tiempoFases;
	public double tiempoDeJuego = -1;
	public HiloTiempo hiloTiempo;
	public HiloTiempo hiloMaquina;
	public HiloTiempo hiloProtagonista;
	Random rd = new Random();
	int aleatorio = rd.nextInt() + 100;
	private DecimalFormat formatoDecimal;
	boolean disparo = false;
	int valor = 0;

	public PantallaJuego(PanelJuego panelJuego) {
		this.panelJuego = panelJuego;
		inicializarPantalla();
		redimensionarPantalla();

	}

	@Override
	public void inicializarPantalla() {
		try {
			
			fondo = ImageIO.read(new File("Imagenes/fondos/fondoduelo.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		double tiempoMaquina = Math.random() + 0.5;

		protagonista = new Sprite(Color.black, 200, 200, (panelJuego.getWidth() / 2) - 100,
				(panelJuego.getHeight() / 2) + 80, "Imagenes/protagonistas/protagonista_03.png");
		enemigo = new Sprite(Color.black, 200, 200, (panelJuego.getWidth() / 2) - 100,
				(panelJuego.getHeight() / 2) - 280, tiempoMaquina, "Imagenes/enemigos/enemigo_02.png");
		formatoDecimal = new DecimalFormat("#.###");
		System.out.println("DISPARO ENEMIGO EN: " + enemigo.getTiempoDisparo());
		tiempoFases = 0;

		hiloTiempo = new HiloTiempo(tiempoMaquina);

	}

	@Override
	public void renderizarPantalla(Graphics g) {
		rellenarFondo(g);
		pintarFases(g);
		protagonista.pintarSpriteEnMundo(g);
		enemigo.pintarSpriteEnMundo(g);
		if (disparo) {
			pintarTiempo(g, valor);
		}
		
	}

	@Override
	public void ejecutarFrame() {
		while (true) {
			panelJuego.repaint();

			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if ((hiloTiempo.isPausa())&&(!disparo)) {
				valor = 2;
				disparo = true;
				try {
					hiloTiempo.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// panelJuego.setPantalla(new PantallaDerrota(panelJuego));
			}

		}
	}

	@Override
	public void moverRaton(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void pulsarRaton(MouseEvent e) {
		
		protagonista.setTiempoDisparo(hiloTiempo.getTiempoDeJuego() / 1000000000);
			valor = 1;
			disparo = true;
			hiloTiempo.setPausa(true);
			try {
				hiloTiempo.join();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	}

	@Override
	public void redimensionarPantalla() {
		fondoEscalado = fondo.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(), Image.SCALE_SMOOTH);
		enemigo.setPosX((panelJuego.getWidth() / 2) - 100);
		protagonista.setPosX((panelJuego.getWidth() / 2) - 100);
		protagonista.setPosY((panelJuego.getHeight() / 2) + 80);
		enemigo.setPosY((panelJuego.getHeight() / 2) - 280);

	}

	private void rellenarFondo(Graphics g) {
		g.drawImage(fondoEscalado, 0, 0, null);
		protagonista.pintarSpriteEnMundo(g);
		enemigo.pintarSpriteEnMundo(g);
	}

	/**
	 * Método que comprueba que se dispara cuando se debe, es decir, después de la
	 * señal. Si disparas antes de tiempo, ese disparo no surte efecto y quedas
	 * expuesto a una muerte segura.
	 * 
	 * @return true si el disparo ha sido ilegal : false si ha sido correcto
	 */
	public boolean disparoIlegal() {
		if (hiloTiempo.getTiempoDeJuego() / 1000000000<0) {
			return true;
		}
		return false;
	}

	/**
	 * Método que pinta en la pantalla las fases Ready, Steady, Fire. Habrá un
	 * tiempo entre ellas, y en la última el retardo es variable. Saldrá de un
	 * aleatorio asignado para que siempre salga distinto.
	 * 
	 * @param g
	 */
	public void pintarFases(Graphics g) {

		if ((tiempoFases <= 50)) {
			ready = new Sprite(Color.BLACK, 200, 100, (panelJuego.getWidth() / 2) - 100,
					(panelJuego.getHeight() / 2) - 50, "Imagenes/fases/ready.png");
			ready.pintarSpriteEnMundo(g);
		}
		if ((tiempoFases >= 100) && (tiempoFases <= 150)) {
			steady = new Sprite(Color.BLACK, 200, 100, (panelJuego.getWidth() / 2) - 100,
					(panelJuego.getHeight() / 2) - 50, "Imagenes/fases/steady.png");
			steady.pintarSpriteEnMundo(g);
		}

		if (tiempoFases == aleatorio) {
			// tiempoDeJuego = 0;
			hiloTiempo.iniciarTiempoDeJuego();

			// Al salir la instrucción fire, comienza el tiempo inicial.
			hiloTiempo.iniciarTiempoInicial();
			hiloTiempo.start();
		}

		if ((tiempoFases >= aleatorio) && (tiempoFases <= (aleatorio + 20))) {
			fire = new Sprite(Color.BLACK, 200, 100, (panelJuego.getWidth() / 2) - 100,
					(panelJuego.getHeight() / 2) - 50, "Imagenes/fases/fire.png");
			fire.pintarSpriteEnMundo(g);
			// Cuando sale la fase FIRE, comienza a contar el tiempo

		}
		tiempoFases++;

	}

	public void pintarTiempo(Graphics g, int valor) {
g.setColor(Color.WHITE);
g.setFont(new Font("Arial", 3, 25));
		switch (valor) {
		case 0:
			break;
		case 1:
			g.drawString(formatoDecimal.format(protagonista.getTiempoDisparo()), panelJuego.getWidth() / 2-60,
					panelJuego.getHeight() / 2 + 80);
			break;
		case 2:
			g.drawString(formatoDecimal.format(enemigo.getTiempoDisparo()), panelJuego.getWidth() / 2-60,
					panelJuego.getHeight() / 2 - 80);
			break;
		}
	}
}
