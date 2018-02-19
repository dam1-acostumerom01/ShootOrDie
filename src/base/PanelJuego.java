package base;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

import pantallas.Pantalla;
import pantallas.PantallaInicial;

/**
 * 
 * @author Aitor Costumero
 * 
 *         Esta clase controla los gr�ficos del juego. Extiende de JPanel para
 *         gestionar los gr�ficos del mismo. Tendr� un hilo que actualizar� los
 *         gr�ficos de la pantalla continuamente. MouseListener lo usaremos para
 *         capturar las pulsaciones del rat�n.
 */

public class PanelJuego extends JPanel implements Runnable, MouseListener, MouseMotionListener, ComponentListener {

	private static final long serialVersionUID = 1L;
	Pantalla pantallaActual;

	/**
	 * Constructor de PanelJuego. - Inicializa el arrayList de cuadrados. - Asigna
	 * el mouse listener que implementa la propia clase. - Inicia un hilo para
	 * actualizar el juego periódicamente.
	 */
	public PanelJuego() {

		pantallaActual = new PantallaInicial(this);

		// MouseMotion
		this.addMouseMotionListener(this);
		// MouseListener:
		this.addMouseListener(this);
		// ComponentListener
		this.addComponentListener(this);

		// Lanzo el hilo.
		new Thread(this).start();
	}

	/**
	 * Sobreescritura del m�todo paintComponent. Este m�todo se llama
	 * autom�ticamente cuando se inicia el componente, se redimensiona o bien cuando
	 * se llama al m�todo "repaint()". Nunca llamarlo directamente.
	 * 
	 * @param g
	 *            Es un Graphics que nos provee JPanel para poder pintar el
	 *            componente a nuestro antojo.
	 */
	@Override
	public void paintComponent(Graphics g) {
		pantallaActual.renderizarPantalla(g);

	}

	@Override
	public void run() {
		while (true) {
			pantallaActual.ejecutarFrame();
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		pantallaActual.pulsarRaton(e);

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// LLamamos al m�todo de mover rat�n de la clase pantalla que est� ejecutando en
		// ese momento
		pantallaActual.moverRaton(e);

	}

	@Override
	public void componentResized(ComponentEvent e) {
		pantallaActual.redimensionarPantalla();

	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	public void setPantalla(Pantalla pantallaActual) {
		this.pantallaActual = pantallaActual;
	}

}
