package it.polito.tdp.imdb.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	private SimpleWeightedGraph<Director, DefaultWeightedEdge> grafo;
	private Map<Integer, Director> idMap;
	private ImdbDAO dao;
	private Director partenza;
	private int max;
	private int sommaAttori;
	private int sommaAttoriFinale;
	private List<Director> soluzioneOttima;
	
	public Model(){
		dao = new ImdbDAO();
	}
	
	public void creaGrafo(int anno) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		idMap = new HashMap<>();
		dao.getVertici(anno, idMap);
		Graphs.addAllVertices(this.grafo, idMap.values());
		for(Adiacenza a : dao.getArchi(anno, idMap)) {
			Graphs.addEdgeWithVertices(grafo, a.getD1(), a.getD2(), a.getPeso());
		}
	}
	
	public Set<Director> getVertexSet(){
		return this.grafo.vertexSet();
	}
	
	public Set<DefaultWeightedEdge> getEdgeSet(){
		return this.grafo.edgeSet();
	}
	
	public List<DirectorPeso> getAdiacenti(Director d){
		List<DirectorPeso> lista = new LinkedList<>();
		for(Director director : Graphs.neighborListOf(grafo, d)) {
			Integer peso = (int) grafo.getEdgeWeight(grafo.getEdge(d, director));
			lista.add(new DirectorPeso(director, peso));
		}
		Collections.sort(lista);
		return lista;
	}
	
	public List<Director> trovaPercorso(Director partenza, int max){
		this.partenza=partenza;
		this.max=max;
		List<Director> parziale = new LinkedList<>();
		parziale.add(partenza);
		sommaAttori = 0;
		this.sommaAttoriFinale = 0;
		this.soluzioneOttima = new LinkedList<>();
		cerca(parziale);
		return this.soluzioneOttima;
	}
	
	private void cerca(List<Director> parziale) {
		if(sommaAttori == max) {
			if(parziale.size() > this.soluzioneOttima.size()) {
				this.soluzioneOttima = new LinkedList<>(parziale);
				this.sommaAttoriFinale = this.sommaAttori;
			}
			return;
		}
		
		else if(sommaAttori < max && parziale.size() > this.soluzioneOttima.size()) {
			this.soluzioneOttima = new LinkedList<>(parziale);
			this.sommaAttoriFinale = this.sommaAttori;
		}
		
		for(Director d : Graphs.neighborListOf(grafo, parziale.get(parziale.size()-1))) {
			int peso = (int) grafo.getEdgeWeight(grafo.getEdge(parziale.get(parziale.size()-1), d));
			if(!parziale.contains(d) && (sommaAttori+peso)<=max) {
				parziale.add(d);
				sommaAttori += peso;
				cerca(parziale);
				sommaAttori -= peso;
				parziale.remove(parziale.size()-1);
			}
		}
	}
	
	public int getSommaAttoriFinale() {
		return this.sommaAttoriFinale;
	}
}
