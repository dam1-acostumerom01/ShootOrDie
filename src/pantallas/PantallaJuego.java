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
import base.Sonido;
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
	Random rd = new Random();
	int aleatorio = rd.nextInt(100) + 100;
	private DecimalFormat formatoDecimal;
	public Sonido miDisparo;
	public Sonido disparoMaquina;
	
	
	

	public PantallaJuego(PanelJuego panelJuego) {
		this.panelJuego = panelJuego;
		
		inicializarPantalla();
		redimensionarPantalla();
		System.out.println("Aleatorio: " + aleatorio);
		
	}

	@Override
	public void inicializarPantalla() {
		
		try {

			fondo = ImageIO.read(new File("Imagenes/fondos/fondoduelo.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		//Inicializo el Sprite del Protagonista
		protagonista = new Sprite(Color.black, 200, 200, (panelJuego.getWidth() / 2) - 100,
				(panelJuego.getHeight() / 2) + 80, "Imagenes/protagonistas/protagonista_03.png");
		
		//Inicializo el Sprite del enemigo
		enemigo = new Sprite(Color.black, 200, 200, (panelJuego.getWidth() / 2) - 100,
				(panelJuego.getHeight() / 2) - 280, panelJuego.hiloTiempo.getDisparo(), "Imagenes/enemigos/enemigo_04.png");
		
		//Creo un formato decimal para mostrar el tiempo
		formatoDecimal = new DecimalFormat("#.###");
		System.out.println("DISPARO ENEMIGO EN: " + enemigo.getTiempoDisparo());
		//Inicializo el tiempo de fases
		tiempoFases = 0;
	}

	@Override
	public void renderizarPantalla(Graphics g) {
		System.out.println("Valor?   --> "+panelJuego.valor);
		rellenarFondo(g);
		pintarFases(g);
		protagonista.pintarSpriteEnMundo(g);
		enemigo.pintarSpriteEnMundo(g);
		if (panelJuego.disparo) {
			pintarTiempo(g, panelJuego.valor);
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
			//Si el tiempo ha dejado de contar y no ha habido disparo, gana la máquina
			if ((panelJuego.hiloTiempo.isPausa()) && (!panelJuego.disparo)) {
				protagonista.actualizarBuffer("Imagenes/protagonistas/protagonistadesarmado_03.png");
				enemigo.actualizarBuffer("Imagenes/enemigos/enemigodisparando_04.png");
				panelJuego.valor = 2; // se pinta el tiempo en la parte del sprite maquina
				panelJuego.disparo = true;
			}
			pintarPantallaVictoria();
			pintarPantallaDerrota();
		}
	}

	@Override
	public void moverRaton(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void pulsarRaton(MouseEvent e) {
		miDisparo = new Sonido("Sonidos/disparo1.mp3");
		miDisparo.start();
		protagonista.setTiempoDisparo(panelJuego.hiloTiempo.getTiempoDeJuego() / 1000000000);
		
		
		
		if (!panelJuego.ganaMaquina) {
			protagonista.actualizarBuffer("Imagenes/protagonistas/protagonistadisparando_03.png");
			enemigo.actualizarBuffer("Imagenes/enemigos/enemigodesarmado_04.png");
			panelJuego.valor = 1;
			panelJuego.heGanado = true;
		}
		
		panelJuego.disparo = true;
		panelJuego.hiloTiempo.setPausa(true);
		
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
		if (panelJuego.hiloTiempo.getTiempoDeJuego() / 1000000000 < 0) {
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
			panelJuego.hiloTiempo.iniciarTiempoDeJuego();

			// Al salir la instrucción fire, comienza el tiempo inicial.
			panelJuego.hiloTiempo.iniciarTiempoInicial();
			panelJuego.hiloTiempo.start();
		}

		if ((tiempoFases >= aleatorio) && (tiempoFases <= (aleatorio + 20))) {
			fire = new Sprite(Color.BLACK, 200, 100, (panelJuego.getWidth() / 2) - 100,
					(panelJuego.getHeight() / 2) - 50, "Imagenes/fases/fire.png");
			fire.pintarSpriteEnMundo(g);
			// Cuando sale la fase FIRE, comienza a contar el tiempo

		}
		tiempoFases++;

	}

	public void pintarPantallaVictoria() {
		
		if (panelJuego.heGanado) {
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			panelJuego.disparo = false;
			panelJuego.setPantalla(new PantallaVictoria(panelJuego));
		}
	}

	public void pintarPantallaDerrota() {
		if (panelJuego.ganaMaquina) {
			try {
				Thread.sleep(2000);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			panelJuego.disparo = false;
			panelJuego.setPantalla(new PantallaDerrota(panelJuego));
		}
	}

	public void pintarTiempo(Graphics g, int valor) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", 3, 25));
		switch (valor) {
		case 0:
			break;
		case 1:
			g.drawString(formatoDecimal.format(protagonista.getTiempoDisparo()), panelJuego.getWidth() / 2 - 20,
					panelJuego.getHeight() / 2 + 100);
			panelJuego.heGanado = true;
			break;
		case 2:
			g.drawString(formatoDecimal.format(enemigo.getTiempoDisparo()), panelJuego.getWidth() / 2 - 20,
					panelJuego.getHeight() / 2 - 90);
			disparoMaquina = new Sonido("Sonidos/disparo2.mp3");
			disparoMaquina.start();
			panelJuego.ganaMaquina = true;
			break;
		}
	}
}
