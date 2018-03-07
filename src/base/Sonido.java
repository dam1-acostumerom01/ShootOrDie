package base;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;


public class Sonido extends Thread {
	String rutaSonido;
	Player player;

	public Sonido(String rutaSonido) {

		this.rutaSonido = rutaSonido;
	}

	@Override
	public void run() {
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		try {

			fis = new FileInputStream(rutaSonido);
			bis = new BufferedInputStream(fis);
			player = new Player(bis);
			player.play();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			bis.close();
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void pararMusica() {
		player.close();
		
	}

}
