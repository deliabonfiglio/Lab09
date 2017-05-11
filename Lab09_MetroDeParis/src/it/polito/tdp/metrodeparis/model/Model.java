package it.polito.tdp.metrodeparis.model;

import java.util.*;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.*;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.metrodeparis.dao.MetroDAO;
import org.jgrapht.alg.DijkstraShortestPath;


public class Model {
	private List<Fermata> fermate;
	private Map<Integer, Linea> mappaL;
	private Map<Integer, Fermata> mappaF;
	private WeightedGraph<Fermata, DefaultWeightedEdge> graph ;
	
	public Model(){	
	}

	public List<Fermata> getFermate() {		
		if(fermate==null){
			MetroDAO dao = new MetroDAO();
			fermate = new ArrayList<Fermata>(dao.getAllFermate());
			mappaF= new TreeMap<Integer, Fermata>(dao.getMappaFermate());
		}
		return fermate;
	}

	public void createGraph(){
		if(graph==null)
			this.graph = new WeightedMultigraph<Fermata, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		MetroDAO dao = new MetroDAO();
		mappaL = new TreeMap<Integer, Linea>(dao.getMappaLinea());
		
		// crea i vertici del grafo
		Graphs.addAllVertices(graph, this.getFermate()) ;
	
		// crea gli archi del grafo -- versione 3 con aggiunta dei pesi
		for(FermatePair cp : dao.getFermateCollegate()) {
			Fermata f1 = this.cercaFermata(cp.getF1());
			Fermata f2 = this.cercaFermata(cp.getF2());
			Linea l = this.cercaLineaa(cp.getId_linea());
					
			double distanza = LatLngTool.distance( f1.getCoords(), f2.getCoords(), LengthUnit. KILOMETER);
			double peso = distanza/l.getVelocita();
			
			DefaultWeightedEdge e = graph.addEdge(f1, f2);
			graph.setEdgeWeight(e, peso);
						
		}

	}
	
	public List<DefaultWeightedEdge> camminoMinimo(Fermata f1, Fermata f2){
		
		return  DijkstraShortestPath.findPathBetween(graph, f1, f2);
	}
	
	
	public double pesoCamminoMinimo(Fermata f1, Fermata f2){
		DijkstraShortestPath<Fermata, DefaultWeightedEdge> dj= new DijkstraShortestPath<Fermata, DefaultWeightedEdge>(graph, f1, f2);
		
		return dj.getPathLength()*3600+dj.getPathEdgeList().size()*30;
		}
	
	private Fermata cercaFermata(int id_fermata){
		if(mappaF.containsKey(id_fermata))
			return mappaF.get(id_fermata);
		return null;
	}
	
	private Linea cercaLineaa(int id){
		if(mappaL.containsKey(id))
			return mappaL.get(id);
		return null;
	}
	
}

