package jrpsoft;
import java.awt.Point;


public class Cuadro {
	Point p;
	Point padre;
	int g,h;
	
	
	public Cuadro(Point p,Point padre,int g,int h) {
		this.p=p;
		this.padre=padre;
		this.g=g;
		this.h=h;
	}

	public String toString() {
		return "Cuadro[ x:"+p.x+" y:"+p.y +"H:"+h+"]->"+padre.toString();
	}
	
	public int getF(){
		return g+h;
	}
	
	public boolean compare(Point punto) {
		return (p.x==punto.x && p.y==punto.y);
	}
	
	public boolean compare(Cuadro cu) {
		return (compare(cu.p));		
	}
	public boolean esPadre(){
		//return (p.x==padre.x && p.y==padre.y);
		return compare(padre);
	}
}
