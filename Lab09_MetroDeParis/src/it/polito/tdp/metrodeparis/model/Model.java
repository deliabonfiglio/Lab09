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
	private DijkstraShortestPath<FermataConLinea, DefaultWeightedEdge> dijkstra;
	private List<Fermata> fermate;
	private Map<Integer, Linea> mappaL;
	private Map<String, FermataConLinea> mappaFCL = new HashMap<String, FermataConLinea>();
	private WeightedGraph<FermataConLinea, DefaultWeightedEdge> graph ;
	private List<FermataConLinea> fermateL= new ArrayList<FermataConLinea>();
	private FermataConLinea p=null;
	private FermataConLinea d=null;
	MetroDAO dao = new MetroDAO();
	
	public Model(){	
	}

	public List<Fermata> getFermate() {		
		if(fermate==null){
			
			fermate = new ArrayList<Fermata>(dao.getAllFermate());
		}
		return fermate;
	}
	
	private List<FermataConLinea> getFermateConLinea(){
		if(fermateL.isEmpty()){
			
			fermateL= dao.getFermateConLinea();
			mappaFCL= dao.getMappaFermateConLinea();
		}
		return fermateL;
	}

	public void createGraph(){
		if(graph==null)
			this.graph = new WeightedMultigraph<FermataConLinea, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		
		mappaL = new TreeMap<Integer, Linea>(dao.getMappaLinea());
		
		// crea i vertici del grafo
		this.getFermate();
		Graphs.addAllVertices(graph, this.getFermateConLinea()) ;
		System.out.println(mappaFCL.size());
		// crea gli archi del grafo -- versione 3 con aggiunta dei pesi
		for(FermatePair cp : dao.getFermateCollegate()) {

			FermataConLinea f1 = mappaFCL.get(cp.getF1()+"_"+cp.getId_linea());
			FermataConLinea f2 = mappaFCL.get(cp.getF2()+"_"+cp.getId_linea());
			Linea l = this.cercaLinea(cp.getId_linea());
					
			double distanza = LatLngTool.distance( f1.getCoords(), f2.getCoords(), LengthUnit. KILOMETER);
			double peso = distanza/l.getVelocita();
			
			DefaultWeightedEdge e = graph.addEdge(f1, f2);
			graph.setEdgeWeight(e, peso);
		System.out.println(graph.toString());
		}


		//controllo se esistono fermate dove ci sono più linee per fare un cambio e aggiunta dei pesi	
				
				for(FermataConLinea fa : fermateL) {				
					for(FermataConLinea fb: fermateL){
						
						if(fa.getIdFermata()==fb.getIdFermata() && fa.getId()!=fb.getId()){
		//stessa fermata ma linee diverse devo creare diversi archi con diversi pesi
		//Il peso degli archi orientati che collegano la stessa stazione su diverse linee=intervallo linea
							DefaultWeightedEdge t=graph.addEdge(fa, fb);
							
							graph.setEdgeWeight(t, this.cercaLinea(fb.getId()).getIntervallo());
						}
					}				
			}
		//System.out.println(graph.toString());
			}
			
	public double pesoCamminoMinimo(Fermata partenza, Fermata destinazione){
		double peso=Double.MAX_VALUE;

		for(FermataConLinea fclP: partenza.getFiglie()){
			for(FermataConLinea fclD: destinazione.getFiglie()){
				
			dijkstra=new DijkstraShortestPath<FermataConLinea,DefaultWeightedEdge>(graph, fclP, fclD);
//genero un cammino per ogni figlia delle fermate di partenza e destinazione
//se il cammino attuale ha peso < di quello trovato prima, me lo salvo e aggiorno le stazioni di partenza e arrivo
				
			if(dijkstra.getPathLength()*3600+(dijkstra.getPathEdgeList().size())*30< peso){
					peso=dijkstra.getPathLength()*3600+(dijkstra.getPathEdgeList().size()-1)*30;
					
					this.p=fclP;
					this.d=fclD;
				}	
			}
		}	
		return peso;
	}
	
	public String camminoMinimo(){
		dijkstra=new DijkstraShortestPath<FermataConLinea,DefaultWeightedEdge>(graph, p, d);
		
		String stemp="Prendo Linea: "+ mappaL.get(p.getId()).getNome()+"\n"+ p.toString()+"\n";
		
		for(DefaultWeightedEdge e :dijkstra.getPathEdgeList()){
			if(graph.getEdgeSource(e).getIdFermata()==graph.getEdgeTarget(e).getIdFermata()){
				
				stemp+="Cambio Linea: "+mappaL.get(graph.getEdgeTarget(e).getId()).getNome()+"\n";
			}
			
			stemp+=graph.getEdgeTarget(e).toString()+"\n";		
		}
		
		return stemp ;
	}

	private Linea cercaLinea(int id){
		if(mappaL.containsKey(id))
			return mappaL.get(id);
		return null;
	}
	
}