package jrpsoft;
import java.awt.Point;
import java.util.ArrayList;

public class Astar {

	static Astar astar = null;
	byte[][] map = null;								// mapa 
	ArrayList<Nodo> abierta = new ArrayList<Nodo>();	// lista de nodos explorados
	ArrayList<Nodo> cerrada = new ArrayList<Nodo>();	// lista de nodos frontera
	Nodo a; // nodo de partida
	Nodo b; // nodo objetivo

	private Astar() {
		abierta = new ArrayList<Nodo>();
		cerrada = new ArrayList<Nodo>();
	}

	public static Astar getAstar() {
		if (astar == null) {
			astar = new Astar();
		}
		return astar;
	}

	/**
	 * Especifica el mapa en el que realizar la busqueda
	 * @param map matriz tipo byte
	 * 			0-> bloque 
	 * 			1-> se puede pasar
	 */
	public void setMap(byte[][] map) {
		this.map = map;
		clean();
	}

	/** borra las listas */
	private void clean() {
		abierta.clear();
		cerrada.clear();
	}

	/**
	 * @param a punto de partida
	 * @param b punto destino
	 * @return direccion a tomar para llegar al destino
	 */
	public ArrayList<Nodo> getPath(Point a, Point b) {
		/*if (this.a != null && this.b != null && 
			this.a.equals(a) && this.b.equals(b)) {
			return null;
		}
		*/
		clean();
		this.a = new Nodo((Point) a.clone(), 0, 0);
		this.b = new Nodo((Point) b.clone(), 0, 0);
		getPath();
		return cerrada;
	}

	/**
	 *	Calcula el camino y lo deja guardado
	 */
	private void getPath() {
		Nodo aux;
		abierta.add(a);
		do {
			aux = sacaMenor(abierta);
			cerrada.add(aux);
			meteAdj(aux);
		} while (!caminoEncontrado() && !abierta.isEmpty());

		// Impresion de las listas resultantes para ver que es correcto
		/*
		System.out.println("-------------------CERRADA-------------------------------");
		for (int i = 0; i < cerrada.size(); i++) {
			System.out.println("" + cerrada.get(i));
		}
		System.out.println("-------------------ABIERTA-------------------------------");
		for (int i = 0; i < abierta.size(); i++) {
			System.out.println("" + abierta.get(i));
		}
		System.out.println("menor: "+aux);
		*/
	}

	
	/**
	 * @param p - punto que del que se quiere saber si bloquea el paso
	 * @return True si bloquea el paso, false en caso contrario
	 */
	private boolean isBlock(Point p) {
		if ((p.y < 0 || p.y >= map.length) || (p.x < 0 || p.x >= map[0].length) || 
				(map[p.y][p.x] == Tablero.BLOQUEADO)) {
			return true;
		}
		return false;
	}

	/**
	 * Va introduciendo los nodos adjacentes al padre a las listas
	 * @param padre
	 */
	private void meteAdj(Nodo padre) {
		// bucles para comprobar los puntos alrededor del nodo padre
		for (int x = -1; x < 2; x++) {
			for (int y = -1; y < 2; y++) {
				Point aux = new Point(padre.getPoint().x + x, padre.getPoint().y + y);
				if (!isBlock(aux) && !padre.esDiagonal(aux)) {
					
					int g = map[aux.y][aux.x] + padre.getG();
					int h = manhattanCost(aux, b.getPoint());
					Nodo actual = new Nodo(aux, padre, g, h);
					
					if (!estaPuntoEn(cerrada, aux)) {
						if (!estaPuntoEn(abierta, aux)) {
							abierta.add(actual);
							//System.out.println(aux + "\t metiendo en abierta: "+ abierta.get(abierta.size() - 1));
						} else {
							actual = getNodo(abierta, aux);
							//System.out.println("\t el nodo esta en abierta-" + aux + "-"+ actual);
							if (actual.getG() > g) {
								actual.setG(g);
								actual.setPadre(padre);
							}

						}
					} else {
						//System.out.println(aux + "\t el nodo esta en cerrada");
					}
				} else {
					//System.out.println(aux + "\t es bloque o diagonal");
				}
			}
		}
	}

	
	/**
	 * Comprueba si un punto esta en una nodo de la lista
	 * @param lista lista de nodos
	 * @param p punto a comprobrar si esta o no
	 * @return true si encuentra el punto, false en caso contrario
	 */
	private boolean estaPuntoEn(ArrayList<Nodo> lista, Point p) {
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).equals(p)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Comprueba si un nodo esta en la lista
	 * @param lista lista de nodos
	 * @param p punto a comprobrar si esta o no
	 * @return true si encuentra el nodo, false en caso contrario
	 */
	private boolean estaNodoEn(ArrayList<Nodo> lista, Nodo nod) {
		return estaPuntoEn(lista, nod.getPoint());
	}
	

	@SuppressWarnings("unused")
	private Nodo sacaPadre(ArrayList<Nodo> lista) {
		Nodo aux = null;
		for (int i = 0; i < lista.size() && aux != null; i++) {
			if (lista.get(i).esPadre()) {
				aux = lista.get(i);
				lista.remove(i);
			}
		}
		return aux;
	}

	
	/**
	 * Saca de una lista el nodo que tenga menor coste(f=g+h)
	 * @param lista
	 * @return el nodo con menor coste
	 */
	private Nodo sacaMenor(ArrayList<Nodo> lista) {
		Nodo menor = lista.get(0);
		int n = 0;
		for (int i = 1; i < lista.size(); i++) {
			if (menor.getF() > lista.get(i).getF()) {
				n = i;
				menor = lista.get(i);
			}
		}
		lista.remove(n);
		return menor;
	}

	/**
	 * Consigue el nodo de una lista segun un punto
	 * @param lista
	 * @param p
	 * @return el nodo del punto p
	 */
	private Nodo getNodo(ArrayList<Nodo> lista, Point p) {
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).equals(p)) {
				return lista.get(i);
			}
		}
		return null;
	}

	@SuppressWarnings("unused")
	private int manhattanCost(Nodo a, Nodo b) {
		return manhattanCost(a.getPoint(), b.getPoint());
	}

	/**
	 * @param a punto a 
	 * @param b	punto b
	 * @return distancia manhattan entre los puntos a y b
	 */
	private int manhattanCost(Point a, Point b) {
		return (int) Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
	}

	/**
	 * @return Point justo el siguiente punto al actual para alcanzar el camino
	 */
	public Point getNextPoint(){
		Nodo aux = null;
		if (cerrada == null){
			return null;
		}
		aux = cerrada.get(cerrada.size() -1 );
		while (!a.equals(aux.getPadre())) {
			aux = aux.getPadre();
		}
		return aux.getPoint();
	}
	
	/**
	 * Mira si el nodo objetivo esta en la lista cerrada
	 * @return true si se ha encontrado el camino
	 */
	private boolean caminoEncontrado() {
		return estaNodoEn(cerrada, b);
	}
}
