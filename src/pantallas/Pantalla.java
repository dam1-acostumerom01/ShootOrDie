package pantallas;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

public interface Pantalla {

	
	/**
	 * Método que inicializa una pantalla.
	 * Por ejemplo, los asteroides y el fondo se cargarían aquí.
	 */
	public void inicializarPantalla();
	
	/**
	 * Método que determina cómo se pinta una pantalla.
	 */
	public void renderizarPantalla(Graphics g);
		
	/**
	 * Determina qué es ocurren en la pantalla en cada frame
	 */
	public void ejecutarFrame();
	
	public void moverRaton(MouseEvent e);
	
	public void pulsarRaton(MouseEvent e);
	
	public void redimensionarPantalla();
	
}
