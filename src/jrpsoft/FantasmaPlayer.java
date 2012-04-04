package jrpsoft;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class FantasmaPlayer extends Fantasma implements KeyListener{
	protected int movArriba=KeyEvent.VK_W;
	protected int movAbajo=KeyEvent.VK_S;
	protected int movDerecha=KeyEvent.VK_D;
	protected int movIzq=KeyEvent.VK_A;
	
	public FantasmaPlayer(Point puntoIncio, Color colorPrincipal){
		super(puntoIncio,colorPrincipal);
	}
	
	
	public void mover(Pacman pacman,Tablero t){
		Point anterior=(Point) p.clone();
		if(up){
			p.y-=FANTASMA_SPEED;
		}
		if(down){
			p.y+=FANTASMA_SPEED;
		}
		if(right){
			p.x+=FANTASMA_SPEED;
		}
		if(left){
			p.x-=FANTASMA_SPEED;
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
