package jrpsoft;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class Fantasma extends Actor {
	protected static final int FANTASMA_SPEED = 1;

	public boolean up, down, right, left;

	static Boolean[] dir = new Boolean[4];

	int avazarA = 0;

	Random random;

	public Fantasma(Point puntoIncio, Color colorPrincipal) {
		super(puntoIncio, colorPrincipal);
		random = new Random();
	}

	public Point getPoint() {
		return p;
	}

	public void paint(Graphics2D g2) {
		g2.setColor(color);
		Point pixelPoint = Director.getPxOfCell(p);
		Ellipse2D fantasma = new Ellipse2D.Float(pixelPoint.x, pixelPoint.y,
				diametro, diametro);
		g2.fill(fantasma);

		g2.fill(new Ellipse2D.Float(pixelPoint.x - 1, pixelPoint.y + 12, diametro / 2, diametro / 2));
		g2.fill(new Ellipse2D.Float(pixelPoint.x + 5, pixelPoint.y + 12, diametro / 2, diametro / 2));
		g2.fill(new Ellipse2D.Float(pixelPoint.x + 11, pixelPoint.y + 12, diametro / 2, diametro / 2));
	}

	public void mover(Pacman pacman, Tablero tablero) {
		/*
		 * System.out.println("ee "+(random.nextInt(5)));
		 * if(random.nextInt(5)==0){ avanzar((random.nextInt(4)+1),tablero); }
		 */
		// avazarA=movAleatorio(tablero);
		//System.err.println(p);
		// avazarA=0;
		
		Astar.getAstar().getPath(p, pacman.p);
		Point nextPoint=Astar.getAstar().getNextPoint();
		avanzar(getDirToPoint(nextPoint), tablero);
	}

	/*@SuppressWarnings("unused")
	private int movAleatorio(Tablero tablero) {
		Point aux = (Point) p.clone();
		int randDir = 0;
		do {
			aux = reverseTranslateDir(aux, randDir);
			randDir = random.nextInt(4) + 1;
			translateDir(aux, randDir);
			// System.out.print("\nwhiling"+randDir+" px:"+aux.x+"  py:"+aux.y);
		} while (!tablero.isWalkable(aux));
		return randDir;
	}*/

	private void avanzar(int dir, Tablero tablero) {
		p=translateDir(p,dir);
		/*Point anterior = (Point) p.clone();
		translateDir(p, dir);
		if (!tablero.isWalkable(p)) {
			p = anterior;
		}*/
	}

	public Point translateDir(Point p, int dir) {
		switch (dir) {
		case DUP:
			p.y += UP;
			break;
		case DDOWN:
			p.y += DOWN;
			break;
		case DLEFT:
			p.x += LEFT;
			break;
		case DRIGHT:
			p.x += RIGHT;
			break;
		default:
			break;
		}
		return p;
	}
/*
	public Point reverseTranslateDir(Point p, int dir) {
		switch (dir) {
		case DUP:
			p.y -= UP;
			break;
		case DDOWN:
			p.y -= DOWN;
			break;
		case DLEFT:
			p.x -= LEFT;
			break;
		case DRIGHT:
			p.x -= RIGHT;
			break;
		default:
			break;
		}
		return p;
	}
	*/
}
