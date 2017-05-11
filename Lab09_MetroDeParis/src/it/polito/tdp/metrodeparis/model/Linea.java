package it.polito.tdp.metrodeparis.model;

public class Linea {
	private int id;
	private int velocita;
	private String nome;
	private double intervallo;
	private String colore;
	
	public Linea(int id, int velocita, String nome, double intervallo, String colore) {
		super();
		this.id = id;
		this.velocita = velocita;
		this.nome = nome;
		this.intervallo = intervallo;
		this.colore = colore;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the velocita
	 */
	public int getVelocita() {
		return velocita;
	}

	/**
	 * @param velocita the velocita to set
	 */
	public void setVelocita(int velocita) {
		this.velocita = velocita;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the intervallo
	 */
	public double getIntervallo() {
		return intervallo;
	}

	/**
	 * @param intervallo the intervallo to set
	 */
	public void setIntervallo(double intervallo) {
		this.intervallo = intervallo;
	}

	/**
	 * @return the colore
	 */
	public String getColore() {
		return colore;
	}

	/**
	 * @param colore the colore to set
	 */
	public void setColore(String colore) {
		this.colore = colore;
	}
	
	

	
	
}
