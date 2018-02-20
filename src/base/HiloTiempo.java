package base;

public class HiloTiempo extends Thread{

	public double tiempoInicial = -1;
	public double tiempoDeJuego = -1;
	public double disparo;
	boolean pausa = false;
	public HiloTiempo() {
	}
	
	

	public HiloTiempo(double tiempoInicial, double tiempoDeJuego) {
		this.tiempoInicial = tiempoInicial;
		this.tiempoDeJuego = tiempoDeJuego;
	}

	public HiloTiempo(double disparo) {
		this.disparo = disparo;
	}


	public double getTiempoInicial() {
		return tiempoInicial;
	}



	public void setTiempoInicial(double tiempoInicial) {
		this.tiempoInicial = tiempoInicial;
	}



	public double getTiempoDeJuego() {
		return tiempoDeJuego;
	}



	public void setTiempoDeJuego(double tiempoDeJuego) {
		this.tiempoDeJuego = tiempoDeJuego;
	}

	public void iniciarTiempoInicial() {
		tiempoInicial = System.nanoTime();
	}
	
	public void iniciarTiempoDeJuego() {
		tiempoDeJuego = 0;
	}
	
	public synchronized void pararTiempo() {
		while (tiempoDeJuego/1000000000>=disparo) {
			pausa = true;
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		float tiempoActual = System.nanoTime();
		tiempoDeJuego = tiempoActual - tiempoInicial;
		System.out.println("Tiempo de Juego: "+tiempoDeJuego/ 1000000000);
		
		
	}

	public double getDisparo() {
		return disparo;
	}



	public void setDisparo(double disparo) {
		this.disparo = disparo;
	}



	public boolean isPausa() {
		return pausa;
	}



	public void setPausa(boolean pausa) {
		this.pausa = pausa;
	}



	@Override
	public void run() {
		while(!pausa) {
		pararTiempo();
		
	}
		try {
			sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
