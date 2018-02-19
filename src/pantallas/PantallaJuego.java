package pantallas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;

import javax.imageio.ImageIO;

import base.PanelJuego;
import base.Sprite;

public class PantallaJuego implements Pantalla{

	public PanelJuego panelJuego;
	public Image fondo = null;
	public Image fondoEscalado = null;
	public Sprite protagonista;
	public Sprite enemigo;
	public Sprite ready;
	public Sprite steady;
	public Sprite fire;
	public double tiempoProtagonista, tiempoEnemigo,tiempoInicial,tiempoFases;
	public double tiempoDeJuego = -1;
	Random rd = new Random();
	int aleatorio = rd.nextInt(400)+300;
	private DecimalFormat formatoDecimal;
	boolean ganaMaquina = false;
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
		
		double aleatorio = Math.random()+0.2;
		
		
		protagonista = new Sprite(Color.black, 100, 100, (panelJuego.getWidth()/2)-50, (panelJuego.getHeight()/2)+150, "Imagenes/protagonistas/protagonista_03.png");
		enemigo = new Sprite(Color.black, 100, 100, (panelJuego.getWidth()/2)-50, (panelJuego.getHeight()/2)-250,aleatorio, "Imagenes/enemigos/enemigo_02.png");
		formatoDecimal = new DecimalFormat("#.##");
		System.out.println("DISPARO ENEMIGO EN: "+enemigo.getTiempoDisparo());
		tiempoFases=0;
	}

	@Override
	public void renderizarPantalla(Graphics g) {
		rellenarFondo(g);
		pintarFases(g);
		protagonista.pintarSpriteEnMundo(g);
		enemigo.pintarSpriteEnMundo(g);
		
		g.drawString("Tiempo de Juego: "+formatoDecimal.format(tiempoDeJuego / 1000000000), 200,200);
		g.drawString("Tiempo de Juego: "+formatoDecimal.format(tiempoDeJuego / 1000000000), 200,200);
		g.drawString("Tiempo de Juego: "+formatoDecimal.format(tiempoDeJuego / 1000000000), 200,200);
		
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
			actualizarTiempo();
			
		}
	}

	@Override
	public void moverRaton(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void pulsarRaton(MouseEvent e) {
		
	}

	@Override
	public void redimensionarPantalla() {
		fondoEscalado = fondo.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(), Image.SCALE_SMOOTH);
	
	}
	
	private void rellenarFondo(Graphics g) {
		g.drawImage(fondoEscalado, 0, 0, null);
	}
	
	
	public void disparoEnemigo(Graphics g) {
		
			System.out.println(enemigo.getTiempoDisparo());
			
	}
	
	/**
	 * Método que comprueba que se dispara cuando se debe, es decir, después de la señal.
	 * Si disparas antes de tiempo, ese disparo no surte efecto y quedas expuesto a una muerte segura.
	 * @return true si el disparo ha sido ilegal : false si ha sido correcto
	 */
	public boolean disparoIlegal() {
		if (tiempoDeJuego==-1) {
			return true;
		}
		return false;
	}
	
	/**
	 * Método que pinta en la pantalla las fases Ready, Steady, Fire.
	 * Habrá un tiempo entre ellas, y en la última el retardo es variable.
	 * Saldrá de un aleatorio asignado para que siempre salga distinto.
	 * @param g
	 */
	public void pintarFases(Graphics g) {
		
		if((tiempoFases <= 100)) {
			ready = new Sprite(Color.BLACK, 200, 100, (panelJuego.getWidth()/2)-100, (panelJuego.getHeight()/2)-50, "Imagenes/fases/ready.png");
			ready.pintarSpriteEnMundo(g);
		}
		if ((tiempoFases >= 150) && (tiempoFases<=250)) {
			steady = new Sprite(Color.BLACK, 200, 100, (panelJuego.getWidth()/2)-100, (panelJuego.getHeight()/2)-50, "Imagenes/fases/steady.png");
			steady.pintarSpriteEnMundo(g);
		}
		
		if (tiempoFases==aleatorio) {
			tiempoDeJuego = 0;
			tiempoInicial = System.nanoTime();
			disparoEnemigo(g);
		}
		
		if ((tiempoFases>=aleatorio)&&(tiempoFases<=(aleatorio+20))) {
			fire = new Sprite(Color.BLACK, 200, 100, (panelJuego.getWidth()/2)-100, (panelJuego.getHeight()/2)-50, "Imagenes/fases/fire.png");
			fire.pintarSpriteEnMundo(g);
			//Cuando sale la fase FIRE, comienza a contar el tiempo
			
			
		}
		
		tiempoFases++;
		
		
	}
	
	/**
	 * MÃ©todo que actualiza el tiempo que ha transcurrido de juego
	 */
	public void actualizarTiempo() {
		float tiempoActual = System.nanoTime(); // <--AquÃ­ se mide el nuevo tiempo. En esta precisa instrucciÃ³n.
		if ((tiempoDeJuego>=0) && (!ganaMaquina)) {
			tiempoDeJuego = tiempoActual - tiempoInicial;
		}
		
		if (tiempoDeJuego>=(1+enemigo.getTiempoDisparo())) {
			ganaMaquina=true;
		}
		
	}
	
	
	
	}
	

