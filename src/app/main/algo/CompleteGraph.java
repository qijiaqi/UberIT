package app.main.algo;

import app.main.model.Carte;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CompleteGraph implements Graph {
	//private static final int MAX_COST = 40;
	//private static final int MIN_COST = 10;
	int nbSommet;

	public double[][] getMatriceCout() {
		return cout;
	}

	double[][] cout;
	List<Node> [][] cheminsIntermediares;
	List<Long> etapes;
	HashMap<Long, Integer> correspondance;

	public CompleteGraph(List<Long> etapes, HashMap<Long, Integer> correspondance, double[][] coutImpossibles) {
		this.etapes = etapes;
		this.nbSommet = correspondance.size();
		this.correspondance = correspondance;
		this.cout = coutImpossibles;
		this.cheminsIntermediares = new ArrayList[nbSommet][nbSommet];
	}

	@Override
	public void FillGraph(Carte map){
		for (int i=0; i<nbSommet; i++){
			Dijkstra algo = new Dijkstra();
			List<List<Node>> chemins = algo.dijkstra(etapes, etapes.get(i), map);
			for (List<Node> chemin : chemins){
				int j = correspondance.get(chemin.get(chemin.size()-1).id);
				if (cout[i][j] != Double.MAX_VALUE){
					cout[i][j] = chemin.get(chemin.size()-1).distance;
					cheminsIntermediares[i][j] = new ArrayList<>();
					for (Node node : chemin){
						cheminsIntermediares[i][j].add(node);
					}
				}
			}
		}
	}

	@Override
	public int getNbSommet() {
		return nbSommet;
	}

	@Override
	public double getCout(int i, int j) {
		if (i<0 || i>=nbSommet || j<0 || j>=nbSommet)
			return -1;
		return cout[i][j];
	}

	@Override
	public double[][] getCouts() {
		return cout;
	}

	@Override
	public List<Node> getCheminsIntermediares(int i, int j) {
		if (i<0 || i>=nbSommet || j<0 || j>=nbSommet)
			return null;
		return cheminsIntermediares[i][j];
	}

	@Override
	public int getSizeCheminsIntermediares(int i, int j) {
		return cheminsIntermediares[i][j].size();
	}

	@Override
	public boolean isArc(int i, int j) {
		if (i<0 || i>=nbSommet || j<0 || j>=nbSommet)
			return false;
		return i != j;
	}

}