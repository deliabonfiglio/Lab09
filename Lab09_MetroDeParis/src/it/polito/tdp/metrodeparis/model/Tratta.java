package it.polito.tdp.metrodeparis.model;

import org.jgrapht.graph.DefaultWeightedEdge;

public class Tratta extends DefaultWeightedEdge{

	private Linea l;


	/**
	 * @return the l
	 */
	public Linea getL() {
		return l;
	}

	/**
	 * @param l the l to set
	 */
	public void setL(Linea l) {
		this.l = l;
	}
	
	
	
}
