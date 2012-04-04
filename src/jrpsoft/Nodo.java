package jrpsoft;
import java.awt.Point;

public class Nodo {
	private Point p;
	private Nodo padre;
	int g = 0, h = 0;

	public Nodo(Point p, Nodo padre, int g, int h) {
		this.p = p;
		this.padre = padre;
		this.g = g;
		this.h = h;
	}

	public Nodo(Point p, int g, int h) {
		this.p = p;
		this.padre = this;
		this.g = g;
		this.h = h;
	}

	public Nodo getPadre() {
		return padre;
	}

	public void setPadre(Nodo padre) {
		this.padre = padre;
	}

	public Point getPoint() {
		return p;
	}

	public int getX() {
		return p.x;
	}

	public int getY() {
		return p.y;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int getF() {
		return g + h;
	}

	public String toString() {
		return "[" + p.x + "," + p.y + "] - padre[" + padre.getPoint().x + ","
				+ padre.getPoint().y + "]" + " - G:" + g + " H:" + h;
	}

	public boolean equals(Nodo n) {
		// return p.equals(n.p);
		return compare(p, n.p);// (p.x==n.getPoint().x && p.y==n.getPoint().y);
	}

	public boolean equals(Point b) {
		return compare(p, b);
	}

	public boolean compare(Point a, Point b) {
		return (a.x == b.x && a.y == b.y);
	}

	public boolean esPadre() {
		// return (p.x==padre.x && p.y==padre.y);
		return equals(padre);
	}

	public boolean esDiagonal(Point pd) {
		int xDif = Math.abs(p.x - pd.x);
		int yDif = Math.abs(p.y - pd.y);
		return ((xDif == yDif) && xDif != 0 && yDif != 0);
	}

}
