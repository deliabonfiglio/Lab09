package it.polito.tdp.metrodeparis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import it.polito.tdp.metrodeparis.model.*;

import com.javadocmd.simplelatlng.LatLng;

public class MetroDAO {
	private Map<Integer, Fermata> mapfermate= new TreeMap<Integer, Fermata>();
	private Map<String, FermataConLinea> mapF= new HashMap<String, FermataConLinea>();

	public List<Fermata> getAllFermate() {

		final String sql = "SELECT id_fermata, nome, coordx, coordy FROM fermata ORDER BY nome ASC";
		List<Fermata> fermate = new ArrayList<Fermata>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Fermata f = new Fermata(rs.getInt("id_Fermata"), rs.getString("nome"), new LatLng(rs.getDouble("coordx"), rs.getDouble("coordy")));
				
				mapfermate.put(f.getIdFermata(), f);
				fermate.add(f);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return fermate;
	}
	
	public Map<Integer, Fermata> getMappaFermate(){
		return mapfermate;
	}

	public Map<Integer, Linea> getMappaLinea() {
		Map<Integer, Linea> mapL= new TreeMap<Integer, Linea> ();
		
		final String sql = "SELECT l.id_linea, l.velocita , l.nome, l.intervallo, l.colore "+
				"FROM linea l ";
		
		try{	
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

				while (rs.next()) {
					Linea l = new Linea(rs.getInt("id_Linea"), rs.getInt("velocita"), rs.getString("nome"), rs.getDouble("intervallo"), rs.getString("colore"));
					mapL.put(l.getId(), l);

				}
		
				st.close();
				conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return mapL;
	}
	
	public List<FermatePair> getFermateCollegate(){
		List<FermatePair> stops = new ArrayList<FermatePair>();
		
		final String sql = "SELECT * FROM connessione ";
		
		try{	
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

				while (rs.next()) {
					FermatePair fp = new FermatePair(rs.getInt("id_linea"), rs.getInt("id_stazP"), rs.getInt("id_stazA"));
					stops.add(fp);

				}
		
				st.close();
				conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		
		return stops;
	}

	public List<FermataConLinea> getFermateConLinea() {
		final String sql = "SELECT DISTINCT fermata.id_fermata, fermata.nome, fermata.coordX, fermata.coordY, connessione.id_linea "+
				"FROM fermata, connessione "+
				"WHERE fermata.id_fermata=connessione.id_stazP";
		
		List<FermataConLinea> fermate = new ArrayList<FermataConLinea>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				FermataConLinea f = new FermataConLinea(rs.getInt("id_Fermata"), rs.getString("nome"), new LatLng(rs.getDouble("coordx"), rs.getDouble("coordy")),rs.getInt("id_linea"));
				fermate.add(f);
				
				mapF.put(f.getIdFermata()+"_"+f.getId(), f);
				Fermata myf = mapfermate.get(f.getIdFermata());
				myf.addChild(f);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}
		return fermate;
	}
	
	public Map<String, FermataConLinea> getMappaFermateConLinea(){
		return mapF;
	}
	
}
