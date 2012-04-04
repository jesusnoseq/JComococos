package jrpsoft;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;



public class Pacman extends Actor implements KeyListener{
	protected static final int PLAYER_SPEED = 1;

	private boolean toggle=true;
	protected boolean up,down,right,left;
	
	protected int movArriba=KeyEvent.VK_UP;
	protected int movAbajo=KeyEvent.VK_DOWN;
	protected int movDerecha=KeyEvent.VK_RIGHT;
	protected int movIzq=KeyEvent.VK_LEFT;
	
	
	public Pacman(Point puntoIncio){
		super(puntoIncio);
		color=Color.yellow;
		diametro=Director.CELL_SIZE;
	}
	
	
	public void paint(Graphics2D g2){
		g2.setColor(color);
        //g2.setStroke(new BasicStroke(4f));	//ponemos un pincel mas normal definimos la elipse y rellenamos
        Point pixelPoint=Director.getPxOfCell(p);
        Ellipse2D pacman = new Ellipse2D.Float(pixelPoint.x,pixelPoint.y,diametro,diametro);
        g2.fill(pacman);
        g2.setColor(Color.BLACK);

		if(toggle){
			if(right){
				g2.fillArc(pixelPoint.x,pixelPoint.y, diametro,diametro, -35, 70);
			}else if(left){
				g2.fillArc(pixelPoint.x,pixelPoint.y, diametro,diametro, 145, 70);
			}else if(up){
				g2.fillArc(pixelPoint.x,pixelPoint.y, diametro,diametro,55, 70);
			}else if(down){
				g2.fillArc(pixelPoint.x,pixelPoint.y, diametro,diametro, 235, 70);
			}
			else{
				//g2.fillArc(pixelPoint.x,pixelPoint.y, diametro,diametro, -35, 70);
			}
        	toggle=false;
        }
		else{
			toggle=true;
		}
	}

	public void mover(Tablero t){
		Point anterior=(Point) p.clone();
		if(up){
			p.y-=PLAYER_SPEED;
		}
		else if(down){
			p.y+=PLAYER_SPEED;
		}
		else if(right){
			p.x+=PLAYER_SPEED;
		}
		else if(left){
			p.x-=PLAYER_SPEED;
		}
		if(!t.isWalkable(p)){
			p=anterior;
		}
	}

	public void keyReleased(KeyEvent e) {
		//System.out.println(e.getKeyCode() + "liberado");
		if (e.getKeyCode() == movArriba) up = false;
		else if (e.getKeyCode() == movAbajo) down = false;
		else if (e.getKeyCode() == movDerecha) right = false;
		else if (e.getKeyCode() == movIzq) left = false;	
	}
		
	public void keyPressed(KeyEvent e) {
		//System.out.println(e.getKeyCode() + "pulsado");
		if (e.getKeyCode() == movArriba) up = true;
		else if (e.getKeyCode() == movAbajo){ down = true; }
		else if (e.getKeyCode() == movDerecha){ right = true; }
		else if (e.getKeyCode() == movIzq){ left = true; }
 	}
	
	@Override
	public void keyTyped(KeyEvent arg0) {}
}
