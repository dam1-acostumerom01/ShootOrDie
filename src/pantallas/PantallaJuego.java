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

import base.HiloTiempo;
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
	public HiloTiempo hiloTiempo;
	public HiloTiempo hiloMaquina;
	public HiloTiempo hiloProtagonista;
	Random rd = new Random();
	int aleatorio = rd.nextInt(40)+30;
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
		
		double tiempoMaquina = Math.random()+0.7;
		
		
		protagonista = new Sprite(Color.black, 200, 200, (panelJuego.getWidth()/2)-100, (panelJuego.getHeight()/2)+80, "Imagenes/protagonistas/protagonista_03.png");
		enemigo = new Sprite(Color.black, 200, 200, (panelJuego.getWidth()/2)-100, (panelJuego.getHeight()/2)-280,tiempoMaquina, "Imagenes/enemigos/enemigo_02.png");
		formatoDecimal = new DecimalFormat("#.###");
		System.out.println("DISPARO ENEMIGO EN: "+enemigo.getTiempoDisparo());
		tiempoFases=0;
		
		hiloTiempo = new HiloTiempo(tiempoMaquina);
		
	}

	@Override
	public void renderizarPantalla(Graphics g) {
		rellenarFondo(g);
		pintarFases(g);
		protagonista.pintarSpriteEnMundo(g);
		enemigo.pintarSpriteEnMundo(g);
		
		g.drawString("Tiempo de Juego: "+formatoDecimal.format(hiloTiempo.getTiempoDeJuego() / 1000000000), 200,200);
		
		
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
			if (hiloTiempo.isPausa()) {
				panelJuego.setPantalla(new PantallaDerrota(panelJuego));
			}
			
		}
	}

	@Override
	public void moverRaton(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void pulsarRaton(MouseEvent e) {
		
		if (!ganaMaquina && tiempoDeJuego>0) {
			protagonista.setTiempoDisparo(tiempoDeJuego/1000000000);
			System.out.println("Tiempo prota: "+protagonista.getTiempoDisparo());
			ganaMaquina=true;
			try {
				new Thread().sleep(2000);
			} catch (InterruptedException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			panelJuego.setPantalla(new PantallaVictoria(panelJuego));
		}
		
	}

	@Override
	public void redimensionarPantalla() {
		fondoEscalado = fondo.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(), Image.SCALE_SMOOTH);
		
	}
	
	private void rellenarFondo(Graphics g) {
		g.drawImage(fondoEscalado, 0, 0, null);
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
		
		if((tiempoFases <= 10)) {
			ready = new Sprite(Color.BLACK, 200, 100, (panelJuego.getWidth()/2)-100, (panelJuego.getHeight()/2)-50, "Imagenes/fases/ready.png");
			ready.pintarSpriteEnMundo(g);
		}
		if ((tiempoFases >= 15) && (tiempoFases<=25)) {
			steady = new Sprite(Color.BLACK, 200, 100, (panelJuego.getWidth()/2)-100, (panelJuego.getHeight()/2)-50, "Imagenes/fases/steady.png");
			steady.pintarSpriteEnMundo(g);
		}
		
		if (tiempoFases==aleatorio) {
			//tiempoDeJuego = 0;
			hiloTiempo.iniciarTiempoDeJuego();
			
			//Al salir la instrucción fire, comienza el tiempo inicial.
			//tiempoInicial = System.nanoTime();
			hiloTiempo.iniciarTiempoInicial();
			hiloTiempo.start();
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
		/*float tiempoActual = System.nanoTime(); // <--AquÃ­ se mide el nuevo tiempo. En esta precisa instrucciÃ³n.
		if((tiempoDeJuego>=0)&&(!ganaMaquina)) {
			tiempoDeJuego = tiempoActual - tiempoInicial;
			System.out.println("Tiempo de Juego: "+tiempoDeJuego/ 1000000000);
			
		}*/
		
		//Si el tiempo de juego redondeado llega al valor del disparo del enemigo, se para el tiempo
		/*if ((tiempoDeJuego/ 1000000000)>=enemigo.getTiempoDisparo()) {
			ganaMaquina=true;
			System.out.println("Tiempo de Juego: "+tiempoDeJuego);
			try {
				new Thread().sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			panelJuego.setPantalla(new PantallaDerrota(panelJuego));
		}*/
		
		
		
		
	}
	
	
	
	}
	

