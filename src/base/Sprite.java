package base;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Sprite {

	private BufferedImage buffer;
	private Color color;
	private int ancho;
	private int alto;
	private int posX;
	private int posY;
	private double tiempoDisparo;
	public Sprite() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Sprite(Color color, int ancho, int alto, int posX, int posY, String ruta) {
		super();
		this.color = color;
		this.ancho = ancho;
		this.alto = alto;
		this.posX = posX;
		this.posY = posY;
		actualizarBuffer(ruta);
	}
	
	
	
	
	public Sprite(Color color, int ancho, int alto, int posX, int posY, double tiempoDisparo,String ruta) {
		super();
		this.color = color;
		this.ancho = ancho;
		this.alto = alto;
		this.posX = posX;
		this.posY = posY;
		this.tiempoDisparo = tiempoDisparo;
		actualizarBuffer(ruta);
	}
	public double getTiempoDisparo() {
		return tiempoDisparo;
	}
	public void setTiempoDisparo(double tiempoDisparo) {
		this.tiempoDisparo = tiempoDisparo;
	}
	public BufferedImage getBuffer() {
		return buffer;
	}
	public void setBuffer(BufferedImage buffer) {
		this.buffer = buffer;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
		actualizarBuffer(null);
	}
	public int getAncho() {
		return ancho;
	}
	public void setAncho(int ancho) {
		this.ancho = ancho;
	}
	public int getAlto() {
		return alto;
	}
	public void setAlto(int alto) {
		this.alto = alto;
	}
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	
	public void actualizarBuffer(String ruta) {
		//Creo un nuevo buffer del tamaño adecuado
				buffer = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
				Graphics g = buffer.getGraphics();
				
				//Intento pintarlo con una imagen
				Image imagenAuxiliar = null;
				try {
					imagenAuxiliar = ImageIO.read(new File(ruta));
					imagenAuxiliar = imagenAuxiliar.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
					g.drawImage(imagenAuxiliar, 0, 0, null);
					return;
				} catch (Exception e) {
					// Si no pinto un cuadrado del color por defecto
					g.setColor(color);
					g.fillRect(0, 0, ancho, alto);
					g.dispose();
	}}
	
	public void pintarSpriteEnMundo(Graphics g){
		g.drawImage(buffer, posX, posY, null);
	}
	
	
	
	
}
