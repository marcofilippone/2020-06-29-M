package it.polito.tdp.imdb.model;

public class DirectorPeso implements Comparable<DirectorPeso>{
	private Director d;
	private Integer peso;
	public DirectorPeso(Director d, Integer peso) {
		super();
		this.d = d;
		this.peso = peso;
	}
	public Director getD() {
		return d;
	}
	public void setD(Director d) {
		this.d = d;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(DirectorPeso other) {
		return -this.getPeso().compareTo(other.getPeso());
	}
	
	

}
