package dijkstra;

import graph.Graph;
import graph.ShortestPath;
import java.util.HashMap;
import java.util.Map;

public class Dijkstra<T> implements ShortestPath<T> {

	Map<T, Integer> distances = new HashMap<>();  // distances minimales
	Map<T, T> predecesseurs = new HashMap<>();

	@Override
	public Distances<T> compute(Graph<T> g, T src, Animator<T> animator) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

}
