package jrpsoft;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;


public class Tablero {
    String rejilla[] = {
        	"BBBBBBBBBBBBBBBBBBBBBBBBBBBB",
            "B............BB............B",
            "B.BBBB.BBBBB.BB.BBBBB.BBBB.B",
            "B.BBBB.BBBBB.BB.BBBBB.BBBB.B",
            "B.BBBB.BBBBB.BB.BBBBB.BBBB.B",
            "B..........................B",
            "B.BBBB.BB.BBBBBBBB.BB.BBBB.B",
            "B.BBBB.BB.BBBBBBBB.BB.BBBB.B",
            "B......BB....BB....BB......B",
            "BBBBBB.BBBBB BB BBBBB.BBBBBB",
            "     B.BBBBB BB BBBBB.B     ",
            "     B.BB          BB.B     ",
            "     B.BB BB____BB BB.B     ",
            "BBBBBB.BB B      B BB.BBBBBB",
            "      .   B      B   .      ",
            "BBBBBB.BB B F  F B BB.BBBBBB",
            "     B.BB BBBBBBBB BB.B     ",
            "     B.BB          BB.B     ",
            "     B.BB BBBBBBBB BB.B     ",
            "BBBBBB.BB BBBBBBBB BB.BBBBBB",
            "B............BB............B",
            "B.BBBB.BBBBB.BB.BBBBB.BBBB.B",
            "B.BBBB.BBBBB.BB.BBBBB.BBBB.B",
            "B...BB.......X........BB...B",
            "BBB.BB.BB.BBBBBBBB.BB.BB.BBB",
            "BBB.BB.BB.BBBBBBBB.BB.BB.BBB",
            "B......BB....BB....BB......B",
            "B.BBBBBBBBBB.BB.BBBBBBBBBB.B",
            "B.BBBBBBBBBB.BB.BBBBBBBBBB.B",
            "B..........................B",
            "BBBBBBBBBBBBBBBBBBBBBBBBBBBB"};
	/*String rejilla[] = {
			" FBBB",
			"B . B",
			"BBB B",
			"BX  B",
			"BBBBB",
			"BBBBB"
		};*/
	/*String rejilla[] = {
			"BBBBBBB",
			"B    FB",
			"B B B B",
			"BBB BBB",
			"B B B B",
			"BX    B",
			"BBBBBBB"
		};*/
	
    // Tipos de celdas
	public static final byte BLOQUEADO=0;
	public static final byte ANDABLE=1;
	static final char BLOQUE = 'B';
	static final char CASILLA = '.';
	static final char VACIA = ' ';
	static final char COMIDA = 'o';
	static final char PUERTA = '_';
	static final char PACMAN = 'X';
	static final char FANTASMA = 'F';

	public Tablero() {
	}

	public String[] getRejilla() {
		return rejilla;
	}

	public byte[][] getMap() {
		byte[][] map = new byte[getHeight()][getWidth()];
		for (int y = 0; y < getHeight(); y++) {
			System.out.println();
			for (int x = 0; x < getWidth(); x++) {
				if (isWalkable(x, y)) {
					map[y][x] = ANDABLE;
				} else {
					map[y][x] = BLOQUEADO;
				}
				//System.out.print(map[y][x]);
			}
		}
		//System.out.println();
		return map;
	}

	public void paint(Graphics2D g2) {
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				Point p = new Point(x, y);
				char cellType = TipeOf(p);
				switch (cellType) {
				case BLOQUE:
					blockPaint(g2, p);
					break;
				case CASILLA:
					slotPaint(g2, p);
					break;
				case COMIDA:
					foodPaint(g2, p);
					break;
				case PUERTA:
					doorPaint(g2, p);
					break;
				case FANTASMA:
				case PACMAN:
				case VACIA:
					nullSlotPaint(g2, p);
					break;
				default:
					break;
				}
			}
		}
	}

	private void blockPaint(Graphics2D g2, Point p) {
		g2.setColor(Color.BLUE);
		Point pixelPoint = Director.getPxOfCell(p);

		g2.fillRect(pixelPoint.x, pixelPoint.y, pixelPoint.x
				+ Director.CELL_SIZE, pixelPoint.y + Director.CELL_SIZE);

		g2.setColor(Color.BLACK);
		g2.drawRect(pixelPoint.x, pixelPoint.y, pixelPoint.x
				+ Director.CELL_SIZE, pixelPoint.y + Director.CELL_SIZE);
	}

	private void foodPaint(Graphics2D g2, Point p) {
		nullSlotPaint(g2, p);

		g2.setColor(Color.YELLOW);
		Point pixelPoint = Director.getPxOfCell(p);
		int diametro = Director.CELL_SIZE / 2;
		center(pixelPoint, diametro);
		Ellipse2D food = new Ellipse2D.Float(pixelPoint.x, pixelPoint.y,
				diametro, diametro);
		g2.fill(food);
	}

	private void slotPaint(Graphics2D g2, Point p) {
		nullSlotPaint(g2, p);

		g2.setColor(Color.YELLOW);
		Point pixelPoint = Director.getPxOfCell(p);
		int diametro = Director.CELL_SIZE / 4;
		center(pixelPoint, diametro);
		Ellipse2D slot = new Ellipse2D.Float(pixelPoint.x, pixelPoint.y,
				diametro, diametro);
		g2.fill(slot);
	}

	private void nullSlotPaint(Graphics2D g2, Point p) {

		g2.setColor(Color.BLACK);
		Point pixelPoint = Director.getPxOfCell(p);
		g2.fillRect(pixelPoint.x, pixelPoint.y, pixelPoint.x
				+ Director.CELL_SIZE, pixelPoint.y + Director.CELL_SIZE);
	}

	private void doorPaint(Graphics2D g2, Point p) {
		nullSlotPaint(g2, p);

		g2.setColor(Color.BLUE);
		Point pixelPoint = Director.getPxOfCell(p);
		g2
				.fillRect(pixelPoint.x, pixelPoint.y, pixelPoint.x
						+ (Director.CELL_SIZE), pixelPoint.y
						+ (Director.CELL_SIZE / 2));

		g2.setColor(Color.BLACK);
		g2.fillRect(pixelPoint.x, pixelPoint.y + (Director.CELL_SIZE / 2),
				pixelPoint.x + Director.CELL_SIZE, pixelPoint.y
						+ Director.CELL_SIZE);

	}

	private void center(Point pixelPoint, int diametro) {
		pixelPoint.x += (Director.CELL_SIZE / 2) - (diametro / 2);
		pixelPoint.y += (Director.CELL_SIZE / 2) - (diametro / 2);
	}

	public boolean isWalkable(int x, int y) {
		return isWalkable(new Point(x, y));
	}

	public boolean isWalkable(Point p) {
		if (p.y >= getHeight() || p.x >= getWidth() || p.x < 0 || p.y < 0) {
			return false;
		}
		char typeOfCell = TipeOf(p);
		return !(typeOfCell == BLOQUE);
	}

	public char TipeOf(Point p) {
		return rejilla[p.y].charAt(p.x);
	}

	public int getWidth() {
		// System.out.println( "ancho total "+rejilla[0].length());
		return rejilla[0].length();
	}

	public int getHeight() {
		// System.out.println( "alto total "+rejilla.length);
		return rejilla.length;
	}

	public Point getPacmanPos() {
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				Point p = new Point(x, y);
				if (TipeOf(p) == PACMAN) {
					// System.out.println("alto "+p.y+ " ancho "+p.x);
					return p;
				}
			}
		}

		return null;
	}

	public Point getFantasmaPos() {
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				Point p = new Point(x, y);
				if (TipeOf(p) == FANTASMA) {
					//System.out.println("alto "+p.y+ " ancho "+p.x);
					return p;
				}
			}
		}

		return null;
	}

	public void actualizar(Point pacmanPos) {
		char pacmanIn = TipeOf(pacmanPos);
		if (pacmanIn == COMIDA || pacmanIn == CASILLA) {
			String fila = rejilla[pacmanPos.y];
			char[] filaArray = fila.toCharArray();
			filaArray[pacmanPos.x] = VACIA;
			// System.out.println("entra "+filaArray[0] +" "+fila+" ");
			fila = "";
			for (int i = 0; i < filaArray.length; i++) {
				fila += filaArray[i];
			}
			rejilla[pacmanPos.y] = fila;
		}
	}

}
