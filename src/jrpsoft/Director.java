package jrpsoft;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Line2D;

@SuppressWarnings("serial")
public class Director extends Applet implements Runnable {
	public static final int CELL_SIZE = 20;
	public static final long FRAME_DELAY=(1000/7);
	private long cycleTime=0L, difference=0L;
	public static Tablero tablero;
	Pacman pacman;
	private static boolean pillado=false;
	// FantasmaPlayer fantasma; // crea un fantasma controlado por el teclado
	Fantasma fantasma;

	// hilo principal del juego
	Thread gameThread;

	// doble bufer
	Image imagen;
	Graphics2D gBuffer;

	public void init() {
		tablero = new Tablero();
		pacman = new Pacman(tablero.getPacmanPos());
		fantasma = new Fantasma(tablero.getFantasmaPos(), Color.magenta);
		Astar.getAstar().setMap(tablero.getMap());
		// fantasma= new FantasmaPlayer(tablero.getFantasmaPos(),Color.magenta);
		// this.addKeyListener(fantasma);
		this.addKeyListener(pacman);

		// tanaño y color de fondo del applet
		setBackground(Color.black);
		setSize(tablero.getWidth() * CELL_SIZE, tablero.getHeight() * CELL_SIZE);
		setVisible(true);
		setFocusable(true);

		// definimos lo threads
		gameThread = new Thread(this, "gameThread"); // creo el hilo que marcará
													 // el ritmo al repintar
		// minima prioridad para que antes se actualicen las agujas y despues
		// repintar
		gameThread.setPriority(Thread.MIN_PRIORITY);
		gameThread.start(); // iniciamos el hilo
	}

	/*
	public void stop() {	
	}

	public void paint(Graphics g) {
		g.drawString("PACMAN!",200 ,tablero.getHeight()*CELL_SIZE/2);
	}
	*/
	
	public void update(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if (gBuffer == null) {
			imagen = createImage(tablero.getWidth() * CELL_SIZE, tablero
					.getHeight()
					* CELL_SIZE);
			gBuffer = (Graphics2D) imagen.getGraphics();
		}

		tablero.paint(gBuffer);
		pacman.paint(gBuffer);
		fantasma.paint(gBuffer);
		
		if(pillado){
			pintarFin(gBuffer);
		}

		// pintarCuadricula((Graphics2D)gBuffer);

		// transfiere la imagen al contexto gráfico del applet
		g2.drawImage(imagen, 0, 0, null);
	}

	@SuppressWarnings("unused")
	private void pintarCuadricula(Graphics2D g2) {
		g2.setColor(Color.white);
		for (int i = 0; i < tablero.getHeight(); i++) {
			Line2D linea = new Line2D.Float(new Point(0, i * CELL_SIZE),
					new Point(i * CELL_SIZE, (tablero.getWidth() + 4)
							* CELL_SIZE));
			g2.draw(linea);
			g2.drawString("line " + i, 0, i * CELL_SIZE);
		}
	}
	private void pintarFin(Graphics2D g2){
		g2.setFont(new Font("Arial", Font.ITALIC, 44));
		g2.setColor(Color.white);
		g2.drawString("¡Te han pillado!", 10, 40);
		g2.setColor(Color.red);
		g2.drawString("Te han pillado!", 9, 39);
	}
	
	
	public static Point getPxOfCell(Point cellPoint) {
		Point pixelPoint = new Point(cellPoint.x * CELL_SIZE, cellPoint.y
				* CELL_SIZE);
		return pixelPoint;
	}

	// TODO quita ese cutre equals!!
	private boolean choca(Pacman p, Fantasma f) {
		return (p.getPoint().equals(f.getPoint()));
	}

	public void run() {
		while (true) { // bucle infinito
			if (choca(pacman, fantasma)) {
				pillado=true;
			}else{
				pacman.mover(tablero);
				fantasma.mover(pacman, tablero);
			}
			cycleTime=System.currentTimeMillis();
			tablero.actualizar(pacman.getPoint());
			repaint(); // repinto el applet

			cycleTime = cycleTime + FRAME_DELAY;
			difference = cycleTime - System.currentTimeMillis();
			try {
				//System.err.println("duerme:"+Math.max(0, difference));
				Thread.sleep(Math.max(0, difference));
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}


/*
		private void updateGUI(BufferStrategy strategy) {
			Graphics g = strategy.getDrawGraphics();
 
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, gui.getWidth(), gui.getHeight());
			g.setColor(Color.BLACK);
 
			g.drawLine(lineX, 0, lineX + 10, 0); // arbitrary rendering logic
			g.dispose();
			strategy.show();
		}
 
		private void synchFramerate() {
			cycleTime = cycleTime + FRAME_DELAY;
			long difference = cycleTime - System.currentTimeMillis();
			try {
				Thread.sleep(Math.max(0, difference));
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
		}

*/
