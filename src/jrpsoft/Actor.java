package jrpsoft;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;


public abstract class Actor{
	public static final int UP = -1;
	public static final int DOWN = +1;
	public static final int LEFT = -1;
	public static final int RIGHT = +1;
	public static final int DUP = 1;
	public static final int DDOWN = 2;
	public static final int DLEFT = 3;
	public static final int DRIGHT = 4;
	
	
	protected Point p;
	protected Color color;
	protected int diametro=Director.CELL_SIZE; 
	
	public Actor(Point puntoIncio) {
		p=puntoIncio;
	}
	
	public Actor(Point puntoIncio, Color colorPrincipal ) {
		p=puntoIncio;
		color=colorPrincipal;
	}
	
	public void paint(Graphics2D g2){
		g2.setColor(color);
	}
	
	public void mover(){}
	
	public Point getPoint(){ return p; }
	
	
/**
 * 
 * @param np punto al que se quiere llegar
 * @return direccion a tomar para llegar al punto
 */
	public int getDirToPoint(Point np) {
		int dir = 0;
		if (np != null) {
			// cuidado, con los indices de las matrices he cosiderado
			// la x como la fila y la como columna... es un relio
			int yDir = np.x - p.x;
			int xDir = np.y - p.y;
			if 		(xDir == UP) 	{	dir = DUP;	} 
			else if (xDir == DOWN)  {	dir = DDOWN;	} 
			else if (yDir == LEFT)	{	dir = DLEFT;	} 
			else if (yDir == RIGHT) {	dir = DRIGHT;	}
			/*
			System.out.println("Punto org: "+p);
			System.out.println("Punto dest: "+np);
			System.out.println("diferencia: x:"+xDir+" - y:"+yDir+"-");
			System.out.println("Direccion: "+dir);
			*/
		}
		return dir;
	}
}
/*
if 		(xDir == UP) {	dir = 3;	} 
else if (xDir == 1)  {	dir = 4;	} 
else if (yDir == -1) {	dir = 1;	} 
else if (yDir == 1)  {	dir = 2;	}
*/