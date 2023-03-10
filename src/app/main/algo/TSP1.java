package app.main.algo;

import java.util.Collection;
import java.util.Iterator;
import java.util.*;

public class TSP1 extends TemplateTSP {
	@Override
	public double bound(Integer currentVertex, Collection<Integer> unvisited, double[][] cout, int nombre, int version) {
		double minIn = Double.MAX_VALUE, minOut = Double.MAX_VALUE;
		// Version 1
		switch (version) {
			default: return 0;
			// Version 1
			case 1:
				int nbUnvisited = unvisited.size();
				double minDistance = Double.MAX_VALUE;
				for (int i : unvisited) {
					minDistance = (cout[currentVertex][i] < minDistance) ? cout[currentVertex][i] : minDistance;
				}
				return minDistance * nbUnvisited;
			// Version 2
			case 2:
				PriorityQueue<Double> pq = new PriorityQueue<>();
				for(int i : unvisited){
					pq.add(cout[currentVertex][i]);
					for(int j : unvisited){
						pq.add(cout[i][j]);
					}
				}
				double minPath = 0;
				for(int i = 0; i < unvisited.size(); i ++){
					minPath += pq.poll();
				}
				return minPath;
			// Version 3 proposée par prof
			case 3:
				PriorityQueue<Double> pqd = new PriorityQueue<>();
				for(int i : unvisited){
					minIn = (minIn > cout[currentVertex][i]) ? cout[currentVertex][i] : minIn;
					minOut = (minOut > cout[i][0]) ? cout[i][0] : minOut;
					for(int j : unvisited) pqd.add(cout[i][j]);
				}
				double minBound = 0;
				for(int i = 0; i < unvisited.size() - 1; i ++){
					minBound += pqd.poll();
				}
				return minBound + minIn + minOut;
			// Version 4 MST Kruksal
			case 4:
				PriorityQueue<Edge> pqe = new PriorityQueue<>(new EdgeComparator());
				int[] circle = new int[nombre];
				for (int i : unvisited) {
					circle[i] = -i;
					minIn = (minIn > cout[currentVertex][i]) ? cout[currentVertex][i] : minIn;
					minOut = (minOut > cout[i][0]) ? cout[i][0] : minOut;
					for (int j : unvisited) {
						pqe.add(new Edge(i, j, cout[i][j]));
					}
				}
				double minTree = 0;
				int size = 0;
				while (size < unvisited.size() - 1) {
					//if(pq.isEmpty() || (minTree == Double.MAX_VALUE)) return Double.MAX_VALUE;
					Edge tmp = pqe.poll();
					if (circle[tmp.depart] != circle[tmp.arrive]) {
						size++;
						int m = Math.max(circle[tmp.depart], Math.max(circle[tmp.arrive], Math.max(tmp.depart, tmp.arrive)));
						circle[tmp.depart] = m;
						circle[tmp.arrive] = m;
						minTree += tmp.length;
					}
				}
				return minTree + minIn + minOut;
		}
	}

	@Override
	protected Iterator<Integer> iterator(Integer currentVertex, Collection<Integer> unvisited, Graph g, double[][] cout) {
		return new SeqIter(unvisited, currentVertex, g, cout);
	}

	@Override
	public void searchSolution(int timeLimit, Graph g, double[][] cout) {
		super.searchSolution(timeLimit, g, cout);
	}
}