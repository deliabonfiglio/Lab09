package it.polito.tdp.metrodeparis.model;

public class FermatePair {

	private int f1;
	private int f2;
	private int id_linea;
	
	public FermatePair(int id_linea, int f1, int f2) {
		super();
		this.id_linea = id_linea;
		this.f1 = f1;
		this.f2 = f2;
	}

	/**
	 * @return the f1
	 */
	public int getF1() {
		return f1;
	}

	/**
	 * @param f1 the f1 to set
	 */
	public void setF1(int f1) {
		this.f1 = f1;
	}

	/**
	 * @return the f2
	 */
	public int getF2() {
		return f2;
	}

	/**
	 * @param f2 the f2 to set
	 */
	public void setF2(int f2) {
		this.f2 = f2;
	}

	/**
	 * @return the id_linea
	 */
	public int getId_linea() {
		return id_linea;
	}

	/**
	 * @param id_linea the id_linea to set
	 */
	public void setId_linea(int id_linea) {
		this.id_linea = id_linea;
	}

	
	
}