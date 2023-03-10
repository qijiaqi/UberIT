package app.main.algo;

import java.util.*;
import java.util.Collection;
import java.util.Iterator;

public class SeqIter implements Iterator<Integer> {
	private Integer[] candidates;
	private int nbCandidates;

	@Override
	public String toString() {
		return "SeqIter{" +
				"candidates=" + Arrays.toString(candidates) +
				'}';
	}
	/**
	 * Create an iterator to traverse the set of vertices in <code>unvisited</code>
	 * which are successors of <code>currentVertex</code> in <code>g</code>
	 * Vertices are traversed in the same order as in <code>unvisited</code>
	 * @param unvisited
	 * @param currentVertex
	 * @param g
	 * @param cout
	 */

	public SeqIter(Collection<Integer> unvisited, int currentVertex, Graph g, double[][] cout) {
		this.candidates = new Integer[unvisited.size()];
		List<Double> sort = new ArrayList<>();
		Map<Integer, Double> ordre = new HashMap<>();
		for (Integer s : unvisited) {
			if (g.isArc(currentVertex, s)) {
				nbCandidates++;
				ordre.put(s, cout[currentVertex][s]);
				sort.add(cout[currentVertex][s]);
			}
		}
		Collections.sort(sort);
		int j = unvisited.size() - 1;
		for (int i = 0; i < sort.size(); i++) {
			for (HashMap.Entry<Integer, Double> e : ordre.entrySet()) {
				if (Objects.equals(e.getValue(), sort.get(i))) {
					if (j > -1) {
						candidates[j--] = e.getKey();
					}
				}
			}
		}
	}

	@Override
	public boolean hasNext() {
		return nbCandidates > 0;
	}

	@Override
	public Integer next() {
		nbCandidates--;
		return candidates[nbCandidates];
	}

	@Override
	public void remove() {}

}