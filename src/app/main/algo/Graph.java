package app.main.algo;

import app.main.model.Carte;

import java.util.List;


public interface Graph {
	/**
	 * @return the number of vertices in <code>this</code>
	 */
	public abstract int getNbSommet();

	/**
	 * @param i
	 * @param j
	 * @return the cost of arc (i,j) if (i,j) is an arc; -1 otherwise
	 */
	public abstract double getCout(int i, int j);

	/**
	 * @return the table cout double[][]
	 */
	public abstract double[][] getCouts();

	/**
	 * @param i
	 * @param j
	 * @return the List of Intersections to go from the vertex i to the vertex j; null otherwise
	 */
	public abstract List<Node> getCheminsIntermediares(int i, int j);

	/**
	 * @param i
	 * @param j
	 * @return the size of the list of Intersections to go from the vertex i to the vertex j;
	 */
	public int getSizeCheminsIntermediares(int i, int j);

	/**
	 * @param i 
	 * @param j 
	 * @return true if <code>(i,j)</code> is an arc of <code>this</code>
	 */
	public abstract boolean isArc(int i, int j);

	/**
	 * Create a complete directed graph such that each edge has a weight that is equal to the distance
	 * @param map
	 * @return void
	 */
	public abstract void FillGraph(Carte map);
}
